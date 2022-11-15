﻿var baseid = 105090311;
var dungeonid = 105090320;
var dungeons = 1;

function enter(pi) {
    if (pi.getMapId() == baseid) {
        for (var i = 0; i < dungeons; i++) {
            if (pi.getPlayerCount(dungeonid + i) == 0) {
                if (pi.getPlayer().getLevel() <= 80) {
                    if (pi.haveItem(5252000)) {
                        pi.gainItem(5252000, -1);
                        pi.warp(dungeonid + i, 0);
                    } else {
                        pi.playerMessage(5, "你沒有持有楓之谷迷你城入場券，無法進入該地圖。");
                    }
                } else {
                    pi.playerMessage(5, "你的等級超過了80級，無法進入該地圖。");
                }
                return true;
            }
        }
        pi.playerMessage(5, "目前所有迷你地下城都有人，請稍後再嘗試。");
    } else
        pi.warp(baseid, "MD00");
    return true;
}