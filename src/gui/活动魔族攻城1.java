package gui;

import client.MapleCharacter;
import handling.channel.ChannelServer;
import handling.world.MapleParty;
import handling.world.World;
import java.awt.Point;
import java.util.Calendar;
import java.util.concurrent.ScheduledFuture;
import server.Timer;
import server.life.MapleLifeFactory;
import server.life.MapleMonster;
import server.maps.MapleMap;
import static tools.FileoutputUtil.CurrentReadable_Time;
import tools.MaplePacketCreator;

public class 活动魔族攻城1 {

    public static int 蝙蝠魔 = 8150000;
    public static int 每波数量 = 40;//10波
    public static ScheduledFuture<?> 魔族攻城线程 = null;
    public static int 魔族攻城 = 0;
    public static ScheduledFuture<?> 射手村线程 = null;
    public static int 破坏射手村 = 0;
    public static ScheduledFuture<?> 勇士部落线程 = null;
    public static int 破坏勇士部落 = 0;
    public static ScheduledFuture<?> 魔法密林线程 = null;
    public static int 破坏魔法密林 = 0;
    public static ScheduledFuture<?> 废弃都市线程 = null;
    public static int 破坏废弃都市 = 0;
    public static int A = 0;
    public static int B = 0;
    public static int C = 0;
    public static int D = 0;
    public static int 破坏间隔 = 30;
    public static Point A1坐标 = new Point(3114, 125);
    public static Point A2坐标 = new Point(2835, 275);
    public static Point A3坐标 = new Point(6084, -176);
    public static Point B1坐标 = new Point(1597, 101);
    public static Point B2坐标 = new Point(1855, 124);
    public static Point B3坐标 = new Point(1669, 124);
    public static Point B4坐标 = new Point(1666, 125);
    public static Point B5坐标 = new Point(1320, 121);
    public static Point B6坐标 = new Point(-849, 373);
    public static Point C1坐标 = new Point(213, -48);
    public static Point C2坐标 = new Point(-1344, 23);
    public static Point C3坐标 = new Point(-359, 24);
    public static Point C4坐标 = new Point(-357, 825);
    public static Point C5坐标 = new Point(-1642, 1560);
    public static Point C6坐标 = new Point(-1091, -2806);
    public static Point D1坐标 = new Point(-415, 2025);
    public static Point D2坐标 = new Point(1372, 2145);
    public static Point D3坐标 = new Point(-437, 2025);
    public static Point D4坐标 = new Point(758, 2025);
    public static Point D5坐标 = new Point(2824, 1935);
    public static int 射手村防御成功 = 0;
    public static int 废弃都市防御成功 = 0;
    public static int 勇士部落防御成功 = 0;
    public static int 魔法密林防御成功 = 0;

    /**
     * <
     * 蝙蝠魔兵分四路攻击4大主城
     *
     * [A部队] 射手村迷宫路口106010100→迷宫通道106010000→射手村100000000
     * [B部队]→(107000400)鳄鱼潭二→(107000300)鳄鱼潭一→(107000200)沼泽地一→(107000100)沼泽地二→(107000000)沼泽地三→(103000000)废弃都市
     * [C部队] 大木林上层101010103→大木林Ⅲ101010102→大木林Ⅱ101010101→大木林I101010100→魔法密林北郊101010000→魔法密林101000000
     * [D部队] 勇士部落迷宫入口106000300→幽深峡谷Ⅲ106000200→幽深峡谷二106000100→幽深峡谷一106000000→勇士部落102000000
     * 1.攻城已设定
     * 2.里程碑未设置
     *
     *
     * >
     */
    public static void 魔族攻城线程() {
        if (魔族攻城线程 == null) {
            System.err.println("[服务端]" + CurrentReadable_Time() + "魔族攻城线程启动");
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[城镇警报] : 据有效情报，魔族大军正在2频道林中之城处集结，恐怕要兵临城下了，射手村，勇士部落，废弃都市，魔法密林，这四转城镇危在旦夕，向冒险家们发出援助请求。"));

            魔族攻城线程 = Timer.BuffTimer.getInstance().register(new Runnable() {
                @Override
                public void run() {
                    int 时 = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                    int 分 = Calendar.getInstance().get(Calendar.MINUTE);
                    //射手村
                    if (A < 3) {
                        if (射手村防御成功 == 0) {
                            攻城部队A();
                        }
                    }//废弃都市
                    if (B < 6) {
                        if (废弃都市防御成功 == 0) {
                            攻城部队B();
                        }
                    }
                    //魔法密林
                    if (C < 6) {
                        if (魔法密林防御成功 == 0) {
                            攻城部队C();
                        }
                    }
                    //勇士部落
                    if (D < 5) {
                        if (勇士部落防御成功 == 0) {
                            攻城部队D();
                        }
                    }
                    if (时 == 21 && 分 == 40) {
                        关闭魔族攻城();
                    }
                }
            }, 1000 * 10);
        }
    }

    public static void 关闭魔族攻城() {
        if (废弃都市线程 != null) {
            废弃都市线程.cancel(true);
            废弃都市线程 = null;
        }
    }

    /**
     * <蝙蝠魔进入射手村后开始破坏射手村>
     */
    public static void 破坏射手村() {
        if (射手村线程 == null) {
            射手村线程 = Timer.BuffTimer.getInstance().register(new Runnable() {
                @Override
                public void run() {
                    ChannelServer channelServer = ChannelServer.getInstance(2);
                    MapleMap mapleMap = channelServer.getMapFactory().getMap(100000000);
                    if (破坏射手村 < 10) {
                        if (mapleMap.getMonsterById(蝙蝠魔) != null) {
                            String 信息 = "射手村正在被破坏，射手村正在被破坏，被破坏····";
                            System.err.println("[服务端]" + CurrentReadable_Time() + " [城镇警报] : " + 信息);
                            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[城镇警报] : " + 信息));
                            破坏射手村++;
                        } else {
                            关闭破坏射手村(0);
                        }
                    } else {
                        关闭破坏射手村(1);
                    }
                }
            }, 1000 * 破坏间隔);
        }
    }

