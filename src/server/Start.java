package server;

import java.util.HashMap;
import java.io.IOException;
import tools.StringUtil;
import server.maps.MapleMap;
import java.util.LinkedHashSet;
import java.util.Collection;
import tools.packet.UIPacket;
import constants.GameConstants;
import gui.FengYeDuan;
import handling.world.World.Broadcast;
import java.util.Iterator;
import tools.MaplePacketCreator;
import client.MapleCharacter;
import gui.活动野外通缉;
import gui.活动神秘商人;
import gui.活动倍率活动;
import gui.活动魔族攻城;
import gui.活动魔族入侵;
import java.util.Calendar;
import handling.world.MapleParty;
import java.sql.ResultSet;
import database.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.Connection;
import client.inventory.OnlyID;
import server.maps.MapleMapFactory;
import server.life.PlayerNPC;
import handling.cashshop.CashShopServer;
import handling.channel.ChannelServer;
import handling.login.LoginServer;
import merchant.merchant_main;
import handling.world.family.MapleFamilyBuff;
import handling.channel.MapleGuildRanking;
import client.SkillFactory;
import server.events.MapleOxQuizFactory;
import server.life.MapleLifeFactory;
import server.quest.MapleQuest;
import handling.login.LoginInformationProvider;
import handling.world.World;
import constants.ServerConfig;
import constants.WorldConstants;
import java.sql.SQLException;
import tools.FileoutputUtil;
import database.DBConPool;
import java.util.Map;
import java.net.ServerSocket;

public class Start
{
    private static ServerSocket srvSocket;
    private static int srvPort;
    public static long startTime;
    public static final Start instance;
    private static int maxUsers;
    private int rankTime;
    private boolean ivCheck;
    public static boolean 是否控制台启动;
    public static Map<String, Integer> ConfigValuesMap;
    public static Map<String, Integer> 地图吸怪检测;
    private static int 记录在线时间;
    private static Boolean 喜从天降;
    private static int 初始通缉令;
    private static Boolean 倍率活动;
    private static Boolean 幸运职业;
    private static Boolean 魔族入侵;
    private static Boolean isClearBossLog;
    private static Boolean 魔族攻城;
    private static int Z;
    public static int 福利泡点;
    private static int 回收内存;
    
