package gui.进阶BOSS;

import client.MapleCharacter;
import client.MapleDisease;
import handling.channel.ChannelServer;
import handling.world.MapleParty;
import java.awt.Point;
import java.util.concurrent.ScheduledFuture;
import server.MapleItemInformationProvider;
import server.Timer;
import server.life.MapleLifeFactory;
import server.life.MapleMonster;
import server.life.MobSkill;
import server.life.MobSkillFactory;
import server.maps.MapleMap;

public class 进阶BOSS线程 {

    /**
     * <9223372036854775807>
     * <蜗牛王血量>
     * <
     * 【虚弱】
     * 【封锁】
     * 【诅咒】
     * 【黑暗】
     * 【诱导】
     * 【贫血】血量剩余1
     * 【颓废】蓝量剩余1
     * 【处死】3秒后处死地图所有玩家
     * 【退散】3秒后驱散地图所有玩家（回城）
     *
     * >
     */
    public static ScheduledFuture<?> 进阶BOSS线程 = null;
    public static ScheduledFuture<?> 进阶BOSS线程伤害 = null;
    public static ScheduledFuture<?> 全图掉HP = null;
    public static ScheduledFuture<?> 全图掉MP = null;
    public static ScheduledFuture<?> 全图封锁 = null;
    public static int 进阶红蜗牛长老 = 9500337;
    public static int 飞鱼 = 2230107;
    public static int 地图 = 104000400;
    public static int 频道 = 2;
    public static Point 坐标 = new Point(232, 185);

