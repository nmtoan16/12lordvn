export const TITLE='BIỆT ĐỘI DƯ QUANG';
export const TILES=[
 {id:0,name:'Xung kích',short:'Xung',color:'#ffb36c',icon:'pulse',help:'Mỗi ô gây sát thương theo Công. Ghép 4+ tạo Ô Xung Tuyến.'},
 {id:1,name:'Năng lượng',short:'Năng',color:'#78b9ff',icon:'flux',help:'Mỗi ô +3 năng lượng kỹ năng.'},
 {id:2,name:'Sinh lực',short:'Sinh',color:'#f48ca2',icon:'heart',help:'Mỗi ô hồi 7 HP.'},
 {id:3,name:'Lá chắn',short:'Khiên',color:'#b6a2fa',icon:'shield',help:'Mỗi ô +6 lá chắn, tối đa 100.'},
 {id:4,name:'Pin hỗ trợ',short:'Pin',color:'#77e7bd',icon:'cell',help:'Mỗi ô +12 pin. Mỗi hành động tốn 6 pin.'},
 {id:5,name:'Tín dụng',short:'Vàng',color:'#f3d77b',icon:'credit',help:'Mỗi ô +8 tín dụng, nhận khi thắng.'},
 {id:6,name:'Dữ liệu',short:'EXP',color:'#7ddce8',icon:'star',help:'Mỗi ô +4 EXP, nhận khi thắng.'}
];
const skill=(name,cost,text,effect)=>({name,cost,text,...effect});
export const HEROES=[
 {id:'lyra',name:'LYRA',title:'Khai Quang',role:'Xung kích',color:'#6bead4',hp:320,attack:10,
  passive:'Xung kích gây thêm 15% sát thương.',
  subPassive:'Dự bị: +7.5% sát thương Xung kích.',
  assist:{name:'Trảm Xung Nhập Trường',short:'Chém 10+ST khi đổi vào'},
  bio:'Kỹ sư cứu hộ tại cảng Sao Trầm. Lyra biến giáp lặn thành bộ dẫn quang, dùng hai lưỡi sáng để cắt những mạch phong tỏa.',
  skills:[
   skill('Cắt quang',10,'Gây 36 (+Công) sát thương. Phá một hàng, thu tài nguyên.',{short:'36+ST · Phá 1 hàng',damage:36,scale:1.8,clear:'row'}),
   skill('Lõi vượt tải',18,'Gây 62 (+Công) sát thương, đốt 8 HP trong 3 lượt và tạo 1 Ô Quá Tải.',{short:'62+ST · Đốt 3 lượt',damage:62,scale:2.2,burn:3,special:1}),
   skill('Đường chân trời',28,'Gây 100 (+Công) sát thương (+25% nếu địch có Khiên). Phá hai hàng, +20 lá chắn.',{short:'100+ST · Phá 2 hàng · +20 Khiên',damage:100,scale:2.8,clear:'double',shield:20,shieldBreak:1.25})
  ]},
 {id:'sora',name:'SORA',title:'Từ Triều',role:'Khống chế',color:'#ffbe7d',hp:330,attack:9,
  passive:'Bắt đầu trận với 12 lá chắn.',
  subPassive:'Dự bị: Đầu trận +6 lá chắn cho đội.',
  assist:{name:'Nhiễu Sóng Tức Thời',short:'Hút 5 NL địch & +4 NL đội'},
  bio:'Hoa tiêu của đoàn tàu trên không. Những dải đồng quanh Sora dẫn trường từ, kéo vũ khí địch khỏi quỹ đạo mà không cần chạm tay.',
  skills:[
   skill('Lệch quỹ đạo',10,'Gây 30 (+Công) sát thương, rút 8 năng lượng địch và nạp +4 năng lượng.',{short:'30+ST · Rút 8 NL địch',damage:30,scale:1.6,drain:8,manaGain:4}),
   skill('Lồng cảm ứng',18,'Gây 35 (+Công) sát thương, choáng đối thủ 1 lượt và +12 lá chắn.',{short:'35+ST · Choáng 1 lượt',damage:35,scale:1.8,stun:1,shield:12}),
   skill('Triều thiên cực',28,'Gây 85 (+Công) sát thương. Thu mọi ô lá chắn và tạo Lõi Siêu Tân Tinh.',{short:'85+ST · Thu mọi ô Khiên',damage:85,scale:2.5,clear:3,special:3})
  ]},
 {id:'nham',name:'NHAM',title:'Thạch Tâm',role:'Hộ vệ',color:'#b5d6ad',hp:390,attack:8,
  passive:'Giảm 12% sát thương lên HP.',
  subPassive:'Dự bị: Giảm 6% sát thương lên HP.',
  assist:{name:'Dựng Tường Chắn',short:'Cộng ngay +14 Khiên'},
  bio:'Một người thợ mỏ mang lớp vỏ khoáng sống. Nham càng giữ được bình tĩnh, các tinh thể trên vai càng cộng hưởng thành tường phòng hộ.',
  skills:[
   skill('Vách sống',10,'Nhận 38 (+Công) lá chắn và hồi 15 HP.',{short:'+38 Khiên · Hồi 15 HP',shield:38,heal:15}),
   skill('Chấn địa',18,'Gây 50 (+35% Khiên đang có) sát thương. Phá một vùng 3×3.',{short:'50+Khiên ST · Nổ 3×3',damage:50,scale:1.8,shieldSlam:.35,clear:'area'}),
   skill('Lời thề đá',28,'Nhận 75 lá chắn, phản 35% sát thương trong 3 lượt và hồi +15 Pin.',{short:'+75 Khiên · Phản đòn 3 lượt',shield:75,reflect:3,batteryGain:15})
  ]},
 {id:'da',name:'DẠ',title:'Ảnh Tuyến',role:'Đột kích',color:'#c1a1ec',hp:300,attack:11,
  passive:'Ghép 4+ gây thêm 20 sát thương.',
  subPassive:'Dự bị: Ghép 4+ gây thêm 10 sát thương.',
  assist:{name:'Đạn Đánh Dấu',short:'Bắn 12 ST xuyên Khiên'},
  bio:'Người đưa tin đi qua những tuyến phố bị xóa khỏi bản đồ. Dạ dùng cung sợi quang và ảnh phân thân để mở đường cho dân sơ tán.',
  skills:[
   skill('Mũi xuyên đêm',10,'Gây 44 (+Công) sát thương xuyên lá chắn.',{short:'44+ST xuyên Khiên',damage:44,scale:2,pierce:true}),
   skill('Dấu im lặng',18,'Gây 48 (+Công) sát thương, choáng 1 lượt và đốt 2 lượt.',{short:'48+ST · Choáng & Đốt',damage:48,scale:2,stun:1,burn:2}),
   skill('Ngàn đường sáng',28,'Gây 105 (+Công) sát thương xuyên khiên. Thu mọi ô xung kích, hút máu nếu địch bị Đốt.',{short:'105+ST xuyên Khiên · Thu Xung',damage:105,scale:3,pierce:true,clear:0,vamp:.25})
  ]},
 {id:'kinh',name:'KÍNH',title:'Lăng Kính',role:'Chiến thuật',color:'#eacb80',hp:310,attack:9,
  passive:'Mỗi ô năng lượng nhận thêm 1 điểm.',
  subPassive:'Dự bị: Mỗi ô năng lượng +0.5 điểm.',
  assist:{name:'Khúc Xạ Năng Lượng',short:'Nạp +5 NL & tạo Ô Quá Tải'},
  bio:'Nhà vật lý từng thiết kế mạng lưới bình minh. Kính không đảo ngược thời gian; anh tạo các lăng kính tách tín hiệu để tìm những lối thoát chưa ai nhìn thấy.',
  skills:[
   skill('Phân kỳ',10,'Gây 28 (+Công) sát thương. Phá một cột và tạo 1 Ô Xung Dọc.',{short:'28+ST · Phá 1 cột',damage:28,scale:1.7,clear:'column',special:2}),
   skill('Nhiễu giao thoa',18,'Rút 20 năng lượng địch, đốt trong 3 lượt, +20 lá chắn và +12 Pin.',{short:'Rút 20 NL · Đốt 3 · +20 Khiên',drain:20,burn:3,shield:20,batteryGain:12}),
   skill('Lăng kính vỡ',28,'Gây 80 (+Công) sát thương. Phá vùng 3×3, choáng 1 lượt và tạo Lõi Siêu Tân Tinh.',{short:'80+ST · Nổ 3×3 · Choáng',damage:80,scale:2.5,clear:'area',stun:1,special:3})
  ]},
 {id:'yen',name:'YÊN',title:'Diệp Mạch',role:'Hồi phục',color:'#91dea1',hp:340,attack:8,
  passive:'Ô sinh lực hồi thêm 3 HP.',
  subPassive:'Dự bị: Ô sinh lực hồi thêm 1.5 HP.',
  assist:{name:'Phun Sương Cấp Cứu',short:'Hồi +18 HP & +8 Pin'},
  bio:'Bác sĩ nuôi những sợi tảo phát sáng trong áo choàng. Yên đưa sự sống vào các kênh năng lượng chết, nhưng mọi sinh lực cô truyền đi đều phải được tái tạo.',
  skills:[
   skill('Mạch xanh',10,'Hồi 45 (+Công) HP, +10 lá chắn và xóa trạng thái Đốt.',{short:'Hồi 45+ HP · +10 Khiên',heal:45,shield:10,cleanse:true}),
   skill('Rễ hồi sinh',18,'Hồi 65 (+Công) HP. Thu mọi ô sinh lực và hồi +15 Pin.',{short:'Hồi 65+ HP · Thu ô Sinh lực',heal:65,clear:2,batteryGain:15}),
   skill('Rừng sao thức',28,'Hồi 90 (+Công) HP, +35 lá chắn và gây 45 (+Công) sát thương.',{short:'Hồi 90+ HP · +35 Khiên · 45 ST',heal:90,shield:35,damage:45,scale:1.8})
  ]}
];
export const MODULES=[
 {id:'edge',name:'Bộ khuếch quang',icon:'pulse',desc:'+10/20/30% sát thương xung kích và kỹ năng.',awakened:'Bậc III: Kỹ năng +12% uy lực & Hút máu 15%.',price:[160,380,700]},
 {id:'aegis',name:'Màng bảo hộ',icon:'shield',desc:'Đầu trận +15/30/45 lá chắn.',awakened:'Bậc III: Đầu lượt tự hồi +8 Khiên; vỡ Khiên phóng xung phản 16 ST.',price:[160,380,700]},
 {id:'well',name:'Tụ năng lượng',icon:'flux',desc:'Đầu trận +5/10/15 năng lượng.',awakened:'Bậc III: Dùng bất kỳ kỹ năng nào đều hoàn trả +4 Năng lượng.',price:[180,420,780]},
 {id:'seed',name:'Mầm sinh học',icon:'heart',desc:'Mỗi ô sinh lực hồi thêm 2/4/6 HP.',awakened:'Bậc III: Khi HP < 35%, tự kích hoạt Cấp Cứu (+45 HP, +25 Khiên).',price:[150,360,680]},
 {id:'cell',name:'Pin vĩnh cửu',icon:'cell',desc:'Đầu trận +20/40/60 pin, tối đa 200.',awakened:'Bậc III: Đổi Chỉ huy nạp thêm +5 Năng lượng & +12 Pin.',price:[130,320,600]},
 {id:'lens',name:'Thấu kính ký ức',icon:'star',desc:'Thưởng tín dụng và EXP +10/20/30%.',awakened:'Bậc III: Đầu trận tạo sẵn 1 Ô Quá Tải; ghép 4+ thưởng +4 Vàng/EXP.',price:[190,440,800]}
];
export const CHAPTERS=['I · THÀNH PHỐ KHÔNG BÌNH MINH','II · NHỮNG NGƯỜI Ở LẠI','III · BÊN KIA MÀN ĐÊM'];
const mission=(id,name,enemy,hp,attack,trait,brief,dialogue,outro)=>({id,name,enemy,hp,attack,trait,brief,dialogue,outro,chapter:Math.floor(id/4),reward:100+id*35,xp:70+id*20});
export const MISSIONS=[
 mission(0,'Tín hiệu từ bến cảng','Trinh sát Hắc Điện',160,6,'drain','Giải phóng trạm cứu hộ đầu tiên.',['LYRA|Tín hiệu vẫn còn. Dưới lớp nhiễu ấy là tiếng người.','KÍNH|Ghép các xung sáng để phá giáp nó. Giữ pin, đừng đốt hết đường về.','LYRA|Chúng ta không bỏ một ai ở lại.'],'Trạm cứu hộ phát sóng trở lại. Những người sống sót gọi nhóm là Dư Quang.'),
 mission(1,'Cầu treo vỡ','Thợ săn Cáp Đen',195,7,'burn','Bảo vệ đoàn xe qua cầu.',['DẠ|Ba nhịp nữa, sợi cáp cuối cùng sẽ đứt.','SORA|Tôi giữ cầu. Cậu giữ tầm bắn.'],'Đoàn xe đến bờ đông. Trong một chiếc radio, nhóm nghe tên Varkhos lần đầu.'),
 mission(2,'Vườn tảo tắt sáng','Bầy Ký Sinh',220,7,'heal','Thu hồi nguồn sinh lực của thành phố.',['YÊN|Chúng không ăn cây. Chúng ăn ánh sáng cây tích trữ.','NHAM|Vậy ta cho chúng một thứ khó nuốt hơn.'],'Những sợi tảo sáng lại. Yên tìm thấy dấu hiệu sự sống bên ngoài màn phong tỏa.'),
 mission(3,'Người gác đê','Giám binh Mỏ Neo',290,9,'shield','Hạ chỉ huy phong tỏa khu cảng.',['GIÁM BINH|Một thành phố được giữ nguyên tốt hơn một thế giới tự hủy.','LYRA|Giữ nguyên không phải là sống.'],'Cổng cảng mở. Một đoàn thuyền rời bóng tối, mang theo bản đồ ba cột dẫn.'),
 mission(4,'Tần số thất lạc','Bóng Gương',265,9,'drain','Tìm nhóm nghiên cứu bị mắc kẹt.',['KÍNH|Tôi từng viết các phương trình này. Varkhos đã biến chúng thành nhà tù.','DẠ|Anh biết ổ khóa. Chúng tôi sẽ giúp anh mở nó.'],'Kính lấy lại nhật ký nghiên cứu: mạng lưới có thể được đảo pha từ bên trong.'),
 mission(5,'Đường hầm đỏ','Máy Nghiền Tro',300,10,'burn','Đi qua tuyến vận tải dưới lòng đất.',['NHAM|Đá đang kể chuyện về những người bị kẹt trong này.','YÊN|Tôi nghe được nhịp tim. Chưa muộn đâu.'],'Những công nhân được giải cứu giúp nhóm dựng một thiết bị đảo pha di động.'),
 mission(6,'Vành đai không trọng lực','Kỵ binh Không Quỹ',330,10,'stun','Vượt hệ phòng thủ trên không.',['SORA|Trên này không có trên dưới. Chỉ có người ta chọn bảo vệ.','LYRA|Vậy hướng của chúng ta rất rõ.'],'Dư Quang lên được vành đai. Thành phố hiện ra bên dưới như một ngôi sao bị nhốt.'),
 mission(7,'Lời nói của thành phố','Quan Chấp Ấn',390,12,'reflect','Chiếm trung tâm phát thanh.',['CHẤP ẤN|Các ngươi định đánh đổi sự ổn định lấy hỗn loạn?','YÊN|Không. Chúng tôi trả lại cho mọi người quyền lựa chọn.'],'Hàng nghìn trạm radio cùng đáp lời. Dân thành phố tự mở các đường dẫn dự phòng.'),
 mission(8,'Cột dẫn thứ nhất','Vệ binh Nhật Thực',375,11,'shield','Ngắt lá chắn ngoại vi của pháo đài.',['DẠ|Tôi có thể vào một mình.','NHAM|Cậu có thể. Nhưng không cần phải thế nữa.'],'Cột dẫn tắt. Varkhos phát hiện nhóm, nhưng đã quá muộn để đóng mọi cánh cửa.'),
 mission(9,'Kho ký ức','Thủ thư Vô Diện',410,12,'heal','Giữ những ký ức Varkhos định xóa.',['KÍNH|Ở đây có cả ký ức của hắn. Một thế giới hắn không cứu được.','LYRA|Nỗi đau giải thích lựa chọn. Nó không miễn trừ hậu quả.'],'Nhóm giữ lại kho ký ức thay vì phá hủy. Sự thật về Varkhos được truyền tới thành phố.'),
 mission(10,'Một phút trước bình minh','Song vệ Tận Cùng',455,13,'stun','Đưa thiết bị đảo pha tới lõi trung tâm.',['SORA|Thiết bị chỉ chịu được một lần kích hoạt.','YÊN|Vậy hãy chắc chắn mọi người đều sẵn sàng.','DƯ QUANG|Cùng nhau.'],'Sáu luồng tín hiệu hội tụ. Phía trước là Varkhos và bình minh bị cầm tù.'),
 mission(11,'Đêm cuối cùng','VARKHOS',620,15,'boss','Phá ba lớp khóa của Kẻ Khóa Bình Minh.',['VARKHOS|Ta đã thấy các nền văn minh tự thiêu rụi mình. Ta cho các ngươi một đêm vĩnh viễn để không bao giờ mất nhau.','LYRA|Một thế giới không được lớn lên cũng không thể chữa lành.','VARKHOS|Vậy hãy chứng minh rằng ánh sáng của các ngươi xứng đáng với cái giá ấy.'],'Varkhos gục xuống. Sáu người không chiếm lấy cỗ máy: họ trao các đường dẫn cho thành phố. Bình minh trở lại từ hàng nghìn ô cửa, không từ một bàn tay duy nhất.')
];
export const ENDING=['YÊN|Không phải mọi vết thương đều biến mất khi mặt trời lên.','KÍNH|Nhưng hôm nay, chúng ta có một ngày mới để bắt đầu.','LYRA|Dư Quang, trở về nhà.'];
