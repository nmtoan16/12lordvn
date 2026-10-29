# Kế hoạch triển khai ver2 — Trang bị, kỹ năng và chiến đấu

Ngày lập: **22/10/2026**. Thư mục làm việc duy nhất: `D:/xampp/htdocs/12suquan/ver2`.

**Điều chỉnh theo yêu cầu ngày 23/10/2026:** thay chính sách chỉ xuất/nhập v1 thủ công bằng sao chép checkpoint v1 tự động trước lần khởi động chưa có bản lưu ver2. Dữ liệu ver2 hiện có được ưu tiên; nút Lấy bản lưu ver1 cho phép xác nhận thay thế. Giữ nguồn v1 chỉ đọc và namespace riêng, không đồng bộ hai chiều. Điều chỉnh này có ưu tiên so với các đoạn mô tả migration thủ công bên dưới; chi tiết thực thi trong `analysis/VER2-DATA.md`, checklist T22 và báo cáo kiểm thử cập nhật.

## 1. Phạm vi và trạng thái hiện tại

Đầu vào là `SPEC.md` người dùng cung cấp, cùng ba yêu cầu bổ sung trong chat: bỏ màn quảng cáo trang phân phối/crack khi khởi động; cho mua dù thiếu vàng; giới hạn mang tối đa ba trang bị vào trận. Những yêu cầu này ưu tiên hơn các phương án cũ trong SPEC. Tài liệu này là kế hoạch thực hiện, không phải báo cáo tính năng đã hoàn thành.

**Đã thực hiện bước chuẩn bị:** clone source hiện hành ở commit `f63b732e0418e318758bf907856dc224ac8423ab` (`new ui`) vào thư mục con `ver2`, có Git độc lập và branch `plan/ver2-equipment`. Không lấy nhầm bản giao diện cũ ở commit `b804a8b`. Đã đối chiếu SHA-256 của **784 file được Git quản lý**: bản sao giống từng byte với source hiện tại. SPEC và tài liệu cốt truyện DOCX cũng được sao chép. Chứng cứ nằm ở [baseline.json](baseline.json).

Không sao chép `node_modules`, bộ công cụ phát triển tải về hoặc kết quả chạy thử tạm. Khi triển khai sẽ chạy `npm ci` trong `ver2`; mã runtime, JAR, assets, analysis và test đều đã có trong bản sao. Remote trỏ về repository cha đã được gỡ khỏi Git của `ver2`, tránh push nhầm vào source gốc.

URL triển khai: **`http://localhost/12suquan/ver2/`**; khi publish, thêm `/ver2/` vào URL ứng dụng hiện tại. Toàn bộ đường dẫn tài nguyên tiếp tục tương đối với thư mục phiên bản.

**Trạng thái chạy:** bản sao hiện vẫn là hành vi của bản gốc. Chưa triển khai hệ thống trang bị hoặc bỏ quảng cáo. Chưa chạy game trong browser tại đường dẫn mới: bản clone hiện còn dùng tên IndexedDB của v1. **T01 phải hoàn tất trước lần chơi thử đầu tiên ở cùng origin**, để thao tác trong ver2 không ghi vào tiến trình v1. Trong lượt lập kế hoạch chỉ tạo bản sao và tài liệu; không sửa mã ứng dụng.

## 2. Quyết định sản phẩm

### 2.1. Mua/nâng cấp khi thiếu vàng

Yêu cầu đã chốt: nút Mua/Nâng cấp luôn hiển thị và bấm được khi món chưa MAX; thiếu vàng vẫn hoàn tất giao dịch và nhận bậc mới. Không buộc người chơi qua màn mua thêm vàng.

Chi tiết kinh tế **đề xuất để triển khai**, vì yêu cầu chưa chỉ rõ phần vàng thiếu được xử lý ra sao:

```text
giá = giá của bậc kế tiếp trong SPEC
vàng bị trừ = min(vàng hiện có, giá)
vàng sau mua = max(0, vàng hiện có - giá)
bậc sau mua = bậc hiện tại + 1
```

Ví dụ món giá 800: có 1.000 còn 200; có 300 còn 0 và mua thành công; có 0 vẫn mua thành công. Phần thiếu được miễn, không sinh nợ hoặc tự thêm gói vàng. Hộp xác nhận ghi giá niêm yết, số vàng thực trừ và bậc nhận được. Hủy xác nhận không thay đổi gì. MAX hiển thị trạng thái đã tối đa, không tạo giao dịch. Chặn xác nhận lặp trong lúc ghi dữ liệu để một lần bấm chỉ tăng một bậc.

Áp dụng chính sách này cho tab Trang Bị và các vật phẩm tiêu hao mua bằng vàng trong tab Vật Phẩm của **ver2**, để hành vi cửa hàng nhất quán. Các gói SMS mô phỏng miễn phí hiện có tiếp tục đi qua callback cũ; không thay số lượng vật phẩm hoặc bảng giá. Nếu mong muốn chỉ áp dụng cho trang bị, đây là một điểm phạm vi có thể thu hẹp trước T05.

