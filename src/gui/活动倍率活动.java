package gui;

import handling.channel.ChannelServer;
import handling.world.MapleParty;
import handling.world.World;
import tools.MaplePacketCreator;
import server.ServerProperties;

/**
 *
 * @author Administrator
 */
public class 活动倍率活动 {

    /**
     * <23点>
     */
    public static void 倍率活动线程() {
        int 随机 = (int) Math.ceil(Math.random() * 9);
        switch (随机) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                爆率倍率活动();
                break;
            case 5:
            case 6:
            case 7:
            case 8:
                经验倍率活动();
                break;
            case 9:
                经验倍率活动();
                爆率倍率活动();
                break;
            default:
                break;
        }

    }

    public static void 经验倍率活动() {
        int 原始经验 = Integer.parseInt(ServerProperties.getProperty("FengYeDuan.expRate"));
        int 经验活动 = 原始经验 * 2;
        int seconds = 0;
        int mins = 0;
        int hours = 24;
        int time = seconds + (mins * 60) + (hours * 60 * 60);
        final String rate = "经验";
        World.scheduleRateDelay(rate, time);
        for (ChannelServer cservs : ChannelServer.getAllInstances()) {
            cservs.setExpRate(经验活动);
        }
        World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(9, 20, "[倍率活动] : 游戏开始 2 倍打怪爆率活动，将持续 24 小时，周末请各位玩家狂欢吧！"));

    }

    public static void 爆率倍率活动() {
        int 原始爆率 = Integer.parseInt(ServerProperties.getProperty("FengYeDuan.dropRate"));
        int 爆率活动 = 原始爆率 * 2;
        int seconds = 0;
        int mins = 0;
        int hours = 24;
        int time = seconds + (mins * 60) + (hours * 60 * 60);
        final String rate = "爆率";
        World.scheduleRateDelay(rate, time);
        for (ChannelServer cservs : ChannelServer.getAllInstances()) {
            cservs.setDropRate(爆率活动);
        }
        World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(9, 20, "[倍率活动] : 游戏开始 2 倍打怪爆率活动，将持续 24 小时，周末请各位玩家狂欢吧！"));    }
}
