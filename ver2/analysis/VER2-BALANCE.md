# Đối chiếu cân bằng ver2

Ngày kiểm: 22/10/2026. Không đổi giá, danh sách trang bị hoặc phần trăm trong SPEC. Các con số dưới đây là fixture có đầu vào công khai, không phải thống kê chơi hết chiến dịch hoặc cam kết về độ khó.

## Luật đang triển khai

- Bậc cao kế thừa bậc thấp; lấy mức cao nhất cho cùng chỉ số. Chỉ ba món đang lắp của side 0 có hiệu lực. Snapshot trận giữ bậc cũ dù hồ sơ ngoài trận thay đổi.
- Damage nguyên → Thần Binh/chí mạng → Quỷ Diện Giáp chia 4 → Bảo Giáp → miễn nhiễm Khiên Sét hoặc barrier → HP. Phản đòn lấy 25% damage sau giảm, trước hấp thụ, nếu đã có khiên trước hit. Đốt chuẩn bỏ armor/giảm damage, vẫn chịu barrier/miễn nhiễm.
- Hút máu lấy HP thực mất, không tính phần overkill, damage bị chặn, đốt hay phản đòn. Linh Châu và Lửa Địa Ngục cộng tỷ lệ rồi làm tròn xuống một lần. Regen/hút máu/cứu nguy không nhân lại bonus hồi Tim/Thuốc/Phép.
- Đốt Thần Binh III là một proc khi cast thành công hoàn tất, gồm Khiên Sét/Cam Lộ ở cả Bí Kíp 0 và III. Damage phép không tự đốt thêm lần nữa; nhóm Kiếm Lửa có token riêng, kể cả trong cascade của phép. Thiếu mana không cast hoặc đốt. Không phát damage giả bằng 0 để kích hoạt proc.
- Barrier cộng dồn tối đa HP tối đa. Cứu nguy chỉ một lần khi `0 < HP < 25% MaxHP`. Pháp Ấn hoàn mana sau cast thành công; chi phí vẫn 12/24/36, không phải chi phí đã trừ refund. Lương dùng số dư phần tư, nên 1.000 tick hao 750.

Nguồn tính: `runtime/equipment/rules.js`, `combat.js`, `skills.js`; thứ tự và số liệu được kiểm bằng expected số nguyên độc lập trong các test `equipment-*.test.cjs`.

## Ba tổ hợp cần theo dõi

Mỗi món ở bậc III. Đầu vào: người chơi 500/1.000 HP, 100 ATK, lương 1.000; địch 1.000 HP, 36 mana; không có item Quỷ Diện Giáp hoặc Khiên Sét. Bắt đầu trận với 0 mana để đo bonus; sau đó fixture chủ động nạp 36 mana, tung Lửa Địa Ngục, nhận một hit 400 và kết thúc hành động người chơi. Không resolve các ô bị phép phá trong bảng này, vì cascades phụ thuộc bàn/RNG.

| Kết quả | A: Thần Binh + Bí Kíp + Pháp Ấn | B: Linh Châu + Bảo Giáp + Bí Kíp | C: Bảo Giáp + Pháp Ấn + Thần Thạch |
|---|---:|---:|---:|
| Mana / barrier đầu trận | 20 / 0 | 0 / 500 | 20 / 500 |
| Lương tối đa | 1.000 | 1.000 | 1.300 |
| Damage phép trực tiếp | 495 | 330 | 250 |
| Đốt bổ sung | 25 | 0 | 0 |
| Hút máu thực | 99 (20%) | 148 (45%) | 0 |
| HP người chơi / địch ngay sau phép | 599 / 480 | 648 / 670 | 500 / 750 |
| Mana / barrier sau phép | 10 / 0 | 0 / 600 | 10 / 600 |
| Lượt thêm từ Pháp Ấn | 1 | 0 | 1 |
| Lượt choáng từ Bí Kíp | 1 | 1 | 0 |
| Hit địch 400: HP mất / barrier hấp thụ | 400 / 0 | 0 / 280 | 0 / 280 |
| Phản đòn thực từ hit đó | 0 | 70 | 70 |
| Regen cuối hành động | 0 | 40 | 0 |
| HP người chơi / địch cuối fixture | 199 / 480 | 688 / 600 | 500 / 680 |

Tổ hợp A gây 520 HP mất trong fixture một phép, so với 250 từ phép gốc; chưa tính ô/cascade và lượt thêm. B có thể đạt hút 45% cùng barrier/regen, nên cần đặc biệt theo dõi khả năng duy trì khi phép phá nhiều kiếm. C ưu tiên phòng thủ, lương và vàng/EXP trên bàn; thưởng nhiệm vụ và vàng mua không nhân. Đây là các tác động dự kiến từ số liệu đã chọn, không có điều chỉnh cân bằng ngầm.

## Chín kỹ năng và bàn cờ

