# Runtime cục bộ và các thay đổi

Nguồn: Rosabis/J2meJS commit `75947c10412c7ff9927835a128b653696f402e9d`, thư mục `java/`. Lịch sử bản web ban đầu bên dưới; phần ver2 ghi riêng ở cuối. Không sửa JAR game, các class MIDP hoặc tài nguyên gốc.

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
- `offline-shop.js`: mô phỏng phản hồi SMS đã ngừng hoạt động; giữ callback cấp đồ/mạng Java gốc. Hook `TextObject.setPayloadText` qua registry chung của ver2. Quy tắc collectible offline được ghi rõ tại `analysis/OFFLINE_TRANSACTIONS.md`.
- `web.css`: đưa màn hình gốc về 240×320 và định dạng input cục bộ.

Upstream để đối chiếu nằm ở `../tools/J2meJS/` trong workspace phát triển; gói publish không chứa công cụ đó. Giấy phép: `LICENSE` trong thư mục runtime.

## Ver2 — 22/10/2026

- `ver2-config.js` tách bốn namespace filesystem/JAR/method cache/version khỏi v1; mọi URL tương đối để chạy trong thư mục con.
- `bld/j2me.js` gọi registry tại đầu frame và return; method có observer giữ interpreter để continuation không làm lặp callback. Replacement đồng bộ gắn khi resolve lần đầu; method khác vẫn dùng JIT.
- `java-hooks.js` và `java-port.js` tra metadata, chạy Context Java riêng cùng Runtime và chờ hoàn tất I/O. Port đọc lại physical RMS đã commit để phát hiện lỗi bị lớp Java nuốt.
- `bld/main-all.js`: Store.sync luôn chờ transaction, truyền lỗi/abort, giữ pending writes; Store.export truyền lỗi. Khi port flush hỏng, khóa ghi và yêu cầu reload để journal có thể phục hồi.
- `clean-startup.js`: loại phần paint/timer quảng cáo `cn`, giữ readiness/init gốc. `compat.js` thêm hai guard startup nêu tại `analysis/VER2-HOOKS.md`.
- `equipment/`: catalog, rules, combat, skills, loadout, store/journal/CRC, sidecar/fingerprint và validator file nhập; `equipment-runtime.js`/`equipment-combat-bridge.js` nối vào game thật.
- `equipment-shop.js`, `equipment.css`, `equipment/{info,hud,vfx}.js`: UI 240×320 dùng sprite gốc, ba ô trang bị, mô tả từ rules, barrier và VFX hữu hạn.
- `bridge.js`: các Context lưu dữ liệu vẫn chạy khi pause; input gameplay khóa khi chưa sẵn sàng hoặc lỗi lưu; export chờ bền và import xác thực trước thay dữ liệu.

JAR và 151 tài nguyên gốc được kiểm SHA-256; bản nguồn cha không thay đổi. Xem báo cáo kiểm thử và giới hạn tại `analysis/VER2-VERIFICATION.md`.
