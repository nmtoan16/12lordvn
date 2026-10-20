# Runtime cục bộ và các thay đổi

Nguồn: Rosabis/J2meJS commit `75947c10412c7ff9927835a128b653696f402e9d`, thư mục `java/`. Giữ nguyên `bld/j2me.js`, `bld/native.js`, các class MIDP và thư viện upstream; không sửa JAR game.

`bld/main-all.js` chỉ thay:

1. Dùng `java/classes.jar` hiện hành thay `classesold.jar`.
2. Khởi động đúng một application isolate.
3. Resolve dữ liệu điện thoại bằng `{icc:{},network:{}}` local thay Promise DumbPipe không có host trả lời. Nguyên nhân và bằng chứng ở `analysis/RUNTIME-STARTUP.md`.
4. Namespace IndexedDB `loan12suquan-fs-v1` và `loan12suquan-jars-v1`.
5. Sửa ba vòng lặp flush file từ `.length` trên Map sang `Map.forEach`, để xuất cả dirty handles và đồng bộ các handle mở cùng file.
6. Khi import snapshot, xóa handle của VM cũ và hàng đợi ghi trước khi thay dữ liệu, ngăn flush định kỳ/pagehide ghi đè bản lưu vừa nhập.

`libs/compiled-method-cache.js` dùng database riêng `loan12suquan-method-cache-v1` và key localStorage `loan12suquan:lastAppVersion`. Clear/restore chỉ tạo transaction sau khi Promise mở IndexedDB hoàn tất, sửa race khi khởi động lại.

`main.html` bỏ keymap mẫu, thêm các adapter cục bộ, CSS màn hình cố định và CSP chỉ kết nối cùng origin. Iframe ở trang chính giữ sandbox, không cấp form navigation, popup hay top navigation.

Các tệp mới:

- `host.js`: giữ danh sách AudioContext cho thao tác người dùng bật/tắt âm.
- `compat.js`: native SMS listener fallback, PushRegistry rỗng; TextBox đặt tên bằng form HTML nằm trong màn hình, xác thực 2–10 ký tự; form tặng game cũ báo không khả dụng rồi quay về. Các native dialog khác tiếp tục qua LCDUI gốc.
- `bridge.js`: bàn phím/cảm ứng, chỉ nhận message từ parent cùng origin; thả phím khi mất focus; pause tại ranh giới thực thi Java, bù thời gian pause trong clock; lưu/xuất/nhập virtual filesystem và thông báo thoát. Sau khi import hoàn tất, parent điều hướng iframe sang VM mới; không để exit từ VM cũ đóng màn hình khôi phục.
- `offline-shop.js`: mô phỏng phản hồi SMS đã ngừng hoạt động; giữ callback cấp đồ/mạng Java gốc. Chỉ hook đúng `TextObject.setPayloadText` ở lần gọi đầu, không sửa interpreter hay gameplay. Quy tắc collectible offline được ghi rõ tại `analysis/OFFLINE_TRANSACTIONS.md`.
- `web.css`: đưa màn hình gốc về 240×320 và định dạng input cục bộ.

Để kiểm tra bản vá so với upstream cục bộ: `git diff --no-index tools/J2meJS/java/bld/main-all.js runtime/bld/main-all.js`. Bản nguồn upstream đầy đủ được giữ trong `tools/J2meJS/`, không cần cài hoặc tải khi chơi game. Giấy phép: `LICENSE` trong thư mục runtime.
