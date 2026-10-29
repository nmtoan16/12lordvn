# Spec: Hệ Thống Cửa Hàng Trang Bị & Cường Hóa Kỹ Năng (Shop Equipment & Skill Upgrades)

## Capability Map (Phân Rã Mô-đun)

| Module ID | Trách nhiệm chính | Phụ thuộc |
|---|---|---|
| `equipment-store` | Lưu trữ trạng thái 6 trang bị (Bậc 0–3) trong RMS (`161b` record `120`), giao diện Tab "Trang Bị" trong Cửa Hàng (`q.java`) và hiển thị trong bảng Thông Tin (`ad.java`) | — |
| `combat-modifiers` | Can thiệp công thức sát thương, hồi máu, hút máu, lá chắn hấp thụ (Barrier HP), phản đòn, chi phí Mana và nâng cấp 9 kỹ năng theo bậc trong `bu.java` & `bn.java` | `equipment-store` |
| `skill-vfx-hud` | Hiển thị thanh Lá chắn (Shield Bar), chữ số sát thương/hồi máu nổi (Floating Combat Text), hào quang hộ thể và hiệu ứng kỹ năng thức tỉnh trong `bx.java` & `i.java` | `equipment-store`, `combat-modifiers` |

Thứ tự xây dựng: `equipment-store` → `combat-modifiers` → `skill-vfx-hud`

---

## Các Giả Định Kỹ Thuật (Assumptions)

1. **Giữ nguyên 100% file `Loan-12-Su-Quan.jar` gốc** (SHA-256 `90bf8dc99acba9f0a89da99c58b45c8fdb20240841e5a708e0ccfee58a650ff9`) để không phá vỡ cơ chế kiểm tra chữ ký khi Xuất/Nhập file lưu (`loan12-save-v1`).
2. **Triển khai dưới dạng Runtime Bytecode Hook (`runtime/equipment-shop.js`)** tương tự kiến trúc của `runtime/offline-shop.js` và `runtime/compat.js`.
3. **Đơn vị tiền tệ mua trang bị là Vàng (`Ngân Lượng` - `E[2]`)** kiếm được từ trận đấu, nhiệm vụ chính/phụ, đấu trường hoặc mua gói Ngân Lượng miễn phí trong Cửa hàng.
4. **Tương thích ngược tuyệt đối với tiến trình đang chơi:** Dữ liệu trang bị mới được ghi vào RMS `161b` tại record ID `120` (chưa từng được sử dụng bởi game gốc), đảm bảo không làm hỏng snapshot bản đồ (`16b/100`) hay snapshot trận đấu (`161b/106`).
5. **Chỉ áp dụng trang bị cho nhân vật người chơi (`side = 0` trong trận đấu)**, không tự động buff vào quái vật/Sứ quân AI (`side = 1`) để tránh làm lệch độ khó cốt truyện gốc.

---

## 1. Objective (Mục Tiêu & Thiết Kế Cơ Chế Gameplay)

### 1.1. Mục tiêu
Mở rộng hệ thống Cửa Hàng (`com/mic/smsgame/suquan/q`) và Chiến Đấu (`bu`, `bn`, `i`, `bx`) của *Loạn 12 Sứ Quân*, cho phép người chơi dùng Vàng mua và nâng cấp **6 Pháp Bảo / Trang Bị Vĩnh Viễn (mỗi món có 3 bậc nâng cấp: Bậc I → Bậc II → Bậc III)** nhằm:
- Gia tăng **Sát thương** (đòn đánh kiếm, kỹ năng, chí mạng, tỷ lệ ra Kiếm Lửa, sát thương chuẩn theo % máu).
- Cải thiện **Hồi máu** (tăng hiệu quả hồi từ Tim/Bình thuốc/Kỹ năng, hút máu khi dùng chiêu, tự hồi máu cuối lượt, hồi máu khẩn cấp khi nguy kịch).
- Bổ sung cơ chế **Lá chắn (Barrier Shield)** cho cả 3 hệ nhân vật (lá chắn máu ảo đầu trận, tạo giáp khi tung chiêu, giảm sát thương nhận vào và phản đòn).
- **Cải tiến trực tiếp 9 Kỹ năng** của 3 hệ Hỏa / Lôi / Băng (tăng hệ số sát thương, mở rộng vùng phá hủy ô cờ, giảm Mana tiêu hao, thêm hiệu ứng phụ như hồi máu/hút mana/tạo khiên/thưởng lượt).
- Nâng cấp **Hiệu ứng thị giác (VFX & HUD)**: vẽ thanh Lá chắn trực quan trên HUD chiến đấu, hiệu ứng hào quang khiên, chữ số sát thương/hồi máu bay lên (`Floating Combat Text`), và tăng mật độ hiệu ứng khi tung kỹ năng đã thức tỉnh.

