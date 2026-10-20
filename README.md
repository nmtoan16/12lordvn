# Loạn 12 Sứ Quân — bản web

Mở **http://localhost/12suquan/** khi Apache XAMPP đang chạy. Bấm **Vào game**, đợi logo gốc, chọn âm thanh bằng phím trái hoặc phải. Website chạy toàn bộ trong trình duyệt, không cần cài Java, không có CDN.

## Điều khiển

Mũi tên hoặc WASD để di chuyển; Enter/Space hoặc 5 để chọn/đổi ô; Q/E là hai phím chức năng. Các phím 0–9, `*`, `#` vẫn được giữ. Trên điện thoại dùng bàn phím ảo. Nhìn nhãn ở đáy màn hình gốc: một số màn dùng Q để “Chọn” (ví dụ chọn avatar), Enter là nút giữa. Trong trận, nhấn chọn một ô rồi nhấn hướng để đổi với ô bên cạnh. Nút Phóng to mở toàn màn hình.

## Phương pháp chuyển sang web

Đây là **bản chuyển bằng runtime Java ME viết bằng JavaScript**, giữ nguyên bytecode trong JAR để bảo toàn toàn bộ công thức, AI, kịch bản, màn hình và tài nguyên. Không phải bản viết lại 167 lớp thành JavaScript thuần. Giao diện web, điều khiển, tạm dừng, quản lý âm thanh, xuất/nhập bản lưu và adapter dịch vụ điện thoại được viết bổ sung.

JAR gốc không bị sửa. SHA-256: `90bf8dc99acba9f0a89da99c58b45c8fdb20240841e5a708e0ccfee58a650ff9`.

## Tiến trình

Game dùng RecordStore gốc, được lưu trong IndexedDB riêng của ứng dụng. Khi rời bản đồ, hãy dùng menu gốc để game ghi trạng thái; trong trận, dùng T.Dừng của game để lưu snapshot theo logic gốc trước khi đóng trang. Nút Tạm dừng trên web dừng đồng hồ và thực thi, không tự tạo checkpoint hoặc snapshot nếu game chưa lưu. Sao lưu qua **Lưu và chuyển tiến trình → Tải bản lưu**, nhập JSON để chuyển sang trình duyệt khác. Xóa dữ liệu website hoặc dùng chế độ riêng tư có thể làm mất tiến trình. Sao lưu không bao gồm bộ nhớ RAM máy ảo.

Tutorial đầu game chưa tạo checkpoint bản đồ. Hoàn thành hoặc bỏ qua tutorial bằng phím phải E và xác nhận gốc để game ghi tiến trình; bản lưu sau đó đã được kiểm tra khôi phục qua nút Tiếp tục.

## Phân tích và tài nguyên

- [Báo cáo logic](analysis/LOGIC.md): trạng thái, ghép 3, AI, công thức, 9 phép, 6 vật phẩm, quest, SMS và RMS.
- [Báo cáo đồ họa/âm thanh](analysis/ASSETS.md).
- [Gallery tài nguyên](analysis/assets/index.html): 131 ảnh, âm thanh gốc và 14 WAV giải mã.
- `analysis/decompiled/`: đủ 167 lớp Java sau decompile.
- `analysis/game-data.json`: 77 nhân vật, 46 bước chiến dịch, 8 nhiệm vụ phụ, 41 điểm và 43 tuyến đường, toàn bộ hội thoại.
- `assets/manifest.json`, `assets/sprites.json`: checksum, kích thước và 212 source rectangle của atlas.
- [Bằng chứng REA](analysis/EVIDENCE.md), [kiểm thử thực tế](analysis/VERIFICATION.md), [nguồn/giấy phép](THIRD_PARTY.md).

## Phát triển

Node.js và Chrome được dùng cho kiểm thử: `npm install`, sau đó `npm test`. XAMPP phục vụ thư mục này; `npm start` là static server thay thế tại http://localhost:8080/. Không cần build để chạy bản XAMPP.

Phần âm thanh MIDI giữ nguyên nốt/tempo nhưng được tổng hợp bằng WebAudio nên không khẳng định âm sắc trùng mọi thiết bị Java ME. Kết quả phân tích tĩnh phủ toàn JAR; báo cáo kiểm thử nêu riêng những luồng đã thực sự chạy, không khẳng định đã chơi hết chiến dịch.