    public static void 开启进阶BOSS线程() {
        召唤怪物();
        开启进阶BOSS线程伤害();
        if (进阶BOSS线程 == null) {
            进阶BOSS线程 = Timer.BuffTimer.getInstance().register(new Runnable() {
                @Override
                public void run() {
                    double 随机 = Math.ceil(Math.random() * 20);
                    if (随机 <= 0) {
                        全图掉HP();
                    } else if (随机 == 1) {
                        全图掉MP();
                    } else if (随机 == 2) {
                        全图封锁();
                    } else if (随机 == 3) {
                        全图黑暗();
                    } else if (随机 == 4) {
                        全图虚弱();
                    } else if (随机 == 5) {
                        全图诅咒();
                    } else if (随机 == 6) {
                        全图诱导();
                        减少血量();
                    } else if (随机 == 7) {
                        全图诱导();
                    } else if (随机 == 8) {
                        全图诱导();
                    } else if (随机 == 9) {
                        全图掉HP();
                        全图诱导();
                    } else if (随机 == 10 || 随机 == 11 || 随机 == 12 || 随机 == 13) {
                        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                                if (chr == null) {
                                    continue;
                                }
                                if (chr.getMapId() == 地图 && chr.getClient().getChannel() == 频道) {
                                    chr.startMapEffect("【进阶BOSS】 : 红蜗牛王使用出黑暗魔法，3秒后将吞噬在场所有人。", 5120027);
                                }
                            }
                        }
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000 * 3);
                                    直接死亡();
                                } catch (InterruptedException e) {
                                }
                            }
                        }.start();
                    } else if (随机 == 14 || 随机 == 15 || 随机 == 16 || 随机 == 17) {
                        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                                if (chr == null) {
                                    continue;
                                }
                                if (chr.getMapId() == 地图 && chr.getClient().getChannel() == 频道) {
                                    chr.startMapEffect("【进阶BOSS】 : 红蜗牛王使用出黑暗魔法，3秒后将驱散在场所有人。", 5120027);
                                }
                            }
                        }
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000 * 3);
                                    直接驱散();
                                } catch (InterruptedException e) {
                                }
                            }
                        }.start();
                    } else {
                        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                                if (chr == null) {
                                    continue;
                                }
                                if (chr.getMapId() == 地图 && chr.getClient().getChannel() == 频道) {
                                    chr.startMapEffect("【进阶BOSS】 : 红蜗牛王使用出黑暗魔法，诱导玩家可以互相攻击。", 5120027);
                                }
                            }
                        }
                        MapleParty.互相伤害 += 1;
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000 * 10);
                                    MapleParty.互相伤害 = 0;
                                } catch (InterruptedException e) {
                                }
                            }
                        }.start();

                    }
                }
            }, 1000 * 40);
        }
    }

    public static void 关闭进阶BOSS线程() {
        if (进阶BOSS线程 != null) {
            关闭进阶BOSS线程伤害();
            进阶BOSS线程.cancel(false);
            进阶BOSS线程 = null;
        }
    }

    public static void 开启进阶BOSS线程伤害() {
        if (进阶BOSS线程伤害 == null) {
            进阶BOSS线程伤害 = Timer.BuffTimer.getInstance().register(new Runnable() {
                @Override
                public void run() {
                    int 随机 = (int) Math.ceil(Math.random() * 2);
                    switch (随机) {
                        case 0:
                            减少血量();
                            break;
                        case 1:
                            减少蓝量();
                            break;
                        default:
                            减少血量();
                            减少蓝量();
                            break;
                    }
                }
            }, 1000 * 4);
        }
    }

    public static void 关闭进阶BOSS线程伤害() {
        if (进阶BOSS线程伤害 != null) {
            进阶BOSS线程伤害.cancel(false);
            进阶BOSS线程伤害 = null;
        }
    }

    public static void 直接驱散() {
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                if (chr == null) {
                    continue;
                }
                if (chr.getMapId() == 地图 && chr.getClient().getChannel() == 频道) {
                    double y = chr.getPosition().getY();
                    if (y == -355 || y == -85 || y == 185 || y == 455 || y == 395 || y == 335 || y == 515) {
                        MapleItemInformationProvider.getInstance().getItemEffect(2030000).applyReturnScroll(chr);
                        chr.dropMessage(5, "直接驱散");
                    } else {
                        chr.dropMessage(5, "躲避了直接驱散");
                    }
                }
            }
        }
    }

    public static void 直接死亡() {
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                if (chr == null) {
                    continue;
                }
                if (chr.getMapId() == 地图 && chr.getClient().getChannel() == 频道) {
                    double y = chr.getPosition().getY();
                    if (y == -355 || y == -85 || y == 185 || y == 455 || y == 395 || y == 335 || y == 515) {
                        chr.addHP(-30000);
                        chr.dropMessage(5, "直接死亡 HP - 999999999");
                    } else {
                        chr.dropMessage(5, "躲避了直接死亡");
                    }
                }
            }
        }
    }

    public static void 减少血量() {
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                if (chr == null) {
                    continue;
                }
                if (chr.getMapId() == 地图 && chr.getClient().getChannel() == 频道) {
                    double y = chr.getPosition().getY();
                    if (y == -355 || y == -85 || y == 185 || y == 455 || y == 395 || y == 335 || y == 515) {
                        int 血量 = (int) Math.ceil(Math.random() * 30000);
                        if (chr.getEquippedFuMoMap().get(21) != null) {
                            long 附魔减伤 = (long) (血量 / 100 * chr.getEquippedFuMoMap().get(21));
                            血量 -= 附魔减伤;
                        }
                        chr.addHP(-血量);
                        chr.dropMessage(5, "HP - " + 血量);
                    } else {
                        int 血量 = (int) Math.ceil(Math.random() * 30000);
                        if (chr.getEquippedFuMoMap().get(21) != null) {
                            long 附魔减伤 = (long) (血量 / 100 * chr.getEquippedFuMoMap().get(21));
                            血量 -= 附魔减伤;
                        }
                        chr.addHP(-血量 / 2);
                        chr.dropMessage(5, "HP - " + 血量 / 2);
                    }
                }
            }
        }
    }

    public static void 减少蓝量() {
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                if (chr == null) {
                    continue;
                }
                if (chr.getMapId() == 地图 && chr.getClient().getChannel() == 频道) {
                    double y = chr.getPosition().getY();
                    int 蓝量 = (int) Math.ceil(Math.random() * 30000);
                    if (y == -355 || y == -85 || y == 185 || y == 455 || y == 395 || y == 335 || y == 515) {
                        chr.addMP(-蓝量);
                        chr.dropMessage(5, "HP - " + 蓝量);
                    } else {
                        chr.addMP(-蓝量 / 2);
                        chr.dropMessage(5, "HP - " + 蓝量 / 2);
                    }
                }
            }
        }
    }

    public static void 全图诱导() {
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                if (chr == null) {
                    continue;
                }
                if (chr.getMapId() == 地图 && chr.getClient().getChannel() == 频道) {
                    if (chr.getJob() != 230 || chr.getJob() != 231 || chr.getJob() != 232) {
                        MobSkill mobSkill = MobSkillFactory.getMobSkill(128, 1);
                        MapleDisease disease = null;
                        disease = MapleDisease.getBySkill(128);
                        chr.giveDebuff(disease, mobSkill);
                        chr.dropMessage(5, "被诱导");
                    } else {
                        chr.dropMessage(5, "主教职业群，免疫被诱导");
                    }
                }
            }
        }
    }

    public static void 全图诅咒() {
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                if (chr == null) {
                    continue;
                }
                if (chr.getMapId() == 地图 && chr.getClient().getChannel() == 频道) {
                    MobSkill mobSkill = MobSkillFactory.getMobSkill(124, 1);
                    MapleDisease disease = null;
                    disease = MapleDisease.getBySkill(124);
                    chr.giveDebuff(disease, mobSkill);
                    chr.dropMessage(5, "被诅咒");
                }
            }
        }
    }

    public static void 全图虚弱() {
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                if (chr == null) {
                    continue;
                }
                if (chr.getMapId() == 地图 && chr.getClient().getChannel() == 频道) {
                    MobSkill mobSkill = MobSkillFactory.getMobSkill(122, 1);
                    MapleDisease disease = null;
                    disease = MapleDisease.getBySkill(122);
                    chr.giveDebuff(disease, mobSkill);
                    chr.dropMessage(5, "被虚弱");
                }
            }
        }
    }

    public static void 全图黑暗() {
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                if (chr == null) {
                    continue;
                }
                if (chr.getMapId() == 地图 && chr.getClient().getChannel() == 频道) {
                    MobSkill mobSkill = MobSkillFactory.getMobSkill(121, 1);
                    MapleDisease disease = null;
                    disease = MapleDisease.getBySkill(121);
                    chr.giveDebuff(disease, mobSkill);
                    chr.dropMessage(5, "被黑暗");
                }
            }
        }
    }

    public static void 全图封锁() {
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                if (chr == null) {
                    continue;
                }
                if (chr.getMapId() == 地图 && chr.getClient().getChannel() == 频道) {
                    MobSkill mobSkill = MobSkillFactory.getMobSkill(120, 1);
                    MapleDisease disease = null;
                    disease = MapleDisease.getBySkill(120);
                    chr.giveDebuff(disease, mobSkill);
                    chr.dropMessage(5, "被封锁");
                }
            }
        }

    }

    public static void 全图掉HP() {
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                if (chr == null) {
                    continue;
                }
                if (chr.getMapId() == 地图 && chr.getClient().getChannel() == 频道) {
                    chr.setHp(1);
                }
            }
        }
    }

    public static void 全图掉MP() {
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                if (chr == null) {
                    continue;
                }
                if (chr.getMapId() == 地图 && chr.getClient().getChannel() == 频道) {
                    chr.setMp(1);
                }
            }
        }
    }

    public static void 召唤怪物() {
        ChannelServer channelServer = ChannelServer.getInstance(频道);
        MapleMonster mapleMonster = MapleLifeFactory.getMonster(进阶红蜗牛长老);
        MapleMap mapleMap = channelServer.getMapFactory().getMap(地图);
        //设置怪物坐标
        mapleMonster.setPosition(坐标);
        mapleMap.spawnMonster(mapleMonster, -2);
    }
}