### 1.2. Cơ chế Cửa Hàng 2 Tab (`q.java`) & Bảng Thông Tin (`ad.java`)
- **Trong Cửa Hàng (`q.java`):**
  - Giữ nguyên bố cục khung gỗ 6 ô (`2 hàng × 3 cột`) của game.
  - Thêm thanh chuyển gian hàng ở đỉnh khung (`[1] Vật Phẩm` | `[2] Trang Bị`) có thể chuyển bằng phím số `1`/`3`, phím `*`/`#`, hoặc chạm trực tiếp trên màn hình cảm ứng.
  - **Tab 1 (Vật Phẩm):** Giữ nguyên 6 vật phẩm tiêu hao gốc (`Long Thần Kiếm`, `Nhân Sâm`, `Ngân Lượng`, `Quỷ Diện Giáp`, `Bình Thuốc`, `Ngọc Ấn`).
  - **Tab 2 (Trang Bị):** Hiển thị 6 ô Trang Bị / Bí Kíp. Mỗi ô hiển thị cấp bậc hiện tại (`Lv0`, `Lv1`, `Lv2`, `Lv3/MAX`), tên trang bị theo bậc, mô tả chi tiết công dụng và giá Vàng để nâng cấp lên bậc tiếp theo.
- **Trong Bảng Thông Tin Nhân Vật trên Bản Đồ (`ad.java`):**
  - Tận dụng hàm `ad.b(Graphics, int, int)` vốn bị bỏ trống trong code gốc để vẽ dãy biểu tượng và cấp bậc của 6 trang bị người chơi đang sở hữu.

### 1.3. Bảng Thông Số Chi Tiết 6 Trang Bị & 3 Bậc Nâng Cấp

