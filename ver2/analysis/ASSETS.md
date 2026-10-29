# Phân tích đồ họa và âm thanh Loạn 12 Sứ Quân

## Nguồn và bằng chứng

Nguồn duy nhất là `Loan-12-Su-Quan.jar`, SHA-256 `90bf8dc99acba9f0a89da99c58b45c8fdb20240841e5a708e0ccfee58a650ff9`. Không tạo thêm hoặc vẽ lại đồ họa. Các tài nguyên đã được lưu nguyên byte ở `assets/original/` trước khi tạo bản dùng cho web. Tổng cộng 151 resource, 288.944 byte: 130 MG, một PNG và 20 âm thanh.

`analysis/decompiled/az.java` xác nhận định dạng MG: trường `b` là `{-119, 80, 78, 71}`, tức `89 50 4E 47`; `c(String)` đọc bốn byte đầu để lấy chiều dài qua `ai.a`, đọc phần còn lại tại offset 4, chép lại bốn byte signature rồi gọi `Image.createImage`. Ví dụ `bkmenu.mg` dài 34.292 byte, bắt đầu `00 00 85 F4 0D 0A 1A 0A`, thay bốn byte đầu thành `89 50 4E 47` là PNG hợp lệ. Đây không phải ảnh bị mã hóa hoặc format cần phỏng đoán.

## Kiểm chứng

Đã xác nhận toàn bộ 130 MG chứa đúng độ dài big-endian tại offset 0; phục hồi đúng bốn byte signature; kiểm tra CRC của **tất cả** chunk PNG; kiểm tra chunk IEND và biên cuối file; giải mã toàn bộ pixel bằng Pillow. Mọi ảnh phục hồi vẫn giữ nguyên số byte như nguồn. `icon.png` được chép nguyên byte. Có 131 ảnh indexed palette (PNG mode P), trong đó 115 ảnh có transparency. Tổng dung lượng PNG 235.737 byte.

`assets/manifest.json` lưu SHA-256 nguồn và output cho từng file, toàn bộ dimensions, palette mode, alpha và danh sách chunk. Các PNG không được resize, đổi màu, xóa nền hoặc tối ưu lại. Bản contact sheet chỉ là công cụ kiểm tra, không dùng làm texture thay thế.

## Đồ họa chính

| File PNG | Kích thước | Vai trò |
|---|---:|---|
| `bksplashscreen.png` | 240 × 320 | Tranh splash và logo Loạn 12 Sứ Quân gốc |
| `bkmenu.png` | 240 × 320 | Phong cảnh menu |
| `bkboard.png` | 240 × 320 | Khung chiến đấu, nền bàn cờ và khu vực thông số |
| `m/m.png` | 380 × 380 | Bản đồ có địa danh vẽ sẵn |
| `avatar.png` | 300 × 240 | Atlas 10 cột × 8 hàng, ô 30 × 30; có các ô cuối trống |
| `sword`, `rice`, `heart`, `yinyang`, `gold`, `book`, `swordred` | Mỗi ảnh 22 × 22 | Bảy loại quân/biểu tượng trên bàn |
| `*icon.png` | Mỗi ảnh 30 × 30 | Chín phép thuật |
| `str*.png` | Nhiều cỡ | Chữ menu, trạng thái thắng/thua vẽ sẵn |

`u.java` dùng hình đại diện `index % 10 * 30`, `index / 10 * 30`, vùng 30 × 30. Đối thủ/người chơi có thể dùng phép lật ngang Java transform 2. Do đó cần dùng atlas gốc và source rectangle, không cắt theo vùng pixel khác màu.

Các label menu có cả ảnh thường và ảnh `focus`, cần giữ cặp tương ứng để giữ màu trạng thái như nguyên bản. Các font bitmap `_font1.png`, `_font2.png` (100 × 125), `f/ari_b.png` (1189 × 14) và `f/nb.png` (68 × 10) cũng được giữ nguyên; bảng ký tự tiếng Việt và glyph metrics nằm trong `dh.java`, `ap.java`, `aa.java`, `cm.java`. Việc dùng font hệ thống thay thế sẽ làm khác bố cục và độ rộng chữ.

## Atlas và animation

`assets/sprites.json` lưu source rectangles có bằng chứng từ các class Java: avatar; các strip tinh thể; hiệu ứng biến mất quân thường/x2; spell fireball, hellfire, icebolt, frozen, healing, shieldlighting, chainlighting; powerattack, defenceshield; focus và shop. Với strip ngang, `dq.a(Image,int)` dùng phép chia nguyên Java để tính frame width. Các pixel thừa cuối strip phải được bỏ qua giống runtime, không co kéo ảnh để chia đều.

