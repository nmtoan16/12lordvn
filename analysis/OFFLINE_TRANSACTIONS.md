# Giao dịch SMS cũ trong bản web

`runtime/offline-shop.js` là adapter cục bộ, load sau `compat.js`/`bridge.js`, trước khi MIDlet bắt đầu liên kết các method. Không thay JAR, battle logic, giá vật phẩm bằng vàng, số lượng phần thưởng shop hoặc các callback mua hàng gốc. Không mở đường gửi SMS/network.

## Bằng chứng trong JAR

- `an.a(String,int,a)`: request `PREFIX v2 a2 o<session> d3 p<platform> rshop ... e<timestamp>` dùng mua shop.
- `an.b(String,int,a)`: request `a2` với reason rỗng dùng mua mạng; callback `n.a()` gọi `bg.c(cx.J)`. `SQMidlet.a(boolean)` đặt `cx.J=3`, tức cộng ba mạng.
- `an.c(String,int,a)`: request `a4 rreco<N>` dùng mở/đổi vật phẩm sưu tập, N từ 0 đến 9.
- `an.a(String,a)`: request `a5 c<code>` cho thông báo nhận phần thưởng.
- `cs.run()`: bản JAR được cung cấp tạo connection + TextMessage, gọi `setPayloadText`, rồi gọi callback thành công. Không có lệnh `MessageConnection.send()` trong method. Vì vậy chỉ override `Native.send0` không thể nhận biết giao dịch shop/mạng/vật phẩm của bản này.
- `com/mic/smsgame/suquan/q.w()`: shop cộng đúng `{3,3,1000,3,3,3}` theo loại; các giao dịch giá vàng vẫn trừ vàng trong Java trước khi cấp vật phẩm. Các lựa chọn mua đều có hộp xác nhận gốc. Hủy hộp gốc không đi tới request/callback cấp đồ.

## Cách adapter hoạt động

`ReplaceMethod[TextObject.setPayloadText]` ghi đúng buffer UTF-16BE như class runtime gốc, quan sát request v2 hợp lệ rồi báo: “Giao dịch mô phỏng miễn phí trên bản web. Không gửi SMS, không thu phí.” Không cấp trực tiếp mạng/vật phẩm trong adapter; `cs.run`, `n`, `o` và các callback Java gốc tiếp tục làm việc đó. Không thêm hộp xác nhận lần hai.

Interpreter J2meJS chạy method Java cold trực tiếp trước ngưỡng JIT nên `ReplaceMethod` đơn thuần không bắt lần mua đầu. Adapter bind đúng một `MethodInfo` `TextObject.setPayloadText` ngay lúc class resolve (`fn` và `state=Compiled`), rồi khôi phục `ClassInfo.getMethodByIndex` nguyên bản. Các method khác không bị thay đổi.

Riêng collectible cần phản hồi server: adapter đợi 350 ms cho callback chờ phản hồi gốc hoàn tất, rồi xếp một SMS ảo vào port listener được mở bởi MIDlet. `SQMidlet` gọi `super(false,"SUQUAN","OLA","17530","36")`, nên prefix thực tế là `SUQUANOLA`, port thực tế là **17530**, thay cho các default `TESTGAME3`/19003 trong `an`. Adapter bắt port từ inbound `open0` để đi theo cấu hình. Năm native `open0`, `waitUntilMessageAvailable0`, `receive0`, `close0`, `send0` được thay bằng xử lý bộ nhớ. Listener chỉ được báo đúng một lần cho mỗi message, tránh busy loop trong thời gian `ct.run()` chờ game sẵn sàng. `receive0` truyền messageType 2 và buffer UCS2 vào packet; đường Java gốc tiếp tục qua `ct` → `an.a(String,String)` → `bv` → `bg.a` → RMS và UI.

Schema phản hồi:

```text
<PREFIX> a<1 hoặc 4> "lBộ sưu tập ảo trong bản web (không đổi thưởng thật)" cLOCAL-WEB-<N> rreco<N>
```

`a4` yêu cầu `bg.a` chọn ngẫu nhiên một trong các collectible khác loại mục tiêu bí mật. `a1` yêu cầu thuật toán gốc cấp loại mục tiêu và lưu mã cục bộ. Vì không có mã nguồn server để tái hiện tỷ lệ trúng thật, adapter dùng chính sách **mô phỏng** `a1` ở slot 3/7/9 và `a4` ở các slot còn lại, giúp bộ sưu tập có thể hoàn thiện. Đây là quy tắc tương thích offline, không phải bằng chứng về xác suất server gốc. Không có giá trị quy đổi hoặc nhận quà thật.

Mỗi `setPayloadText` hợp lệ là một operation riêng, kể cả payload lặp hệt nhau. Nếu runtime khác còn gọi `send0` ngay sau đó, adapter nhận biết cùng operation trong cửa sổ một giây để tránh ack kép. Không deduplicate toàn phiên, không gây kẹt khi đổi lại cùng slot. Request action/reason không hợp lệ không tạo ack; mọi native SMS khác bị từ chối bằng IOException. Không gọi lại implementation `mozActivity/websms` upstream.

## Kiểm tra

`node --test tests/offline-shop.test.cjs` — 7 tests pass: buffer UTF16BE; bind lần gọi cold đầu tiên và không thay method khác; shop/mạng giữ callback gốc; xác thực collectible và single notification; repeated purchase tạo ack riêng nhưng send cùng operation không kép; nhánh award thông thường; chặn gửi native bên ngoài; đóng connection giải phóng wait; prefix/port thực tế SUQUANOLA/17530.

Probe Chrome riêng: `node tools/offline-shop-probe.cjs`, output `analysis/offline-shop-probe.json` và các ảnh `analysis/offline-*.png`. Đã mua Long Thần Kiếm trên UI game gốc: số lượng 0→3; trước đó chọn Không ở hộp mua giữ số lượng và transaction counter ở 0. Sau khi mua, counter 1 và status miễn phí/không SMS xuất hiện trên trang chính. Probe giữ đầy đủ log các exception được Java bắt khi dò platform/RMS lần đầu; đó không phải lỗi page uncaught. Kiểm tra phản hồi collectible dùng fixture TextObject trong browser context tạm, không ghi vào tiến trình người dùng.

`node tools/offline-shop-probe.cjs --full-collection` đã kiểm chứng thêm toàn bộ vòng collectible: slot 0 từ rương xám thành Tượng Phật qua parser/callback/RMS/UI gốc; sau mười request fixture thu đủ bốn loại; nút Gửi gốc tạo request a5; đóng xác nhận gốc chạy callback `f` và reset bộ sưu tập để chơi tiếp. Kết quả cuối: 12 transactions (một shop, mười mở, một claim), 10 acknowledgments, 0 pending. Ảnh bằng chứng `offline-collectibles-before.png`, `offline-collectibles-ack.png`, `offline-collectibles-full.png`, `offline-collectibles-claim.png`, `offline-collectibles-reset.png`. Fixture chỉ thay bước kiếm vật phẩm trong bài kiểm tra; không thay thuật toán cấp/kiểm bộ/nhận/reset của ứng dụng.