| Ô | Nhóm cơ chế | Bậc I (Sơ Cấp) | Bậc II (Trung Cấp) | Bậc III (Thức Tỉnh) |
|---|---|---|---|---|
| **0** | **Thần Binh (Sát Thương & Kiếm Lửa)** | **Thanh Phong Kiếm** (`800 Vàng`)<br>• +15% sát thương Kiếm & Kỹ năng<br>• Ghép $\ge 4$ Kiếm nổ Chí Mạng (+15% DMG) | **Hỏa Lôi Đao** (`2.200 Vàng`)<br>• +30% sát thương Kiếm & Kỹ năng<br>• Tăng tỷ lệ rơi Kiếm Lửa từ 11% lên 20% | **Thuận Thiên Bảo Kiếm** (`5.000 Vàng`)<br>• +50% sát thương Kiếm & Kỹ năng<br>• Tỷ lệ Kiếm Lửa 25%<br>• Kỹ năng & Kiếm Lửa đốt thêm 5% HP hiện tại của địch |
| **1** | **Linh Châu (Hồi Máu & Hấp Huyết)** | **Mộc Linh Châu** (`700 Vàng`)<br>• +25% lượng HP hồi phục (Tim, Thuốc, Phép)<br>• Hồi +3% MaxHP cuối mỗi lượt của mình | **Huyết Ngọc Bội** (`2.000 Vàng`)<br>• +50% lượng HP hồi phục<br>• Hồi +4% MaxHP cuối lượt<br>• **Hút máu 15%** từ sát thương Kỹ năng | **Cửu Chuyển Liên Hoa** (`4.500 Vàng`)<br>• +80% lượng HP hồi phục, Hút máu Kỹ năng 25%<br>• Khi HP $< 25\%$ lần đầu mỗi trận: tự hồi ngay **30% MaxHP** |
| **2** | **Bảo Giáp (Lá Chắn & Phản Đòn)** | **Huyền Thiết Giáp** (`900 Vàng`)<br>• Đầu trận nhận **Lá Chắn = 20% MaxHP**<br>• Giảm 10% sát thương nhận vào | **Kim Cang Hộ Tâm Kính** (`2.500 Vàng`)<br>• Đầu trận nhận **Lá Chắn = 35% MaxHP**, giảm 20% DMG<br>• Mỗi lần tung Kỹ năng nhận thêm Lá Chắn = **10% MaxHP** | **Long Lân Chiến Giáp** (`5.500 Vàng`)<br>• Đầu trận **Lá Chắn = 50% MaxHP**, giảm 30% DMG<br>• Khi đang có Lá Chắn/Khiên: **phản lại 25% sát thương** vào địch & miễn trừ Mana |
| **3** | **Pháp Ấn (Năng Lượng & Giảm Mana)** | **Thái Cực Phù** (`800 Vàng`)<br>• Vào trận có sẵn **+8 Mana**<br>• Mỗi nhóm Âm Dương ghép được cộng thêm **+2 Mana** | **Lưỡng Nghi Ngọc Ấn** (`2.200 Vàng`)<br>• Vào trận có sẵn **+14 Mana**, +3 Mana/nhóm Âm Dương<br>• Hoàn lại **6 Mana** sau mỗi lần dùng Kỹ năng | **Tiên Thiên Bát Quái Ấn** (`4.800 Vàng`)<br>• Vào trận có sẵn **+20 Mana**<br>• Hoàn lại **10 Mana** sau mỗi lần dùng Kỹ năng<br>• Tung chiêu cuối (Slot 3) được **+1 lượt đi ngay** |
| **4** | **Bí Kíp Võ Học (Cải Tiến 9 Kỹ Năng)** | **Bí Kíp Quyển Thượng** (`1.200 Vàng`)<br>• Cường hóa **Chiêu 1 (Slot 1)** của hệ đang chơi (tăng sát thương + mở rộng số ô phá hủy) | **Bí Kíp Quyển Trung** (`3.000 Vàng`)<br>• Cường hóa **Chiêu 1 & Chiêu 2 (Slot 1–2)** (thêm hiệu ứng tạo Lá Chắn / giật sét / hồi máu lớn) | **Bí Kíp Quyển Hạ** (`6.000 Vàng`)<br>• Thức tỉnh **cả 3 Chiêu (Slot 1–2–3)** với hiệu ứng đặc biệt (hút máu, bật khiên, thêm lượt đóng băng) |
| **5** | **Thần Thạch (Bàn Cờ & Hiệu Ứng VFX)** | **Linh Thạch Giác Ngộ** (`600 Vàng`)<br>• +30% Lương thực tối đa, giảm 25% hao lương<br>• Giảm 50% sát thương tự phạt khi xếp sai | **Tinh Thạch Phong Vân** (`1.800 Vàng`)<br>• Nhân **1.5× Vàng & EXP** ăn trên bàn cờ<br>• Ghép 4 quân cũng có 25% cơ hội rơi vật phẩm | **Thiên Nhãn Thần Thạch** (`4.000 Vàng`)<br>• Nhân **2.0× Vàng & EXP** trên bàn cờ<br>• Mở khóa **Hiệu ứng Kỹ năng Hoàng Kim**, hào quang hộ thể & chữ số sát thương nổi |

### 1.4. Chi Tiết Cải Tiến 9 Kỹ Năng Khi Nâng Cấp Ô 4 (`Bí Kíp Võ Học`)

