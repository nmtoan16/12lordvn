# Phân tích logic Loạn 12 Sứ Quân

Ngày phân tích: 20/10/2026. Đối tượng duy nhất: `Loan-12-Su-Quan.jar` trong thư mục dự án. Đây là **game Java ME ghép biểu tượng 8×8, chiến đấu theo lượt, kết hợp nhập vai và chiến dịch trên bản đồ**, không phải game hành động thời gian thực.

## 1. Bằng chứng, phạm vi và độ chắc chắn

- Đã giải mã ngược **167/167 class** bằng CFR 0.152; `decompiled/summary.txt` không báo phương thức thất bại. Các cảnh báo thiếu `javax.microedition.*` là API nền tảng Java ME không đóng gói trong JAR.
- Mã sau decompile ở `analysis/decompiled/`. Tên lớp/phương thức bị rút gọn trong JAR; tên vai trò bên dưới là diễn giải từ luồng gọi, không phải tên nguồn ban đầu.
- `static-data.json` là mảng nguyên bản lấy trực tiếp từ bytecode `<clinit>`, **không chạy ứng dụng**. Công cụ `tools/extract_static_data.py` chỉ hiểu lệnh hằng số, stack, dựng mảng và `putstatic`; gặp lệnh gọi/nhánh ngoài phạm vi sẽ dừng.
- `game-data.json` ánh xạ cùng dữ liệu thành trường có tên, phục vụ xây dựng web; giữ toàn bộ 77 nhân vật, 46 bước chiến dịch, 8 nhiệm vụ phụ, 41 điểm bản đồ, 43 tuyến đường, 9 phép, 6 vật phẩm và hội thoại tiếng Việt.
- Kết luận logic dưới đây dựa vào phân tích tĩnh. Không coi chúng là bằng chứng đã chơi hết 46 bước trên máy ảo. Hành vi thời gian/âm thanh và dịch vụ SMS cần kiểm tra runtime riêng.
- Manifest: tên MIDlet `Loan 12 Su Quan`; entry `com.mic.smsgame.suquan.SQMidlet`; MIDP-2.0, CLDC-1.0, phiên bản 1.0.0. Credits trong `com/mic/smsgame/suquan/h.java:16` ghi MicroGame 2010; vendor manifest của bản phân phối là GiaiTri321.Pro.

SHA-256 chuẩn được ghi trong hai JSON dữ liệu để tái kiểm tra đúng artifact.

## 2. Bản đồ lớp và luồng màn hình