| Atlas | Số frame | Kích thước frame |
|---|---:|---:|
| fireball | 9 | 61 × 49 |
| hellfire | 4 | 77 × 59 |
| icebolt | 5 | 20 × 40 |
| frozen | 9 | 50 × 50 |
| healing | 3 | 40 × 40 |
| shieldlighting | 7 | 39 × 37 |
| chainlighting | 3 | 30 × 30 |
| blastlighting | 5 | Vùng không đồng đều, mô tả chính xác từ `au.java` |

`blastlighting` không được chia đều theo chiều rộng/chiều cao. Năm rectangle x/y/w/h là `(19,0,12,17)`, `(20,17,11,34)`, `(17,51,4,17)`, `(12,68,27,63)`, `(0,131,56,62)`, kèm offset neo dọc lần lượt 91, 74, 56, 1, 0. Bảng animation timing/chuyển trạng thái vẫn thuộc logic game, không thể suy ra chỉ từ số frame.

`bm.java` xác nhận board 8 × 8, bước tọa độ 21 pixel; resource quân có cỡ 22 pixel để phủ biên liền kề. Không thay thành grid 22 pixel. Đồ họa chiến đấu được thiết kế ở canvas 240 × 320; khi scale trên web nên giữ tỷ lệ 3:4 và `imageSmoothingEnabled=false`/CSS `image-rendering:pixelated` để bảo toàn nét pixel.

## Âm thanh

14 AMR-NB nguyên byte được lưu cả trong `assets/original/audio/` và `assets/audio/`. Đã giải mã tất cả thành WAV PCM signed 16-bit, mono 8000 Hz tại `assets/audio/*.wav`; không thêm hiệu ứng hoặc thay nguồn âm. Duration, số sample, SHA-256 bản WAV được lưu ở `browserAudio` của từng entry trong manifest. AMR nguồn vẫn là bằng chứng chính; WAV phục vụ trình duyệt không có decoder AMR.

| Hiệu ứng | Thời lượng giây |
|---|---:|
| attack | 0,74 |
| explodechess | 0,38 |
| fire1 / fire2 / fire3 | 1,30 / 2,00 / 1,54 |
| ice1 / ice2 / ice3 | 2,02 / 2,50 / 1,98 |
| lightning1 / lightning2 / lightning3 | 2,04 / 1,80 / 2,04 |
| lvu / useitem / vs | 1,98 / 2,08 / 1,60 |

Sáu file MIDI giữ nguyên dữ liệu note, track, tempo và program. Durations được tính theo tempo bằng Mido: `intro.mid` 25,253924 giây (15 tracks), `menu.mid` 50 giây (11), `m.mid` 53,302501 giây (9), `victory.mid` 32 giây (1), `win.mid` 3,769767 giây (1), `lose.mid` 3,099748 giây (2). WAV giải mã AMR giữ đúng sample từ decoder; MIDI chỉ giữ được âm sắc nếu runtime dùng cùng bộ tổng hợp/soundfont như thiết bị gốc. Không có recording/soundfont của điện thoại gốc trong JAR để chứng minh âm sắc giống tuyệt đối.

`bs.java` ánh xạ âm thanh vào sự kiện: explodechess khi ăn quân, attack khi đánh, useitem khi dùng đồ; fire1/2/3, lightning1/2/3, ice1/2/3 cho các phép; win/lose cho kết quả. Các track nền được dùng bởi các screen khác.

## Công cụ duyệt và tái lập

- `analysis/assets/index.html`: gallery ảnh có tìm kiếm và scale pixel 1–4×; phát WAV, tải byte nguồn và xem manifest. Truy cập qua web server để fetch JSON.
- `analysis/assets/contact-sheet-major.png`: splash/menu/board/map/avatar.
- `analysis/assets/contact-sheet-main.png`: toàn bộ hình thư mục gốc.
- `analysis/assets/contact-sheet-map-fonts.png`: hình bản đồ, font và vật phẩm thư mục con.
- `analysis/assets/extract_assets.py`: tạo lại ảnh, originals, WAV, manifest và contact sheets trực tiếp từ JAR, không phụ thuộc folder giải nén tạm REA.
- `analysis/assets/build_sprite_metadata.py`: tạo source rectangle atlas từ kết quả đối chiếu decompile.

Lệnh tái lập ở `assets/README.md`. Gallery và contact sheets độc lập, không sửa trang game chính.
