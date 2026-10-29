# Báo cáo kiểm thử ver2

## Cập nhật chuyển tiếp ver1 — 23/10/2026

Yêu cầu mới thay chính sách xuất/nhập thủ công trước đây: VÀO GAME tự sao chép checkpoint ver1 khi ver2 chưa có bản lưu. Nếu ver2 đã có dữ liệu (kể cả đã mở menu), ưu tiên dữ liệu đó; nút **Lấy bản lưu ver1** trước khi vào game cho phép xác nhận thay thế. Bản lưu ver1 không bị ghi. Việc chuyển lấy checkpoint đã commit trên cùng trình duyệt/origin, không lấy trạng thái chưa lưu và không đồng bộ hai chiều.

`tests/v1-migration.spec.cjs` có 9 ca mới: Continue thật ở v1 → Continue ở ver2 → mua/lắp → reload còn trang bị; đối chiếu snapshot bản đồ/tên/EXP/avatar và byte nguồn; giữ ver2/hủy/thay; hai tab mở đồng thời; chưa có nguồn; nguồn hỏng; ghi bị abort và thử lại; bố cục 375/768/1440. Unit test mới kiểm allocator 16-byte bằng tên TOAN, EXP 10 và snapshot bản đồ 262 byte trong fixture gốc.

Ảnh: [375px](verification/ver2/v1-migration-375.png), [768px](verification/ver2/v1-migration-768.png), [1440px](verification/ver2/v1-migration-1440.png), [đã chơi tiếp bản đồ ở ver2](verification/ver2/v1-migration-continued.png). Ảnh cuối chụp khi pause để cố định khung hình; lớp pause vẫn hiển thị. Test dùng browser context riêng và bản lưu nhân vật tự động TOAN, không thay đổi profile trình duyệt cá nhân.

Kết quả: `npm run check` đạt; **91/91 unit test đạt**. Lượt toàn bộ 60 browser test có **59 đạt, 1 lỗi harness** tại phép chờ pause trong ca Continue xuyên phiên bản. Sau khi sửa riêng bài test, chạy lại toàn bộ nhóm migration/isolation/release: **13/13 đạt**, 0 skipped, 0 flaky. Như vậy cả 51 ca cũ và 9 ca mới đều có kết quả đạt trên mã ứng dụng cuối; không mô tả lượt tổng đầu là 60/60. Mã ứng dụng không thay đổi giữa hai lượt này.

Hai lỗi harness đã xử lý: lượt focused đầu dùng biến `loanPaused` chỉ có ở ver2 để kiểm v1 (đổi sang trạng thái UI v1); lượt tổng đọc tên biến trần trước khi `postMessage` pause được nhận ở ver2 (đổi thành `window.loanPaused`, cho phép poll chờ từ undefined đến true). Không tăng timeout, bật retry hay bỏ assertion.

Log hồi quy: `verification/ver2/migration-full-test.log` và `migration-full-results.json`; lượt kiểm lại cuối: `verification/ver2/migration-focused-test.log` và `migration-focused-results.json`. Ảnh mới đã được mở xem trực quan. Kiểm hash 784 file nguồn cha và SPEC vẫn đạt.

---

Ngày 22/10/2026. Phạm vi: bản sao độc lập tại `D:/xampp/htdocs/12suquan/ver2`; chỉ bản này được nâng cấp. Kế hoạch và tiêu chí đối chiếu: `tasks/plan.md`, `tasks/todo.md`. Đồ họa, âm thanh, bản đồ và bytecode JAR gốc được giữ nguyên; gameplay mở rộng qua adapter Java/JavaScript có kiểm chứng.

## Kết quả nghiệm thu

Lượt tổng cuối ngày 22/10/2026: **90/90 unit test đạt** (2,39 giây), **51/51 browser test đạt** (11 phút 25 giây), 0 fail, 0 skipped, 0 flaky; tiến trình thoát mã 0. `npm run check` đạt. Đã mở xem ảnh chụp để kiểm trực quan và xác nhận Git nguồn cha không có thay đổi tracked. Checklist T00–T21 hoàn tất trong phạm vi kiểm chứng nêu dưới đây; các giới hạn hiệu năng, fixture và ranh giới reset được ghi rõ cuối báo cáo.

Chạy tại `http://localhost/12suquan/ver2/`. Gói phát hành được tạo bằng `npm run package:ver2`, xác định qua `dist/latest.json` trong source ver2, kèm manifest SHA-256. Không cần Node trên hosting.

## Chức năng đã triển khai

