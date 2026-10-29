# Checklist triển khai ver2

Ngày lập: 22/10/2026. Kế hoạch/giả định: [plan.md](plan.md). Mọi đường dẫn bên dưới tính từ `D:/xampp/htdocs/12suquan/ver2`; mọi lệnh chạy trong thư mục đó. T00–T21 đã hoàn tất; lượt tổng cuối đạt 90/90 unit test và 51/51 browser test. Giới hạn kiểm chứng và hiệu năng được ghi rõ trong báo cáo. Các tên file/lệnh dự kiến bên dưới được ánh xạ sang test thực tế trong analysis/VER2-VERIFICATION.md.

Quy mô: **S** = 1–2 file; **M** = 3–5 file. Đây là quy mô thay đổi của một bước, không phải cam kết thời gian. Các thay đổi được commit theo nhóm phụ thuộc (nền JVM, persistence, combat, UI, phát hành) trong repository ver2; không commit source cha.

## M0 — Sao chép trước khi chỉnh sửa

### T00 — Clone baseline và sao chép đầu vào — S — HOÀN TẤT

- [x] Clone commit hiện hành `f63b732`, có Git riêng tại `ver2`, branch `plan/ver2-equipment`, không có remote push về source cha.
- [x] Sao chép SPEC và tài liệu cốt truyện; kiểm tra 784 file được quản lý giống SHA-256 với source hiện hành, kể cả giao diện mới, JAR và assets.
- [x] Ghi `tasks/baseline.json`; chưa chạy ứng dụng với namespace chung của bản clone.

**Xác minh:** so hash từng file; `git rev-parse HEAD` ở clone trước commit kế hoạch; source cha không có tracked diff.
**Phụ thuộc:** không. **Tệp hồ sơ:** `tasks/baseline.json`, `README-VER2.md`.

## M1 — Khởi động độc lập, không quảng cáo

### T01a — Tách namespace và cấu hình ver2 — M

**Việc làm:** tạo cấu hình ver2 nạp trước lớp storage; chuyển tất cả DB/key/cache sang tên riêng; mặc định test trỏ URL ver2.

- [x] Không mở DB/key của v1 trong quá trình boot ver2; version key rõ ràng và không dùng chung cache method giữa hai phiên bản.
- [x] JAR, script và ảnh đều được nạp bằng đường dẫn nằm trong ver2; envelope save cũ vẫn được chấp nhận theo kế hoạch.
- [x] Mặc định Playwright trỏ `/12suquan/ver2/`; hash JAR vẫn là hash gốc.

**Xác minh:** `npm ci`, `npm run check`; đọc lại toàn bộ nơi mở IndexedDB/localStorage; boot bằng browser context sạch sau khi namespace đổi, ghi danh sách DB.
**Phụ thuộc:** T00. **Tệp:** `runtime/ver2-config.js`, `runtime/bld/main-all.js`, `runtime/libs/compiled-method-cache.js`, `runtime/main.html`, `playwright.config.cjs`.

### T01b — Chứng minh v1/ver2 không ghi lẫn tiến trình — M

**Việc làm:** thêm bài test cô lập dữ liệu, chuyển helper VM dùng chung khỏi spec nếu cần, xác minh server/path con.

- [x] Hai tab cùng origin dùng hai hồ sơ giả lập độc lập; sửa/import/reset ver2 không đổi RMS v1; không dùng browser profile cá nhân.
- [x] Test thất bại khi cố tình trả namespace ver2 về tên v1 hoặc trỏ baseURL về v1.
- [x] Có kết quả regression baseline của clone, ghi nhận riêng nếu test cũ không còn khớp giao diện mới của commit `f63b732`.

**Xác minh:** `npx playwright test tests/version-isolation.spec.cjs`; `npm test`; kiểm URL iframe/JAR thực tế bằng request log.
**Phụ thuộc:** T01a. **Tệp:** `tests/version-isolation.spec.cjs`, `tests/helpers/vm.cjs`, `tests/game.spec.cjs`, `scripts/serve.mjs` nếu cần, `README-VER2.md`.

