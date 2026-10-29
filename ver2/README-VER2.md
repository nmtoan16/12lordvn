# Loạn 12 Sứ Quân — ver2

Chạy tại **http://localhost/12suquan/ver2/** trên XAMPP hiện tại. Source cha được giữ nguyên; ver2 có Git, runtime, tài nguyên và vùng lưu riêng.

## Trang bị và mua hàng

- Cửa hàng có `[1] Vật Phẩm` và `[3] Trang Bị`; `*`/`#` đổi tab khi đang ở cửa hàng.
- Sáu nhóm trang bị, mỗi nhóm ba bậc. Có thể sở hữu cả sáu nhưng chỉ **lắp tối đa ba món**. Mua không tự lắp; muốn thêm món thứ tư phải chọn món thay hoặc hủy.
- Có thể mua/nâng cấp khi thiếu vàng. Thực trừ bằng số nhỏ hơn giữa số vàng hiện có và giá niêm yết; phần thiếu được miễn, không ghi nợ. Quy tắc này cũng áp dụng vật phẩm tiêu hao có giá vàng. Bậc III hiển thị MAX.
- Chọn trang bị trong cửa hàng, bảng Thông Tin hoặc màn chuẩn bị trước trận. ID và bậc trang bị được chốt khi vào trận; thay đổi trong kho chỉ ảnh hưởng trận sau. Vật phẩm tiêu hao không chiếm ô trang bị.
- Bí Kíp cường hóa lần lượt chiêu 1, chiêu 1–2 rồi cả ba chiêu. Bảng Chiêu thức thể hiện chi phí 12/24/36 mana và hiệu ứng theo trang bị đang có hiệu lực.
- Thanh chắn và số nổi thể hiện sát thương/hồi phục/hấp thụ thực tế. Màn quảng cáo phân phối ở lúc khởi động đã được bỏ; bìa, cốt truyện và tài nguyên game được giữ.

## Lưu và chuyển tiến trình

Dùng chức năng lưu trận/menu của game trước, rồi mở **Lưu và chuyển tiến trình → Tải bản lưu** ở giao diện web. Xuất tệp không tạo thay cho checkpoint của game; phần hướng dẫn ban đầu phải hoàn tất hoặc dùng lựa chọn bỏ hướng dẫn của game để có checkpoint bản đồ.

Từ 23/10/2026, khi ver2 chưa có bản lưu riêng, bấm **VÀO GAME** sẽ tự sao chép checkpoint ver1 trên **cùng trình duyệt và cùng origin** (giao thức, tên miền, cổng), rồi chọn **Chơi tiếp** trong game. Tên nhân vật, bản đồ, EXP, vàng, vật phẩm và checkpoint trận được giữ. Các lần sau ver2 tiếp tục từ bản lưu ver2; không đồng bộ ngược vào ver1.

Nếu đã mở/chơi ver2 trước đó, bản lưu hiện có được ưu tiên. Để chuyển sang tiến trình ver1, trước khi vào game bấm **Lấy bản lưu ver1** và xác nhận thay thế. Nên tải bản lưu ver2 trước nếu muốn giữ cả hai. Nút này tạm khóa khi game đang chạy; có thể tải lại trang để dùng. Ver1 luôn được giữ nguyên. Chuyển trình duyệt, thiết bị hoặc tên miền thì dùng **Tải bản lưu / Nhập bản lưu** như trước.

Bản lưu v1 được nhận với kho trang bị rỗng; trận cũ thiếu sidecar tiếp tục theo luật gốc đến trận mới. Bản lưu ver2 giữ kho, ba món đang lắp, vàng và trạng thái trang bị của checkpoint trận. Chuyển tiếp lấy checkpoint đã ghi, không lấy trạng thái đang chơi chưa lưu của tab ver1.

Nhập tệp hỏng hoặc schema không hỗ trợ sẽ báo lỗi trước khi thay dữ liệu hiện có. Không nhập ngược bản lưu ver2 vào v1 để tiếp tục các hiệu ứng trang bị: v1 không hiểu chúng. Muốn quay về v1, mở URL v1 và dùng bản lưu v1 đã giữ.

Xác nhận **Chơi mới** xóa tiến trình nhân vật và trang bị của ver2. Hủy xác nhận, mở menu hoặc tải lại trang không xóa trang bị. Xóa dữ liệu website trong trình duyệt sẽ mất tiến trình cục bộ nếu chưa xuất tệp.

## Đưa lên website

Chạy `npm ci`, `npm run check`, `npm test`, rồi `npm run package:ver2` trong thư mục ver2. Script tạo thư mục mới ở `dist/<thời điểm>/ver2/`; đường dẫn gần nhất nằm trong `dist/latest.json`. Chỉ upload thư mục phát hành đó vào cạnh bản đang chạy. Ví dụ URL cũ `https://example.com/12suquan/` sẽ có bản mới `https://example.com/12suquan/ver2/`.

Đường dẫn tài nguyên là tương đối, nên có thể đổi tên thư mục phát hành. Không cần sửa router hoặc database của bản cũ. Gói phát hành không chứa Git, node_modules, tools, scripts, tests hay tài liệu công việc riêng; `.htaccess` chặn liệt kê thư mục và đường dẫn phát triển. Nếu dùng Nginx, áp dụng quy tắc tương đương cho dotfiles và tắt directory listing.

Muốn thu hồi bản mới, ngừng phục vụ hoặc chuyển thư mục ver2 ra khỏi webroot; không xóa dữ liệu hay source bản cũ. Tiến trình của mỗi trình duyệt/origin độc lập; đổi tên thư mục trên cùng origin vẫn dùng namespace ver2, nên cần xuất bản lưu trước khi thử một bản ver2 khác.

## Kiểm chứng và giấy phép

Xem [báo cáo kiểm thử](analysis/VER2-VERIFICATION.md), [phát hành](analysis/VER2-RELEASE.md), [logic gốc](analysis/LOGIC.md), [nguồn và giấy phép](THIRD_PARTY.md). JAR gốc và ảnh gốc không bị sửa; tính năng ver2 được gắn qua adapter runtime Java và JavaScript.