| Lớp | Vai trò được xác nhận | Điểm vào/bằng chứng |
|---|---|---|
| `com/mic/smsgame/suquan/SQMidlet` | Entry game, màu UI, cấu hình 3 mạng | `SQMidlet.java:14`, `:31` |
| `com/mg/smsgame/main/MGMIDlet` | Lifecycle MIDlet, màn hình, đăng ký/nhận SMS | `MGMIDlet.java:30`, `:110`, `:127` |
| `cn` | Logo nhà phát triển, partner, thiết lập viewport 240×320 | `cn.java:33`, `:48`, `:91` |
| `cz` | GameCanvas, input, vòng lặp khoảng 40 ms/tick | `cz.java:130` |
| `com/mic/smsgame/suquan/y` | Điều hướng các màn hình theo ID | `y.java:53` |
| `com/mic/smsgame/suquan/ac` | Menu chính và hướng dẫn | `ac.java:76`, `:329`, `:347` |
| `com/mic/smsgame/suquan/z` | Chọn 6 avatar, nhập tên 2–10 ký tự | `z.java:35`, `:181` |
| `df` | Cuộn cốt truyện mở đầu/kết thúc | `df.java:58`, `:148` |
| `com/mic/smsgame/suquan/aa` | Bản đồ, đi đường, event, quests, save map | `aa.java:149`, `:503`, `:653` |
| `com/mic/smsgame/suquan/ab` | Tạo/khôi phục màn chiến đấu | `ab.java:22` |
| `cq` | Bàn cờ, danh sách nước hợp lệ, hai đấu sĩ | `cq.java:23`, `:258`, `:324` |
| `bu` | State machine trận đấu, cascade, phép, vật phẩm, thắng/thua | `bu.java:279`, `:450`, `:780` |
| `bn` | HP, sức công, mana, lương thực, counters từng đấu sĩ | `bn.java:58`, `:147`, `:211` |
| `i`, `bm`, `bs` | Vẽ trận, sprite bàn cờ, thư viện hình/âm thanh | `i.java:74`, `bm.java:22`, `bs.java:59` |
| `ac` (default package) | Điều khiển người chơi | `ac.java:11`, `:46` |
| `t` (default package) | AI chọn nước, tiêu lương, dùng phép | `t.java:21` |
| `w` (default package) | Tutorial có trình tự và nước cờ cố định | `w.java:15`, `:31` |
| `com/mic/smsgame/suquan/d` | Bàn tutorial dựng sẵn | `d.java:14` |
| `dd`, `bq`, `ea` | Bảng chiến dịch/chỉ số, bản đồ, toàn bộ hội thoại | Các static initializer và `dd.java:590` trở đi |
| `bg`, `dr`, `com/mic/smsgame/suquan/f` | RMS lưu trạng thái, tiền, vật phẩm, resume | `bg.java:8`, `f.java:17` |
| `co` | Đấu trường, chọn cấp luyện tập và trả vàng | `co.java:43`, `:96` |
| `com/mic/smsgame/suquan/q` | Cửa hàng sáu vật phẩm | `q.java:22`, `:174` |
| `dc`, `com/mic/smsgame/suquan/m` | Bộ sưu tập 10 ô và đổi thưởng | `dc.java:280`, `m.java:18` |
| `an`, `dm`, `bc`, `ct` | Giao thức mua/nhận thưởng qua SMS | `an.java:36`, `dm.java:38` |

Router `y.d` dùng ID: 1 lớp splash framework; 6 splash game; 2 menu; 3 bộ sưu tập; 4 cửa hàng; 8 tạo nhân vật; 7 bản đồ; 5 trận đấu. Người chơi mới: menu → intro → chọn avatar/đặt tên → Hoa Lư → hội thoại → tutorial → bản đồ/chiến dịch. Continue khôi phục snapshot trận nếu record 106 tồn tại, nếu không mở map (`ac.java:349`).

Menu có Tiếp tục, Chơi mới, bộ sưu tập, cửa hàng, thông tin; trang thông tin có tặng game, Chat Ola, game khác, hướng dẫn và tác giả. Những mục liên quan dịch vụ là tích hợp phân phối thời Java ME, không phải gameplay offline.

## 3. Bàn cờ và luật ghép

- Bàn chơi thật **8 hàng × 8 cột**, mỗi ô 21 px, sprite 20 px. Ma trận model 12×12 có vành bảo vệ; phần chơi chỉ số `[2..9][2..9]` (`cq.java:26`, `bm.java:37`, `:81`). Vùng cờ được vẽ tại x=46, y=64 trên viewport chuẩn 240×320 (`i.java:83`).
- ID 0 kiếm; 1 bánh chưng/lương thực; 2 tim; 3 âm dương/năng lượng; 4 vàng; 5 sách EXP; 6 kiếm lửa; 7 ô trống. Các ID 8–11 là sentinel, không phải quân cờ. Bảng chuẩn hoá `e.i[6]=0` cho kiếm lửa ghép cùng kiếm thường (`com/.../e.java:30`).
- Chỉ hoán đổi hai ô cạnh nhau. Nước hợp lệ khi ít nhất một vị trí sau hoán đổi tạo dãy ngang hoặc dọc ≥3. `cq.c` thử swap, đo bốn dãy, trả lại swap và cung cấp mô tả nước (`cq.java:258`).
- Người chơi chọn ô bằng 5/OK rồi hướng; chạm ô hoặc vuốt/đi sang ô cạnh tương ứng. Con trỏ chưa chọn được wrap ở biên; ô đang chọn chỉ được swap trong vùng cờ (`cq.java:161`, `ac.java:46`).
- Swap sai có animation đi-về, **tự mất `3 + ATK` HP** rồi đổi lượt, có xét bảo vệ/giảm sát thương (`bu.java:646`).
- Sau loại bỏ: dồn xuống theo cột → sinh quân phía trên → tìm dãy mới trong những ô đã thay đổi → xử lý cascade đến khi ổn định (`bu.java:1052`, `:1112`). Không còn nước hợp lệ sẽ dựng bàn mới, không kết thúc trận (`bu.java:467`).
- Bộ sinh ban đầu chủ động tránh dãy sẵn và cài ít nhất một thế ghép được (`com/.../w.java:12`). Khi refill, rút đều trong 6 loại chính; nếu là kiếm, xác suất 11% đổi thành kiếm lửa (`com/.../w.java:114`). Vì vậy xác suất kiếm lửa của mỗi ô sinh mới là 11%/6, không phải 11% mọi ô.
- Khi một kiếm lửa bị loại, thêm một sự kiện nổ; nó loại tám láng giềng và có thể kéo tiếp kiếm lửa khác. Các ô nổ vẫn cho tài nguyên/sát thương (`bu.java:674`).