### T02 — Registry hook và kiểm chứng JVM sớm — M

**Việc làm:** thống nhất cách hook với adapter SMS hiện có; lập danh mục chữ ký đầy đủ cho `cn`, `dr/bg`, `q`, `ad`, `bu/bn`, `ah`, `i/bx`.

- [x] Mỗi hook được gọi đúng một lần ở lần chạy cold, sau JIT và sau cache/reload; wrapper không ghi đè hoặc tháo hook khác.
- [x] Gọi phương thức Java theo đúng ngữ cảnh VM, giữ continuation/yield/exception và thứ tự thực thi gốc; field lookup không hard-code heap address.
- [x] Đọc thử giá trị vàng và khóa chưa tồn tại qua `dr`; thiếu chữ ký dừng bootstrap có thông báo thay vì chạy adapter một phần.

**Xác minh:** `node --test tests/java-hooks.test.cjs`; probe browser chạy cold/warm và xen với SMS mock; mutation thay số lần gọi phải làm test fail.
**Phụ thuộc:** T01b. **Tệp:** `runtime/java-hooks.js`, `runtime/offline-shop.js`, `runtime/main.html`, `tests/java-hooks.test.cjs`, `analysis/VER2-HOOKS.md`.

### T03 — Loại bỏ màn quảng cáo startup — M

**Việc làm:** hook có phạm vi trong `cn`, bỏ nội dung phân phối/crack và thời gian chờ quảng cáo, giữ phần khởi tạo thật.

- [x] Không xuất hiện `www.giaitri321.pro` hoặc màn partner quảng cáo từ frame đầu; bìa/cốt truyện game còn nguyên.
- [x] Đợi trạng thái MIDlet sẵn sàng, thiết lập canvas đúng và chuyển tới âm thanh/menu đúng một lần; không dùng `cx.F` làm phím tắt.
- [x] Cold boot, warm boot, CPU chậm và restart sau import không trắng màn hình hoặc treo; JAR không bị sửa.

**Xác minh:** `npx playwright test tests/startup.spec.cjs`; ghi trace các lời vẽ nội dung quảng cáo và ảnh theo frame, không chỉ chụp menu cuối; đo thời gian bỏ phần chờ quảng cáo riêng với thời gian init thật.
**Phụ thuộc:** T02. **Tệp:** `runtime/clean-startup.js`, `runtime/main.html`, `tests/startup.spec.cjs`, `tests/game.spec.cjs` nếu helper phụ thuộc startup, `runtime/PATCHES.md`.

### Checkpoint M1

- [x] Regression của clone đạt, ver2 không chạm DB v1, màn quảng cáo không lóe một frame.
- [x] Có bằng chứng đường dẫn `/ver2/` và một isolate; source/JAR/ảnh gốc không đổi.

## M2 — Mua, nâng cấp, lắp ba món

### T04 — Schema trang bị, RMS và journal — M

**Việc làm:** kiểm kê khóa `dr`, đặt schema mới 120 và các khóa sidecar/journal; hoàn thiện lifecycle save/new game.

- [x] Đọc save v1 thiếu extension tạo bậc 0/loadout rỗng; schema hỏng hoặc tương lai không bị ghi đè ngầm.
- [x] Xác minh 120/121/122 không trùng khóa dùng trực tiếp hoặc gián tiếp; ghi qua `dr` giữ nguyên physical RecordStore mapping và các record gốc.
- [x] Transaction state hỗ trợ recovery và Chơi mới theo quyết định được ghi trong plan; reload/menu không reset bậc.

**Xác minh:** `node --test tests/equipment-store.test.cjs`; browser round-trip blob mới qua RMS, đóng/mở VM và so record gốc; fault injection vào codec/journal.
**Phụ thuộc:** T02; xem các giả định ở plan §5/§11. **Tệp:** `runtime/equipment/store.js`, `runtime/equipment/rules.js`, `runtime/main.html`, `tests/equipment-store.test.cjs`, `analysis/VER2-DATA.md`.