| Hệ | Kỹ năng | Nguyên bản (`Bậc 0`) | Sau khi nâng cấp `Bí Kíp Võ Học` |
|---|---|---|---|
| **Hỏa** | **Slot 1: Quả Cầu Lửa** (ID 0) | $1.0\times\text{ATK}$, phá 1 vùng $3\times3$ | **Bậc I+:** $1.35\times\text{ATK}$, phá 1 vùng $3\times3$ + biến **2 ô ngẫu nhiên trên bàn thành Kiếm Lửa** |
| **Hỏa** | **Slot 2: Mưa Thiên Thạch** (ID 1) | $1.5\times\text{ATK}$, phá 3–5 vùng $2\times2$ | **Bậc II+:** $2.0\times\text{ATK}$, phá **5–6 vùng $2\times2$** + tạo **Lá Chắn Nhiệt = 15% MaxHP** |
| **Hỏa** | **Slot 3: Lửa Địa Ngục** (ID 2) | $2.5\times\text{ATK}$, phá 2 vùng $4\times4$ | **Bậc III:** $3.3\times\text{ATK}$, phá 2 vùng $4\times4$ + **hồi máu bằng 20% sát thương gây ra** + địch bị choáng mất 1 lượt |
| **Lôi** | **Slot 1: Chuỗi Sét** (ID 3) | $0.8\times\text{ATK}$, phá 4–8 ô cùng loại | **Bậc I+:** $1.2\times\text{ATK}$, phá **7–10 ô cùng loại** + nạp lại **+4 Mana** |
| **Lôi** | **Slot 2: Khiên Sét** (ID 4) | Bất tử 6 nhịp lượt | **Bậc II+:** Bất tử **8 nhịp lượt** + khi bật Khiên lập tức **giật sét gây $1.2\times\text{ATK}$ sát thương** & hồi **15% MaxHP** |
| **Lôi** | **Slot 3: Sấm Sét** (ID 5) | $2.0\times\text{ATK}$, phá 3–5 vùng $3\times3$ | **Bậc III:** $2.8\times\text{ATK}$, phá **5–6 vùng $3\times3$** + trừ **12 Mana địch** + tự bật **Khiên Sét 3 lượt** |
| **Băng** | **Slot 1: Mũi Tên Băng** (ID 6) | $0.8\times\text{ATK}$, trừ 8 Mana địch, phá 2–3 ô | **Bậc I+:** $1.2\times\text{ATK}$, trừ **12 Mana địch**, phá **4–5 ô** + hút **+6 Mana** cho bản thân |
| **Băng** | **Slot 2: Cam Lộ Thủy** (ID 7) | Hồi 20% MaxHP, ăn toàn bộ ô Tim | **Bậc II+:** Hồi **30% MaxHP**, ăn toàn bộ ô Tim + tạo **Lá Chắn Băng = 20% MaxHP** |
| **Băng** | **Slot 3: Băng Phong** (ID 8) | $1.5\times\text{ATK}$, trừ 18 Mana, phá 3–5 ô, đóng băng +2 lượt | **Bậc III:** $2.2\times\text{ATK}$, trừ **24 Mana**, phá **5–7 ô**, đóng băng **+3 lượt** + hồi **15% MaxHP** |

---

## 2. Tech Stack & Commands

- **Runtime:** Java ME (`MIDP-2.0` / `CLDC-1.0`) chạy trên trình giả lập `J2meJS` (`runtime/bld/j2me.js`, `runtime/bld/main-all.js`), giao diện HTML5/CSS3/Vanilla JS (`ES2022`).
- **Commands:**
  ```bash
  Build/Check: npm run check
  Unit Tests:  node --test tests/*.test.cjs
  Full Tests:  npm test
  Dev Server:  npm start
  ```

---

## 3. Project Structure