## 4. Công thức tính điểm, tài nguyên và lượt

Ký hiệu `L = max(chiều rộng, chiều cao)` của nhóm được ghi nhận, `S = L² − 2L`, nếu S<0 thì S=1; `D` là số wave/cascade, bắt đầu 1 và tăng ở mỗi lượt resolve. Các phép loại từng ô tạo nhóm 1×1 nên S=1. Code xử lý từng nhóm ngang/dọc; không được gộp cả giao chữ T thành một dãy dài nếu muốn tương đương gốc.

| Nhóm | Tác dụng |
|---|---|
| Kiếm/kiếm lửa | Địch mất `S×D + ATK` HP mỗi nhóm |
| Tim | Hồi `S×D + floor(min(ATK hai bên)/2)` HP |
| Bánh chưng | Cộng `S×D + 240` đơn vị lương thực |
| Âm dương | Cộng `S×D` năng lượng, chặn tại 36 |
| Vàng | Cộng `S×D` vàng chiến đấu |
| Sách | Cộng `S×D` EXP chiến đấu |

Nguồn: `bu.java:403–448`, `cq.java:30–38`, `bn.java:167`. Ví dụ L=3/4/5 cho S=3/8/15; cascade thứ hai nhân đôi phần S, không nhân đôi hạng ATK hay phần hồi phụ.

Mỗi nhóm có một chiều ≥4 tăng một lượt thưởng (`bu.java:1172`). Tăng lượt có thể tích lũy qua cascade. Hết các lượt thưởng mới chuyển người. Những hiệu ứng shield/frozen và vật phẩm có bộ đếm giảm ở mỗi lần đi vào state7, kể cả các lượt được thưởng (`bu.java:569`).

Lương thực tối đa ban đầu của mỗi bên là `floor(max(maxHP hai bên)/min(ATK hai bên)) × 3 × 120` (`cq.java:35`). Chỉ bên đang suy nghĩ/đi cờ tiêu hao: người chơi -1/tick, AI -120 khi chọn nước (`ac.java:101`, `t.java:31`). Nếu giữ nhịp gốc 25 tick/s thì tương ứng lương thực của người chơi chạy theo thời gian; không được thay thành chỉ trừ một lần mỗi lượt. Khi lương thực ≤0, bên đó thua (`bu.java:370`).

Thắng/thua thường theo HP hai bên ≤0; tutorial có bảo vệ và kết thúc bằng kịch bản. Đấu trường mode2/3 còn có đường quyết định kết quả khi cờ state thời gian đạt1 (`bu.java:1026`).

## 5. Chỉ số, thăng cấp, AI

Mỗi cấu hình nhân vật trong `dd.o` có `[level,HP cộng thêm,ATK cộng thêm,...skillIds]`; JSON ngữ nghĩa đã tính maxHP và attack ban đầu:

- `maxHP = 100 + 5×level + HP cộng thêm`.
- `ATK = floor(level/2) + 5 + ATK cộng thêm`.
- Ngưỡng EXP tổng để đạt cấp n: 0 nếu n≤1, còn lại `16×n² − 25`.
- Level-up tăng maxHP, bù lượng HP tăng thêm vào HP hiện tại, tính lại ATK (`dd.java:795–836`, `c.java:14`).