### T05 — Một giao dịch mua hoàn chỉnh, kể cả thiếu vàng — M

**Việc làm:** triển khai trước món Thần Binh bậc I từ shop đến RMS; đồng thời sửa đường thiếu vàng của vật phẩm có giá vàng trong ver2 theo phạm vi plan.

- [x] Giá 800: vàng 1.000 → 200, 300 → 0, 0 → 0; đều nhận đúng một bậc khi xác nhận; hủy không thay đổi.
- [x] Refresh cả dữ liệu `bg` và cache `q.E`; reload/export/import vẫn giữ bậc và vàng, không cấp đôi khi click/confirm lặp.
- [x] Đóng tab ở giữa các bước journal rồi mở lại cho một giao dịch nhất quán, không mất vàng riêng lẻ hoặc tự nâng hai bậc.

**Xác minh:** `node --test tests/purchase.test.cjs`; `npx playwright test tests/purchase.spec.cjs`; fault injection ở trước/sau mỗi lần ghi vàng/bậc/journal.
**Phụ thuộc:** T04. **Tệp:** `runtime/equipment-shop.js`, `runtime/equipment/catalog.js`, `runtime/main.html`, `tests/purchase.test.cjs`, `tests/purchase.spec.cjs`.

### Checkpoint sau T04–T05

- [x] Một món thực sự mua được ở 0 vàng, tồn tại sau reload và recover được từ giao dịch bị ngắt.
- [x] Không tiếp tục nhập đủ 18 bậc/UI khi chưa chứng minh được đường RMS và hook này.

### T06 — Đủ sáu món, 18 bậc và hai tab — M

**Việc làm:** mở rộng catalog từ bảng SPEC, thêm grid 2×3, mô tả/giá/MAX và mapping phím thống nhất.

- [x] Đủ sáu nhóm và 18 mức giá/chỉ số nguyên bản; mỗi món nâng lần lượt 0→1→2→3, MAX không thu vàng.
- [x] `[1] Vật Phẩm` và `[3] Trang Bị` khớp phím 1/3; */# chỉ đổi tab trong shop; click/touch chọn đúng ô.
- [x] Tab tiêu hao tiếp tục dùng callback và số lượng gốc; tên/bậc/giá không đè khung hoặc softkey.

**Xác minh:** `node --test tests/catalog.test.cjs`; `npx playwright test tests/equipment-shop.spec.cjs`; kiểm bảng kỳ vọng từ SPEC, không tạo expected bằng catalog implementation.
**Phụ thuộc:** T05. **Tệp:** `runtime/equipment/catalog.js`, `runtime/equipment-shop.js`, `assets/ver2/equipment-icons.png` nếu cần, `tests/catalog.test.cjs`, `tests/equipment-shop.spec.cjs`.

### T07 — Lắp/tháo và chốt loadout ba món trước trận — M

**Việc làm:** thêm ba ô vào quản lý trang bị và màn chuẩn bị trận; đóng băng ID/bậc khi xác nhận Đánh.

- [x] Kiểm đủ 42 tập hợp 0–3 món: không trùng, không bậc 0; món thứ tư phải chọn thay hoặc hủy, không tự thay.
- [x] Mua không tự lắp; món chưa lắp không nằm trong `activeTiers`; vật phẩm tiêu hao không chiếm ô.
- [x] Đổi loadout được trước trận khi đã thấy đối thủ; mở/đóng bảng không tiêu Ngọc Ấn lần nữa; battle/snapshot hiện tại giữ ID và bậc đã chụp.

**Xác minh:** `node --test tests/loadout.test.cjs`; `npx playwright test tests/loadout.spec.cjs`; kiểm pointer và bàn phím, reload, hủy thay và mua nâng cấp khi còn snapshot cũ.
**Phụ thuộc:** T06. **Tệp:** `runtime/equipment/loadout.js`, `runtime/equipment-shop.js`, `runtime/main.html`, `tests/loadout.test.cjs`, `tests/loadout.spec.cjs`.

