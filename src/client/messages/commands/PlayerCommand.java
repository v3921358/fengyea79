package client.messages.commands;

import server.gashapon.GashaponFactory;
import client.inventory.MapleInventory;
import server.MapleInventoryManipulator;
import tools.FileoutputUtil;
import handling.world.World.Broadcast;
import constants.PiPiConfig;
import tools.StringUtil;
import java.util.Iterator;
import server.life.MapleMonster;
import java.util.Arrays;
import server.maps.MapleMapObjectType;
import java.util.Calendar;
import tools.FilePrinter;
import tools.MaplePacketCreator;
import client.MapleStat;
import server.maps.MapleMap;
import server.maps.SavedLocationType;
import client.inventory.IItem;
import server.maps.MapleMapObject;
import client.inventory.Item;
import client.inventory.Equip;
import client.inventory.MapleInventoryType;
import server.MapleItemInformationProvider;
import client.messages.CommandProcessorUtil;
import client.MapleCharacter;
import handling.channel.ChannelServer;
import handling.world.World.Find;
import constants.GameConstants;
import scripting.NPCScriptManager;
import client.MapleClient;
import constants.ServerConstants.PlayerGMRank;

public class PlayerCommand
{
    public static PlayerGMRank getPlayerLevelRequired() {
        return PlayerGMRank.普通玩家;
    }
    