Giá ở các cột SPEC được hiểu là **chi phí từng lần nâng lên bậc tương ứng**, không phải giá tích lũy. Giữ nguyên 18 mức giá và mọi thông số đã đề xuất; cân bằng chỉ được đo và báo cáo, không tự chỉnh.

### 2.2. Sở hữu sáu món, mang tối đa ba món

- Người chơi có thể sở hữu/nâng cả sáu món. Mỗi món có bậc 0–3.
- Có đúng ba ô lắp; được mang 0, 1, 2 hoặc 3 món. Không được lắp trùng hoặc lắp món bậc 0.
- Cả Bí Kíp Võ Học và Thần Thạch đều chiếm một ô như các món khác. Vật phẩm tiêu hao gốc không chiếm ô trang bị.
- Mua mới không tự lắp. Khi chọn món thứ tư, UI yêu cầu chọn một ô để thay hoặc hủy; không tự tháo món cũ.
- Cho lắp/tháo trong tab Trang Bị, màn Thông Tin và màn chuẩn bị trận sau khi đã thấy đối thủ. Trước trận hiển thị tên/đặc tính đối thủ mà game đã có, ba ô hiện tại và mô tả tác dụng.
- Khi xác nhận Đánh, chụp `loadout` gồm ID và bậc của tối đa ba món. Toàn bộ công thức trong trận dùng bản chụp này. Món sở hữu nhưng chưa lắp có hiệu lực bằng bậc 0.
- Không đổi trang bị giữa trận. Nếu đang có trận tạm lưu rồi quay ra cửa hàng, mua/nâng cấp chỉ có tác dụng từ trận kế tiếp; tiếp tục trận cũ dùng bậc đã chụp khi bắt đầu.
- Không dựng lại pha chuẩn bị trận khi mở/đóng bảng lắp đồ, tránh tiêu Ngọc Ấn hoặc tung lại lượt đi trước nhiều lần.

### 2.3. Bỏ màn quảng cáo khởi động

Đã xác định bằng source decompile: `analysis/decompiled/cn.java:132` vẽ `www.giaitri321.pro`; `cn.run()` điều khiển hai pha logo; `cn.a()` tải `/_partnerLogo`. `cn.b()` còn thiết lập kích thước canvas và gọi `MGMIDlet.c()`, nên không thể xóa cả lớp hoặc nhảy qua tất cả khởi tạo.

Thiết kế: adapter riêng bỏ phần hiển thị/quãng chờ quảng cáo của `cn`; trong lúc VM thực sự còn khởi tạo chỉ dùng thông báo nạp trung tính trên nền game. Giữ điều kiện `MGMIDlet.d` sẵn sàng, thiết lập 240×320 và gọi bước chuyển màn đúng một lần. Giữ tranh bìa game, menu, cốt truyện và credits tác giả.

Không sửa JAR; không bật `cx.F` để bỏ splash, vì cờ này còn ảnh hưởng giao thức và các đường xử lý khác. Không chỉ giấu iframe đến hết quảng cáo rồi vẫn giữ toàn bộ thời gian chờ giả. Kiểm tra từ frame đầu, cả lần mở sạch cache, mở lại, restart sau import và máy chạy chậm: không lóe quảng cáo trong một frame.

## 3. Bằng chứng kỹ thuật và các sửa rõ nghĩa cho SPEC

| Thành phần | Kết quả đối chiếu | Hệ quả thiết kế |
|---|---|---|
| Source hiện hành | HEAD `f63b732`, có giao diện mới | Bắt đầu từ clone này và kiểm tra lại viewport; không coi báo cáo kiểm thử cũ là kết quả của ver2 |
| Tên DB | `loan12suquan-fs-v1`, `loan12suquan-jars-v1`, `loan12suquan-method-cache-v1`; key `loan12suquan:lastAppVersion` | Đường dẫn URL không tách storage; phải đổi namespace trong ver2 trước browser test |
| Màn thông tin | `analysis/decompiled/ad.java`, `b(Graphics,int,int)` rỗng; khung gốc cao 80px | Hook lớp `ad` package gốc và mở rộng bố cục có kiểm soát; không hook `com/mic/smsgame/suquan/ad` vì đó là âm thanh |
| Vàng | `q.E[2]` là cache của shop; dữ liệu qua `bg.v()`, `bg.f(2)`, `bg.b(2,amount)` và khóa logic `161b/9` | Ghi qua API game rồi refresh cache/UI; không chỉ sửa mảng E để tạo vàng ảo |
| RMS | `dr` ánh xạ khóa logic sang nhiều physical RecordStore records | `120` là khóa logic đề xuất của `dr`, không phải gọi `RecordStore.setRecord(120)` trực tiếp hoặc sửa byte file RMS |
| Kiếm Lửa | Xác suất 11% **khi ô sinh ra đã là kiếm** | Nâng lên 20%/25% là xác suất có điều kiện; không đổi thành 20%/25% của tất cả ô |
| Lương thực | Người chơi -1/tick khi nghĩ, AI -120 lúc chọn nước | Giảm hao lương phải giữ nhịp và phần lẻ; không biến thành chỉ trừ lúc hết lượt |
| Mana | Trần 36; chi phí theo vị trí chiêu 12/24/36 | Hoàn mana khác giảm chi phí đầu vào; UI vẫn yêu cầu đủ chi phí trước khi cast |
| Khiên gốc | `bn.k(int)` chặn HP khi miễn nhiễm, giảm bằng Quỷ Diện Giáp; `bn.a(int,boolean)` xử lý mất mana | Cần một pipeline rõ thứ tự để tránh trừ HP/hồi HP hai lần hoặc nhầm mất mana do cast với do địch |
| Snapshot trận | `161b/106` có cấu trúc nhị phân và buffer gốc 400 byte | Dữ liệu mới đặt ở sidecar riêng, không nối byte vào snapshot gốc |
| Hook runtime | `ReplaceMethod` đơn lẻ không bắt mọi lần gọi cold; đã gặp ở `offline-shop.js` | Dùng registry chung cho đường interpreter/JIT, không chồng các wrapper tháo lẫn nhau |