Fixture VM thật: 100 ATK, 1.000 MaxHP người tung phép, 36 mana trước cast, không Thần Binh/Linh Châu/Bảo Giáp/Pháp Ấn. HP địch đủ để không overkill. Dừng FSM ngay khi Java `bu.a(I)` hoàn tất để tách damage trực tiếp khỏi score/cascade sau đó.

| ID | Tên | Damage gốc → cường hóa | Hiệu ứng chính cường hóa |
|---|---|---:|---|
| 0 | Quả Cầu Lửa | 100 → 135 | 3×3; hai ô Kiếm Lửa khác nhau ngoài vùng bị phá |
| 1 | Mưa Thiên Thạch | 150 → 200 | 5–6 vùng 2×2; barrier 150 |
| 2 | Lửa Địa Ngục | 250 → 330 | Hai vùng 4×4; hút 66 HP; choáng một lượt |
| 3 | Chuỗi Sét | 80 → 120 | 7–10 ô cùng loại, clamp theo số có trên bàn; +4 mana |
| 4 | Khiên Sét | 0 → 120 | Khiên 8 nhịp; hồi 150 HP |
| 5 | Sấm Sét | 200 → 280 | 5–6 vùng 3×3; trừ 12 mana; khiên 3 nhịp |
| 6 | Mũi Tên Băng | 80 → 120 | 4–5 ô khác nhau; trừ 12 mana, nhận 6 mana |
| 7 | Cam Lộ Thủy | 0 → 0 | Hồi 300 HP; ăn toàn bộ Tim; barrier 200 |
| 8 | Băng Phong | 150 → 220 | 5–7 ô khác nhau; trừ 24 mana; đóng băng ba lượt; hồi 150 HP |

**Khác biệt mã gốc cần ghi rõ:** `analysis/decompiled/bu.java:921–943` chỉ dựng bốn vùng 3×3 cho Sấm Sét, rồi yêu cầu chọn 3–5 nhưng `h(count,9)` clamp theo bốn vùng có sẵn (`:1005`). Vì vậy thực tế gốc phá 3 hoặc 4 vùng, mặc dù bảng mô tả cũ ghi 3–5. Ver2 cường hóa tạo đủ 5–6 vùng hợp lệ, chọn góc vùng không trùng; vùng 3×3 có thể giao nhau trên bàn 8×8 và danh sách ô cuối cùng hợp nhất để mỗi ô chỉ được phá một lần. Không tuyên bố 45–54 ô khác nhau khi các vùng giao nhau.

Đóng băng dùng đúng bộ đếm gốc: `G += số lượt`, `J[target] += số lượt + (J==0 ? 1 : 0)`. Một nhịp bù ban đầu bảo toàn việc `f(7)` giảm bộ đếm trước lượt kế. Không vừa tăng G vừa tự gọi chuyển lượt.

## Bằng chứng và giới hạn

- `tests/combat-parity.spec.cjs`: Java `bu.x()` resolve sáu nhóm, kiểm HP/heal/mana/lương/vàng/EXP gốc; kiếm bốn ô side 0 được tăng, AI giữ số gốc; hit 4.000 qua item armor, Bảo Giáp, barrier và phản đòn.
- `tests/skills.spec.cjs`: 45 cast Java thật (chín chiêu × bậc Bí Kíp 0/1/2/3, cộng chín cast AI mang cùng ID); kiểm field HP/mana, I/J/G, barrier, y/z, ô không lặp và hai Kiếm Lửa. Ba fixture thiếu mana không thay HP/bàn/buff/refund. Các kiểm này đã pass.
- Kiểm bổ sung đã pass: Khiên Sét/Cam Lộ × Bí Kíp 0/III × đủ/thiếu mana (tám trường hợp), mỗi cast thành công có đúng một event đốt; retry reset cứu nguy/phần lẻ lương/barrier và không nhân lương tối đa lần hai; Nhân Sâm giữ heal gốc trong khi Bình Thuốc nhận bonus hồi.
- Sáu fixture kết quả campaign/side-quest/arena × thắng/thua đi qua Java `bu.y()` → `bu.t()` → `dd.i/j()`, kiểm trạng thái 2/3, xóa snapshot 106, vàng nhiệm vụ không nhân Thần Thạch và arena phục hồi HP đã lưu. Đây là kiểm nhánh kết quả; không tuyên bố đã hoàn thành cốt truyện/nhiệm vụ/đấu trường qua chơi tay.
- Test pure có seed/fixture và số expected công khai; kiểm overkill, immune, ngưỡng cứu nguy, cộng hút máu, cap barrier, chuỗi 1.000 tick lương, token lặp và snapshot không cấp buff lại.
- Các fixture sửa field chỉ nằm trong browser context tạm và có chú thích trong `tests/helpers/combat-fixture.cjs`. Luồng tạo nhân vật → bản đồ → trận hướng dẫn vẫn chạy qua giao diện game thật trước khi áp fixture.
- Chưa từ các fixture này suy ra thời gian hạ mọi đối thủ, tỷ lệ thắng toàn chiến dịch hay kinh tế dài hạn. VFX/hiệu năng và xuất/nhập RMS có báo cáo kiểm riêng.
