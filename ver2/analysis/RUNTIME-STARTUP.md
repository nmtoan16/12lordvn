# Sửa lỗi khởi động Java ME trong trình duyệt

## Lỗi tái hiện

Với một lệnh `jvm.startIsolate0(config.main, config.args)`, canvas trắng, không phát sinh pageerror; snapshot máy ảo cho thấy một application runtime, một thread, stack dừng tại:

`Sys.isolateEntryPoint → MIDletSuiteLoader.main → CldcMIDletSuiteLoader.runMIDletSuite → MIDletSuiteLoader.init → CldcMIDletSuiteLoader.init → MIDletSuiteUtils.vmBeginStartUp (native)`.

Trên bản upstream gọi `startIsolate0` hai lần, lần thứ hai vào được game nhưng để runtime đầu bị treo. `Sys.isolate0Entry` chỉ dựng một `Isolate` rồi start; lần gọi đầu không có nhiệm vụ warm-up đặc biệt. Bằng chứng decompile tại `runtime-decompiled/org/mozilla/internal/Sys.java:60`.

## Nguyên nhân

`runtime/bld/main-all.js` tạo `getMobileInfo` bằng DumbPipe rồi cho Promise này vào `loadingMIDletPromises`. Bản web upstream đã vô hiệu host transport: `DumbPipe.runSendQueue` chỉ lấy bỏ message mà không chuyển đến bất kỳ bộ xử lý nào. Vì thế không có reply `mobileInfo` và Promise không bao giờ resolve.

Native `vmBeginStartUp` đặt `loadingMIDletPromisesResolved = true` ngay trước `asyncImpl(Promise.all(...))`. Do cờ được đặt trước khi Promise hoàn tất, lần khởi động thứ hai bỏ qua chờ nên game chạy. Đây là hành vi che khuất deadlock, không phải quy trình hợp lệ cần hai isolate.

## Thay đổi

- Cấp `mobileInfo = {icc:{},network:{}}` local và Promise đã resolve cho browser không có telephone host. Cả hai object được giữ để các property lookup sau đó an toàn.
- Giữ đúng một `startIsolate0`; không cần sửa JAR game.
- Namespace IndexedDB thành `loan12suquan-fs-v1` và `loan12suquan-jars-v1`, tránh dùng chung `asyncStorage`/`JARStore` với ứng dụng khác trên localhost.

## Kiểm chứng

- `node tools/debug-startup.cjs`: sau5,5s một application runtime có6 threads, game đang chạy logo; không còn dừng một thread ở `vmBeginStartUp`.
- `node tools/probe.cjs`: sau16s canvas240×320 hiển thị màn chọn âm thanh gốc. Screenshot `analysis/runtime-probe.png`.
- Không dùng gọi isolate lần thứ hai trong các lần kiểm tra này.

Console runtime vẫn ghi một số ngoại lệ Java đã được game bắt, đặc biệt dò platform Sony trả null, và `RecordStoreNotFoundException` khi tạo save lần đầu. Máy ảo upstream log cả ngoại lệ đã bắt bằng `console.error`; chúng không tự chứng minh game crash. Những log này được giữ để phân biệt khỏi pageerror thật; phạm vi sửa ở đây là startup deadlock, không viết lại toàn bộ cơ chế ngoại lệ của VM.

`tools/debug-startup.cjs --second` là chế độ điều tra lịch sử để so sánh; không dùng nó làm bước khởi động ứng dụng hay kiểm chứng thành công.