Các chữ ký Java overload và điểm gọi sẽ được xác nhận bằng metadata/bytecode ở T02 trước khi đăng ký hook. Source decompile là bằng chứng đọc hiểu, không phải project Java có thể biên dịch lại nguyên trạng.

## 4. Kiến trúc dự kiến

Mọi đường dẫn sau đều nằm dưới `ver2/`. Tách module để tránh đưa toàn bộ lưu trữ, UI, chiến đấu và VFX vào một file `equipment-shop.js` lớn.

| File dự kiến | Trách nhiệm |
|---|---|
| `runtime/ver2-config.js` | ID phiên bản, namespace và cờ tính năng; nạp trước DB/cache |
| `runtime/java-hooks.js` | Registry hook có thứ tự, tra field theo tên/chữ ký, gọi implementation gốc, kiểm tra tương thích |
| `runtime/clean-startup.js` | Bỏ riêng quảng cáo startup, giữ lifecycle |
| `runtime/equipment/catalog.js` | Một nguồn dữ liệu cho 6 món × 3 bậc, giá và mô tả; không chứa state |
| `runtime/equipment/rules.js` | Hàm thuần: giá, bậc, kiểm tra 3 ô, effective tiers, tính toán giao dịch |
| `runtime/equipment/store.js` | Codec, đọc/ghi `dr`, migrate, journal và lifecycle hồ sơ |
| `runtime/equipment-shop.js` | Hai tab, grid 2×3, xác nhận mua/nâng cấp, input khi cửa hàng mở |
| `runtime/equipment/loadout.js` | Lắp/tháo/thay món, ba ô và khóa cấu hình khi vào trận |
| `runtime/equipment/combat.js` | Battle context, nguồn sát thương, heal, barrier, mana, lương, phần thưởng |
| `runtime/equipment/battle-save.js` | Sidecar lưu các hiệu ứng đang diễn ra và nối với snapshot gốc |
| `runtime/equipment/skills.js` | Điều chỉnh 9 kỹ năng theo effective tier của Bí Kíp; hook vùng phá ô/cast |
| `runtime/equipment/info.js` | Thông Tin và mô tả Tuyệt Chiêu đọc cùng quy tắc thực thi |
| `runtime/equipment/hud.js` | Thanh barrier, số nổi và hiển thị ba món đang mang |
| `runtime/equipment/vfx.js` | Các hiệu ứng tăng cường; giới hạn số đối tượng và dọn khi đổi màn |

Luồng dữ liệu: catalog + RMS → hồ sơ → ba ô loadout → snapshot khi Đánh → pipeline chiến đấu → sự kiện HUD/VFX. UI không sửa trực tiếp HP/vàng. VFX không tính sát thương. Hooks chỉ gọi rule/service và nối lại luồng gốc.

Hợp đồng dự kiến:

- `getProfile()` trả bản đọc của bậc sở hữu, loadout và revision.
- `purchaseNextTier(id, transactionId)` kiểm tra MAX, tính vàng, commit một lần; trả bậc mới và vàng thực trừ.
- `setLoadout(ids, expectedRevision)` xác nhận tối đa ba món sở hữu duy nhất; bị từ chối khi ngữ cảnh đang khóa.
- `beginBattle(originalBattle)` tạo `battleId`, `activeTiers`, `levelsAtStart`; áp buff đầu trận một lần.
- `resolveDamage/heal/cast/turnSettled(event)` mang `side`, `source`, `actionId`, `groupId`, `isReflected` để loại lặp và phân biệt nguồn.
- `serializeBattle/restoreBattle` lưu/phục hồi trạng thái mới cùng liên kết snapshot gốc.

