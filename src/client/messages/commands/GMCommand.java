package client.messages.commands;

import merchant.merchant_main;
import abc.Game;
import tools.LoadPacket;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.ListIterator;
import handling.cashshop.CashShopServer;
import tools.Triple;
import client.inventory.OnlyID;
import server.life.MapleMonster;
import server.life.OverrideMonsterStats;
import java.util.Arrays;
import server.maps.MapleMapObjectType;
import server.maps.MapleReactorStats;
import server.maps.MapleReactorFactory;
import constants.ServerConfig;
import constants.WorldConstants;
import handling.login.LoginServer;
import client.inventory.MapleRing;
import client.inventory.Equip;
import client.inventory.MapleInventoryIdentifier;
import constants.GameConstants;
import client.inventory.Item;
import java.util.Map;
import java.util.Map.Entry;
import client.inventory.ItemFlag;
import tools.ArrayMap;
import tools.Pair;
import java.util.ArrayList;
import client.ISkill;
import client.SkillFactory;
import handling.world.family.MapleFamily;
import handling.world.guild.MapleGuild;
import server.MapleItemInformationProvider;
import server.maps.MapleMapFactory;
import server.events.MapleOxQuizFactory;
import server.quest.MapleQuest;
import server.FishingRewardFactory;
import server.CashItemFactory;
import server.MapleShopFactory;
import scripting.PortalScriptManager;
import scripting.ReactorScriptManager;
import server.life.MapleMonsterInformationProvider;
import handling.RecvPacketOpcode;
import handling.SendPacketOpcode;
import client.messages.CommandProcessorUtil;
import client.inventory.ModifyInventory;
import client.inventory.MapleInventory;
import server.MapleInventoryManipulator;
import client.inventory.IItem;
import java.util.LinkedList;
import client.inventory.MapleInventoryType;
import java.text.DateFormat;
import java.util.Calendar;
import handling.world.World;
import server.Timer.EventTimer;
import java.util.concurrent.ScheduledFuture;
import server.ShutdownServer;
import server.maps.MapleMap;
import java.util.HashMap;
import server.MaplePortal;
import server.maps.MapleReactor;
import server.maps.MapleMapObject;
import handling.world.World.Find;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import handling.world.World.Guild;
import database.DBConPool;
import server.life.MapleNPC;
import scripting.NPCScriptManager;
import server.life.MapleLifeFactory;
import handling.world.World.Broadcast;
import tools.MaplePacketCreator;
import tools.StringUtil;
import java.awt.Point;
import tools.FileoutputUtil;
import client.MapleStat;
import java.util.List;
import java.util.Iterator;
import client.MapleCharacter;
import handling.channel.ChannelServer;
import constants.ServerConstants;
import client.MapleClient;
import constants.ServerConstants.PlayerGMRank;

public class GMCommand
{
    public static PlayerGMRank getPlayerLevelRequired() {
        return PlayerGMRank.?????????;
    }
    
