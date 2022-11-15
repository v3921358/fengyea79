﻿/*
 NPC Name: 		Mark of the Squad
 Map(s): 		Entrance to Horned Tail's Cave
 Description: 		Horntail Battle starter
 */
var status = -1;

function start() {
    if (cm.getPlayer().getClient().getChannel() != 2 && cm.getPlayer().getClient().getChannel() != 1 && cm.getPlayer().getClient().getChannel() != 3 && cm.getPlayer().getClient().getChannel() != 5) {
        //cm.sendOk("暗黑龙王只有在频道 1 、 2 、 3 、 4 才可以挑战，頻道4为混沌黑龙。");
		cm.sendOk("暗黑龙王只有在频道 1 、 2 、 3 、 5 才可以挑战。");
        cm.dispose();
        return;
    }

    if (cm.getPlayer().getClient().getChannel() == 1 || cm.getPlayer().getClient().getChannel() == 2 || cm.getPlayer().getClient().getChannel() == 3 || cm.getPlayer().getClient().getChannel() == 5) {
        if (cm.getPlayer().getLevel() < 80) {
            cm.sendOk("必须80等以上才可以挑战#b暗黑龙王#k");
            cm.dispose();
            return;
        }
        if (cm.getPlayer().getClient().getChannel() != 2 && cm.getPlayer().getClient().getChannel() != 1 && cm.getPlayer().getClient().getChannel() != 3 && cm.getPlayer().getClient().getChannel() != 5) {
            cm.sendOk("暗黑龙王只有在頻道 1 、 2 、 3 、 5 才可以挑战。");
            cm.dispose();
            return;
        }
        var em = cm.getEventManager("HorntailBattle");

        if (em == null) {
            cm.sendOk("找不到腳本，請联系GM！！");
            cm.dispose();
            return;
        }
        var prop = em.getProperty("state");

        var marr = cm.getQuestRecord(160100);
        var data = marr.getCustomData();
        if (data == null) {
            marr.setCustomData("0");
            data = "0";
        }
        var time = parseInt(data);
        if (prop == null || prop.equals("0")) {
            var squadAvailability = cm.getSquadAvailability("Horntail");
            var check1 = cm.getMapFactory().getMap(240060100);
            var check2 = cm.getMapFactory().getMap(240060200);
            if (check1.playerCount() != 0 || check2.playerCount() != 0) {
                cm.sendOk("其它远征队，正在对战中。");
                cm.safeDispose();
            }
            if (squadAvailability == -1) {
                cm.sendOk("你的远征队不在当前频道，或已经挑战结束了，不可以重返战场");
                cm.safeDispose();

            } else if (squadAvailability == 1) {
                // -1 = Cancelled, 0 = not, 1 = true
                var type = cm.isSquadLeader("Horntail");
                if (type == -1) {
                    cm.sendOk("由于远征队时间流逝，所以必须重新再申请一次远征队。");
                    cm.dispose();
                } else if (type == 0) {
                    var memberType = cm.isSquadMember("Horntail");
                    if (memberType == 2) {
                        cm.sendOk("你已经被黑名单了。");
                        cm.dispose();
                    } else if (memberType == 1) {
                        status = 5;
                        cm.sendSimple("你要做什么? \r\n#b#L0#查看远征队成员#l \r\n#b#L1#加入远征队#l \r\n#b#L2#退出远征队#l");
                    } else if (memberType == -1) {
                        cm.sendOk("由于远征队时间流逝，所以必须重新再申请一次远征队。");
                        cm.dispose();
                    } else {
                        status = 5;
                        cm.sendSimple("你要做什么? \r\n#b#L0#查看远征队成员#l \r\n#b#L1#加入远征队#l \r\n#b#L2#退出远征队#l");
                    }
                } else { // Is leader
                    status = 10;
                    cm.sendSimple("你要做什么? \r\n#b#L0#查看远征队成员#l \r\n#b#L1#移除远征队员#l \r\n#b#L2#编辑限制列表#l \r\n#r#L3#进入挑战#l");
                    // TODO viewing!
                }
            } else {
                var propssa = em.getProperty("leader");
                if (propssa != null && propssa.equals("true")) {
                    var eim = cm.getDisconnected("HorntailBattle");
                    if (eim == null) {
                        cm.sendOk("其它远征队，正在对战中。");
                        cm.safeDispose();
                    } else {
                        cm.sendOk("其它远征队，正在对战中。");
                        cm.safeDispose();
                    }
                } else {
                    cm.sendOk("很抱歉你的远征队队长离开了现场，所以你不能再返回战场。");
                    cm.safeDispose();
                }
            }
        } else {
            var propssb = em.getProperty("leader");
            if (propssb != null && propssb.equals("true")) {
                var eima = em.getInstance("HorntailBattle");
                var propsa = eima.getProperty("isSquadPlayerID_" + cm.getPlayer().getId());
                var saya = "\r\n" + (eima == null ? "eima is null" : propsa) + "\r\n";
                if ((eima != null) && (propsa != null) && propsa.equals("1")) {
					if (cm.getMeso() >= 10000000 ) {
                        if( cm.getBossLog("黑龙重返") >= 2) {
	                    cm.sendOk("您好,黑龙限定每天只能重返2次！");
                        cm.dispose();
				        return;
                        }
                        cm.setBossLog('黑龙重返');//给次数					
			            cm.gainMeso(-1000000);
                    status = 13;
                    saya += "#b现在是否要重新返回远征队所在场地？";
                    saya += "\r\n#r#L1#重新返回战场#l";
                    cm.sendSimple(saya);
					    }else{
				        cm.sendOk("\t抱歉哦！您的金币不足或次数超出。");
				        cm.dispose();
			            }
						
                } else {
                    eim = cm.getDisconnected("HorntailBattle");
                    if (eim == null) {
                        cm.sendOk("其它远征队，正在挑战中2。");
                        cm.safeDispose();
                    } else {
                        cm.sendOk("其它远征队，正在挑战中3。");
                        cm.safeDispose();
                    }
                }
            } else {
                var eimb = em.getInstance("HorntailBattle");
                var propsb = eimb.getProperty("isSquadPlayerID_" + cm.getPlayer().getId());
                var sayb = "\r\n" + (eimb == null ? "eimb is null" : propsb) + "\r\n";
                if ((eimb != null) && (propsb != null) && propsb.equals("1")) {
                    status = 13;
                    sayb += "#b現在是否要重新返回遠征隊所在場地？";
                    sayb += "\r\n#r#L1#重新返回遠征隊所在場地#l";
                    cm.sendSimple(sayb);
                } else {

                    cm.sendOk("很抱歉你的遠征隊隊長離開了現場，所以你不能再返回戰場。");
                    cm.safeDispose();
                }
            }
        }
    } 


}

