# PLAN: Kế Hoạch Triển Khai Nâng Cấp Toàn Diện `ver3` (Afterglow v3.5)

> **Dựa trên đặc tả đã duyệt:** [`ver3/SPEC.md`](file:///d:/xampp/htdocs/12suquan/ver3/SPEC.md)
> **Phương pháp thực thi:** `test-driven-development` (RED → GREEN → REFACTOR) kết hợp `incremental-implementation` (chia thành 4 lát cắt dọc độc lập, kiểm thử sau mỗi bước).

---

## Danh Sách Lát Cắt Triển Khai (Vertical Slices)

### Slice 1 (`M1` & `M2`): Nâng Cấp Lõi Gameplay, Ô Quá Tải (`Overcharged Tiles`), Cộng Hưởng Đội Hình (`Tag-In & Sub-Passive`), Kỹ Năng Tăng Tiến & Module Thức Tỉnh Bậc III
- **Tập tin:** [`ver3/src/content.js`](file:///d:/xampp/htdocs/12suquan/ver3/src/content.js), [`ver3/src/engine.js`](file:///d:/xampp/htdocs/12suquan/ver3/src/engine.js), [`ver3/src/storage.js`](file:///d:/xampp/htdocs/12suquan/ver3/src/storage.js), [`ver3/tests/engine.test.js`](file:///d:/xampp/htdocs/12suquan/ver3/tests/engine.test.js), [`ver3/tests/combat.test.js`](file:///d:/xampp/htdocs/12suquan/ver3/tests/combat.test.js), [`ver3/tests/storage.test.js`](file:///d:/xampp/htdocs/12suquan/ver3/tests/storage.test.js)
- **Chi tiết công việc:**
  1. **Ô Quá Tải (`s.specials` — mảng 64 phần tử `0: thường | 1: Xung Ngang | 2: Xung Dọc | 3: Lõi Siêu Tân Tinh 3×3`):**
     - Khi ghép chuỗi `4 ô` → kết tinh ô tại vị trí vừa đổi thành **Ô Xung Tuyến (`1` hoặc `2`)**.
     - Khi ghép chuỗi `>= 5 ô` hoặc giao điểm chữ L/T → kết tinh thành **Lõi Siêu Tân Tinh (`3`)**.
     - Khi một Ô Quá Tải nằm trong chuỗi thu hoạch (`collect`), kích nổ toàn bộ Hàng ngang (`1`), Cột dọc (`2`) hoặc Vùng `3×3` (`3`), gây thêm sát thương cộng hưởng (`+12` cho Xung Tuyến, `+18` + `+6⚡` + `+6 Pin` cho Lõi Siêu Tân Tinh) và phát sự kiện `special` cho tầng VFX.
  2. **Cộng Hưởng Đội Hình (`Sub-Passive` & `Tag-In Assist`):**
     - **Nội tại phụ (`Sub-Passive`):** Các thành viên dự bị trong `s.team` vẫn đóng góp **50% hiệu lực nội tại** (ví dụ: Lyra dự bị `+7.5%` sát thương xung kích, Sora dự bị `+6` khiên đầu trận, Nham dự bị giảm `6%` sát thương nhận vào, Dạ dự bị `+10` sát thương khi ghép 4+, Kính dự bị `+1` năng lượng mỗi 2 ô năng lượng, Yên dự bị `+1.5 HP` mỗi ô sinh lực).
     - **Đòn Chi Viện Khi Đổi Chỉ Huy (`switchHero`):** Khi đổi sang thành viên khác trên sân, ngoài việc đổi bộ kỹ năng và nội tại chính, kích hoạt ngay **Chi Viện Nhập Trường (`Tag-In Assist`)** đặc trưng của nhân vật mới và phát sự kiện `assist` (Lyra chém xung kích, Sora hút năng lượng địch, Nham dựng khiên, Dạ bắn xuyên khiên, Kính nạp năng lượng & biến đổi ô, Yên hồi máu & pin).
  3. **Kỹ Năng Tăng Tiến Động (`ATK Scaling`) & Nâng Cấp 18 Kỹ Năng:**
     - Bảo toàn giá trị cơ bản ở Cấp 1 (`xp = 0`) để tương thích 100% với các bài kiểm thử đơn vị hiện có, đồng thời cộng thêm hệ số tăng tiến theo `(attack - baseAttack)` và tiến hóa hiệu ứng phụ cho cả 18 kỹ năng.
  4. **Module Thức Tỉnh Bậc III (`Awakened Tier III`):**
     - `edge III`: Kỹ năng có 25% Chí mạng (`×1.35`) và Hút máu `12%`.
     - `aegis III`: Đầu lượt người chơi tự hồi `+8 Khiên`; khi Khiên bị đánh vỡ hoàn toàn, phóng xung điện phản `18` sát thương.
     - `well III`: Khi tung kỹ năng bất kỳ, hoàn trả lại `+4 Năng lượng`.
     - `seed III`: Khi HP xuống `< 35%`, kích hoạt **Cấp Cứu Sinh Học** (1 lần/trận: hồi `+45 HP` và `+25 Khiên`).
     - `cell III`: Đổi Chỉ huy cộng thêm `+5 Năng lượng` và `+10 Pin`.
     - `lens III`: Đầu trận tự tạo sẵn `1 Ô Xung Tuyến` trên bàn cờ; ghép 4+ thưởng thêm `+4 Tín dụng` & `+4 Dữ liệu`.
  5. **Dự báo ý định AI (`enemyIntent`) & Sự kiện Soi nước đi AI (`aiSwap`):**
     - Xuất hàm `enemyIntent(s)` trả về thông tin chi tiết hành động kế tiếp của địch.
     - Khi `enemyStep(s)` đổi ô, ghi nhận sự kiện `swap` (`{side: 1, a, b}`) để tầng UI làm nổi bật 2 ô địch vừa đi.
- **Kiểm chứng:** Viết test mới cho `specials`, `Tag-In Assist`, `Sub-Passive`, `Awakened Modules`, `ATK Scaling` và chạy `node --test ver3/tests/*.test.js`.

---

### Slice 2 (`M3`): Tái Thiết Giao Diện Command Center & HUD Chiến Đấu Trực Quan
- **Tập tin:** [`ver3/src/views.js`](file:///d:/xampp/htdocs/12suquan/ver3/src/views.js), [`ver3/src/style.css`](file:///d:/xampp/htdocs/12suquan/ver3/src/style.css)
- **Chi tiết công việc:**
  1. **Trung tâm Chỉ huy (`command` view):**
     - Bổ sung **Thanh Điều Hướng Nhanh & Tóm Tắt Đội Hình (Tactical Command Bar)** hiển thị trực quan 3 thành viên đang chọn (Chỉ huy + 2 Chi viện), 3 Module đang lắp, nút chuyển nhanh đến *Đội Hình / Chiến Dịch / Xưởng Module*, và hiển thị bộ 3 kỹ năng + hiệu ứng Chi viện ngay trên thẻ nhân vật.
     - Hiển thị rõ nhãn **Đặc tính Thức Tỉnh Bậc III** trên từng thẻ Module ở Xưởng Cộng Hưởng.
  2. **HUD Chiến Đấu (`battleView`):**
     - Nâng cấp hệ thống hiển thị chỉ số từ 2 thanh lên **4 Thanh Tiến Trình Trực Quan**: `SINH LỰC (HP)`, `LÁ CHẮN (SHIELD)`, `NĂNG LƯỢNG (MANA)`, `PIN HỖ TRỢ (BATTERY)` (nhấp nháy cảnh báo khi Pin thấp).
     - Thay thế dòng trạng thái chữ đơn điệu bằng **Huy Hiệu Trạng Thái (`Status Pills`)** có biểu tượng và màu sắc riêng (`🔥 Đốt`, `⚡ Choáng`, `🛡️ Phản đòn`, `✦ Thức tỉnh`).
     - Hiển thị **Tóm tắt hiệu ứng kỹ năng trực tiếp trên mặt nút `.skill`** (vẫn giữ `white-space: nowrap` theo chuẩn kiểm thử chống tràn của `browser.spec.js`).
     - Hiển thị **Ô Quá Tải (`⚡↔`, `⚡↕`, `✦`)** rực sáng trên bàn cờ 8×8 với huy hiệu năng lượng riêng.
     - Bổ sung **Thanh Chiến Thuật Di Động (`Mobile Tactical Strip / Toggle`)** trên màn hình `<= 760px` để người chơi điện thoại xem được Nội tại, Module và Nhật ký giao tranh mà không bị tràn khung hình.

---

### Slice 3 (`M4`): Nâng Cấp Động Cơ VFX Canvas 2D, Hoạt Ảnh Bàn Cờ & Web Audio Đa Tầng
- **Tập tin:** [`ver3/src/effects.js`](file:///d:/xampp/htdocs/12suquan/ver3/src/effects.js), [`ver3/src/app.js`](file:///d:/xampp/htdocs/12suquan/ver3/src/app.js), [`ver3/src/style.css`](file:///d:/xampp/htdocs/12suquan/ver3/src/style.css)
- **Chi tiết công việc:**
  1. **VFX Canvas 2D Chuyên Biệt Cho 6 Anh Hùng & Boss (`src/effects.js`):**
     - Nâng cấp hệ thống hạt vật lý (particles có kích thước đa dạng, phát sáng glow, tia lửa kéo đuôi motion-trail, sóng xung kích shockwave rings).
     - Thiết kế 7 kiểu vẽ kỹ năng riêng biệt:
       - `lyra`: Song đao chém chéo chữ X rực lửa quang năng.
       - `sora`: Tia sét điện từ phân nhánh (zig-zag lightning arcs) + vòng từ trường.
       - `nham`: Khiên tinh thể lục giác đa tầng + sóng chấn địa.
       - `da`: Mưa tên ảnh tuyến xuyên mục tiêu + vệt khói tím.
       - `kinh`: Chùm tia laser khúc xạ lăng kính 3 màu + tam giác quang học xoay.
       - `yen`: Luồng bào tử sinh học phát sáng bay lượn + vòng sóng hồi sinh.
       - `enemy`: Giao thức Hắc Điện / Khóa Bình Minh của Varkhos với ấn ký khóa không gian đỏ thẫm.
  2. **Hoạt Ảnh Bàn Cờ, Soi Nước Đi AI & Phản Hồi Nhân Vật (`src/app.js`, `src/style.css`):**
     - Khi đổi ô sai (`invalid`): rung lắc đỏ 2 ô vừa chạm (`.tile.invalid-shake`).
     - Khi AI đi nước cờ (`enemyStep`): khoanh sáng 2 ô AI chọn (`.tile.ai-focus`) trước khi nổ chuỗi.
     - Khi kích nổ Ô Quá Tải hoặc vùng Kỹ năng: phát tia quét sáng trên các ô mục tiêu (`.tile.skill-hit`) và hiển thị huy hiệu `CHUỖI COMBO ×N!`, `CHI VIỆN!`, `THỨC TỈNH!`.
     - Thẻ nhân vật có hoạt ảnh `lunge` (lao tới khi tấn công) và `hit-react` (rung chấn chớp đỏ khi trúng đòn).
  3. **Web Audio Synthesizer Đa Tầng:**
     - Tạo hợp âm (chord/arpeggio) riêng cho: ghép chuỗi (tăng cao độ theo `chain`), kích nổ Ô Quá Tải, đổi Chỉ huy Chi viện, tung Kỹ năng, Chiến thắng & Cảnh báo.

---

### Slice 4: Kiểm Thử Toàn Diện (Unit Tests, Check Script, Balance Sim & Browser Verification)
- Chạy `node ver3/scripts/check.js`
- Chạy `node --test ver3/tests/*.test.js`
- Chạy `node ver3/scripts/balance.js`
- Kiểm tra giao diện trình duyệt để đảm bảo 0 lỗi console, 0 tràn ngang (`noOverflow`) ở mọi độ phân giải (`375px`, `768px`, `1440px`).