Đăng ký hook trước khi các class game được resolve. Kiểm tra đủ chữ ký và hash game ngay lúc bootstrap; khi adapter lỗi, ngừng phiên ver2 kèm thông báo rõ và khả năng tải bản lưu, không tiếp tục một trận với nửa bộ quy tắc đã áp dụng. Không đổi toàn cục hệ số/ID của bảng kỹ năng dùng chung cho AI.

## 5. Dữ liệu và tương thích save

### 5.1. Tách hoàn toàn v1/ver2

Namespace dự kiến: `loan12suquan-ver2-fs-v1`, `loan12suquan-ver2-jars-v1`, `loan12suquan-ver2-method-cache-v1`, `loan12suquan-ver2:lastAppVersion`. Phân biệt **phiên bản sản phẩm ver2** với **schema storage v1**. Rà soát thêm mọi IndexedDB/localStorage/cache do runtime mở, không chỉ ba chuỗi đã biết.

Không tự đọc hoặc migrate DB v1 khi mở ver2. Người dùng chủ động tải save từ v1 và nhập vào ver2. Sau đó hai tiến trình độc lập; kiểm thử cùng origin trong hai tab để chứng minh giao dịch, reload, import và xóa dữ liệu ver2 không đổi bản v1.

Giữ envelope `loan12-save-v1` và hash JAR để nhận save cũ. Đề xuất thêm metadata tùy chọn `variant: "ver2"`, `equipmentSchema: 1`; file tải ra có tên chứa `ver2`. Metadata không thay thế dữ liệu RMS và không được dùng để tin cậy các giá trị bất hợp lệ. Cam kết hướng nhập **v1 → ver2**; chiều ver2 → v1 không cam kết vì v1 không hiểu luật mới. Không sửa trình import ở v1.

### 5.2. Các khóa logic dự kiến

| Store/khóa | Dữ liệu | Quy tắc |
|---|---|---|
| `161b/120` | `schemaVersion`, `revision`, `ownedTiers[6]`, `equippedIds[0..3]` | Record chính theo SPEC; thiếu thì tạo mặc định 0/không lắp |
| `161b/121` | Sidecar trận: battle ID, fingerprint snapshot 106, ID/bậc đã chụp, barrier, cờ hồi khẩn cấp, phần lẻ hao lương, token chống proc lặp | Chỉ có hiệu lực với đúng trận đã lưu; không đặt vào buffer 106 |
| `161b/122` | Journal giao dịch mua: ID, revision cũ/mới, vàng cũ/mới và trạng thái commit | Hoàn tất hoặc phục hồi nhất quán sau đóng tab giữa lúc ghi |

`121/122` là đề xuất đặt chỗ, phải kiểm toàn bộ lời gọi `dr` và fixture RMS ở T04 để xác nhận không trùng, kể cả khóa tính gián tiếp. Chỉ gọi API `dr` đúng ngữ cảnh JVM; không gọi phương thức Java có thể yield như hàm JavaScript đồng bộ từ DOM.

Codec có magic/version/độ dài, số nguyên có miền kiểm tra và checksum phát hiện hỏng dữ liệu. Bậc phải 0–3, ID 0–5, tối đa ba ID khác nhau và đã sở hữu. Save có extension hỏng được báo rõ và giữ nguyên bản trước khi nhập; không âm thầm ghi lại bản hỏng thành mặc định. Save tương lai có schema chưa hỗ trợ bị từ chối trước khi ghi DB.

Vàng và trang bị nằm ở hai khóa khác nhau: `flush()` đơn thuần không bảo đảm giao dịch nghiệp vụ nguyên tử. Khi xác nhận mua: khóa input mua → journal → ghi số vàng/bậc bằng trạng thái đích tuyệt đối → commit revision → đóng journal. Recovery chạy trước khi mở shop/battle. Test ngắt ở từng bước, kể cả lần mua thiếu vàng.

Giữ map `16b/100`, tên và thuộc tính nhân vật `16b/101..105`, vật phẩm/vàng `161b/9`, snapshot `161b/106` và các khóa gốc khác theo cấu trúc hiện có. Không tự cấp buff đầu trận khi resume, không hồi barrier/khẩn cấp hoặc nhận +mana lần nữa. Battle legacy không có sidecar tiếp tục theo luật cũ đến hết trận; trang bị bắt đầu từ trận mới.

**Đề xuất vòng đời Chơi mới:** trang bị là vĩnh viễn trong tiến trình nhân vật, nên sau xác nhận tạo nhân vật mới sẽ reset sở hữu/loadout và sidecar; vào menu, đổi cảnh hoặc reload thì giữ nguyên. Thao tác hủy Chơi mới không reset. Cần chốt cách hiểu này trước T04 nếu muốn trang bị giữ xuyên các lần tạo nhân vật.

## 6. Quy tắc chiến đấu cần định nghĩa trước khi code

Các bảng thông số tại SPEC §1.3–1.4 là nguồn chuẩn. Những điểm chưa định lượng dưới đây là **đề xuất hoàn thiện thiết kế**, không phải số liệu đã chứng minh từ game gốc.

### 6.1. Nguyên tắc chung

