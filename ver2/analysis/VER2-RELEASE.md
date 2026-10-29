# Phát hành ver2

Địa chỉ XAMPP hiện tại: `http://localhost/12suquan/ver2/`. Thư mục nguồn độc lập: `D:/xampp/htdocs/12suquan/ver2`.

## Tạo và triển khai gói

1. Chạy `npm run check` và `npm test` trong ver2.
2. Chạy `npm run package:ver2`. Gói mới nằm trong `dist/<thời điểm>/ver2/`; `dist/latest.json` chỉ tới gói gần nhất. `release-manifest.json` liệt kê SHA-256 từng file.
3. Upload **thư mục ver2 bên trong gói** cạnh website hiện tại. Nếu URL hiện tại là `https://tenmien/12suquan/`, truy cập bản này tại `https://tenmien/12suquan/ver2/`. Không upload node_modules, repository Git, công cụ hoặc test output.
4. Giữ `.htaccess` khi dùng Apache. Gói được kiểm ở hai path `/ver2/` và `/alternative/`, không cần sửa URL cứng trong code. Với server khác, phục vụ `index.html` và cấm liệt kê thư mục/file ẩn tương đương.
5. Mở game, thử mua một món/lắp, tải bản lưu, reload và kiểm trạng thái. Gói cần HTTP(S); không chạy bằng double-click `file://`.

JavaScript/ảnh/JAR/runtime đều được phục vụ cục bộ; không cần Node trên hosting. Gói không có `.git`, `tools`, `node_modules`, tests/scripts/tasks hay SPEC. Credits, gallery, manifest tài nguyên, giấy phép runtime và tài liệu hướng dẫn được giữ.

## Dữ liệu và rollback

V1 và ver2 dùng namespace IndexedDB khác nhau trên cùng origin. URL con khác tên vẫn dùng dữ liệu ver2 theo origin; không coi việc đổi tên thư mục là tạo thêm một hồ sơ người chơi. Từ 23/10/2026, VÀO GAME tự đọc và sao chép checkpoint v1 khi chưa có bản lưu ver2. Sao chép xong mới khởi động VM; không ghi vào v1 và không đồng bộ hai chiều.

Nếu ver2 đã có dữ liệu, dùng **Lấy bản lưu ver1** trước khi vào game và xác nhận thay thế; nên xuất save ver2 trước nếu cần giữ. Nút này chỉ đọc được v1 trên cùng trình duyệt/origin. Khác trình duyệt/thiết bị/origin thì dùng xuất/nhập tệp. Save v1 chưa có trang bị bắt đầu sáu bậc0/không lắp. Trận legacy đang lưu giữ luật cũ đến trận mới. Không nhập save ver2 ngược về v1.

Rollback website bằng cách thay riêng thư mục đã publish bằng gói ver2 đã giữ trước đó hoặc tạm bỏ đường dẫn ver2; không đụng source/DBv1. Xuất save trước khi đổi phiên bản; bản cũ có thể không đọc schema mới và phải từ chối rõ thay vì ghi đè. Git riêng của ver2 giữ baseline và các commit triển khai để đối chiếu.

Ranh giới và kết quả kiểm thử: [VER2-VERIFICATION.md](VER2-VERIFICATION.md). Hướng dẫn người chơi: [README-VER2.md](../README-VER2.md).
