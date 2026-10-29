# Biệt đội Dư Quang — kế hoạch ver3

Ngày 29/10/2026. Source mới độc lập, không sửa v1/ver2, không dùng lại JAR hoặc ảnh gốc. Tham khảo nhịp chơi từ phân tích ver2/analysis/LOGIC.md: đổi hai ô liền kề, ghép ≥3, chuỗi rơi, ghép ≥4 thưởng lượt, mana, hồi máu, năng lượng duy trì, vàng/EXP, AI cân nhắc bàn cờ. Viết engine JavaScript mới để thể hiện chuyển động và nội dung riêng.

## Hợp đồng thiết kế
Giao diện chiến thuật với nền minh họa nguyên bản; navy #080f1b, teal #67ebd5, cam #ffb36c, chữ trắng ấm. Không sao chép màn hình phim hoặc logo. Trên desktop: cột anh hùng, bàn 8×8 trung tâm, đối thủ/nhật ký bên phải. Mobile: HUD và bàn ưu tiên, roster/skill gọn phía dưới. Nút không ngắt chữ, icon vừa hình vừa màu; bàn dùng được bằng phím, click và vuốt. Tôn trọng prefers-reduced-motion và có nút giảm chuyển động.

## Các phần triển khai
1. Nội dung: 6 nhân vật nguyên bản, 18 kỹ năng, 12 đối thủ/nhiệm vụ, 3 hồi và kết truyện.
2. Engine thuần: bàn có nước hợp lệ, cascade, AI, hiệu ứng trạng thái, boss 3 giai đoạn; RNG xác định để phục hồi đúng.
3. Hồ sơ: đội 3, module tối đa 3, shop 6 loại ×3 bậc; autosave sau hành động đã giải quyết, xuất/nhập có kiểm tra, namespace ver3 riêng.
4. Giao diện và asset: tranh nền tạo mới, atlas chân dung minh họa riêng, 7 biểu tượng tài nguyên, hiệu ứng Canvas/CSS, âm thanh tổng hợp cục bộ.
5. Kiểm thử: engine/AI/save/campaign/boss, browser flow và ảnh 375/768/1440; gói web tĩnh với đường dẫn tương đối.

Ver3 là vũ trụ riêng, không dùng tên/ngoại hình nhân vật Marvel, lời thoại hoặc cốt truyện sao chép. Tương đồng chủ đề không phải cam kết pháp lý. Save v1/ver2 không tương thích với engine/campaign mới và không được tự nhập.