    public static class 帮助 extends help
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            NPCScriptManager.getInstance().start(c, 9330079, "玩家指令查询");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@帮助 - 帮助").toString();
        }
    }
    
    public static class help extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            NPCScriptManager.getInstance().start(c, 9330079, "玩家指令查询");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@help - 帮助").toString();
        }
    }
    
    public abstract static class OpenNPCCommand extends CommandExecute
    {
        protected int npc;
        private static final int[] npcs;
        
        public OpenNPCCommand() {
            this.npc = -1;
        }
        
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (this.npc != 1 && c.getPlayer().getMapId() != 910000000) {
                for (final int i : GameConstants.blockedMaps) {
                    if (c.getPlayer().getMapId() == i) {
                        c.getPlayer().dropMessage(1, "你不能在这裡使用指令.");
                        return true;
                    }
                }
                if (this.npc != 2 && c.getPlayer().getLevel() < 10) {
                    c.getPlayer().dropMessage(1, "你的等级必须是10等.");
                    return true;
                }
                if (c.getPlayer().getMap().getSquadByMap() != null || c.getPlayer().getEventInstance() != null || c.getPlayer().getMap().getEMByMap() != null || c.getPlayer().getMapId() >= 990000000) {
                    c.getPlayer().dropMessage(1, "你不能在这裡使用指令.");
                    return true;
                }
                if ((c.getPlayer().getMapId() >= 680000210 && c.getPlayer().getMapId() <= 680000502) || (c.getPlayer().getMapId() / 1000 == 980000 && c.getPlayer().getMapId() != 980000000) || c.getPlayer().getMapId() / 100 == 1030008 || c.getPlayer().getMapId() / 100 == 922010 || c.getPlayer().getMapId() / 10 == 13003000) {
                    c.getPlayer().dropMessage(1, "你不能在这裡使用指令.");
                    return true;
                }
            }
            NPCScriptManager.getInstance().start(c, OpenNPCCommand.npcs[this.npc]);
            return true;
        }
        
        static {
            npcs = new int[] { 9010017, 9000001, 9000058, 9330082, 9209002 };
        }
    }
    
    public static class 丢装 extends DropCash
    {
        @Override
        public String getMessage() {
            return new StringBuilder().append("@丢装 - 呼叫清除现金道具npc").toString();
        }
    }
    
    public static class DropCash extends OpenNPCCommand
    {
        public DropCash() {
            this.npc = 0;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@dropbash - 呼叫清除现金道具npc").toString();
        }
    }
    
    public static class GoldMoney extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                return false;
            }
            int amount = 0;
            String name = "";
            try {
                amount = Integer.parseInt(splitted[1]);
                name = splitted[2];
            }
            catch (Exception ex) {
                return false;
            }
            final int ch = Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage("该玩家不在线上");
                return true;
            }
            final MapleCharacter player = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (player == null) {
                c.getPlayer().dropMessage("该玩家不在线上");
                return true;
            }
            player.gainmoneyb(amount);
            player.dropMessage("已经收到" + amount + "");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@GoldMoney").toString();
        }
    }
    
    public static class GoldCoupon extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                return false;
            }
            int amount = 0;
            String name = "";
            try {
                amount = Integer.parseInt(splitted[1]);
                name = splitted[2];
            }
            catch (Exception ex) {
                return false;
            }
            final int ch = Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage("该玩家不在线上");
                return true;
            }
            final MapleCharacter player = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (player == null) {
                c.getPlayer().dropMessage("该玩家不在线上");
                return true;
            }
            player.modifyCSPoints(1, amount, true);
            player.dropMessage("已经收到" + amount + "");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@GoldCoupon").toString();
        }
    }
    
    public static class GoldCoin extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            final String name = splitted[1];
            final int gain = Integer.parseInt(splitted[2]);
            final int ch = Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "玩家必须上线");
                return true;
            }
            final MapleCharacter victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (victim == null) {
                c.getPlayer().dropMessage(5, "找不到 '" + name);
            }
            else {
                victim.gainMeso(gain, false);
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@GoldCoin").toString();
        }
    }
    
    public static class GoldThrow extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            int itemId = 0;
            String name = null;
            try {
                itemId = Integer.parseInt(splitted[1]);
                name = splitted[3];
            }
            catch (Exception ex) {}
            final short quantity = (short)CommandProcessorUtil.getOptionalIntArg(splitted, 2, 1);
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            if (GameConstants.isPet(itemId)) {
                c.getPlayer().dropMessage(5, "宠物请到购物商城购买.");
            }
            else if (!ii.itemExists(itemId)) {
                c.getPlayer().dropMessage(5, itemId + " - 物品不存在");
            }
            else {
                IItem toDrop;
                if (GameConstants.getInventoryType(itemId) == MapleInventoryType.EQUIP) {
                    toDrop = ii.randomizeStats((Equip)ii.getEquipById(itemId));
                }
                else {
                    toDrop = new Item(itemId, (short)0, quantity, (byte)0);
                }
                if (name != null) {
                    final int ch = Find.findChannel(name);
                    if (ch > 0) {
                        final MapleCharacter victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
                        if (victim != null) {
                            victim.getMap().spawnItemDrop((MapleMapObject)victim, victim, toDrop, victim.getPosition(), true, true);
                        }
                    }
                    else {
                        c.getPlayer().dropMessage("玩家: [" + name + "] 不在线上唷");
                    }
                }
                else {
                    c.getPlayer().getMap().spawnItemDrop((MapleMapObject)c.getPlayer(), c.getPlayer(), toDrop, c.getPlayer().getPosition(), true, true);
                }
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@GoldThrow").toString();
        }
    }
    
    public static class GoldEquipment extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                return false;
            }
            int itemId = 0;
            final int quantity = 1;
            int Str = 0;
            int Dex = 0;
            int Int = 0;
            int Luk = 0;
            int HP = 0;
            int MP = 0;
            int Watk = 0;
            int Matk = 0;
            int Wdef = 0;
            int Mdef = 0;
            int Scroll = 0;
            int Upg = 0;
            int Acc = 0;
            int Avoid = 0;
            int jump = 0;
            int speed = 0;
            int day = 0;
            try {
                int splitted_count = 1;
                itemId = Integer.parseInt(splitted[splitted_count++]);
                Str = Integer.parseInt(splitted[splitted_count++]);
                Dex = Integer.parseInt(splitted[splitted_count++]);
                Int = Integer.parseInt(splitted[splitted_count++]);
                Luk = Integer.parseInt(splitted[splitted_count++]);
                HP = Integer.parseInt(splitted[splitted_count++]);
                MP = Integer.parseInt(splitted[splitted_count++]);
                Watk = Integer.parseInt(splitted[splitted_count++]);
                Matk = Integer.parseInt(splitted[splitted_count++]);
                Wdef = Integer.parseInt(splitted[splitted_count++]);
                Mdef = Integer.parseInt(splitted[splitted_count++]);
                Upg = Integer.parseInt(splitted[splitted_count++]);
                Acc = Integer.parseInt(splitted[splitted_count++]);
                Avoid = Integer.parseInt(splitted[splitted_count++]);
                speed = Integer.parseInt(splitted[splitted_count++]);
                jump = Integer.parseInt(splitted[splitted_count++]);
                Scroll = Integer.parseInt(splitted[splitted_count++]);
                day = Integer.parseInt(splitted[splitted_count++]);
            }
            catch (Exception ex) {}
            final boolean Str_check = Str != 0;
            final boolean Int_check = Int != 0;
            final boolean Dex_check = Dex != 0;
            final boolean Luk_check = Luk != 0;
            final boolean HP_check = HP != 0;
            final boolean MP_check = MP != 0;
            final boolean WATK_check = Watk != 0;
            final boolean MATK_check = Matk != 0;
            final boolean WDEF_check = Wdef != 0;
            final boolean MDEF_check = Mdef != 0;
            final boolean SCROLL_check = true;
            final boolean UPG_check = Upg != 0;
            final boolean ACC_check = Acc != 0;
            final boolean AVOID_check = Avoid != 0;
            final boolean JUMP_check = jump != 0;
            final boolean SPEED_check = speed != 0;
            final boolean DAY_check = day != 0;
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            if (GameConstants.isPet(itemId)) {
                c.getPlayer().dropMessage(5, "请从商城购买宠物.");
                return true;
            }
            if (!ii.itemExists(itemId)) {
                c.getPlayer().dropMessage(5, itemId + " 不存在");
                return true;
            }
            if (GameConstants.getInventoryType(itemId) == MapleInventoryType.EQUIP) {
                final Equip equip = ii.randomizeStats((Equip)ii.getEquipById(itemId));
                if (Str_check) {
                    equip.setStr((short)Str);
                }
                if (Luk_check) {
                    equip.setLuk((short)Luk);
                }
                if (Dex_check) {
                    equip.setDex((short)Dex);
                }
                if (Int_check) {
                    equip.setInt((short)Int);
                }
                if (HP_check) {
                    equip.setHp((short)HP);
                }
                if (MP_check) {
                    equip.setMp((short)MP);
                }
                if (WATK_check) {
                    equip.setWatk((short)Watk);
                }
                if (MATK_check) {
                    equip.setMatk((short)Matk);
                }
                if (WDEF_check) {
                    equip.setWdef((short)Wdef);
                }
                if (MDEF_check) {
                    equip.setMdef((short)Mdef);
                }
                if (ACC_check) {
                    equip.setAcc((short)Acc);
                }
                if (AVOID_check) {
                    equip.setAvoid((short)Avoid);
                }
                if (SCROLL_check) {
                    equip.setUpgradeSlots((byte)Scroll);
                }
                if (UPG_check) {
                    equip.setLevel((byte)Upg);
                }
                if (JUMP_check) {
                    equip.setJump((short)jump);
                }
                if (SPEED_check) {
                    equip.setSpeed((short)speed);
                }
                if (DAY_check) {
                    equip.setExpiration(System.currentTimeMillis() + (long)(day * 24 * 60 * 60 * 1000));
                }
                c.getPlayer().getMap().spawnItemDrop((MapleMapObject)c.getPlayer(), c.getPlayer(), (IItem)equip, c.getPlayer().getPosition(), true, true);
            }
            else {
                final IItem toDrop = new Item(itemId, (short)0, (short)quantity, (byte)0);
                c.getPlayer().getMap().spawnItemDrop((MapleMapObject)c.getPlayer(), c.getPlayer(), toDrop, c.getPlayer().getPosition(), true, true);
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@GoldEquipment").toString();
        }
    }
    
    public static class event extends OpenNPCCommand
    {
        public event() {
            this.npc = 1;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@event - 呼叫活动npc").toString();
        }
    }
    
    public static class npc extends 万能
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            NPCScriptManager.getInstance().start(c, 9900004, "拍卖功能");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@npc - 呼叫万能npc").toString();
        }
    }
    
    public static class 解卡组队 extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().setParty(null);
            c.getPlayer().dropMessage(1, "解卡组队成功。");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@解卡组队-游戏无法进行组队的时候输入").toString();
        }
    }
    
    public static class 万能 extends OpenNPCCommand
    {
        public 万能() {
            this.npc = 2;
        }
        
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            NPCScriptManager.getInstance().start(c, 9900004, "拍卖功能");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@万能 - 呼叫万能npc").toString();
        }
    }
    
    public static class 关闭身外 extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().dropMessage(6, c.getPlayer().getCloneSize() + "个分身消失了.");
            c.getPlayer().disposeClones();
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@关闭身外 - 摧毁分身").toString();
        }
    }
    
    public static class FM extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            for (final int i : GameConstants.blockedMaps) {
                if (c.getPlayer().getMapId() == i) {
                    c.getPlayer().dropMessage(5, "当前地图无法使用.");
                    return false;
                }
            }
            if (c.getPlayer().getLevel() < 10) {
                c.getPlayer().dropMessage(5, "你的等级不足10级无法使用.");
                return false;
            }
            if (c.getPlayer().hasBlockedInventory(true) || c.getPlayer().getMap().getSquadByMap() != null || c.getPlayer().getEventInstance() != null || c.getPlayer().getMap().getEMByMap() != null || c.getPlayer().getMapId() >= 990000000) {
                c.getPlayer().dropMessage(5, "请稍后再试");
                return false;
            }
            if (c.getPlayer().getMapId() == 180000001) {
                c.getPlayer().dropMessage(5, "该地图无法使用该功能!");
                return false;
            }
            if ((c.getPlayer().getMapId() >= 680000210 && c.getPlayer().getMapId() <= 680000502) || (c.getPlayer().getMapId() / 1000 == 980000 && c.getPlayer().getMapId() != 980000000) || c.getPlayer().getMapId() / 100 == 1030008 || c.getPlayer().getMapId() / 100 == 922010 || c.getPlayer().getMapId() / 10 == 13003000) {
                c.getPlayer().dropMessage(5, "请稍后再试.");
                return false;
            }
            c.getPlayer().saveLocation(SavedLocationType.FREE_MARKET, c.getPlayer().getMap().getReturnMap().getId());
            final MapleMap map = c.getChannelServer().getMapFactory().getMap(910000000);
            c.getPlayer().changeMap(map, map.getPortal(0));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("FM - 回自由").toString();
        }
    }
    
    public static class expfix extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().setExp(0);
            c.getPlayer().updateSingleStat(MapleStat.EXP, c.getPlayer().getExp());
            c.getPlayer().dropMessage(5, "经验修复完成");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@expfix - 经验归零").toString();
        }
    }
    
    public static class TSmega extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().setSmega();
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@TSmega - 开/关闭广播").toString();
        }
    }
    
    public static class Gashponmega extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().setGashponmega();
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@Gashponmega - 开/关闭转蛋广播").toString();
        }
    }
    
    public static class 解卡 extends ea
    {
        @Override
        public String getMessage() {
            return new StringBuilder().append("@解卡 - 解卡").toString();
        }
    }
    
    public static class 查看 extends ea
    {
        @Override
        public String getMessage() {
            return new StringBuilder().append("@查看 - 解卡").toString();
        }
    }
    
    public static class ea extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.removeClickedNPC();
            NPCScriptManager.getInstance().dispose(c);
            c.sendPacket(MaplePacketCreator.enableActions());
            c.sendPacket(MaplePacketCreator.sendHint("解卡完毕..\r\n当前系统时间" + FilePrinter.getLocalDateString() + " 星期" + getDayOfWeek() + "\r\n目前剩余: 点卷 " + c.getPlayer().getCSPoints(1) + "  抵用卷 " + c.getPlayer().getCSPoints(2) + "  \r\n当前延迟 " + c.getPlayer().getClient().getLatency() + " 毫秒", 350, 5));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@ea - 解卡").toString();
        }
        
        public static String getDayOfWeek() {
            final int dayOfWeek = Calendar.getInstance().get(7) - 1;
            String dd = String.valueOf(dayOfWeek);
            switch (dayOfWeek) {
                case 0: {
                    dd = "日";
                    break;
                }
                case 1: {
                    dd = "一";
                    break;
                }
                case 2: {
                    dd = "二";
                    break;
                }
                case 3: {
                    dd = "三";
                    break;
                }
                case 4: {
                    dd = "四";
                    break;
                }
                case 5: {
                    dd = "五";
                    break;
                }
                case 6: {
                    dd = "六";
                    break;
                }
            }
            return dd;
        }
    }
    
    public static class 怪物 extends mob
    {
        @Override
        public String getMessage() {
            return new StringBuilder().append("@怪物 - 查看怪物状态").toString();
        }
    }
    
    public static class mob extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            MapleMonster monster = null;
            for (final MapleMapObject monstermo : c.getPlayer().getMap().getMapObjectsInRange(c.getPlayer().getPosition(), 100000.0, Arrays.asList(MapleMapObjectType.MONSTER))) {
                monster = (MapleMonster)monstermo;
                if (monster.isAlive()) {
                    c.getPlayer().dropMessage(6, "怪物 " + monster.toString());
                }
            }
            if (monster == null) {
                c.getPlayer().dropMessage(6, "找不到地图上的怪物");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@mob - 查看怪物状态").toString();
        }
    }
    
    public static class CGM extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            boolean autoReply = false;
            if (splitted.length < 2) {
                return false;
            }
            final String talk = StringUtil.joinStringFrom(splitted, 1);
            if (c.getPlayer().isGM()) {
                c.getPlayer().dropMessage(6, "因为你自己是GM所以无法使用此指令,可以尝试!cngm <讯息> 来建立GM聊天频道~");
            }
            else if (!c.getPlayer().getCheatTracker().GMSpam(100000, 1)) {
                boolean fake = false;
                boolean showmsg = true;
                if (PiPiConfig.getBlackList().containsKey(c.getAccID())) {
                    fake = true;
                }
                if (talk.contains((CharSequence)"抢") && talk.contains((CharSequence)"图")) {
                    c.getPlayer().dropMessage(1, "抢图自行解决！！");
                    fake = true;
                    showmsg = false;
                }
                else if ((talk.contains((CharSequence)"被") && talk.contains((CharSequence)"骗")) || (talk.contains((CharSequence)"点") && talk.contains((CharSequence)"骗"))) {
                    c.getPlayer().dropMessage(1, "被骗请自行解决");
                    fake = true;
                    showmsg = false;
                }
                else if (talk.contains((CharSequence)"删") && (talk.contains((CharSequence)"角") || talk.contains((CharSequence)"脚")) && talk.contains((CharSequence)"错")) {
                    c.getPlayer().dropMessage(1, "删错角色请自行解决");
                    fake = true;
                    showmsg = false;
                }
                else if (talk.contains((CharSequence)"乱") && talk.contains((CharSequence)"名") && talk.contains((CharSequence)"声")) {
                    c.getPlayer().dropMessage(1, "请自行解决");
                    fake = true;
                    showmsg = false;
                }
                if (talk.toUpperCase().contains((CharSequence)"VIP") && (talk.contains((CharSequence)"领") || talk.contains((CharSequence)"获")) && talk.contains((CharSequence)"取")) {
                    c.getPlayer().dropMessage(1, "VIP将会于储值后一段时间后自行发放，请耐心等待");
                    autoReply = true;
                }
                else if (talk.contains((CharSequence)"贡献") || talk.contains((CharSequence)"666") || ((talk.contains((CharSequence)"取") || talk.contains((CharSequence)"拿") || talk.contains((CharSequence)"发") || talk.contains((CharSequence)"领")) && (talk.contains((CharSequence)"勳") || talk.contains((CharSequence)"徽") || talk.contains((CharSequence)"勋")) && talk.contains((CharSequence)"章"))) {
                    c.getPlayer().dropMessage(1, "勳章请去点拍卖NPC案领取勳章\r\n如尚未被加入清单请耐心等候GM。");
                    autoReply = true;
                }
                else if ((talk.contains((CharSequence)"商人") && talk.contains((CharSequence)"吃")) || (talk.contains((CharSequence)"商店") && talk.contains((CharSequence)"补偿"))) {
                    c.getPlayer().dropMessage(1, "目前精灵商人装备和枫币有机率被吃\r\n如被吃了请务必将当时的情况完整描述给管理员\r\n\r\nPS: 不会补偿任何物品");
                    autoReply = true;
                }
                else if (talk.contains((CharSequence)"档") && talk.contains((CharSequence)"案") && talk.contains((CharSequence)"受") && talk.contains((CharSequence)"损")) {
                    c.getPlayer().dropMessage(1, "档案受损请重新解压缩主程式唷");
                    autoReply = true;
                }
                else if ((talk.contains((CharSequence)"缺") || talk.contains((CharSequence)"少")) && ((talk.contains((CharSequence)"技") && talk.contains((CharSequence)"能") && talk.contains((CharSequence)"点")) || talk.toUpperCase().contains((CharSequence)"SP"))) {
                    c.getPlayer().dropMessage(1, "缺少技能点请重练，没有其他方法了唷");
                    autoReply = true;
                }
                if (showmsg) {
                    c.getPlayer().dropMessage(6, "讯息已经寄送给GM了!");
                }
                if (!fake) {
                    Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[管理员帮帮忙]频道 " + c.getPlayer().getClient().getChannel() + " 玩家 [" + c.getPlayer().getName() + "] (" + c.getPlayer().getId() + "): " + talk + (autoReply ? " -- (系统已自动回复)" : "")));
                }
                FileoutputUtil.logToFile("logs/data/管理员帮帮忙.txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家[" + c.getPlayer().getName() + "] 帐号[" + c.getAccountName() + "]: " + talk + (autoReply ? " -- (系统已自动回复)" : "") + "\r\n");
            }
            else {
                c.getPlayer().dropMessage(6, "为了防止对GM刷屏所以每1分钟只能发一次.");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@cgm - 跟GM回报").toString();
        }
    }
    
    public static class 清除道具 extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 4) {
                return false;
            }
            String Column = "null";
            int start = -1;
            int end = -1;
            try {
                Column = splitted[1];
                start = Integer.parseInt(splitted[2]);
                end = Integer.parseInt(splitted[3]);
            }
            catch (Exception ex) {}
            if (start == -1 || end == -1) {
                c.getPlayer().dropMessage("@清除道具 <装备栏/消耗栏/装饰栏/其他栏/特殊栏> <开始格数> <结束格数>");
                return true;
            }
            if (start < 1) {
                start = 1;
            }
            if (end > 96) {
                end = 96;
            }
            final String s = Column;
            MapleInventoryType type = null;
            switch (s) {
                case "装备栏": {
                    type = MapleInventoryType.EQUIP;
                    break;
                }
                case "消耗栏": {
                    type = MapleInventoryType.USE;
                    break;
                }
                case "装饰栏": {
                    type = MapleInventoryType.SETUP;
                    break;
                }
                case "其他栏": {
                    type = MapleInventoryType.ETC;
                    break;
                }
                case "特殊栏": {
                    type = MapleInventoryType.CASH;
                    break;
                }
                default: {
                    type = null;
                    break;
                }
            }
            if (type == null) {
                c.getPlayer().dropMessage("@清除道具 <装备栏/消耗栏/装饰栏/其他栏/特殊栏> <开始格数> <结束格数>");
                return true;
            }
            final MapleInventory inv = c.getPlayer().getInventory(type);
            for (int i = start; i <= end; ++i) {
                if (inv.getItem((short)i) != null) {
                    MapleInventoryManipulator.removeFromSlot(c, type, (short)i, inv.getItem((short)i).getQuantity(), true);
                }
            }
            FileoutputUtil.logToFile("logs/Data/玩家指令.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 帐号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 使用了指令 " + StringUtil.joinStringFrom(splitted, 0));
            c.getPlayer().dropMessage(6, "您已经清除了第 " + start + " 格到 " + end + "格的" + Column + "道具");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@清除道具 <装备栏/消耗栏/装饰栏/其他栏/特殊栏> <开始格数> <结束格数>").toString();
        }
    }
    
    public static class jk_hm extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().RemoveHired();
            c.getPlayer().dropMessage("卡精灵商人已经解除");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@jk_hm - 卡精灵商人解除").toString();
        }
    }
    
    public static class jcds extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            int gain = c.getPlayer().getMP();
            if (gain <= 0) {
                c.getPlayer().dropMessage("目前没有任何在线点数唷。");
                return true;
            }
            if (splitted.length < 2) {
                c.getPlayer().dropMessage("目前枫叶点数: " + c.getPlayer().getCSPoints(2));
                c.getPlayer().dropMessage("目前在线点数已经累积: " + gain + " 点，若要领取请输入 @jcds true");
            }
            else if ("true".equals(splitted[1])) {
                gain = c.getPlayer().getMP();
                c.getPlayer().modifyCSPoints(2, gain, true);
                c.getPlayer().setMP(0);
                c.getPlayer().saveToDB(false, false);
                c.getPlayer().dropMessage("领取了 " + gain + " 点在线点数, 目前枫叶点数: " + c.getPlayer().getCSPoints(2));
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@jcds - 领取在线点数").toString();
        }
    }
    
    public static class 在线点数 extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            int gain = c.getPlayer().getMP();
            if (gain <= 0) {
                c.getPlayer().dropMessage("目前没有任何在线点数唷。");
                return true;
            }
            if (splitted.length < 2) {
                c.getPlayer().dropMessage("目前枫叶点数: " + c.getPlayer().getCSPoints(2));
                c.getPlayer().dropMessage("目前在线点数已经累积: " + gain + " 点，若要领取请输入 @在线点数 是");
            }
            else if ("是".equals(splitted[1])) {
                gain = c.getPlayer().getMP();
                c.getPlayer().modifyCSPoints(2, gain, true);
                c.getPlayer().setMP(0);
                c.getPlayer().saveToDB(false, false);
                c.getPlayer().dropMessage("领取了 " + gain + " 点在线点数, 目前枫叶点数: " + c.getPlayer().getCSPoints(2));
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@在线点数 - 领取在线点数").toString();
        }
    }
    
    public static class 出来吧皮卡丘 extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            final int id = Integer.parseInt(splitted[1]);
            final int quantity = 1;
            final int mod = Integer.parseInt(splitted[2]);
            final String npcname = GashaponFactory.getInstance().getGashaponByNpcId(mod).getName();
            final IItem item = MapleInventoryManipulator.addbyId_GachaponGM(c, id, (short)quantity);
            Broadcast.broadcastGashponmega(MaplePacketCreator.getGachaponMega(c.getPlayer().getName(), " : x" + quantity + "恭喜玩家 " + c.getPlayer().getName() + " 在" + npcname + "获得！", item, (byte)1, c.getPlayer().getClient().getChannel()));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("你就是傻逼").toString();
        }
    }
    
    public static class 丢弃点装 extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.sendPacket(MaplePacketCreator.enableActions());
            NPCScriptManager.getInstance().start(c, 9010000, "丢弃点装");
            return true;
        }
        
        @Override
        public String getMessage() {
            return "@" + this.getClass().getSimpleName().toLowerCase() + "丢弃点装 [点装在装备栏的位置]";
        }
    }
}
