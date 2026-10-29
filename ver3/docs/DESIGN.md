# Thiết kế và cấu trúc ver3

## Định hướng thị giác

Thành phố ven biển tương lai, đêm xanh sâu và các mạch sáng cứu hộ. Bảng màu: nền `#080f1b`, mặt nổi `#101c2c`, đường viền `#28394b`, chữ `#edf3ef`, phụ `#a2b4c4`, điểm nhấn `#67ebd5`, cảnh báo/cộng hưởng `#ffb36c`. Chân dung bán hiện thực, giáp có dấu hao mòn và ánh sáng viền. Không dùng bộ nhận diện của phim.

Trang chỉ huy mở bằng tranh toàn cảnh và lời mời chiến dịch. Sau đó là đội hình 6 người, bản đồ 12 nhiệm vụ với briefing đối chiếu, cuối cùng xưởng module. Trong trận, desktop có ba cột: anh hùng – bàn – đối thủ. Mobile thu HUD vào hai cột, ưu tiên diện tích bàn và ba nút kỹ năng.

Nút, badge và nav giữ một dòng. Tên dài cắt bằng ellipsis; nội dung truyện được xuống dòng. Breakpoint 760/1050/1600, kiểm tra 375/768/1440. Bàn không cuộn khi vuốt; các vùng còn lại giữ cuộn trang thông thường.

## Bộ asset

| Asset | Sử dụng |
|---|---|
| afterglow.png | Hero trang đầu, nền sàn đấu đã phủ lớp tối |
| heroes.png | Atlas 3 cột × 2 hàng: Lyra/Sora/Nham; Dạ/Kính/Yên |
| enemies.png | Atlas 3 cột × 4 hàng; thứ tự nhiệm vụ 01–12 |
| pulse.svg | Xung kích / tấn công |
| flux.svg | Năng lượng / nhận diện Dư Quang |
| heart.svg | Sinh lực |
| shield.svg | Lá chắn |
| cell.svg | Pin duy trì |
| credit.svg | Tín dụng |
| star.svg | Dữ liệu EXP |
| enemy-*.svg | Biểu tượng hologram phụ, không thay chân dung chính |

Atlas kẻ địch có đường chia hàng thực tế tại y=358/720/1078 trên ảnh 1024×1536. `enemyPortrait` căn lại crop theo tọa độ này. Không cần cắt/lưu 12 ảnh riêng. Chiều rộng ba ô được chia đều. Bộ chân dung anh hùng 1536×1024 dùng CSS 300% × 200%.

## Chuyển động và âm thanh

- Tranh vào trang zoom nhẹ; chân dung thở sáng; địch chuyển động nổi nhẹ.
- Từng đợt cascade hiển thị bàn tại thời điểm ghép, co/biến mất ô, hạt sáng, rồi rơi ô mới.
- Hiệu ứng kỹ năng theo anh hùng: Lyra/Dạ vệt sáng, Sora vòng cảm ứng, Nham lục giác bảo hộ, Kính lăng kính tam giác, Yên các vòng sinh lực; Varkhos vệt công kích và thông báo mở khóa.
- Số sát thương, hồi máu, hấp thụ khiên và tên kỹ năng xuất hiện trong trận. Canvas giới hạn tối đa 160 hạt.
- Web Audio tổng hợp âm ngắn; mặc định tắt, bật trong Cài đặt.
- Tạm dừng ngăn AI và tiến trình hoạt ảnh ghép ô; khi tab bị ẩn tự tạm dừng. Hạt/âm đang chạy được dọn; thông báo trang trí có thể hết hạn trong lúc tạm dừng.
- Cài đặt giảm chuyển động và `prefers-reduced-motion` tắt phần chuyển động, giữ thông tin chiến đấu. Đây là trình diễn 2D, không có bộ xương 3D hay video cutscene.

## Biên giới dữ liệu

Engine độc lập DOM; cùng board/seed/action tạo cùng kết quả. Mỗi hành động được giải quyết và lưu trước khi diễn hoạt ảnh, tránh tải lại để hủy một nước bất lợi. UI khóa hành động trong cascade và trong lượt AI. Boss, cooldown, trạng thái, loadout chốt và thưởng ghi nhận đều nằm trong save.

Lưu cục bộ có schema và giới hạn giá trị, không phải chống gian lận. Không có máy chủ hoặc tính năng PvP. Save v1/ver2 không bị sửa. Một phiên chơi chủ động được khuyến nghị; chưa có khóa xung đột nhiều tab. Các test chạy profile trình duyệt riêng.

## Phạm vi hoàn thành

- [x] Nội dung gốc 6 anh hùng / 18 kỹ năng / 12 nhiệm vụ, toàn bộ lời thoại và kết truyện.
- [x] Engine ghép ô, AI, thêm lượt, cascade, trạng thái, boss 3 khóa.
- [x] Chọn đội, đổi chỉ huy, 6 module × 3 cấp, tối đa 3 module.
- [x] Minh họa mới, atlas 18 chân dung, icon SVG, Canvas/CSS/Web Audio.
- [x] Tự lưu, tiếp tục trận, backup, import/export và kiểm tra dữ liệu.
- [x] Giao diện 375/768/1440, phím/click/vuốt, reduced motion.
- [x] Kiểm thử và gói xuất bản tĩnh.
