package gui;

import static abc.Game.主城;
import client.MapleCharacter;
import handling.channel.ChannelServer;
import handling.world.World;
import java.util.Calendar;
import java.util.concurrent.ScheduledFuture;
import server.Timer;
import server.life.MapleLifeFactory;
import server.life.MapleMonster;
import server.maps.MapleMap;
import static tools.FileoutputUtil.CurrentReadable_Time;
import tools.MaplePacketCreator;

public class 活动魔族入侵 {

    public static ScheduledFuture<?> 活动魔族的骚动 = null;
    public static Boolean 调试 = false;//true//false
    public static int x = 1;

    /**
     * <
     * >
     */
    public static int 蝙蝠魔 = 8150000;
    public static int S = 0;
 public static void main(String args[]) {
        魔族入侵线程();
    }
    public static void 魔族入侵线程() {
        if (活动魔族的骚动 == null) {
            System.err.println("[服务端]" + CurrentReadable_Time() + " : [魔族入侵] : 魔族已经悄然接近冒险大陆，请各位冒险家呆在主城等安全区域");
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(1, "[冒险警报]\r\n魔族已经悄然接近冒险大陆，请各位冒险家呆在主城等安全区域。"));        
            活动魔族的骚动 = Timer.BuffTimer.getInstance().register(new Runnable() {
                @Override
                public void run() {
                    if (S > 0) {
                        x++;
                        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                                if (chr == null) {
                                    continue;
                                }
                                final double 随机 = Math.ceil(Math.random() * 100);
                                int 被袭击 = 100;
                                int 等级 = chr.getLevel();
                                if (等级 >= 10 && 等级 <= 30) {
                                    被袭击 -= 100;
                                } else if (等级 > 30 && 等级 <= 70) {
                                    被袭击 -= 30;
                                } else if (等级 > 70 && 等级 <= 120) {
                                    被袭击 -= 40;
                                } else if (等级 > 120 && 等级 <= 150) {
                                    被袭击 -= 50;
                                } else if (等级 > 150 && 等级 <= 200) {
                                    被袭击 -= 60;
                                } else if (等级 > 200) {
                                    被袭击 -= 70;
                                }
                                int 地图人数 = chr.getMap().getCharactersSize();
                                if (地图人数 > 3) {
                                    被袭击 -= 30;
                                } else if (地图人数 > 1) {
                                    被袭击 -= 10;
                                }
                                if (chr.getMapId() >= 100000000) {
                                    if (随机 <= 被袭击) {
                                        if (chr.getMapId() > 910000024 || chr.getMapId() < 910000000) {
                                            if (!主城(chr.getMapId())) {
                                                MapleMonster mob1 = MapleLifeFactory.getMonster(蝙蝠魔);
                                                chr.getMap().spawnMonsterOnGroundBelow(mob1, chr.getPosition());
                                                MapleMonster mob2 = MapleLifeFactory.getMonster(蝙蝠魔);
                                                chr.getMap().spawnMonsterOnGroundBelow(mob2, chr.getPosition());
                                                MapleMonster mob3 = MapleLifeFactory.getMonster(蝙蝠魔);
                                                chr.getMap().spawnMonsterOnGroundBelow(mob3, chr.getPosition());
                                                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[冒险警报] : 玩家 " + chr.getName() + " 在 " + chr.getMap().getMapName() + " 被魔族袭击。"));
                                                chr.dropMessage(5, "你被魔族袭击了。");
                                            }
                                        }
                                    } else {
                                        chr.dropMessage(5, "一阵凉风吹过。");
                                    }
                                }
                            }
                        }
                        if (Calendar.getInstance().get(Calendar.MINUTE) >= 10) {
                            关闭活动魔族的骚动();
                        }
                    } else {
                        S++;
                    }
                }
            }, 1000 * 60 * 2);
        }
    }

    public static void 关闭活动魔族的骚动() {
        if (活动魔族的骚动 != null) {
            活动魔族的骚动.cancel(true);
            活动魔族的骚动 = null;
            System.err.println("[服务端]" + CurrentReadable_Time() + " : [魔族入侵] : 魔族已经暂且离去，它们可能会再次卷土重来");
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[冒险警报] : 魔族已经暂且离去，它们可能会再次卷土重来。"));
        }
    }

}