### Checkpoint M2

- [x] Đủ sáu món, mua thiếu vàng thành công, tối đa ba món có trạng thái đang lắp; mọi thay đổi lưu được.
- [x] Shop/loadout ở 375/768/1440 không tràn/ngắt chữ; pointer không xuyên xuống game.

## M3 — Pipeline chiến đấu và resume

### T08 — Thần Binh và pipeline sát thương có phân biệt nguồn — M

**Việc làm:** tạo BattleContext và sự kiện action/group/cast, nối một modifier vào sát thương thật; triển khai các bậc Thần Binh.

- [x] Không mang món nào cho kết quả giống gốc; Thần Binh chỉ áp side0 đang lắp, sát thương/crit chỉ nhân một lần mỗi nhóm/cast.
- [x] Xác suất Kiếm Lửa dùng điều kiện “refill ra kiếm”; đốt 5% theo proc đã định nghĩa, không lặp theo frame/ô nổ lan.
- [x] Log test chứa damage gốc/sau modifier/HP thực mất, nguồn, side và action ID; AI không bị nhận modifier từ bảng dùng chung.

**Xác minh:** `node --test tests/equipment-combat.test.cjs`; `npx playwright test tests/combat-parity.spec.cjs`; fixture seed, sát thương bình thường/crit/fire/cascade và cả hai bên.
**Phụ thuộc:** T07. **Tệp:** `runtime/equipment/combat.js`, `runtime/equipment/rules.js`, `runtime/main.html`, `tests/equipment-combat.test.cjs`, `tests/combat-parity.spec.cjs`.

### T09 — Snapshot trận có trạng thái trang bị — M

**Việc làm:** lưu sidecar theo đúng thời điểm snapshot 106 gốc được tạo; thêm restore mode và vòng đời trận.

- [x] Battle ID/fingerprint/loadout/bậc ở đầu trận được khôi phục; dữ liệu gốc 106 vẫn đúng cấu trúc.
- [x] Resume không gọi buff đầu trận lại; battle legacy không sidecar giữ luật cũ, sidecar mồ côi không được áp sang trận khác.
- [x] Khung dữ liệu hỗ trợ barrier/cờ hồi khẩn cấp/phần lẻ lương/token proc; mỗi module sau phải mở rộng fixture resume của mình.

**Xác minh:** `node --test tests/battle-save.test.cjs`; `npx playwright test tests/equipment-resume.spec.cjs`; import/restart thật, giả lập ngắt giữa snapshot/sidecar, snapshot cũ và fingerprint sai.
**Phụ thuộc:** T08. **Tệp:** `runtime/equipment/battle-save.js`, `runtime/equipment/store.js`, `runtime/main.html`, `tests/battle-save.test.cjs`, `tests/equipment-resume.spec.cjs`.

### Checkpoint M3

- [x] Một build mang/không mang Thần Binh thay đổi sát thương đúng; tháo món trả về luật gốc.
- [x] Save/continue giữ đúng build và không phát lại sự kiện đầu trận.

## M4 — Các nhóm cơ chế còn lại

### T10 — Linh Châu: heal, regen, hút máu, cứu nguy — M

**Việc làm:** nối nguồn heal và damage thực mất vào modifier; cấp cờ dùng một lần mỗi trận.

- [x] Tim/thuốc/phép nhận đúng hệ số; regen chỉ chạy cuối hành động của mình, kể cả lượt thưởng, không theo frame/cascade.
- [x] Hút máu dùng HP thực mất, không hút từ immune/overkill/reflect; không nhân bonus hồi nhiều lần.
- [x] Cứu nguy đúng ngưỡng, chỉ một lần và chỉ khi còn sống; pause/resume không reset cờ.