```text
index.html                  → Giao diện khung máy chơi game Web/Mobile
web/app.js                  → Điều khiển vỏ máy, phím bấm, import/export save
web/style.css               → Giao diện responsive Mobile/Desktop
runtime/main.html           → Iframe nạp máy ảo J2ME và các adapter script
runtime/compat.js           → Adapter SMS sleep & hộp thoại nhập tên HTML
runtime/bridge.js           → Cầu nối phím bấm, âm thanh, tạm dừng, xuất/nhập IndexedDB
runtime/offline-shop.js     → Giả lập giao dịch SMS miễn phí cho vật phẩm/mạng chơi/bộ sưu tập
runtime/equipment-shop.js   → (MỚI) Module mở rộng Cửa hàng Trang bị, Cường hóa Kỹ năng, Lá chắn & VFX
tests/                      → Bộ kiểm thử Unit (`*.test.cjs`) và Playwright E2E (`*.spec.js`)
```

---

## 4. Code Style

Sử dụng IIFE `'use strict'` đồng nhất với `runtime/offline-shop.js` và `runtime/bridge.js`, kết hợp cơ chế hook `J2ME.ClassInfo.prototype.getMethodByIndex` và `ReplaceMethod` để can thiệp chính xác vào phương thức Java mà không làm rò rỉ biến toàn cục:

```javascript
'use strict';
(() => {
  function hookJavaMethod(className, implKey, createWrapper) {
    // Bọc cả đường chạy interpreter lẫn JIT/AOT trong J2ME
  }
})();
```

---

## 5. Testing Strategy

- **Unit Tests (`tests/unit.test.cjs`):**
  - Kiểm tra cú pháp (`node --check`) cho mọi file script bao gồm `runtime/equipment-shop.js`.
  - Kiểm tra công thức tính toán nâng cấp trang bị (sát thương, hồi máu, lá chắn, giảm mana, giá vàng từng bậc) và tính toàn vẹn SHA-256 của `Loan-12-Su-Quan.jar`.
- **Browser E2E Tests (`tests/game.spec.js`):**
  - Kiểm tra vào Cửa Hàng chuyển đổi giữa Tab `Vật Phẩm` và Tab `Trang Bị`, mua/nâng cấp trang bị bằng Vàng, lưu trữ bền vững trong RMS/IndexedDB và hiển thị hiệu ứng trong trận đấu mà không phát sinh lỗi console hay mất dữ liệu save cũ.

---

## 6. Boundaries

- **Always do (Luôn thực hiện):**
  - Giữ nguyên tuyệt đối file `Loan-12-Su-Quan.jar` và các tài nguyên gốc trong `assets/original/`.
  - Bảo toàn 100% dữ liệu lưu hiện có của người chơi trong IndexedDB (`asyncStorage`) và định dạng xuất file `loan12-save-v1`.
  - Chạy `npm run check` và bộ test trước khi hoàn tất.
- **Ask first (Hỏi trước khi làm):**
  - Thay đổi giá Vàng, chỉ số phần trăm cân bằng, hoặc thay đổi danh sách 6 loại trang bị.
  - Áp dụng trang bị cho cả AI đối phương (chế độ khó / New Game+).
- **Never do (Không bao giờ làm):**
  - Ghi đè các record ID gốc của RMS (`16b: 4..16, 100..105`; `161b: 1, 2, 3, 9, 106`) theo cấu trúc không tương thích ngược.
  - Gửi kết nối mạng hoặc SMS ra bên ngoài.

---

## 7. Success Criteria (Tiêu Chí Nghiệm Thu)