- Bỏ màn quảng cáo phân phối lúc khởi động, giữ khởi tạo MIDlet, bìa và nội dung game.
- Sáu nhóm trang bị, 18 bậc nâng cấp, mua khi thiếu vàng: trừ tối đa số vàng đang có, phần thiếu miễn, không phát sinh nợ.
- Sở hữu cả sáu nhưng chỉ mang tối đa ba món. Mua không tự lắp; món thứ tư yêu cầu chọn thay hoặc hủy. Trước trận xem được tên, HP/Công đối thủ; bộ trang bị/bậc được chốt theo trận.
- Đủ các cơ chế Thần Binh, Linh Châu, Bảo Giáp, Pháp Ấn, Bí Kíp và Thần Thạch; chín kỹ năng Hỏa/Lôi/Băng cường hóa. AI giữ luật gốc; chỉ món đang mang có hiệu lực.
- UI thông tin/kỹ năng, thanh barrier, số sát thương/hồi/chặn, hiệu ứng thức tỉnh và Hoàng Kim. Dùng lại sprite gốc trong bố cục 240×320.
- Hồ sơ, journal giao dịch, sidecar trận và nhập/xuất save; namespace độc lập với v1. Lỗi ghi bền tạm dừng game và yêu cầu tải lại để phục hồi.

## Các lớp kiểm chứng

| Nhóm | Bằng chứng thực thi |
|---|---|
| Nguồn/đồ họa | `artifacts.test.cjs`, `source-isolation.test.cjs`: SHA JAR, 151 tài nguyên gốc, 131 PNG khôi phục; 784 file nguồn cha và SPEC giữ byte nguyên |
| Boot/JVM | `java-hooks.test.cjs`, `java-port.spec.cjs`, `startup*.spec.cjs`: continuation, khóa thử124, 20 lượt cold/warm và 20 context lạnh độc lập thường/CPU4×, JAR nền chậm1,5giây, chuyển màn đúng một lần |
| Mua/lắp | `equipment-store.test.cjs`, `loadout.test.cjs`, `purchase.spec.cjs`, `equipment-shop.spec.cjs`, `loadout.spec.cjs`: 18 bậc/MAX, 42 tập hợp 0–3 món, hủy/thay, keyboard/touch, reload, chọn trước trận |
| Vàng vật phẩm gốc | `ver2-integration.spec.cjs`: 3 vật phẩm × 0/thiếu/đủ vàng, hủy rồi xác nhận bằng handler Java gốc; kiểm cache, dữ liệu đã commit và reload |
| Giao dịch lỗi | `fs-durability.test.cjs`, `equipment-store.test.cjs`, `rms-durability.spec.cjs`: codec/journal, sáu lỗi ghi thật ở ba mốc, reload/replay không thu hoặc cấp đôi |
| Khóa khi lỗi lưu | `storage-fault-ui.spec.cjs`: lỗi trong purchase API thật khóa điều khiển, tạm dừng, cung cấp nút tải lại, VM mới phục hồi |
| Combat | `equipment-rules/skills/combat.test.cjs`, `combat-parity.spec.cjs`: số nguyên/nguồn/side, damage/armor/barrier/reflect/heal/mana/lương/drop, retry, Nhân Sâm/Thuốc |
| Kỹ năng | `skills.spec.cjs`: 45 cast Java (9×bậc0/1/2/3 +9 AI), 3 thiếu mana, 8 tổ hợp chiêu thủ/hồi có/không mana; kiểm HP, mana, ô phá, I/J/G, barrier và proc đốt |
| Save/import | `battle-save.test.cjs`, `equipment-import.test.cjs`, `equipment-resume.spec.cjs`, `ver2-integration.spec.cjs`: legacy, CRC/schema lỗi, fingerprint, frozen tiers, export/import thật và Chơi mới Có/Không |
| Cô lập v1 | `version-isolation.spec.cjs`: hai tab v1/ver2 sống trên cùng origin, mua/reset/import ver2 không đổi RMS v1 |
| UI/HUD | Các spec `equipment-info/hud/ui-fixture/vfx`, `game`, `native-form`: 375/768/1440, nút không ngắt dòng, không tràn ngang, pause, pool hữu hạn |
| Phát hành | `release.spec.cjs`: gói độc lập tại `/ver2/` và `/alternative/`, mua/lưu thật, đường dẫn tương đối, tài nguyên/credits200, file phát triển403/404, không request ngoài origin |
| Mutation | `mutation.test.cjs`: bản sao mã trong RAM cố tình cho lắp4, bỏ chống hit lặp, dùng DBv1 và lặp observer continuation đều làm oracle thất bại; không sửa mã đang chạy |

Kết quả chạy tổng cuối được ghi tại `verification/ver2/final-test.log` và `final-results.json`; các báo cáo test không dùng browser profile cá nhân. `npm run check` kiểm cú pháp ứng dụng và toàn bộ script runtime cục bộ. Lệnh tái lập: `npm ci`, `npm run check`, `npm test`.