**Xác minh:** `node --test tests/healing.test.cjs`; thêm case vào `tests/equipment-resume.spec.cjs`; fixture HP ngay trên/bằng/dưới 25%, 0 HP và đầy máu.
**Phụ thuộc:** T09. **Tệp:** `runtime/equipment/combat.js`, `runtime/equipment/rules.js`, `tests/healing.test.cjs`, `tests/equipment-resume.spec.cjs`.

### T11 — Bảo Giáp: barrier, giảm sát thương, phản đòn — M

**Việc làm:** thêm barrier state và thứ tự giải quyết với Khiên Sét/Quỷ Diện Giáp gốc.

- [x] Barrier đầu trận và sau cast đúng mức, cap theo quyết định trong plan; HP chỉ giảm phần dư sau hấp thụ, không hồi bù để giả lập barrier.
- [x] Phản 25% đúng nguồn/cơ sở, không phản đệ quy; mana immunity chặn drain do địch khi có khiên nhưng vẫn trả chi phí cast.
- [x] Barrier/hiệu lực trong trận giữ nguyên khi resume, không cấp lại; AI không tự có giáp người chơi.

**Xác minh:** `node --test tests/barrier.test.cjs`; mở rộng `tests/equipment-resume.spec.cjs`; test barrier vỡ/đủ/thừa, shield invul, Quỷ Diện Giáp, mana drain và reflect lethal.
**Phụ thuộc:** T10. **Tệp:** `runtime/equipment/combat.js`, `runtime/equipment/rules.js`, `tests/barrier.test.cjs`, `tests/equipment-resume.spec.cjs`.

### Checkpoint sau T10–T11

- [x] Không có vòng heal/reflect; HP, barrier, cứu nguy đều khớp trước/sau save.
- [x] Các mặc định về cap/kế thừa/nguồn heal được thể hiện thành case expected rõ ràng.

### T12 — Pháp Ấn: mana đầu trận, nhóm Âm Dương, refund, lượt thưởng — M

**Việc làm:** phát sự kiện cast thành công và dùng bộ đếm lượt thưởng gốc.

- [x] +8/14/20 chỉ khi bắt đầu trận; +2/+3/+3 mỗi nhóm và refund 0/6/10, luôn clamp 36.
- [x] Chi phí đầu vào 12/24/36 không bị nhầm với refund; hủy/thiếu mana không refund hoặc tạo lượt.
- [x] Bậc III slot3 thêm đúng một lượt; không phát lại sau resume hoặc gọi lại animation.

**Xác minh:** `node --test tests/mana.test.cjs`; bổ sung fixture resume/cast; nhiều nhóm Âm Dương trong cascade và mana sát trần.
**Phụ thuộc:** T11. **Tệp:** `runtime/equipment/combat.js`, `runtime/equipment/rules.js`, `tests/mana.test.cjs`, `tests/equipment-resume.spec.cjs`.

### T13 — Thần Thạch: lương, phạt sai, tài nguyên và drop — M

**Việc làm:** can thiệp đúng nguồn tài nguyên trên board và nhịp tiêu lương của người chơi.

- [x] Max lương +30%, hao giảm 25% có phần lẻ tích lũy; pause không hao; AI giữ cách tiêu lương cũ.
- [x] Swap sai giảm 50% phạt, không kích hoạt reflect/lifesteal; chỉ vàng/EXP trên bàn nhân 1,5/2, không nhân thưởng quest/gói mua.
- [x] Nhóm đúng4 có drop 25% ở II+, nhóm ≥5 giữ một đường drop gốc; không tạo hai vật phẩm cho một proc.

**Xác minh:** `node --test tests/board-rewards.test.cjs`; fixture 1.000 tick và pause, số lẻ, nguồn phần thưởng khác nhau; RNG xác định cho nhóm 4/5/6.
**Phụ thuộc:** T12. **Tệp:** `runtime/equipment/combat.js`, `runtime/equipment/rules.js`, `tests/board-rewards.test.cjs`, `tests/equipment-resume.spec.cjs`.

### Checkpoint M4

