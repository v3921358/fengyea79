
var status;

function start() {
    status = -1;
    action(1,0,0);
}

function action(mode, type, selection){
    if (mode == 0 && status == 0) {
	cm.dispose();
	return;
    } else {
	if (mode == 1)
	    status++;
	else
	    status--;
	var mapId = cm.getMapId();
	if (mapId == 103000890) { 
	    cm.warp(103000000, "mid00");
	    cm.removeAll(4001007);
	    cm.removeAll(4001008);
	    cm.dispose();
	} else {
	    var outText;
	    if (mapId == 103000805) {
		outText = "你要离开这个地方吗?";
	    } else {
		outText = "一旦你离开这个地图，任务将以失败告终。";
	    }
	    if (status == 0) {
		cm.sendYesNo(outText);
	    } else if (mode == 1) {
		cm.warp(103000890, "st00"); 
		cm.dispose();
	    }
	}
    }
}