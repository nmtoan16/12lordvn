# Mục tiêu bản web

Chuyển Loạn 12 Sứ Quân từ JAR Java ME sang trải nghiệm chạy trong trình duyệt tại XAMPP. Nguồn duy nhất về thiết kế/gameplay là JAR người dùng cung cấp. Giữ nguyên màn hình pixel, ảnh, font bitmap, âm thanh và logic gốc.

## Phạm vi

- Phân tích mọi lớp ứng dụng, tài nguyên, trạng thái và lưu trữ; ghi rõ phần chưa kiểm chứng.
- Phục hồi ảnh `.mg` thành PNG và kiểm kê toàn bộ tệp.
- Chạy gameplay gốc qua lớp Java ME bằng JavaScript, với HTML/CSS/JS cho màn hình web, bàn phím, cảm ứng, lưu trên trình duyệt. Việc này bảo toàn bytecode thay vì thay công thức bằng phỏng đoán.
- Bản web phục vụ tại `http://localhost/12suquan/`, không cần plugin Java hoặc Java trên máy người chơi.
- Kiểm thử menu, bản đồ, bắt đầu trận, match-3, lượt đối thủ, lưu/tải và viewport 375/768/1440.

## Tiêu chí

- Giữ JAR gốc và SHA-256.
- Tất cả runtime/ảnh/âm thanh phải có bản cục bộ, không phụ thuộc CDN.
- Phân biệt bản chuyển tương thích với bản viết lại toàn bộ bằng JavaScript thuần; không tuyên bố hai việc này tương đương.
- Nút/nhãn không ngắt dòng, không tràn ngang; bàn phím không cuộn trang khi điều khiển game.
- Báo cáo bằng chứng phân tích và giới hạn xác minh. Không coi kiểm tra tĩnh là bằng chứng đã chơi hết chiến dịch.
