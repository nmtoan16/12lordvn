# Tài nguyên gốc

Các tài nguyên lấy trực tiếp từ `Loan-12-Su-Quan.jar`, SHA-256 `90bf8dc99acba9f0a89da99c58b45c8fdb20240841e5a708e0ccfee58a650ff9`.

- `original/`: 151 resource gốc, giữ nguyên byte/hash; gồm 130 `.mg`, một PNG và 20 âm thanh.
- `images/`: 131 PNG có thể sử dụng trực tiếp trên web, giữ nguyên kích thước, palette, alpha và dữ liệu nén PNG gốc.
- `audio/`: 14 AMR, 6 MIDI nguyên byte và 14 WAV PCM giải mã từ AMR cho trình duyệt.
- `manifest.json`: nguồn, đường dẫn, SHA-256, số byte, kích thước, alpha, chunk PNG và thông số âm thanh.
- `sprites.json`: 33 atlas / source rectangles đối chiếu với mã Java, thứ tự quân và biểu tượng phép.

`.mg` chỉ thay bốn byte `89 50 4E 47` đầu PNG bằng tổng chiều dài file dạng số nguyên 32-bit big-endian. Quá trình phục hồi thay đúng bốn byte này; từ offset 4 đến hết file giống hệt nguồn. Thuật toán tương ứng `az.c(String)` trong `analysis/decompiled/az.java`.

Tạo lại từ JAR tại thư mục dự án:

```powershell
uv run --with pillow --with imageio-ffmpeg --with mido python analysis/assets/extract_assets.py
uv run python analysis/assets/build_sprite_metadata.py
```

Mở `analysis/assets/index.html` qua web server để duyệt ảnh và nghe WAV. Các MIDI cần bộ tổng hợp MIDI/WebAudio; âm sắc phụ thuộc soundfont/bộ tổng hợp của runtime. Dữ liệu MIDI không bị thay đổi.