function action(mode, type, selection) {

    if (cm.getPlayer().getClient().getChannel() == 1 || cm.getPlayer().getClient().getChannel() == 2 || cm.getPlayer().getClient().getChannel() == 3 || cm.getPlayer().getClient().getChannel() == 5) {
        switch (status) {
            case 0:
                if (mode == 1) {
                    if (cm.getPlayer().getBossLogD("龙王次数") == 5) {
                        cm.sendNext("很抱歉每天只能打5次..");
                        cm.dispose();
                        return;
                    }
                    if (cm.registerSquad("Horntail", 5, " 已成为暗黑龙王远征队长，想要參加远征队的玩家请开始进行申请。")) {
                        cm.sendOk("你成功申请了远征队队长，你必须在接下來的五分钟召集玩家申请远征队，然后开始进入战斗。");
                        //cm.getPlayer().setBossLog("龙王次数");
                    } else {
                        cm.sendOk("创建远征队出错。");
                    }
                }
                cm.dispose();
                break;
            case 1:
                if (!cm.reAdd("HorntailBattle", "Horntail")) {
                    cm.sendOk("發生未知錯誤，請稍後再試。");
                }
                cm.safeDispose();
                break;
            case 3:
                if (mode == 1) {
                    var squd = cm.getSquad("Horntail");
                    if (squd != null && !squd.getAllNextPlayer().contains(cm.getPlayer().getName())) {
                        if (cm.getPlayer().getBossLogD("龙王次数") == 5) {
                            cm.sendNext("很抱歉每天只能打5次..");
                            cm.dispose();
                            return;
                        }
                        squd.setNextPlayer(cm.getPlayer().getName());
                        cm.sendOk("你已加入了遠征隊。");
                        //cm.getPlayer().setBossLog("龙王次数");
                    }
                }
                cm.dispose();
                break;
            case 5:
                if (selection == 0) {
                    if (!cm.getSquadList("Horntail", 0)) {
                        cm.sendOk("發生未知錯誤，請稍後再試。");
                    }
                } else if (selection == 1) { // join
                    var ba = cm.addMember("Horntail", true);
                    if (ba == 2) {
                        cm.sendOk("遠征隊人數已滿，請稍後再嘗試。");
                    } else if (ba == 1) {
                        if (cm.getPlayer().getBossLogD("龙王次数") == 5) {
                            cm.sendNext("很抱歉每天只能打5次..");
                            cm.dispose();
                            return;
                        }
                        //cm.getPlayer().setBossLog("龙王次数");
                        cm.sendOk("申請遠征隊成功。");

                    } else {
                        cm.sendOk("你已經在遠征隊裡面了。");
                    }
                } else {// withdraw
                    var baa = cm.addMember("Horntail", false);
                    if (baa == 1) {
                        cm.sendOk("離開遠征隊成功。");
                    } else {
                        cm.sendOk("你不再遠征隊裡面。");
                    }
                }
                cm.dispose();
                break;
            case 10:
                if (mode == 1) {
                    if (selection == 0) {
                        if (!cm.getSquadList("Horntail", 0)) {
                            cm.sendOk("由於未知的錯誤，遠征隊的請求被拒絕了。");
                        }
                        cm.dispose();
                    } else if (selection == 1) {
                        status = 11;
                        if (!cm.getSquadList("Horntail", 1)) {
                            cm.sendOk("由於未知的錯誤，遠征隊的請求被拒絕了。");
                            cm.dispose();
                        }
                    } else if (selection == 2) {
                        status = 12;
                        if (!cm.getSquadList("Horntail", 2)) {
                            cm.sendOk("由於未知的錯誤，遠征隊的請求被拒絕了。");
                            cm.dispose();
                        }
                    } else if (selection == 3) { // get insode
                        if (cm.getSquad("Horntail") != null) {
                            var dd = cm.getEventManager("HorntailBattle");
							//记录重返
							//重返记录("Horntail", "暗黑龙王掉线重返", "龙王次数");
                            dd.startInstance(cm.getSquad("Horntail"), cm.getMap(), 160100);
							
                            if (!cm.getPlayer().isGM()) {
                                cm.getMap().startSpeedRun();
                            }
                        } else {
                            cm.sendOk("由於未知的錯誤，遠征隊的請求被拒絕了。");
                        }
                        cm.dispose();
                    }
                } else {
                    cm.dispose();
                }
                break;
            case 11:
                cm.banMember("Horntail", selection);
                cm.dispose();
                break;
            case 12:
                if (selection != -1) {
                    cm.acceptMember("Horntail", selection);
                }
                cm.dispose();
                break;
            case 13:
                var em = cm.getEventManager("HorntailBattle");
                if ((selection == 1) && (em != null)) {
                    var eim = em.getInstance("HorntailBattle");
                    if ((eim != null) && (eim.getProperty("isSquadPlayerID_" + cm.getPlayer().getId()) != null)) {
                        eim.registerPlayer(cm.getPlayer());
                    }
                }
                cm.dispose();
                break;
            default:
                cm.dispose();
                break;
        }
    } else if (cm.getPlayer().getClient().getChannel() == 4) {
        switch (status) {
            case 0:
                if (mode == 1) {
                    if (cm.getPlayer().getBossLogD("混沌龙王次数") == 5) {
                        cm.sendNext("很抱歉每天只能打5次..");
                        cm.dispose();
                        return;
                    }
                    if (cm.registerSquad("ChaosHt", 5, " 已成為闇黑龍王遠征隊長，想要參加遠征隊的玩家請開始進行申請。")) {
                        cm.sendOk("你成功申請了遠征隊隊長，你必須在接下來的五分鐘召集玩家申請遠征隊，然後開始戰鬥。");
                        //cm.getPlayer().setBossLog("混沌龙王次数");
                    } else {
                        cm.sendOk("創建遠征隊出錯。");
                    }
                }
                cm.dispose();
                break;
            case 1:
                if (!cm.reAdd("ChaosHorntail", "ChaosHt")) {
                    cm.sendOk("發生未知錯誤，請稍後再試。");
                }
                cm.safeDispose();
                break;
            case 3:
                if (mode == 1) {
                    var squd = cm.getSquad("ChaosHt");
                    if (squd != null && !squd.getAllNextPlayer().contains(cm.getPlayer().getName())) {
                        if (cm.getPlayer().getBossLogD("混沌龙王次数") == 5) {
                            cm.sendNext("很抱歉每天只能打5次..");
                            cm.dispose();
                            return;
                        }
                        squd.setNextPlayer(cm.getPlayer().getName());
                        cm.sendOk("你已加入了遠征隊。");
                        //cm.getPlayer().setBossLog("混沌龙王次数");
                    }
                }
                cm.dispose();
                break;
            
           
            
            
            case 13:
                var em = cm.getEventManager("ChaosHorntail");
                if ((selection == 1) && (em != null)) {
                    var eim = em.getInstance("ChaosHorntail");
                    if ((eim != null) && (eim.getProperty("isSquadPlayerID_" + cm.getPlayer().getId()) != null)) {
                        eim.registerPlayer(cm.getPlayer());
                    }
                }
                cm.dispose();
                break;
            default:
                cm.dispose();
                break;
        }
    } else {
        cm.sendOk("黑暗龙王只有在頻道 1 、 2 、 3 、 5 才可以挑战。");
        cm.dispose();
        return;
    }
}


/*
function 重返记录(type, name1, name2) {
	var sq = cm.getSquad(type);
	var members = sq.getMembers();
	var bosscopy = cm.getBosslogManager();
	for (var i in members) {
		var chr = cm.getMap().getCharacterByName(members[i]);
		if (chr != null) {
			
			bosscopy.setBossLog(name1, chr);
			bosscopy.setBossLog(name2, chr);
		}
	}
}
*/