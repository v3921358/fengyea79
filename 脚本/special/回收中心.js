/**
**回收脚本
**/
var ttt1 = "#fEffect/CharacterEff/1062114/1/0#";  //爱心
var add = "#fEffect/CharacterEff/1112903/0/0#";//红桃心
var 四方印 = "#fUI/GuildMark/Mark/Pattern/00004014/11#"; // 紫色四方印
var 装备回收列表=[
1332055, 
1482022,
1372034, 
1382039, 
1402039, 
1412027, 
1432040, 
1452045, 
1462040, 
1472055,
1442024,
1312056,
1492095,
1302064,
1052202,//套服
1072533,//鞋子
1102071,//披风
1082440,//手套
1003454,//枫叶冒
1092030,//枫叶盾
1092045,//法师盾
1092046,//战士盾
1092047//飞侠盾
];
var 装备回收积分=1;
var 卷轴回收列表=[2040013,
2040025,
2040026,
2040028,
2040029,
2040030,
2040031,
2040301,
2040302,
2040304,
2040305,
2040306,
2040307,
2040317,
2040318,
2040320,
2040321,
2040322,
2040323,
2040329,
2040330,
2040331,
2040401,
2040402,
2040404,
2040405,
2040406,
2040407,
2040408,
2040409,
2040418,
2040419,
2040421,
2040422,
2040424,
2040425,
2040426,
2040427,
2040501,
2040502,
2040504,
2040505,
2040508,
2040509,
2040510,
2040511,
2040513,
2040514,
2040516,
2040517,
2040518,
2040519,
2040520,
2040521,
2040531,
2040532,
2040533,
2040534,
2040601,
2040604,
2040605,
2040606,
2040607,
2040608,
2040609,
2040619,
2040624,
2040625,
2040626,
2040627,
2040701,
2040702,
2040704,
2040705,
2040707,
2040708,
2040712,
2040713,
2040714,
2040715,
2040716,
2040717,
2040727,
2040804,
2040805,
2040808,
2040809,
2040810,
2040811,
2040906,
2040907,
2040924,
2040925,
2040930,
2040931,
2040932,
2040933,
2041010,
2041011,
2041013,
2041014,
2041016,
2041017,
2041019,
2041020,
2041022,
2041023,
2041035,
2043001,
2043002,
2043005,
2043101,
2043102,
2043105,
2043201,
2043202,
2043205,
2043301,
2043302,
2043305,
2043701,
2043702,
2043705,
2043801,
2043802,
2043805,
2044001,
2044002,
2044005,
2044101,
2044102,
2044105,
2044201,
2044202,
2044205,
2044301,
2044302,
2044305,
2044401,
2044402,
2044405,
2044501,
2044502,
2044505,
2044601,
2044809,
2044602,
2044605,
2044701,
2044702,
2044705,
2044801,
2044802,
2044901,
2044902];
var 卷轴回收积分=1;
var 兑换机制=[
	100,300,500,1000,1500,2000,3000,4000,5000,6000
];
var 装备兑换=[
[1112789,100],//100积分可回收，对应回收机制
[1112792,300],//300积分可回收，对应回收机制
[1112764,500],//500积分可回收，对应回收机制
[1112768,1000],//1000积分可回收，对应回收机制
[1112772,1500],//1500积分可回收，对应回收机制
[1112776,2000],//2000积分可回收，对应回收机制
[1112763,3000],//3000积分可回收，对应回收机制
[1112767,4000],//4000积分可回收，对应回收机制
[1112771,5000],//5000积分可回收，对应回收机制
[1112775,6000]//6000积分可回收，对应回收机制
];
var 装备兑换数量=[
	[1],//100积分可回收，对应回收机制
	[1],//300积分可回收，对应回收机制
	[1],//500积分可回收，对应回收机制
	[1],//1000积分可回收，对应回收机制
	[1],//1500积分可回收，对应回收机制
	[1],//2000积分可回收，对应回收机制
	[1],//3000积分可回收，对应回收机制
	[1],//4000积分可回收，对应回收机制
	[1],//5000积分可回收，对应回收机制
	[1] //6000积分可回收，对应回收机制
];
var 卷轴兑换=[
	[4032391,100],//100积分可回收，对应回收机制
	[4032392,300],//300积分可回收，对应回收机制
	[2340000,500],//500积分可回收，对应回收机制
	[2049118,1000],//1000积分可回收，对应回收机制	
	[4310034,2000],//3000积分可回收，对应回收机制
	[4310029,3000],//4000积分可回收，对应回收机制
	[4310148,4000]//5000积分可回收，对应回收机制
	
];
var 卷轴兑换数量=[
	[30],//100积分可回收，对应回收机制
	[30],//300积分可回收，对应回收机制
	[5],//500积分可回收，对应回收机制
	[6],//1000积分可回收，对应回收机制
	[1],//200积分可回收，对应回收机制
	[1],//3000积分可回收，对应回收机制
	[1],//4000积分可回收，对应回收机制
];
var fee=0;
var 物品坐标=0;
var 物品栏=0;
var 状态=0;
var status=-1;
var slot = Array();
var 兑换内部坐标=-1;
var 兑换坐标=-1;
function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
	if (status >= 0 && mode == 0) {
		cm.sendOk("感谢使用！");
		cm.dispose();
		return;
	}
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
		switch(status){
				
			case 0:
				var text="";
				text+="#e#d   亲爱的玩家 #r[#h ##k#r]#d,欢迎来到回收中心 #k\r\n\r\n";
				text+=""+四方印+四方印+四方印+四方印+四方印+四方印+四方印+四方印+四方印+四方印+四方印+四方印+四方印+四方印+四方印+四方印+四方印+四方印+四方印+四方印+四方印+"\r\n";
				text+="#e#d您目前拥有回收积分 ："+cm.getPlayer().getjf3()+"   #d#e当前元宝：#r"+cm.getmoneyb()+"#k#d个\r\n";
				text+="     #e#r#L0#"+ttt1+"装备回收"+ttt1+"#l";
				text+="        #e#r#L1#"+ttt1+"卷轴回收"+ttt1+"#l\r\n";
				//text+="#e#r#L2#"+ttt1+"装备兑换#l\r\n";
				text+="     #e#r#L4#"+ttt1+"积分元宝"+ttt1+"#l";
				text+="        #e#r#L3#"+ttt1+"积分兑换"+ttt1+"#l\r\n";
				//text+="                #e#r#L5#"+ttt1+"高级回收"+ttt1+"#l\r\n";
				cm.sendOk(text);
				break;
			case 1:
				fee=selection;
				switch(fee){
					case 0:
						物品栏=1;
						状态=1;
						var avail="#e#d可回收物品如下:\r\n";
						avail+="\t\t\t\t#e#r#L10000#一键回收所有#l\r\n";
						for (var i = 0; i < 装备回收列表.length; i++) {
							if(cm.haveItem(装备回收列表[i], 1)){
								avail += "\t\t#b#L"+i+"##v"+装备回收列表[i]+"##z"+装备回收列表[i]+"##r【可回收#r#c"+装备回收列表[i]+"##k】#b#l\r\n";
							}else{
								avail += "\t\t#b#L"+i+"##v"+装备回收列表[i]+"##z"+装备回收列表[i]+"##r#b#l\r\n";
							}
						}
						cm.sendOk(avail);
						break;
					case 1:
						物品栏=2;
						状态=1;
						var avail="#e#d可回收物品如下:\r\n";
						avail+="\t\t\t\t#e#r#L10000#一键回收所有#l\r\n";
						for (var i = 0; i < 卷轴回收列表.length; i++) {
							if(cm.haveItem(卷轴回收列表[i], 1)){
								avail += "\t\t#b#L"+i+"##v"+卷轴回收列表[i]+"##z"+卷轴回收列表[i]+"##r【可回收数量#r#c"+卷轴回收列表[i]+"##k】#b#l\r\n";
							}else{
								avail += "\t\t#b#L"+i+"##v"+卷轴回收列表[i]+"##z"+卷轴回收列表[i]+"##r#b#l\r\n";
							}
						}
						cm.sendOk(avail);
						break;
					case 2:
						
						物品栏=1;
						状态=2;
						var avail="#e#d可兑换物品如下:\r\n";
						for(var i=0;i<装备兑换.length;i++){
							avail+="\t\t#b#L"+i+"##v"+装备兑换[i][0]+"##r "+装备兑换[i][1]+"#b 积分兑换 #r"+装备兑换数量[i]+" #b个#b#l\r\n";
							//avail+="\t\t#b#L"+i+"##v"+装备兑换[i][0]+"##z"+装备兑换[i][0]+"##r "+装备兑换[i][1]+"#b 积分兑换 #r"+装备兑换数量[i]+" #b个#b#l\r\n";
						}
						cm.sendOk(avail);
						break;
					case 3:
					
						物品栏=2;
						状态=2;
						var avail="#e#d可兑换物品如下:\r\n";
						for(var i=0;i<卷轴兑换.length;i++){
							avail+="\t\t#b#L"+i+"##v"+卷轴兑换[i][0]+"##r "+卷轴兑换[i][1]+"#b 积分兑换 #r"+卷轴兑换数量[i]+" #b个#b#l\r\n";
							//avail+="\t\t#b#L"+i+"##v"+卷轴兑换[i][0]+"##z"+卷轴兑换[i][0]+"##r "+卷轴兑换[i][1]+"#b 积分兑换 #r"+卷轴兑换数量[i]+" #b个#b#l\r\n";
						}
						cm.sendOk(avail);
						break;
					case 4:
					
						cm.dispose();
				        cm.openNpc(9270052,  "积分元宝");
						break;
					case 5:
					
						cm.dispose();
				        cm.openNpc(9270052,  "高级回收");
						break;
				}
				break;
			case 2:
				物品坐标=selection;
				if(物品坐标==10000){
					cm.sendYesNo("#b#e是否确认一键回收，回收后不可还原哦?");
				}else{
					switch(状态){
						case 1:	
							
							if(物品栏==1){
								if(!cm.haveItem(装备回收列表[物品坐标], 1)){
									cm.sendOk("您没有该装备，请确认。");
									cm.dispose();
									return;
								}
								cm.sendYesNo("#b#e是否回收#i"+装备回收列表[物品坐标]+"# X "+cm.itemQuantity(装备回收列表[物品坐标])+"?");
							}else{
								if(!cm.haveItem(卷轴回收列表[物品坐标], 1)){
									cm.sendOk("您没有该卷轴，请确认。");
									cm.dispose();
									return;
								}
								
								cm.sendYesNo("#b#e是否回收#i"+卷轴回收列表[物品坐标]+"# X "+cm.itemQuantity(卷轴回收列表[物品坐标])+"?");
							}
							break;
						case 2:
							if(物品栏==1)
								cm.sendYesNo("#b#e是否兑换#i"+装备兑换[物品坐标][0]+"#?");
							else
								cm.sendYesNo("#b#e是否兑换#i"+卷轴兑换[物品坐标][0]+"#?");
							break;
					}
				}
				break;
			case 3:
				var 总积分=0;
				if(物品坐标==10000){
					if(物品栏==1){
						for (var i = 0; i < 装备回收列表.length; i++) {
							if(cm.itemQuantity(装备回收列表[i])>0){
								总积分+=(装备回收积分*cm.itemQuantity(装备回收列表[i]))
								
								cm.gainItem(装备回收列表[i],-cm.itemQuantity(装备回收列表[i]));
							}
						}
						cm.getPlayer().setjf3(cm.getPlayer().getjf3()+总积分);
						cm.sendOk("回收成功，共回收"+总积分+"点回收积分！");
						cm.dispose();
						return;
					}else{
						for (var i = 0; i < 卷轴回收列表.length; i++) {
							if(cm.itemQuantity(卷轴回收列表[i])>0){
								总积分+=(卷轴回收积分*cm.itemQuantity(卷轴回收列表[i]))
								
								cm.gainItem(卷轴回收列表[i],-cm.itemQuantity(卷轴回收列表[i]));
							}
						}
						cm.getPlayer().setjf3(cm.getPlayer().getjf3()+总积分);
						cm.sendOk("回收成功，共回收"+总积分+"点回收积分！");
						cm.dispose();
						return;
					}
				}else{
					switch(状态){
						case 1:
							if(物品栏==1){
								总积分+=(装备回收积分*cm.itemQuantity(装备回收列表[物品坐标]))
								cm.getPlayer().setjf3(cm.getPlayer().getjf3()+总积分);
								cm.gainItem(装备回收列表[物品坐标],-cm.itemQuantity(装备回收列表[物品坐标]));
							}else{
								总积分+=(卷轴回收积分*cm.itemQuantity(卷轴回收列表[物品坐标]))
								cm.getPlayer().setjf3(cm.getPlayer().getjf3()+总积分);
								cm.gainItem(卷轴回收列表[物品坐标],-cm.itemQuantity(卷轴回收列表[物品坐标]));
							}
							cm.sendOk("回收成功，共回收"+总积分+"点回收积分！");
							cm.dispose();
							return;
							break;
						case 2:
						
							if(物品栏==1){
								if(cm.getInventory(1).isFull(1)) {
									cm.sendOk("请保证背包装备栏位至少有 #r2 #k个空格。");
									cm.dispose();
									return;
								}
								if(cm.getPlayer().getjf3()<装备兑换[物品坐标][1]){
									cm.sendOk("您的积分不够，请确认。");
									cm.dispose();
									return;
								}
								cm.getPlayer().setjf3(cm.getPlayer().getjf3()-装备兑换[物品坐标][1]);
								cm.gainItem(装备兑换[物品坐标][0],装备兑换数量[物品坐标]);
								cm.sendOk("兑换成功，请查看背包！");
							}else{
								if(cm.getInventory(2).isFull(1)) {
									cm.sendOk("请保证背包消耗栏位至少有 #r2 #k个空格。");
									cm.dispose();
									return;
								}
								if(cm.getInventory(4).isFull(1)) {
									cm.sendOk("请保证背包其他栏位至少有 #r2 #k个空格。");
									cm.dispose();
									return;
								}
								if(cm.getPlayer().getjf3()<卷轴兑换[物品坐标][1]){
									cm.sendOk("您的积分不够，请确认。");
									cm.dispose();
									return;
								}
								cm.getPlayer().setjf3(cm.getPlayer().getjf3()-卷轴兑换[物品坐标][1]);
								cm.gainItem(卷轴兑换[物品坐标][0],卷轴兑换数量[物品坐标]);
								cm.sendOk("兑换成功，请查看背包！");
							}
							cm.dispose();
							return;
							break;
					}
				}
				break;
		}
	}
}
function getmoneyb() {
	accid = cm.getPlayer().getAccountID();
	xmfhz = 0;
	var conn = Packages.database.DatabaseConnection.getConnection();
	var sql = "SELECT * FROM accounts WHERE id = "+accid+"   ;";
	var pstmt = conn.prepareStatement(sql);
	var result = pstmt.executeQuery();
	if (result.next()) {
	xmfhz = result.getString("moneyb");
	}
	result.close();
	pstmt.close();	
	return xmfhz;
}