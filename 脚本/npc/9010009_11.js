importPackage(Packages.client);
var status = 0;
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        if (status == 0) {
            var txt = "";
            txt = "我是每日跑商任务NPC！第八轮.\r\n\r\n";

            if (cm.getBossLog("跑商任务") == 7){// cm.getPS()  的意思是 读取跑商值如果等于0 就得出他没有开始跑商 就运行他进行第一环跑商!
                txt += "#L1##b收集50个火野猪尖牙#v4000024#交给我！[奖励经验"+cm.getLevel()*4000+" ] [奖励金币"+cm.getLevel()*8000+" ]#l";
                cm.sendSimple(txt);
            }else{
				if (cm.getBossLog("跑商任务") > 7){ 
                txt += "你已经完成过了然后了第八轮，继续进行下一环吧.!\r\n请第二天再来完成本环节！";
				}
                cm.sendOk(txt);
                cm.dispose();
            }

        } else if (selection == 1) {
            if (cm.haveItem(4000024,50)){
                cm.setBossLog("跑商任务");//跑环CD  的意思是 你完成跑商第一环的时候给予你 跑商值+1这样你就无法在重复做第一环了。只有凌晨12点刷新才行！
		
                cm.gainItem(4000024, -50);
                cm.gainMeso(cm.getLevel()*8000);//读取变量
                cm.gainExp(cm.getLevel()*4000);
				cm.gainItem(4000313,40);
				cm.gainItem(4251202,3);
				cm.gainNX(2000);
                cm.sendOk("跑商第八轮完成![奖励经验"+cm.getLevel()*4000+" ] [奖励金币"+cm.getLevel()*8000+" ]黄金枫叶40张、高等五彩水晶3个、点券2000点\r\n\r\n你已经完成过了然后了第八轮，继续进行下一环吧.");
                cm.dispose();
            }else{
                cm.sendOk("收集50个火野猪尖牙#v4000024#交给我!");
                cm.dispose();
            }
        }
    }
}
