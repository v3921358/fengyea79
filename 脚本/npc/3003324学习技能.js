var 中条猫 ="#fUI/ChatBalloon/37/n#";
var 猫右 =  "#fUI/ChatBalloon/37/ne#";
var 猫左 =  "#fUI/ChatBalloon/37/nw#";
var 右 =    "#fUI/ChatBalloon/37/e#";
var 左 =    "#fUI/ChatBalloon/37/w#";
var 下条猫 ="#fUI/ChatBalloon/37/s#";
var 猫下右 ="#fUI/ChatBalloon/37/se#";
var 猫下左 ="#fUI/ChatBalloon/37/sw#";
var 皇冠白 ="#fUI/GuildMark/Mark/Etc/00009004/16#";
var status = -1;
var job = 0;
var type = -1;
var skill = [[8, 1004, 1007, 1013],[10000018, 10001004, 10001007],[20000024, 20001004, 20001007]];

function start(){
	action(1, 0, 0);
}

function action(mode, type ,selection) {
	if(mode == 0 && status == 0) {
		status --;
	} else if(mode == 1) {
		status ++;
	} else {
		cm.dispose();
		return;
	}
	
	if (status == 0) {
		cm.sendYesNo("                  #k"+皇冠白+" #r#e#w" + cm.getServerName() + "#n#k "+皇冠白+"\r\n\r\n  "+猫左+中条猫+中条猫+中条猫+中条猫+中条猫+中条猫+中条猫+中条猫+中条猫+中条猫+中条猫+中条猫+中条猫+中条猫+中条猫+中条猫+中条猫+中条猫+中条猫+中条猫+猫右+"\r\n\r\n#d	   #e到达#r等级30#d，在我这里可以帮你一键学习#n\r\n\r\n 	骑宠#s8# 群宠#s1004# 锻造#s1007# 皇家骑宠技能#s1013#");
	} else if (status == 1){
		if(cm.getPlayer().getLevel() < 30){
			cm.sendNext("你的等级没有达到30级");
			cm.dispose();
			return;
		}
		job = cm.getPlayer().getJob();
		if (job < 1000){// Adv(0 ~ 522)
			type = 0;
		} else if (job < 2000) {// Cy(1000 ~ 1512)
			type = 1;
		} else if (job < 3000) {// Aran(2000 ~ 2112)
			type = 2;
		} else {
			cm.dispose();
			return;
		}
		for(var i = 0; i < skill[type].length;i++){
			var level = 1;
			if(i == 2) {
				level = 3;
			}
			cm.teachSkill(skill[type][i], level);
		}
		cm.sendNext("技能已经学习成功");
		cm.dispose();
	} else {
		cm.dispose();
	}
}