    public static final void main(final String[] args) {
        if (Start.是否控制台启动) {
            final String name = null;
            final int id = 0;
            final int vip = 0;
            final int size = 0;
            try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
                 final PreparedStatement ps = con.prepareStatement("UPDATE accounts SET loggedin = 0")) {
                ps.executeUpdate();
            }
            catch (SQLException ex) {
                FileoutputUtil.outError("logs/資料庫異常.txt", (Throwable)ex);
                throw new RuntimeException("【錯誤】 請確認資料庫是否正確連接");
            }
            GetConfigValues();
            System.out.println("[正在加载] -> 正在启动枫叶服务端");
            System.out.println("[正在加载] -> 版本信息: ver.079 ");
            System.out.println("[正在加载] -> 正在读取授权码请稍后");
            System.out.println("[正在加载] -> 授权码读取完毕");
            final long startQuestTime = System.currentTimeMillis();
            System.out.println("\r\n[正在加载] -> 开始加载各项游戏数据");
            if (WorldConstants.ADMIN_ONLY) {
                System.out.println("[正在加载] -> 只允许管理员登录模式开关: 开启");
            }
            else {
                System.out.println("[正在加载] -> 只允许管理员登录模式开关: 关闭");
            }
            if (ServerConfig.AUTO_REGISTER) {
                System.out.println("[正在加载] -> 账号自动注册模式开关: 开启");
            }
            else {
                System.out.println("[正在加载] -> 账号自动注册模式开关: 关闭");
            }
            if (!WorldConstants.GMITEMS) {
                System.out.println("[正在加载] -> 允许玩家使用管理员物品开关: 开启");
            }
            else {
                System.out.println("[正在加载] -> 允许玩家使用管理员物品开关: 关闭");
            }
            ServerConfig.loadSetting();
            World.init();
            server.Timer.WorldTimer.getInstance().start();
            server.Timer.EtcTimer.getInstance().start();
            server.Timer.MapTimer.getInstance().start();
            server.Timer.MobTimer.getInstance().start();
            server.Timer.CloneTimer.getInstance().start();
            server.Timer.EventTimer.getInstance().start();
            server.Timer.BuffTimer.getInstance().start();
            server.Timer.PingTimer.getInstance().start();
            LoginInformationProvider.getInstance();
            FishingRewardFactory.getInstance();
            MapleQuest.initQuests();
            MapleLifeFactory.loadQuestCounts();
            MapleOxQuizFactory.getInstance().initialize();
            MapleItemInformationProvider.getInstance().load();
            PredictCardFactory.getInstance().initialize();
            CashItemFactory.getInstance().initialize();
            RandomRewards.getInstance();
            SkillFactory.LoadSkillInformaion();
            MapleCarnivalFactory.getInstance();
            System.out.println("[正在加载] -> 游戏商品数量: " + 服务器游戏商品() + " 个");
            System.out.println("[正在加载] -> 商城商品数量: " + 服务器商城商品() + " 个");
            System.out.println("[正在加载] -> 玩家账号数量: " + 服务器账号() + " 个");
            System.out.println("[正在加载] -> 玩家角色数量: " + 服务器角色() + " 个");
            System.out.println("[正在加载] -> 玩家道具数量: " + 服务器道具() + " 个");
            System.out.println("[正在加载] -> 玩家技能数量: " + 服务器技能() + " 个");
            System.out.println("[正在加载] -> 自动存档线程");
            System.out.println("[正在加载] -> 启动记录在线时长线程");
            System.out.println("[正在加载] -> 启动服务端内存回收线程");
            System.out.println("[正在加载] -> 启动服务端地图回收线程");
            System.out.println("[正在加载] -> 处理怪物重生、CD、宠物、坐骑");
            System.out.println("[正在加载] -> 自定义玩家NPC");
            System.out.println("[正在加载] -> 检测游戏复制道具系统");
            MapleGuildRanking.getInstance().getGuildRank();
            MapleGuildRanking.getInstance().getJobRank(1);
            MapleGuildRanking.getInstance().getJobRank(2);
            MapleGuildRanking.getInstance().getJobRank(3);
            MapleGuildRanking.getInstance().getJobRank(4);
            MapleGuildRanking.getInstance().getJobRank(5);
            MapleGuildRanking.getInstance().getJobRank(6);
            MapleFamilyBuff.getBuffEntry();
            System.out.println("\r\n[正在加载] -> 游戏倍率信息");
            if (Integer.parseInt(ServerProperties.getProperty("FengYeDuan.expRate")) > 100) {
                System.out.println("游戏经验倍率: 100 倍 (上限)");
            }
            else {
                System.out.println("游戏经验倍率: " + Integer.parseInt(ServerProperties.getProperty("FengYeDuan.expRate")) + " 倍 ");
            }
            if (Integer.parseInt(ServerProperties.getProperty("FengYeDuan.dropRate")) > 100) {
                System.out.println("游戏物品倍率：100 倍 (上限)");
            }
            else {
                System.out.println("游戏物品倍率：" + Integer.parseInt(ServerProperties.getProperty("FengYeDuan.dropRate")) + " 倍 ");
            }
            if (Integer.parseInt(ServerProperties.getProperty("FengYeDuan.mesoRate")) > 100) {
                System.out.println("游戏金币倍率：100 倍 (上限)");
            }
            else {
                System.out.println("游戏金币倍率：" + Integer.parseInt(ServerProperties.getProperty("FengYeDuan.mesoRate")) + " 倍 ");
            }
            System.out.println("\r\n[开始加载] -> 游戏端口配置");
            merchant_main.getInstance().load_data();
            LoginServer.setup();
            ChannelServer.startAllChannels();
            CashShopServer.setup();
            server.Timer.CheatTimer.getInstance().register((Runnable)AutobanManager.getInstance(), 60000L);
            Runtime.getRuntime().addShutdownHook(new Thread((Runnable)ShutdownServer.getInstance()));
            SpeedRunner.getInstance().loadSpeedRuns();
            World.registerRespawn();
            PlayerNPC.loadAll();
            LoginServer.setOn();
            MapleMapFactory.loadCustomLife();
            System.out.println("[开始加载地图吸怪检测]");
            读取地图吸怪检测();
            System.out.println("[启动角色福利泡点线程]");
            福利泡点(2);
            自动存档(3);
            在线时间(1);
            回收内存(360);
            回收地图(480);
            在线统计(30);
            记录在线时间(1);
            World.isShutDown = false;
            OnlyID.getInstance();
            System.out.println("[所有游戏数据加载完毕] ");
            System.out.println("[枫叶服务端已启动完毕，耗时 " + (System.currentTimeMillis() - startQuestTime) / 1000L + " 秒]");
            System.out.println("[温馨提示]运行中请勿直接关闭本控制台，使用下方关闭服务器按钮来关闭服务端，否则回档自负\r\n");
        }
    }
    
    public static void GetConfigValues() {
        final Connection con = DatabaseConnection.getConnection();
        try (final PreparedStatement ps = con.prepareStatement("SELECT name, val FROM ConfigValues")) {
            try (final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final String name = rs.getString("name");
                    final int val = rs.getInt("val");
                    Start.ConfigValuesMap.put(name, Integer.valueOf(val));
                }
            }
            ps.close();
        }
        catch (SQLException ex) {
            System.err.println("读取动态数据库出错：" + ex.getMessage());
        }
    }
    
    public static void 记录在线时间(final int time) {
        server.Timer.WorldTimer.getInstance().register((Runnable)new Runnable() {
            @Override
            public void run() {
                if (记录在线时间 > 0) {
                    ++MapleParty.服务端运行时长;
                    final Calendar calendar = Calendar.getInstance();
                    final int 时 = Calendar.getInstance().get(11);
                    final int 分 = Calendar.getInstance().get(12);
                    final int 星期 = Calendar.getInstance().get(7);
                    if (时 == 0 && !(boolean)isClearBossLog) {
                        System.err.println("[服务端]" + FileoutputUtil.CurrentReadable_Time() + " : ------------------------------");
                        System.err.println("[服务端]" + FileoutputUtil.CurrentReadable_Time() + " : 服务端开始清理每日信息 √");
                        try {
                            try (final PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("UPDATE characters SET todayOnlineTime = 0")) {
                                ps.executeUpdate();
                                System.err.println("[服务端]" + FileoutputUtil.CurrentReadable_Time() + " : 清理今日在线时间完成 √");
                            }
                            try (final PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("UPDATE bosslog SET characterid = 0")) {
                                ps.executeUpdate();
                                System.err.println("[服务端]" + FileoutputUtil.CurrentReadable_Time() + " : 清理今日log信息完成 √");
                            }
                            System.err.println("[服务端]" + FileoutputUtil.CurrentReadable_Time() + " : 服务端清理每日信息完成 √");
                            System.err.println("[服务端]" + FileoutputUtil.CurrentReadable_Time() + " : ------------------------------");
                        }
                        catch (SQLException ex) {
                            System.err.println("[服务端]" + FileoutputUtil.CurrentReadable_Time() + " : 服务端处理每日数据出错 × " + ex.getMessage());
                            System.err.println("[服务端]" + FileoutputUtil.CurrentReadable_Time() + " : ------------------------------");
                        }
                        isClearBossLog = Boolean.valueOf(true);
                        魔族入侵 = Boolean.valueOf(false);
                        魔族攻城 = Boolean.valueOf(false);
                        喜从天降 = Boolean.valueOf(false);
                    }
                    else if (时 == 23) {
                        isClearBossLog = Boolean.valueOf(false);
                    }
                    if ((int)Integer.valueOf(ConfigValuesMap.get("魔族突袭开关")) == 0 && calendar.get(11) == 22 && !(boolean)魔族入侵) {
                        活动魔族入侵.魔族入侵线程();
                        魔族入侵 = Boolean.valueOf(true);
                    }
                    if ((int)Integer.valueOf(ConfigValuesMap.get("魔族攻城开关")) == 0 && Calendar.getInstance().get(7) == 1 && 时 == 21 && 分 <= 10 && !(boolean)魔族攻城) {
                        活动魔族攻城.魔族攻城线程();
                        魔族攻城 = Boolean.valueOf(true);
                    }
                    if ((int)Integer.valueOf(ConfigValuesMap.get("幸运职业开关")) == 0) {
                        if (时 == 11 && !(boolean)幸运职业) {
                            Start.幸运职业();
                            幸运职业 = Boolean.valueOf(true);
                        }
                        else if (时 == 23 && (boolean)幸运职业) {
                            Start.幸运职业();
                            幸运职业 = Boolean.valueOf(false);
                        }
                        else if (MapleParty.幸运职业 == 0) {
                            Start.幸运职业();
                        }
                    }
                    if ((int)Integer.valueOf(ConfigValuesMap.get("周末倍率开关")) == 0) {
                        switch (Calendar.getInstance().get(7)) {
                            case 7: {
                                if (时 == 0 && !(boolean)倍率活动) {
                                    活动倍率活动.倍率活动线程();
                                    倍率活动 = Boolean.valueOf(true);
                                    break;
                                }
                                if (时 == 23) {
                                    倍率活动 = Boolean.valueOf(false);
                                    break;
                                }
                                break;
                            }
                            case 1: {
                                if (时 == 0 && !(boolean)倍率活动) {
                                    活动倍率活动.倍率活动线程();
                                    倍率活动 = Boolean.valueOf(true);
                                    break;
                                }
                                if (时 == 23) {
                                    倍率活动 = Boolean.valueOf(false);
                                    break;
                                }
                                break;
                            }
                            case 6: {
                                if ((boolean)倍率活动) {
                                    倍率活动 = Boolean.valueOf(false);
                                    break;
                                }
                                break;
                            }
                        }
                    }
                    if ((int)Integer.valueOf(ConfigValuesMap.get("神秘商人开关")) == 0) {
                        if (MapleParty.神秘商人线程 == 0) {
                            活动神秘商人.启动神秘商人();
                            ++MapleParty.神秘商人线程;
                        }
                        if (MapleParty.神秘商人线程 > 0 && 时 == MapleParty.神秘商人时间 && MapleParty.神秘商人 == 0) {
                            活动神秘商人.召唤神秘商人();
                        }
                    }
                    if ((int)Integer.valueOf(ConfigValuesMap.get("野外通缉开关")) == 0) {
                        if (初始通缉令 == 30) {
                            活动野外通缉.随机通缉();
                            初始通缉令++;
                        }
                        else {
                            初始通缉令++;
                        }
                    }
                    Z = 0;
                    for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                        for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                            if (chr == null) {
                                continue;
                            }
                            final Connection con = DatabaseConnection.getConnection();
                        }
                    }
                }
                else {
                    记录在线时间++;
                }
            }
        }, (long)(60000 * time));
    }
    
    public static void 记录在线时间补救(final int a) {
        final Connection con = DatabaseConnection.getConnection();
        try (final PreparedStatement psu = con.prepareStatement("UPDATE characters SET todayOnlineTime = todayOnlineTime + ?, totalOnlineTime = totalOnlineTime + ? WHERE id = ?")) {
            psu.setInt(1, 1);
            psu.setInt(2, 1);
            psu.setInt(3, a);
            psu.executeUpdate();
            psu.close();
        }
        catch (SQLException ex) {
            记录在线时间补救2(a);
        }
    }
    
    public static void 记录在线时间补救2(final int a) {
        final Connection con = DatabaseConnection.getConnection();
        try (final PreparedStatement psu = con.prepareStatement("UPDATE characters SET todayOnlineTime = todayOnlineTime + ?, totalOnlineTime = totalOnlineTime + ? WHERE id = ?")) {
            psu.setInt(1, 1);
            psu.setInt(2, 1);
            psu.setInt(3, a);
            psu.executeUpdate();
            psu.close();
        }
        catch (SQLException ex) {
            记录在线时间补救3(a);
        }
    }
    
    public static void 记录在线时间补救3(final int a) {
        final Connection con = DatabaseConnection.getConnection();
        try (final PreparedStatement psu = con.prepareStatement("UPDATE characters SET todayOnlineTime = todayOnlineTime + ?, totalOnlineTime = totalOnlineTime + ? WHERE id = ?")) {
            psu.setInt(1, 1);
            psu.setInt(2, 1);
            psu.setInt(3, a);
            psu.executeUpdate();
            psu.close();
        }
        catch (SQLException ex) {}
    }
    
    public static void 幸运职业() {
        int 随机 = (int)Math.ceil(Math.random() * 18.0);
        if (随机 == 0) {
            ++随机;
        }
        switch (随机) {
            case 1: {
                MapleParty.幸运职业 = 111;
                break;
            }
            case 2: {
                MapleParty.幸运职业 = 121;
                break;
            }
            case 3: {
                MapleParty.幸运职业 = 131;
                break;
            }
            case 4: {
                MapleParty.幸运职业 = 211;
                break;
            }
            case 5: {
                MapleParty.幸运职业 = 221;
                break;
            }
            case 6: {
                MapleParty.幸运职业 = 231;
                break;
            }
            case 7: {
                MapleParty.幸运职业 = 311;
                break;
            }
            case 8: {
                MapleParty.幸运职业 = 321;
                break;
            }
            case 9: {
                MapleParty.幸运职业 = 411;
                break;
            }
            case 10: {
                MapleParty.幸运职业 = 421;
                break;
            }
            case 11: {
                MapleParty.幸运职业 = 511;
                break;
            }
            case 12: {
                MapleParty.幸运职业 = 521;
                break;
            }
            case 13: {
                MapleParty.幸运职业 = 1111;
                break;
            }
            case 14: {
                MapleParty.幸运职业 = 1211;
                break;
            }
            case 15: {
                MapleParty.幸运职业 = 1311;
                break;
            }
            case 16: {
                MapleParty.幸运职业 = 1411;
                break;
            }
            case 17: {
                MapleParty.幸运职业 = 1511;
                break;
            }
            case 18: {
                MapleParty.幸运职业 = 2111;
                break;
            }
        }
        final String 信息 = "恭喜 " + MapleCarnivalChallenge.getJobNameById(MapleParty.幸运职业 - 1) + " " + MapleCarnivalChallenge.getJobNameById(MapleParty.幸运职业) + " " + MapleCarnivalChallenge.getJobNameById(MapleParty.幸运职业 + 1) + " 幸运成为幸运职业，增加50%基础狩猎经验";
        System.err.println("[服务端]" + FileoutputUtil.CurrentReadable_Time() + " : [幸运职业] : " + 信息);
        Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[幸运职业] : " + 信息));
    }
    
    public static void 福利泡点(final int time) {
        server.Timer.WorldTimer.getInstance().register((Runnable)new Runnable() {
            @Override
            public void run() {
                if (福利泡点 > 0) {
                    try {
                        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                            for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                                if (chr == null) {
                                    continue;
                                }
                                if (chr.level <= 10) {
                                    continue;
                                }
                                int 点券 = 0;
                                int 经验 = 0;
                                int 金币 = 0;
                                int 抵用 = 0;
                                int 豆豆 = 0;
                                final int 泡点豆豆开关 = (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("泡点豆豆开关"));
                                if (泡点豆豆开关 <= 0) {
                                    final int 泡点豆豆 = (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("泡点豆豆"));
                                    豆豆 += 泡点豆豆;
                                    chr.gainBeans(豆豆);
                                }
                                final int 泡点金币开关 = (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("泡点金币开关"));
                                if (泡点金币开关 <= 0) {
                                    final int 泡点金币 = (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("泡点金币"));
                                    金币 += chr.getLevel() * 泡点金币;
                                    chr.gainMeso(chr.getLevel() * 泡点金币, true);
                                }
                                final int 泡点点券开关 = (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("泡点点券开关"));
                                if (泡点点券开关 <= 0) {
                                    final int 泡点点券 = (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("泡点点券"));
                                    chr.modifyCSPoints(1, 泡点点券, true);
                                    点券 += 泡点点券;
                                }
                                final int 泡点抵用开关 = (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("泡点抵用开关"));
                                if (泡点抵用开关 <= 0) {
                                    final int 泡点抵用 = (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("泡点抵用"));
                                    chr.modifyCSPoints(2, 泡点抵用, true);
                                    抵用 += 泡点抵用;
                                }
                                final int 泡点经验开关 = (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("泡点经验开关"));
                                if (泡点经验开关 > 0) {
                                    continue;
                                }
                                final int 泡点经验 = (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("泡点经验"));
                                经验 += chr.getLevel() * 泡点经验;
                                chr.gainExp(chr.getLevel() * 经验, false, false, false);
                            }
                        }
                    }
                    catch (Exception e) {
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(10000L);
                                    Start.福利泡点();
                                }
                                catch (InterruptedException ex) {}
                            }
                        }.start();
                    }
                }
                else {
                    ++福利泡点;
                }
            }
        }, (long)(60000 * time));
    }
    
    public static void 福利泡点() {
        try {
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                    if (chr == null) {
                        continue;
                    }
                    if (chr.level <= 10) {
                        continue;
                    }
                    int 点券 = 0;
                    int 经验 = 0;
                    int 金币 = 0;
                    int 抵用 = 0;
                    int 豆豆 = 0;
                    final int 泡点豆豆开关 = (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("泡点豆豆开关"));
                    if (泡点豆豆开关 <= 0) {
                        final int 泡点豆豆 = (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("泡点豆豆"));
                        豆豆 += 泡点豆豆;
                        chr.gainBeans(豆豆);
                    }
                    final int 泡点金币开关 = (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("泡点金币开关"));
                    if (泡点金币开关 <= 0) {
                        final int 泡点金币 = (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("泡点金币"));
                        金币 += chr.getLevel() * 泡点金币;
                        chr.gainMeso(chr.getLevel() * 泡点金币, true);
                    }
                    final int 泡点点券开关 = (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("泡点点券开关"));
                    if (泡点点券开关 <= 0) {
                        final int 泡点点券 = (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("泡点点券"));
                        chr.modifyCSPoints(1, 泡点点券, true);
                        点券 += 泡点点券;
                    }
                    final int 泡点抵用开关 = (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("泡点抵用开关"));
                    if (泡点抵用开关 <= 0) {
                        final int 泡点抵用 = (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("泡点抵用"));
                        chr.modifyCSPoints(2, 泡点抵用, true);
                        抵用 += 泡点抵用;
                    }
                    final int 泡点经验开关 = (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("泡点经验开关"));
                    if (泡点经验开关 <= 0) {
                        final int 泡点经验 = (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("泡点经验"));
                        经验 += chr.getLevel() * 泡点经验;
                        chr.gainExp(chr.getLevel() * 泡点经验, true, true, false);
                    }
                    chr.getClient().getSession().write(MaplePacketCreator.serverNotice(5, "[世界泡点] ：获得 [" + 点券 + "] 点卷 / [" + 抵用 + "] 抵用卷 / [" + 经验 + "] 经验  [" + 金币 + "] 金币  !"));
                    chr.getClient().sendPacket(UIPacket.getTopMsg("[" + GameConstants.冒险岛名字 + "世界泡点]:获得 " + 点券 + " 点券 / " + 抵用 + " 抵用 / " + 经验 + " 经验 / " + 金币 + " 金币 ! "));
                    chr.getClient().sendPacket(MaplePacketCreator.enableActions());
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public static void 自动存档(final int time) {
        server.Timer.WorldTimer.getInstance().register((Runnable)new Runnable() {
            @Override
            public void run() {
                int ppl = 0;
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    Collection<MapleCharacter> allCharacters = null;
                    if (allCharacters != null) {
                        boolean flag = true;
                        while (flag) {
                            ppl = 0;
                            try {
                                int over = 0;
                                for (final MapleCharacter chr : allCharacters) {
                                    if (chr == null) {
                                        continue;
                                    }
                                    ++ppl;
                                    if (over == 0) {
                                        cserv.getLastOfflineTime2();
                                        ++over;
                                    }
                                    chr.saveToDB(false, false);
                                }
                                flag = false;
                            }
                            catch (Exception e2) {
                                System.err.println("自动存档出错2：" + e2);
                            }
                        }
                    }
                }
            }
        }, (long)(60000 * time));
    }
    
    public static void 回收内存(final int time) {
        server.Timer.WorldTimer.getInstance().register((Runnable)new Runnable() {
            @Override
            public void run() {
                if (回收内存 > 0) {
                    System.gc();
                    System.err.println("○【内存回收】 " + FileoutputUtil.CurrentReadable_Time() + " : 回收服务端内存 √");
                }
                else {
                    回收内存++;
                }
            }
        }, (long)(60000 * time));
    }
    
    public static void 回收地图(final int time) {
        server.Timer.WorldTimer.getInstance().register((Runnable)new Runnable() {
            @Override
            public void run() {
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        for (int i = 0; i < 6; ++i) {
                            int mapidA = 100000000 + (i + 1000000 - 2000000);
                            final MapleCharacter player = chr;
                            if (i == 6) {
                                mapidA = 910000000;
                            }
                            final int mapid = mapidA;
                            final MapleMap map = player.getClient().getChannelServer().getMapFactory().getMap(mapid);
                            if (player.getClient().getChannelServer().getMapFactory().destroyMap(mapid)) {
                                final MapleMap newMap = player.getClient().getChannelServer().getMapFactory().getMap(mapid);
                                final MaplePortal newPor = newMap.getPortal(0);
                                final LinkedHashSet<MapleCharacter> mcs = new LinkedHashSet<MapleCharacter>((Collection<? extends MapleCharacter>)map.getCharacters());
                                for (final MapleCharacter m : mcs) {
                                    int x = 0;
                                    while (x < 5) {
                                        try {
                                            m.changeMap(newMap, newPor);
                                        }
                                        catch (Throwable t) {
                                            System.err.println("○【地图回收】 " + FileoutputUtil.CurrentReadable_Time() + " : 系统正在回收地图 √");
                                            ++x;
                                            continue;
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, (long)(60000 * time));
    }
    
    public static void 读取地图吸怪检测() {
        final Connection con = DatabaseConnection.getConnection();
        try (final PreparedStatement ps = con.prepareStatement("SELECT name, val FROM 地图吸怪检测")) {
            try (final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final String name = rs.getString("name");
                    final int val = rs.getInt("val");
                    Start.地图吸怪检测.put(name, Integer.valueOf(val));
                }
            }
            ps.close();
        }
        catch (SQLException ex) {
            System.err.println("读取吸怪检测错误：" + ex.getMessage());
        }
    }
    
    public static int 服务器角色() {
        int p = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT id as DATA FROM characters WHERE id >=0");
            try (final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ++p;
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("服务器角色？");
        }
        return p;
    }
    
    public static int 服务器账号() {
        int p = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT id as DATA FROM accounts WHERE id >=0");
            try (final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ++p;
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("服务器账号？");
        }
        return p;
    }
    
    public static int 服务器技能() {
        int p = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT id as DATA FROM skills ");
            try (final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ++p;
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("服务器技能？");
        }
        return p;
    }
    
    public static int 服务器道具() {
        int p = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT inventoryitemid as DATA FROM inventoryitems WHERE inventoryitemid >=0");
            try (final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ++p;
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("服务器道具？");
        }
        return p;
    }
    
    public static int 服务器商城商品() {
        int p = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT serial as DATA FROM cashshop_modified_items WHERE serial >=0");
            try (final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ++p;
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("服务器商城商品？");
        }
        return p;
    }
    
    public static int 服务器游戏商品() {
        int p = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT shopitemid as DATA FROM shopitems WHERE shopitemid >=0");
            try (final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ++p;
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("服务器道具游戏商品？");
        }
        return p;
    }
    
    public static void 在线统计(final int time) {
        System.out.println("[枫叶服务端启用在线统计." + time + "分钟统计一次在线的人数信息]");
        server.Timer.WorldTimer.getInstance().register((Runnable)new Runnable() {
            @Override
            public void run() {
                final Map<Integer, Integer> connected = World.getConnected();
                final StringBuilder conStr = new StringBuilder(FileoutputUtil.CurrentReadable_Time() + " 在线人数: ");
                final Iterator<Integer> iterator = connected.keySet().iterator();
                while (iterator.hasNext()) {
                    final int i = (int)Integer.valueOf(iterator.next());
                    if (i == 0) {
                        final int users = (int)Integer.valueOf(connected.get(Integer.valueOf(i)));
                        conStr.append(StringUtil.getRightPaddedStr(String.valueOf(users), ' ', 3));
                        if (users > maxUsers) {
                            maxUsers = users;
                        }
                        conStr.append(" 最高在线: ");
                        conStr.append(maxUsers);
                        break;
                    }
                }
                System.out.println("[在线统计]" + conStr.toString());
                if (maxUsers > 0) {
                    FileoutputUtil.log("在线统计.txt", conStr.toString() + "\r\n");
                }
            }
        }, (long)(60000 * time));
    }
    
    public static void 在线时间(final int time) {
        server.Timer.WorldTimer.getInstance().register((Runnable)new Runnable() {
            @Override
            public void run() {
                final Calendar c = Calendar.getInstance();
                final int hour = c.get(11);
                final int minute = c.get(12);
                if (hour == 0 && minute == 0) {
                    try {
                        final Connection con = DatabaseConnection.getConnection();
                        try (final PreparedStatement ps = con.prepareStatement("UPDATE accounts_info SET gamePoints = ?, updateTime = CURRENT_TIMESTAMP()")) {
                            ps.setInt(1, 0);
                            ps.executeUpdate();
                            ps.close();
                        }
                    }
                    catch (SQLException Ex) {
                        System.err.println("更新角色帐号的在线时间出现错误 - 数据库更新失败." + Ex);
                    }
                }
                try {
                    for (final ChannelServer chan : ChannelServer.getAllInstances()) {
                        for (final MapleCharacter chr : chan.getPlayerStorage().getAllCharacters()) {
                            if (chr == null) {
                                continue;
                            }
                            if (hour == 0 && minute == 0) {
                                chr.set在线时间(0);
                            }
                            else {
                                chr.gainGamePoints(1);
                                if (chr.get在线时间() >= 5) {
                                    continue;
                                }
                                chr.resetGamePointsPS();
                                chr.resetGamePointsPD();
                            }
                        }
                    }
                }
                catch (Exception e) {
                    System.err.println("在线时间出错:" + e);
                }
            }
        }, (long)(60000 * time));
    }
    
    public static void startCheck() {
        System.out.println("服务端启用检测.30秒检测一次角色是否与登录器断开连接.");
        server.Timer.WorldTimer.getInstance().register((Runnable)new Runnable() {
            @Override
            public void run() {
                for (final ChannelServer cserv_ : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv_.getPlayerStorage().getAllCharacters()) {
                        if (chr != null) {
                            chr.startCheck();
                        }
                    }
                }
            }
        }, 30000L);
    }
    
    protected static void checkSingleInstance() {
        try {
            Start.srvSocket = new ServerSocket(Start.srvPort);
        }
        catch (IOException ex) {
            if (ex.getMessage().contains((CharSequence)"地址已经在使用:JVM_Bind")) {
                System.out.println("在一台主机上同时只能启动一个进程(Only one instance allowed)。");
            }
            System.exit(0);
        }
    }
    
    static {
        Start.srvSocket = null;
        Start.srvPort = 6350;
        Start.startTime = System.currentTimeMillis();
        instance = new Start();
        Start.maxUsers = 0;
        Start.是否控制台启动 = false;
        Start.ConfigValuesMap = new HashMap<String, Integer>();
        Start.地图吸怪检测 = new HashMap<String, Integer>();
        Start.记录在线时间 = 0;
        Start.喜从天降 = Boolean.valueOf(false);
        Start.初始通缉令 = 0;
        Start.倍率活动 = Boolean.valueOf(false);
        Start.幸运职业 = Boolean.valueOf(false);
        Start.魔族入侵 = Boolean.valueOf(false);
        Start.isClearBossLog = Boolean.valueOf(false);
        Start.魔族攻城 = Boolean.valueOf(false);
        Start.Z = 0;
        Start.福利泡点 = 0;
        Start.回收内存 = 0;
    }
    
    public static class Shutdown implements Runnable
    {
        @Override
        public void run() {
            new Thread((Runnable)ShutdownServer.getInstance()).start();
        }
    }
}