    public static void 关闭破坏射手村(int a) {
        if (射手村线程 != null) {
            射手村线程.cancel(true);
            射手村线程 = null;
            if (a == 1) {
                清怪(100000000);
                String 信息 = "攻击停止了，射手村繁荣度大大下降了";
                System.err.println("[服务端]" + CurrentReadable_Time() + " : [城镇警报] : " + 信息);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[城镇警报] : " + 信息));
            } else {
                String 信息 = "虽然射手村被破坏了，但还是挽救回来了一些";
                System.err.println("[服务端]" + CurrentReadable_Time() + " : [城镇警报] : " + 信息);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[城镇警报] : " + 信息));
            }
        }
    }

    public static void 射手村防御成功() {
        射手村防御成功 = 0;
        String 信息 = "勇士们成功防消灭了进攻射手村的魔族，恭喜你们了。";
        System.err.println("[服务端]" + CurrentReadable_Time() + " : [城镇通知] : " + 信息);
        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[城镇通知] : " + 信息));
    }

    /**
     * <蝙蝠魔进入废弃都市后开始破坏废弃都市>
     */
    public static void 破坏废弃都市() {
        if (废弃都市线程 == null) {
            废弃都市线程 = Timer.BuffTimer.getInstance().register(new Runnable() {
                @Override
                public void run() {
                    ChannelServer channelServer = ChannelServer.getInstance(2);
                    MapleMap mapleMap = channelServer.getMapFactory().getMap(103000000);
                    if (破坏废弃都市 <= 10) {
                        if (mapleMap.getMonsterById(蝙蝠魔) != null) {
                            String 信息 = "射手村正在被破坏，射手村正在被破坏，被破坏····";
                            System.err.println("[服务端]" + CurrentReadable_Time() + " [城镇警报] : " + 信息);
                            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[城镇警报] : " + 信息));
                            破坏废弃都市++;
                        } else {
                            关闭破坏废弃都市(0);
                        }
                    } else {
                        关闭破坏废弃都市(1);
                    }
                }
            }, 1000 * 破坏间隔);
        }
    }

    public static void 关闭破坏废弃都市(int a) {
        if (废弃都市线程 != null) {
            废弃都市线程.cancel(true);
            废弃都市线程 = null;
            if (a == 1) {
                清怪(103000000);
                String 信息 = "攻击停止了，废弃都市繁荣度大大下降了";
                System.err.println("[服务端]" + CurrentReadable_Time() + " : [城镇警报] : " + 信息);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[城镇警报] : " + 信息));
            } else {
                String 信息 = "虽然废弃都市被破坏了，但还是挽救回来了一些";
                System.err.println("[服务端]" + CurrentReadable_Time() + " : [城镇警报] : " + 信息);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[城镇警报] : " + 信息));
            }
        }
    }

    public static void 废弃都市防御成功() {
        废弃都市防御成功 = 0;
        String 信息 = "勇士们成功防消灭了进攻废弃都市的魔族，恭喜你们了。";
        System.err.println("[服务端]" + CurrentReadable_Time() + " : [城镇通知] : " + 信息);
        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[城镇通知] : " + 信息));
    }

    /**
     * <蝙蝠魔进入勇士部落后开始破坏勇士部落>
     */
    public static void 破坏勇士部落() {
        if (勇士部落线程 == null) {
            勇士部落线程 = Timer.BuffTimer.getInstance().register(new Runnable() {
                @Override
                public void run() {
                    ChannelServer channelServer = ChannelServer.getInstance(2);
                    MapleMap mapleMap = channelServer.getMapFactory().getMap(103000000);
                    if (破坏勇士部落 <= 10) {
                        if (mapleMap.getMonsterById(蝙蝠魔) != null) {
                            String 信息 = "勇士部落正在被破坏，勇士部落正在被破坏，被破坏····";
                            System.err.println("[服务端]" + CurrentReadable_Time() + " [城镇警报] : " + 信息);
                            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[城镇警报] : " + 信息));
                            破坏勇士部落++;
                        } else {
                            关闭破坏勇士部落(0);
                        }
                    } else {
                        关闭破坏勇士部落(1);
                    }
                }
            }, 1000 * 破坏间隔);
        }
    }

    public static void 关闭破坏勇士部落(int a) {
        if (勇士部落线程 != null) {
            勇士部落线程.cancel(true);
            勇士部落线程 = null;
            if (a == 1) {
                清怪(102000000);
                String 信息 = "攻击停止了，勇士部落繁荣度大大下降了";
                System.err.println("[服务端]" + CurrentReadable_Time() + " : [城镇警报] : " + 信息);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[城镇警报] : " + 信息));
            } else {
                String 信息 = "虽然勇士部落被破坏了，但还是挽救回来了一些";
                System.err.println("[服务端]" + CurrentReadable_Time() + " : [城镇警报] : " + 信息);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[城镇警报] : " + 信息));
            }
        }
    }

    public static void 勇士部落防御成功() {
        勇士部落防御成功 = 0;
        String 信息 = "勇士们成功防消灭了进攻勇士部落的魔族，恭喜你们了。";
        System.err.println("[服务端]" + CurrentReadable_Time() + " : [城镇通知] : " + 信息);
        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[城镇通知] : " + 信息));
    }

