# Biệt đội Dư Quang — Đêm cuối cùng (ver3)

Game web ghép ô chiến thuật với nội dung và đồ họa riêng. 6 anh hùng, 18 kỹ năng, 12 nhiệm vụ / 3 hồi, 12 loại địch và Varkhos có 3 giai đoạn. Viết mới trong `ver3`, chạy độc lập; không cần Java/JAR, CDN, tài khoản hay máy chủ API.

## Chơi ngay

1. Bật **Apache** trong XAMPP.
2. Mở **http://localhost/12suquan/ver3/**.
3. Chọn tối đa 3 anh hùng, chọn nhiệm vụ và bấm **Triển khai biệt đội**.

Không mở `index.html` bằng `file://`: trình duyệt chặn tải ES module. Trang này sẽ hiện hướng dẫn mở localhost, không để loading vô hạn.

Có thể chạy độc lập bằng Node: `npm start`, sau đó mở `http://localhost:8080`. Nếu đổi cổng/tên miền/trình duyệt, hãy xuất bản lưu trước vì vùng lưu của trình duyệt thay đổi.

## Điều khiển và luật

- Chạm/click 2 ô kề nhau hoặc kéo/vuốt một ô. Ghép 3 thu tài nguyên; ghép 4+ thêm lượt. Nước sai được hoàn lại. Hết nước tự xáo.
- Mũi tên: di chuyển tiêu điểm trên bàn; Enter/Space: chọn ô. `1/2/3`: kỹ năng, `H`: gợi ý, `P`: tạm dừng.
- Xung kích tấn công; năng lượng nạp kỹ năng; sinh lực hồi HP; khiên hấp thụ sát thương; pin duy trì trận; tín dụng/EXP được quyết toán khi thắng.
- Mỗi hành động tốn 6 pin. Cạn pin mất 10 HP. Chuỗi rơi tăng 15% sát thương mỗi đợt, tối đa +75%.
- Người đứng đầu đội quyết định HP/Công chung. Đổi chỉ huy miễn phí một lần mỗi lượt để đổi nội tại và bộ kỹ năng; không tạo một thanh HP mới.
- Kỹ năng tiêu 10/18/28 năng lượng, kết thúc lượt. Tuyệt kỹ có hồi chiêu 4 lượt. Đốt gây 8 sát thương cơ bản xuyên khiên mỗi lượt; choáng bỏ lượt; phản đòn trả 35% sát thương HP.
- Chỉ huy địch có AI chọn nước đi, cân nhắc HP/pin/năng lượng và dùng giao thức ở 18 năng lượng. Boss cuối tăng công, khiên và hút năng lượng tại ngưỡng 2/3 và 1/3 HP.
- Có 6 module × 3 bậc. **Mua được khi thiếu tín dụng**, chỉ trừ tối đa số đang có, phần thiếu miễn. Mua không tự lắp; mang **tối đa 3 module**, chốt hiệu lực khi bắt đầu trận.
- Thắng mở nhiệm vụ tiếp theo; chơi lại nhận 50% thưởng. Thua không mất tiến trình chiến dịch, nhưng không nhận tài nguyên nhặt trong trận thua.

## Lưu và chơi tiếp

Tự lưu sau mỗi hành động được giải quyết, kể cả khi hoạt ảnh chưa chạy xong. Vào lại trang, bấm **Tiếp tục trận**. Dữ liệu gồm bàn, seed RNG, HP, lượt AI, module đã chốt, hồi chiêu và thưởng đã nhận. Thưởng thắng chỉ ghi nhận một lần.

Trong **Cài đặt** có xuất/nhập JSON, giảm chuyển động, bật âm thanh và tạo hành trình mới. Bản lưu lỗi được kiểm tra, có bản dự phòng và không tự xóa dữ liệu gốc. Xuất bản lưu trước khi xóa dữ liệu website hoặc đổi máy.

**Ver3 là trò chơi mới, dùng khóa `afterglow-ver3:save`, không nhập tiến trình Loạn 12 Sứ Quân của v1/ver2.** Các khóa cũ không bị sửa. Không có đồng bộ cloud hoặc tài khoản. Một tab chơi tại một thời điểm được khuyến nghị; chưa có giải quyết xung đột giữa hai tab cùng chơi.

## Đưa lên website

Chạy `npm run package`. File `dist/ver3-web.zip` có sẵn thư mục `ver3/`. Giải nén cạnh source hiện tại để truy cập **URL-hiện-tại/ver3/**. Không ghi đè thư mục v1/ver2. Runtime dùng đường dẫn tương đối nên cũng có thể đổi tên thư mục.

Chỉ cần `index.html`, `.htaccess`, `src/`, `assets/`, `README.md`, `CREDITS.md`; gói web không chứa Git, node_modules, tests, ảnh kiểm thử hay tài liệu nội bộ. `manifest.json` chứa SHA-256 của các tệp phát hành. Apache cần cho phép `.htaccess` tương ứng; các host tĩnh khác vẫn chạy được bằng `index.html`.

## Phát triển và xác minh

Node và npm; chạy `npm ci` để cài Playwright khi cần kiểm thử. Chrome phải được cài cho cấu hình browser hiện tại.

```text
npm run check
npm test
npm run test:browser
node scripts/balance.js
npm run package
```

`GAME_URL` đổi URL kiểm thử; mặc định là localhost XAMPP. Runtime không cần cài npm. Các bài browser dùng profile riêng, không đọc hoặc xóa save thật.

- `src/content.js`: toàn bộ dữ liệu cốt truyện, nhân vật, kỹ năng, nhiệm vụ.
- `src/engine.js`: luật chiến đấu thuần JavaScript, RNG có seed, AI và phần thưởng.
- `src/storage.js`: kiểm tra schema, sao lưu và import/export.
- `src/app.js`, `views.js`, `effects.js`, `style.css`: điều khiển giao diện và hoạt ảnh Canvas/CSS.
- `docs/STORY.md`: kịch bản và thông số đầy đủ.
- `docs/DESIGN.md`: giao diện, asset và nguyên tắc phát triển.
- `docs/TEST-REPORT.md`, `evidence/`: báo cáo và ảnh kiểm thử.

Thiết kế phù hợp Chrome/Edge hiện đại. Chưa xác minh trên thiết bị iOS/Safari thật. Hoạt ảnh là hiệu ứng 2D Canvas/CSS trên minh họa, không phải game 3D hay hoạt hình khung hình đầy đủ.