Có 6 avatar đầu tương ứng nam/nữ của ba hệ. IDs0/3 lửa `[0,1,2]`; IDs1/4 sét `[3,4,5]`; IDs2/5 băng `[6,7,8]`. Nhân vật lịch sử IDs6–41, quái thường42–73, boss Ấu Rồng74, lính tuỳ cấp75, Lính Tiên Du76. Boss Kiều Thuận cấp99 có HP cộng500, ATK cộng20; Ấu Rồng cấp96 có HP cộng1000, ATK cộng40. Không nên dùng một cấu hình địch chung cho toàn bộ chiến dịch.

AI duyệt mọi nước hợp lệ và chấm điểm: kiếm9, kiếm lửa10, tim4, lương3, mana2, vàng/sách1; cộng bảng điểm theo độ dài `[1,1,1,1,20,40,60,80,100]`. Tim/lương tăng ưu tiên khi ≤30% và ≤10%; nước có thể kết liễu được 120 điểm. Có yếu tố chọn sai/ngẫu nhiên theo cấp (70% dưới30,50% dưới60,30% từ60; `bn.java:60`, `bu.java:1350`, `:1567`). AI chờ khoảng1s, tiêu120 lương; nếu đủ ít nhất một cấp phép thì 50% khả năng dùng ngẫu nhiên phép đã mở (`t.java:21–93`).

EXP ngoài thưởng chiến dịch và sách: cơ sở `floor(enemyLevel²×0.2)+10` với ID<42, với quái ID≥42 là `floor(enemyLevel²×0.1)+10`. Nếu địch thấp hơn người chơi hơn5 cấp thì EXP0; cao hơn hơn5 cấp thì lấy một nửa cơ sở. Chênh0/âm lấy cơ sở; chênh1..5 cộng tỷ lệ chênh5% mỗi cấp và thưởng theo HP còn nếu >70% (`dd.java:838–866`). Thua có phạt EXP, không hạ xuống dưới ngưỡng cấp hiện tại (`dd.java:868–877`).

## 6. Chín phép và vật phẩm

Mana cost theo **vị trí slot** 1/2/3 là12/24/36; ID kỹ năng có thể bị lặp trong cấu hình boss, nên không suy chi phí từ ID (`bu.java:780–796`). Mọi ô bị phép huỷ vẫn đi qua resolve/thu tài nguyên, ngoài sát thương trực tiếp dưới đây.

| ID | Phép | Tác dụng từ code |
|---|---|---|
| 0 | Quả Cầu Lửa | ATK sát thương; phá ngẫu nhiên vùng3×3 |
| 1 | Mưa Thiên Thạch | floor(1.5×ATK); phá3–5 vùng2×2 |
| 2 | Lửa Địa Ngục | floor(2.5×ATK); phá2 vùng4×4 |
| 3 | Chuỗi Sét | floor(0.8×ATK); chọn một loại quân rồi phá ngẫu nhiên4–8 ô loại ấy, giới hạn số ô đang có |
| 4 | Khiên Sét | Miễn sát thương, chặn mất mana do địch; cộng6 bộ đếm hiệu lực |
| 5 | Sấm Sét | 2×ATK; phá3–5 vùng3×3 |
| 6 | Mũi Tên Băng | floor(0.8×ATK); địch mất8 mana nếu không khiên; phá2–3 ô khác nhau |
| 7 | Cam Lộ Thủy | Hồi `floor(maxHP/10)×2` HP; phá tất cả tim |
| 8 | Băng Phong | floor(1.5×ATK); địch mất18 mana; phá3–5 ô; nếu địch không khiên, cộng2 lượt cho người cast, thêm bộ đếm frozen |

Nguồn code thực thi: `bu.java:780–1003`; mô tả UI ở `com/.../h.java:11`. Khi code và mô tả khác nhau (chẳng hạn Sấm Sét UI nói3–6 vùng), khi tái tạo chính xác phải theo code3–5. Các số nguyên dùng chia kiểu Java, làm tròn xuống với giá trị không âm.