    public static class ?????????????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                return false;
            }
            int input = 0;
            int Change = 0;
            int sn = 0;
            try {
                input = Integer.parseInt(splitted[1]);
                Change = input - 1;
                sn = Integer.parseInt(splitted[2]);
            }
            catch (Exception ex) {
                return false;
            }
            if (input < 1 || input > 5) {
                c.getPlayer().dropMessage("??????????????????1~5?????????");
                return true;
            }
            ServerConstants.hot_sell[Change] = sn;
            c.getPlayer().dropMessage("?????????????????????" + input + "??????????????????SN??? " + sn + " ?????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!?????????????????? <???X???????????????> <????????????SN> - ??????????????????????????????").toString();
        }
    }
    
    public static class SaveAll extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            int p = 0;
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                final List<MapleCharacter> chrs = cserv.getPlayerStorage().getAllCharactersThreadSafe();
                for (final MapleCharacter chr : chrs) {
                    ++p;
                    chr.saveToDB(false, false);
                }
            }
            c.getPlayer().dropMessage("[??????] " + p + "?????????????????????????????????.");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!saveall - ????????????????????????").toString();
        }
    }
    
    public static class LowHP extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().getStat().setHp(1);
            c.getPlayer().getStat().setMp(1);
            c.getPlayer().updateSingleStat(MapleStat.HP, 1);
            c.getPlayer().updateSingleStat(MapleStat.MP, 1);
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!lowhp - ????????????").toString();
        }
    }
    
    public static class ??????aclog extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                return false;
            }
            final String playername = splitted[1];
            final String mod = splitted[2];
            final int playerid = MapleCharacter.getCharacterIdByName(playername);
            if (playerid == -1) {
                c.getPlayer().dropMessage(6, "??????[" + playername + "]???????????????????????????");
                return true;
            }
            final MapleCharacter victim = MapleCharacter.getCharacterById(playerid);
            if (victim != null) {
                victim.setAcLog(mod);
                c.getPlayer().dropMessage(6, "????????????[" + playername + "] aclog [" + mod + "]?????????");
            }
            else {
                c.getPlayer().dropMessage(6, "??????[" + playername + "]???????????????????????????");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!??????aclog <????????????> - aclog??????").toString();
        }
    }
    
    public static class ?????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                return false;
            }
            final String playername = splitted[1];
            final int playerid = MapleCharacter.getCharacterIdByName(playername);
            if (playerid == -1) {
                c.getPlayer().dropMessage(6, "??????[" + playername + "]???????????????????????????");
                return true;
            }
            final MapleCharacter victim = MapleCharacter.getCharacterById(playerid);
            if (victim != null) {
                victim.modifyCSPoints(1, 300, true);
                victim.modifyCSPoints(2, 500, true);
                c.getPlayer().dropMessage(6, "????????????[" + playername + "] 300Gash 500???????????????????????????");
            }
            else {
                c.getPlayer().dropMessage(6, "??????[" + playername + "]???????????????????????????");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!?????? <????????????>").toString();
        }
    }
    
    public static class ???????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            final String playername = splitted[1];
            c.getPlayer().setFxName(playername);
            c.getPlayer().dropMessage(6, "????????????[" + playername + "] ?????????????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!???????????? <????????????> - aclog??????").toString();
        }
    }
    
    public static class ???????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                return false;
            }
            final String playername = splitted[1];
            final int mod = Integer.parseInt(splitted[2]);
            final int playerid = MapleCharacter.getCharacterIdByName(playername);
            if (playerid == -1) {
                c.getPlayer().dropMessage(6, "??????[" + playername + "]???????????????????????????");
                return true;
            }
            final MapleCharacter victim = MapleCharacter.getCharacterById(playerid);
            if (victim != null) {
                victim.setBuLingZanZu(mod);
                victim.modifyCSPoints(1, mod * 5, true);
                victim.gainVip();
                FileoutputUtil.logToFile("logs/Data/????????????.txt", "\r\n " + FileoutputUtil.NowTime() + " GM " + c.getPlayer().getName() + " ?????? " + victim.getName() + " " + mod + "??????????????????");
                c.getPlayer().dropMessage(6, "??????[" + playername + "] ???????????? [" + mod + "] ?????????");
            }
            else {
                c.getPlayer().dropMessage(6, "??????[" + playername + "]???????????????????????????");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!???????????? <????????????> - ??????????????????").toString();
        }
    }
    
    public static class ???????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                return false;
            }
            final String playername = splitted[1];
            final int mod = Integer.parseInt(splitted[2]);
            final int playerid = MapleCharacter.getCharacterIdByName(playername);
            if (playerid == -1) {
                c.getPlayer().dropMessage(6, "??????[" + playername + "]???????????????????????????");
                return true;
            }
            final MapleCharacter victim = MapleCharacter.getCharacterById(playerid);
            if (victim != null) {
                victim.modifyCSPoints(3, mod, true);
                FileoutputUtil.logToFile("logs/Data/????????????.txt", "\r\n " + FileoutputUtil.NowTime() + " GM " + c.getPlayer().getName() + " ?????? " + victim.getName() + " " + mod + "???????????????");
                c.getPlayer().dropMessage(6, "??????[" + playername + "] ???????????? [" + mod + "] ?????????");
            }
            else {
                c.getPlayer().dropMessage(6, "??????[" + playername + "]???????????????????????????");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!???????????? <????????????> - ??????????????????").toString();
        }
    }
    
    public static class MyPos extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            final Point pos = c.getPlayer().getPosition();
            c.getPlayer().dropMessage(6, "X: " + pos.x + " | Y: " + pos.y + " | RX0: " + (pos.x + 50) + " | RX1: " + (pos.x - 50) + " | FH: " + c.getPlayer().getFH() + "| CY:" + pos.y);
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!mypos - ????????????").toString();
        }
    }
    
    public static class Notice extends CommandExecute
    {
        private static int getNoticeType(final String typestring) {
            switch (typestring) {
                case "n": {
                    return 0;
                }
                case "p": {
                    return 1;
                }
                case "l": {
                    return 2;
                }
                case "nv": {
                    return 5;
                }
                case "v": {
                    return 5;
                }
                case "b": {
                    return 6;
                }
                default: {
                    return -1;
                }
            }
        }
        
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            int joinmod = 1;
            int range = -1;
            if (splitted.length < 2) {
                return false;
            }
            final String s = splitted[1];
            switch (s) {
                case "m": {
                    range = 0;
                    break;
                }
                case "c": {
                    range = 1;
                    break;
                }
                case "w": {
                    range = 2;
                    break;
                }
            }
            int tfrom = 2;
            if (range == -1) {
                range = 2;
                tfrom = 1;
            }
            if (splitted.length < tfrom + 1) {
                return false;
            }
            int type = getNoticeType(splitted[tfrom]);
            if (type == -1) {
                type = 0;
                joinmod = 0;
            }
            final StringBuilder sb = new StringBuilder();
            if (splitted[tfrom].equals("nv")) {
                sb.append("[Notice]");
            }
            else {
                sb.append("");
            }
            joinmod += tfrom;
            if (splitted.length < joinmod + 1) {
                return false;
            }
            sb.append(StringUtil.joinStringFrom(splitted, joinmod));
            final byte[] packet = MaplePacketCreator.serverNotice(type, sb.toString());
            if (range == 0) {
                c.getPlayer().getMap().broadcastMessage(packet);
            }
            else if (range == 1) {
                ChannelServer.getInstance(c.getChannel()).broadcastPacket(packet);
            }
            else if (range == 2) {
                Broadcast.broadcastMessage(packet);
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!notice <n|p|l|nv|v|b> <m|c|w> <message> - ??????").toString();
        }
    }
    
    public static class Yellow extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            int range = -1;
            final String s = splitted[1];
            switch (s) {
                case "m": {
                    range = 0;
                    break;
                }
                case "c": {
                    range = 1;
                    break;
                }
                case "w": {
                    range = 2;
                    break;
                }
            }
            if (range == -1) {
                range = 2;
            }
            final byte[] packet = MaplePacketCreator.yellowChat((splitted[0].equals("!y") ? ("[" + c.getPlayer().getName() + "] ") : "") + StringUtil.joinStringFrom(splitted, 2));
            if (range == 0) {
                c.getPlayer().getMap().broadcastMessage(packet);
            }
            else if (range == 1) {
                ChannelServer.getInstance(c.getChannel()).broadcastPacket(packet);
            }
            else if (range == 2) {
                Broadcast.broadcastMessage(packet);
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!yellow <m|c|w> <message> - ????????????").toString();
        }
    }
    
    public static class Y extends Yellow
    {
    }
    
    public static class NpcNotice extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length <= 2) {
                return false;
            }
            final int npcid = Integer.parseInt(splitted[1]);
            final String msg = splitted[2];
            Broadcast.broadcastMessage(MaplePacketCreator.getNPCTalk(npcid, (byte)0, msg, "00 00", (byte)0));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!NpcNotice <npcid> <message> - ???NPC?????????").toString();
        }
    }
    
    public static class opennpc extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            int npcid = 0;
            try {
                npcid = Integer.parseInt(splitted[1]);
            }
            catch (NumberFormatException ex) {}
            final MapleNPC npc = MapleLifeFactory.getNPC(npcid);
            if (npc != null && !npc.getName().equalsIgnoreCase("MISSINGNO")) {
                NPCScriptManager.getInstance().start(c, npcid);
            }
            else {
                c.getPlayer().dropMessage(6, "??????NPC");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!openNpc <NPC??????> - ??????NPC").toString();
        }
    }
    
    public static class ????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            final String after = splitted[1];
            if (after.length() <= 12) {
                c.getPlayer().setName(splitted[1]);
                c.getPlayer().fakeRelog();
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("*????????? \t?????????\u0010 - ???????????????").toString();
        }
    }
    
    public static class ???????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length != 2) {
                return false;
            }
            try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
                final PreparedStatement ps = con.prepareStatement("SELECT guildid FROM guilds WHERE name = ?");
                ps.setString(1, splitted[1]);
                final ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    if (c.getPlayer().getGuildId() > 0) {
                        try {
                            Guild.leaveGuild(c.getPlayer().getMGC());
                        }
                        catch (Exception e) {
                            c.sendPacket(MaplePacketCreator.serverNotice(5, "??????????????????????????????????????????????????????"));
                            return false;
                        }
                        c.sendPacket(MaplePacketCreator.showGuildInfo(null));
                        c.getPlayer().setGuildId(0);
                        c.getPlayer().saveGuildStatus();
                    }
                    c.getPlayer().setGuildId(rs.getInt("guildid"));
                    c.getPlayer().setGuildRank((byte)2);
                    try {
                        Guild.addGuildMember(c.getPlayer().getMGC(), false);
                    }
                    catch (Exception ex) {}
                    c.sendPacket(MaplePacketCreator.showGuildInfo(c.getPlayer()));
                    c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MaplePacketCreator.removePlayerFromMap(c.getPlayer().getId()), false);
                    c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MaplePacketCreator.spawnPlayerMapobject(c.getPlayer()), false);
                    c.getPlayer().saveGuildStatus();
                }
                else {
                    c.getPlayer().dropMessage(6, "????????????????????????");
                }
                rs.close();
                ps.close();
            }
            catch (SQLException ex2) {}
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!???????????? \t????????????\u0010 - ??????????????????").toString();
        }
    }
    
    public static class ?????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            final String name = splitted[1];
            final int ch = Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "??????????????????");
                return true;
            }
            final MapleCharacter victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (victim == null) {
                c.getPlayer().dropMessage(6, "??????????????????");
                return true;
            }
            victim.setMarriageId(0);
            victim.reloadC();
            victim.dropMessage(5, "???????????????");
            victim.saveToDB(false, false);
            c.getPlayer().dropMessage(6, victim.getName() + "???????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!?????? <????????????> - ??????").toString();
        }
    }
    
    public static class CancelBuffs extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().cancelAllBuffs();
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!CancelBuffs - ????????????BUFF").toString();
        }
    }
    
    public static class RemoveNPCs extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().getMap().resetNPCs();
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!removenpcs - ????????????NPC").toString();
        }
    }
    
    public static class LookNPCs extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            for (final MapleMapObject reactor1l : c.getPlayer().getMap().getAllNPCsThreadsafe()) {
                final MapleNPC reactor2l = (MapleNPC)reactor1l;
                c.getPlayer().dropMessage(5, "NPC: oID: " + reactor2l.getObjectId() + " npcID: " + reactor2l.getId() + " Position: " + reactor2l.getPosition().toString() + " Name: " + reactor2l.getName());
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!looknpcs - ????????????NPC").toString();
        }
    }
    
    public static class ??????NPC extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            for (final MapleMapObject reactor1l : c.getPlayer().getMap().getAllNPCsThreadsafe()) {
                final MapleNPC reactor2l = (MapleNPC)reactor1l;
                c.getPlayer().dropMessage(5, "NPC: oID: " + reactor2l.getObjectId() + " npcID: " + reactor2l.getId() + " Position: " + reactor2l.getPosition().toString() + " Name: " + reactor2l.getName());
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!??????NPC - ????????????NPC").toString();
        }
    }
    
    public static class LookReactors extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            for (final MapleMapObject reactor1l : c.getPlayer().getMap().getAllReactorsThreadsafe()) {
                final MapleReactor reactor2l = (MapleReactor)reactor1l;
                c.getPlayer().dropMessage(5, "Reactor: oID: " + reactor2l.getObjectId() + " reactorID: " + reactor2l.getReactorId() + " Position: " + reactor2l.getPosition().toString() + " State: " + (int)reactor2l.getState() + " Name: " + reactor2l.getName());
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!lookreactors - ?????????????????????").toString();
        }
    }
    
    public static class LookPortals extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            for (final MaplePortal portal : c.getPlayer().getMap().getPortals()) {
                c.getPlayer().dropMessage(5, "Portal: ID: " + portal.getId() + " script: " + portal.getScriptName() + " name: " + portal.getName() + " pos: " + portal.getPosition().x + "," + portal.getPosition().y + " target: " + portal.getTargetMapId() + " / " + portal.getTarget());
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!lookportals - ?????????????????????").toString();
        }
    }
    
    public static class GoTo extends CommandExecute
    {
        private static final HashMap<String, Integer> gotomaps;
        
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().dropMessage(6, "????????????: !goto <????????????>");
            }
            else if (GoTo.gotomaps.containsKey(splitted[1])) {
                final MapleMap target = c.getChannelServer().getMapFactory().getMap((int)Integer.valueOf(GoTo.gotomaps.get(splitted[1])));
                final MaplePortal targetPortal = target.getPortal(0);
                c.getPlayer().changeMap(target, targetPortal);
            }
            else if (splitted[1].equals("?????????")) {
                c.getPlayer().dropMessage(6, "?????? !goto <?????????>. ?????????????????????:");
                final StringBuilder sb = new StringBuilder();
                for (final String s : GoTo.gotomaps.keySet()) {
                    sb.append(s).append(", ");
                }
                c.getPlayer().dropMessage(6, sb.substring(0, sb.length() - 2));
            }
            else {
                c.getPlayer().dropMessage(6, "????????????????????? - ?????? !goto <?????????>. ???????????????????????????, ???????????? !goto ?????????????????????.");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!goto <??????> - ???????????????").toString();
        }
        
        static {
            (gotomaps = new HashMap<String, Integer>()).put("gmmap", Integer.valueOf(180000000));
            GoTo.gotomaps.put("southperry", Integer.valueOf(2000000));
            GoTo.gotomaps.put("amherst", Integer.valueOf(1010000));
            GoTo.gotomaps.put("henesys", Integer.valueOf(100000000));
            GoTo.gotomaps.put("ellinia", Integer.valueOf(101000000));
            GoTo.gotomaps.put("perion", Integer.valueOf(102000000));
            GoTo.gotomaps.put("kerning", Integer.valueOf(103000000));
            GoTo.gotomaps.put("lithharbour", Integer.valueOf(104000000));
            GoTo.gotomaps.put("sleepywood", Integer.valueOf(105040300));
            GoTo.gotomaps.put("florina", Integer.valueOf(110000000));
            GoTo.gotomaps.put("orbis", Integer.valueOf(200000000));
            GoTo.gotomaps.put("happyville", Integer.valueOf(209000000));
            GoTo.gotomaps.put("elnath", Integer.valueOf(211000000));
            GoTo.gotomaps.put("ludibrium", Integer.valueOf(220000000));
            GoTo.gotomaps.put("aquaroad", Integer.valueOf(230000000));
            GoTo.gotomaps.put("leafre", Integer.valueOf(240000000));
            GoTo.gotomaps.put("mulung", Integer.valueOf(250000000));
            GoTo.gotomaps.put("herbtown", Integer.valueOf(251000000));
            GoTo.gotomaps.put("omegasector", Integer.valueOf(221000000));
            GoTo.gotomaps.put("koreanfolktown", Integer.valueOf(222000000));
            GoTo.gotomaps.put("newleafcity", Integer.valueOf(600000000));
            GoTo.gotomaps.put("sharenian", Integer.valueOf(990000000));
            GoTo.gotomaps.put("pianus", Integer.valueOf(230040420));
            GoTo.gotomaps.put("horntail", Integer.valueOf(240060200));
            GoTo.gotomaps.put("chorntail", Integer.valueOf(240060201));
            GoTo.gotomaps.put("mushmom", Integer.valueOf(100000005));
            GoTo.gotomaps.put("griffey", Integer.valueOf(240020101));
            GoTo.gotomaps.put("manon", Integer.valueOf(240020401));
            GoTo.gotomaps.put("zakum", Integer.valueOf(280030000));
            GoTo.gotomaps.put("czakum", Integer.valueOf(280030001));
            GoTo.gotomaps.put("papulatus", Integer.valueOf(220080001));
            GoTo.gotomaps.put("showatown", Integer.valueOf(801000000));
            GoTo.gotomaps.put("zipangu", Integer.valueOf(800000000));
            GoTo.gotomaps.put("ariant", Integer.valueOf(260000100));
            GoTo.gotomaps.put("nautilus", Integer.valueOf(120000000));
            GoTo.gotomaps.put("boatquay", Integer.valueOf(541000000));
            GoTo.gotomaps.put("malaysia", Integer.valueOf(550000000));
            GoTo.gotomaps.put("taiwan", Integer.valueOf(740000000));
            GoTo.gotomaps.put("thailand", Integer.valueOf(500000000));
            GoTo.gotomaps.put("erev", Integer.valueOf(130000000));
            GoTo.gotomaps.put("ellinforest", Integer.valueOf(300000000));
            GoTo.gotomaps.put("kampung", Integer.valueOf(551000000));
            GoTo.gotomaps.put("singapore", Integer.valueOf(540000000));
            GoTo.gotomaps.put("amoria", Integer.valueOf(680000000));
            GoTo.gotomaps.put("timetemple", Integer.valueOf(270000000));
            GoTo.gotomaps.put("pinkbean", Integer.valueOf(270050100));
            GoTo.gotomaps.put("peachblossom", Integer.valueOf(700000000));
            GoTo.gotomaps.put("fm", Integer.valueOf(910000000));
            GoTo.gotomaps.put("freemarket", Integer.valueOf(910000000));
            GoTo.gotomaps.put("oxquiz", Integer.valueOf(109020001));
            GoTo.gotomaps.put("ola", Integer.valueOf(109030101));
            GoTo.gotomaps.put("fitness", Integer.valueOf(109040000));
            GoTo.gotomaps.put("snowball", Integer.valueOf(109060000));
            GoTo.gotomaps.put("cashmap", Integer.valueOf(741010200));
            GoTo.gotomaps.put("golden", Integer.valueOf(950100000));
            GoTo.gotomaps.put("phantom", Integer.valueOf(610010000));
            GoTo.gotomaps.put("cwk", Integer.valueOf(610030000));
            GoTo.gotomaps.put("rien", Integer.valueOf(140000000));
        }
    }
    
    public static class warp extends CommandExecute
    {
        private static final HashMap<String, Integer> gotomaps;
        
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().dropMessage(6, "????????????: !goto <????????????>");
            }
            else if (warp.gotomaps.containsKey(splitted[1])) {
                final MapleMap target = c.getChannelServer().getMapFactory().getMap((int)Integer.valueOf(warp.gotomaps.get(splitted[1])));
                final MaplePortal targetPortal = target.getPortal(0);
                c.getPlayer().changeMap(target, targetPortal);
            }
            else if (splitted[1].equals("?????????")) {
                c.getPlayer().dropMessage(6, "?????? !goto <?????????>. ?????????????????????:");
                final StringBuilder sb = new StringBuilder();
                for (final String s : warp.gotomaps.keySet()) {
                    sb.append(s).append(", ");
                }
                c.getPlayer().dropMessage(6, sb.substring(0, sb.length() - 2));
            }
            else {
                c.getPlayer().dropMessage(6, "????????????????????? - ?????? !goto <?????????>. ???????????????????????????, ???????????? !goto ?????????????????????.");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!warp <????????????> - ?????????????????????").toString();
        }
        
        static {
            (gotomaps = new HashMap<String, Integer>()).put("gmmap", Integer.valueOf(180000000));
            warp.gotomaps.put("southperry", Integer.valueOf(2000000));
            warp.gotomaps.put("amherst", Integer.valueOf(1010000));
            warp.gotomaps.put("henesys", Integer.valueOf(100000000));
            warp.gotomaps.put("ellinia", Integer.valueOf(101000000));
            warp.gotomaps.put("perion", Integer.valueOf(102000000));
            warp.gotomaps.put("kerning", Integer.valueOf(103000000));
            warp.gotomaps.put("lithharbour", Integer.valueOf(104000000));
            warp.gotomaps.put("sleepywood", Integer.valueOf(105040300));
            warp.gotomaps.put("florina", Integer.valueOf(110000000));
            warp.gotomaps.put("orbis", Integer.valueOf(200000000));
            warp.gotomaps.put("happyville", Integer.valueOf(209000000));
            warp.gotomaps.put("elnath", Integer.valueOf(211000000));
            warp.gotomaps.put("ludibrium", Integer.valueOf(220000000));
            warp.gotomaps.put("aquaroad", Integer.valueOf(230000000));
            warp.gotomaps.put("leafre", Integer.valueOf(240000000));
            warp.gotomaps.put("mulung", Integer.valueOf(250000000));
            warp.gotomaps.put("herbtown", Integer.valueOf(251000000));
            warp.gotomaps.put("omegasector", Integer.valueOf(221000000));
            warp.gotomaps.put("koreanfolktown", Integer.valueOf(222000000));
            warp.gotomaps.put("newleafcity", Integer.valueOf(600000000));
            warp.gotomaps.put("sharenian", Integer.valueOf(990000000));
            warp.gotomaps.put("pianus", Integer.valueOf(230040420));
            warp.gotomaps.put("horntail", Integer.valueOf(240060200));
            warp.gotomaps.put("chorntail", Integer.valueOf(240060201));
            warp.gotomaps.put("mushmom", Integer.valueOf(100000005));
            warp.gotomaps.put("griffey", Integer.valueOf(240020101));
            warp.gotomaps.put("manon", Integer.valueOf(240020401));
            warp.gotomaps.put("zakum", Integer.valueOf(280030000));
            warp.gotomaps.put("czakum", Integer.valueOf(280030001));
            warp.gotomaps.put("papulatus", Integer.valueOf(220080001));
            warp.gotomaps.put("showatown", Integer.valueOf(801000000));
            warp.gotomaps.put("zipangu", Integer.valueOf(800000000));
            warp.gotomaps.put("ariant", Integer.valueOf(260000100));
            warp.gotomaps.put("nautilus", Integer.valueOf(120000000));
            warp.gotomaps.put("boatquay", Integer.valueOf(541000000));
            warp.gotomaps.put("malaysia", Integer.valueOf(550000000));
            warp.gotomaps.put("taiwan", Integer.valueOf(740000000));
            warp.gotomaps.put("thailand", Integer.valueOf(500000000));
            warp.gotomaps.put("erev", Integer.valueOf(130000000));
            warp.gotomaps.put("ellinforest", Integer.valueOf(300000000));
            warp.gotomaps.put("kampung", Integer.valueOf(551000000));
            warp.gotomaps.put("singapore", Integer.valueOf(540000000));
            warp.gotomaps.put("amoria", Integer.valueOf(680000000));
            warp.gotomaps.put("timetemple", Integer.valueOf(270000000));
            warp.gotomaps.put("pinkbean", Integer.valueOf(270050100));
            warp.gotomaps.put("peachblossom", Integer.valueOf(700000000));
            warp.gotomaps.put("fm", Integer.valueOf(910000000));
            warp.gotomaps.put("freemarket", Integer.valueOf(910000000));
            warp.gotomaps.put("oxquiz", Integer.valueOf(109020001));
            warp.gotomaps.put("ola", Integer.valueOf(109030101));
            warp.gotomaps.put("fitness", Integer.valueOf(109040000));
            warp.gotomaps.put("snowball", Integer.valueOf(109060000));
            warp.gotomaps.put("cashmap", Integer.valueOf(741010200));
            warp.gotomaps.put("golden", Integer.valueOf(950100000));
            warp.gotomaps.put("phantom", Integer.valueOf(610010000));
            warp.gotomaps.put("cwk", Integer.valueOf(610030000));
            warp.gotomaps.put("rien", Integer.valueOf(140000000));
        }
    }
    
    public static class cleardrops extends RemoveDrops
    {
    }
    
    public static class ?????????????????? extends RemoveDrops
    {
    }
    
    public static class RemoveDrops extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().dropMessage(5, "????????? " + c.getPlayer().getMap().getNumItems() + " ????????????");
            c.getPlayer().getMap().removeDrops();
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!removedrops - ?????????????????????").toString();
        }
    }
    
    public static class NearestPortal extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            final MaplePortal portal = c.getPlayer().getMap().findClosestSpawnpoint(c.getPlayer().getPosition());
            c.getPlayer().dropMessage(6, portal.getName() + " id: " + portal.getId() + " script: " + portal.getScriptName());
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!nearestportal - ????????????").toString();
        }
    }
    
    public static class SpawnDebug extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().dropMessage(6, c.getPlayer().getMap().spawnDebug());
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!spawndebug - debug????????????").toString();
        }
    }
    
    public static class Speak extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            final String name = splitted[1];
            final int ch = Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "??????????????????");
                return true;
            }
            final MapleCharacter victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (victim == null) {
                c.getPlayer().dropMessage(5, "????????? '" + splitted[1]);
                return false;
            }
            victim.getMap().broadcastMessage(MaplePacketCreator.getChatText(victim.getId(), StringUtil.joinStringFrom(splitted, 2), victim.isGM(), 0));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!speak <????????????> <??????> - ????????????????????????").toString();
        }
    }
    
    public static class SpeakMap extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            for (final MapleCharacter victim : c.getPlayer().getMap().getCharactersThreadsafe()) {
                if (victim.getId() != c.getPlayer().getId()) {
                    victim.getMap().broadcastMessage(MaplePacketCreator.getChatText(victim.getId(), StringUtil.joinStringFrom(splitted, 1), victim.isGM(), 0));
                }
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!speakmap <??????> - ?????????????????????????????????").toString();
        }
    }
    
    public static class SpeakChannel extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            for (final MapleCharacter victim : c.getChannelServer().getPlayerStorage().getAllCharactersThreadSafe()) {
                if (victim.getId() != c.getPlayer().getId()) {
                    victim.getMap().broadcastMessage(MaplePacketCreator.getChatText(victim.getId(), StringUtil.joinStringFrom(splitted, 1), victim.isGM(), 0));
                }
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!speakchannel <??????> - ?????????????????????????????????").toString();
        }
    }
    
    public static class SpeakWorld extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (final MapleCharacter victim : cserv.getPlayerStorage().getAllCharactersThreadSafe()) {
                    if (victim.getId() != c.getPlayer().getId()) {
                        victim.getMap().broadcastMessage(MaplePacketCreator.getChatText(victim.getId(), StringUtil.joinStringFrom(splitted, 1), victim.isGM(), 0));
                    }
                }
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!speakchannel <??????> - ????????????????????????????????????").toString();
        }
    }
    
    public static class SpeakMega extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            MapleCharacter victim = null;
            if (splitted.length >= 2) {
                victim = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
            }
            try {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(3, (victim == null) ? c.getChannel() : victim.getClient().getChannel(), (victim == null) ? splitted[1] : (victim.getName() + " : " + StringUtil.joinStringFrom(splitted, 2)), true));
            }
            catch (Exception e) {
                return false;
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!speakmega [????????????] <??????> - ????????????????????????????????????").toString();
        }
    }
    
    public static class Say extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length > 1) {
                final StringBuilder sb = new StringBuilder();
                sb.append("[");
                sb.append(c.getPlayer().getName());
                sb.append("] ");
                sb.append(StringUtil.joinStringFrom(splitted, 1));
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, sb.toString()));
                return true;
            }
            return false;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!say ?????? - ???????????????").toString();
        }
    }
    
    public static class ??????????????? extends Shutdown
    {
    }
    
    public static class Shutdown extends CommandExecute
    {
        private static Thread t;
        
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().dropMessage(6, "??????????????????...");
            if (Shutdown.t == null || !Shutdown.t.isAlive()) {
                (Shutdown.t = new Thread((Runnable)ShutdownServer.getInstance())).start();
            }
            else {
                c.getPlayer().dropMessage(6, "???????????????...");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!shutdown - ???????????????").toString();
        }
        
        static {
            Shutdown.t = null;
        }
    }
    
    public static class ????????????????????? extends ShutdownTime
    {
    }
    
    public static class ShutdownTime extends CommandExecute
    {
        private static ScheduledFuture<?> ts;
        private int minutesLeft;
        private static Thread t;
        
        public ShutdownTime() {
            this.minutesLeft = 0;
        }
        
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            this.minutesLeft = Integer.parseInt(splitted[1]);
            c.getPlayer().dropMessage(6, "??????????????? " + this.minutesLeft + "???????????????. ??????????????????????????? ?????????.");
            if (ShutdownTime.ts == null && (ShutdownTime.t == null || !ShutdownTime.t.isAlive())) {
                ShutdownTime.t = new Thread((Runnable)ShutdownServer.getInstance());
                ShutdownTime.ts = EventTimer.getInstance().register((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        if (minutesLeft > 0 && minutesLeft <= 11 && !World.isShutDown) {
                            World.isShutDown = true;
                            c.getPlayer().dropMessage(6, "???????????????????????????????????????");
                        }
                        else if (minutesLeft == 0) {
                            ShutdownServer.getInstance().shutdown();
                            t.start();
                            ts.cancel(false);
                            return;
                        }
                        final StringBuilder message = new StringBuilder();
                        message.append("[???????????????] ??????????????? ");
                        message.append(minutesLeft);
                        message.append(" ???????????????????????????????????????????????????????????????");
                        Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, message.toString()));
                        Broadcast.broadcastMessage(MaplePacketCreator.serverMessage(message.toString()));
                        for (final ChannelServer cs : ChannelServer.getAllInstances()) {
                            cs.setServerMessage("??????????????? " + minutesLeft + " ???????????????");
                        }
                        minutesLeft--;
                    }
                }, 60000L);
            }
            else {
                c.getPlayer().dropMessage(6, "?????????????????????????????? " + this.minutesLeft + "????????????????????????????????????");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!shutdowntime <?????????> - ???????????????").toString();
        }
        
        static {
            ShutdownTime.ts = null;
            ShutdownTime.t = null;
        }
    }
    
    public static class UnbanIP extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            final byte ret_ = MapleClient.unbanIP(splitted[1]);
            if (ret_ == -2) {
                c.getPlayer().dropMessage(6, "[unbanip] SQL ??????.");
            }
            else if (ret_ == -1) {
                c.getPlayer().dropMessage(6, "[unbanip] ???????????????.");
            }
            else if (ret_ == 0) {
                c.getPlayer().dropMessage(6, "[unbanip] No IP or Mac with that character exists!");
            }
            else if (ret_ == 1) {
                c.getPlayer().dropMessage(6, "[unbanip] IP???Mac?????????????????????.");
            }
            else if (ret_ == 2) {
                c.getPlayer().dropMessage(6, "[unbanip] IP??????Mac???????????????.");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!unbanip <????????????> - ????????????").toString();
        }
    }
    
    public static class ?????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            final byte ret_ = MapleClient.unbanIP(splitted[1]);
            if (ret_ == -2) {
                c.getPlayer().dropMessage(6, "[unbanip] SQL ??????.");
            }
            else if (ret_ == -1) {
                c.getPlayer().dropMessage(6, "[unbanip] ???????????????.");
            }
            else if (ret_ == 0) {
                c.getPlayer().dropMessage(6, "[unbanip] No IP or Mac with that character exists!");
            }
            else if (ret_ == 1) {
                c.getPlayer().dropMessage(6, "[unbanip] IP???Mac?????????????????????.");
            }
            else if (ret_ == 2) {
                c.getPlayer().dropMessage(6, "[unbanip] IP??????Mac???????????????.");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!?????? <????????????> - ????????????").toString();
        }
    }
    
    public static class TempBan extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            final String name = splitted[1];
            final int ch = Find.findChannel(name);
            if (ch <= 0) {
                return false;
            }
            final MapleCharacter victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            final int reason = Integer.parseInt(splitted[2]);
            final int numDay = Integer.parseInt(splitted[3]);
            final Calendar cal = Calendar.getInstance();
            cal.add(5, numDay);
            final DateFormat df = DateFormat.getInstance();
            if (victim == null) {
                c.getPlayer().dropMessage(6, "[tempban] ?????????????????????");
            }
            else {
                victim.tempban("???" + c.getPlayer().getName() + "???????????????", cal, reason, true);
                c.getPlayer().dropMessage(6, "[tempban] " + splitted[1] + " ??????????????????????????? " + df.format(cal.getTime()));
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!tempban <????????????> - ??????????????????").toString();
        }
    }
    
    public static class ?????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            final String name = splitted[1];
            final int ch = Find.findChannel(name);
            if (ch <= 0) {
                return false;
            }
            final MapleCharacter victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            final int reason = Integer.parseInt(splitted[2]);
            final int numDay = Integer.parseInt(splitted[3]);
            final Calendar cal = Calendar.getInstance();
            cal.add(5, numDay);
            final DateFormat df = DateFormat.getInstance();
            if (victim == null) {
                c.getPlayer().dropMessage(6, "[tempban] ?????????????????????");
            }
            else {
                victim.tempban("???" + c.getPlayer().getName() + "???????????????", cal, reason, true);
                c.getPlayer().dropMessage(6, "[tempban] " + splitted[1] + " ??????????????????????????? " + df.format(cal.getTime()));
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!?????? <????????????> - ??????????????????").toString();
        }
    }
    
    public static class ?????????????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            World.isShutDown = !World.isShutDown;
            c.getPlayer().dropMessage(0, "[??????????????????] " + (World.isShutDown ? "??????" : "??????"));
            System.out.println("[??????????????????] " + (World.isShutDown ? "??????" : "??????"));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!??????????????????  - ???????????????????????????").toString();
        }
    }
    
    public static class ???????????????????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            World.isShopShutDown = !World.isShopShutDown;
            c.getPlayer().dropMessage(0, "[????????????????????????] " + (World.isShopShutDown ? "??????" : "??????"));
            System.out.println("[????????????????????????] " + (World.isShopShutDown ? "??????" : "??????"));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!????????????????????????  - ????????????????????????").toString();
        }
    }
    
    public static class copyAll extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            final MapleCharacter player = c.getPlayer();
            if (splitted.length < 2) {
                return false;
            }
            final String name = splitted[1];
            final int ch = Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "??????????????????");
                return true;
            }
            final MapleCharacter victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (victim == null) {
                player.dropMessage("??????????????????");
                return true;
            }
            final MapleInventory equipped = c.getPlayer().getInventory(MapleInventoryType.EQUIPPED);
            final MapleInventory equip = c.getPlayer().getInventory(MapleInventoryType.EQUIP);
            final List<Short> ids = new LinkedList<Short>();
            for (final IItem item : equipped.list()) {
                ids.add(item.getPosition());
            }
            final Iterator<Short> iterator2 = ids.iterator();
            while (iterator2.hasNext()) {
                final short id = (short)Short.valueOf(iterator2.next());
                MapleInventoryManipulator.unequip(c, id, equip.getNextFreeSlot());
            }
            c.getPlayer().clearSkills();
            c.getPlayer().setStr(victim.getStr());
            c.getPlayer().setDex(victim.getDex());
            c.getPlayer().setInt(victim.getInt());
            c.getPlayer().setLuk(victim.getLuk());
            c.getPlayer().setMeso(victim.getMeso());
            c.getPlayer().setLevel(victim.getLevel());
            c.getPlayer().changeJob((int)victim.getJob());
            c.getPlayer().setHp(victim.getHp());
            c.getPlayer().setMp(victim.getMp());
            c.getPlayer().setMaxHp(victim.getMaxHp());
            c.getPlayer().setMaxMp(victim.getMaxMp());
            final String normal = victim.getName();
            final String after = normal + "x2";
            if (after.length() <= 12) {
                c.getPlayer().setName(victim.getName() + "x2");
            }
            c.getPlayer().setRemainingAp(victim.getRemainingAp());
            c.getPlayer().setRemainingSp(victim.getRemainingSp());
            c.getPlayer().LearnSameSkill(victim);
            c.getPlayer().setFame(victim.getFame());
            c.getPlayer().setHair(victim.getHair());
            c.getPlayer().setFace(victim.getFace());
            c.getPlayer().setSkinColor((victim.getSkinColor() == 0) ? c.getPlayer().getSkinColor() : victim.getSkinColor());
            c.getPlayer().setGender(victim.getGender());
            for (final IItem ii : victim.getInventory(MapleInventoryType.EQUIPPED).list()) {
                final IItem eq = ii.copy();
                eq.setPosition(eq.getPosition());
                eq.setQuantity((short)1);
                c.getPlayer().forceReAddItem_NoUpdate(eq, MapleInventoryType.EQUIPPED);
            }
            c.getPlayer().fakeRelog();
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!copyall ???????????? - ????????????").toString();
        }
    }
    
    public static class copyInv extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            final MapleCharacter player = c.getPlayer();
            int type = 1;
            if (splitted.length < 2) {
                return false;
            }
            final String name = splitted[1];
            final int ch = Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "??????????????????");
                return false;
            }
            final MapleCharacter victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (victim == null) {
                player.dropMessage("??????????????????");
                return true;
            }
            try {
                type = Integer.parseInt(splitted[2]);
            }
            catch (Exception ex) {}
            if (type == 0) {
                for (final IItem ii : victim.getInventory(MapleInventoryType.EQUIPPED).list()) {
                    final IItem n = ii.copy();
                    player.getInventory(MapleInventoryType.EQUIP).addItem(n);
                }
                player.fakeRelog();
            }
            else {
                MapleInventoryType types;
                if (type == 1) {
                    types = MapleInventoryType.EQUIP;
                }
                else if (type == 2) {
                    types = MapleInventoryType.USE;
                }
                else if (type == 3) {
                    types = MapleInventoryType.ETC;
                }
                else if (type == 4) {
                    types = MapleInventoryType.SETUP;
                }
                else if (type == 5) {
                    types = MapleInventoryType.CASH;
                }
                else {
                    types = null;
                }
                if (types == null) {
                    c.getPlayer().dropMessage("????????????");
                    return true;
                }
                final int[] equip = new int[97];
                for (int i = 1; i < 97; ++i) {
                    if (victim.getInventory(types).getItem((short)i) != null) {
                        equip[i] = i;
                    }
                }
                for (int i = 0; i < equip.length; ++i) {
                    if (equip[i] != 0) {
                        final IItem n2 = victim.getInventory(types).getItem((short)equip[i]).copy();
                        player.getInventory(types).addItem(n2);
                        c.sendPacket(MaplePacketCreator.modifyInventory(false, new ModifyInventory(0, n2)));
                    }
                }
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!copyinv ???????????? ????????????(0 = ????????? 1=????????? 2=????????? 3=????????? 4=????????? 5=?????????)(???????????????) - ??????????????????").toString();
        }
    }
    
    public static class Clock extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.getClock(CommandProcessorUtil.getOptionalIntArg(splitted, 1, 60)));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!clock <time> ??????").toString();
        }
    }
    
    public static class Song extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.musicChange(splitted[1]));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!song - ????????????").toString();
        }
    }
    
    public static class ?????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            final MapleCharacter player = c.getPlayer();
            if (splitted.length < 2) {
                return false;
            }
            final String name = splitted[1];
            final int ch = Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "??????????????????");
                return true;
            }
            final MapleCharacter victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (victim == null) {
                c.getPlayer().dropMessage(6, "[kill] ?????? " + name + " ?????????.");
            }
            else if (player.allowedToTarget(victim)) {
                victim.getStat().setHp(0);
                victim.getStat().setMp(0);
                victim.updateSingleStat(MapleStat.HP, 0);
                victim.updateSingleStat(MapleStat.MP, 0);
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("*?????? <????????????> - ????????????").toString();
        }
    }
    
    public static class ReloadOps extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            SendPacketOpcode.reloadValues();
            RecvPacketOpcode.reloadValues();
            c.getPlayer().dropMessage(6, "??????????????????????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!reloadops - ????????????OpCode").toString();
        }
    }
    
    public static class ReloadDrops extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            MapleMonsterInformationProvider.getInstance().clearDrops();
            ReactorScriptManager.getInstance().clearDrops();
            c.getPlayer().dropMessage(6, "?????????????????????????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!reloaddrops - ??????????????????").toString();
        }
    }
    
    public static class ???????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            SendPacketOpcode.reloadValues();
            RecvPacketOpcode.reloadValues();
            c.getPlayer().dropMessage(6, "??????????????????????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!???????????? - ??????????????????").toString();
        }
    }
    
    public static class ???????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            MapleMonsterInformationProvider.getInstance().clearDrops();
            ReactorScriptManager.getInstance().clearDrops();
            c.getPlayer().dropMessage(6, "?????????????????????????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!???????????? - ??????????????????").toString();
        }
    }
    
    public static class ??????????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            PortalScriptManager.getInstance().clearScripts();
            c.getPlayer().dropMessage(6, "???????????????????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!??????????????? - ?????????????????????").toString();
        }
    }
    
    public static class ???????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            MapleShopFactory.getInstance().clear();
            c.getPlayer().dropMessage(6, "NPC?????????????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!???????????? - ??????????????????").toString();
        }
    }
    
    public static class ???????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            CashItemFactory.getInstance().clearItems();
            c.getPlayer().dropMessage(6, "???????????????????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!???????????? - ????????????????????????").toString();
        }
    }
    
    public static class ?????????????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            FishingRewardFactory.getInstance().reloadItems();
            c.getPlayer().dropMessage(6, "?????????????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!?????????????????? - ????????????????????????").toString();
        }
    }
    
    public static class ReloadPortals extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            PortalScriptManager.getInstance().clearScripts();
            c.getPlayer().dropMessage(6, "???????????????????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!reloadportals - ?????????????????????").toString();
        }
    }
    
    public static class ReloadShops extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            MapleShopFactory.getInstance().clear();
            c.getPlayer().dropMessage(6, "NPC?????????????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!reloadshops - ??????????????????").toString();
        }
    }
    
    public static class ReloadCS extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            CashItemFactory.getInstance().clearItems();
            c.getPlayer().dropMessage(6, "???????????????????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!reloadCS - ????????????????????????").toString();
        }
    }
    
    public static class ReloadFishing extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            FishingRewardFactory.getInstance().reloadItems();
            c.getPlayer().dropMessage(6, "?????????????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!reloadFishing - ????????????????????????").toString();
        }
    }
    
    public static class ReloadEvents extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            for (final ChannelServer instance : ChannelServer.getAllInstances()) {
                instance.reloadEvents();
            }
            c.getPlayer().dropMessage(6, "?????????????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!reloadevents - ????????????????????????").toString();
        }
    }
    
    public static class ReloadQuests extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            MapleQuest.clearQuests();
            c.getPlayer().dropMessage(6, "?????????????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!reloadquests - ??????????????????").toString();
        }
    }
    
    public static class ???????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            MapleQuest.clearQuests();
            c.getPlayer().dropMessage(6, "?????????????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!???????????? - ??????????????????").toString();
        }
    }
    
    public static class ???????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            for (final ChannelServer instance : ChannelServer.getAllInstances()) {
                instance.reloadEvents();
            }
            c.getPlayer().dropMessage(6, "?????????????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!????????????- ????????????????????????").toString();
        }
    }
    
    public static class ReloadOX extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            MapleOxQuizFactory.getInstance().reloadOX();
            c.getPlayer().dropMessage(6, "OX?????????????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!reloadox - ????????????OX??????").toString();
        }
    }
    
    public static class ReloadLife extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().dropMessage("?????????????????????????????? ???????????????:" + MapleMapFactory.loadCustomLife(true, c.getPlayer().getMap()));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!ReloadLife - ??????????????????NPC/??????").toString();
        }
    }
    
    public static class Reloadall extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            for (final ChannelServer instance : ChannelServer.getAllInstances()) {
                instance.reloadEvents();
            }
            MapleShopFactory.getInstance().clear();
            PortalScriptManager.getInstance().clearScripts();
            MapleItemInformationProvider.getInstance().load();
            CashItemFactory.getInstance().initialize();
            MapleMonsterInformationProvider.getInstance().clearDrops();
            MapleGuild.loadAll();
            MapleFamily.loadAll();
            MapleLifeFactory.loadQuestCounts();
            MapleQuest.initQuests();
            MapleOxQuizFactory.getInstance();
            ReactorScriptManager.getInstance().clearDrops();
            SendPacketOpcode.reloadValues();
            RecvPacketOpcode.reloadValues();
            c.getPlayer().dropMessage(6, "???????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!Reloadall - ??????????????????").toString();
        }
    }
    
    public static class Skill extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            final ISkill skill = SkillFactory.getSkill(Integer.parseInt(splitted[1]));
            byte level = (byte)CommandProcessorUtil.getOptionalIntArg(splitted, 2, 1);
            final byte masterlevel = (byte)CommandProcessorUtil.getOptionalIntArg(splitted, 3, 1);
            if (level > skill.getMaxLevel()) {
                level = skill.getMaxLevel();
            }
            c.getPlayer().changeSkillLevel(skill, level, masterlevel);
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!skill <??????ID> [????????????] [??????????????????] ...  - ????????????").toString();
        }
    }
    
    public static class GiveSkill extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                return false;
            }
            final String name = splitted[1];
            final int ch = Find.findChannel(name);
            if (ch <= 0) {
                return false;
            }
            final MapleCharacter victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            final ISkill skill = SkillFactory.getSkill(Integer.parseInt(splitted[2]));
            byte level = (byte)CommandProcessorUtil.getOptionalIntArg(splitted, 3, 1);
            final byte masterlevel = (byte)CommandProcessorUtil.getOptionalIntArg(splitted, 4, 1);
            if (level > skill.getMaxLevel()) {
                level = skill.getMaxLevel();
            }
            victim.changeSkillLevel(skill, level, masterlevel);
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!giveskill <????????????> <??????ID> [????????????] [??????????????????] - ????????????").toString();
        }
    }
    
    public static class MaxSkills extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().maxSkills();
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!MaxSkills - ????????????").toString();
        }
    }
    
    public static class ????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().maxSkills();
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!????????? - ????????????").toString();
        }
    }
    
    public static class ClearSkills extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().clearSkills();
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!ClearSkills - ????????????").toString();
        }
    }
    
    public static class SP extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().setRemainingSp(CommandProcessorUtil.getOptionalIntArg(splitted, 1, 1));
            c.sendPacket(MaplePacketCreator.updateSp(c.getPlayer(), false));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!sp [??????] - ??????SP").toString();
        }
    }
    
    public static class GiveSP extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            final String name = splitted[1];
            final int sp = Integer.parseInt(splitted[2]);
            final int ch = Find.findChannel(name);
            if (ch <= 0) {
                return false;
            }
            final MapleCharacter victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (victim != null) {
                victim.gainSP(sp);
                c.sendPacket(MaplePacketCreator.updateSp(victim, false));
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!sp [????????????] [??????] - ??????SP").toString();
        }
    }
    
    public static class AP extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().setRemainingAp((short)CommandProcessorUtil.getOptionalIntArg(splitted, 1, 1));
            final List<Pair<MapleStat, Integer>> statupdate = new ArrayList<Pair<MapleStat, Integer>>();
            c.sendPacket(MaplePacketCreator.updateAp(c.getPlayer(), false));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!ap [??????] - ??????AP").toString();
        }
    }
    
    public static class Shop extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            final MapleShopFactory shop = MapleShopFactory.getInstance();
            int shopId = 0;
            try {
                shopId = Integer.parseInt(splitted[1]);
            }
            catch (Exception ex) {}
            if (shop.getShop(shopId) != null) {
                shop.getShop(shopId).sendShop(c);
            }
            else {
                c.getPlayer().dropMessage(5, "?????????ID[" + shopId + "]?????????");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!shop - ????????????").toString();
        }
    }
    
    public static class ???????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            final MapleShopFactory shop = MapleShopFactory.getInstance();
            int shopId = 0;
            try {
                shopId = Integer.parseInt(splitted[1]);
            }
            catch (Exception ex) {}
            if (shop.getShop(shopId) != null) {
                shop.getShop(shopId).sendShop(c);
            }
            else {
                c.getPlayer().dropMessage(5, "?????????ID[" + shopId + "]?????????");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!???????????? - ????????????").toString();
        }
    }
    
    public static class ???????????? extends CommandExecute
    {
        protected static ScheduledFuture<?> ts;
        
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 1) {
                return false;
            }
            if (????????????.ts != null) {
                ????????????.ts.cancel(false);
                c.getPlayer().dropMessage(0, "??????????????????????????????");
            }
            int minutesLeft;
            try {
                minutesLeft = Integer.parseInt(splitted[1]);
            }
            catch (NumberFormatException ex) {
                return false;
            }
            if (minutesLeft > 0) {
                ????????????.ts = EventTimer.getInstance().schedule((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                            for (final MapleCharacter mch : cserv.getPlayerStorage().getAllCharactersThreadSafe()) {
                                if (mch.getLevel() >= 29 && !mch.isGM()) {
                                    NPCScriptManager.getInstance().start(mch.getClient(), 9010010, "CrucialTime");
                                }
                            }
                        }
                        Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "??????????????????????????????30????????????????????????????????????"));
                        ts.cancel(false);
                        ts = null;
                    }
                }, (long)minutesLeft);
                c.getPlayer().dropMessage(0, "???????????????????????????");
            }
            else {
                c.getPlayer().dropMessage(0, "????????????????????? > 0???");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!???????????? <??????:??????> - ????????????").toString();
        }
        
        static {
            ????????????.ts = null;
        }
    }
    
    public static class UnlockInv extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            final Map<IItem, MapleInventoryType> eqs = new ArrayMap<IItem, MapleInventoryType>();
            boolean add = false;
            if (splitted.length < 2 || splitted[1].equals("??????")) {
                for (final MapleInventoryType type : MapleInventoryType.values()) {
                    for (final IItem item : c.getPlayer().getInventory(type)) {
                        if (ItemFlag.LOCK.check((int)item.getFlag())) {
                            item.setFlag((byte)(item.getFlag() - ItemFlag.LOCK.getValue()));
                            add = true;
                            c.getPlayer().reloadC();
                            c.getPlayer().dropMessage(5, "????????????");
                        }
                        if (ItemFlag.UNTRADEABLE.check((int)item.getFlag())) {
                            item.setFlag((byte)(item.getFlag() - ItemFlag.UNTRADEABLE.getValue()));
                            add = true;
                            c.getPlayer().reloadC();
                            c.getPlayer().dropMessage(5, "????????????");
                        }
                        if (add) {
                            eqs.put(item, type);
                        }
                        add = false;
                    }
                }
            }
            else if (splitted[1].equals("???????????????")) {
                for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.EQUIPPED)) {
                    if (ItemFlag.LOCK.check((int)item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.LOCK.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "????????????");
                    }
                    if (ItemFlag.UNTRADEABLE.check((int)item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.UNTRADEABLE.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "????????????");
                    }
                    if (add) {
                        eqs.put(item2, MapleInventoryType.EQUIP);
                    }
                    add = false;
                }
            }
            else if (splitted[1].equals("??????")) {
                for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.EQUIP)) {
                    if (ItemFlag.LOCK.check((int)item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.LOCK.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "????????????");
                    }
                    if (ItemFlag.UNTRADEABLE.check((int)item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.UNTRADEABLE.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "????????????");
                    }
                    if (add) {
                        eqs.put(item2, MapleInventoryType.EQUIP);
                    }
                    add = false;
                }
            }
            else if (splitted[1].equals("??????")) {
                for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.USE)) {
                    if (ItemFlag.LOCK.check((int)item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.LOCK.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "????????????");
                    }
                    if (ItemFlag.UNTRADEABLE.check((int)item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.UNTRADEABLE.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "????????????");
                    }
                    if (add) {
                        eqs.put(item2, MapleInventoryType.USE);
                    }
                    add = false;
                }
            }
            else if (splitted[1].equals("??????")) {
                for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.SETUP)) {
                    if (ItemFlag.LOCK.check((int)item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.LOCK.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "????????????");
                    }
                    if (ItemFlag.UNTRADEABLE.check((int)item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.UNTRADEABLE.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "????????????");
                    }
                    if (add) {
                        eqs.put(item2, MapleInventoryType.SETUP);
                    }
                    add = false;
                }
            }
            else if (splitted[1].equals("??????")) {
                for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.ETC)) {
                    if (ItemFlag.LOCK.check((int)item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.LOCK.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "????????????");
                    }
                    if (ItemFlag.UNTRADEABLE.check((int)item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.UNTRADEABLE.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "????????????");
                    }
                    if (add) {
                        eqs.put(item2, MapleInventoryType.ETC);
                    }
                    add = false;
                }
            }
            else {
                if (!splitted[1].equals("??????")) {
                    return false;
                }
                for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.CASH)) {
                    if (ItemFlag.LOCK.check((int)item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.LOCK.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "????????????");
                    }
                    if (ItemFlag.UNTRADEABLE.check((int)item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.UNTRADEABLE.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "????????????");
                    }
                    if (add) {
                        eqs.put(item2, MapleInventoryType.CASH);
                    }
                    add = false;
                }
            }
            for (final Entry<IItem, MapleInventoryType> eq : eqs.entrySet()) {
                c.getPlayer().forceReAddItem_NoUpdate(((IItem)eq.getKey()).copy(), (MapleInventoryType)eq.getValue());
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!unlockinv <??????/???????????????/??????/??????/??????/??????/??????> - ????????????").toString();
        }
    }
    
    public static class Letter extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                c.getPlayer().dropMessage(6, "????????????: ");
                return false;
            }
            int start;
            int nstart;
            if (splitted[1].equalsIgnoreCase("green")) {
                start = 3991026;
                nstart = 3990019;
            }
            else {
                if (!splitted[1].equalsIgnoreCase("red")) {
                    c.getPlayer().dropMessage(6, "???????????????!");
                    return true;
                }
                start = 3991000;
                nstart = 3990009;
            }
            String splitString = StringUtil.joinStringFrom(splitted, 2);
            final List<Integer> chars = new ArrayList<Integer>();
            splitString = splitString.toUpperCase();
            for (int i = 0; i < splitString.length(); ++i) {
                final char chr = splitString.charAt(i);
                if (chr == ' ') {
                    chars.add(Integer.valueOf(-1));
                }
                else if (chr >= 'A' && chr <= 'Z') {
                    chars.add(Integer.valueOf((int)chr));
                }
                else if (chr >= '0' && chr <= '9') {
                    chars.add(Integer.valueOf(chr + '??'));
                }
            }
            final int w = 32;
            int dStart = c.getPlayer().getPosition().x - splitString.length() / 2 * 32;
            for (final Integer j : chars) {
                if ((int)j == -1) {
                    dStart += 32;
                }
                else if ((int)j < 200) {
                    final int val = start + (int)j - 65;
                    final Item item = new Item(val, (byte)0, (short)1);
                    c.getPlayer().getMap().spawnItemDrop((MapleMapObject)c.getPlayer(), c.getPlayer(), (IItem)item, new Point(dStart, c.getPlayer().getPosition().y), false, false);
                    dStart += 32;
                }
                else {
                    if ((int)j < 200 || (int)j > 300) {
                        continue;
                    }
                    final int val = nstart + (int)j - 48 - 200;
                    final Item item = new Item(val, (byte)0, (short)1);
                    c.getPlayer().getMap().spawnItemDrop((MapleMapObject)c.getPlayer(), c.getPlayer(), (IItem)item, new Point(dStart, c.getPlayer().getPosition().y), false, false);
                    dStart += 32;
                }
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append(" !letter <color (green/red)> <word> - ??????").toString();
        }
    }
    
    public static class Marry extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                return false;
            }
            final int itemId = Integer.parseInt(splitted[2]);
            if (!GameConstants.isEffectRing(itemId)) {
                c.getPlayer().dropMessage(6, "???????????????ID.");
            }
            else {
                final String name = splitted[1];
                final int ch = Find.findChannel(name);
                if (ch <= 0) {
                    c.getPlayer().dropMessage(6, "??????????????????");
                    return false;
                }
                final MapleCharacter fff = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
                if (fff == null) {
                    c.getPlayer().dropMessage(6, "??????????????????");
                }
                else {
                    final int[] ringID = { MapleInventoryIdentifier.getInstance(), MapleInventoryIdentifier.getInstance() };
                    try {
                        final MapleCharacter[] chrz = { fff, c.getPlayer() };
                        for (int i = 0; i < chrz.length; ++i) {
                            final Equip eq = (Equip)MapleItemInformationProvider.getInstance().getEquipById(itemId);
                            if (eq == null) {
                                c.getPlayer().dropMessage(6, "???????????????ID.");
                                return true;
                            }
                            eq.setUniqueId(ringID[i]);
                            MapleInventoryManipulator.addbyItem(chrz[i].getClient(), eq.copy());
                            chrz[i].dropMessage(6, "?????????  " + chrz[i == 0? 1 : 0].getName() + " ??????");
                        }
                        MapleRing.addToDB(itemId, c.getPlayer(), fff.getName(), fff.getId(), ringID);
                    }
                    catch (SQLException ex) {}
                }
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!marry <????????????> <????????????> - ??????").toString();
        }
    }
    
    public static class KillID extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            final MapleCharacter player = c.getPlayer();
            if (splitted.length < 2) {
                return false;
            }
            int id = 0;
            try {
                id = Integer.parseInt(splitted[1]);
            }
            catch (Exception ex) {}
            final int ch = Find.findChannel(id);
            if (ch <= 0) {
                return false;
            }
            final MapleCharacter victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterById(id);
            if (victim == null) {
                c.getPlayer().dropMessage(6, "[kill] ??????ID " + id + " ?????????.");
            }
            else if (player.allowedToTarget(victim)) {
                victim.getStat().setHp(0);
                victim.getStat().setMp(0);
                victim.updateSingleStat(MapleStat.HP, 0);
                victim.updateSingleStat(MapleStat.MP, 0);
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!KillID <??????ID> - ????????????").toString();
        }
    }
    
    public static class autoreg extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            LoginServer.setAutoReg(!LoginServer.getAutoReg());
            c.getPlayer().dropMessage(0, "[autoreg] " + (LoginServer.getAutoReg() ? "??????" : "??????"));
            System.out.println("[autoreg] " + (LoginServer.getAutoReg() ? "??????" : "??????"));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!autoreg  - ??????????????????").toString();
        }
    }
    
    public static class ???????????? extends nmgb
    {
    }
    
    public static class nmgb extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            LoginServer.setNMGB(!LoginServer.getNMGB());
            c.getPlayer().dropMessage(0, "[????????????] " + (LoginServer.getNMGB() ? "??????" : "??????"));
            System.out.println("[????????????] " + (LoginServer.getNMGB() ? "??????" : "??????"));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!????????????  - ??????????????????").toString();
        }
    }
    
    public static class ?????????????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            LoginServer.setPDCS(!LoginServer.getPDCS());
            c.getPlayer().dropMessage(0, "[??????????????????] " + (LoginServer.getPDCS() ? "??????" : "??????"));
            System.out.println("[??????????????????] " + (LoginServer.getPDCS() ? "??????" : "??????"));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!??????????????????  - ??????????????????").toString();
        }
    }
    
    public static class logindoor extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            WorldConstants.ADMIN_ONLY = !WorldConstants.ADMIN_ONLY;
            c.getPlayer().dropMessage(0, "[logindoor] " + (WorldConstants.ADMIN_ONLY ? "??????" : "??????"));
            System.out.println("[logindoor] " + (WorldConstants.ADMIN_ONLY ? "??????" : "??????"));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!logindoor  - ???????????????????????????").toString();
        }
    }
    
    public static class ???????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            ServerConfig.LOG_PACKETS = !ServerConfig.LOG_PACKETS;
            c.getPlayer().dropMessage(0, "[????????????] " + (ServerConfig.LOG_PACKETS ? "??????" : "??????"));
            System.out.println("[logindoor] " + (ServerConfig.LOG_PACKETS ? "??????" : "??????"));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!????????????  - ??????????????????").toString();
        }
    }
    
    public static class ?????????????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            ServerConfig.CHRLOG_PACKETS = !ServerConfig.CHRLOG_PACKETS;
            c.getPlayer().dropMessage(0, "[??????????????????] " + (ServerConfig.CHRLOG_PACKETS ? "??????" : "??????"));
            System.out.println("[??????????????????] " + (ServerConfig.CHRLOG_PACKETS ? "??????" : "??????"));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!??????????????????  - ????????????????????????").toString();
        }
    }
    
    public static class ??????????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            WorldConstants.WUYANCHI = !WorldConstants.WUYANCHI;
            c.getPlayer().dropMessage(0, "[???????????????] " + (WorldConstants.WUYANCHI ? "??????" : "??????"));
            System.out.println("[???????????????] " + (WorldConstants.WUYANCHI ? "??????" : "??????"));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!???????????????  - ?????????????????????").toString();
        }
    }
    
    public static class ???????????????????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            WorldConstants.JZSD = !WorldConstants.JZSD;
            c.getPlayer().dropMessage(0, "[????????????????????????] " + (WorldConstants.JZSD ? "??????" : "??????"));
            System.out.println("[????????????????????????] " + (WorldConstants.JZSD ? "??????" : "??????"));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!????????????????????????  - ????????????????????????").toString();
        }
    }
    
    public static class ???????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            WorldConstants.LieDetector = !WorldConstants.LieDetector;
            c.getPlayer().dropMessage(0, "[????????????] " + (WorldConstants.LieDetector ? "??????" : "??????"));
            System.out.println("[????????????] " + (WorldConstants.LieDetector ? "??????" : "??????"));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!????????????  - ??????????????????").toString();
        }
    }
    
    public static class ?????????????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            WorldConstants.DropItem = !WorldConstants.DropItem;
            c.getPlayer().dropMessage(0, "[??????????????????] " + (WorldConstants.DropItem ? "??????" : "??????"));
            System.out.println("[??????????????????] " + (WorldConstants.DropItem ? "??????" : "??????"));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!??????????????????  - ??????????????????").toString();
        }
    }
    
    public static class ???????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            final String name = splitted[1];
            final int ch = Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage("?????????????????????");
                return true;
            }
            c.getPlayer().dropMessage("????????? " + ch);
            for (final ChannelServer cs : ChannelServer.getAllInstances()) {
                final MapleCharacter character = cs.getPlayerStorage().getCharacterByName(name);
                if (character != null) {
                    character.startLieDetector(false);
                    c.getPlayer().dropMessage(" ?????? " + name + "???????????????");
                }
                else {
                    c.getPlayer().dropMessage("???????????????????????????");
                }
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!???????????? <??????> - ????????????").toString();
        }
    }
    
    public static class LevelUp extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().levelUp();
            }
            else {
                int up = 0;
                try {
                    up = Integer.parseInt(splitted[1]);
                }
                catch (Exception ex) {}
                for (int i = 0; i < up; ++i) {
                    c.getPlayer().levelUp();
                }
            }
            c.getPlayer().setExp(0);
            c.getPlayer().updateSingleStat(MapleStat.EXP, 0);
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!levelup - ????????????").toString();
        }
    }
    
    public static class ?????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().levelUp();
            }
            else {
                int up = 0;
                try {
                    up = Integer.parseInt(splitted[1]);
                }
                catch (Exception ex) {}
                for (int i = 0; i < up; ++i) {
                    c.getPlayer().levelUp();
                }
            }
            c.getPlayer().setExp(0);
            c.getPlayer().updateSingleStat(MapleStat.EXP, 0);
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!?????? - ????????????").toString();
        }
    }
    
    public static class FakeRelog extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            final MapleCharacter player = c.getPlayer();
            c.sendPacket(MaplePacketCreator.getCharInfo(player));
            player.getMap().removePlayer(player);
            player.getMap().addPlayer(player);
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!fakerelog - ??????????????????").toString();
        }
    }
    
    public static class SpawnReactor extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            final MapleReactorStats reactorSt = MapleReactorFactory.getReactor(Integer.parseInt(splitted[1]));
            final MapleReactor reactor = new MapleReactor(reactorSt, Integer.parseInt(splitted[1]));
            reactor.setDelay(-1);
            reactor.setPosition(c.getPlayer().getPosition());
            c.getPlayer().getMap().spawnReactor(reactor);
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!spawnreactor - ??????Reactor").toString();
        }
    }
    
    public static class HReactor extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            c.getPlayer().getMap().getReactorByOid(Integer.parseInt(splitted[1])).hitReactor(c);
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!hitreactor - ??????Reactor").toString();
        }
    }
    
    public static class DestroyReactor extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            final MapleMap map = c.getPlayer().getMap();
            final List<MapleMapObject> reactors = map.getMapObjectsInRange(c.getPlayer().getPosition(), Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.REACTOR));
            if (splitted[1].equals("all")) {
                for (final MapleMapObject reactorL : reactors) {
                    final MapleReactor reactor2l = (MapleReactor)reactorL;
                    c.getPlayer().getMap().destroyReactor(reactor2l.getObjectId());
                }
            }
            else {
                c.getPlayer().getMap().destroyReactor(Integer.parseInt(splitted[1]));
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!drstroyreactor - ??????Reactor").toString();
        }
    }
    
    public static class ResetReactors extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().getMap().resetReactors();
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!resetreactors - ????????????????????????Reactor").toString();
        }
    }
    
    public static class SetReactor extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            c.getPlayer().getMap().setReactorState(Byte.parseByte(splitted[1]));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!hitreactor - ??????Reactor").toString();
        }
    }
    
    public static class ResetQuest extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            MapleQuest.getInstance(Integer.parseInt(splitted[1])).forfeit(c.getPlayer());
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!resetquest <??????ID> - ????????????").toString();
        }
    }
    
    public static class StartQuest extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            MapleQuest.getInstance(Integer.parseInt(splitted[1])).start(c.getPlayer(), Integer.parseInt(splitted[2]));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!startquest <??????ID> - ????????????").toString();
        }
    }
    
    public static class CompleteQuest extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            MapleQuest.getInstance(Integer.parseInt(splitted[1])).complete(c.getPlayer(), Integer.parseInt(splitted[2]), Integer.parseInt(splitted[3]));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!completequest <??????ID> - ????????????").toString();
        }
    }
    
    public static class FStartQuest extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            MapleQuest.getInstance(Integer.parseInt(splitted[1])).forceStart(c.getPlayer(), Integer.parseInt(splitted[2]), (splitted.length >= 4) ? splitted[3] : null);
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!fstartquest <??????ID> - ??????????????????").toString();
        }
    }
    
    public static class FCompleteQuest extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            MapleQuest.getInstance(Integer.parseInt(splitted[1])).forceComplete(c.getPlayer(), Integer.parseInt(splitted[2]));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!fcompletequest <??????ID> - ??????????????????").toString();
        }
    }
    
    public static class FStartOther extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            MapleQuest.getInstance(Integer.parseInt(splitted[2])).forceStart(c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]), Integer.parseInt(splitted[3]), (splitted.length >= 4) ? splitted[4] : null);
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!fstartother - ????????????").toString();
        }
    }
    
    public static class FCompleteOther extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            MapleQuest.getInstance(Integer.parseInt(splitted[2])).forceComplete(c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]), Integer.parseInt(splitted[3]));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!fcompleteother - ????????????").toString();
        }
    }
    
    public static class log extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            boolean next = false;
            boolean Action = false;
            String LogType = null;
            final String[] Log = { "??????", "??????", "??????", "??????", "????????????" };
            final StringBuilder show_log = new StringBuilder();
            for (final String s : Log) {
                show_log.append(s);
                show_log.append(" / ");
            }
            if (splitted.length < 3) {
                c.getPlayer().dropMessage("??????Log??????: " + show_log.toString());
                return false;
            }
            if (!splitted[1].contains((CharSequence)"???") && !splitted[1].contains((CharSequence)"???")) {
                return false;
            }
            if (splitted[1].contains((CharSequence)"???") && splitted[1].contains((CharSequence)"???")) {
                c.getPlayer().dropMessage("?????????????????????????????????????????????????");
                return true;
            }
            for (int i = 0; i < Log.length; ++i) {
                if (splitted[2].contains((CharSequence)Log[i])) {
                    next = true;
                    LogType = Log[i];
                    break;
                }
            }
            Action = splitted[1].contains((CharSequence)"???");
            if (!next) {
                c.getPlayer().dropMessage("??????Log??????: " + show_log.toString());
                return true;
            }
            final String s2 = LogType;
            switch (s2) {
                case "??????": {
                    ServerConfig.LOG_MEGA = Action;
                    break;
                }
                case "??????": {
                    ServerConfig.LOG_DAMAGE = Action;
                    break;
                }
                case "??????": {
                    ServerConfig.LOG_CHAT = Action;
                    break;
                }
                case "??????": {
                    ServerConfig.LOG_CSBUY = Action;
                    break;
                }
                case "????????????": {
                    ServerConfig.LOG_MRECHANT = Action;
                    break;
                }
            }
            final String msg = "[GM ??????] ?????????[" + c.getPlayer().getName() + "] " + splitted[1] + "???" + LogType + "???Log";
            Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, msg));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!log ???/??? Log????????????").toString();
        }
    }
    
    public static class RemoveItem extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                return false;
            }
            final String name = splitted[1];
            final int id = Integer.parseInt(splitted[2]);
            final int ch = Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "??????????????????");
                return true;
            }
            final MapleCharacter chr = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (chr == null) {
                c.getPlayer().dropMessage(6, "?????????????????????");
            }
            else {
                chr.removeAll(id, false, true);
                c.getPlayer().dropMessage(6, "??????ID??? " + id + " ?????????????????? " + name + " ??????????????????");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("*RemoveItem <????????????> <??????ID> - ???????????????????????????").toString();
        }
    }
    
    public static class RemoveItemOff extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                return false;
            }
            try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
                final int item = Integer.parseInt(splitted[1]);
                final String name = splitted[2];
                int id = 0;
                int quantity = 0;
                final List<Long> inventoryitemid = new LinkedList<Long>();
                final boolean isEquip = GameConstants.isEquip(item);
                if (MapleCharacter.getCharacterByName(name) == null) {
                    c.getPlayer().dropMessage(5, "???????????????????????????");
                    return true;
                }
                id = MapleCharacter.getCharacterByName(name).getId();
                PreparedStatement ps = con.prepareStatement("select inventoryitemid, quantity from inventoryitems WHERE itemid = ? and characterid = ?");
                ps.setInt(1, item);
                ps.setInt(2, id);
                try (final ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        if (isEquip) {
                            final long Equipid = rs.getLong("inventoryitemid");
                            if (Equipid != 0L) {
                                inventoryitemid.add(Long.valueOf(Equipid));
                            }
                            ++quantity;
                        }
                        else {
                            quantity += rs.getInt("quantity");
                        }
                    }
                }
                if (quantity == 0) {
                    c.getPlayer().dropMessage(5, "??????[" + name + "]????????????[" + item + "]????????????");
                    return true;
                }
                if (isEquip) {
                    final StringBuilder Sql = new StringBuilder();
                    Sql.append("Delete from inventoryequipment WHERE inventoryitemid = ");
                    for (int i = 0; i < inventoryitemid.size(); ++i) {
                        Sql.append(inventoryitemid.get(i));
                        if (i < inventoryitemid.size() - 1) {
                            Sql.append(" OR inventoryitemid = ");
                        }
                    }
                    ps = con.prepareStatement(Sql.toString());
                    ps.executeUpdate();
                }
                ps = con.prepareStatement("Delete from inventoryitems WHERE itemid = ? and characterid = ?");
                ps.setInt(1, item);
                ps.setInt(2, id);
                ps.executeUpdate();
                ps.close();
                c.getPlayer().dropMessage(6, "????????? " + name + " ???????????????????????? ID[" + item + "] ??????x" + quantity);
                return true;
            }
            catch (SQLException e) {
                FileoutputUtil.outError("logs/???????????????.txt", (Throwable)e);
                return true;
            }
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!RemoveItemOff <??????ID> <????????????> - ???????????????????????????").toString();
        }
    }
    
    public static class ?????????????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            ChannelServer ch = c.getChannelServer();
            if (splitted.length > 1) {
                ch = ChannelServer.getInstance(Integer.parseInt(splitted[1]));
            }
            final int[] maps = { 240060000, 240060100, 240060200 };
            for (int i = 0; i < maps.length; ++i) {
                final int mapid = maps[i];
                ch.getMapFactory().destroyMap(mapid, true);
                ch.getMapFactory().HealMap(mapid);
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!?????????????????? <??????> - ??????????????????").toString();
        }
    }
    
    public static class ???????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            int mid = 0;
            try {
                mid = Integer.parseInt(splitted[1]);
            }
            catch (Exception ex) {}
            int num = Math.min(CommandProcessorUtil.getOptionalIntArg(splitted, 2, 1), 500);
            if (num > 1000) {
                num = 1000;
            }
            final Long hp = CommandProcessorUtil.getNamedLongArg(splitted, 1, "hp");
            final Integer mp = CommandProcessorUtil.getNamedIntArg(splitted, 1, "mp");
            final Integer exp = CommandProcessorUtil.getNamedIntArg(splitted, 1, "exp");
            final Double php = CommandProcessorUtil.getNamedDoubleArg(splitted, 1, "php");
            final Double pmp = CommandProcessorUtil.getNamedDoubleArg(splitted, 1, "pmp");
            final Double pexp = CommandProcessorUtil.getNamedDoubleArg(splitted, 1, "pexp");
            MapleMonster onemob;
            try {
                onemob = MapleLifeFactory.getMonster(mid);
            }
            catch (RuntimeException e) {
                c.getPlayer().dropMessage(5, "??????: " + e.getMessage());
                return true;
            }
            long newhp;
            if (hp != null) {
                newhp = (long)hp;
            }
            else if (php != null) {
                newhp = (long)((double)onemob.getMobMaxHp() * ((double)php / 100.0));
            }
            else {
                newhp = onemob.getMobMaxHp();
            }
            if (mp != null) {
                final int newmp = (int)mp;
            }
            else if (pmp != null) {
                final int newmp = (int)((double)onemob.getMobMaxMp() * ((double)pmp / 100.0));
            }
            else {
                final int newmp = onemob.getMobMaxMp();
            }
            int newexp;
            if (exp != null) {
                newexp = (int)exp;
            }
            else if (pexp != null) {
                newexp = (int)((double)onemob.getMobExp() * ((double)pexp / 100.0));
            }
            else {
                newexp = onemob.getMobExp();
            }
            if (newhp < 1L) {
                newhp = 1L;
            }
            final OverrideMonsterStats overrideStats = new OverrideMonsterStats(newhp, onemob.getMobMaxMp(), newexp, false);
            for (int i = 0; i < num; ++i) {
                final MapleMonster mob = MapleLifeFactory.getMonster(mid);
                mob.setHp(newhp);
                mob.setOverrideStats(overrideStats);
                c.getPlayer().getMap().spawnMonsterOnGroundBelow(mob, c.getPlayer().getPosition());
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!???????????? <??????ID> - ????????????").toString();
        }
    }
    
    public static class ??????????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().dropMessage("????????????????????????....");
            OnlyID.getInstance().StartCheckings();
            c.getPlayer().dropMessage("????????????????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!??????????????? - ?????????????????????").toString();
        }
    }
    
    public static class ??????????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            final List<Triple<Integer, Long, Long>> OnlyIDList = OnlyID.getData();
            if (OnlyIDList.isEmpty()) {
                c.getPlayer().dropMessage("???????????????????????????????????????????????? !??????????????? ???????????????");
                return true;
            }
            try {
                final ListIterator<Triple<Integer, Long, Long>> OnlyId = OnlyIDList.listIterator();
                while (OnlyId.hasNext()) {
                    final Triple<Integer, Long, Long> Only = (Triple<Integer, Long, Long>)OnlyId.next();
                    final int chr = (int)Integer.valueOf(Only.getLeft());
                    final long invetoryitemid = (long)Long.valueOf(Only.getMid());
                    final long equiponlyid = (long)Long.valueOf(Only.getRight());
                    final int ch = Find.findChannel(chr);
                    if (ch < 0 && ch != -10) {
                        this.HandleOffline(c, chr, invetoryitemid, equiponlyid);
                    }
                    else {
                        MapleCharacter chrs = null;
                        if (ch == -10) {
                            chrs = CashShopServer.getPlayerStorage().getCharacterById(chr);
                        }
                        else {
                            chrs = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterById(chr);
                        }
                        if (chrs == null) {
                            break;
                        }
                        MapleInventoryManipulator.removeAllByEquipOnlyId(chrs.getClient(), equiponlyid);
                    }
                }
                OnlyID.clearData();
            }
            catch (Exception ex) {
                c.getPlayer().dropMessage("???????????? " + ex.toString());
            }
            return true;
        }
        
        public void HandleOffline(final MapleClient c, final int chr, final long inventoryitemid, final long equiponlyid) {
            try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
                String itemname = "null";
                try (final PreparedStatement ps = con.prepareStatement("select itemid from inventoryitems WHERE inventoryitemid = ?")) {
                    ps.setLong(1, inventoryitemid);
                    try (final ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            final int itemid = rs.getInt("itemid");
                            itemname = MapleItemInformationProvider.getInstance().getName(itemid);
                        }
                        else {
                            c.getPlayer().dropMessage("????????????: ?????????????????????????????????");
                        }
                    }
                }
                try (final PreparedStatement ps = con.prepareStatement("Delete from inventoryequipment WHERE inventoryitemid = " + inventoryitemid)) {
                    ps.executeUpdate();
                }
                try (final PreparedStatement ps = con.prepareStatement("Delete from inventoryitems WHERE inventoryitemid = ?")) {
                    ps.setLong(1, inventoryitemid);
                    ps.executeUpdate();
                }
                final String msgtext = "??????ID: " + chr + " ????????????????????????????????????[" + itemname + "]?????????????????????";
                Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM??????] " + msgtext));
                FileoutputUtil.logToFile("Hack/????????????_?????????.txt", msgtext + " ????????????ID: " + equiponlyid);
            }
            catch (Exception ex) {
                FileoutputUtil.outError("logs/???????????????.txt", (Throwable)ex);
            }
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!??????????????? - ??????????????????").toString();
        }
    }
    
    public static class ???????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.removeClickedNPC();
            NPCScriptManager.getInstance().start(c, 9010000, "AdvancedSearch");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!???????????? - ????????????????????????").toString();
        }
    }
    
    public static class ???????????? extends ExpRate
    {
    }
    
    public static class ExpRate extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length > 1) {
                final int rate = Integer.parseInt(splitted[1]);
                if (splitted.length > 2 && splitted[2].equalsIgnoreCase("all")) {
                    for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                        cserv.setExpRate(rate);
                    }
                }
                else {
                    c.getChannelServer().setExpRate(rate);
                }
                c.getPlayer().dropMessage(6, "Exprate has been changed to " + rate + "x");
                return true;
            }
            return false;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!exprate <??????> - ??????????????????").toString();
        }
    }
    
    public static class ???????????? extends DropRate
    {
    }
    
    public static class DropRate extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length > 1) {
                final int rate = Integer.parseInt(splitted[1]);
                if (splitted.length > 2 && splitted[2].equalsIgnoreCase("all")) {
                    for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                        cserv.setDropRate(rate);
                    }
                }
                else {
                    c.getChannelServer().setDropRate(rate);
                }
                c.getPlayer().dropMessage(6, "Drop Rate has been changed to " + rate + "x");
                return true;
            }
            return false;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!droprate <??????> - ??????????????????").toString();
        }
    }
    
    public static class ???????????? extends MesoRate
    {
    }
    
    public static class MesoRate extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length > 1) {
                final int rate = Integer.parseInt(splitted[1]);
                if (splitted.length > 2 && splitted[2].equalsIgnoreCase("all")) {
                    for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                        cserv.setMesoRate(rate);
                    }
                }
                else {
                    c.getChannelServer().setMesoRate(rate);
                }
                c.getPlayer().dropMessage(6, "Meso Rate has been changed to " + rate + "x");
                return true;
            }
            return false;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!mesorate <??????> - ??????????????????").toString();
        }
    }
    
    public static class KillAll extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            MapleMap map = c.getPlayer().getMap();
            double range = Double.POSITIVE_INFINITY;
            boolean withdrop = false;
            if (splitted.length > 1) {
                final int mapid = Integer.parseInt(splitted[1]);
                int irange = 9999;
                if (splitted.length <= 2) {
                    range = (double)(irange * irange);
                }
                else {
                    map = c.getChannelServer().getMapFactory().getMap(Integer.parseInt(splitted[1]));
                    irange = Integer.parseInt(splitted[2]);
                    range = (double)(irange * irange);
                }
                if (splitted.length >= 3) {
                    withdrop = splitted[3].equalsIgnoreCase("true");
                }
            }
            if (map == null) {
                c.getPlayer().dropMessage("??????[" + splitted[2] + "] ????????????");
                return true;
            }
            final List<MapleMapObject> monsters = map.getMapObjectsInRange(c.getPlayer().getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER));
            for (final MapleMapObject monstermo : map.getMapObjectsInRange(c.getPlayer().getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER))) {
                final MapleMonster mob = (MapleMonster)monstermo;
                map.killMonster(mob, c.getPlayer(), withdrop, false, (byte)1);
            }
            c.getPlayer().dropMessage("??????????????? " + monsters.size() + " ??????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!killall [range] [mapid] - ??????????????????").toString();
        }
    }
    
    public static class ???????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            MapleMap map = c.getPlayer().getMap();
            double range = Double.POSITIVE_INFINITY;
            boolean withdrop = false;
            if (splitted.length > 1) {
                final int mapid = Integer.parseInt(splitted[1]);
                int irange = 9999;
                if (splitted.length <= 2) {
                    range = (double)(irange * irange);
                }
                else {
                    map = c.getChannelServer().getMapFactory().getMap(Integer.parseInt(splitted[1]));
                    irange = Integer.parseInt(splitted[2]);
                    range = (double)(irange * irange);
                }
                if (splitted.length >= 3) {
                    withdrop = splitted[3].equalsIgnoreCase("true");
                }
            }
            if (map == null) {
                c.getPlayer().dropMessage("??????[" + splitted[2] + "] ????????????");
                return true;
            }
            final List<MapleMapObject> monsters = map.getMapObjectsInRange(c.getPlayer().getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER));
            for (final MapleMapObject monstermo : map.getMapObjectsInRange(c.getPlayer().getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER))) {
                final MapleMonster mob = (MapleMonster)monstermo;
                map.killMonster(mob, c.getPlayer(), withdrop, false, (byte)1);
            }
            c.getPlayer().dropMessage("??????????????? " + monsters.size() + " ??????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!???????????? [range] [mapid] - ??????????????????").toString();
        }
    }
    
    public static class KillMonster extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            final MapleMap map = c.getPlayer().getMap();
            final double range = Double.POSITIVE_INFINITY;
            for (final MapleMapObject monstermo : map.getMapObjectsInRange(c.getPlayer().getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER))) {
                final MapleMonster mob = (MapleMonster)monstermo;
                if (mob.getId() == Integer.parseInt(splitted[1])) {
                    mob.damage(c.getPlayer(), mob.getHp(), false);
                }
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!killmonster <mobid> - ???????????????????????????").toString();
        }
    }
    
    public static class ?????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            final MapleMap map = c.getPlayer().getMap();
            final double range = Double.POSITIVE_INFINITY;
            for (final MapleMapObject monstermo : map.getMapObjectsInRange(c.getPlayer().getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER))) {
                final MapleMonster mob = (MapleMonster)monstermo;
                if (mob.getId() == Integer.parseInt(splitted[1])) {
                    mob.damage(c.getPlayer(), mob.getHp(), false);
                }
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!?????? <mobid> - ???????????????????????????").toString();
        }
    }
    
    public static class ??????????????????IP extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            final String name = splitted[1];
            final MapleCharacter victim = MapleCharacter.getCharacterByName(name);
            final int ch = Find.findChannel(name);
            if (victim != null) {
                if (ch <= 0) {
                    c.getPlayer().dropMessage(5, "????????????????????????");
                }
                else {
                    victim.setChrDangerousIp(victim.getClient().getSession().remoteAddress().toString());
                }
            }
            else {
                c.getPlayer().dropMessage(5, "??????????????????.");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!??????????????????IP <????????????> - ????????????IP").toString();
        }
    }
    
    public static class ???????????? extends discounied
    {
    }
    
    public static class discounied extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            LoginServer.setDiscounied(!LoginServer.getDiscounied());
            c.getPlayer().dropMessage(0, "[????????????] " + (LoginServer.getDiscounied() ? "??????" : "??????"));
            System.out.println("[????????????] " + (LoginServer.getDiscounied() ? "??????" : "??????"));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!????????????  - ??????????????????").toString();
        }
    }
    
    public static class ???????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().dropMessage(5, "??????????????????: " + c.getPlayer().getChrMeso());
            c.getPlayer().dropMessage(5, "??????????????????: " + c.getPlayer().getStorageMeso());
            c.getPlayer().dropMessage(5, "??????????????????: " + c.getPlayer().getHiredMerchMeso());
            final long meso = c.getPlayer().getHiredMerchMeso() + c.getPlayer().getStorageMeso() + c.getPlayer().getChrMeso();
            c.getPlayer().dropMessage(5, "?????????????????????: " + meso);
            FileoutputUtil.logToFile("logs/Data/????????????.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " ??????????????????: " + c.getPlayer().getChrMeso() + " ??????????????????: " + c.getPlayer().getStorageMeso() + " ??????????????????: " + c.getPlayer().getHiredMerchMeso() + " ?????????????????????: " + meso);
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("*???????????? - ??????????????????????????????").toString();
        }
    }
    
    public static class UpdateMap extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            final MapleCharacter player = c.getPlayer();
            if (splitted.length < 2) {
                return false;
            }
            final boolean custMap = splitted.length >= 2;
            final int mapid = custMap ? Integer.parseInt(splitted[1]) : player.getMapId();
            final MapleMap map = custMap ? player.getClient().getChannelServer().getMapFactory().getMap(mapid) : player.getMap();
            if (player.getClient().getChannelServer().getMapFactory().destroyMap(mapid)) {
                final MapleMap newMap = player.getClient().getChannelServer().getMapFactory().getMap(mapid);
                final MaplePortal newPor = newMap.getPortal(0);
                final LinkedHashSet<MapleCharacter> mcs = new LinkedHashSet<MapleCharacter>((Collection<? extends MapleCharacter>)map.getCharacters());
            Label_0139:
                for (final MapleCharacter m : mcs) {
                	 for (int x = 0; x < 5; ) {
                         try {
                           m.changeMap(newMap, newPor);
                         } catch (Throwable throwable) {}
                         continue Label_0139;
                       } 
                    player.dropMessage("???????????? " + m.getName() + " ??????????????????. ????????????...");
                }
                player.dropMessage("??????????????????.");
                return true;
            }
            player.dropMessage("??????????????????!");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!UpdateMap <mapid> - ??????????????????").toString();
        }
    }
    
    public static class MobDrop extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            NPCScriptManager.getInstance().start(c, 9010000, "????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!MobDrop").toString();
        }
    }
    
    public static class ???????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            NPCScriptManager.getInstance().start(c, 9010000, "????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!????????????").toString();
        }
    }
    
    public static class ?????????????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            final String name = splitted[1];
            final MapleCharacter victim = MapleCharacter.getCharacterByName(name);
            final int ch = Find.findChannel(name);
            if (victim != null) {
                if (ch <= 0) {
                    victim.setChrDangerousAcc(victim.getClient().getAccountName());
                }
                else {
                    victim.setChrDangerousAcc(victim.getClient().getAccountName());
                }
            }
            else {
                c.getPlayer().dropMessage(5, "??????????????????.");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!?????????????????? <????????????> - ??????????????????").toString();
        }
    }
    
    public static class ???????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getSession().writeAndFlush(LoadPacket.getPacket());
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!???????????? ??????????????????").toString();
        }
    }
    
    public static class ?????????????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().getClient().getChannelServer().getEventSM().getEventManager("shaoling").setProperty("state", "0");
            c.getPlayer().dropMessage(6, "????????????????????????");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!?????????????????? - ??????????????????").toString();
        }
    }
    
    public static class ???????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length > 1) {
                final StringBuilder sb = new StringBuilder();
                sb.append(StringUtil.joinStringFrom(splitted, 1));
                LoginServer.setTouDing(sb.toString());
                c.getPlayer().dropMessage(0, "[????????????] " + sb.toString());
                System.out.println("[????????????] " + sb.toString());
                return true;
            }
            return false;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("*????????????  - ????????????").toString();
        }
    }
    
    public static class ????????????????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length > 1) {
                final int rate = Integer.parseInt(splitted[1]);
                LoginServer.setRSGS(rate);
                c.getPlayer().dropMessage(6, "???????????? " + rate + "%");
                return true;
            }
            return false;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("*?????????????????????  - ?????????????????????").toString();
        }
    }
    
    public static class pvp extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            ServerConfig.pvp = !ServerConfig.pvp;
            c.getPlayer().dropMessage(0, "[pvp] " + (ServerConfig.pvp ? "??????" : "??????"));
            System.out.println("[pvp] " + (ServerConfig.pvp ? "??????" : "??????"));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("*pvp  - pvp??????").toString();
        }
    }
    
    public static class ?????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().levelUp();
            }
            else {
                int up = 0;
                try {
                    up = Integer.parseInt(splitted[1]);
                }
                catch (Exception ex) {}
                for (int i = 0; i < up; ++i) {
                    c.getPlayer().levelUp();
                }
            }
            c.getPlayer().setExp(0);
            c.getPlayer().updateSingleStat(MapleStat.EXP, 0);
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!?????? - ????????????").toString();
        }
    }
    
    public static class ?????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (Game.?????? == "???") {
                return true;
            }
            final MapleCharacter player = c.getPlayer();
            if (player.isInvincible()) {
                player.setInvincible(false);
                player.dropMessage(6, "??????????????????");
            }
            else {
                player.setInvincible(true);
                player.dropMessage(6, "??????????????????.");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!??????  - ????????????").toString();
        }
    }
    
    public static class ??????????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            final merchant_main chat = merchant_main.getInstance();
            if (chat.isClose()) {
                c.getPlayer().dropMessage(5, "?????????????????????,?????????????????????[!???????????????]");
            }
            else {
                chat.save_data();
                c.getPlayer().dropMessage(5, "???????????????????????????,?????????????????????,?????????????????????[!???????????????]");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!???????????????  - ?????????????????????").toString();
        }
    }
    
    public static class ??????????????? extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            final merchant_main chat = merchant_main.getInstance();
            if (chat.isClose()) {
                chat.load_data();
                chat.setClose(false);
                c.getPlayer().dropMessage(5, "??????????????????????????????");
            }
            else {
                c.getPlayer().dropMessage(5, "??????????????????,????????????[!???????????????]");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!???????????????  - ?????????????????????").toString();
        }
    }
}