    /**
     * <蝙蝠魔进入魔法密林后开始破坏魔法密林>
     */
    public static void 破坏魔法密林() {
        if (魔法密林线程 == null) {
            魔法密林线程 = Timer.BuffTimer.getInstance().register(new Runnable() {
                @Override
                public void run() {
                    ChannelServer channelServer = ChannelServer.getInstance(2);
                    MapleMap mapleMap = channelServer.getMapFactory().getMap(103000000);
                    if (破坏魔法密林 < 10) {
                        if (mapleMap.getMonsterById(蝙蝠魔) != null) {
                            String 信息 = "魔法密林正在被破坏，魔法密林正在被破坏，被破坏····";
                            System.err.println("[服务端]" + CurrentReadable_Time() + " [城镇警报] : " + 信息);
                            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[城镇警报] : " + 信息));
                            破坏魔法密林++;
                        } else {
                            关闭破坏魔法密林(0);
                        }
                    } else {
                        关闭破坏魔法密林(1);
                    }
                }
            }, 1000 * 破坏间隔);
        }
    }

    public static void 关闭破坏魔法密林(int a) {
        if (魔法密林线程 != null) {
            魔法密林线程.cancel(true);
            魔法密林线程 = null;
            if (a == 1) {
                清怪(101000000);
                String 信息 = "攻击停止了，魔法密林繁荣度大大下降了";
                System.err.println("[服务端]" + CurrentReadable_Time() + " : [城镇警报] : " + 信息);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[城镇警报] : " + 信息));
            } else {
                String 信息 = "虽然魔法密林被破坏了，但还是挽救回来了一些";
                System.err.println("[服务端]" + CurrentReadable_Time() + " : [城镇警报] : " + 信息);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[城镇警报] : " + 信息));
            }

        }
    }

    public static void 魔法密林防御成功() {
        魔法密林防御成功 = 0;
        String 信息 = "勇士们成功防消灭了进攻魔法密林的魔族，恭喜你们了。";
        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[城镇通知] : " + 信息));
    }
    /**
     * <
     * A部队进军射手村
     *[A部队]
     * →(106010100)射手村迷宫入口
     * →(106010000)迷宫通道
     * →(100000000)射手村
     * >
     */
    public static int A队数量 = 0;