| ID | Vật phẩm | Hành vi |
|---|---|---|
| 0 | Long Thần Kiếm | ATK×2 cho lượt; counter buff1 |
| 1 | Nhân Sâm | maxHP của trận=2×maxHP cơ bản; hồi thêm một maxHP cơ bản, một lần/trận |
| 2 | Ngân Lượng | Số vàng persistent; không phải nút dùng giữa trận |
| 3 | Quỷ Diện Giáp | Nhận floor(sát thương/4); counter buff2 |
| 4 | Bình Thuốc | Hồi floor(maxHP/10); giới hạn dùng vật phẩm trong một lượt |
| 5 | Ngọc Ấn | Tự tiêu một cái trước trận để chắc chắn được đi trước |

`bn.java:147` nhân ATK, `:215` giảm sát thương, `bu.java:1417` áp hiệu ứng; `aa.java:800`, `co.java:103` dùng Ngọc Ấn. Không có ấn, cấp cao hơn đi trước; bằng cấp tung ngẫu nhiên.

Shop mua gói3 cho các vật phẩm thường; giáp1000 vàng/gói, thuốc250, ấn500. Kiếm/nhân sâm/vàng gọi SMS thay vì mua bằng vàng. Gói vàng1000. Nguồn `com/.../q.java:22–29`, `:186–234`.

Ghép≥5 có xác suất rơi vật phẩm chứ không bảo đảm như hướng dẫn UI. Đặt K=(L−5)²×15; kiểm tra rơi `random100 <40+K`; lượt ngẫu nhiên thứ hai chọn hiếm/phổ thông theo các ngưỡng10+K,25+K,45+K,65+K. Loại có thể rơi1/0,3,5,4; không rơi2 (`bu.java:742`).

## 7. Bản đồ, chiến dịch và nhiệm vụ phụ

Bản đồ có41 điểm (27 điểm có tên,14 điểm trung gian không có tên),43 cạnh; toạ độ, loại node, thứ tự cạnh lân cận nằm trong `bq.b/c/d`. Đường mở khi cả hai đầu đã mở (`aa.java:208`). Mới chơi mở Hoa Lư; event đầu ở Hoa Lư. Bản đồ cuộn với viewport, nhân vật bước theo vector cố định2 pixel/tick (`aa.java:779–793`).

Mỗi bước `dd.l[i]` là `[mode,locationId,enemyId,xpReward,goldReward,collectibleFlag]`: mode0 chỉ hội thoại, mode1 tutorial, mode2 chiến đấu. `dd.m[i]` danh sách node được mở sau khi hoàn thành. Thứ tự i0..45 là tiến trình chính; điểm bản đồ chứa event hiện tại được cập nhật sau mỗi chiến thắng/đối thoại (`aa.java:632`). Mảng `ea.b/c/d` là hội thoại trước trận/sau thắng/sau thua. Độ dài các mảng khác nhau, không được đọc ngoài cuối mảng khi event không có lời thoại.

Các mốc: 0 luyện với Lưu Cơ;4 Sơn Tặc/Tam Điệp;8 Ngô Xương Xí/Bình Kiều;10 Ngô Nhật Khánh/Đường Lâm;15 Lã Đường/Tế Giang;18 Đỗ Cảnh Thạc/Đỗ Động;23 Nguyễn Siêu/Đông Phù Liệt;28 Lý Khuê/Dương Xá;30 Nguyễn Thủ Tiệp/Tiên Du;33 Lữ Xử Bình/Cổ Loa;36 Nguyễn Khoan/Tam Đái;41 Kiều Công Hãn/Hồi Hồ;42 Kiều Thuận/Hồi Hồ;44 Ấu Rồng/Hoa Lư;45 hội thoại xưng đế và kết thúc. Các event xen giữa là hành quân, quân lính hoặc hội thoại; toàn bộ chi tiết phải lấy từ JSON thay vì suy từ các mốc này.

Flag1/2 trong event liên quan **thưởng ô bộ sưu tập** (flag2 là món cuối), không phải cờ bắt buộc để thắng trận. Người chơi được hỏi mở món bằng dịch vụ SMS; từ chối không chặn tiếp tục chiến dịch (`aa.java:563`, `:870`).

