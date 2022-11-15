package gui;

import client.MapleCharacter;
import database.DatabaseConnection;
import static gui.活动魔族攻城1.A1坐标;
import static gui.活动魔族攻城1.A2坐标;
import static gui.活动魔族攻城1.A3坐标;
import static gui.活动魔族攻城1.蝙蝠魔;
import handling.channel.ChannelServer;
import handling.world.MapleParty;
import handling.world.World;
import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.concurrent.ScheduledFuture;
import server.Timer;
import server.life.MapleLifeFactory;
import server.life.MapleMonster;
import server.maps.MapleMap;
import static tools.FileoutputUtil.CurrentReadable_Time;
import tools.MaplePacketCreator;

public class 活动魔族攻城 {

    public static int 蝙蝠魔 = 8150000;
    public static int 每波数量 = 20;//10波
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
                    if (时 == 21 && 分 == 10) {
                        if (射手村1 == null) {
                            射手村攻城1();
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
    public static ScheduledFuture<?> 射手村1 = null;
    public static ScheduledFuture<?> 射手村2 = null;
    public static ScheduledFuture<?> 射手村3 = null;
    public static int A出数 = 0;

    public static void 射手村攻城1() {
        if (射手村1 == null) {
            String 信息 = "蝙蝠魔大军正在进行射手村迷宫入口集结 ";
            System.err.println("[服务端]" + CurrentReadable_Time() + " : [城镇警报] : " + 信息);
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[城镇警报] : " + 信息));
            射手村1 = Timer.BuffTimer.getInstance().register(new Runnable() {
                @Override
                public void run() {
                    if (MapleParty.蝙蝠魔A部队 <= 500) {
                        攻城部队A(1);
                        MapleParty.蝙蝠魔A部队++;
                    } else if (射手村1 != null) {
                        射手村1.cancel(true);
                        射手村1 = null;
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000 * 60);
                                    if (MapleParty.蝙蝠魔A部队 > 0) {
                                        清怪(106010100);
                                        射手村攻城2();
                                    } else {
                                        射手村防御成功();
                                    }
                                } catch (InterruptedException e) {
                                }
                            }
                        }.start();
                    }
                }
            }, 1000 * 1);
        }
    }
    public static int 射手村2出数 = 0;

    public static void 射手村攻城2() {
        if (射手村2 == null) {
            String 信息 = "蝙蝠魔大军已经抵达迷宫通道，此次兵力大概 " + MapleParty.蝙蝠魔A部队 + " ";
            System.err.println("[服务端]" + CurrentReadable_Time() + " : [城镇警报] : " + 信息);
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[城镇警报] : " + 信息));
            射手村2 = Timer.BuffTimer.getInstance().register(new Runnable() {
                @Override
                public void run() {
                    if (射手村2出数 <= MapleParty.蝙蝠魔A部队) {
                        攻城部队A(2);
                        射手村2出数++;
                    } else if (射手村2 != null) {
                        射手村2.cancel(true);
                        射手村2 = null;
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000 * 60);
                                    if (MapleParty.蝙蝠魔A部队 > 0) {
                                        清怪(106010000);
                                        射手村攻城3();
                                    } else {
                                        射手村防御成功();
                                    }
                                } catch (InterruptedException e) {
                                }
                            }
                        }.start();
                    }
                }
            }, 1000 * 1);
        }
    }
    public static int 射手村3出数 = 0;

    public static void 射手村攻城3() {
        String 信息 = "蝙蝠魔大军已经抵达射手村，此次兵力大概 " + MapleParty.蝙蝠魔A部队 + " ";
        System.err.println("[服务端]" + CurrentReadable_Time() + " : [城镇警报] : " + 信息);
        if (射手村3 == null) {
            射手村3 = Timer.BuffTimer.getInstance().register(new Runnable() {
                @Override
                public void run() {
                    if (射手村3出数 <= MapleParty.蝙蝠魔A部队) {
                        攻城部队A(3);
                        射手村3出数++;
                    } else if (射手村3 != null) {
                        射手村3.cancel(true);
                        射手村3 = null;
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000 * 60);
                                    if (MapleParty.蝙蝠魔A部队 > 0) {
                                        破坏射手村();
                                    } else {
                                        射手村防御成功();
                                    }
                                } catch (InterruptedException e) {
                                }
                            }
                        }.start();
                    }
                }
            }, 1000 * 1);
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
                            int 繁荣度 = Getcharactera("射手村繁荣度", 1);
                            int 减少 = (int) (繁荣度 * 0.1);
                            if (繁荣度 > 10000) {
                                Gaincharactera("射手村繁荣度", 1, -减少);
                            }
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

    public static void Gaincharactera(String Name, int Channale, int Piot) {
        try {
            int ret = Getcharactera(Name, Channale);
            if (ret == -1) {
                ret = 0;
                PreparedStatement ps = null;
                try {
                    ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO charactera (channel, Name,Point) VALUES (?, ?, ?)");
                    ps.setInt(1, Channale);
                    ps.setString(2, Name);
                    ps.setInt(3, ret);

                    ps.execute();
                } catch (SQLException e) {
                    System.out.println("xxxxxxxx:" + e);
                } finally {
                    try {
                        if (ps != null) {
                            ps.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("xxxxxxxxzzzzzzz:" + e);
                    }
                }
            }
            ret += Piot;
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE charactera SET `Point` = ? WHERE Name = ? and channel = ?");
            ps.setInt(1, ret);
            ps.setString(2, Name);
            ps.setInt(3, Channale);
            ps.execute();
            ps.close();
        } catch (SQLException sql) {
            System.err.println("Getcharactera!!55" + sql);
        }
    }

    public static int Getcharactera(String Name, int Channale) {
        int ret = -1;
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM charactera WHERE channel = ? and Name = ?");
            ps.setInt(1, Channale);
            ps.setString(2, Name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            ret = rs.getInt("Point");
            rs.close();
            ps.close();
        } catch (SQLException ex) {
        }
        return ret;
    }
}