- [x] Cả sáu nhóm hoạt động theo `activeTiers`; món trong kho không mang không có tác dụng.
- [x] Kiểm ba build mạnh ghi trong plan, không tự điều chỉnh giá/chỉ số khi thấy khó dễ thay đổi.

## M5 — Nâng cấp chín chiêu

### T14 — Ba chiêu Hỏa — M

**Việc làm:** chỉ thay các điểm tính của skill người chơi khi Bí Kíp đang lắp và đủ bậc.

- [x] ID 0: hệ số 1,35 + hai ô Kiếm Lửa hợp lệ; ID 1: hệ số 2,0  + 5–6 vùng 2×2  + barrier 15%.
- [x] ID 2: hệ số 3,3  + hai vùng 4×4  + hút máu 20%  + choáng một lượt; bậc không đủ giữ chiêu gốc.
- [x] Kết hợp Thần Binh/Linh Châu/Pháp Ấn không nhân damage/refund/heal/proc hai lần; AI cùng ID dùng luật gốc.

**Xác minh:** `node --test tests/fire-skills.test.cjs`; `npx playwright test tests/skills.spec.cjs --grep Hỏa`; kiểm grid trước/sau và counters, không chỉ animation.
**Phụ thuộc:** T13. **Tệp:** `runtime/equipment/skills.js`, `runtime/main.html`, `runtime/equipment/combat.js`, `tests/fire-skills.test.cjs`, `tests/skills.spec.cjs`.

### T15 — Ba chiêu Lôi — M

**Việc làm:** mở rộng nhánh kỹ năng bằng cùng contract đã kiểm tra ở Hỏa.

- [x] ID 3: hệ số 1,2; phá 7–10 ô cùng loại và +4 mana; clamp khi bàn không đủ loại đó.
- [x] ID 4: miễn nhiễm 8 nhịp, gây 1,2×ATK sát thương và hồi 15% MaxHP; ID 5: 2,8×ATK, phá 5–6 vùng 3×3, trừ 12 mana và Khiên Sét 3 nhịp.
- [x] Bộ đếm hết hiệu lực theo nhịp gốc/lượt thưởng; không sửa định nghĩa shield global hoặc buff AI.

**Xác minh:** `node --test tests/lightning-skills.test.cjs`; `npx playwright test tests/skills.spec.cjs --grep Lôi`; tương tác mana immunity, barrier và reflect.
**Phụ thuộc:** T14. **Tệp:** `runtime/equipment/skills.js`, `tests/lightning-skills.test.cjs`, `tests/skills.spec.cjs`.

### T16 — Ba chiêu Băng — M

**Việc làm:** triển khai drain/heal/barrier/frozen theo cùng pipeline.

- [x] ID 6: 1,2×ATK, trừ 12 mana, phá 4–5 ô và +6 mana; ID 7: hồi 30% MaxHP, phá Tim và barrier 20% MaxHP.
- [x] ID 8: 2,2×ATK, trừ 24 mana, phá 5–7 ô, đóng băng +3 lượt, hồi 15% MaxHP; bậc Bí Kíp không đủ không mở hiệu ứng.
- [x] Frozen/lượt thưởng Pháp Ấn/bonus ghép cộng đúng và không chạy hai cơ chế skip turn chồng nhau; resume giữ bộ đếm.

**Xác minh:** `node --test tests/ice-skills.test.cjs`; `npx playwright test tests/skills.spec.cjs --grep Băng`; đối thủ đang Khiên Sét, mana 0, gần chết, nhiều lượt thưởng.
**Phụ thuộc:** T15. **Tệp:** `runtime/equipment/skills.js`, `tests/ice-skills.test.cjs`, `tests/skills.spec.cjs`, `tests/equipment-resume.spec.cjs`.

### Checkpoint M5

- [x] Đủ 9 kỹ năng × bậc 0/1/2/3 được kiểm số liệu, vùng phá ô và hiệu ứng phụ; tháo Bí Kíp trở lại kỹ năng gốc.
- [x] Mỗi hệ có ít nhất một trận chạy thật; fixture test được ghi rõ trong báo cáo.