- Chỉ `side=0` nhận modifier từ trang bị đang mang. Không lắp đồ/buff thụ động cho AI. Bàn cờ dùng chung nên các ô Kiếm Lửa do người chơi tạo vẫn có thể bị AI sử dụng về sau.
- Bậc cao kế thừa tính năng đã mở ở bậc thấp; nếu cùng một chỉ số có mức mới, lấy mức cao nhất, không cộng các bậc. Ví dụ bonus sát thương III là 50%, không phải 15+30+50%.
- Bậc I/II/III của Bí Kíp mở chiêu 1 / chiêu 1–2 / chiêu 1–2–3, chỉ khi Bí Kíp đang lắp. Loại hệ theo nhân vật, mana cost theo slot; không suy slot từ ID chiêu của đối thủ.
- Dùng số nguyên và làm tròn xuống tại các bước được công bố, clamp HP/mana theo giới hạn gốc. Mọi phép tính dùng HP thực bị mất để tránh hút máu từ sát thương bị chặn hoặc overkill.
- Mỗi cast/action/group có ID ổn định. Phản đòn không tự phản tiếp; phản đòn, hồi máu và sát thương đốt phụ không kích hoạt đệ quy cùng proc.
- Buff thời gian dùng bộ đếm lượt của game, không dùng `setTimeout`. Phân biệt tick, nhóm ghép, cascade, hành động và lượt thưởng trong test.

### 6.2. Từng nhóm trang bị

| Nhóm | Cách diễn giải triển khai |
|---|---|
| Thần Binh | Nhân bonus 15/30/50% trên sát thương kiếm/kỹ năng; chí mạng +15% khi nhóm kiếm có chiều ≥4, kế thừa ở bậc cao. Kiếm Lửa 20/25% là xác suất có điều kiện khi refill ra kiếm trong hành động người chơi. Đốt 5% HP hiện tại ở III: đề xuất một lần mỗi cast hoặc mỗi nhóm Kiếm Lửa, không mỗi ô bị nổ lan |
| Linh Châu | Tăng hồi Tim/Thuốc/phép 25/50/80%; regen cuối hành động người chơi 3/4/4% MaxHP. Hút máu kỹ năng 0/15/25% từ HP địch thực mất. Hồi khẩn cấp III một lần khi `0 < HP < 25% MaxHP`, hồi 30%; không hồi sinh sau HP=0. Đề xuất không nhân tiếp bonus hồi lên regen/hút máu/hồi khẩn cấp |
| Bảo Giáp | Barrier đầu trận 20/35/50% MaxHP; giảm damage 10/20/30%; từ II mỗi cast +10% MaxHP barrier. Đề xuất barrier cộng dồn tối đa MaxHP để tránh vô hạn; III phản 25% khi đã có barrier hoặc Khiên Sét trước cú đánh. “Miễn trừ Mana” hiểu là chặn mất mana do địch khi có khiên, vẫn trả phí dùng chiêu |
| Pháp Ấn | +8/14/20 mana đầu trận đúng một lần, vẫn clamp 36; +2/+3/+3 mana cho mỗi nhóm Âm Dương; refund 0/6/10 sau cast thành công. III thêm một lượt khi cast slot 3, không phát khi thiếu mana/hủy chọn; không thêm lần nữa khi resume |
| Bí Kíp | Áp đủ thay đổi của 9 chiêu trong SPEC; thông tin chiêu và thực thi dùng chung rule. Ô phá ngẫu nhiên chọn hợp lệ/không lặp; khi thiếu quân cùng loại thì clamp số ô thực có |
| Thần Thạch | Kế thừa +30% max lương, giảm 25% hao và 50% phạt swap sai. Hao 1/tick giữ phần lẻ để đạt 0,75/tick theo thời gian, không làm tròn mỗi tick thành 0. Vàng/EXP trên bàn ×1,5/×2 tại II/III, không nhân thưởng nhiệm vụ hoặc số vàng mua. Nhóm đúng 4 có 25% drop ở II+, không cộng thêm proc mới cho nhóm ≥5 đã có luật drop gốc |

Giới hạn barrier, kế thừa tính năng, điều kiện hồi khẩn cấp và phạm vi proc là quyết định cân bằng bổ sung cần được nhìn thấy khi duyệt kế hoạch; không che trong code như “chi tiết kỹ thuật”.

### 6.3. Thứ tự một sự kiện sát thương

1. Nhận sát thương gốc đã tính theo ô/cascade/skill; ghi nguồn và bên gây.
2. Áp modifier tấn công/chí mạng của trang bị người chơi đúng một lần; skill base mới chỉ thay điểm tính tương ứng, không nhân lại sát thương đã được hook.
3. Tính giảm sát thương Quỷ Diện Giáp và Bảo Giáp của bên nhận. Ghi nhận trạng thái khiên trước cú đánh.
4. Nếu Khiên Sét miễn nhiễm thì không mất HP/barrier; nếu không, barrier hấp thụ rồi HP nhận phần dư.
5. Phản đòn III đề xuất lấy 25% sát thương sau giảm ở bước 3, trước hấp thụ/miễn nhiễm, khi điều kiện có khiên đã đúng; phản một lần và không tạo vòng phản đòn.
6. Tính đốt phụ/hút máu trên các nguồn được cho phép; khiển trách swap sai không đi qua nhánh phản đòn hoặc hút máu.
7. Kiểm điều kiện hồi khẩn cấp khi người chơi còn sống, clamp; phát sự kiện HP/barrier thực thay đổi cho HUD.