1. Trong màn hình **Cửa Hàng (`q.java`)**, người chơi có thể chuyển sang gian hàng **Trang Bị**, xem đầy đủ tên, bậc hiện tại (`0..3`), công dụng và giá Vàng của 6 trang bị (`Thần Binh`, `Linh Châu`, `Bảo Giáp`, `Pháp Ấn`, `Bí Kíp Võ Học`, `Thần Thạch`), và bấm **Mua/Nâng cấp** khi đủ Vàng.
2. Trong hộp thoại **Thông Tin (`ad.java`)** trên bản đồ, hiển thị rõ các trang bị và bậc mà nhân vật đã sở hữu.
3. Trong **Trận Đấu (`bu.java`, `bn.java`, `ah.java`)**:
   - Sát thương kiếm & kỹ năng, lượng hồi máu, hút máu, hoàn Mana và mô tả sát thương trong bảng chọn Tuyệt Chiêu (`ah.java`) tự động cập nhật theo cấp bậc trang bị.
   - Nhân vật có **Lá Chắn (Barrier HP)** đầu trận và khi tung chiêu; lá chắn hấp thụ sát thương thực tế trước khi trừ máu và phản đòn ở Bậc III.
   - Cả 9 kỹ năng của 3 hệ hoạt động đúng hiệu ứng cải tiến khi nâng cấp `Bí Kíp Võ Học` (Bậc I → Chiêu 1, Bậc II → Chiêu 2, Bậc III → Chiêu 3).
4. Trên **HUD & Hiệu Ứng Chiến Đấu (`bx.java`, `i.java`)**:
   - Thanh máu hiển thị thêm lớp **Thanh Lá Chắn (màu xanh lam/kim)** và biểu tượng hộ thể khi đang có giáp ảo.
   - Khi gây sát thương, hồi máu hoặc chắn đòn, hiển thị **chữ số nổi (Floating Text)** và hiệu ứng kỹ năng tăng cường mượt mà ở khung hình `240×320`.
5. Dữ liệu trang bị được lưu tự động vào RMS/IndexedDB, giữ nguyên khi tải lại trang hoặc Xuất/Nhập file save.

---

## 8. Open Questions (Câu Hỏi Cần Duyệt)

1. **Về cân bằng giá Vàng & khả năng mua:** Nếu người chơi không đủ Vàng khi bấm mua Trang Bị ở Tab 2, bạn muốn hệ thống báo *"Không đủ vàng"* kèm gợi ý chuyển sang mua gói `+1.000 Ngân Lượng` (miễn phí trên bản web), hay chỉ cho phép dùng Vàng cày được từ đánh quái/nhiệm vụ?
2. **Về giới hạn trang bị:** Người chơi có thể mua và kích hoạt **đồng thời cả 6 trang bị** (mỗi món nâng tối đa lên Bậc III) để nhân vật mạnh toàn diện, hay bạn muốn giới hạn chỉ được **mang tối đa 3 trang bị vào trận** (cơ chế lắp/tháo trang bị theo chiến thuật)?
3. **Về thông số 6 trang bị & cải tiến 9 kỹ năng:** Các chỉ số tăng sát thương, hồi máu, lá chắn và hiệu ứng thức tỉnh của 9 chiêu thức trong bảng trên đã đúng ý bạn chưa, hay cần điều chỉnh món nào?

---

## 9. Bổ sung theo yêu cầu người dùng ngày 22/10/2026

Phần này cập nhật các lựa chọn còn mở ở §8 và thay thế những mô tả mâu thuẫn trong các phần trước. Bản SPEC gốc ở thư mục cha được giữ nguyên. Kế hoạch chi tiết: [tasks/plan.md](tasks/plan.md); checklist: [tasks/todo.md](tasks/todo.md).

### 9.1. Tách phiên bản trước khi chỉnh sửa

- Đã clone source hiện hành, gồm giao diện mới ở commit `f63b732`, vào `D:/xampp/htdocs/12suquan/ver2` trước khi viết bổ sung này.
- Mọi triển khai sau đây chỉ thực hiện trên bản sao ver2; URL khi phát hành là `<URL ứng dụng hiện tại>/ver2/`.
- Tách namespace IndexedDB/localStorage/cache trước khi chạy thử, vì hai thư mục cùng origin không tự tách tiến trình. Cập nhật theo yêu cầu ngày 23/10/2026: tự sao chép checkpoint v1 khi ver2 chưa có bản lưu; nếu ver2 đã có dữ liệu, cung cấp nút Lấy bản lưu ver1 có xác nhận thay thế. Giữ nguyên v1, không đồng bộ ngược. Xuất/nhập tệp tiếp tục hỗ trợ chuyển thiết bị/origin.
- Lượt hiện tại chỉ hoàn tất clone và kế hoạch; các tính năng bên dưới đang ở trạng thái dự kiến triển khai.