## M6 — Màn thông tin, HUD và VFX

### T17 — Thông Tin và mô tả kỹ năng khớp thực thi — M

**Việc làm:** mở rộng lớp `ad` package gốc và bảng chọn chiêu `ah`; đọc mô tả từ rule đang hiệu lực.

- [x] Hiện đủ món sở hữu/bậc, ba món đang lắp và các tác dụng thực tế; tên dài không vỡ bố cục 240×320.
- [x] Mô tả damage/heal/chi phí/refund/hiệu ứng của chiêu khớp active tier; đang tiếp tục trận cũ hiển thị bậc đã chụp.
- [x] Không tác động lớp `com/mic/smsgame/suquan/ad` âm thanh; softkey/HP/EXP không bị che.

**Xác minh:** `npx playwright test tests/equipment-info.spec.cjs`; so mô tả với golden cases, kiểm 240×320 trong viewport 375/768/1440, tên dài và bậc MAX.
**Phụ thuộc:** T16. **Tệp:** `runtime/equipment/info.js`, `runtime/main.html`, `tests/equipment-info.spec.cjs`, `tests/catalog.test.cjs`.

### T18 — Thanh barrier và số nổi cơ bản — M

**Việc làm:** render qua sự kiện combat đã tính xong; có hiệu ứng hấp thụ/vỡ khiên.

- [x] Bar/số phản ánh HP/barrier thực; nhiều hit cùng wave không hiển thị số raw khác kết quả, không che HP/mana/lương.
- [x] Không tính combat trong renderer; không gắn clock VFX độc lập với pause; đổi trận dọn hết đối tượng cũ.
- [x] Nhãn damage/heal/block có phân biệt màu/ký hiệu; responsive/fullscreen giữ vị trí và hitbox.

**Xác minh:** `npx playwright test tests/equipment-hud.spec.cjs`; screenshot trước/sau absorb/break/heal và pause; stress multi-hit.
**Phụ thuộc:** T17. **Tệp:** `runtime/equipment/hud.js`, `runtime/main.html`, `runtime/equipment/combat.js`, `tests/equipment-hud.spec.cjs`.

### T19 — Hiệu ứng thức tỉnh và Thần Thạch III — M

**Việc làm:** thêm biến thể chiêu/hào quang dùng sprite gốc hoặc asset mới riêng, áp giới hạn pool.

- [x] Đủ biến thể được quy định, Thần Thạch III mở phong cách vàng; tháo món mất hiệu ứng tương ứng, chỉ số vẫn do pipeline quyết định.
- [x] Không chỉnh `assets/original/`, không resample đè atlas; hiệu ứng không che bàn cờ hoặc làm mất khả năng chọn ô.
- [x] Pool hữu hạn, không tăng bộ nhớ theo số lần cast/trận; có mức giảm trang trí khi hiệu năng không đạt.

**Xác minh:** `npx playwright test tests/equipment-vfx.spec.cjs`; đo frame time/pool/memory trên cùng fixture; kiểm hash asset gốc.
**Phụ thuộc:** T18. **Tệp:** `runtime/equipment/vfx.js`, `runtime/main.html`, `assets/ver2/vfx.png` nếu cần, `tests/equipment-vfx.spec.cjs`, `analysis/VER2-PERFORMANCE.md`.

### Checkpoint M6

- [x] UI/HUD/VFX khớp luật, nút/tab/nhãn gọn không ngắt chữ ở 375/768/1440; kiểm được cả keyboard và touch.
- [x] Có số đo hiệu năng trước/sau cùng điều kiện; không chỉ kết luận “mượt” bằng quan sát.

## M7 — Hồi quy và publish thư mục con

### T20 — Kiểm thử tích hợp, save cũ và tổ hợp build — M

**Việc làm:** chạy toàn bộ flow và ghi kết quả/các giới hạn thực tế.

