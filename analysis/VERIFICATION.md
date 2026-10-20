# Kiểm chứng bản web

Ngày 20/10/2026, Windows, Chrome/Playwright, XAMPP Apache tại `http://localhost/12suquan/`.

## Kết quả tự động

`npm test`: **16/16 Node tests và 11/11 browser tests pass**, không bỏ qua test. `npm run check` pass cho JavaScript giao diện và adapter. `uv run python analysis/assets/validate_assets.py` pass: 151 resource khớp byte/hash JAR, 131 PNG, 14 WAV, 33 atlas/212 rectangle trong biên ảnh.

| Phần | Đã quan sát và xác nhận |
|---|---|
| Khởi động | JAR thật qua runtime Java ME bằng JavaScript, đúng một application isolate; logo, chọn âm thanh, menu gốc |
| Nhân vật | Chơi mới → cốt truyện → chọn avatar → nhập tên TOAN → bản đồ Hoa Lư |
| Chiến đấu | Qua hội thoại bằng softkey gốc → bàn tutorial 8×8 → chọn kiếm, đổi xuống → ghép/xóa/rơi ô và gây sát thương |
| Công thức thực tế | HP đối thủ 105 → 97 sau nước kiếm đầu; lượt AI làm HP người chơi 105 → 85 |
| Tạm dừng | Canvas thật đứng yên; callback Java đã lên lịch không chạy; clock loại trừ thời gian pause; resume một lần |
| Lưu | Xuất RMS qua nút web, có tên TOAN trong byte gốc; dirty handle chưa đóng vẫn được flush đúng |
| Nhập | Bỏ qua tutorial bằng menu gốc để tạo checkpoint → xuất/nhập JSON qua UI → VM mới → Tiếp tục → bản đồ, RMS vẫn giữ TOAN; không bị handle của VM cũ ghi đè |
| Form native | Giữ command ID của Java, trim/validate tên, cancel/Escape, phone offline, alert khác dùng renderer gốc |
| Responsive | Viewport 375/768/1440px không tràn ngang; nút và nhãn điều khiển không ngắt dòng; iframe đang chơi khớp khung hiển thị trong sai số dưới 1px |
| Âm thanh | Nút mute áp dụng cả AudioContext tạo sau khi tắt âm |
| Kết nối | Luồng game đầy đủ không tạo HTTP request ra ngoài origin và không có uncaught pageerror |

Browser E2E thực hiện input qua UI và đọc trạng thái VM để xác minh; không thay HP, board hay save để vượt trận. Mã đọc heap trong test chỉ lấy field theo metadata của lớp. Các fixture shop/collectible được nêu riêng bên dưới.

## SMS và dịch vụ cũ

Adapter giữ callback cấp vật phẩm/mạng của Java, thay phản hồi mạng bằng xử lý cục bộ, không gửi SMS hoặc thu tiền. 7 unit tests kiểm tra parser, request đầu tiên chưa compile, gửi lặp, ACK/listener, prefix/port và chặn native SMS.

Kiểm tra Chrome riêng: hủy mua không cấp đồ; mua kiếm tăng 0 → 3; request fixture bộ sưu tập đi qua listener/parser/RMS/UI gốc, đủ bộ → đổi → reset. Quy tắc trao collectible là chính sách offline mô phỏng vì không có backend gốc để đối chiếu. Chưa cố tình chơi thua hết mạng để xác minh màn mua mạng bằng thao tác tự nhiên. Chi tiết và bằng chứng: [OFFLINE_TRANSACTIONS.md](OFFLINE_TRANSACTIONS.md), [offline-shop-probe.json](offline-shop-probe.json).

## Bằng chứng tái kiểm tra

- `tests/game.spec.cjs`: luồng game thật, export/import và kiểm tra layout.
- `verification/gameplay.json`: lịch sử screen, board, HP, lượt và tutorial.
- `verification/web-flow.json`: kết quả Tiếp tục sau import, một isolate, hình học khung game ở ba viewport, danh sách pageerror/request ngoài origin rỗng.
- `verification/game-menu.png`, `game-character.png`, `game-map.png`, `game-battle.png`, `game-match-resolved.png`, `game-ai-response.png`: ảnh canvas thực tế.
- `verification/playing-375.png`, `playing-768.png`, `playing-1440.png`: trang web khi game đang chạy sau khi khôi phục.
- `verification/storage-review.json`, `import-storage-review.json`: dirty handle, signed bytes, import → flush VM cũ → reload.
- [ADAPTER-REVIEW.md](ADAPTER-REVIEW.md): nguyên nhân lỗi, bản sửa, mutation tests tái hiện regression pause/flush/cache/import.
- [EVIDENCE.md](EVIDENCE.md): hash artifact, bằng chứng REA và ranh giới giữa phân tích tĩnh với quan sát runtime.

Để chạy lại: bật Apache XAMPP, `npm install`, `npm test`. Có thể dùng static server dự phòng `npm start` rồi đặt `GAME_URL=http://localhost:8080/` khi chạy Playwright. Website không cần Node hoặc Java khi Apache phục vụ các file tĩnh.

## Phạm vi đã phân tích và giới hạn

Đã decompile 167/167 class và trích bảng dữ liệu: 46 bước chiến dịch, 77 nhân vật, 8 nhiệm vụ phụ, 41 địa điểm, 43 tuyến đường, 9 phép và 6 vật phẩm. Runtime thực thi toàn bộ bytecode JAR gốc; lớp web bổ sung điều khiển, pause, âm thanh, lưu và dịch vụ offline. Đây là bản chuyển qua runtime tương thích, không phải viết lại 167 lớp thành JavaScript thuần.

Chưa chơi hết 46 bước, mọi phép, mọi nhánh AI/ngẫu nhiên hoặc mọi thiết bị di động. Phân tích tĩnh không thay thế cho kiểm chứng các luồng đó. MIDI giữ dữ liệu nốt/tempo nhưng âm sắc WebAudio có thể khác điện thoại Java ME. Bản lưu chứa filesystem/RMS đã được game ghi, không chứa RAM đang chạy; dùng menu lưu/T.Dừng gốc trước khi đóng hoặc chuyển tiến trình.

Apache đã kiểm tra trả 200 cho trang chơi/gallery và 403 cho `.git/config`, `tools/`. Static server Node dự phòng cũng đã kiểm tra trang chính, runtime, gallery và chặn thư mục phát triển.
