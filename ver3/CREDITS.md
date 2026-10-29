# Nguồn nội dung và asset

Biệt đội Dư Quang là vũ trụ hư cấu riêng được tạo cho dự án này. Tên, lời thoại, nhiệm vụ, thiết kế nhân vật và hình ảnh được viết/tạo mới. Chỉ kế thừa ý tưởng gameplay ghép ô theo lượt, phát triển nhân vật, trang bị và AI của dự án hiện có.

Không lấy hình phim, khuôn mặt diễn viên, logo, nhạc, lời thoại hay tên nhân vật Marvel/Avengers. Varkhos đảm nhiệm vai trò phản diện vũ trụ cuối chiến dịch, có hình dạng, nguồn sức mạnh, động cơ và kết thúc riêng.

- `assets/afterglow.png`: minh họa thành phố Sao Trầm và biệt đội, tạo bằng công cụ imagegen của OpenAI cho dự án.
- `assets/heroes.png`: atlas 3×2 chân dung 6 anh hùng, tạo bằng imagegen; cắt hiển thị bằng CSS.
- `assets/enemies.png`: atlas 3×4 với 12 chân dung kẻ địch, tạo bằng imagegen; tọa độ hàng được căn theo hình xuất thực tế.
- 7 icon SVG tài nguyên: vẽ bằng đường vector trong `scripts/assets.js`.
- `enemy-0.svg` đến `enemy-11.svg`: bộ biểu tượng hologram ban đầu, giữ làm tài nguyên vector phụ; chân dung chính dùng atlas raster.
- Nhạc nền không sử dụng. Âm hiệu được tổng hợp trực tiếp bằng Web Audio, mặc định tắt.
- Chữ dùng font hệ thống Segoe UI/Arial, không tải font bên ngoài.

Asset minh họa do AI tạo; dự án không đưa ra chứng nhận độc quyền hoặc bảo đảm pháp lý về mọi thị trường phát hành.