- [x] `npm run check`, toàn bộ unit và browser tests đạt với URL ver2; kiểm hash JAR/ảnh, không uncaught error và không request ra ngoài origin.
- [x] Bao phủ ma trận save/loadout/cold-warm/9 kỹ năng theo plan; smoke trận chiến dịch, nhiệm vụ phụ, đấu trường, thắng/thua; không tuyên bố đã chơi toàn bộ chiến dịch nếu chỉ dùng fixture.
- [x] Có báo cáo cân bằng của các build mạnh, dữ liệu v1 không đổi; import lỗi giữ dữ liệu ver2 hiện có và báo rõ.

**Xác minh:** `npm test`; `node --test tests/*.test.cjs`; fault/mutation tests cho giới hạn 3 món, chống áp dụng hai lần và namespace; review diff trong Git ver2.
**Phụ thuộc:** T03,T19 và toàn bộ checkpoint trước. **Tệp:** `tests/ver2-integration.spec.cjs`, `tests/artifacts.test.cjs`, `package.json`, `analysis/VER2-VERIFICATION.md`, `analysis/VER2-BALANCE.md`.

### T21 — Gói triển khai `/ver2/` và tài liệu sử dụng — M

**Việc làm:** chuẩn bị thư mục phát hành, rà đường dẫn và hướng dẫn chuyển tiến trình, lưu snapshot trước migration.

- [x] Thư mục ver2 hoạt động chỉ bằng upload vào cạnh bản hiện tại; không cần sửa root index/route/database v1; mọi tài nguyên lấy đúng path con.
- [x] Gói publish không có `.git`/tools/node_modules/raw test output; chỉ giữ ảnh và số đo minh họa được chọn cho tài liệu; các liên kết gallery/analysis/credits vẫn hợp lệ, thư mục riêng tư bị chặn truy cập.
- [x] Hướng dẫn rõ mua thiếu vàng, 3 ô lắp, lưu trận bằng menu gốc, nhập save v1 → ver2, giới hạn chiều ngược và rollback riêng ver2.

**Xác minh:** serve gói staging ở path con `/ver2/` và một tên path khác; crawler link + UI smoke; HTTP 200 cho file cần dùng, 403/404 cho dữ liệu phát triển; đối chiếu hash source cha với baseline.
**Phụ thuộc:** T20. **Tệp:** `scripts/package-ver2.mjs`, `.htaccess`, `README-VER2.md`, `THIRD_PARTY.md`, `analysis/VER2-RELEASE.md`.

### Checkpoint hoàn thành

- [x] Đủ các tiêu chí SPEC đã cập nhật và ba yêu cầu mới; không task nào được coi xong chỉ vì đã tạo file.
- [x] Có artifact chạy được tại URL ver2, báo cáo kiểm thử và snapshot để rollback.
- [x] Mã của ứng dụng gốc ở thư mục cha vẫn giữ nguyên; chỉ ver2 được nâng cấp.

## Bổ sung 23/10/2026 — T22: chơi tiếp từ URL ver1 sang ver2

- [x] Tự sao chép checkpoint v1 trước khi mở VM khi ver2 chưa có bản lưu; giữ hai namespace riêng.
- [x] Không tự ghi đè dữ liệu ver2; nút Lấy bản lưu ver1 có xác nhận thay thế trước khi vào game.
- [x] Kiểm cấu trúc RMS, chép nguyên byte bằng một transaction; nguồn v1 chỉ đọc, hai lần mở đồng thời không nhập đôi.
- [x] Kiểm thực tế Continue v1 → ver2, mua/lắp, tải lại vẫn giữ và v1 không đổi; kiểm lỗi/hủy/abort và ảnh 375/768/1440.
- [x] Chốt hồi quy: 91 unit đạt; lượt tổng 59/60 browser đạt, sửa lỗi chờ pause trong test rồi kiểm lại 13/13 migration/isolation/release đạt; cả 60 ca đều có kết quả đạt trên mã ứng dụng cuối. Gói phát hành và báo cáo được cập nhật, ghi rõ lịch sử kiểm thử.
