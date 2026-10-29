# Nguồn và thành phần

- **Loạn 12 Sứ Quân**: toàn bộ bytecode, lời thoại, bản đồ, hình và âm thanh từ `Loan-12-Su-Quan.jar` do người dùng cung cấp. Credits trong game: MicroGame (2010); manifest bản phân phối ghi GiaiTri321.Pro. Các tài nguyên này không được đổi giấy phép thành giấy phép runtime.
- **J2meJS**: [Rosabis/J2meJS](https://github.com/Rosabis/J2meJS/tree/75947c10412c7ff9927835a128b653696f402e9d), commit `75947c10412c7ff9927835a128b653696f402e9d`, GPL-2.0. Runtime cục bộ ở `runtime/`; giấy phép ở `runtime/LICENSE`. Dự án dựa trên Mozilla PluotSorbet/j2me.js; các thông báo bản quyền trong mã và thư viện được giữ nguyên. Bản nguồn upstream đầy đủ có tại `tools/J2meJS` trong workspace và tại liên kết commit nêu trên.
- **Thư viện âm thanh đi kèm upstream**: WebAudio TinySynth, BenzAMRRecorder; encoding.js và các polyfill giữ nguyên thông báo bản quyền trong tệp. TinySynth tổng hợp MIDI trên trình duyệt; âm sắc có thể khác điện thoại đời cũ dù dữ liệu MIDI không đổi.
- **Emoji upstream**: các ảnh `runtime/style/emoji/` theo CC-BY 4.0, ghi công Twitter, Inc. và cộng tác viên; xem `runtime/style/emoji/LICENSE`. Game này dùng font bitmap gốc thay vì emoji.
- **REA**: [morluto/rea](https://github.com/morluto/rea), skill đã cài để kiểm kê, nhận dạng và trích xuất JAR. REA không cung cấp decompiler Java chuyên biệt trong session; bytecode Java được đọc thêm bằng CFR 0.152, mảng hằng lấy bằng parser không thực thi game.
- **CFR**: [leibnitz27/cfr](https://github.com/leibnitz27/cfr), MIT; công cụ phát triển trong `tools/cfr-0.152.jar`, không cần cho website khi chạy.
- **Playwright**: [microsoft/playwright](https://github.com/microsoft/playwright), Apache-2.0, chỉ dùng kiểm thử.

Các thay đổi runtime cho bản web được ghi ở `runtime/PATCHES.md`. HTML/CSS/JavaScript của lớp giao diện và adapter trong dự án sử dụng GPL-2.0 theo runtime tích hợp.

Ver2 giữ nguyên các ghi công và giấy phép trên. Công cụ REA/CFR/upstream đầy đủ ở workspace phát triển cha (`../tools/`), không nằm trong gói website. Mã JavaScript runtime đã sửa và `runtime/LICENSE` đi cùng gói phát hành. Biểu tượng trang bị tái sử dụng sprite đã bóc tách; phần HUD/VFX bổ sung vẽ bằng canvas/CSS, không thêm bộ ảnh bên thứ ba.