    public static void 攻城部队A() {
        int 时 = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int 分 = Calendar.getInstance().get(Calendar.MINUTE);
        /**
         * <#################################第一图###################################>
         */
        if (时 == 21 && 分 == 10 && A == 0) {
            //初始化蝙蝠魔数量
            MapleParty.蝙蝠魔A部队 = 每波数量 * 10;
            final int 路 = 1;
            /**
             * <第一波怪物出现>
             */
            for (int i = 0; i <= 每波数量; i++) {
                攻城部队A(1);
            }
            /**
             * <第二波怪物 30 秒后出现>
             */
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 30);
                        for (int i = 0; i <= 每波数量; i++) {
                            攻城部队A(路);
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }.start();
            /**
             * <第三波怪物 60 秒后出现>
             */
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 60);
                        for (int i = 0; i <= 每波数量; i++) {
                            攻城部队A(路);
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }.start();
            /**
             * <第四波怪物 90 秒后出现>
             */
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 90);
                        for (int i = 0; i <= 每波数量; i++) {
                            攻城部队A(路);
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }.start();
            /**
             * <第五波怪物 120 秒后出现>
             */
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 120);
                        for (int i = 0; i <= 每波数量; i++) {
                            攻城部队A(路);
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }.start();
            /**
             * <第六波怪物 150 秒后出现>
             */
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 150);
                        for (int i = 0; i <= 每波数量; i++) {
                            攻城部队A(路);
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }.start();
            /**
             * <第七波怪物 180 秒后出现>
             */
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 180);
                        for (int i = 0; i <= 每波数量; i++) {
                            攻城部队A(路);
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }.start();
            /**
             * <第八波怪物 210 秒后出现>
             */
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 210);
                        for (int i = 0; i <= 每波数量; i++) {
                            攻城部队A(路);
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }.start();
            /**
             * <第九波怪物 240 秒后出现>
             */
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 240);
                        for (int i = 0; i <= 每波数量; i++) {
                            攻城部队A(路);
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }.start();
            /**
             * <第十波怪物 270 秒后出现>
             */
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 270);
                        for (int i = 0; i <= 每波数量; i++) {
                            攻城部队A(路);
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }.start();
             /**
             * < 300 秒后结算>
             */
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 300);
                        
                    } catch (InterruptedException e) {
                    }
                }
            }.start();
        }
    }

    /**
     * <
     * B部队进军废弃都市
     *[B部队]
     * →(107000400)鳄鱼潭二
     * →(107000300)鳄鱼潭一
     * →(107000200)沼泽地一
     * →(107000100)沼泽地二
     * →(107000000)沼泽地三
     * →(103000000)废弃都市
     * >
     */
    public static int B队数量 = 0;

    public static void 攻城部队B() {
        int 时 = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int 分 = Calendar.getInstance().get(Calendar.MINUTE);
        /**
         * <#################################第一图###################################>
         */
        if (时 == 21 && 分 == 10 && B == 0) {
            //初始化蝙蝠魔数量
            MapleParty.蝙蝠魔B部队 = 每波数量;
            final int 路 = 1;
            B队数量 = MapleParty.蝙蝠魔B部队;
            /**
             * <第一波怪物出现>
             */
            for (int i = 0; i <= B队数量 / 4; i++) {
                攻城部队B(路);
            }
            /**
             * <第二波怪物 60 秒后出现>
             */
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 60);
                        for (int i = 0; i <= B队数量 / 4; i++) {
                            攻城部队B(路);
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }.start();
            /**
             * <第三波怪物 120 秒后出现>
             */
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 120);
                        for (int i = 0; i <= B队数量 / 4; i++) {
                            攻城部队B(路);
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }.start();
            /**
             * <第四波怪物 180 秒后出现>
             */
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 180);
                        for (int i = 0; i <= B队数量 / 4; i++) {
                            攻城部队B(路);
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }.start();
            String 信息 = "(B部队)已经抵达鳄鱼潭二，正在向废弃都市逼近";
            System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : " + 信息);
            System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : 部队兵力 " + MapleParty.蝙蝠魔B部队);
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[魔族攻城] : " + 信息));
            B = 1;
            /**
             * <#################################第二图###################################>
             */
        } else if (时 == 21 && 分 == 15 && B == 1) {
            if (MapleParty.蝙蝠魔B部队 == 0) {
                废弃都市防御成功();
            } else {
                final int 路 = 2;
                B队数量 = MapleParty.蝙蝠魔B部队;
                /**
                 * <第一波怪物出现>
                 */
                for (int i = 0; i <= B队数量 / 4; i++) {
                    攻城部队B(路);
                }
                /**
                 * <第二波怪物 60 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 60);
                            for (int i = 0; i <= B队数量 / 4; i++) {
                                攻城部队B(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第三波怪物 120 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 120);
                            for (int i = 0; i <= B队数量 / 4; i++) {
                                攻城部队B(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第四波怪物 180 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 180);
                            for (int i = 0; i <= B队数量 / 4; i++) {
                                攻城部队B(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                String 信息 = "(B部队)已经抵达鳄鱼潭一，正在向废弃都市逼近";
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : " + 信息);
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : 部队兵力 " + MapleParty.蝙蝠魔B部队);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[魔族攻城] : " + 信息));
                B = 2;
                清怪(107000400);
            }
            /**
             * <#################################第三图###################################>
             */
        } else if (时 == 21 && 分 == 20 && B == 2) {
            if (MapleParty.蝙蝠魔B部队 == 0) {
                废弃都市防御成功();
            } else {
                final int 路 = 3;
                B队数量 = MapleParty.蝙蝠魔B部队;
                /**
                 * <第一波怪物出现>
                 */
                for (int i = 0; i <= B队数量 / 4; i++) {
                    攻城部队B(路);
                }
                /**
                 * <第二波怪物 60 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 60);
                            for (int i = 0; i <= B队数量 / 4; i++) {
                                攻城部队B(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第三波怪物 120 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 120);
                            for (int i = 0; i <= B队数量 / 4; i++) {
                                攻城部队B(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第四波怪物 180 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 180);
                            for (int i = 0; i <= B队数量 / 4; i++) {
                                攻城部队B(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                String 信息 = "(B部队)已经抵达沼泽地III，正在向废弃都市逼近";
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : " + 信息);
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : 部队兵力 " + MapleParty.蝙蝠魔B部队);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[魔族攻城] : " + 信息));
                B = 3;
                清怪(107000300);
            }
            /**
             * <#################################第四图###################################>
             */
        } else if (时 == 21 && 分 == 25 && B == 3) {
            if (MapleParty.蝙蝠魔B部队 == 0) {
                废弃都市防御成功();
            } else {
                final int 路 = 4;
                B队数量 = MapleParty.蝙蝠魔B部队;
                /**
                 * <第一波怪物出现>
                 */
                for (int i = 0; i <= B队数量 / 4; i++) {
                    攻城部队B(路);
                }
                /**
                 * <第二波怪物 60 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 60);
                            for (int i = 0; i <= B队数量 / 4; i++) {
                                攻城部队B(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第三波怪物 120 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 120);
                            for (int i = 0; i <= B队数量 / 4; i++) {
                                攻城部队B(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第四波怪物 180 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 180);
                            for (int i = 0; i <= B队数量 / 4; i++) {
                                攻城部队B(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                String 信息 = "(B部队)已经抵达沼泽地II，正在向废弃都市逼近";
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : " + 信息);
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : 部队兵力 " + MapleParty.蝙蝠魔B部队);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[魔族攻城] : " + 信息));
                B = 4;
                清怪(107000200);
            }
            /**
             * <#################################第五图###################################>
             */
        } else if (时 == 21 && 分 == 30 && B == 4) {
            if (MapleParty.蝙蝠魔B部队 == 0) {
                废弃都市防御成功();
            } else {
                final int 路 = 5;
                B队数量 = MapleParty.蝙蝠魔B部队;
                /**
                 * <第一波怪物出现>
                 */
                for (int i = 0; i <= B队数量 / 4; i++) {
                    攻城部队B(路);
                }
                /**
                 * <第二波怪物 60 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 60);
                            for (int i = 0; i <= B队数量 / 4; i++) {
                                攻城部队B(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第三波怪物 120 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 120);
                            for (int i = 0; i <= B队数量 / 4; i++) {
                                攻城部队B(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第四波怪物 180 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 180);
                            for (int i = 0; i <= B队数量 / 4; i++) {
                                攻城部队B(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                String 信息 = "(B部队)已经抵达沼泽地I，正在向废弃都市逼近";
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : " + 信息);
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : 部队兵力 " + MapleParty.蝙蝠魔B部队);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[魔族攻城] : " + 信息));
                B = 5;
                清怪(107000100);
            }
            /**
             * <#################################第六图###################################>
             */
        } else if (时 == 21 && 分 == 35 && B == 5) {
            if (MapleParty.蝙蝠魔B部队 == 0) {
                废弃都市防御成功();
            } else {
                final int 路 = 6;
                B队数量 = MapleParty.蝙蝠魔B部队;
                /**
                 * <第一波怪物出现>
                 */
                for (int i = 0; i <= B队数量 / 4; i++) {
                    攻城部队B(路);
                }
                /**
                 * <第二波怪物 60 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 60);
                            for (int i = 0; i <= B队数量 / 4; i++) {
                                攻城部队B(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第三波怪物 120 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 120);
                            for (int i = 0; i <= B队数量 / 4; i++) {
                                攻城部队B(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第四波怪物 180 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 180);
                            for (int i = 0; i <= B队数量 / 4; i++) {
                                攻城部队B(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                String 信息 = "(B部队)已经抵达废弃都市，正在破坏废弃都市";
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : " + 信息);
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : 部队兵力 " + MapleParty.蝙蝠魔B部队);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[魔族攻城] : " + 信息));
                B = 6;
                清怪(107000000);
                破坏废弃都市();
            }
        }
    }

    /**
     * <
     * C部队进军魔法密林
     *[C部队]
     * →(101010103)大木林上层
     * →(101010102)大木林Ⅲ
     * →(101010101)大木林Ⅱ
     * →(101010100)大木林I
     * →(101010000)魔法密林北郊
     * →(101000000)魔法密林
     * >
     */
    public static int C队数量 = 0;

    public static void 攻城部队C() {
        int 时 = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int 分 = Calendar.getInstance().get(Calendar.MINUTE);
        /**
         * <#################################第一图###################################>
         */
        if (时 == 21 && 分 == 10 && C == 0) {
            MapleParty.蝙蝠魔C部队 = 每波数量;
            final int 路 = 1;
            C队数量 = MapleParty.蝙蝠魔C部队;
            /**
             * <第一波怪物出现>
             */
            for (int i = 0; i <= C队数量 / 4; i++) {
                攻城部队C(路);
            }
            /**
             * <第二波怪物 60 秒后出现>
             */
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 60);
                        for (int i = 0; i <= C队数量 / 4; i++) {
                            攻城部队C(路);
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }.start();
            /**
             * <第三波怪物 120 秒后出现>
             */
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 120);
                        for (int i = 0; i <= C队数量 / 4; i++) {
                            攻城部队C(路);
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }.start();
            /**
             * <第四波怪物 180 秒后出现>
             */
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 180);
                        for (int i = 0; i <= C队数量 / 4; i++) {
                            攻城部队C(路);
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }.start();
            String 信息 = "(C部队)已经抵达大木林上层，正在向魔法密林逼近";
            System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : " + 信息);
            System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : 部队兵力 " + MapleParty.蝙蝠魔C部队);
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[魔族攻城] : " + 信息));
            C = 1;
            /**
             * <#################################第二图###################################>
             */
        } else if (时 == 21 && 分 == 15 && C == 1) {
            if (MapleParty.蝙蝠魔C部队 == 0) {
                魔法密林防御成功();
            } else {
                final int 路 = 2;
                C队数量 = MapleParty.蝙蝠魔C部队;
                /**
                 * <第一波怪物出现>
                 */
                for (int i = 0; i <= C队数量 / 4; i++) {
                    攻城部队C(路);
                }
                /**
                 * <第二波怪物 60 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 60);
                            for (int i = 0; i <= C队数量 / 4; i++) {
                                攻城部队C(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第三波怪物 120 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 120);
                            for (int i = 0; i <= C队数量 / 4; i++) {
                                攻城部队C(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第四波怪物 180 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 180);
                            for (int i = 0; i <= C队数量 / 4; i++) {
                                攻城部队C(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                String 信息 = "(C部队)已经抵达大木林Ⅲ，正在向魔法密林逼近";
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : " + 信息);
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : 部队兵力 " + MapleParty.蝙蝠魔C部队);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[魔族攻城] : " + 信息));
                C = 2;
                清怪(101010103);
            }
            /**
             * <#################################第三图###################################>
             */
        } else if (时 == 21 && 分 == 20 && C == 2) {
            if (MapleParty.蝙蝠魔C部队 == 0) {
                魔法密林防御成功();
            } else {
                final int 路 = 3;
                C队数量 = MapleParty.蝙蝠魔C部队;
                /**
                 * <第一波怪物出现>
                 */
                for (int i = 0; i <= C队数量 / 4; i++) {
                    攻城部队C(路);
                }
                /**
                 * <第二波怪物 60 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 60);
                            for (int i = 0; i <= C队数量 / 4; i++) {
                                攻城部队C(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第三波怪物 120 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 120);
                            for (int i = 0; i <= C队数量 / 4; i++) {
                                攻城部队C(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第四波怪物 180 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 180);
                            for (int i = 0; i <= C队数量 / 4; i++) {
                                攻城部队C(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                String 信息 = "(C部队)已经抵达大木林Ⅱ，正在向魔法密林逼近";
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : " + 信息);
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : 部队兵力 " + MapleParty.蝙蝠魔C部队);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[魔族攻城] : " + 信息));
                C = 3;
                清怪(101010102);
            }
            /**
             * <#################################第四图###################################>
             */
        } else if (时 == 21 && 分 == 25 && C == 3) {
            if (MapleParty.蝙蝠魔C部队 == 0) {
                魔法密林防御成功();
            } else {
                final int 路 = 4;
                C队数量 = MapleParty.蝙蝠魔C部队;
                /**
                 * <第一波怪物出现>
                 */
                for (int i = 0; i <= C队数量 / 4; i++) {
                    攻城部队C(路);
                }
                /**
                 * <第二波怪物 60 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 60);
                            for (int i = 0; i <= C队数量 / 4; i++) {
                                攻城部队C(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第三波怪物 120 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 120);
                            for (int i = 0; i <= C队数量 / 4; i++) {
                                攻城部队C(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第四波怪物 180 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 180);
                            for (int i = 0; i <= C队数量 / 4; i++) {
                                攻城部队C(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                String 信息 = "(C部队)已经抵达大木林I，正在向魔法密林逼近";
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : " + 信息);
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : 部队兵力 " + MapleParty.蝙蝠魔C部队);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[魔族攻城] : " + 信息));
                C = 4;
                清怪(101010101);
            }
            /**
             * <#################################第五图###################################>
             */
        } else if (时 == 21 && 分 == 30 && C == 4) {
            if (MapleParty.蝙蝠魔C部队 == 0) {
                魔法密林防御成功();
            } else {
                final int 路 = 5;
                C队数量 = MapleParty.蝙蝠魔C部队;
                /**
                 * <第一波怪物出现>
                 */
                for (int i = 0; i <= C队数量 / 4; i++) {
                    攻城部队C(路);
                }
                /**
                 * <第二波怪物 60 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 60);
                            for (int i = 0; i <= C队数量 / 4; i++) {
                                攻城部队C(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第三波怪物 120 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 120);
                            for (int i = 0; i <= C队数量 / 4; i++) {
                                攻城部队C(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第四波怪物 180 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 180);
                            for (int i = 0; i <= C队数量 / 4; i++) {
                                攻城部队C(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                String 信息 = "(C部队)已经抵达魔法密林北郊，正在向魔法密林逼近";
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : " + 信息);
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : 部队兵力 " + MapleParty.蝙蝠魔C部队);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[魔族攻城] : " + 信息));
                C = 5;
                清怪(101010100);
            }
            /**
             * <#################################第六图###################################>
             */
        } else if (时 == 21 && 分 == 35 && C == 5) {
            if (MapleParty.蝙蝠魔C部队 == 0) {
                魔法密林防御成功();
            } else {
                final int 路 = 6;
                C队数量 = MapleParty.蝙蝠魔C部队;
                /**
                 * <第一波怪物出现>
                 */
                for (int i = 0; i <= C队数量 / 4; i++) {
                    攻城部队C(路);
                }
                /**
                 * <第二波怪物 60 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 60);
                            for (int i = 0; i <= C队数量 / 4; i++) {
                                攻城部队C(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第三波怪物 120 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 120);
                            for (int i = 0; i <= C队数量 / 4; i++) {
                                攻城部队C(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第四波怪物 180 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 180);
                            for (int i = 0; i <= C队数量 / 4; i++) {
                                攻城部队C(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                String 信息 = "(C部队)已经抵达魔法密林，正在破坏魔法密林";
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : " + 信息);
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : 部队兵力 " + MapleParty.蝙蝠魔C部队);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[魔族攻城] : " + 信息));
                C = 6;
                清怪(101010000);
                破坏魔法密林();
            }
        }
    }

    /**
     * <
     * D部队进军勇士部落
     * →(106000300)勇士部落迷宫入口
     * →(106000200)幽深峡谷Ⅲ
     * →(106000100)幽深峡谷二
     * →(106000000)幽深峡谷一
     * →(102000000)勇士部落
     *
     * >
     */
    public static int D队数量 = 0;

    public static void 攻城部队D() {
        int 时 = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int 分 = Calendar.getInstance().get(Calendar.MINUTE);
        /**
         * <#################################第一图###################################>
         */
        if (时 == 21 && 分 == 10 && D == 0) {
            //初始化蝙蝠魔数量
            MapleParty.蝙蝠魔D部队 = 每波数量;
            final int 路 = 1;
            D队数量 = MapleParty.蝙蝠魔D部队;
            /**
             * <第一波怪物出现>
             */
            for (int i = 0; i <= D队数量 / 4; i++) {
                攻城部队B(路);
            }
            /**
             * <第二波怪物 60 秒后出现>
             */
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 60);
                        for (int i = 0; i <= D队数量 / 4; i++) {
                            攻城部队D(路);
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }.start();
            /**
             * <第三波怪物 120 秒后出现>
             */
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 120);
                        for (int i = 0; i <= D队数量 / 4; i++) {
                            攻城部队D(路);
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }.start();
            /**
             * <第四波怪物 180 秒后出现>
             */
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 180);
                        for (int i = 0; i <= B队数量 / 4; i++) {
                            攻城部队D(路);
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }.start();
            String 信息 = "(D部队)已经抵达勇士部落迷宫入口，正在向勇士部落逼近";
            System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : " + 信息);
            System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : 部队兵力 " + MapleParty.蝙蝠魔D部队);
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[魔族攻城] : " + 信息));
            D = 1;
            /**
             * <#################################第二图###################################>
             */
        } else if (时 == 21 && 分 == 15 && D == 1) {
            if (MapleParty.蝙蝠魔D部队 == 0) {
                勇士部落防御成功();
            } else {
                final int 路 = 2;
                D队数量 = MapleParty.蝙蝠魔D部队;
                /**
                 * <第一波怪物出现>
                 */
                for (int i = 0; i <= D队数量 / 4; i++) {
                    攻城部队B(路);
                }
                /**
                 * <第二波怪物 60 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 60);
                            for (int i = 0; i <= D队数量 / 4; i++) {
                                攻城部队D(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第三波怪物 120 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 120);
                            for (int i = 0; i <= D队数量 / 4; i++) {
                                攻城部队D(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第四波怪物 180 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 180);
                            for (int i = 0; i <= B队数量 / 4; i++) {
                                攻城部队D(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                String 信息 = "(D部队)已经抵达幽深峡谷Ⅲ，正在向勇士部落逼近";
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : " + 信息);
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : 部队兵力 " + MapleParty.蝙蝠魔D部队);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[魔族攻城] : " + 信息));
                D = 2;
                清怪(106000300);
            }
            /**
             * <#################################第三图###################################>
             */
        } else if (时 == 21 && 分 == 20 && D == 2) {
            if (MapleParty.蝙蝠魔D部队 == 0) {
                勇士部落防御成功();
            } else {
                final int 路 = 3;
                D队数量 = MapleParty.蝙蝠魔D部队;
                /**
                 * <第一波怪物出现>
                 */
                for (int i = 0; i <= D队数量 / 4; i++) {
                    攻城部队B(路);
                }
                /**
                 * <第二波怪物 60 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 60);
                            for (int i = 0; i <= D队数量 / 4; i++) {
                                攻城部队D(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第三波怪物 120 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 120);
                            for (int i = 0; i <= D队数量 / 4; i++) {
                                攻城部队D(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第四波怪物 180 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 180);
                            for (int i = 0; i <= B队数量 / 4; i++) {
                                攻城部队D(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                String 信息 = "(D部队)已经抵达幽深峡谷Ⅱ，正在向勇士部落逼近";
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : " + 信息);
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : 部队兵力 " + MapleParty.蝙蝠魔D部队);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[魔族攻城] : " + 信息));
                D = 3;
                清怪(106000200);
            }
            /**
             * <#################################第四图###################################>
             */
        } else if (时 == 21 && 分 == 25 && D == 3) {
            if (MapleParty.蝙蝠魔D部队 == 0) {
                勇士部落防御成功();
            } else {
                final int 路 = 4;
                D队数量 = MapleParty.蝙蝠魔D部队;
                /**
                 * <第一波怪物出现>
                 */
                for (int i = 0; i <= D队数量 / 4; i++) {
                    攻城部队B(路);
                }
                /**
                 * <第二波怪物 60 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 60);
                            for (int i = 0; i <= D队数量 / 4; i++) {
                                攻城部队D(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第三波怪物 120 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 120);
                            for (int i = 0; i <= D队数量 / 4; i++) {
                                攻城部队D(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第四波怪物 180 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 180);
                            for (int i = 0; i <= B队数量 / 4; i++) {
                                攻城部队D(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                String 信息 = "(D部队)已经抵达幽深峡谷Ⅰ，正在向勇士部落逼近";
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : " + 信息);
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : 部队兵力 " + MapleParty.蝙蝠魔D部队);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[魔族攻城] : " + 信息));
                D = 4;
                清怪(106000100);
            }
            /**
             * <#################################第五图###################################>
             */
        } else if (时 == 21 && 分 == 30 && D == 4) {
            if (MapleParty.蝙蝠魔D部队 == 0) {
                勇士部落防御成功();
            } else {
                final int 路 = 5;
                D队数量 = MapleParty.蝙蝠魔D部队;
                /**
                 * <第一波怪物出现>
                 */
                for (int i = 0; i <= D队数量 / 4; i++) {
                    攻城部队B(路);
                }
                /**
                 * <第二波怪物 60 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 60);
                            for (int i = 0; i <= D队数量 / 4; i++) {
                                攻城部队D(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第三波怪物 120 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 120);
                            for (int i = 0; i <= D队数量 / 4; i++) {
                                攻城部队D(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                /**
                 * <第四波怪物 180 秒后出现>
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 180);
                            for (int i = 0; i <= B队数量 / 4; i++) {
                                攻城部队D(路);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();
                String 信息 = "(D部队)已经抵达勇士部落，正在破坏勇士部落";
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : " + 信息);
                System.err.println("[服务端]" + CurrentReadable_Time() + " [魔族攻城] : 部队兵力 " + MapleParty.蝙蝠魔D部队);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[魔族攻城] : " + 信息));
                D = 5;
                清怪(106000000);
                破坏勇士部落();
            }
        }
    }

    /**
     * <D部队进军> @param a
     *
     * @param a
     */
    public static void 攻城部队D(int a) {
        ChannelServer channelServer = ChannelServer.getInstance(2);
        MapleMonster mapleMonster = MapleLifeFactory.getMonster(蝙蝠魔);
        switch (a) {
            //勇士部落迷宫入口
            case 1: {
                int 地图 = 106000300;
                MapleMap mapleMap = channelServer.getMapFactory().getMap(地图);
                mapleMonster.setPosition(D1坐标);
                mapleMap.spawnMonster(mapleMonster, -2);
                break;
            }
            //幽深峡谷Ⅲ
            case 2: {
                int 地图 = 106000200;
                MapleMap mapleMap = channelServer.getMapFactory().getMap(地图);
                mapleMonster.setPosition(D2坐标);
                mapleMap.spawnMonster(mapleMonster, -2);
                break;
            }
            //幽深峡谷二
            case 3: {
                int 地图 = 106000100;
                MapleMap mapleMap = channelServer.getMapFactory().getMap(地图);
                mapleMonster.setPosition(D3坐标);
                mapleMap.spawnMonster(mapleMonster, -2);
                break;
            }
            //幽深峡谷一
            case 4: {
                int 地图 = 106000000;
                MapleMap mapleMap = channelServer.getMapFactory().getMap(地图);
                mapleMonster.setPosition(D4坐标);
                mapleMap.spawnMonster(mapleMonster, -2);
                break;
            }
            //勇士部落
            case 5: {
                int 地图 = 102000000;
                MapleMap mapleMap = channelServer.getMapFactory().getMap(地图);
                mapleMonster.setPosition(D5坐标);
                mapleMap.spawnMonster(mapleMonster, -2);
                break;
            }
            default:
                break;
        }
    }

    /**
     * <C部队进军> @param a
     */
    public static void 攻城部队C(int a) {
        ChannelServer channelServer = ChannelServer.getInstance(2);
        MapleMonster mapleMonster = MapleLifeFactory.getMonster(蝙蝠魔);
        switch (a) {
            //大木林上层
            case 1: {
                int 地图 = 101010103;
                MapleMap mapleMap = channelServer.getMapFactory().getMap(地图);
                mapleMonster.setPosition(C1坐标);
                mapleMap.spawnMonster(mapleMonster, -2);
                break;
            }
            //大木林Ⅲ
            case 2: {
                int 地图 = 101010102;
                MapleMap mapleMap = channelServer.getMapFactory().getMap(地图);
                mapleMonster.setPosition(C2坐标);
                mapleMap.spawnMonster(mapleMonster, -2);
                break;
            }
            //大木林Ⅱ
            case 3: {
                int 地图 = 101010101;
                MapleMap mapleMap = channelServer.getMapFactory().getMap(地图);
                mapleMonster.setPosition(C3坐标);
                mapleMap.spawnMonster(mapleMonster, -2);
                break;
            }
            //大木林I
            case 4: {
                int 地图 = 101010100;
                MapleMap mapleMap = channelServer.getMapFactory().getMap(地图);
                mapleMonster.setPosition(C4坐标);
                mapleMap.spawnMonster(mapleMonster, -2);
                break;
            }
            //魔法密林北郊
            case 5: {
                int 地图 = 101010000;
                MapleMap mapleMap = channelServer.getMapFactory().getMap(地图);
                mapleMonster.setPosition(C5坐标);
                mapleMap.spawnMonster(mapleMonster, -2);
                break;
            }
            //魔法密林
            case 6: {
                int 地图 = 101000000;
                MapleMap mapleMap = channelServer.getMapFactory().getMap(地图);
                mapleMonster.setPosition(C6坐标);
                mapleMap.spawnMonster(mapleMonster, -2);
                break;
            }
            default:
                break;
        }
    }

    /**
     * <B部队进军> @param a
     */
    public static void 攻城部队B(int a) {
        ChannelServer channelServer = ChannelServer.getInstance(2);
        MapleMonster mapleMonster = MapleLifeFactory.getMonster(蝙蝠魔);
        switch (a) {
            //鳄鱼潭二
            case 1: {
                int 地图 = 107000400;
                MapleMap mapleMap = channelServer.getMapFactory().getMap(地图);
                mapleMonster.setPosition(B1坐标);
                mapleMap.spawnMonster(mapleMonster, -2);
                break;
            }
            //鳄鱼潭一
            case 2: {
                int 地图 = 107000300;
                MapleMap mapleMap = channelServer.getMapFactory().getMap(地图);
                mapleMonster.setPosition(B2坐标);
                mapleMap.spawnMonster(mapleMonster, -2);
                break;
            }
            //沼泽地一
            case 3: {
                int 地图 = 107000200;
                MapleMap mapleMap = channelServer.getMapFactory().getMap(地图);
                mapleMonster.setPosition(B3坐标);
                mapleMap.spawnMonster(mapleMonster, -2);
                break;
            }
            //沼泽地二
            case 4: {
                int 地图 = 107000100;
                MapleMap mapleMap = channelServer.getMapFactory().getMap(地图);
                mapleMonster.setPosition(B4坐标);
                mapleMap.spawnMonster(mapleMonster, -2);
                break;
            }
            //沼泽地三
            case 5: {
                int 地图 = 107000000;
                MapleMap mapleMap = channelServer.getMapFactory().getMap(地图);
                mapleMonster.setPosition(B5坐标);
                mapleMap.spawnMonster(mapleMonster, -2);
                break;
            }
            //废弃都市
            case 6: {
                int 地图 = 103000000;
                MapleMap mapleMap = channelServer.getMapFactory().getMap(地图);
                mapleMonster.setPosition(B6坐标);
                mapleMap.spawnMonster(mapleMonster, -2);
                break;
            }
            default:
                break;
        }
    }

    /**
     * <A部队进军> @param a
     */
    public static void 攻城部队A(int a) {
        ChannelServer channelServer = ChannelServer.getInstance(2);
        MapleMonster mapleMonster = MapleLifeFactory.getMonster(蝙蝠魔);
        switch (a) {
            //射手村迷宫入口
            case 1: {
                int 地图 = 106010100;
                MapleMap mapleMap = channelServer.getMapFactory().getMap(地图);
                mapleMonster.setPosition(A1坐标);
                mapleMap.spawnMonster(mapleMonster, -2);
                break;
            }
            //迷宫通道
            case 2: {
                int 地图 = 106010000;
                MapleMap mapleMap = channelServer.getMapFactory().getMap(地图);
                mapleMonster.setPosition(A2坐标);
                mapleMap.spawnMonster(mapleMonster, -2);
                break;
            }
            //射手村
            case 3: {
                int 地图 = 100000000;
                MapleMap mapleMap = channelServer.getMapFactory().getMap(地图);
                mapleMonster.setPosition(A3坐标);
                mapleMap.spawnMonster(mapleMonster, -2);
                break;
            }

            default:
                break;
        }
    }

    /**
     * <清怪，地图怪物，并刷新地图玩家>
     */
    public static void 清怪(int a) {
        ChannelServer channelServer = ChannelServer.getInstance(2);
        MapleMap mapleMap = channelServer.getMapFactory().getMap(a);
        mapleMap.resetFully();
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                if (chr == null) {
                    continue;
                }
                if (chr.getMapId() == a) {
                    chr.changeMap(a, 0);
                }
            }
        }
    }

}
