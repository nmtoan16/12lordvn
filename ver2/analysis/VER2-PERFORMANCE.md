# Đo HUD/VFX ver2

Ngày kiểm tra: 22/10/2026. Browser: Chrome qua Playwright, profile tạm. Test tái lập: `npx playwright test tests/equipment-performance.spec.cjs`.

Bảng dưới giữ một lần đo tham chiếu tại `verification/ver2/vfx-reference-performance.json`. File `vfx-matched-performance.json` được cập nhật mỗi lần chạy lại suite; scheduler và GC có thể gây dao động nhẹ.

## Phạm vi đo

Hai pha trong cùng browser và cùng máy ảo Java đang chạy: cùng bảng 8×8 được seed, cùng HP/ATK, Bảo Giáp III + Bí Kíp III; chỉ đổi Thần Thạch từ 0 sang III bằng fixture khởi tạo trận. Controller đứng ở trạng thái chờ fixture để không có AI hoặc cascade ngẫu nhiên làm lệch hai pha. Mỗi pha phát cùng sự kiện renderer sát thương/chiêu mỗi 100 ms trong 3 giây.

Đây là đối chiếu phần trang trí Hoàng Kim trong **ver2 hiện tại**, không phải phép đo v1 trước mọi thay đổi. Sự kiện stress là fixture renderer; kiểm cơ chế chiến đấu thực nằm trong test HUD/combat riêng.

| Số đo | Thần Thạch 0 | Thần Thạch III |
|---|---:|---:|
| Số mẫu RAF | 176 | 176 |
| RAF p95 | 17,5 ms | 17,3 ms |
| Số khoảng refresh canvas Java | 62 | 63 |
| Refresh canvas Java p50 | 51,2 ms | 51,0 ms |
| Refresh canvas Java p95 | 51,8 ms | 51,6 ms |
| Số mẫu vẽ lớp VFX | 177 | 177 |
| CPU vẽ VFX p95 | 0,1 ms | 0,1 ms |
| Pool cuối pha: chữ / trang trí | 9 / 24 | 9 / 24 |

Canvas Java được đo bằng timestamp lời gọi `MIDP.deviceContext.drawImage` trên canvas hiển thị. Khoảng này bao gồm nhịp sleep của game và lịch trình browser; không được diễn giải thành CPU render 51,6 ms. Mục tiêu dự kiến 40 ms cho toàn khoảng frame **chưa đạt trong fixture này ở cả hai pha**. Hiệu ứng Hoàng Kim không làm tăng p95 đo được; không có căn cứ từ phép đo này để cam kết game đạt 25 FPS hoặc mọi thiết bị đều mượt.

Heap do Chrome báo trước/sau: pha 0 khoảng 187,7 → 185,7 MB; pha III 186,6 → 190,6 MB. Đây là bộ nhớ JavaScript thô chịu ảnh hưởng GC và toàn runtime, không phải bộ nhớ riêng VFX; mẫu ngắn này không chứng minh không rò rỉ dài hạn. Pool có giới hạn cấu trúc: tối đa 12 nhãn và 24 hạt; test 1.000 hit/cast và stress browser 500 lượt vẫn giữ giới hạn. Renderer tự giảm số hạt trang trí nếu p95 CPU vẽ vượt 4 ms, giữ chữ số cơ bản.

## Kiểm tra hình ảnh và pause

`tests/equipment-hud.spec.cjs` đi từ tạo nhân vật thật đến trận hướng dẫn, mua/lắp Linh Châu III + Bảo Giáp III + Thần Thạch III qua RMS. Fixture hit/heal đi qua phương thức Java `bu.a(bn,int)` / `bu.b(bn,int)`:

- MaxHP 105, barrier đầu trận 52.
- Hit 20 → sau giảm 30% còn 14: barrier còn 38, HP vẫn 105.
- Hit 80 → sau giảm còn 56: barrier vỡ, HP còn 87.
- Heal 10 → cộng 80% hồi phục thành 18: HP trở lại 105.

Sự kiện và chữ số dùng đúng lượng thực mất/hồi/chặn. Snapshot VFX giữ nguyên khi pause 500 ms; hiệu ứng hết hạn sau resume. Các ảnh HUD dùng VM đang pause và chỉ ẩn lớp che pause của vỏ web trong lúc chụp để kiểm được pixel game. Sau mỗi hit, test cho VM vẽ một nhịp rồi pause lại; không dựng giả ảnh HUD.

Đã mở và xem các ảnh: `verification/ver2/hud-barrier-start.png`, `hud-barrier-absorb.png`, `hud-barrier-break.png`, `hud-heal-floating.png`, `vfx-golden-stress.png`. Thanh barrier nằm dưới thanh mana, không che HP/lương; nhãn nổi xếp thành các hàng để nhiều hit không chồng hết vào một vị trí. Sprite/avatar/bàn cờ gốc giữ nguyên.

Dữ liệu thô: [vfx-matched-performance.json](verification/ver2/vfx-matched-performance.json), [vfx-performance.json](verification/ver2/vfx-performance.json), [hud-events.json](verification/ver2/hud-events.json).
