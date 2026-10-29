# Điểm nối JVM ver2

Registry `runtime/java-hooks.js` chạy observer tại đầu frame interpreter và lệnh return thật, không bọc một hàm Java đang yield bằng JS `finally`. Frame được giữ theo thread/frame pointer; callback đầu vào không lặp khi tiếp tục I/O. Các method được quan sát giữ chế độ interpreter; các method khác vẫn JIT. Đây là lựa chọn có chủ đích để không đánh mất continuation hoặc bị bản biên dịch cache bỏ qua observer.

Khi Java exception thực sự tháo frame, registry discard entry trước `popFrame`; không chạy callback return cho method đã ném lỗi. Regression kiểm fp được tái sử dụng với args mới, trong khi catching frame và frame đang suspend vẫn giữ nguyên.

Replacement chỉ dùng cho leaf có thể hoàn tất đồng bộ (SMS payload, vẽ quảng cáo); registry gắn ngay khi MethodInfo được resolve, gồm lần gọi cold. Không thay JAR.

`java-port.js` tạo Context/Thread trong cùng Runtime ứng dụng, đẩy frame Java và chạy scheduler bình thường. Observer return của frame gốc chụp kết quả trước GC; Context kết thúc mới resolve Promise. Java RMS I/O được phép yield. Không gọi Java từ DOM trên một stack cũ.

Đã kiểm bằng unit registry, SMS regression và Java port thật: key thử nghiệm 124 được ghi/flush/đọc/xóa mà record9 giữ nguyên. Cold/warm startup chuyển đúng một lần; 20 lượt khởi động gồm 10 lượt CPU throttle 4×. Baseline trước adapter: 16 unit +12 browser đạt. Ma trận kiểm cuối nằm trong `VER2-VERIFICATION.md`.

Startup: thay `cn.d/e/a` để không vẽ quảng cáo/partner, đặt `o=false,q=1` để bỏ timer nhưng giữ vòng đợi `MGMIDlet.d`. Giữ `cn.b`/khởi tạo viewport và `cx.F` nguyên bản.

Các điểm nối gameplay:

| Chữ ký / đối tượng | Trách nhiệm |
|---|---|
| `q.b(II)` trong package game | Cho ba vật phẩm tiêu hao xác nhận mua thiếu vàng; giữ callback và số lượng gốc |
| `f.a()` trong package game | Chơi mới đã xác nhận mới reset hồ sơ trang bị |
| `bu.a([BI)` | Bắt đầu hoặc phục hồi trận; chốt ba món/bậc và đọc sidecar |
| `bu.a()` / `bu.t()` | Retry với buff đầu trận mới; dọn checkpoint khi kết thúc |
| `bn.k/l/a/f/j` | Damage, heal, mana và lương; phân biệt side và nguồn |
| `bu.x()` / `i.a(cc,int)` | Group/cascade, thưởng board và drop đúng bốn ô |
| `bu.a(int)` | Xác thực mana, chín phép, refund/lượt thưởng và proc theo cast |
| `f.a(byte[],int,int)` | Lưu snapshot106 rồi sidecar121 gắn fingerprint chính xác |

Không hard-code địa chỉ heap. Adapter tra field/method theo metadata Java; `validate()` kiểm toàn bộ chữ ký trước khi cho thao tác trang bị. Combat thuần không gọi Java và renderer không tính lại combat. Java port kiểm dữ liệu đã commit thật, chặn ghi sau lỗi cho tới reload (xem `VER2-DATA.md`).

Hai tương thích startup có phạm vi hẹp: platform Sony `JP-8.0` giữ nhánh `cx.D=false`; lời gọi nội bộ Display(null) trong quá trình class-init trả reference0 có kiểm điều kiện. Bỏ log lỗi Java bị bắt hợp lệ trước unwind; lỗi không được xử lý vẫn đi qua đường báo lỗi của VM.