Lifesteal của Lửa Địa Ngục và Linh Châu nếu cùng có được đề xuất cộng tỷ lệ trên cùng HP thực mất rồi hồi một lần. Hỏa III choáng một lượt và Băng III đóng băng +3 phải nối vào hệ lượt/buff gốc; Pháp Ấn +1 lượt và lượt thưởng ghép ≥4 vẫn cộng theo rule riêng, tránh vừa tăng counter vừa gọi chuyển lượt lần nữa.

## 7. UX, đồ họa và điều khiển

- Giữ canvas 240×320 và phong cách pixel/khung gỗ; tận dụng atlas, font bitmap, hiệu ứng đã trích. Icon trang bị mới ưu tiên phối/cắt từ tài nguyên sẵn có và lưu riêng trong `assets/ver2/`; không đổi ảnh nguyên bản. Cần kiểm tra độ nhận diện đủ sáu món, không dùng một icon mơ hồ cho mọi loại.
- Tab thống nhất nhãn **`[1] Vật Phẩm` / `[3] Trang Bị`**, khớp phím 1/3; `*`/`#` là chuyển tab thay thế chỉ khi shop đang mở. Trong battle chúng giữ công dụng gốc.
- Grid 2×3 có bậc, dấu đang lắp và dòng giá kế tiếp. Tên dài cuộn/ellipsis trong khu vực riêng; mô tả nhiều dòng được cuộn, không làm vỡ button/tab.
- Ba ô loadout nằm trong vùng riêng; chọn món → Lắp → chọn ô, hoặc Tháo. Khi cả ba ô đầy, hiển thị ba lựa chọn thay cùng tác dụng bị mất/nhận; Hủy giữ nguyên.
- Tách trạng thái `sở hữu`, `đang mang`, `bậc áp dụng trong trận hiện tại`. Món lên bậc nhưng trận tạm dừng đang dùng bậc cũ phải hiển thị rõ.
- Cửa hàng và panel chuẩn bị trận cần hỗ trợ bàn phím, softkey, pointer đúng tọa độ canvas sau scale; mở overlay phải chặn input xuyên xuống game và đồng bộ pause/clock.
- Màn Thông Tin `ad` hiện cao 80px, không đủ chỗ cho dãy mới. Thiết kế lại chiều cao/vị trí trong 240×320 hoặc thêm trang Trang Bị; không vẽ đè softkey/HP/EXP.
- HUD cơ bản: HP, mana và lương vẫn đọc được; barrier có màu riêng và số hấp thụ. Số damage/heal/barrier cơ bản luôn phản ánh cơ chế để người chơi hiểu tác dụng. Đề xuất Thần Thạch III mở biến thể vàng/hào quang/VFX tăng cường, không khóa toàn bộ thông tin chiến đấu sau món này; đây là cách giải quyết điểm chưa thống nhất giữa mục VFX chung và mô tả Thần Thạch III của SPEC.
- VFX theo pool hữu hạn, tối đa khoảng 12 nhãn nổi và 24 phần tử trang trí cùng lúc để đo thử; gộp số của cùng đối tượng trong một wave, không che quá hai ô cờ. Giảm hiệu ứng trang trí khi cần nhưng giữ thông tin combat. Ngừng cập nhật khi pause, dọn khi rời trận.
- Kiểm viewport 375/768/1440, zoom và fullscreen: không tràn ngang, control không ngắt chữ; hitbox đúng sau resize. Kiểm input cả keyboard lẫn cảm ứng giả lập bằng browser, không suy từ screenshot.

## 8. Trình tự, mốc nghiệm thu và điểm dừng kỹ thuật

Checklist chi tiết có dependency, file dự kiến và cách xác minh tại [todo.md](todo.md). Mỗi task giữ trong khoảng 2–5 file; các module được mở rộng từng bước, không tạo một bản vá lớn thay toàn bộ game.

