# Sổ bằng chứng

Artifact: `D:\xampp\htdocs\12suquan\Loan-12-Su-Quan.jar`, 521304 byte, SHA-256 `90bf8dc99acba9f0a89da99c58b45c8fdb20240841e5a708e0ccfee58a650ff9`.

## REA — quan sát artifact

- `open_binary`: định dạng ZIP, archive, không phải executable Java SE độc lập.
- `inspect_artifact` parent Evidence `ev_3026e68fb04524b2a04915cc845b19ea4b8d1f7bbe5d50ca501d5adba3082625`.
- Summary Evidence `ev_6b66275665c3836008c2f405a31725499b6ffdaca78e52d5175e2c4ec33e2920`.
- Inventory Evidence `ev_8bb701ccf28c9de868e0e47266d26e996c6cf6859fbbbd641bc90f82907e2ed8`.
- Manifest graph `agm_05044bee9d049adc5403a5948601548c8e0cbe60be3a5966039bcc54703c7687`: 333 nodes/occurrences (gồm root), 332 edges; central-directory có 320 file entries và 13 directory entries; không có integrity contradiction.
- Extraction Evidence `ev_4e977f32eb38e808c16a2c1194f76eab2e20fb19ae9c0a68cedee4a578cb8acc`: 319 regular files được materialize, containment verified; entry metadata còn lại có trong inventory. Không coi số entry trong container là số tệp ứng dụng độc lập.

Giới hạn REA: ZIP central directory không bao phủ non-entry bytes; inventory không thiết lập gameplay. Các Evidence trên có authority `shipped-artifact`; không phải quan sát chơi game.

## Phân tích Java và tài nguyên

CFR 0.152 decompile 167 class; `analysis/class-inventory.json` ghi constant-pool metadata, API và tham chiếu từng lớp. Bộ đọc `<clinit>` riêng chỉ diễn giải opcode hằng/dựng mảng, không thực thi code ứng dụng; lưu raw arrays ở `static-data.json` và mapping có nghĩa ở `game-data.json`. Công thức và vai trò suy ra từ code được dẫn ở `LOGIC.md`.

130 MG khôi phục signature PNG theo `az.c(String)`; không vẽ lại, resample hay thay pixel. 131 ảnh gồm icon được decode, kiểm CRC, SHA-256; 20 audio giữ nguyên byte. Chi tiết có trong `ASSETS.md` và manifest.

## Runtime

Bản xuất canonical của bốn REA Evidence records được giữ trong `analysis/rea-evidence.json` (842328 byte). Phiên REA đã đóng sau khi xuất.

Bằng chứng chạy thật ở `analysis/verification/` và `VERIFICATION.md`. Chỉ các màn hình/luồng được kiểm tra ở đó được gắn trạng thái đã quan sát. Các chapter cuối, mọi nhánh ngẫu nhiên và âm sắc theo từng điện thoại vẫn là phần chưa kiểm chứng bằng runtime gốc trên thiết bị.
