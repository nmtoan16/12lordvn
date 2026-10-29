# Báo cáo bàn giao ver3 — 29/10/2026

## Kết quả

Ứng dụng chạy tại `http://localhost/12suquan/ver3/` trong XAMPP. Source là repository riêng trong `ver3`; không chỉnh sửa source v1/ver2. Gói web tĩnh `dist/ver3-web.zip` khoảng 9,19 MiB, gồm thư mục `ver3/` với 35 tệp. Runtime không cần Java, npm, CDN hay API.

| Kiểm tra | Kết quả |
|---|---|
| JavaScript syntax + tồn tại asset/nội dung | Đạt `npm run check` |
| Unit logic, chiến đấu, save | **13/13 đạt** |
| Chrome/Playwright luồng giao diện | **12/12 đạt** |
| Responsive | 375, 768, 1440 px; không tràn ngang |
| Console / page error | Không ghi nhận lỗi trong các luồng đã chạy |
| Mô phỏng chiến đấu | 240 trận đều kết thúc, không treo engine |
| Gói phát hành | 34 hash SHA-256 đúng + manifest, ZIP 35 tệp đọc được |
| Host tĩnh riêng | Nạp asset nội bộ, vào trận, tải lại và tiếp tục thành công |

## Đã thực hiện trong trình duyệt

1. Tải trang, kiểm tra tranh và bộ chân dung, mở giới thiệu nhiệm vụ, vào bàn đủ 64 ô ở ba kích thước.
2. Chọn/bỏ chọn đội; từ chối thành viên thứ tư. Mua khi tín dụng bằng 0 vẫn được; lắp module thứ tư bị từ chối; nâng ngoài trận không đổi loadout đã chốt.
3. Chơi nhiệm vụ đầu bằng click và kỹ năng tới chiến thắng, có lượt AI thật. Nhận thưởng, reload, mở kết quả lần nữa không nhân đôi thưởng, mở nhiệm vụ kế.
4. Gợi ý hai ô, di chuyển bằng phím, thao tác kéo tương đương vuốt trong context có touch, tạm dừng và tiếp tục. Sau reload giữ nguyên board, seed RNG và chỉ số chiến đấu.
5. Không cho người chơi kéo ô thay AI trong khoảng AI đang suy nghĩ.
6. Xuất JSON, từ chối JSON lỗi, reset có xác nhận, nhập lại bản hợp lệ; canary thuộc vùng ver2 không bị sửa.
7. Dùng fixture trùm để kiểm tra hiển thị Khóa III, chiến thắng cuối và lời thoại kết truyện; xác nhận cờ hoàn thành.
8. Mở bằng `file://` nhận hướng dẫn Apache/localhost, không kẹt ở loading.
9. Cả sáu anh hùng dùng được kỹ năng qua UI; kiểm tra hiệu ứng riêng, âm thanh và giảm chuyển động được lưu.
10. Trạng thái thua quay lại trung tâm, giữ tín dụng/EXP đã có.

Các test dùng profile trình duyệt cô lập. Không dùng hoặc thay đổi bản lưu thật của người chơi. Các bài fixture boss/thua giúp kiểm tra trạng thái biên; không được tính là đã chơi thủ công toàn bộ chiến dịch.

## Kiểm thử logic và cân bằng

13 bài unit bao gồm 100 seed tạo bàn có nước đi; cascade; đủ 18 kỹ năng; giới hạn trang bị; thiếu tiền; thưởng một lần; hai lần chuyển pha boss; cooldown; choáng; thêm lượt; đòn kết liễu; đổi chỉ huy; giới hạn tài nguyên; phục hồi bản lưu và từ chối dữ liệu sai; lỗi bộ nhớ đầy.

Mô phỏng 20 seed × 12 nhiệm vụ, tổng **240 trận**. Agent chọn nước theo trọng số, dùng Lyra tấn công/Yên hồi phục; module Công/Khiên/Năng lượng bậc III có thể mua hợp lệ với cơ chế miễn phần thiếu. EXP tăng bằng thưởng nhận được; không sửa chỉ số giữa trận. Nếu thua một màn, bộ kiểm thử vẫn mở màn kế để phủ đủ nội dung.

- Nhiệm vụ 1–11: 20/20 lần thắng ở cấu hình này.
- Varkhos: 15/20 lần thắng; 5 lần thua hợp lệ.
- Tổng: 235 thắng, 5 thua, 0 trận vượt giới hạn 600 hành động.

Đây là kiểm thử khả năng hoàn tất và cân bằng sơ bộ với đội đã nâng cấp, chưa phải nghiên cứu độ khó bằng người chơi thật. Tham số nằm trong `src/content.js`, có thể cân chỉnh sau playtest.

## Ảnh đã mở và tự đối chiếu

- `evidence/home-1440.png`, `home-768.png`, `home-375.png`: màn chỉ huy.
- `evidence/battle-1440.png`, `battle-768.png`, `battle-375.png`: sàn đấu.
- `evidence/release-mobile-home.png`, `release-mobile-battle.png`: kiểm tra bản đóng gói trên host khác.
- `evidence/boss-varkhos.png`, `ending.png`: trùm và kết truyện (fixture kiểm thử).
- `evidence/skill-prism.png`: kỹ năng lăng kính/Canvas.
- `evidence/victory.png`: kết quả trận chơi bằng UI.

Đã sửa qua kiểm tra hình: module bị dồn vào cột hẹp trên mobile, header bị rơi hàng, crop chân dung địch, ảnh chụp quá sớm khi background chưa tải, nhãn hẹp xuống dòng. Ảnh bàn giao được chụp lại sau sửa.

## Giới hạn thực tế

- Đã chạy Chrome trên Windows với viewport mô phỏng điện thoại/tablet; chưa xác minh trên iPhone/Safari hoặc thiết bị Android thật.
- Save chỉ cục bộ theo origin và trình duyệt, chưa có đồng bộ cloud hoặc giải quyết xung đột giữa nhiều tab. Ver3 không nhập save v1/ver2 vì khác game/chiến dịch.
- Hoạt ảnh 2D bằng Canvas/CSS; không có mô hình 3D, lồng tiếng hay phim cắt cảnh.
- Source mới và tài nguyên gốc không đồng nghĩa với chứng nhận pháp lý độc quyền cho sản phẩm phát hành.
- Chưa upload lên máy chủ công khai; gói web đã kiểm thử cục bộ và sẵn sàng đặt cạnh website hiện tại.

Log máy đọc: `evidence/browser-full-results.json` (12 bài), `browser-results.json` (responsive sau tinh chỉnh), `balance.json`, `release-check.json`.
