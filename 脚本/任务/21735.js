var CY0 = "┣━━━━━━━━━━━━━━ ━━━━━━━━━━━━━━━━┫";
var CY1 = "┣       - 创意 -       ┫";
var CY2 = "┣ 玩法仿制  　定制脚本 ┫";
var CY3 = "┣ 技术支持 　 游戏顾问 ┫";
var CY4 = "┣ ＷＺ添加　  地图制作 ┫";
var CY5 = "┣ 加盾防御　  售登陆器 ┫";
var CY7 = "┣ 手游开服    端游开服 ┫";
var CY8 = "┣━━━━━━━━━━━━━━ ━━━━━━━━━━━━━━━━┫";
var CY9 = "┣    唯一微信:ZerekY   ┫";
var CY0 = "┣━━━━━━━━━━━━━━ ━━━━━━━━━━━━━━━━┫";
 var status = -1;

function start(mode, type, selection) {
	qm.removeAll(4032323);
	qm.gainItem(4032323, 1);
	qm.forceStartQuest();//开始任务
	qm.sendNextS("#p1002104#说自从受到#o9300346#的攻击之后，就动员一切力量在金银岛上找到了封印石。他说留在自己身上太危险，应该把它保管在岛上。#b#m140000000##k应该比较安全，快把#b封印石交给#p1201000#吧#k", 3);
	qm.dispose();
}

function end(mode, type, selection) {
	qm.teachSkill(21100005, qm.getPlayer().getSkillLevel(21100005), 10);   // Combo Ability 
	qm.forceCompleteQuest();//完成任务
	qm.sendNextS("好好的解读#b连环吸血#k技能吧！", 3);
	qm.dispose();
}