| Mốc | Task | Kết quả có thể dùng/kiểm thử |
|---|---|---|
| M0 — Bản sao | T00 | **Đã xong:** clone độc lập, hash đối chiếu, SPEC được sao chép |
| M1 — Nền ver2 | T01–T03 | URL con chạy độc lập dữ liệu, hook an toàn, khởi động không quảng cáo |
| M2 — Cửa hàng | T04–T07 | Sáu món/18 bậc, mua thiếu vàng, ba ô lắp, save và reload |
| M3 — Chiến đấu nền | T08–T09 | Một món sát thương tác động thật, zero-equipment parity, resume không cấp buff lại |
| M4 — Sáu nhóm cơ chế | T10–T13 | Heal/barrier/mana/lương/phần thưởng theo các món đang lắp |
| M5 — Chín kỹ năng | T14–T16 | 3 hệ × 3 chiêu hoạt động đúng từng bậc Bí Kíp |
| M6 — Thông tin và VFX | T17–T19 | Mô tả khớp rule, HUD barrier/số nổi, biến thể thức tỉnh |
| M7 — Phát hành thư mục | T20–T21 | Ma trận regression, gói web chỉ cần đặt tại `/ver2/`, hướng dẫn chuyển save |

Đường phụ thuộc chính: T00 → T01 → T02 → T04 → T05 → T06/T07 → T08 → T09 → T10/T11/T12/T13 → T14/T15/T16 → T17/T18/T19 → T20 → T21. T03 độc lập với shop sau khi T02 xong. Khi chạy song song trong tương lai, chỉ tách việc sau khi hợp đồng dữ liệu/hook đã ổn định; một người chịu trách nhiệm cuối cho các điểm `bu/bn` chung.

**Bài thử khả thi sớm:** trước khi làm đủ sáu món, phải chứng minh hook cold/warm gọi đúng một lần, đọc/ghi khóa logic mới qua `dr`, mua một bậc và reload giữ vàng/bậc, rồi một đòn kiếm có modifier side0 mà side1 giữ công thức. Nếu chưa chứng minh được, xử lý adapter trước; không làm hàng loạt UI/VFX trên giả định chưa kiểm chứng.

## 9. Chiến lược kiểm thử

### Unit và đối chiếu với game gốc

- Giá/bậc/giao dịch: 18 mức; vàng 0, thiếu 1, bằng giá, dư; MAX, hủy, double-click, giao dịch bị ngắt và replay journal.
- Loadout: 0–3 món, món thứ tư, trùng ID, chưa sở hữu, giữ nguyên khi hủy thay, khóa trong battle, upgrade chờ trận sau. Kiểm đủ **42 tập hợp** tối đa ba món trong sáu món ở tầng validation.
- Các trường hợp tính damage/heal/barrier có số đầu vào/đầu ra được tính độc lập: shield miễn nhiễm, Quỷ Diện Giáp, barrier không đủ/đủ/thừa, overkill, HP sát ngưỡng 25%, reflect không lặp.
- RNG trong test dùng nguồn có seed/fixture, không thay xác suất production. Kiểm Kiếm Lửa có điều kiện, phá ô không lặp và clamp, drop4 tách drop5, các giới hạn min/max của mỗi phép.
- Kiểm regen theo action, mana refund chỉ một lần, cấp buff đầu trận một lần, lương giảm 0,75/tick dài hạn; pause không tiêu lương và không tăng VFX.
- Zero equipment phải cho kết quả như bytecode gốc với cùng trạng thái/chuỗi input/RNG. Snapshot state trước/sau lấy từ VM thật; không lấy expected từ chính hàm mới.

### Browser E2E

1. V1/ver2 cùng origin: tạo save riêng, mua/đổi/import ở ver2; so checksum RMS v1 trước/sau. Test dùng browser context tạm, không đọc profile cá nhân.
2. Khởi động mới, cache ấm, import/restart: không frame chứa logo quảng cáo, không request ngoài origin; vào chọn âm thanh/menu một lần.
3. Hai tab shop, đủ/thiếu/0 vàng, hủy/MAX, nâng 18 bậc; quay lại cửa hàng và tải lại trang.
4. Lắp ba món, thay món thứ tư/hủy, lắp trước đối thủ khác, xác nhận Đánh; không đổi giữa trận hoặc nhận buff từ món nằm trong kho.
5. Mỗi hệ có fixture đủ bậc/mana để kiểm đủ 9 chiêu. Ghi rõ fixture phục vụ test; đồng thời có một luồng người chơi tự nhiên từ menu → shop → map → battle.
6. Lưu bằng T.Dừng gốc ở state ổn định → export → VM mới → import → Tiếp tục: HP/barrier/loadout/cờ khẩn cấp/mana/lượt không bị tái cấp. Lưu map riêng, victory/defeat/arena riêng.
7. Save cũ không extension, extension hợp lệ/hỏng/tương lai, trận cũ không sidecar, sidecar sai fingerprint, Chơi mới/xác nhận/hủy.
8. Shop, loadout, chuẩn bị trận, Thông Tin và HUD ở 375/768/1440; input chuột/touch/keyboard, fullscreen/resize; không control wrap hoặc overflow.

### Đo hiệu năng và báo cáo

Đo frame time trên cùng máy/browser và cùng fixture v1/ver2, ít nhất 3 lượt khi cache ấm. Mục tiêu đề xuất: p95 thời gian xử lý frame vẫn nằm trong nhịp 40ms của game gốc; không có tăng trưởng bộ nhớ theo số trận, không có exception hoặc long-task lặp do VFX. Ghi rõ CPU/browser và phương pháp đo; không biến kết quả trên một máy thành cam kết mọi điện thoại.

