# Rà soát adapter web và kiểm chứng

Phạm vi: `web/app.js`, `runtime/compat.js`, `runtime/bridge.js` và đường ghi/xuất filesystem của runtime. Đây không phải kiểm toán toàn bộ mã nguồn máy ảo Java ME bên thứ ba.

## Các lỗi đã sửa

1. **Tạo nhân vật không mở được form:** adapter đọc `curDisplayableId` từ global, nhưng biến đó chỉ có trong closure LCDUI upstream. Adapter hiện nhận diện đúng TextBox thông qua command type và label: Xong/OK4 + Trở về/BACK2. ID command được giữ nguyên khi phát `MIDP.sendCommandEvent`.
2. **Form có input nhưng nút bị ẩn:** upstream `style/main.css` áp `button {display:none}`. Quy tắc có scope trong `runtime/web.css` nay hiện nút riêng của form.
3. **Bấm Xong không có tác dụng:** iframe cố ý không cấp `allow-forms`. Native HTML submit bị sandbox chặn trước handler. Nút nay dùng `type=button`, xử lý click/Enter trực tiếp, gọi validation của input; sandbox không phải mở thêm quyền.
4. **Tạm dừng vẫn có thể chạy một callback đã lên lịch:** bọc `Scheduler.processRunningQueue` không chặn closure `run` đã được lên lịch trước đó. Adapter nay hoãn tại `Context.execute`, gom context trong Set và enqueue lại đúng một lần khi tiếp tục. Scheduler gốc vẫn được drain nên không mắc cờ `processQueueScheduled=true`.
5. **Flush bỏ sót file đang mở:** ba đoạn code xem `openedFiles` (Map) như mảng `.length`. Đã đổi thành Map.forEach trong flush/shared handle, flushAll, flushAllRms. Byte mới trong handle chưa close nay được xuất đúng và các handle cùng file được đồng bộ.

TextBox tặng game được xử lý riêng bằng Gửi/OK4 + Đóng/EXIT7; dùng input tel tối đa20 ký tự và trở về màn gốc kèm thông báo dịch vụ offline. Không phát command gửi SMS. Native alert khác tiếp tục dùng renderer cũ. Không chỉnh `DynamicCharacterArray` hay cơ chế đọc chuỗi Java khi chưa có bằng chứng lỗi.

## Kết quả kiểm chứng

- `node --test tests/runtime-bridge.test.cjs`: **3/3 pass**. Bao gồm callback đã lên lịch trước pause, deduplicate khi resume, đồng hồ Java loại trừ thời gian pause, nhả phím đang giữ và từ chối origin khác.
- `npx playwright test tests/native-form.spec.cjs --output=test-results-review-forms`: **7/7 pass**. Dùng iframe sandbox thật, cùng upstream main.css và web.css. Kiểm native command IDs, trim tên, cancel/Escape, phone offline, fallback alert và layout375/768/1440px không wrap control hoặc tràn viewport240px.
- `node tools/review-character.cjs`: **pass** toàn luồng original Java menu → intro → avatar → tên TOAN → bản đồ Hoa Lư. Một isolate, không pageerror. Bằng chứng: `verification/character-review.json`, `verification/review-character-map.png`.
- `node tools/review-storage.cjs`: **pass**. Tạo file và hai handle, ghi byte có dấu `[0,-128,-1,42,127]` vào handle chưa đóng, gọi flush, xác nhận handle thứ hai và JSON export giống từng byte. Bằng chứng: `verification/storage-review.json`.
- Đã thực hiện mutation trong bản code nạp riêng của test, không đổi source đang chạy: tắt pause guard làm test pause thất bại (Java chạy sau pause); bỏ flushAll làm test storage thất bại (vẫn nhận byte cũ `[1,2,3,4,5]`). Hai test có khả năng bắt chính regression vừa sửa.

## Giới hạn được giữ rõ

Nút pause trên web dừng thực thi và đồng hồ; flush/xuất chỉ ghi filesystem hiện có. Snapshot live battle vẫn được tạo bằng cơ chế Tạm dừng gốc trong game. Không tự gọi `bu.u()` từ sự kiện DOM vì phương thức đó còn điều chỉnh mạng chơi khi thoát/resume trận. README và hướng dẫn giao diện hiện nói rõ phải lưu trận theo menu gốc trước khi đóng hoặc chuyển tiến trình.

Không phát hiện thêm lỗi chặn trong các adapter đã rà soát sau các kiểm tra trên. Việc vượt hết chiến dịch, mọi combat spell/AI và tất cả giao dịch offline được kiểm chứng theo các kiểm tra riêng; kết quả tạo nhân vật không được coi là đã chơi hết game.

## Bổ sung: import và khởi động lại VM

- Sửa race trong `compiled-method-cache.js`: `openDatabase.then(new Promise(...))` chạy executor ngay khi IndexedDB chưa mở. Đã đổi thành callback tạo Promise sau khi open hoàn tất, cho cả clear/restore; namespace localStorage thành `loan12suquan:lastAppVersion`.
- `Store.import` bỏ các handle cũ và hàng đợi changesToSync trước khi thay snapshot. Nếu giữ lại, periodic/pagehide flush của VM bị thay có thể ghi buffer cũ đè dữ liệu vừa nhập.
- `node --test tests/compiled-cache.test.cjs`:2/2 pass với IndexedDB mock cố tình trì hoãn open. Đổi lại hành vi cũ làm test fail đúng `Cannot read properties of undefined (reading 'transaction')`.
- `node tools/review-import-storage.cjs`: pass. Giữ một handle dirty `[9,9,9]`, nhập `[77,-4,0,127]`, thực hiện old-frame flush rồi reload cùng browser context; byte nhập vẫn nguyên, không pageerror. Mutation bỏ invalidation làm fail vì dữ liệu lại thành `[9,9,9]`. Bằng chứng `verification/import-storage-review.json`.
- Parent web sở hữu việc điều hướng sang VM mới sau khi transaction import hoàn tất; việc kiểm chứng toàn luồng nút Import → boot → menu/name trong dữ liệu RMS được thực hiện trong E2E riêng.