8 sidequests `dd.n`: `[mode,locationId,enemyId,ATK thưởng vĩnh viễn,HP thưởng vĩnh viễn]`. Gồm Đông Lỗ/Ma Nhãn(+10HP), Bảo Thái/Lê Hoàn(+3ATK), Chùa Bảo Thái/Nguyễn Minh Quang(+20HP), Bình Lục/Nguyễn Ninh(+2ATK,+20HP), Ái Châu/Bùi Quang Dũng(+1ATK,+10HP), Trà Hương/hội thoại(+3ATK), Chùa Non Nước/hội thoại, Tam Đảo/Nhện Tinh(+3ATK,+50HP). Thắng loại bỏ quest trên node và tính lại chỉ số (`aa.java:572–588`).

Các node trung gian có quân địch một lần trong `dd.p`. ID75 lấy cấp gần người chơi: `max(1,playerLevel-random(6))`. Quái dọc đường chọn ID43..73 với cấp ngẫu nhiên theo min/max của đường (`dd.java:879`). Bộ đếm số chặng ngẫu nhiên3..10 quyết định lúc encounter; trừ một mỗi lần đi đường (`aa.java:187`, `:795`, `:909`).

Đấu trường tại Tam Điệp mở sau đánh Sơn Tặc. Có5 bậc đối thủ `[Lưu Cơ,Nguyễn Bặc,Lê Hoàn,Nguyễn Ninh,Đinh Bộ Lĩnh]`; level lệch `[-5,0,5,10,20]`, phí `[50,80,140,230,500]` vàng, thưởng `[5,10,20,40,100]%` khoảng EXP giữa cấp hiện tại và kế tiếp. Hai bậc đầu mở mặc định; thắng Lê Hoàn, thắng Nguyễn Ninh và đạt cấp30 lần lượt mở bậc tiếp (`dd.java:957`, `co.java:43`, `aa.java:577`, `:608`). Trước trận đấu trường lưu HP bản đồ và phục hồi HP đầy cho trận; sau đó trả HP bản đồ (`aa.java:854`, `dd.java:929–951`).

## 8. Save/resume và mô hình mạng chơi

RMS có hai store tên `16b` và `161b` (`bg.java:8`). Wrapper `dr` đóng gói các record logic thành dữ liệu RMS. Record logic quan trọng:

| Store / key | Nội dung |
|---|---|
| 16b /100 | Snapshot bản đồ |
| 16b /101 | Kết quả đang chờ xử lý:0 idle,1 đang trận,2 thắng,3 thua |
| 16b /103 | EXP tổng |
| 16b /104 | Tên nhân vật UTF |
| 16b /105 | Avatar ID |
| 16b /5 | Mạng chơi hiện có |
| 161b /9 | Số lượng6 vật phẩm, vàng ở index2 |
| 161b /106 | Snapshot resume trận |
| 161b /1,2,3 | Bộ sưu tập, mã đổi thưởng và nội dung giải thưởng |

Map snapshot gồm node hiện tại, node đích, event index, cờ mở từng node, event/sidequest/encounter còn lại, cạnh mở, vị trí đang đi, các thông số nhân vật, HP, bonus vĩnh viễn và bậc đấu trường (`aa.java:227–323`).

Battle snapshot gồm event index; hai cấu hình nhân vật;64 ô; con trỏ; HP/maxHP, mana, lương thực, vàng/EXP của mỗi bên; các buff; trạng thái shield/frozen; lượt thưởng; cờ đã dùng vật phẩm. Chỉ ghi khi controller ở state8 ổn định, buffer400 byte (`bu.java:97`). Resume đọc rồi xoá record106 trước khi tạo model (`com/.../ab.java:26`). Như vậy để giữ đủ tính năng trên web phải có cả map save lẫn battle resume; chỉ save level/event index chưa tương đương.

Game mới cấp3 mạng (`SQMidlet.java:32`, `bg.java:57–85`). Bắt đầu trận trừ1 (`dd.java:920`), thắng hoàn1 (`dd.java:926`), tạm dừng/save trận hoàn1 (`bu.java:126`). Thua mất mạng và có thể bị phạt EXP. Khi hết mạng, nút Đánh bị chặn bởi hộp thoại mua mạng qua SMS (`aa.java:1123`).