Chạy lại regression hiện có từ **ver2** với baseURL đúng `/12suquan/ver2/`. Playwright hiện mặc định trỏ v1, phải sửa ở T01 và kiểm thêm URL thực tế để tránh test nhầm. Kết quả 27 test của phiên bản cũ chỉ là lịch sử, không phải nghiệm thu tính năng mới.

Lệnh cuối dự kiến: `npm run check`, `node --test tests/*.test.cjs`, `npm test`. Bổ sung cú pháp check cho tất cả module mới; không đánh dấu pass chỉ vì `node --check` không báo lỗi. Ghi bằng chứng và những phạm vi chưa chạy trong `analysis/VER2-VERIFICATION.md` khi thực sự có kết quả.

## 10. Rủi ro và cách kiểm soát

| Rủi ro | Cách kiểm soát |
|---|---|
| Ver2 dùng chung DB với v1 | Tách storage trước browser boot; test hai tab cùng origin; chỉ chuyển save bằng thao tác nhập |
| Hook cold/JIT hoặc các wrapper ghi đè nhau | Registry duy nhất, test cả cold/warm/cache-reload và số lần invoke |
| Trừ vàng nhưng chưa ghi được bậc, hoặc cấp bậc hai lần | Journal + revision + input lock + fault injection từng bước |
| Modifier vô tình áp AI | Nguồn event/side rõ, không sửa bảng class global; test đối xứng hai bên |
| Regen/reflect/đốt chạy nhiều lần theo cascade/frame | Action/group ID, quy tắc proc và nhật ký sự kiện trong test |
| Resume cấp lại barrier, mana hoặc hồi khẩn cấp | Sidecar gắn fingerprint trận, restore mode, đối chiếu trạng thái trước/sau |
| Đổi trang bị để farm buff đầu trận | Snapshot ID/bậc tại Đánh; panel chuẩn bị không tái tạo trận; khóa cấu hình của trận tạm lưu |
| UI mới chèn lên canvas hoặc phím * / # rơi xuống battle | Context router cho input, tọa độ chuẩn 240×320, đo responsive/hitbox |
| Giá không còn hạn chế người chơi vì mua thiếu vàng được miễn | Đây là lựa chọn sản phẩm đã nhận; chiều sâu chủ yếu ở giới hạn ba món. Báo cáo độ khó, không tự tăng giá hoặc giảm chỉ số |
| Các cơ chế cộng dồn làm chiến dịch quá dễ hoặc có vòng lặp | Kiểm tổ hợp mạnh: Thần Binh+Bí Kíp+Pháp Ấn; Linh Châu+Bảo Giáp+Bí Kíp; Bảo Giáp+Pháp Ấn+Thần Thạch; báo cáo trước khi đề xuất đổi cân bằng |

## 11. Các giả định đề xuất cần đọc cùng kế hoạch

Các lựa chọn “mua khi thiếu vàng”, “tối đa ba trang bị” và “bỏ quảng cáo” đã chốt theo yêu cầu, không hỏi lại. Giá/chỉ số trong SPEC được giữ làm chuẩn. Các chi tiết chưa được người dùng nêu rõ gồm: trừ hết phần vàng đang có; chính sách thiếu vàng áp cả tab vật phẩm; reset trang bị khi Chơi mới; kế thừa bậc; cap barrier; chống proc lặp và cộng hút máu; phân chia VFX cơ bản/Hoàng Kim. Tài liệu đã đưa phương án cụ thể để có thể duyệt/chỉnh trước các task liên quan, không yêu cầu trả lời mới có thể nhận bản kế hoạch này.

## 12. Xuất bản và bảo vệ bản hiện tại

Sau nghiệm thu, gói web gồm `index.html`, `.htaccess`, `Loan-12-Su-Quan.jar`, `runtime/`, `web/`, `assets/`, giấy phép/credits và các đích được trang đang liên kết tới. Kiểm crawler đường dẫn tương đối để không thiếu gallery/analysis/THIRD_PARTY. Không đưa `.git`, `node_modules`, tools và kết quả test vào gói publish.

Chép nguyên thư mục phát hành với tên **ver2** vào cùng vị trí của ứng dụng hiện tại; truy cập `<URL-hiện-tại>/ver2/`. Không cần thay route hoặc ghi đè v1. Kiểm trang chính, JAR, script, ảnh, audio và iframe đều lấy từ dưới `/ver2/`; test cả path con có tên khác trên server tạm để bắt URL viết cứng. Giữ HTTPS của website thật cho các API browser yêu cầu secure context.

Rollback chỉ thay thư mục ver2 bằng bản ver2 đã biết tốt và dùng save ver2 tương thích; không sửa/xóa DB v1. Giữ snapshot export trước mỗi migration schema. Mọi thay đổi mã, test, tài liệu và commit tiếp theo đều thực hiện trong `D:/xampp/htdocs/12suquan/ver2`.
