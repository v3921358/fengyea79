//importPackage(java.lang);
//importPackage(Packages.tools);
//importPackage(Packages.client);
//importPackage(Packages.server);

var status = 0;

var acc = "#fEffect/CharacterEff/1112903/0/0#";//红桃心
var zzz = "#fUI/UIWindow.img/Quest/icon8/0#";//蓝色右箭头
var sss = "#fUI/UIWindow.img/QuestIcon/3/0#";//选择道具
var 蓝色角点 = "#fUI/UIWindow.img/PvP/Scroll/enabled/next2#";
var 蓝色箭头 = "#fUI/UIWindow/Quest/icon2/7#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 圆形 = "#fUI/UIWindow/Quest/icon3/6#";
var 美化new = "#fUI/UIWindow/Quest/icon2/7#";
var 美化ne = "#fUI/UIWindow/Quest/icon6/7#";
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 中条猫 ="#fUI/ChatBalloon/37/n#";
var 猫右 =  "#fUI/ChatBalloon/37/ne#";
var 猫左 =  "#fUI/ChatBalloon/37/nw#";
var 右 =    "#fUI/ChatBalloon/37/e#";
var 左 =    "#fUI/ChatBalloon/37/w#";
var 下条猫 ="#fUI/ChatBalloon/37/s#";
var 猫下右 ="#fUI/ChatBalloon/37/se#";
var 猫下左 ="#fUI/ChatBalloon/37/sw#";
var 皇冠白 ="#fUI/GuildMark/Mark/Etc/00009004/16#";

	function start() {
		status = -1;
		action(1, 0, 0);
		}
	function action(mode, type, selection) {
		if (mode == -1) {
		cm.dispose();
		} else {
		if (status >= 0 && mode == 0) {
		cm.dispose();
		return;
		}
		if (mode == 1)
		status++;
		else
		status--;


	if (status == 0) {

	    var textz = "\t#d#e#b#v4030001# 欢迎来到#r" + cm.getServerName() + " 租借中心 #e#b#v4030001##b#k#n\r\r\n";
		textz += "#d\r\n\t你好，我是大姐大，请选择你需要租借的装备 \r\n  所有装备出租的属性==【10】，时效只有【24】小时。\r\n";

		textz += "#r#L0##v1302208##z1302208#   战士 - 单手剑#l\r\n\r\n";

		textz += "#r#L1##v1332025##z1332025#   飞侠 - 短  刀#l\r\n\r\n";

		textz += "#r#L2##v1382012##z1382012#   法师 - 长  杖#l\r\n\r\n";

		textz += "#r#L3##v1452022##z1452022#   弓手 - 远  弓#l\r\n\r\n";

		textz += "#r#L4##v1462019##z1462019#   弩手 - 远  弩#l\r\n\r\n";

		textz += "#r#L5##v1472032##z1472032#   飞侠 - 拳  套#l\r\n\r\n";
		
		textz += "#r#L6##v1482020##z1482020#   海盗 - 拳  甲#l\r\n\r\n";
		
		textz += "#r#L7##v1492020##z1492020#   海盗 - 手  枪#l\r\n\r\n";
		
		textz += "#r#L8##v1432012##z1432012#   战士 - 枪  器#l\r\n\r\n";
		
		textz += "#r#L9##v1442024##z1442024#   海盗 - 矛  器#l\r\n\r\n";

	

		cm.sendSimple (textz);  

	}else if (status == 1) {

	if (selection == 0) {
		if (cm.getMeso() < 0) {
 			cm.sendOk("请带来#r1亿#k金币#k");
      			cm.dispose();
		} else if (!cm.haveItem(5220007,1)) {
 			cm.sendOk("#v5220007##z5220007#您没有该物品 需要在商城购买后在来吧");
      			cm.dispose();
		//} else if (cm.getInventory(1).isFull(3)){
		//	cm.sendOk("#b请保证装备栏位至少有3个空格,否则无法兑换.");
		//	cm.dispose();
		} else {
        //var ii = MapleItemInformationProvider.getInstance();		                
		//var type = ii.getInventoryType(1302208); //获得装备的类形/////////////////////////////////////////////////////////////////////
		//var toDrop = ii.randomizeStats(ii.getEquipById(1302208)).copy(); // 生成一个Equip类
		//var temptime = (System.currentTimeMillis() + 1 * 24 * 60 * 60 * 1000); //时间
		//toDrop.setExpiration(temptime); 
		//cm.getPlayer().getInventory(type).addItem(toDrop);//将这个装备放入包中
		//cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop)); //刷新背包	
		//cm.getChar().saveToDB(false, false);
		cm.gainItem(1302208,10,10,10,10,10,10,60,60,0,0,0,0,0,0,24);//
		cm.gainItem(5220007,-1);
		cm.sendOk("租借成功，有效期24小时!")
		cm.dispose();
		}
	} else if (selection == 1) {
		if (cm.getMeso() < 0) {
 			cm.sendOk("请带来#r5000W#k金币#k");
      			cm.dispose();
		} else if (!cm.haveItem(5220007,1)) {
 			cm.sendOk("#v5220007##z5220007#您没有该物品 需要在商城购买后在来吧#k");
      			cm.dispose();
		//} else if (cm.getInventory(1).isFull(3)){
		//	cm.sendOk("#b请保证装备栏位至少有3个空格,否则无法兑换.");
		//	cm.dispose();
		} else {
			cm.gainItem(1332025,10,10,10,10,10,10,60,60,0,0,0,0,0,0,24);//
			cm.gainItem(5220007,-1);
			cm.sendOk("租借成功，有效期24小时!")
      			cm.dispose();
			}

	}else if (selection == 2){
		if (cm.getMeso() < 0) {
 			cm.sendOk("请带来#r5000W#k金币#k");
      			cm.dispose();
		} else if (!cm.haveItem(5220007,1)) {
 			cm.sendOk("#v5220007##z5220007#您没有该物品 需要在商城购买后在来吧#k");
      			cm.dispose();
		//} else if (cm.getInventory(1).isFull(3)){
		//	cm.sendOk("#b请保证装备栏位至少有3个空格,否则无法兑换.");
		//	cm.dispose();
		} else{
			cm.gainItem(1382012,10,10,10,10,10,10,60,60,0,0,0,0,0,0,24);//
			cm.gainItem(5220007,-1);
			cm.sendOk("租借成功，有效期24小时!")
      			cm.dispose();
			}

	}else if (selection == 3){
		if (cm.getMeso() < 0) {
 			cm.sendOk("请带来#r5000W#k金币#k");
      			cm.dispose();
		} else if (!cm.haveItem(5220007,1)) {
 			cm.sendOk("#v5220007##z5220007#您没有该物品 需要在商城购买后在来吧#k");
      			cm.dispose();
		//} else if (cm.getInventory(1).isFull(3)){
		//	cm.sendOk("#b请保证装备栏位至少有3个空格,否则无法兑换.");
		//	cm.dispose();
		} else{
         cm.gainItem(1452022,10,10,10,10,10,10,60,60,0,0,0,0,0,0,24);//
			cm.gainItem(5220007,-1);
			cm.sendOk("租借成功，有效期24小时!")
			cm.dispose();
			}

	}else if (selection == 4){
		if (cm.getMeso() < 0) {
 			cm.sendOk("请带来#r5000W#k金币#k");
      			cm.dispose();
		} else if (!cm.haveItem(5220007,1)) {
 			cm.sendOk("#v5220007##z5220007#您没有该物品 需要在商城购买后在来吧#k");
      			cm.dispose();
		//} else if (cm.getInventory(1).isFull(3)){
		//	cm.sendOk("#b请保证装备栏位至少有3个空格,否则无法兑换.");
		//	cm.dispose();
		} else{
			cm.gainItem(1462019,10,10,10,10,10,10,60,60,0,0,0,0,0,0,24);//
			cm.gainItem(5220007,-1);
			cm.sendOk("租借成功，有效期24小时!")
      			cm.dispose();
			}

	}else if (selection == 5){
		if (cm.getMeso() < 0) {
 			cm.sendOk("请带来#r5000W#k金币#k");
      			cm.dispose();
		} else if (!cm.haveItem(5220007,1)) {
 			cm.sendOk("#v5220007##z5220007#您没有该物品 需要在商城购买后在来吧#k");
      			cm.dispose();
		//} else if (cm.getInventory(1).isFull(3)){
		//	cm.sendOk("#b请保证装备栏位至少有3个空格,否则无法兑换.");
		//	cm.dispose();
		} else{
			cm.gainItem(1472032,10,10,10,10,10,10,30,30,0,0,0,0,0,0,24);//
			cm.gainItem(5220007,-1);
			cm.sendOk("租借成功，有效期24小时!")
      			cm.dispose();
			}

	}else if (selection == 6){
		if (cm.getMeso() < 0) {
 			cm.sendOk("请带来#r5000W#k金币#k");
      			cm.dispose();
		} else if (!cm.haveItem(5220007,1)) {
 			cm.sendOk("#v5220007##z5220007#您没有该物品 需要在商城购买后在来吧#k");
      			cm.dispose();
		//} else if (cm.getInventory(1).isFull(3)){
		//	cm.sendOk("#b请保证装备栏位至少有3个空格,否则无法兑换.");
		//	cm.dispose();
		} else{
			cm.gainItem(1482041,10,10,10,10,10,10,40,40,0,0,0,0,0,0,24);//
			cm.gainItem(5220007,-1);
			cm.sendOk("租借成功，有效期24小时!")
      			cm.dispose();
			}
	}else if (selection == 7){
		if (cm.getMeso() < 0) {
 			cm.sendOk("请带来#r5000W#k金币#k");
      			cm.dispose();
		} else if (!cm.haveItem(5220007,1)) {
 			cm.sendOk("#v5220007##z5220007#您没有该物品 需要在商城购买后在来吧#k");
      			cm.dispose();
		//} else if (cm.getInventory(1).isFull(3)){
		//	cm.sendOk("#b请保证装备栏位至少有3个空格,否则无法兑换.");
		//	cm.dispose();
		} else {
			cm.gainItem(1492042,10,10,10,10,10,10,40,40,0,0,0,0,0,0,24);//
			cm.gainItem(5220007,-1);
			cm.sendOk("租借成功，有效期24小时!")
      			cm.dispose();
			}
   }else if (selection == 8){
		if (cm.getMeso() < 0) {
 			cm.sendOk("请带来#r5000W#k金币#k");
      			cm.dispose();
		} else if (!cm.haveItem(5220007,1)) {
 			cm.sendOk("#v5220007##z5220007#您没有该物品 需要在商城购买后在来吧#k");
      			cm.dispose();
		//} else if (cm.getInventory(1).isFull(3)){
		//	cm.sendOk("#b请保证装备栏位至少有3个空格,否则无法兑换.");
		//	cm.dispose();
		} else {
			cm.gainItem(1432012,10,10,10,10,10,10,70,70,0,0,0,0,0,0,24);//
			cm.gainItem(5220007,-1);
			cm.sendOk("租借成功，有效期24小时!")
      			cm.dispose();
	       }
	 }else if (selection == 9){
		if (cm.getMeso() < 0) {
 			cm.sendOk("请带来#r5000W#k金币#k");
      			cm.dispose();
		} else if (!cm.haveItem(5220007,1)) {
 			cm.sendOk("#v5220007##z5220007#您没有该物品 需要在商城购买后在来吧#k");
      			cm.dispose();
		//} else if (cm.getInventory(1).isFull(3)){
		//	cm.sendOk("#b请保证装备栏位至少有3个空格,否则无法兑换.");
		//	cm.dispose();
		} else {
         cm.gainItem(1442024,10,10,10,10,10,10,70,70,0,0,0,0,0,0,24);//
			cm.gainItem(5220007,-1);
			cm.sendOk("租借成功，有效期24小时!")
      			cm.dispose();
}       }
}
}
}