## 9. SMS, phân phối và điều kiện chặn

- Game khởi động đăng ký push `sms://:17530` và cài MessageListener (`MGMIDlet.java:127–161`, `dm.java:38`). Đây là API Java ME Wireless Messaging; listener native không được giải quyết bằng vòng lặp tức thì nếu chạy máy ảo web, vì sẽ gây bận CPU hoặc hết bộ nhớ.
- Chuỗi lệnh gốc mang tiền tố SUQUANOLA, distributor, platform, timestamp; gửi mua mạng, vật phẩm, mở bộ sưu tập, đổi thưởng, tặng game (`an.java:36–108`). Nội dung và kết quả phụ thuộc nhà cung cấp không nằm trong JAR.
- Checksum cấu hình `al.a(true)` kiểm MD5 của `0065557`; kết quả `3ef9f437ab2ed01315d84fc1d7c6718a` khớp `an.m`. Do đó **mẫu JAR này không đòi SMS kích hoạt trước khi vào menu/tạo game**, nhưng mạng chơi và hàng premium phụ thuộc SMS về sau. Chế độ chống emulator còn theo dõi RMS/send count (`al.java:24–91`).
- Điều này là bằng chứng về ứng dụng cục bộ, không chứng minh dịch vụ SMS hoặc các URL cũ còn tồn tại vào2026. Không có server/mã xác nhận thật được phục dựng từ client. Web không nên tự động gửi SMS hay gọi URL thanh toán cũ.

## 10. State machine trận chi tiết

`bu.v` và `f(state)`:

| State | Công việc |
|---|---|
|0|Sinh bàn/animation ban đầu|
|1|Animation swap hợp lệ, cập nhật model|
|2|Trả swap sai, phạt HP|
|3|Ghi nhóm bị phá, kiếm lửa/nổ, tăng cascade, thu tài nguyên|
|4|Rơi quân/refill|
|5|Tìm match mới từ quân đổi vị trí|
|6|Kiểm thắng/thua và nước hợp lệ; dựng lại bàn nếu bế tắc|
|7|Giảm buff, xét bonus turn/chuyển lượt, reset cascade|
|8|Chờ input/người chơi hoặc AI|
|9|Animation kết quả|
|10|Chuyển tiếp dựng lại bàn|
|11|Hoàn animation phép rồi resolve|
|12|Đồng bộ HP/vàng/EXP và dialog kết quả|
|13–14|Tutorial: chọn bước, chỉ dẫn, chờ thao tác|

Nguồn `bu.java:279–393`, `:450–740`. `com/.../ab.java` gọi update controller rồi update renderer mỗi frame. `bu.t()` commit kết quả và xoá snapshot khi rời trận (`bu.java:1508`).

## 11. Điều còn phải kiểm chứng runtime khi tái tạo

1. Vượt đầy đủ chiến dịch, các nhánh nhiệm vụ phụ và đấu trường để đối chiếu UI/animation với phân tích tĩnh.
2. Nhịp frame, ngắt/restart âm thanh, trường hợp background tab và ánh xạ softkey trên điện thoại.
3. Resume đúng lúc tạm dừng, quay menu, reload tab, migrate save và trạng thái hết mạng.
4. Các khác biệt code/mô tả đã nêu: drop≥5 có xác suất, Sấm Sét3–5 vùng, thời gian shield theo bộ đếm turn-state.
5. Lớp JAR bị obfuscate, decompile không phải source buildable nguyên trạng. Không thể tuyên bố đã phục hồi project Java gốc hoặc backend dịch vụ chỉ từ artifact này.

Với yêu cầu giữ nguyên toàn bộ đồ hoạ và logic, chạy bytecode gốc qua runtime Java ME trên web là phương án giữ chính xác hơn việc vẽ lại sprite hoặc viết mới tất cả điều kiện từ mô tả. Các JSON và bản đồ lớp ở đây cung cấp nền tảng để kiểm thử, sửa tương thích runtime hoặc xây dựng bản port độc lập có đối chiếu.