Rà soát cuối đã tìm và sửa hai lỗi có regression: frame hook cũ sau Java exception, và Continue cùng VM dùng sidecar cache chưa cập nhật. Test same-VM đi qua lệnh Java Lưu rồi thoát và phím Continue thật, giữ battle ID, bậc, barrier và trạng thái chiến đấu; không phải chỉ reload để vô tình làm mới cache.

Lượt tổng đầu còn phát hiện lỗi trong chính harness: helper gọi `CLASSES.getClass('cz')` trước builtin JAR sẵn sàng có thể làm hỏng class cache, test ảnh chụp trước khi pause qua `postMessage` được nhận, và mock native form thiếu trường platform mới. Đã sửa observer thành chỉ đọc sau readiness, chờ trạng thái pause thật, bổ sung mock đúng contract; thêm 20 browser context lạnh độc lập và regression cố tình trì hoãn builtin JAR 1,5 giây. Lượt focused tiếp theo phát hiện phép đo bố cục ngay sau resize chạy trước ResizeObserver; test đã chờ đúng điều kiện hình học cũ trước khi chụp ảnh. Không dùng tăng timeout hoặc tự retry để che các lỗi này.

## Kiểm trực quan

Đã chụp và mở xem ảnh thật: shop ở 375/768/1440, chọn ba món trước trận, bảng thông tin/kỹ năng, barrier đầu trận/hấp thụ/vỡ, hồi máu, VFX và trận được nhập lại. Ảnh tại `verification/ver2/`; mỗi ảnh được tạo bởi test có tên tương ứng. Khi chụp HUD, test pause VM và ẩn riêng lớp che pause của vỏ web để thấy canvas game bên dưới.

- [Shop 375px](verification/ver2/equipment-shop-375.png)
- [Shop 1440px](verification/ver2/equipment-shop-1440.png)
- [Chọn trang bị trước trận](verification/ver2/loadout-prematch.png)
- [Khiên vỡ và số thực mất](verification/ver2/hud-barrier-break.png)
- [Thông tin kỹ năng](verification/ver2/equipment-info-skills.png)
- [Trận phục hồi](verification/ver2/resumed-battle.png)

## Giới hạn và sai khác đã ghi nhận

1. Đã đi qua luồng tạo nhân vật, bản đồ và trận hướng dẫn bằng UI thật. Smoke chiến dịch/nhiệm vụ phụ/đấu trường × thắng/thua sử dụng fixture đi qua `bu.y → bu.t → dd.i/j`; đây không phải chơi hết 46 màn hoặc đo tỷ lệ thắng toàn chiến dịch.
2. Fixture kỹ năng dừng FSM ngay sau cast để tách damage trực tiếp khỏi cascade ngẫu nhiên; phép phá ô được kiểm bằng dữ liệu y/z/board. Cân bằng ba build được đo với đầu vào công khai trong [VER2-BALANCE.md](VER2-BALANCE.md).
3. Refresh canvas Java p95 khoảng 52 ms trong phép đo cùng điều kiện, cả có/không Hoàng Kim; chưa đạt mục tiêu dự kiến 40 ms. CPU riêng VFX p95 khoảng 0,1 ms ở lần đo tham chiếu, pool12 chữ/24 hạt. Không cam kết 25FPS hay chứng minh không rò rỉ dài hạn bằng mẫu ngắn. Chi tiết [VER2-PERFORMANCE.md](VER2-PERFORMANCE.md).
4. Save hỏng/phiên bản chưa hỗ trợ bị từ chối, giữ dữ liệu để chẩn đoán; không tự ghi đè để “sửa”. Journal phục hồi giao dịch hợp lệ, không phục hồi tùy ý mọi payload RMS bị hỏng. Reset tiến trình Java gốc và reset extension là hai thao tác; tắt cưỡng bức đúng giữa hai thao tác là ranh giới chưa có giao dịch nguyên tử chung. Chi tiết [VER2-DATA.md](VER2-DATA.md).
5. Kiểm thử tự động dùng Chrome trên Windows/XAMPP và HTTP staging cục bộ; chưa kiểm trên mọi trình duyệt/điện thoại vật lý. Website chạy local đã sẵn sàng; chưa upload lên máy chủ public vì chưa có đích triển khai được cung cấp.

Không nâng thư viện runtime, đổi JAR, sửa nguồn cha hoặc chỉnh ngầm các chỉ số cân bằng. Các tên file test dự kiến trong kế hoạch được gom theo module để chia sẻ fixture; bảng trên là ánh xạ kiểm thực tế.