### 9.2. Mua khi thiếu vàng

Nút Mua/Nâng cấp vẫn hiển thị, bấm được và giao dịch được hoàn tất dù thiếu vàng, miễn món chưa MAX. Không bắt buộc chuyển sang mua thêm Ngân Lượng. Hủy không thay dữ liệu; không xử lý hai lần một xác nhận.

Đề xuất chi tiết: giữ giá niêm yết, trừ `min(vàng hiện có, giá)`; phần thiếu được miễn và vàng sau mua không âm. Quy tắc này áp dụng cho tab Trang Bị và các vật phẩm tiêu hao có giá vàng trong ver2. Đây là cách làm rõ phần trừ vàng/phạm vi chưa được chỉ định riêng, được nêu minh bạch trong kế hoạch. Giá của 18 bậc và số lượng vật phẩm giữ như bảng hiện có.

Tiêu chí §7.1 được cập nhật: mua/nâng cấp thành công ở cả trường hợp đủ, thiếu hoặc 0 vàng; chỉ trạng thái MAX không nâng tiếp.

### 9.3. Mang tối đa ba trang bị

- Sở hữu/nâng cấp cả sáu món nhưng có tối đa ba ô lắp, không trùng món và không lắp bậc 0.
- Chỉ món đang lắp áp dụng chỉ số, kỹ năng và hiệu ứng. Bí Kíp Võ Học/Thần Thạch đều chiếm một ô. Vật phẩm tiêu hao không chiếm ô.
- Lắp/tháo ở cửa hàng, Thông Tin hoặc màn chuẩn bị trận để cân nhắc đối thủ; chọn món thứ tư phải chọn thay một ô hoặc hủy.
- Khi vào trận chụp ID/bậc của ba món; khóa trong trận và khi tiếp tục snapshot. Mua/nâng cấp ở ngoài không đổi bậc đang áp cho trận đã tạm lưu.
- Tiêu chí nghiệm thu bổ sung: kiểm 0–3 món, từ chối món thứ 4 nếu chưa chọn thay, không hiệu lực từ món không lắp, khôi phục đúng loadout sau save/import.

### 9.4. Bỏ màn quảng cáo lúc load

Loại bỏ màn quảng cáo trang phân phối/crack `www.giaitri321.pro` và pha quảng cáo liên quan trong `cn`. Giữ khởi tạo MIDlet, canvas 240×320, bìa/cốt truyện game và credits. Thực hiện bằng adapter có phạm vi; giữ nguyên file JAR. Kiểm từ frame đầu của cold/warm boot và restart sau import, không chỉ giấu dòng URL.

### 9.5. Các điểm kỹ thuật được làm rõ

- Khóa 120 đề xuất là **khóa logic của lớp `dr`** trong store 161b, không phải physical RecordStore ID 120. Sidecar trận/journal có khóa riêng sau khi xác minh không xung đột; không đổi buffer 106 gốc.
- Màn Thông Tin là class `ad` trong default package (`analysis/decompiled/ad.java`); class cùng tên trong `com/mic/smsgame/suquan` xử lý âm thanh.
- Nhãn tab thống nhất `[1] Vật Phẩm` / `[3] Trang Bị` cho đúng phím 1/3; */# chỉ chuyển tab khi ở shop.
- Chia `equipment-shop.js` thành các module nhỏ theo kế hoạch; dữ liệu sở hữu, loadout, công thức chiến đấu và renderer có trách nhiệm riêng.
- Các giá trị/phần trăm trong §1.3–1.4 là baseline. Quy tắc kế thừa bậc, giới hạn barrier, nguồn kích hoạt hiệu ứng, vòng đời Chơi mới và mức VFX trong kế hoạch là đề xuất bổ sung, không phải thay đổi đã triển khai hoặc được tự coi là chỉ số nguyên bản.
