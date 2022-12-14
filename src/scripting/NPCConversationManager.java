package scripting;

import merchant.merchant_main;
import gui.FengYeDuan;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Calendar;
import server.MaplePortal;
import java.util.LinkedHashSet;
import constants.ServerConfig;
import database.DatabaseConnection;
import handling.channel.handler.InterServerHandler;
import client.inventory.Item;
import server.gashapon.GashaponFactory;
import server.gashapon.Gashapon;
import server.shops.HiredMerchant;
import server.life.MonsterDropEntry;
import client.MapleJob;
import handling.channel.MapleGuildRanking.JobRankingInfo;
import tools.FilePrinter;
import tools.SearchGenerator;
import server.Timer.EventTimer;
import handling.world.World.Family;
import server.Timer.CloneTimer;
import server.StructPotentialItem;
import java.util.Collection;
import java.util.ArrayList;
import handling.world.guild.MapleGuild;
import handling.world.MapleParty;
import handling.world.World.Alliance;
import server.MapleStatEffect;
import client.inventory.ItemFlag;
import java.util.HashMap;
import server.SpeedRunner;
import server.maps.SpeedRunType;
import tools.Pair;
import java.util.EnumMap;
import server.MapleCarnivalChallenge;
import server.MapleCarnivalParty;
import server.maps.AramiaFireWorks;
import server.maps.Event_PyramidSubway;
import server.maps.Event_DojoAgent;
import tools.packet.PlayerShopPacket;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import tools.FileoutputUtil;
import database.DBConPool;
import server.life.MapleMonster;
import server.maps.MapleMapObject;
import java.util.Arrays;
import server.maps.MapleMapObjectType;
import server.life.MapleMonsterInformationProvider;
import client.inventory.Equip;
import handling.channel.MapleGuildRanking;
import handling.world.World.Guild;
import tools.StringUtil;
import server.MapleSquad;
import server.maps.MapleMap;
import handling.channel.ChannelServer;
import handling.world.MaplePartyCharacter;
import client.MapleCharacter;
import client.SkillFactory;
import java.util.Map;
import client.SkillEntry;
import client.ISkill;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.List;
import client.inventory.MapleInventory;
import java.util.LinkedList;
import client.inventory.MapleInventoryType;
import server.quest.MapleQuest;
import client.inventory.IItem;
import handling.world.World.Broadcast;
import constants.GameConstants;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.MapleShopFactory;
import handling.world.World;
import server.Randomizer;
import client.MapleStat;
import tools.MaplePacketCreator;
import javax.script.Invocable;
import client.MapleClient;

public class NPCConversationManager extends AbstractPlayerInteraction
{
    private final MapleClient c;
    private final int npc;
    private final int questid;
    private final int mode;
    protected String script;
    private String getText;
    private final byte type;
    private byte lastMsg;
    public boolean pendingDisposal;
    private final Invocable iv;
    public int p;
    
    public NPCConversationManager(final MapleClient c, final int npc, final int questid, final int mode, final String npcscript, final byte type, final Invocable iv) {
        super(c);
        this.lastMsg = -1;
        this.pendingDisposal = false;
        this.p = 0;
        this.c = c;
        this.npc = npc;
        this.questid = questid;
        this.mode = mode;
        this.type = type;
        this.iv = iv;
        this.script = npcscript;
    }
    
    public Invocable getIv() {
        return this.iv;
    }
    
    public int getMode() {
        return this.mode;
    }
    
    public int getNpc() {
        return this.npc;
    }
    
    public int getQuest() {
        return this.questid;
    }
    
    public String getScript() {
        return this.script;
    }
    
    public byte getType() {
        return this.type;
    }
    
    public void safeDispose() {
        this.pendingDisposal = true;
    }
    
    public void dispose() {
        NPCScriptManager.getInstance().dispose(this.c);
    }
    
    public void askMapSelection(final String sel) {
        if (this.lastMsg > -1) {
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getMapSelection(this.npc, sel));
        this.lastMsg = 13;
    }
    
    public void sendNext(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "00 01", (byte)0));
        this.lastMsg = 0;
    }
    
    public void sendNextS(final String text, final byte type) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimpleS(text, type);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "00 01", type));
        this.lastMsg = 0;
    }
    
    public void sendPrev(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "01 00", (byte)0));
        this.lastMsg = 0;
    }
    
    public void sendPrevS(final String text, final byte type) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimpleS(text, type);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "01 00", type));
        this.lastMsg = 0;
    }
    
    public void sendNextPrev(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "01 01", (byte)0));
        this.lastMsg = 0;
    }
    
    public void PlayerToNpc(final String text) {
        this.sendNextPrevS(text, (byte)3);
    }
    
    public void sendNextPrevS(final String text) {
        this.sendNextPrevS(text, (byte)3);
    }
    
    public void sendNextPrevS(final String text, final byte type) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimpleS(text, type);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "01 01", type));
        this.lastMsg = 0;
    }
    
    public void sendOk(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "00 00", (byte)0));
        this.lastMsg = 0;
    }
    
    public void sendOkS(final String text, final byte type) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimpleS(text, type);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "00 00", type));
        this.lastMsg = 0;
    }
    
    public void sendYesNo(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)1, text, "", (byte)0));
        this.lastMsg = 1;
    }
    
    public void sendYesNoS(final String text, final byte type) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimpleS(text, type);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)1, text, "", type));
        this.lastMsg = 1;
    }
    
    public void sendAcceptDecline(final String text) {
        this.askAcceptDecline(text);
    }
    
    public void sendAcceptDeclineNoESC(final String text) {
        this.askAcceptDeclineNoESC(text);
    }
    
    public void askAcceptDecline(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)11, text, "", (byte)0));
        this.lastMsg = 11;
    }
    
    public void askAcceptDeclineNoESC(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)12, text, "", (byte)0));
        this.lastMsg = 12;
    }
    
    public void askAvatar(final String text, final int... args) {
        if (this.lastMsg > -1) {
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalkStyle(this.npc, text, args));
        this.lastMsg = 7;
    }
    
    public void sendSimple(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (!text.contains((CharSequence)"#L")) {
            this.sendNext(text);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)4, text, "", (byte)0));
        this.lastMsg = 4;
    }
    
    public void sendSimpleS(final String text, final byte type) {
        if (this.lastMsg > -1) {
            return;
        }
        if (!text.contains((CharSequence)"#L")) {
            this.sendNextS(text, type);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)4, text, "", type));
        this.lastMsg = 4;
    }
    
    public void sendStyle(final String text, final int[] styles) {
        if (this.lastMsg > -1) {
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalkStyle(this.npc, text, styles));
        this.lastMsg = 7;
    }
    
    public void sendGetNumber(final String text, final int def, final int min, final int max) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalkNum(this.npc, text, def, min, max));
        this.lastMsg = 3;
    }
    
    public void sendGetText(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalkText(this.npc, text));
        this.lastMsg = 2;
    }
    
    public void setGetText(final String text) {
        this.getText = text;
    }
    
    public String getText() {
        return this.getText;
    }
    
    public void setHair(final int hair) {
        this.getPlayer().setHair(hair);
        this.getPlayer().updateSingleStat(MapleStat.HAIR, hair);
        this.getPlayer().equipChanged();
    }
    
    public void setFace(final int face) {
        this.getPlayer().setFace(face);
        this.getPlayer().updateSingleStat(MapleStat.FACE, face);
        this.getPlayer().equipChanged();
    }
    
    public void setSkin(final int color) {
        this.getPlayer().setSkinColor((byte)color);
        this.getPlayer().updateSingleStat(MapleStat.SKIN, color);
        this.getPlayer().equipChanged();
    }
    
    public int setRandomAvatar(final int ticket, final int... args_all) {
        if (!this.haveItem(ticket)) {
            return -1;
        }
        this.gainItem(ticket, (short)(-1));
        final int args = args_all[Randomizer.nextInt(args_all.length)];
        if (args < 100) {
            this.c.getPlayer().setSkinColor((byte)args);
            this.c.getPlayer().updateSingleStat(MapleStat.SKIN, args);
        }
        else if (args < 30000) {
            this.c.getPlayer().setFace(args);
            this.c.getPlayer().updateSingleStat(MapleStat.FACE, args);
        }
        else {
            this.c.getPlayer().setHair(args);
            this.c.getPlayer().updateSingleStat(MapleStat.HAIR, args);
        }
        this.c.getPlayer().equipChanged();
        return 1;
    }
    
    public int setAvatar(final int ticket, final int args) {
        if (!this.haveItem(ticket)) {
            return -1;
        }
        this.gainItem(ticket, (short)(-1));
        if (args < 100) {
            this.c.getPlayer().setSkinColor((byte)args);
            this.c.getPlayer().updateSingleStat(MapleStat.SKIN, args);
        }
        else if (args < 30000) {
            this.c.getPlayer().setFace(args);
            this.c.getPlayer().updateSingleStat(MapleStat.FACE, args);
        }
        else {
            this.c.getPlayer().setHair(args);
            this.c.getPlayer().updateSingleStat(MapleStat.HAIR, args);
        }
        this.c.getPlayer().equipChanged();
        return 1;
    }
    
    public void sendStorage() {
        if (this.getPlayer().hasBlockedInventory2(true)) {
            this.c.getPlayer().dropMessage(1, "????????????????????????????????????");
            this.c.sendPacket(MaplePacketCreator.enableActions());
            return;
        }
        if (!World.isShutDown) {
            if (!World.isShopShutDown) {
                this.c.getPlayer().setConversation(4);
                this.c.getPlayer().getStorage().sendStorage(this.c, this.npc);
            }
            else {
                this.c.getPlayer().dropMessage(1, "???????????????????????????");
                this.c.sendPacket(MaplePacketCreator.enableActions());
            }
        }
        else {
            this.c.getPlayer().dropMessage(1, "???????????????????????????");
            this.c.sendPacket(MaplePacketCreator.enableActions());
        }
    }
    
    public void openShop(final int id) {
        MapleShopFactory.getInstance().getShop(id).sendShop(this.c);
    }
    
    public int gainGachaponItemTime(final int id, final int quantity, final long period) {
        return this.gainGachaponItemTime(id, quantity, this.c.getPlayer().getMap().getStreetName() + " - " + this.c.getPlayer().getMap().getMapName(), period);
    }
    
    public int gainGachaponItemTime(final int id, final int quantity, final String msg, final long period) {
        try {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            if (!ii.itemExists(id)) {
                return -1;
            }
            final IItem item = ii.isCash(id) ? MapleInventoryManipulator.addbyId_GachaponTime(this.c, id, (short)quantity, period) : MapleInventoryManipulator.addbyId_Gachapon(this.c, id, (short)quantity);
            if (item == null) {
                return -1;
            }
            final byte rareness = GameConstants.gachaponRareItem(item.getItemId());
            if (rareness == 1) {
                if (this.c.getPlayer().getMapId() == 910000000) {
                    Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[????????????]", " : ???????????? " + this.c.getPlayer().getName() + " ???" + msg + "?????????", item, rareness));
                }
                else {
                    Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[????????????-?????????]", " : ???????????? " + this.c.getPlayer().getName() + " ???" + msg + "?????????", item, rareness));
                }
            }
            else if (rareness == 2) {
                Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "] " + this.c.getPlayer().getName(), " : ?????????????????????????????????????????????", item, rareness));
            }
            else if (rareness > 2) {
                Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "] " + this.c.getPlayer().getName(), " : ?????????????????????????????????????????????????????????", item, rareness));
            }
            return item.getItemId();
        }
        catch (Exception ex) {
            return -1;
        }
    }
    
    public void changeJob(final int job) {
        this.c.getPlayer().changeJob(job);
    }
    
    public void startQuest(final int id) {
        MapleQuest.getInstance(id).start(this.getPlayer(), this.npc);
    }
    
    public void completeQuest(final int id) {
        MapleQuest.getInstance(id).complete(this.getPlayer(), this.npc);
    }
    
    public void forfeitQuest(final int id) {
        MapleQuest.getInstance(id).forfeit(this.getPlayer());
    }
    
    public void forceStartQuest() {
        MapleQuest.getInstance(this.questid).forceStart(this.getPlayer(), this.getNpc(), null);
    }
    
    @Override
    public void forceStartQuest(final int id) {
        MapleQuest.getInstance(id).forceStart(this.getPlayer(), this.getNpc(), null);
    }
    
    public void forceStartQuest(final String customData) {
        MapleQuest.getInstance(this.questid).forceStart(this.getPlayer(), this.getNpc(), customData);
    }
    
    public void forceCompleteQuest() {
        MapleQuest.getInstance(this.questid).forceComplete(this.getPlayer(), this.getNpc());
    }
    
    @Override
    public void forceCompleteQuest(final int id) {
        MapleQuest.getInstance(id).forceComplete(this.getPlayer(), this.getNpc());
    }
    
    public String getQuestCustomData() {
        return this.c.getPlayer().getQuestNAdd(MapleQuest.getInstance(this.questid)).getCustomData();
    }
    
    public void setQuestCustomData(final String customData) {
        this.getPlayer().getQuestNAdd(MapleQuest.getInstance(this.questid)).setCustomData(customData);
    }
    
    public int getMeso() {
        return this.getPlayer().getMeso();
    }
    
    public void gainAp(final int amount) {
        this.c.getPlayer().gainAp((short)amount);
    }
    
    public void expandInventory(final byte type, final int amt) {
        this.c.getPlayer().expandInventory(type, amt);
    }
    
    public void unequipEverything() {
        final MapleInventory equipped = this.getPlayer().getInventory(MapleInventoryType.EQUIPPED);
        final MapleInventory equip = this.getPlayer().getInventory(MapleInventoryType.EQUIP);
        final List<Short> ids = new LinkedList<Short>();
        for (final IItem item : equipped.list()) {
            ids.add(item.getPosition());
        }
        final Iterator<Short> iterator2 = ids.iterator();
        while (iterator2.hasNext()) {
            final short id = (short)Short.valueOf(iterator2.next());
            MapleInventoryManipulator.unequip(this.getC(), id, equip.getNextFreeSlot());
        }
    }
    
    public final void clearSkills() {
        final Map<ISkill, SkillEntry> skills = this.getPlayer().getSkills();
        for (final Entry<ISkill, SkillEntry> skill : skills.entrySet()) {
            this.getPlayer().changeSkillLevel((ISkill)skill.getKey(), (byte)0, (byte)0);
        }
    }
    
    public boolean hasSkill(final int skillid) {
        final ISkill theSkill = SkillFactory.getSkill(skillid);
        return theSkill != null && this.c.getPlayer().getSkillLevel(theSkill) > 0;
    }
    
    public void showEffect(final boolean broadcast, final String effect) {
        if (broadcast) {
            this.c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.showEffect(effect));
        }
        else {
            this.c.sendPacket(MaplePacketCreator.showEffect(effect));
        }
    }
    
    public void playSound(final boolean broadcast, final String sound) {
        if (broadcast) {
            this.c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.playSound(sound));
        }
        else {
            this.c.sendPacket(MaplePacketCreator.playSound(sound));
        }
    }
    
    public void environmentChange(final boolean broadcast, final String env) {
        if (broadcast) {
            this.c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.environmentChange(env, 2));
        }
        else {
            this.c.sendPacket(MaplePacketCreator.environmentChange(env, 2));
        }
    }
    
    public void updateBuddyCapacity(final int capacity) {
        this.c.getPlayer().setBuddyCapacity((byte)capacity);
    }
    
    public int getBuddyCapacity() {
        return this.c.getPlayer().getBuddyCapacity();
    }
    
    public int partyMembersInMap() {
        int inMap = 0;
        for (final MapleCharacter char2 : this.getPlayer().getMap().getCharactersThreadsafe()) {
            if (char2.getParty() == this.getPlayer().getParty()) {
                ++inMap;
            }
        }
        return inMap;
    }
    
    public List<MapleCharacter> getPartyMembers() {
        if (this.getPlayer().getParty() == null) {
            return null;
        }
        final List<MapleCharacter> chars = new LinkedList<MapleCharacter>();
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            for (final ChannelServer channel : ChannelServer.getAllInstances()) {
                final MapleCharacter ch = channel.getPlayerStorage().getCharacterById(chr.getId());
                if (ch != null) {
                    chars.add(ch);
                }
            }
        }
        return chars;
    }
    
    public void warpPartyWithExp(final int mapId, final int exp) {
        final MapleMap target = this.getMap(mapId);
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.c.getChannelServer().getPlayerStorage().getCharacterByName(chr.getName());
            if ((curChar.getEventInstance() == null && this.getPlayer().getEventInstance() == null) || curChar.getEventInstance() == this.getPlayer().getEventInstance()) {
                curChar.changeMap(target, target.getPortal(0));
                curChar.gainExp(exp, true, false, true);
            }
        }
    }
    
    public void warpPartyWithExpMeso(final int mapId, final int exp, final int meso) {
        final MapleMap target = this.getMap(mapId);
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.c.getChannelServer().getPlayerStorage().getCharacterByName(chr.getName());
            if ((curChar.getEventInstance() == null && this.getPlayer().getEventInstance() == null) || curChar.getEventInstance() == this.getPlayer().getEventInstance()) {
                curChar.changeMap(target, target.getPortal(0));
                curChar.gainExp(exp, true, false, true);
                curChar.gainMeso(meso, true);
            }
        }
    }
    
    public MapleSquad getSquad(final String type) {
        return this.c.getChannelServer().getMapleSquad(type);
    }
    
    public int getSquadAvailability(final String type) {
        final MapleSquad squad = this.c.getChannelServer().getMapleSquad(type);
        if (squad == null) {
            return -1;
        }
        return squad.getStatus();
    }
    
    public boolean registerSquad(final String type, final int minutes, final String startText) {
        if (this.c.getChannelServer().getMapleSquad(type) == null) {
            final MapleSquad squad = new MapleSquad(this.c.getChannel(), type, this.c.getPlayer(), minutes * 60 * 1000, startText);
            final boolean ret = this.c.getChannelServer().addMapleSquad(squad, type);
            if (ret) {
                final MapleMap map = this.c.getPlayer().getMap();
                map.broadcastMessage(MaplePacketCreator.getClock(minutes * 60));
                map.broadcastMessage(MaplePacketCreator.serverNotice(6, this.c.getPlayer().getName() + startText));
            }
            else {
                squad.clear();
            }
            return ret;
        }
        return false;
    }
    
    public boolean getSquadList(final String type, final byte type_) {
        final MapleSquad squad = this.c.getChannelServer().getMapleSquad(type);
        if (squad == null) {
            return false;
        }
        if (type_ == 0 || type_ == 3) {
            this.sendNext(squad.getSquadMemberString(type_));
        }
        else if (type_ == 1) {
            this.sendSimple(squad.getSquadMemberString(type_));
        }
        else if (type_ == 2) {
            if (squad.getBannedMemberSize() > 0) {
                this.sendSimple(squad.getSquadMemberString(type_));
            }
            else {
                this.sendNext(squad.getSquadMemberString(type_));
            }
        }
        return true;
    }
    
    public byte isSquadLeader(final String type) {
        final MapleSquad squad = this.c.getChannelServer().getMapleSquad(type);
        if (squad == null) {
            return -1;
        }
        if (squad.getLeader() != null && squad.getLeader().getId() == this.c.getPlayer().getId()) {
            return 1;
        }
        return 0;
    }
    
    public boolean reAdd(final String eim, final String squad) {
        final EventInstanceManager eimz = this.getDisconnected(eim);
        final MapleSquad squadz = this.getSquad(squad);
        if (eimz != null && squadz != null) {
            squadz.reAddMember(this.getPlayer());
            eimz.registerPlayer(this.getPlayer());
            return true;
        }
        return false;
    }
    
    public void banMember(final String type, final int pos) {
        final MapleSquad squad = this.c.getChannelServer().getMapleSquad(type);
        if (squad != null) {
            squad.banMember(pos);
        }
    }
    
    public void acceptMember(final String type, final int pos) {
        final MapleSquad squad = this.c.getChannelServer().getMapleSquad(type);
        if (squad != null) {
            squad.acceptMember(pos);
        }
    }
    
    public String getReadableMillis(final long startMillis, final long endMillis) {
        return StringUtil.getReadableMillis(startMillis, endMillis);
    }
    
    public int addMember(final String type, final boolean join) {
        final MapleSquad squad = this.c.getChannelServer().getMapleSquad(type);
        if (squad != null) {
            return squad.addMember(this.c.getPlayer(), join);
        }
        return -1;
    }
    
    public byte isSquadMember(final String type) {
        final MapleSquad squad = this.c.getChannelServer().getMapleSquad(type);
        if (squad == null) {
            return -1;
        }
        if (squad.getMembers().contains(this.c.getPlayer().getName())) {
            return 1;
        }
        if (squad.isBanned(this.c.getPlayer())) {
            return 2;
        }
        return 0;
    }
    
    public void resetReactors() {
        this.getPlayer().getMap().resetReactors();
    }
    
    public void genericGuildMessage(final int code) {
        this.c.sendPacket(MaplePacketCreator.genericGuildMessage((byte)code));
    }
    
    public void disbandGuild() {
        final int gid = this.c.getPlayer().getGuildId();
        if (gid <= 0 || this.c.getPlayer().getGuildRank() != 1) {
            return;
        }
        Guild.disbandGuild(gid);
    }
    
    public void increaseGuildCapacity() {
        if (this.c.getPlayer().getMeso() < 5000000) {
            this.c.sendPacket(MaplePacketCreator.serverNotice(1, "You do not have enough mesos."));
            return;
        }
        final int gid = this.c.getPlayer().getGuildId();
        if (gid <= 0) {
            return;
        }
        Guild.increaseGuildCapacity(gid);
        this.c.getPlayer().gainMeso(-5000000, true, false, true);
    }
    
    public void displayGuildRanks() {
        this.c.sendPacket(MaplePacketCreator.showGuildRanks(this.npc, MapleGuildRanking.getInstance().getGuildRank()));
    }
    
    public void showlvl() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().getLevelRank()));
    }
    
    public void showmeso() {
        this.c.sendPacket(MaplePacketCreator.showmesoRanks(this.npc, MapleGuildRanking.getInstance().getMesoRank()));
    }
    
    public boolean removePlayerFromInstance() {
        if (this.c.getPlayer().getEventInstance() != null) {
            this.c.getPlayer().getEventInstance().removePlayer(this.c.getPlayer());
            return true;
        }
        return false;
    }
    
    public boolean isPlayerInstance() {
        return this.c.getPlayer().getEventInstance() != null;
    }
    
    public void changeStat(final byte slot, final int type, final short amount) {
        final Equip sel = (Equip)this.c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short)slot);
        switch (type) {
            case 0: {
                sel.setStr(amount);
                break;
            }
            case 1: {
                sel.setDex(amount);
                break;
            }
            case 2: {
                sel.setInt(amount);
                break;
            }
            case 3: {
                sel.setLuk(amount);
                break;
            }
            case 4: {
                sel.setHp(amount);
                break;
            }
            case 5: {
                sel.setMp(amount);
                break;
            }
            case 6: {
                sel.setWatk(amount);
                break;
            }
            case 7: {
                sel.setMatk(amount);
                break;
            }
            case 8: {
                sel.setWdef(amount);
                break;
            }
            case 9: {
                sel.setMdef(amount);
                break;
            }
            case 10: {
                sel.setAcc(amount);
                break;
            }
            case 11: {
                sel.setAvoid(amount);
                break;
            }
            case 12: {
                sel.setHands(amount);
                break;
            }
            case 13: {
                sel.setSpeed(amount);
                break;
            }
            case 14: {
                sel.setJump(amount);
                break;
            }
            case 15: {
                sel.setUpgradeSlots((byte)amount);
                break;
            }
            case 16: {
                sel.setViciousHammer((byte)amount);
                break;
            }
            case 17: {
                sel.setLevel((byte)amount);
                break;
            }
            case 18: {
                sel.setEnhance((byte)amount);
                break;
            }
            case 19: {
                sel.setPotential1(amount);
                break;
            }
            case 20: {
                sel.setPotential2(amount);
                break;
            }
            case 21: {
                sel.setPotential3(amount);
                break;
            }
            case 22: {
                sel.setOwner(this.getText());
                break;
            }
        }
        this.c.getPlayer().equipChanged();
    }
    
    public void cleardrops() {
        MapleMonsterInformationProvider.getInstance().clearDrops();
    }
    
    public void killAllMonsters() {
        final MapleMap map = this.c.getPlayer().getMap();
        final double range = Double.POSITIVE_INFINITY;
        for (final MapleMapObject monstermo : map.getMapObjectsInRange(this.c.getPlayer().getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER))) {
            final MapleMonster mob = (MapleMonster)monstermo;
            if (mob.getStats().isBoss()) {
                map.killMonster(mob, this.c.getPlayer(), false, false, (byte)1);
            }
        }
    }
    
    public void giveMerchantMesos() {
        long mesos = 0L;
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT mesos FROM hiredmerchants WHERE merchantid = ?");
            ps.setInt(1, this.getPlayer().getId());
            final ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                rs.close();
                ps.close();
            }
            else {
                mesos = rs.getLong("mesos");
            }
            rs.close();
            ps.close();
            ps = con.prepareStatement("UPDATE hiredmerchants SET mesos = 0 WHERE merchantid = ?");
            ps.setInt(1, this.getPlayer().getId());
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException ex) {
            System.err.println("Error gaining mesos in hired merchant" + ex);
            FileoutputUtil.outError("logs/???????????????.txt", (Throwable)ex);
        }
        this.c.getPlayer().gainMeso((int)mesos, true);
    }
    
    public void dc() {
        final MapleCharacter victim = this.getChannelServer().getPlayerStorage().getCharacterByName(this.getPlayer().getName());
        victim.getClient().getSession().close();
        victim.getClient().disconnect(true, false);
    }
    
    public long getMerchantMesos() {
        long mesos = 0L;
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
             final PreparedStatement ps = con.prepareStatement("SELECT mesos FROM hiredmerchants WHERE merchantid = ?")) {
            ps.setInt(1, this.getPlayer().getId());
            try (final ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    rs.close();
                    ps.close();
                }
                else {
                    mesos = rs.getLong("mesos");
                }
            }
        }
        catch (SQLException ex) {
            System.err.println("Error gaining mesos in hired merchant" + ex);
            FileoutputUtil.outError("logs/???????????????.txt", (Throwable)ex);
        }
        return mesos;
    }
    
    public void openDuey() {
        this.c.getPlayer().setConversation(2);
        this.c.sendPacket(MaplePacketCreator.sendDuey((byte)9, null));
    }
    
    public void openMerchantItemStore() {
        if (!World.isShutDown) {
            this.c.getPlayer().setConversation(3);
            this.c.sendPacket(PlayerShopPacket.merchItemStore((byte)34));
        }
        else {
            this.c.getPlayer().dropMessage(1, "???????????????????????????????????????");
            this.c.sendPacket(MaplePacketCreator.enableActions());
        }
    }
    
    public void sendRepairWindow() {
        this.c.sendPacket(MaplePacketCreator.sendRepairWindow(this.npc));
    }
    
    public final int getDojoPoints() {
        return this.c.getPlayer().getDojo();
    }
    
    public void setDojoPoints(final int point) {
        this.c.getPlayer().setDojo(this.c.getPlayer().getDojo() + point);
    }
    
    public final int getDojoRecord() {
        return this.c.getPlayer().getDojoRecord();
    }
    
    public void setDojoRecord(final boolean reset) {
        this.c.getPlayer().setDojoRecord(reset);
    }
    
    public boolean start_DojoAgent(final boolean dojo, final boolean party) {
        if (dojo) {
            return Event_DojoAgent.warpStartDojo(this.c.getPlayer(), party);
        }
        return Event_DojoAgent.warpStartAgent(this.c.getPlayer(), party);
    }
    
    public boolean start_PyramidSubway(final int pyramid) {
        if (pyramid >= 0) {
            return Event_PyramidSubway.warpStartPyramid(this.c.getPlayer(), pyramid);
        }
        return Event_PyramidSubway.warpStartSubway(this.c.getPlayer());
    }
    
    public boolean bonus_PyramidSubway(final int pyramid) {
        if (pyramid >= 0) {
            return Event_PyramidSubway.warpBonusPyramid(this.c.getPlayer(), pyramid);
        }
        return Event_PyramidSubway.warpBonusSubway(this.c.getPlayer());
    }
    
    public final short getKegs() {
        return AramiaFireWorks.getInstance().getKegsPercentage();
    }
    
    public void giveKegs(final int kegs) {
        AramiaFireWorks.getInstance().giveKegs(this.c.getPlayer(), kegs);
    }
    
    public final short getSunshines() {
        return AramiaFireWorks.getInstance().getSunsPercentage();
    }
    
    public void addSunshines(final int kegs) {
        AramiaFireWorks.getInstance().giveSuns(this.c.getPlayer(), kegs);
    }
    
    public final short getDecorations() {
        return AramiaFireWorks.getInstance().getDecsPercentage();
    }
    
    public void addDecorations(final int kegs) {
        try {
            AramiaFireWorks.getInstance().giveDecs(this.c.getPlayer(), kegs);
        }
        catch (Exception ex) {}
    }
    
    public final MapleInventory getInventory(final int type) {
        return this.c.getPlayer().getInventory(MapleInventoryType.getByType((byte)type));
    }
    
    public final MapleCarnivalParty getCarnivalParty() {
        return this.c.getPlayer().getCarnivalParty();
    }
    
    public final MapleCarnivalChallenge getNextCarnivalRequest() {
        return this.c.getPlayer().getNextCarnivalRequest();
    }
    
    public final MapleCarnivalChallenge getCarnivalChallenge(final MapleCharacter chr) {
        return new MapleCarnivalChallenge(chr);
    }
    
    public void maxStats() {
        final Map<MapleStat, Integer> statup = new EnumMap<MapleStat, Integer>(MapleStat.class);
        this.c.getPlayer().getStat().setStr((short)32767);
        this.c.getPlayer().getStat().setDex((short)32767);
        this.c.getPlayer().getStat().setInt((short)32767);
        this.c.getPlayer().getStat().setLuk((short)32767);
        this.c.getPlayer().getStat().setMaxHp((short)30000);
        this.c.getPlayer().getStat().setMaxMp((short)30000);
        this.c.getPlayer().getStat().setHp(30000);
        this.c.getPlayer().getStat().setMp(30000);
        statup.put(MapleStat.STR, Integer.valueOf(32767));
        statup.put(MapleStat.DEX, Integer.valueOf(32767));
        statup.put(MapleStat.LUK, Integer.valueOf(32767));
        statup.put(MapleStat.INT, Integer.valueOf(32767));
        statup.put(MapleStat.HP, Integer.valueOf(30000));
        statup.put(MapleStat.MAXHP, Integer.valueOf(30000));
        statup.put(MapleStat.MP, Integer.valueOf(30000));
        statup.put(MapleStat.MAXMP, Integer.valueOf(30000));
        this.c.sendPacket(MaplePacketCreator.updatePlayerStats(statup, this.c.getPlayer()));
    }
    
    public Pair<String, Map<Integer, String>> getSpeedRun(final String typ) {
        final SpeedRunType stype = SpeedRunType.valueOf(typ);
        if (SpeedRunner.getInstance().getSpeedRunData(stype) != null) {
            return SpeedRunner.getInstance().getSpeedRunData(stype);
        }
        return new Pair<String, Map<Integer, String>>("", (Map<Integer, String>)new HashMap<Integer, String>());
    }
    
    public boolean getSR(final Pair<String, Map<Integer, String>> ma, final int sel) {
        if (((Map<Integer, String>)ma.getRight()).get(Integer.valueOf(sel)) == null || ((String)((Map<Integer, String>)ma.getRight()).get(Integer.valueOf(sel))).length() <= 0) {
            this.dispose();
            return false;
        }
        this.sendOk((String)((Map<Integer, String>)ma.getRight()).get(Integer.valueOf(sel)));
        return true;
    }
    
    public Equip getEquip(final int itemid) {
        return (Equip)MapleItemInformationProvider.getInstance().getEquipById(itemid);
    }
    
    public void setExpiration(final Object statsSel, final long expire) {
        if (statsSel instanceof Equip) {
            ((Equip)statsSel).setExpiration(System.currentTimeMillis() + expire * 24L * 60L * 60L * 1000L);
        }
    }
    
    public void setLock(final Object statsSel) {
        if (statsSel instanceof Equip) {
            final Equip eq = (Equip)statsSel;
            if (eq.getExpiration() == -1L) {
                eq.setFlag((byte)(eq.getFlag() | ItemFlag.LOCK.getValue()));
            }
            else {
                eq.setFlag((byte)(eq.getFlag() | ItemFlag.UNTRADEABLE.getValue()));
            }
        }
    }
    
    public boolean addFromDrop(final Object statsSel) {
        if (statsSel instanceof IItem) {
            final IItem it = (IItem)statsSel;
            return MapleInventoryManipulator.checkSpace(this.getClient(), it.getItemId(), (int)it.getQuantity(), it.getOwner()) && MapleInventoryManipulator.addFromDrop(this.getClient(), it, false);
        }
        return false;
    }
    
    public boolean replaceItem(final int slot, final int invType, final Object statsSel, final int offset, final String type) {
        return this.replaceItem(slot, invType, statsSel, offset, type, false);
    }
    
    public boolean replaceItem(final int slot, final int invType, final Object statsSel, final int offset, final String type, final boolean takeSlot) {
        final MapleInventoryType inv = MapleInventoryType.getByType((byte)invType);
        if (inv == null) {
            return false;
        }
        IItem item = this.getPlayer().getInventory(inv).getItem((short)(byte)slot);
        if (item == null || statsSel instanceof IItem) {
            item = (IItem)statsSel;
        }
        if (offset > 0) {
            if (inv != MapleInventoryType.EQUIP) {
                return false;
            }
            final Equip eq = (Equip)item;
            if (takeSlot) {
                if (eq.getUpgradeSlots() < 1) {
                    return false;
                }
                eq.setUpgradeSlots((byte)(eq.getUpgradeSlots() - 1));
            }
            if (type.equalsIgnoreCase("Slots")) {
                eq.setUpgradeSlots((byte)(eq.getUpgradeSlots() + offset));
            }
            else if (type.equalsIgnoreCase("Level")) {
                eq.setLevel((byte)(eq.getLevel() + offset));
            }
            else if (type.equalsIgnoreCase("Hammer")) {
                eq.setViciousHammer((byte)(eq.getViciousHammer() + offset));
            }
            else if (type.equalsIgnoreCase("STR")) {
                eq.setStr((short)(eq.getStr() + offset));
            }
            else if (type.equalsIgnoreCase("DEX")) {
                eq.setDex((short)(eq.getDex() + offset));
            }
            else if (type.equalsIgnoreCase("INT")) {
                eq.setInt((short)(eq.getInt() + offset));
            }
            else if (type.equalsIgnoreCase("LUK")) {
                eq.setLuk((short)(eq.getLuk() + offset));
            }
            else if (type.equalsIgnoreCase("HP")) {
                eq.setHp((short)(eq.getHp() + offset));
            }
            else if (type.equalsIgnoreCase("MP")) {
                eq.setMp((short)(eq.getMp() + offset));
            }
            else if (type.equalsIgnoreCase("WATK")) {
                eq.setWatk((short)(eq.getWatk() + offset));
            }
            else if (type.equalsIgnoreCase("MATK")) {
                eq.setMatk((short)(eq.getMatk() + offset));
            }
            else if (type.equalsIgnoreCase("WDEF")) {
                eq.setWdef((short)(eq.getWdef() + offset));
            }
            else if (type.equalsIgnoreCase("MDEF")) {
                eq.setMdef((short)(eq.getMdef() + offset));
            }
            else if (type.equalsIgnoreCase("ACC")) {
                eq.setAcc((short)(eq.getAcc() + offset));
            }
            else if (type.equalsIgnoreCase("Avoid")) {
                eq.setAvoid((short)(eq.getAvoid() + offset));
            }
            else if (type.equalsIgnoreCase("Hands")) {
                eq.setHands((short)(eq.getHands() + offset));
            }
            else if (type.equalsIgnoreCase("Speed")) {
                eq.setSpeed((short)(eq.getSpeed() + offset));
            }
            else if (type.equalsIgnoreCase("Jump")) {
                eq.setJump((short)(eq.getJump() + offset));
            }
            else if (type.equalsIgnoreCase("ItemEXP")) {
                eq.setItemEXP(eq.getItemEXP() + offset);
            }
            else if (type.equalsIgnoreCase("Expiration")) {
                eq.setExpiration(eq.getExpiration() + (long)offset);
            }
            else if (type.equalsIgnoreCase("Flag")) {
                eq.setFlag((byte)(eq.getFlag() + offset));
            }
            if (eq.getExpiration() == -1L) {
                eq.setFlag((byte)(eq.getFlag() | ItemFlag.LOCK.getValue()));
            }
            else {
                eq.setFlag((byte)(eq.getFlag() | ItemFlag.UNTRADEABLE.getValue()));
            }
            item = eq.copy();
        }
        MapleInventoryManipulator.removeFromSlot(this.getClient(), inv, (short)slot, item.getQuantity(), false);
        return MapleInventoryManipulator.addFromDrop(this.getClient(), item, false);
    }
    
    public boolean replaceItem(final int slot, final int invType, final Object statsSel, final int upgradeSlots) {
        return this.replaceItem(slot, invType, statsSel, upgradeSlots, "Slots");
    }
    
    public boolean isCash(final int itemId) {
        return MapleItemInformationProvider.getInstance().isCash(itemId);
    }
    
    public void buffGuild(final int buff, final int duration, final String msg) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        if (ii.getItemEffect(buff) != null && this.getPlayer().getGuildId() > 0) {
            final MapleStatEffect mse = ii.getItemEffect(buff);
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharactersThreadSafe()) {
                    if (chr.getGuildId() == this.getPlayer().getGuildId()) {
                        mse.applyTo(chr, chr, true, null, duration);
                        chr.dropMessage(5, "Your guild has gotten a " + msg + " buff.");
                    }
                }
            }
        }
    }
    
    public boolean createAlliance(final String alliancename) {
        final MapleParty pt = this.c.getPlayer().getParty();
        final MapleCharacter otherChar = this.c.getChannelServer().getPlayerStorage().getCharacterById(pt.getMemberByIndex(1).getId());
        if (otherChar == null || otherChar.getId() == this.c.getPlayer().getId()) {
            return false;
        }
        try {
            return Alliance.createAlliance(alliancename, this.c.getPlayer().getId(), otherChar.getId(), this.c.getPlayer().getGuildId(), otherChar.getGuildId());
        }
        catch (Exception re) {
            return false;
        }
    }
    
    public boolean addCapacityToAlliance() {
        try {
            final MapleGuild gs = Guild.getGuild(this.c.getPlayer().getGuildId());
            if (gs != null && this.c.getPlayer().getGuildRank() == 1 && this.c.getPlayer().getAllianceRank() == 1 && Alliance.getAllianceLeader(gs.getAllianceId()) == this.c.getPlayer().getId() && Alliance.changeAllianceCapacity(gs.getAllianceId())) {
                this.gainMeso(-10000000);
                return true;
            }
        }
        catch (Exception ex) {}
        return false;
    }
    
    public boolean disbandAlliance() {
        try {
            final MapleGuild gs = Guild.getGuild(this.c.getPlayer().getGuildId());
            if (gs != null && this.c.getPlayer().getGuildRank() == 1 && this.c.getPlayer().getAllianceRank() == 1 && Alliance.getAllianceLeader(gs.getAllianceId()) == this.c.getPlayer().getId() && Alliance.disbandAlliance(gs.getAllianceId())) {
                return true;
            }
        }
        catch (Exception ex) {}
        return false;
    }
    
    public byte getLastMsg() {
        return this.lastMsg;
    }
    
    public final void setLastMsg(final byte last) {
        this.lastMsg = last;
    }
    
    @Override
    public void setPartyBossLog(final String bossid) {
        final MapleParty party = this.getPlayer().getParty();
        for (final MaplePartyCharacter pc : party.getMembers()) {
            final MapleCharacter chr = World.getStorage(this.getChannelNumber()).getCharacterById(pc.getId());
            if (chr != null) {
                chr.setBossLog(bossid);
            }
        }
    }
    
    public final void maxAllSkills() {
        for (final ISkill skil : SkillFactory.getAllSkills()) {
            if (GameConstants.isApplicableSkill(skil.getId())) {
                this.teachSkill(skil.getId(), skil.getMaxLevel(), skil.getMaxLevel());
            }
        }
    }
    
    public final void resetStats(final int str, final int dex, final int z, final int luk) {
        this.c.getPlayer().resetStats(str, dex, z, luk);
    }
    
    public final boolean dropItem(final int slot, final int invType, final int quantity) {
        final MapleInventoryType inv = MapleInventoryType.getByType((byte)invType);
        return inv != null && MapleInventoryManipulator.drop(this.c, inv, (short)slot, (short)quantity, true);
    }
    
    public final List<Integer> getAllPotentialInfo() {
        return new ArrayList<Integer>((Collection<? extends Integer>)MapleItemInformationProvider.getInstance().getAllPotentialInfo().keySet());
    }
    
    public final String getPotentialInfo(final int id) {
        final List<StructPotentialItem> potInfo = MapleItemInformationProvider.getInstance().getPotentialInfo(id);
        final StringBuilder builder = new StringBuilder("#b#ePOTENTIAL INFO FOR ID: ");
        builder.append(id);
        builder.append("#n#k\r\n\r\n");
        int minLevel = 1;
        int maxLevel = 10;
        for (final StructPotentialItem item : potInfo) {
            builder.append("#eLevels ");
            builder.append(minLevel);
            builder.append("~");
            builder.append(maxLevel);
            builder.append(": #n");
            builder.append(item.toString());
            minLevel += 10;
            maxLevel += 10;
            builder.append("\r\n");
        }
        return builder.toString();
    }
    
    public final void sendRPS() {
        this.c.sendPacket(MaplePacketCreator.getRPSMode((byte)8, -1, -1, -1));
    }
    
    public final void setQuestRecord(final Object ch, final int questid, final String data) {
        ((MapleCharacter)ch).getQuestNAdd(MapleQuest.getInstance(questid)).setCustomData(data);
    }
    
    public final void doWeddingEffect(final Object ch) {
        final MapleCharacter chr = (MapleCharacter)ch;
        this.getMap().broadcastMessage(MaplePacketCreator.yellowChat(chr.getName() + ", ??????????????? " + this.getPlayer().getName() + " ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????"));
        CloneTimer.getInstance().schedule((Runnable)new Runnable() {
            @Override
            public void run() {
                if (chr == null || NPCConversationManager.this.getPlayer() == null) {
                    NPCConversationManager.this.warpMap(680000500, 0);
                }
                else {
                    NPCConversationManager.this.getMap().broadcastMessage(MaplePacketCreator.yellowChat(NPCConversationManager.this.getPlayer().getName() + ", ????????????????????? " + chr.getName() + " ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????"));
                }
            }
        }, 10000L);
        CloneTimer.getInstance().schedule((Runnable)new Runnable() {
            @Override
            public void run() {
                if (chr == null || NPCConversationManager.this.getPlayer() == null) {
                    if (NPCConversationManager.this.getPlayer() != null) {
                        NPCConversationManager.this.setQuestRecord(NPCConversationManager.this.getPlayer(), 160001, "3");
                        NPCConversationManager.this.setQuestRecord(NPCConversationManager.this.getPlayer(), 160002, "0");
                    }
                    else if (chr != null) {
                        NPCConversationManager.this.setQuestRecord(chr, 160001, "3");
                        NPCConversationManager.this.setQuestRecord(chr, 160002, "0");
                    }
                    NPCConversationManager.this.warpMap(680000500, 0);
                }
                else {
                    NPCConversationManager.this.setQuestRecord(NPCConversationManager.this.getPlayer(), 160001, "2");
                    NPCConversationManager.this.setQuestRecord(chr, 160001, "2");
                    NPCConversationManager.this.sendNPCText(NPCConversationManager.this.getPlayer().getName() + " ??? " + chr.getName() + "??? ????????????????????????????????????????????????????????????", 9201002);
                    NPCConversationManager.this.getMap().startExtendedMapEffect("??????????????????????????? " + NPCConversationManager.this.getPlayer().getName() + "???", 5120006);
                    if (chr.getGuildId() > 0) {
                        Guild.guildPacket(chr.getGuildId(), MaplePacketCreator.sendMarriage(false, chr.getName()));
                    }
                    if (chr.getFamilyId() > 0) {
                        Family.familyPacket(chr.getFamilyId(), MaplePacketCreator.sendMarriage(true, chr.getName()), chr.getId());
                    }
                    if (NPCConversationManager.this.getPlayer().getGuildId() > 0) {
                        Guild.guildPacket(NPCConversationManager.this.getPlayer().getGuildId(), MaplePacketCreator.sendMarriage(false, NPCConversationManager.this.getPlayer().getName()));
                    }
                    if (NPCConversationManager.this.getPlayer().getFamilyId() > 0) {
                        Family.familyPacket(NPCConversationManager.this.getPlayer().getFamilyId(), MaplePacketCreator.sendMarriage(true, chr.getName()), NPCConversationManager.this.getPlayer().getId());
                    }
                }
            }
        }, 20000L);
    }
    
    public void ???????????????(final int type) {
        this.c.sendPacket(MaplePacketCreator.openBeans(this.getPlayer().getBeans(), type));
    }
    
    public void worldMessage(final String text) {
        Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, text));
    }
    
    public int getBeans() {
        return this.getClient().getPlayer().getBeans();
    }
    
    public void warpBack(final int mid, final int retmap, final int time) {
        final MapleMap warpMap = this.c.getChannelServer().getMapFactory().getMap(mid);
        this.c.getPlayer().changeMap(warpMap, warpMap.getPortal(0));
        this.c.sendPacket(MaplePacketCreator.getClock(time));
        EventTimer.getInstance().schedule((Runnable)new Runnable() {
            @Override
            public void run() {
                final MapleMap warpMap = c.getChannelServer().getMapFactory().getMap(retmap);
                if (c.getPlayer() != null) {
                    c.sendPacket(MaplePacketCreator.stopClock());
                    c.getPlayer().changeMap(warpMap, warpMap.getPortal(0));
                    c.getPlayer().dropMessage(6, "????????????????????????!");
                }
            }
        }, (long)(1000 * time));
    }
    
    public void ChangeName(final String name) {
        this.getPlayer().setName(name);
        this.save();
        this.getPlayer().fakeRelog();
    }
    
    public String searchData(final int type, final String search) {
        return SearchGenerator.searchData(type, search);
    }
    
    public int[] getSearchData(final int type, final String search) {
        final Map<Integer, String> data = SearchGenerator.getSearchData(type, search);
        if (data.isEmpty()) {
            return null;
        }
        final int[] searches = new int[data.size()];
        int i = 0;
        final Iterator<Integer> iterator = data.keySet().iterator();
        while (iterator.hasNext()) {
            final int key = (int)Integer.valueOf(iterator.next());
            searches[i] = key;
            ++i;
        }
        return searches;
    }
    
    public boolean foundData(final int type, final String search) {
        return SearchGenerator.foundData(type, search);
    }
    
    public boolean ReceiveMedal() {
        final int acid = this.getPlayer().getAccountID();
        final int id = this.getPlayer().getId();
        final String name = this.getPlayer().getName();
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final int item = 1142475;
        if (!this.getPlayer().canHold(item)) {
            return false;
        }
        if (this.getPlayer().haveItem(item)) {
            return false;
        }
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT id FROM RCmedals WHERE name = ?");
            ps.setString(1, name);
            final ResultSet rs = ps.executeQuery();
            if (!rs.first()) {
                return false;
            }
            ps.close();
            rs.close();
            ps = con.prepareStatement("Update RCmedals set amount = ? Where id = ?");
            ps.setInt(1, 0);
            ps.setInt(2, id);
            ps.execute();
            ps.close();
        }
        catch (Exception ex) {
            FilePrinter.printError("NPCConversationManager.txt", (Throwable)ex, "ReceiveMedal(" + name + ")");
            FileoutputUtil.outError("logs/???????????????.txt", (Throwable)ex);
        }
        final IItem toDrop = ii.randomizeStats((Equip)ii.getEquipById(item));
        toDrop.setGMLog(this.getPlayer().getName() + " ????????????");
        MapleInventoryManipulator.addbyItem(this.c, toDrop);
        FileoutputUtil.logToFile("logs/Data/NPC????????????.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + this.c.getSession().remoteAddress().toString().split(":")[0] + " ??????: " + this.c.getAccountName() + " ??????: " + this.c.getPlayer().getName() + " ?????????RC??????");
        return true;
    }
    
    public String ShowJobRank(final int type) {
        final StringBuilder sb = new StringBuilder();
        final List<JobRankingInfo> Ranking = MapleGuildRanking.getInstance().getJobRank(type);
        if (Ranking != null) {
            int num = 0;
            for (final JobRankingInfo info : Ranking) {
                ++num;
                sb.append("#n#e#k??????:#r ");
                sb.append(num);
                sb.append("\r\n#n#e#k????????????:#d ");
                sb.append(StringUtil.getRightPaddedStr(info.getName(), ' ', 13));
                sb.append("\r\n#n#e#k??????:#e#r ");
                sb.append(StringUtil.getRightPaddedStr(String.valueOf(info.getLevel()), ' ', 3));
                sb.append("\r\n#n#e#k??????:#e#b ");
                sb.append(MapleJob.getName(MapleJob.getById(info.getJob())));
                sb.append("\r\n#n#e#k??????:#e#d ");
                sb.append(StringUtil.getRightPaddedStr(String.valueOf(info.getStr()), ' ', 4));
                sb.append("\r\n#n#e#k??????:#e#d ");
                sb.append(StringUtil.getRightPaddedStr(String.valueOf(info.getDex()), ' ', 4));
                sb.append("\r\n#n#e#k??????:#e#d ");
                sb.append(StringUtil.getRightPaddedStr(String.valueOf(info.getInt()), ' ', 4));
                sb.append("\r\n#n#e#k??????:#e#d ");
                sb.append(StringUtil.getRightPaddedStr(String.valueOf(info.getLuk()), ' ', 4));
                sb.append("\r\n");
                sb.append("#n#k======================================================\r\n");
            }
        }
        else {
            sb.append("#r????????????????????????");
        }
        return sb.toString();
    }
    
    public static boolean hairExists(final int hair) {
        return MapleItemInformationProvider.hairList.containsKey(Integer.valueOf(hair));
    }
    
    public int[] getCanHair(final int[] hairs) {
        final List<Integer> canHair = new ArrayList<Integer>();
        final List<Integer> cantHair = new ArrayList<Integer>();
        for (final int hair : hairs) {
            if (hairExists(hair)) {
                canHair.add(Integer.valueOf(hair));
            }
            else {
                cantHair.add(Integer.valueOf(hair));
            }
        }
        if (cantHair.size() > 0 && this.c.getPlayer().isAdmin()) {
            final StringBuilder sb = new StringBuilder("???????????????????????????");
            sb.append(cantHair.size()).append("??????????????????????????????????????????????????????");
            for (int i = 0; i < cantHair.size(); ++i) {
                sb.append(cantHair.get(i));
                if (i < cantHair.size() - 1) {
                    sb.append(",");
                }
            }
            this.playerMessage(sb.toString());
        }
        final int[] getHair = new int[canHair.size()];
        for (int i = 0; i < canHair.size(); ++i) {
            getHair[i] = (int)Integer.valueOf(canHair.get(i));
        }
        return getHair;
    }
    
    public static boolean faceExists(final int face) {
        return MapleItemInformationProvider.faceLists.containsKey(Integer.valueOf(face));
    }
    
    public int[] getCanFace(final int[] faces) {
        final List<Integer> canFace = new ArrayList<Integer>();
        final List<Integer> cantFace = new ArrayList<Integer>();
        for (final int face : faces) {
            if (faceExists(face)) {
                canFace.add(Integer.valueOf(face));
            }
            else {
                cantFace.add(Integer.valueOf(face));
            }
        }
        if (cantFace.size() > 0 && this.c.getPlayer().isAdmin()) {
            final StringBuilder sb = new StringBuilder("???????????????????????????");
            sb.append(cantFace.size()).append("??????????????????????????????????????????????????????");
            for (int i = 0; i < cantFace.size(); ++i) {
                sb.append(cantFace.get(i));
                if (i < cantFace.size() - 1) {
                    sb.append(",");
                }
            }
            this.playerMessage(sb.toString());
        }
        final int[] getFace = new int[canFace.size()];
        for (int i = 0; i < canFace.size(); ++i) {
            getFace[i] = (int)Integer.valueOf(canFace.get(i));
        }
        return getFace;
    }
    
    public String checkDrop(final int mobId) {
        final List<MonsterDropEntry> ranks = MapleMonsterInformationProvider.getInstance().retrieveDrop(mobId);
        if (ranks != null && ranks.size() > 0) {
            int num = 0;
            int itemId = 0;
            int ch = 0;
            final StringBuilder name = new StringBuilder();
            for (int i = 0; i < ranks.size(); ++i) {
                final MonsterDropEntry de = (MonsterDropEntry)ranks.get(i);
                if (de.chance > 0 && (de.questid <= 0 || (de.questid > 0 && MapleQuest.getInstance((int)de.questid).getName().length() > 0))) {
                    itemId = de.itemId;
                    if (num == 0) {
                        name.append("???????????? #o" + mobId + "# ????????????:\r\n");
                        name.append("--------------------------------------\r\n");
                    }
                    String namez = "#z" + itemId + "#";
                    if (itemId == 0) {
                        itemId = 4031041;
                        namez = de.Minimum * this.getClient().getChannelServer().getMesoRate() + " ??? " + de.Maximum * this.getClient().getChannelServer().getMesoRate() + " ??????";
                    }
                    ch = de.chance * this.getClient().getChannelServer().getDropRate();
                    name.append(num + 1 + ") #v" + itemId + "#" + namez + ((de.questid > 0 && MapleQuest.getInstance((int)de.questid).getName().length() > 0) ? ("?????????????????? " + MapleQuest.getInstance((int)de.questid).getName() + "") : "") + "\r\n");
                    ++num;
                }
            }
            if (name.length() > 0) {
                return name.toString();
            }
        }
        return "????????????????????????????????????";
    }
    
    public String checkDrop(final MapleCharacter chr, final int mobId, final boolean GM) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final List<MonsterDropEntry> ranks = MapleMonsterInformationProvider.getInstance().retrieveDrop(mobId);
        if (ranks != null && ranks.size() > 0) {
            int num = 0;
            int itemId = 0;
            int ch = 0;
            final StringBuilder name = new StringBuilder();
            final StringBuilder error = new StringBuilder();
            name.append("???#r#o" + mobId + "##k???????????????????????????:#b\r\n");
            for (int i = 0; i < ranks.size(); ++i) {
                final MonsterDropEntry de = (MonsterDropEntry)ranks.get(i);
                if (de.chance > 0 && (de.questid <= 0 || (de.questid > 0 && MapleQuest.getInstance((int)de.questid).getName().length() > 0))) {
                    itemId = de.itemId;
                    String namez = "#z" + itemId + "#";
                    if (itemId == 0) {
                        itemId = 4031041;
                        namez = de.Minimum * this.getClient().getChannelServer().getMesoRate() + " to " + de.Maximum * this.getClient().getChannelServer().getMesoRate() + " #b??????#l#k";
                    }
                    else if (itemId != 0 && ii.itemExists(itemId)) {
                        ch = de.chance * this.getClient().getChannelServer().getDropRate();
                        if (!GM) {
                            name.append("#k" + (num + 1) + ": #v" + itemId + "# " + namez + (chr.isGM() ? ("#d  ???????????????" + (double)Integer.valueOf((ch >= 999999) ? 1000000 : ch) / 10000.0 + "%\r\n") : "\r\n") + "#b(????????????:" + ((de.questid > 0 && MapleQuest.getInstance((int)de.questid).getName().length() > 0) ? ("??????????????????#r " + MapleQuest.getInstance((int)de.questid).getName() + " #b)\r\n") : "#r???#b)") + "\r\n");
                        }
                        else {
                            name.append("#L" + itemId + "##k" + (num + 1) + ": #v" + itemId + "# " + namez + (chr.isGM() ? ("#d  ???????????????" + (double)Integer.valueOf((ch >= 999999) ? 1000000 : ch) / 10000.0 + "%(????????????)\r\n") : "\r\n") + "#b(????????????:" + ((de.questid > 0 && MapleQuest.getInstance((int)de.questid).getName().length() > 0) ? ("??????????????????#r " + MapleQuest.getInstance((int)de.questid).getName() + " #b)\r\n") : "#r???#b)") + "\r\n");
                        }
                        ++num;
                    }
                    else {
                        error.append(itemId + "\r\n");
                    }
                }
            }
            if (GM) {
                name.append("\r\n#L10000##k" + (num + 1) + ": #b??????????????????????????????!");
            }
            if (error.length() > 0) {
                chr.dropMessage(1, "???????????????ID:\r\n" + error.toString());
            }
            if (name.length() > 0) {
                return name.toString();
            }
        }
        return "????????????????????????????????????";
    }
    
    public void gainBeans(final int s) {
        this.getPlayer().gainBeans(s);
        this.c.getSession().write(MaplePacketCreator.updateBeans(this.c.getPlayer()));
    }
    
    public void openBeans() {
        this.c.getSession().write(MaplePacketCreator.openBeans(this.getPlayer().getBeans(), 0));
        this.c.getPlayer().dropMessage(5, "?????????????????????????????????,?????????????????????????????????,????????????????????????????????????????????????????????????");
    }
    
    public void setMonsterRiding(final int itemid) {
        final short src = this.getClient().getPlayer().haveItemPos(itemid);
        if (src == 100) {
            this.c.getPlayer().dropMessage(5, "????????????????????????");
        }
        else {
            MapleInventoryManipulator.equip(this.c, src, (short)(-18));
            this.c.getPlayer().dropMessage(5, "?????????????????????");
        }
    }
    
    public int getRandom(final int... args_all) {
        final int args = args_all[Randomizer.nextInt(args_all.length)];
        return args;
    }
    
    public void OwlAdv(final int point, final int itemid) {
        owlse(this.c, point, itemid);
    }
    
    public static void owlse(final MapleClient c, final int point, final int itemid) {
        final int itemSearch = itemid;
        final List<HiredMerchant> hms = new ArrayList<HiredMerchant>();
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            if (!cserv.searchMerchant(itemSearch).isEmpty()) {
                hms.addAll((Collection<? extends HiredMerchant>)cserv.searchMerchant(itemSearch));
            }
        }
        if (hms.size() > 0) {
            if (c.getPlayer().haveItem(5230000, 1)) {
                MapleInventoryManipulator.removeById(c, MapleInventoryType.CASH, 5230000, 1, true, false);
            }
            else if (c.getPlayer().getCSPoints(point) >= 5) {
                c.getPlayer().modifyCSPoints(point, -5, true);
            }
            else {
                c.getPlayer().dropMessage(1, "??????????????????????????????");
                if (NPCScriptManager.getInstance().getCM(c) != null) {
                    NPCScriptManager.getInstance().dispose(c);
                    c.sendPacket(MaplePacketCreator.enableActions());
                }
            }
            if (NPCScriptManager.getInstance().getCM(c) != null) {
                NPCScriptManager.getInstance().dispose(c);
            }
            c.sendPacket(MaplePacketCreator.getOwlSearched(itemSearch, hms));
        }
        else {
            if (NPCScriptManager.getInstance().getCM(c) != null) {
                NPCScriptManager.getInstance().dispose(c);
                c.sendPacket(MaplePacketCreator.enableActions());
            }
            c.getPlayer().dropMessage(1, "???????????????");
        }
    }
    
    public void checkMobs(final MapleCharacter chr) {
        if (this.getMap().getAllMonstersThreadsafe().size() <= 0) {
            this.sendOk("#????????????????????????!!???");
            this.dispose();
        }
        String msg = "?????? #b" + chr.getName() + "#k ???????????????????????????:\r\n#r(????????????????????????,????????????BUG??????????????????????????????)\r\n#d";
        for (final Object monsterid : this.getMap().getAllUniqueMonsters()) {
            msg = msg + "#L" + monsterid + "##o" + monsterid + "# ??????:" + monsterid + " (??????)#l\r\n";
        }
        this.sendOk(msg);
    }
    
    public void getMobs(final int itemid) {
        final MapleMonsterInformationProvider mi = MapleMonsterInformationProvider.getInstance();
        final List<Integer> mobs = MapleMonsterInformationProvider.getInstance().getMobByItem(itemid);
        String text = "#d???????????????????????????????????????#k: \r\n\r\n";
        for (int i = 0; i < mobs.size(); ++i) {
            int quest = 0;
            if (mi.getDropQuest((int)Integer.valueOf(mobs.get(i))) > 0) {
                quest = mi.getDropQuest((int)Integer.valueOf(mobs.get(i)));
            }
            final int chance = mi.getDropChance((int)Integer.valueOf(mobs.get(i))) * this.getClient().getChannelServer().getDropRate();
            text = text + "#r#o" + mobs.get(i) + "##k " + ((quest > 0 && MapleQuest.getInstance(quest).getName().length() > 0) ? ("#b???????????? " + MapleQuest.getInstance(quest).getName() + " ???????????????#k") : "") + "\r\n";
        }
        this.sendNext(text);
    }
    
    public Gashapon getGashapon() {
        return GashaponFactory.getInstance().getGashaponByNpcId(this.getNpc());
    }
    
    public void getGachaponMega(final String msg, final Item item, final int quantity) {
        Broadcast.broadcastGashponmega(MaplePacketCreator.getGachaponMega(this.c.getPlayer().getName(), " : x" + quantity + "???????????? " + this.c.getPlayer().getName() + " ???" + msg + "?????????", (IItem)item, (byte)1, this.c.getPlayer().getClient().getChannel()));
    }
    
    public void EnterCS(final int mod) {
        this.c.getPlayer().setCsMod(mod);
        InterServerHandler.EnterCashShop(this.c, this.c.getPlayer(), false);
    }
    
    public int[] getSavedFaces() {
        return this.getPlayer().getSavedFaces();
    }
    
    public int getSavedFace(final int sel) {
        return this.getPlayer().getSavedFace(sel);
    }
    
    public void setSavedFace(final int sel, final int id) {
        this.getPlayer().setSavedFace(sel, id);
    }
    
    public int[] getSavedHairs() {
        return this.getPlayer().getSavedHairs();
    }
    
    public int getSavedHair(final int sel) {
        return this.getPlayer().getSavedHair(sel);
    }
    
    public void setSavedHair(final int sel, final int id) {
        this.getPlayer().setSavedHair(sel, id);
    }
    
    public int ???????????????ID() {
        int ?????????ID = 0;
        try {
            final int cid = this.getPlayer().getAccountID();
            final Connection con = DatabaseConnection.getConnection();
            ResultSet rs;
            try (final PreparedStatement limitCheck = con.prepareStatement("SELECT * FROM accounts WHERE id=" + cid + "")) {
                rs = limitCheck.executeQuery();
                if (rs.next()) {
                    ?????????ID = rs.getInt("?????????ID");
                }
            }
            rs.close();
        }
        catch (SQLException ex) {
            ex.getStackTrace();
        }
        return ?????????ID;
    }
    
    public void ???????????????ID(final int slot) {
        try {
            final int cid = this.getPlayer().getAccountID();
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE accounts SET ?????????ID = " + slot + " WHERE id = " + cid + "");
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException ex) {
            ex.getStackTrace();
        }
    }
    
    public int ???????????????() {
        int ????????? = 0;
        try {
            final int cid = this.getPlayer().getAccountID();
            final Connection con = DatabaseConnection.getConnection();
            ResultSet rs;
            try (final PreparedStatement limitCheck = con.prepareStatement("SELECT * FROM accounts WHERE id=" + cid + "")) {
                rs = limitCheck.executeQuery();
                if (rs.next()) {
                    ????????? = rs.getInt("?????????");
                }
            }
            rs.close();
        }
        catch (SQLException ex) {
            ex.getStackTrace();
        }
        return ?????????;
    }
    
    public void ???????????????(final int slot) {
        try {
            final int cid = this.???????????????ID();
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE accounts SET ????????? = ????????? + " + slot + " WHERE id = " + cid + "");
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException ex) {
            ex.getStackTrace();
        }
    }
    
    public void ???????????????(final int slot) {
        try {
            final int cid = this.getPlayer().getAccountID();
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE accounts SET ????????? = ?????????+" + slot + " WHERE id = " + cid + "");
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException ex) {
            ex.getStackTrace();
        }
    }
    
    @Override
    public String getServerName() {
        return ServerConfig.SERVERNAME;
    }
    
    public String ????????????() {
        return this.c.getChannelServer().getServerName();
    }
    
    public String ????????????(final int a) {
        String data = "";
        data = "#v" + a + "# #b#z" + a + "##k";
        return data;
    }
    
    public void ????????????() {
        this.c.getPlayer().getClient().getSession().write(MaplePacketCreator.getCharInfo(this.c.getPlayer()));
        this.c.getPlayer().getMap().removePlayer(this.c.getPlayer());
        this.c.getPlayer().getMap().addPlayer(this.c.getPlayer());
        this.c.getSession().write(MaplePacketCreator.enableActions());
    }
    
    public void ????????????() {
        final boolean custMap = true;
        final int mapid = this.c.getPlayer().getMapId();
        final MapleMap map = custMap ? this.c.getPlayer().getClient().getChannelServer().getMapFactory().getMap(mapid) : this.c.getPlayer().getMap();
        if (this.c.getPlayer().getClient().getChannelServer().getMapFactory().destroyMap(mapid)) {
            final MapleMap newMap = this.c.getPlayer().getClient().getChannelServer().getMapFactory().getMap(mapid);
            final MaplePortal newPor = newMap.getPortal(0);
            final LinkedHashSet<MapleCharacter> mcs = new LinkedHashSet<MapleCharacter>((Collection<? extends MapleCharacter>)map.getCharacters());
            for (final MapleCharacter m : mcs) {
                int x = 0;
                while (x < 5) {
                    try {
                        m.changeMap(newMap, newPor);
                    }
                    catch (Throwable t) {
                        ++x;
                        continue;
                    }
                    break;
                }
            }
        }
    }
    
    public void ????????????(final int a) {
        final boolean custMap = true;
        final int mapid = a;
        final MapleMap map = custMap ? this.c.getPlayer().getClient().getChannelServer().getMapFactory().getMap(mapid) : this.c.getPlayer().getMap();
        if (this.c.getPlayer().getClient().getChannelServer().getMapFactory().destroyMap(mapid)) {
            final MapleMap newMap = this.c.getPlayer().getClient().getChannelServer().getMapFactory().getMap(mapid);
            final MaplePortal newPor = newMap.getPortal(0);
            final LinkedHashSet<MapleCharacter> mcs = new LinkedHashSet<MapleCharacter>((Collection<? extends MapleCharacter>)map.getCharacters());
            for (final MapleCharacter m : mcs) {
                int x = 0;
                while (x < 5) {
                    try {
                        m.changeMap(newMap, newPor);
                    }
                    catch (Throwable t) {
                        ++x;
                        continue;
                    }
                    break;
                }
            }
        }
    }
    
    public void ????????????(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "00 00", (byte)0));
        this.lastMsg = 0;
    }
    
    public void ??????????????????(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)1, text, "", (byte)0));
        this.lastMsg = 1;
    }
    
    public int ????????????() {
        int count = 0;
        for (final ChannelServer chl : ChannelServer.getAllInstances()) {
            count += chl.getPlayerStorage().getAllCharacters().size();
        }
        return count;
    }
    
    public void ????????????(final String web) {
        this.c.sendPacket(MaplePacketCreator.openWeb(web));
    }
    
    public void ?????????(final int action, final byte level, final byte masterlevel) {
        this.c.getPlayer().changeSkillLevel(SkillFactory.getSkill(action), level, masterlevel);
    }
    
    public void ????????????() {
        NPCScriptManager.getInstance().dispose(this.c);
    }
    
    public int ??????????????????() {
        return this.getClient().getPlayer().getBeans();
    }
    
    public void ?????????(final int s) {
        this.getPlayer().gainBeans(s);
        this.c.sendPacket(MaplePacketCreator.updateBeans(this.c.getPlayer()));
    }
    
    public void ?????????(final int s) {
        this.getPlayer().gainBeans(-s);
        this.c.sendPacket(MaplePacketCreator.updateBeans(this.c.getPlayer()));
    }
    
    public void ??????????????????(final int upgr) {
        final Equip item = (Equip)this.c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((short)1).copy();
        item.setUpgradeSlots((byte)(item.getUpgradeSlots() + upgr));
        MapleInventoryManipulator.removeFromSlot(this.getC(), MapleInventoryType.EQUIP, (short)1, (short)1, true);
        MapleInventoryManipulator.addFromDrop(this.getChar().getClient(), (IItem)item, false);
    }
    
    public void gainEquiPproperty(final int upgr, final int watk, final int matk, final int str, final int dex, final int Int, final int luk, final int hp, final int mp, final int acc, final int avoid) {
        final Equip item = (Equip)this.c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((short)1).copy();
        item.setUpgradeSlots((byte)(item.getUpgradeSlots() + upgr));
        item.setWatk((short)(item.getWatk() + watk));
        item.setMatk((short)(item.getMatk() + matk));
        item.setStr((short)(item.getStr() + str));
        item.setDex((short)(item.getDex() + dex));
        item.setInt((short)(item.getInt() + Int));
        item.setLuk((short)(item.getLuk() + luk));
        item.setHp((short)(item.getHp() + hp));
        item.setMp((short)(item.getMp() + mp));
        item.setAcc((short)(byte)(item.getAcc() + acc));
        item.setAvoid((short)(byte)(item.getAvoid() + avoid));
        MapleInventoryManipulator.removeFromSlot(this.getC(), MapleInventoryType.EQUIP, (short)1, (short)1, true);
        MapleInventoryManipulator.addFromDrop(this.getChar().getClient(), (IItem)item, false);
    }
    
    public String ??????????????????() {
        final StringBuilder name = new StringBuilder();
        final Equip item = (Equip)this.c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((short)1).copy();
        if (item.getUpgradeSlots() > 0) {
            name.append("????????????:#b" + (int)item.getUpgradeSlots() + "#k\r\n");
        }
        if (item.getWatk() > 0) {
            name.append("???????????????:#b" + (int)item.getWatk() + "#k\r\n");
        }
        if (item.getMatk() > 0) {
            name.append("???????????????:#b" + (int)item.getMatk() + "#k\r\n");
        }
        if (item.getWdef() > 0) {
            name.append("???????????????:#b" + (int)item.getWdef() + "#k\r\n");
        }
        if (item.getMdef() > 0) {
            name.append("???????????????:#b" + (int)item.getMdef() + "#k\r\n");
        }
        if (item.getStr() > 0) {
            name.append("??????:#b" + (int)item.getStr() + "#k\r\n");
        }
        if (item.getDex() > 0) {
            name.append("??????:#b" + (int)item.getDex() + "#k\r\n");
        }
        if (item.getLuk() > 0) {
            name.append("??????:#b" + (int)item.getLuk() + "#k\r\n");
        }
        if (item.getInt() > 0) {
            name.append("??????:#b" + (int)item.getInt() + "#k\r\n");
        }
        if (item.getHp() > 0) {
            name.append("HP:#b" + (int)item.getHp() + "#k\r\n");
        }
        if (item.getMp() > 0) {
            name.append("MP:#b" + (int)item.getMp() + "#k\r\n");
        }
        if (item.getAcc() > 0) {
            name.append("?????????:#b" + (int)item.getAcc() + "#k\r\n");
        }
        if (item.getAvoid() > 0) {
            name.append("?????????:#b" + (int)item.getAvoid() + "#k\r\n");
        }
        if (item.getSpeed() > 0) {
            name.append("????????????:#b" + (int)item.getSpeed() + "#k\r\n");
        }
        return name.toString();
    }
    
    public void ??????????????????(final int aa, final int bb, final int cc, final int dd) {
        final Equip item = (Equip)this.c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((short)aa).copy();
    }
    
    public void ????????????(final int id) {
        MapleShopFactory.getInstance().getShop(id).sendShop(this.c);
    }
    
    public int getLevel() {
        return this.getPlayer().getLevel();
    }
    
    public void ????????????(final int s) {
        this.c.getPlayer().setLevel((short)s);
    }
    
    public int ????????????() {
        return this.getPlayer().getLevel();
    }
    
    public void ??????() {
        final MapleCharacter player = this.c.getPlayer();
        this.c.sendPacket(MaplePacketCreator.getCharInfo(player));
        player.getMap().removePlayer(player);
        player.getMap().addPlayer(player);
    }
    
    public int ????????????() {
        return this.getPlayer().getMeso();
    }
    
    public int ????????????ID() {
        return this.c.getPlayer().getId();
    }
    
    public int ????????????() {
        return this.c.getPlayer().getCSPoints(1);
    }
    
    public int ???????????????() {
        return this.c.getPlayer().getCSPoints(2);
    }
    
    public int ????????????() {
        return this.getPlayer().getCurrentRep();
    }
    
    public int ????????????() {
        return this.getPlayer().getFamilyId();
    }
    
    public int ????????????() {
        return this.getPlayer().getSeniorId();
    }
    
    public void ?????????(final int s) {
        this.c.getPlayer().setCurrentRep(s);
    }
    
    public void ????????????() {
        if (this.getParty() != null) {
            this.c.getPlayer().getParty().getMembers().size();
        }
    }
    
    public int ????????????() {
        return this.c.getPlayer().getExp();
    }
    
    public int ??????????????????????????????() {
        return this.c.getPlayer().getMap().getAllMonstersThreadsafe().size();
    }
    
    public int ??????????????????????????????(final int a) {
        return this.getMap(a).getAllMonstersThreadsafe().size();
    }
    
    public int ??????????????????????????????() {
        return this.c.getPlayer().getMap().getCharactersSize();
    }
    
    public int ?????????(final int a) {
        return (int)Math.ceil(Math.random() * (double)a);
    }
    
    @Override
    public int ??????????????????() {
        return Calendar.getInstance().get(7);
    }
    
    public void ??????(final int lx, final String msg) {
        switch (lx) {
            case 1: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(11, this.c.getChannel(), "[" + ServerConfig.SERVERNAME + "] : " + msg));
                break;
            }
            case 2: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(12, this.c.getChannel(), "[" + ServerConfig.SERVERNAME + "] : " + msg));
                break;
            }
            case 3: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(3, this.c.getChannel(), "[" + ServerConfig.SERVERNAME + "] : " + msg));
                break;
            }
        }
    }
    
    public void ??????(final String text) {
        Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, text));
    }
    
    public void ??????(final int lx, final String msg) {
        switch (lx) {
            case 1: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(11, this.c.getChannel(), "[" + ServerConfig.SERVERNAME + "] : " + msg));
                break;
            }
            case 2: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(12, this.c.getChannel(), "[" + ServerConfig.SERVERNAME + "] : " + msg));
                break;
            }
            case 3: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(3, this.c.getChannel(), "[" + ServerConfig.SERVERNAME + "] : " + msg));
                break;
            }
            case 4: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(9, this.c.getChannel(), "[" + ServerConfig.SERVERNAME + "] : " + msg));
                break;
            }
            case 5: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(2, this.c.getChannel(), "[" + ServerConfig.SERVERNAME + "] : " + msg));
                break;
            }
        }
    }
    
    public void ??????????????????(final int lx, final String msg) {
        switch (lx) {
            case 1: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(11, this.c.getChannel(), "[???????????????]  : " + msg));
                break;
            }
            case 2: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(12, this.c.getChannel(), "[???????????????] : " + msg));
                break;
            }
            case 3: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(3, this.c.getChannel(), "[???????????????] :" + msg));
                break;
            }
            case 4: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(9, this.c.getChannel(), "[???????????????] : " + msg));
                break;
            }
            case 5: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(2, this.c.getChannel(), "[???????????????] :" + msg));
                break;
            }
        }
    }
    
    public static String SN?????????(final int id) {
        String data = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT Point as DATA FROM character7 WHERE Name = ? && channel = 1");
            ps.setInt(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("????????????ID??????????????? - ????????????????????????" + Ex);
        }
        if (data == null) {
            data = "????????????";
        }
        return data;
    }
    
    public static String SN?????????(final int id) {
        String data = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT Point as DATA FROM character7 WHERE Name = ? &&  channel = 2");
            ps.setInt(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("????????????ID??????????????? - ????????????????????????" + Ex);
        }
        if (data == null) {
            data = "????????????";
        }
        return data;
    }
    
    public static String SN?????????(final int id) {
        String data = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT Point as DATA FROM character7 WHERE Name = ? &&  channel = 3");
            ps.setInt(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("????????????ID??????????????? - ????????????????????????" + Ex);
        }
        if (data == null) {
            data = "????????????";
        }
        return data;
    }
    
    public static String SN?????????(final int id) {
        String data = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT Point as DATA FROM character7 WHERE Name = ? &&  channel = 4");
            ps.setInt(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("????????????ID??????????????? - ????????????????????????" + Ex);
        }
        if (data == null) {
            data = "????????????";
        }
        return data;
    }
    
    public static String SN?????????(final int id) {
        String data = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT Point as DATA FROM character7 WHERE Name = ? &&  channel = 5");
            ps.setInt(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("????????????ID??????????????? - ????????????????????????" + Ex);
        }
        if (data == null) {
            data = "????????????";
        }
        return data;
    }
    
    public static int ???????????????ID(final String id) {
        int data = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT id as DATA FROM characters WHERE name = ?");
            ps.setString(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getInt("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("?????????????????????ID?????? - ????????????????????????" + Ex);
        }
        return data;
    }
    
    public static String ??????ID?????????(final int id) {
        String data = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT name as DATA FROM characters WHERE id = ?");
            ps.setInt(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("????????????ID??????????????? - ????????????????????????" + Ex);
        }
        if (data == null) {
            data = "????????????";
        }
        return data;
    }
    
    public static int ?????????????????????ID(final String id) {
        int data = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT accountid as DATA FROM characters WHERE name = ?");
            ps.setString(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getInt("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("?????????????????????ID?????? - ????????????????????????" + Ex);
        }
        return data;
    }
    
    public static String IP?????????(final String id) {
        String data = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT name as DATA FROM accounts WHERE SessionIP = ?");
            ps.setString(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                    return data;
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("????????????ID??????????????? - ????????????????????????" + Ex);
        }
        if (data == null) {
            data = "????????????";
        }
        return data;
    }
    
    public static String MAC?????????(final String id) {
        String data = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT name as DATA FROM accounts WHERE macs = ?");
            ps.setString(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("????????????ID??????????????? - ????????????????????????" + Ex);
        }
        if (data == null) {
            data = "????????????";
        }
        return data;
    }
    
    public static String ??????ID?????????(final String id) {
        String data = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT name as DATA FROM accounts WHERE id = ?");
            ps.setString(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("?????????????????????ID?????? - ????????????????????????" + Ex);
        }
        return data;
    }
    
    public static String ??????ID?????????(final int id) {
        String data = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT loggedin as DATA FROM accounts WHERE id = ?");
            ps.setInt(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("?????????????????????ID?????? - ????????????????????????" + Ex);
        }
        return data;
    }
    
    public static String ?????????????????????(final String id) {
        String data = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT level as DATA FROM characters WHERE name = ?");
            ps.setString(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("?????????????????????ID?????? - ????????????????????????" + Ex);
        }
        if (data == null) {
            data = "????????????";
        }
        return data;
    }
    
    public static String ????????????????????????(final int itemid) {
        String data = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT dropperid as DATA FROM drop_data WHERE itemid = ?");
            ps.setInt(1, itemid);
            try (final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("???????????????????????????????????? - ????????????????????????" + Ex);
        }
        return data;
    }
    
    public static String ??????????????????(final int guildId) {
        String data = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT name as DATA FROM guilds WHERE guildid = ?");
            ps.setInt(1, guildId);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("???????????????????????? - ????????????????????????" + Ex);
        }
        return data;
    }
    
    public static String ??????????????????????????????() {
        String name = "";
        String level = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT `name`, `level` FROM characters WHERE gm = 0 ORDER BY `level` DESC LIMIT 1");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("name");
                    level = rs.getString("level");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("???????????????????????? - ????????????????????????" + Ex);
        }
        return String.format("%s", name);
    }
    
    public int ??????ID???????????????(final int id) {
        int data = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT cid as DATA FROM hire WHERE cid = ?");
            ps.setInt(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getInt("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("?????????????????????ID?????????");
        }
        return data;
    }
    
    public static int ??????ID?????????ID(final int id) {
        int data = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT accountid as DATA FROM characters WHERE id = ?");
            ps.setInt(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getInt("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("?????????????????????ID?????????");
        }
        return data;
    }
    
    public static String ??????ID?????????QQ(final int id) {
        String data = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT qq as DATA FROM accounts WHERE id = ?");
            ps.setInt(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("??????ID??????????????????");
        }
        return data;
    }
    
    public int getzs() {
        return this.getPlayer().getzs();
    }
    
    public void setzs(final int set) {
        this.getPlayer().setzs(set);
    }
    
    public void gainzs(final int gain) {
        this.getPlayer().gainzs(gain);
    }
    
    public int getjf() {
        return this.getPlayer().getjf();
    }
    
    public void setjf(final int set) {
        this.getPlayer().setjf(set);
    }
    
    public void gainjf(final int gain) {
        this.getPlayer().gainjf(gain);
    }
    
    public int getzdjf() {
        return this.getPlayer().getzdjf();
    }
    
    public void setzdjf(final int set) {
        this.getPlayer().setzdjf(set);
    }
    
    public void gainzdjf(final int gain) {
        this.getPlayer().gainzdjf(gain);
    }
    
    public int getrwjf() {
        return this.getPlayer().getrwjf();
    }
    
    public void setrwjf(final int set) {
        this.getPlayer().setrwjf(set);
    }
    
    public void gainrwjf(final int gain) {
        this.getPlayer().gainrwjf(gain);
    }
    
    public int getcz() {
        return this.getPlayer().getcz();
    }
    
    public void setcz(final int set) {
        this.getPlayer().setcz(set);
    }
    
    public void gaincz(final int gain) {
        this.getPlayer().gaincz(gain);
    }
    
    public int getdy() {
        return this.getPlayer().getdy();
    }
    
    public void setdy(final int set) {
        this.getPlayer().setdy(set);
    }
    
    public void gaindy(final int gain) {
        this.getPlayer().gaindy(gain);
    }
    
    public int getrmb() {
        return this.getPlayer().getrmb();
    }
    
    public void setrmb(final int set) {
        this.getPlayer().setrmb(set);
    }
    
    public void gainrmb(final int gain) {
        this.getPlayer().gainrmb(gain);
    }
    
    public int getyb() {
        return this.getPlayer().getyb();
    }
    
    public void setyb(final int set) {
        this.getPlayer().setyb(set);
    }
    
    public void gainyb(final int gain) {
        this.getPlayer().gainyb(gain);
    }
    
    public int getplayerPoints() {
        return this.getPlayer().getplayerPoints();
    }
    
    public void setplayerPoints(final int set) {
        this.getPlayer().setplayerPoints(set);
    }
    
    public void gainplayerPoints(final int gain) {
        this.getPlayer().gainplayerPoints(gain);
    }
    
    public int getplayerEnergy() {
        return this.getPlayer().getplayerEnergy();
    }
    
    public void setplayerEnergy(final int set) {
        this.getPlayer().setplayerEnergy(set);
    }
    
    public void gainplayerEnergy(final int gain) {
        this.getPlayer().gainplayerEnergy(gain);
    }
    
    public int getjf1() {
        return this.getPlayer().getjf1();
    }
    
    public void setjf1(final int set) {
        this.getPlayer().setjf1(set);
    }
    
    public void gainjf1(final int gain) {
        this.getPlayer().gainjf1(gain);
    }
    
    public int getjf2() {
        return this.getPlayer().getjf2();
    }
    
    public void setjf2(final int set) {
        this.getPlayer().setjf2(set);
    }
    
    public void gainjf2(final int gain) {
        this.getPlayer().gainjf2(gain);
    }
    
    public int getjf3() {
        return this.getPlayer().getjf3();
    }
    
    public void setjf3(final int set) {
        this.getPlayer().setjf3(set);
    }
    
    public void gainjf3(final int gain) {
        this.getPlayer().gainjf3(gain);
    }
    
    public int getjf4() {
        return this.getPlayer().getjf4();
    }
    
    public void setjf4(final int set) {
        this.getPlayer().setjf4(set);
    }
    
    public void gainjf4(final int gain) {
        this.getPlayer().gainjf4(gain);
    }
    
    public int getjf5() {
        return this.getPlayer().getjf5();
    }
    
    public void setjf5(final int set) {
        this.getPlayer().setjf5(set);
    }
    
    public void gainjf5(final int gain) {
        this.getPlayer().gainjf5(gain);
    }
    
    public int getjf6() {
        return this.getPlayer().getjf6();
    }
    
    public void setjf6(final int set) {
        this.getPlayer().setjf6(set);
    }
    
    public void gainjf6(final int gain) {
        this.getPlayer().gainjf6(gain);
    }
    
    public int getjf7() {
        return this.getPlayer().getjf7();
    }
    
    public void setjf7(final int set) {
        this.getPlayer().setjf7(set);
    }
    
    public void gainjf7(final int gain) {
        this.getPlayer().gainjf7(gain);
    }
    
    public int getjf8() {
        return this.getPlayer().getjf8();
    }
    
    public void setjf8(final int set) {
        this.getPlayer().setjf8(set);
    }
    
    public void gainjf8(final int gain) {
        this.getPlayer().gainjf8(gain);
    }
    
    public int getjf9() {
        return this.getPlayer().getjf9();
    }
    
    public void setjf9(final int set) {
        this.getPlayer().setjf9(set);
    }
    
    public void gainjf9(final int gain) {
        this.getPlayer().gainjf9(gain);
    }
    
    public int getjf10() {
        return this.getPlayer().getjf10();
    }
    
    public void setjf10(final int set) {
        this.getPlayer().setjf10(set);
    }
    
    public void gainjf10(final int gain) {
        this.getPlayer().gainjf10(gain);
    }
    
    public void ????????????() {
        this.c.getPlayer().saveToDB(false, false);
    }
    
    public void ??????ID() {
        this.c.getPlayer().getId();
    }
    
    public void ????????????() {
        try {
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                    if (chr == null) {
                        continue;
                    }
                    chr.saveToDB(false, false);
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public static void ????????????(final int id, final int key) throws SQLException {
        try {
            final Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ps = con.prepareStatement("INSERT INTO cashshop_modified_items (itemid, meso) VALUES (?, ?)");
            ps.setInt(1, id);
            ps.setInt(2, key);
        }
        catch (SQLException ex) {
            Logger.getLogger(NPCConversationManager.class.getName()).log(Level.SEVERE, null, (Throwable)ex);
        }
    }
    
    public void ??????????????????(final int id, final int key, final int type, final int action, final byte level) {
        final ISkill skill = SkillFactory.getSkill(action);
        this.c.getPlayer().changeSkillLevel(skill, level, skill.getMaxLevel());
        this.c.getPlayer().changeKeybinding(key, (byte)type, action);
        this.c.sendPacket(MaplePacketCreator.getKeymap(this.c.getPlayer().getKeyLayout()));
    }
    
    public void ???????????????(final int id, final int key, final int type, final int action, final byte level) throws SQLException {
        final ISkill skill = SkillFactory.getSkill(action);
        this.c.getPlayer().changeSkillLevel(skill, level, skill.getMaxLevel());
        this.c.getPlayer().dropMessage(1, "<??????>\r\n5??????????????????????????????1????????????????????????");
        this.c.getPlayer().saveToDB(false, false);
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000L);
                    c.getPlayer().getClient().getSession().close();
                    Thread.sleep(2000L);
                    String SqlStr = "";
                    final Connection con = DatabaseConnection.getConnection();
                    PreparedStatement ps = null;
                    SqlStr = "SELECT * from keymap where characterid=" + id + " and keye=" + key + "";
                    ps = con.prepareStatement(SqlStr);
                    final ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        PreparedStatement psu = null;
                        SqlStr = "UPDATE keymap set type=" + type + ",action=" + action + " where characterid=" + id + " and keye=" + key + "";
                        psu = con.prepareStatement(SqlStr);
                        psu.execute();
                        psu.close();
                    }
                    else {
                        PreparedStatement psu = null;
                        psu = con.prepareStatement("INSERT INTO keymap (characterid, `keye`, `type`, `action`) VALUES (?, ?, ?, ?)");
                        psu.setInt(1, id);
                        psu.setInt(2, key);
                        psu.setInt(3, type);
                        psu.setInt(4, action);
                        psu.executeUpdate();
                        psu.close();
                    }
                    rs.close();
                    ps.close();
                }
                catch (InterruptedException ex2) {}
                catch (SQLException ex) {
                    Logger.getLogger(NPCConversationManager.class.getName()).log(Level.SEVERE, null, (Throwable)ex);
                }
            }
        }.start();
    }
    
    public void ????????????(final int id) {
        PreparedStatement ps1 = null;
        try {
            ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM characters WHERE id = ?");
        }
        catch (SQLException ex) {}
        final String sqlstr = " delete from characters where id =" + id + "";
        try {
            ps1.executeUpdate(sqlstr);
            this.c.getPlayer().dropMessage(1, "?????????????????????");
        }
        catch (SQLException ex2) {}
    }
    
    public void ????????????() {
        System.currentTimeMillis();
    }
    
    public void ???????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().??????()));
    }
    
    public void ???????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().??????()));
    }
    
    public void ???????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().??????()));
    }
    
    public void ??????????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().?????????()));
    }
    
    public void ???????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().??????()));
    }
    
    public void ??????????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().?????????()));
    }
    
    public void ??????????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().?????????()));
    }
    
    public void ??????????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().?????????()));
    }
    
    public void ??????????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().?????????()));
    }
    
    public void ?????????????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().????????????()));
    }
    
    public void ?????????????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().????????????()));
    }
    
    public void ????????????????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().???????????????()));
    }
    
    public void ?????????????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().????????????()));
    }
    
    public void ?????????????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().????????????()));
    }
    
    public void ????????????????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().???????????????()));
    }
    
    public void ???????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().??????()));
    }
    
    public void ???????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().??????()));
    }
    
    public void ???????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().??????()));
    }
    
    public void ???????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().??????()));
    }
    
    public void ???????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().??????()));
    }
    
    public void ??????????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().?????????()));
    }
    
    public void ??????????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().?????????()));
    }
    
    public void ???????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().??????()));
    }
    
    public void ???????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().??????()));
    }
    
    public void ???????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().??????()));
    }
    
    public void ??????????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().?????????()));
    }
    
    public void ???????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().??????()));
    }
    
    public void ???????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().??????()));
    }
    
    public void ??????????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().?????????()));
    }
    
    public void ???????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().??????()));
    }
    
    public void ???????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().??????()));
    }
    
    public void ???????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().??????()));
    }
    
    public void ?????????????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().????????????()));
    }
    
    public void ??????????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().?????????()));
    }
    
    public void ???????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().??????()));
    }
    
    public void ???????????????() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().??????()));
    }
    
    public int ??????????????????(final int ??????, final int ??????) {
        int count = 0;
        try {
            if (?????? <= 0 || ?????? <= 0) {
                return 0;
            }
            if (?????? == 1 || ?????? == 2) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        mch.modifyCSPoints(??????, ??????);
                        String cash = null;
                        if (?????? == 1) {
                            cash = "??????";
                        }
                        else if (?????? == 2) {
                            cash = "?????????";
                        }
                        ++count;
                    }
                }
            }
            else if (?????? == 3) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        mch.gainMeso(??????, true);
                        ++count;
                    }
                }
            }
            else if (?????? == 4) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        mch.gainExp(??????, true, false, true);
                        ++count;
                    }
                }
            }
        }
        catch (Exception e) {
            this.c.getPlayer().dropMessage("???????????????????????????" + e.getMessage());
        }
        return count;
    }
    
    public int ????????????????????????(final int ??????, final int ??????) {
        int count = 0;
        final int mapId = this.c.getPlayer().getMapId();
        try {
            if (?????? <= 0 || ?????? <= 0) {
                return 0;
            }
            if (?????? == 1 || ?????? == 2) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        if (mch.getMapId() != mapId) {
                            continue;
                        }
                        mch.modifyCSPoints(??????, ??????);
                        String cash = null;
                        if (?????? == 1) {
                            cash = "??????";
                        }
                        else if (?????? == 2) {
                            cash = "?????????";
                        }
                        ++count;
                    }
                }
            }
            else if (?????? == 3) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        if (mch.getMapId() != mapId) {
                            continue;
                        }
                        mch.gainMeso(??????, true);
                        ++count;
                    }
                }
            }
            else if (?????? == 4) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        if (mch.getMapId() != mapId) {
                            continue;
                        }
                        mch.gainExp(??????, true, false, true);
                        ++count;
                    }
                }
            }
        }
        catch (Exception e) {
            this.c.getPlayer().dropMessage("?????????????????????????????????" + e.getMessage());
        }
        return count;
    }
    
    public int ????????????????????????(final int ??????, final int ??????) {
        int count = 0;
        final int chlId = this.c.getPlayer().getMap().getChannel();
        try {
            if (?????? <= 0 || ?????? <= 0) {
                return 0;
            }
            if (?????? == 1 || ?????? == 2) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    if (cserv1.getChannel() != chlId) {
                        continue;
                    }
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        mch.modifyCSPoints(??????, ??????);
                        String cash = null;
                        if (?????? == 1) {
                            cash = "??????";
                        }
                        else if (?????? == 2) {
                            cash = "?????????";
                        }
                        ++count;
                    }
                }
            }
            else if (?????? == 3) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    if (cserv1.getChannel() != chlId) {
                        continue;
                    }
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        mch.gainMeso(??????, true);
                        ++count;
                    }
                }
            }
            else if (?????? == 4) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    if (cserv1.getChannel() != chlId) {
                        continue;
                    }
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        mch.gainExp(??????, true, false, true);
                        ++count;
                    }
                }
            }
        }
        catch (Exception e) {
            this.c.getPlayer().dropMessage("?????????????????????????????????" + e.getMessage());
        }
        return count;
    }
    
    public int ??????????????????(final int ??????ID, final int ??????, final int ??????, final int ??????, final int ??????, final int ??????, final int HP, final int MP, final int ???????????????, final String ???????????????, final int ????????????, final String ??????????????????, final int ?????????, final int ?????????, final int ????????????, final int ????????????) {
        int count = 0;
        try {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(??????ID);
            for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    if (?????? >= 0) {
                        if (!MapleInventoryManipulator.checkSpace(mch.getClient(), ??????ID, ??????, "")) {
                            return 0;
                        }
                        if ((type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(??????ID) && !GameConstants.isBullet(??????ID)) || (type.equals(MapleInventoryType.CASH) && ??????ID >= 5000000 && ??????ID <= 5000100)) {
                            final Equip item = (Equip)(Equip)ii.getEquipById(??????ID);
                            if (ii.isCash(??????ID)) {
                                item.setUniqueId(1);
                            }
                            if (?????? > 0 && ?????? <= 32767) {
                                item.setStr((short)??????);
                            }
                            if (?????? > 0 && ?????? <= 32767) {
                                item.setDex((short)??????);
                            }
                            if (?????? > 0 && ?????? <= 32767) {
                                item.setInt((short)??????);
                            }
                            if (?????? > 0 && ?????? <= 32767) {
                                item.setLuk((short)??????);
                            }
                            if (????????? > 0 && ????????? <= 32767) {
                                item.setWatk((short)?????????);
                            }
                            if (????????? > 0 && ????????? <= 32767) {
                                item.setMatk((short)?????????);
                            }
                            if (???????????? > 0 && ???????????? <= 32767) {
                                item.setWdef((short)????????????);
                            }
                            if (???????????? > 0 && ???????????? <= 32767) {
                                item.setMdef((short)????????????);
                            }
                            if (HP > 0 && HP <= 30000) {
                                item.setHp((short)HP);
                            }
                            if (MP > 0 && MP <= 30000) {
                                item.setMp((short)MP);
                            }
                            if ("????????????".equals(??????????????????)) {
                                byte flag = item.getFlag();
                                if (item.getType() == MapleInventoryType.EQUIP.getType()) {
                                    flag |= (byte)ItemFlag.KARMA_EQ.getValue();
                                }
                                else {
                                    flag |= (byte)ItemFlag.KARMA_USE.getValue();
                                }
                                item.setFlag(flag);
                            }
                            if (???????????? > 0) {
                                item.setExpiration(System.currentTimeMillis() + (long)(???????????? * 24 * 60 * 60 * 1000));
                            }
                            if (??????????????? > 0) {
                                item.setUpgradeSlots((byte)???????????????);
                            }
                            if (??????????????? != null) {
                                item.setOwner(???????????????);
                            }
                            final String name = ii.getName(??????ID);
                            if (??????ID / 10000 == 114 && name != null && name.length() > 0) {
                                final String msg = "?????????????????? <" + name + ">";
                                mch.getClient().getPlayer().dropMessage(5, msg);
                            }
                            MapleInventoryManipulator.addbyItem(mch.getClient(), item.copy());
                        }
                        else {
                            MapleInventoryManipulator.addById(mch.getClient(), ??????ID, (short)??????, "", null, (long)????????????, (byte)0);
                        }
                    }
                    else {
                        MapleInventoryManipulator.removeById(mch.getClient(), GameConstants.getInventoryType(??????ID), ??????ID, -??????, true, false);
                    }
                    mch.getClient().sendPacket(MaplePacketCreator.getShowItemGain(??????ID, (short)??????, true));
                    ++count;
                }
            }
        }
        catch (Exception e) {
            this.c.getPlayer().dropMessage("???????????????????????????" + e.getMessage());
        }
        return count;
    }
    
    public int ????????????????????????(final int ??????ID, final int ??????, final int ??????, final int ??????, final int ??????, final int ??????, final int HP, final int MP, final int ???????????????, final String ???????????????, final int ????????????, final String ??????????????????, final int ?????????, final int ?????????, final int ????????????, final int ????????????) {
        int count = 0;
        final int mapId = this.c.getPlayer().getMapId();
        try {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(??????ID);
            for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    if (mch.getMapId() != mapId) {
                        continue;
                    }
                    if (?????? >= 0) {
                        if (!MapleInventoryManipulator.checkSpace(mch.getClient(), ??????ID, ??????, "")) {
                            return 0;
                        }
                        if ((type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(??????ID) && !GameConstants.isBullet(??????ID)) || (type.equals(MapleInventoryType.CASH) && ??????ID >= 5000000 && ??????ID <= 5000100)) {
                            final Equip item = (Equip)(Equip)ii.getEquipById(??????ID);
                            if (ii.isCash(??????ID)) {
                                item.setUniqueId(1);
                            }
                            if (?????? > 0 && ?????? <= 32767) {
                                item.setStr((short)??????);
                            }
                            if (?????? > 0 && ?????? <= 32767) {
                                item.setDex((short)??????);
                            }
                            if (?????? > 0 && ?????? <= 32767) {
                                item.setInt((short)??????);
                            }
                            if (?????? > 0 && ?????? <= 32767) {
                                item.setLuk((short)??????);
                            }
                            if (????????? > 0 && ????????? <= 32767) {
                                item.setWatk((short)?????????);
                            }
                            if (????????? > 0 && ????????? <= 32767) {
                                item.setMatk((short)?????????);
                            }
                            if (???????????? > 0 && ???????????? <= 32767) {
                                item.setWdef((short)????????????);
                            }
                            if (???????????? > 0 && ???????????? <= 32767) {
                                item.setMdef((short)????????????);
                            }
                            if (HP > 0 && HP <= 30000) {
                                item.setHp((short)HP);
                            }
                            if (MP > 0 && MP <= 30000) {
                                item.setMp((short)MP);
                            }
                            if ("????????????".equals(??????????????????)) {
                                byte flag = item.getFlag();
                                if (item.getType() == MapleInventoryType.EQUIP.getType()) {
                                    flag |= (byte)ItemFlag.KARMA_EQ.getValue();
                                }
                                else {
                                    flag |= (byte)ItemFlag.KARMA_USE.getValue();
                                }
                                item.setFlag(flag);
                            }
                            if (???????????? > 0) {
                                item.setExpiration(System.currentTimeMillis() + (long)(???????????? * 24 * 60 * 60 * 1000));
                            }
                            if (??????????????? > 0) {
                                item.setUpgradeSlots((byte)???????????????);
                            }
                            if (??????????????? != null) {
                                item.setOwner(???????????????);
                            }
                            final String name = ii.getName(??????ID);
                            if (??????ID / 10000 == 114 && name != null && name.length() > 0) {
                                final String msg = "?????????????????? <" + name + ">";
                                mch.getClient().getPlayer().dropMessage(5, msg);
                            }
                            MapleInventoryManipulator.addbyItem(mch.getClient(), item.copy());
                        }
                        else {
                            MapleInventoryManipulator.addById(mch.getClient(), ??????ID, (short)??????, "", null, (long)????????????, (byte)0);
                        }
                    }
                    else {
                        MapleInventoryManipulator.removeById(mch.getClient(), GameConstants.getInventoryType(??????ID), ??????ID, -??????, true, false);
                    }
                    mch.getClient().sendPacket(MaplePacketCreator.getShowItemGain(??????ID, (short)??????, true));
                    ++count;
                }
            }
        }
        catch (Exception e) {
            this.c.getPlayer().dropMessage("?????????????????????????????????" + e.getMessage());
        }
        return count;
    }
    
    public int ????????????????????????(final int ??????ID, final int ??????, final int ??????, final int ??????, final int ??????, final int ??????, final int HP, final int MP, final int ???????????????, final String ???????????????, final int ????????????, final String ??????????????????, final int ?????????, final int ?????????, final int ????????????, final int ????????????) {
        int count = 0;
        final int chlId = this.c.getPlayer().getMap().getChannel();
        try {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(??????ID);
            for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                if (cserv1.getChannel() != chlId) {
                    continue;
                }
                for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    if (?????? >= 0) {
                        if (!MapleInventoryManipulator.checkSpace(mch.getClient(), ??????ID, ??????, "")) {
                            return 0;
                        }
                        if ((type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(??????ID) && !GameConstants.isBullet(??????ID)) || (type.equals(MapleInventoryType.CASH) && ??????ID >= 5000000 && ??????ID <= 5000100)) {
                            final Equip item = (Equip)(Equip)ii.getEquipById(??????ID);
                            if (ii.isCash(??????ID)) {
                                item.setUniqueId(1);
                            }
                            if (?????? > 0 && ?????? <= 32767) {
                                item.setStr((short)??????);
                            }
                            if (?????? > 0 && ?????? <= 32767) {
                                item.setDex((short)??????);
                            }
                            if (?????? > 0 && ?????? <= 32767) {
                                item.setInt((short)??????);
                            }
                            if (?????? > 0 && ?????? <= 32767) {
                                item.setLuk((short)??????);
                            }
                            if (????????? > 0 && ????????? <= 32767) {
                                item.setWatk((short)?????????);
                            }
                            if (????????? > 0 && ????????? <= 32767) {
                                item.setMatk((short)?????????);
                            }
                            if (???????????? > 0 && ???????????? <= 32767) {
                                item.setWdef((short)????????????);
                            }
                            if (???????????? > 0 && ???????????? <= 32767) {
                                item.setMdef((short)????????????);
                            }
                            if (HP > 0 && HP <= 30000) {
                                item.setHp((short)HP);
                            }
                            if (MP > 0 && MP <= 30000) {
                                item.setMp((short)MP);
                            }
                            if ("????????????".equals(??????????????????)) {
                                byte flag = item.getFlag();
                                if (item.getType() == MapleInventoryType.EQUIP.getType()) {
                                    flag |= (byte)ItemFlag.KARMA_EQ.getValue();
                                }
                                else {
                                    flag |= (byte)ItemFlag.KARMA_USE.getValue();
                                }
                                item.setFlag(flag);
                            }
                            if (???????????? > 0) {
                                item.setExpiration(System.currentTimeMillis() + (long)(???????????? * 24 * 60 * 60 * 1000));
                            }
                            if (??????????????? > 0) {
                                item.setUpgradeSlots((byte)???????????????);
                            }
                            if (??????????????? != null) {
                                item.setOwner(???????????????);
                            }
                            final String name = ii.getName(??????ID);
                            if (??????ID / 10000 == 114 && name != null && name.length() > 0) {
                                final String msg = "?????????????????? <" + name + ">";
                                mch.getClient().getPlayer().dropMessage(5, msg);
                            }
                            MapleInventoryManipulator.addbyItem(mch.getClient(), item.copy());
                        }
                        else {
                            MapleInventoryManipulator.addById(mch.getClient(), ??????ID, (short)??????, "", null, (long)????????????, (byte)0);
                        }
                    }
                    else {
                        MapleInventoryManipulator.removeById(mch.getClient(), GameConstants.getInventoryType(??????ID), ??????ID, -??????, true, false);
                    }
                    mch.getClient().sendPacket(MaplePacketCreator.getShowItemGain(??????ID, (short)??????, true));
                    ++count;
                }
            }
        }
        catch (Exception e) {
            this.c.getPlayer().dropMessage("?????????????????????????????????" + e.getMessage());
        }
        return count;
    }
    
    public int ??????????????????????????????????????????(final int destMapId, final Boolean includeSelf) {
        int count = 0;
        final int myMapId = this.c.getPlayer().getMapId();
        final int myId = this.c.getPlayer().getId();
        try {
            final MapleMap tomap = this.getMapFactory().getMap(destMapId);
            final MapleMap frommap = this.getMapFactory().getMap(myMapId);
            final List<MapleCharacter> list = frommap.getCharactersThreadsafe();
            if (tomap != null && frommap != null && list != null && frommap.getCharactersSize() > 0) {
                for (final MapleMapObject mmo : list) {
                    final MapleCharacter chr = (MapleCharacter)mmo;
                    if (chr.getId() == myId) {
                        if (!(boolean)includeSelf) {
                            continue;
                        }
                        chr.changeMap(tomap, tomap.getPortal(0));
                        ++count;
                    }
                    else {
                        chr.changeMap(tomap, tomap.getPortal(0));
                        ++count;
                    }
                }
            }
        }
        catch (Exception e) {
            this.c.getPlayer().dropMessage("???????????????????????????????????????????????????" + e.getMessage());
        }
        return count;
    }
    
    public int ???????????????????????????(final Boolean includeSelf) {
        int count = 0;
        final int myMapId = this.c.getPlayer().getMapId();
        final int myId = this.c.getPlayer().getId();
        try {
            final MapleMap frommap = this.getMapFactory().getMap(myMapId);
            final List<MapleCharacter> list = frommap.getCharactersThreadsafe();
            if (frommap != null && list != null && frommap.getCharactersSize() > 0) {
                for (final MapleMapObject mmo : list) {
                    if (mmo != null) {
                        final MapleCharacter chr = (MapleCharacter)mmo;
                        if (chr.getId() == myId) {
                            if (!(boolean)includeSelf) {
                                continue;
                            }
                            chr.setHp(0);
                            chr.updateSingleStat(MapleStat.HP, 0);
                            ++count;
                        }
                        else {
                            chr.setHp(0);
                            chr.updateSingleStat(MapleStat.HP, 0);
                            ++count;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            this.c.getPlayer().dropMessage("????????????????????????????????????" + e.getMessage());
        }
        return count;
    }
    
    public int ???????????????????????????(final Boolean includeSelf) {
        int count = 0;
        final int myMapId = this.c.getPlayer().getMapId();
        final int myId = this.c.getPlayer().getId();
        try {
            final MapleMap frommap = this.getMapFactory().getMap(myMapId);
            final List<MapleCharacter> list = frommap.getCharactersThreadsafe();
            if (frommap != null && list != null && frommap.getCharactersSize() > 0) {
                for (final MapleMapObject mmo : list) {
                    if (mmo != null) {
                        final MapleCharacter chr = (MapleCharacter)mmo;
                        if (chr.getId() == myId) {
                            if (!(boolean)includeSelf) {
                                continue;
                            }
                            chr.getStat().setHp((int)chr.getStat().getMaxHp());
                            chr.updateSingleStat(MapleStat.HP, (int)chr.getStat().getMaxHp());
                            chr.getStat().setMp((int)chr.getStat().getMaxMp());
                            chr.updateSingleStat(MapleStat.MP, (int)chr.getStat().getMaxMp());
                            chr.dispelDebuffs();
                            ++count;
                        }
                        else {
                            chr.getStat().setHp((int)chr.getStat().getMaxHp());
                            chr.updateSingleStat(MapleStat.HP, (int)chr.getStat().getMaxHp());
                            chr.getStat().setMp((int)chr.getStat().getMaxMp());
                            chr.updateSingleStat(MapleStat.MP, (int)chr.getStat().getMaxMp());
                            chr.dispelDebuffs();
                            ++count;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            this.c.getPlayer().dropMessage("????????????????????????????????????" + e.getMessage());
        }
        return count;
    }
    
    public void ????????????(final String charName) {
        for (final ChannelServer chl : ChannelServer.getAllInstances()) {
            for (final MapleCharacter chr : chl.getPlayerStorage().getAllCharacters()) {
                if (chr.getName() == charName) {
                    this.c.getPlayer().changeMap(chr.getMapId());
                }
            }
        }
    }
    
    public int ????????????????????????(int ??????ID, final int ??????ID, final int ??????, final int ??????, final int ??????, final int ??????, final int ??????, final int HP, final int MP, final int ???????????????, final String ???????????????, final int ????????????, final String ??????????????????, final int ?????????, final int ?????????, final int ????????????, final int ????????????) {
        int count = 0;
        if (??????ID < 1) {
            ??????ID = this.c.getPlayer().getMapId();
        }
        try {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(??????ID);
            final MapleMap frommap = this.getMapFactory().getMap(??????ID);
            final List<MapleCharacter> list = frommap.getCharactersThreadsafe();
            if (list != null && frommap.getCharactersSize() > 0) {
                for (final MapleMapObject mmo : list) {
                    if (mmo != null) {
                        final MapleCharacter chr = (MapleCharacter)mmo;
                        if (?????? >= 0) {
                            if (!MapleInventoryManipulator.checkSpace(chr.getClient(), ??????ID, ??????, "")) {
                                return 0;
                            }
                            if ((type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(??????ID) && !GameConstants.isBullet(??????ID)) || (type.equals(MapleInventoryType.CASH) && ??????ID >= 5000000 && ??????ID <= 5000100)) {
                                final Equip item = (Equip)(Equip)ii.getEquipById(??????ID);
                                if (ii.isCash(??????ID)) {
                                    item.setUniqueId(1);
                                }
                                if (?????? > 0 && ?????? <= 32767) {
                                    item.setStr((short)??????);
                                }
                                if (?????? > 0 && ?????? <= 32767) {
                                    item.setDex((short)??????);
                                }
                                if (?????? > 0 && ?????? <= 32767) {
                                    item.setInt((short)??????);
                                }
                                if (?????? > 0 && ?????? <= 32767) {
                                    item.setLuk((short)??????);
                                }
                                if (????????? > 0 && ????????? <= 32767) {
                                    item.setWatk((short)?????????);
                                }
                                if (????????? > 0 && ????????? <= 32767) {
                                    item.setMatk((short)?????????);
                                }
                                if (???????????? > 0 && ???????????? <= 32767) {
                                    item.setWdef((short)????????????);
                                }
                                if (???????????? > 0 && ???????????? <= 32767) {
                                    item.setMdef((short)????????????);
                                }
                                if (HP > 0 && HP <= 30000) {
                                    item.setHp((short)HP);
                                }
                                if (MP > 0 && MP <= 30000) {
                                    item.setMp((short)MP);
                                }
                                if ("????????????".equals(??????????????????)) {
                                    byte flag = item.getFlag();
                                    if (item.getType() == MapleInventoryType.EQUIP.getType()) {
                                        flag |= (byte)ItemFlag.KARMA_EQ.getValue();
                                    }
                                    else {
                                        flag |= (byte)ItemFlag.KARMA_USE.getValue();
                                    }
                                    item.setFlag(flag);
                                }
                                if (???????????? > 0) {
                                    item.setExpiration(System.currentTimeMillis() + (long)(???????????? * 24 * 60 * 60 * 1000));
                                }
                                if (??????????????? > 0) {
                                    item.setUpgradeSlots((byte)???????????????);
                                }
                                if (??????????????? != null) {
                                    item.setOwner(???????????????);
                                }
                                final String name = ii.getName(??????ID);
                                if (??????ID / 10000 == 114 && name != null && name.length() > 0) {
                                    final String msg = "?????????????????? <" + name + ">";
                                    chr.dropMessage(5, msg);
                                }
                                MapleInventoryManipulator.addbyItem(chr.getClient(), item.copy());
                            }
                            else {
                                MapleInventoryManipulator.addById(chr.getClient(), ??????ID, (short)??????, "", null, (long)????????????, (byte)0);
                            }
                        }
                        else {
                            MapleInventoryManipulator.removeById(chr.getClient(), GameConstants.getInventoryType(??????ID), ??????ID, -??????, true, false);
                        }
                        chr.getClient().sendPacket(MaplePacketCreator.getShowItemGain(??????ID, (short)??????, true));
                        ++count;
                    }
                }
            }
        }
        catch (Exception e) {
            this.c.getPlayer().dropMessage("?????????????????????????????????" + e.getMessage());
        }
        return count;
    }
    
    public int ????????????????????????(final int ??????ID, final int ??????ID, final int ??????) {
        return this.????????????????????????(??????ID, ??????ID, ??????, 0, 0, 0, 0, 0, 0, 0, "", 0, "", 0, 0, 0, 0);
    }
    
    public int ????????????????????????(int ??????ID, final int ??????, final int ??????) {
        int count = 0;
        final String name = this.c.getPlayer().getName();
        if (??????ID < 1) {
            ??????ID = this.c.getPlayer().getMapId();
        }
        try {
            if (?????? <= 0 || ?????? <= 0) {
                return 0;
            }
            final MapleMap frommap = this.getMapFactory().getMap(??????ID);
            final List<MapleCharacter> list = frommap.getCharactersThreadsafe();
            if (list != null && frommap.getCharactersSize() > 0) {
                if (?????? == 1 || ?????? == 2) {
                    for (final MapleMapObject mmo : list) {
                        if (mmo != null) {
                            final MapleCharacter chr = (MapleCharacter)mmo;
                            chr.modifyCSPoints(??????, ??????);
                            String cash = null;
                            if (?????? == 1) {
                                cash = "??????";
                            }
                            else if (?????? == 2) {
                                cash = "?????????";
                            }
                            ++count;
                        }
                    }
                }
                else if (?????? == 3) {
                    for (final MapleMapObject mmo : list) {
                        if (mmo != null) {
                            final MapleCharacter chr = (MapleCharacter)mmo;
                            chr.gainMeso(??????, true);
                            ++count;
                        }
                    }
                }
                else if (?????? == 4) {
                    for (final MapleMapObject mmo : list) {
                        if (mmo != null) {
                            final MapleCharacter chr = (MapleCharacter)mmo;
                            chr.gainExp(??????, true, false, true);
                            ++count;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            this.c.getPlayer().dropMessage("?????????????????????????????????" + e.getMessage());
        }
        return count;
    }
    
    public int ??????????????????????????????(final int mapId) {
        return this.getMapFactory().getMap(mapId).characterSize();
    }
    
    public int ??????????????????????????????(final int mapId) {
        return this.getMapFactory().getMap(mapId).characterSize();
    }
    
    public void ????????????????????????(final int mapId, final String msg, final int itemId) {
        this.getMapFactory().getMap(mapId).startMapEffect(msg, itemId);
    }
    
    public void ????????????(final int ??????ID) {
        if (this.c.getPlayer().getMap().getPermanentWeather() > 0) {
            this.c.getPlayer().getMap().setPermanentWeather(0);
            this.c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.removeMapEffect());
        }
        else if (!MapleItemInformationProvider.getInstance().itemExists(??????ID) || ??????ID / 10000 != 512) {
            this.c.getPlayer().dropMessage(5, "???????????????ID???");
        }
        else {
            this.c.getPlayer().getMap().setPermanentWeather(??????ID);
            this.c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.startMapEffect("", ??????ID, false));
            this.c.getPlayer().dropMessage(5, "????????????????????????");
        }
    }
    
    public void ????????????() {
        this.c.getPlayer().getMap().toggleDrops();
    }
    
    public long ??????????????????() {
        return (System.currentTimeMillis() - this.c.getPlayer().????????????) / 10L;
    }
    
    public void ?????????(final int r) {
        this.c.getPlayer().addFame(r);
    }
    
    public void ????????????() {
        this.c.getPlayer().getFame();
    }
    
    public void ????????????() {
        if (this.????????????(this.c.getPlayer().getMapId()) <= 0) {
            final int ?????? = this.c.getPlayer().getMapId();
            this.????????????(??????);
            this.c.getPlayer().dropMessage(1, "?????????????????????????????? 5 ?????????????????????");
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(300000L);
                        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                            for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                                if (chr == null) {
                                    continue;
                                }
                                if (chr.getMapId() != ??????) {
                                    continue;
                                }
                                chr.getClient().getSession().close();
                            }
                        }
                        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                            cserv.getMapFactory().destroyMap(??????, true);
                            cserv.getMapFactory().HealMap(??????);
                        }
                        NPCConversationManager.this.????????????(??????);
                    }
                    catch (InterruptedException ex) {}
                }
            }.start();
        }
        else {
            this.c.getPlayer().dropMessage(1, "?????????????????????????????????????????????");
        }
    }
    
    public void ????????????(final int a) {
        if (this.????????????(a) <= 0) {
            final int ?????? = a;
            this.????????????(??????);
            this.c.getPlayer().dropMessage(1, "?????????????????????????????? 1 ?????????????????????");
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(60000L);
                        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                            cserv.getMapFactory().destroyMap(??????, true);
                            cserv.getMapFactory().HealMap(??????);
                        }
                        NPCConversationManager.this.????????????(??????);
                    }
                    catch (InterruptedException ex) {}
                }
            }.start();
        }
        else {
            this.c.getPlayer().dropMessage(1, "?????????????????????????????????????????????");
        }
    }
    
    public void ????????????(final int a) {
        try (final Connection con = DatabaseConnection.getConnection();
             final PreparedStatement ps = con.prepareStatement("INSERT INTO map (id) VALUES ( ?)")) {
            ps.setInt(1, a);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException ex) {}
    }
    
    public void ????????????(final int a) {
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        try {
            ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM map where id =" + a + "");
            rs = ps1.executeQuery();
            if (rs.next()) {
                final String sqlstr = " Delete from map where id = '" + a + "'";
                ps1.executeUpdate(sqlstr);
            }
        }
        catch (SQLException ex) {}
    }
    
    public int ????????????(final int a) {
        int data = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM map where id =" + a + "");
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ++data;
            }
            ps.close();
        }
        catch (SQLException ex) {}
        return data;
    }
    
    public void ???????????????() {
        MapleGuild.????????????(this.getClient(), this.npc);
    }
    
    public void ???????????????() {
        MapleGuild.????????????(this.getClient(), this.npc);
    }
    
    public void ???????????????() {
        MapleGuild.????????????(this.getClient(), this.npc);
    }
    
    public void ??????????????????() {
        MapleGuild.???????????????(this.getClient(), this.npc);
    }
    
    public void ????????????????????????() {
        MapleGuild.?????????????????????(this.getClient(), this.npc);
    }
    
    public int ????????????????????????() {
        int data = 0;
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement psu = con.prepareStatement("SELECT todayOnlineTime FROM characters WHERE id = ?");
            psu.setInt(1, this.c.getPlayer().getId());
            final ResultSet rs = psu.executeQuery();
            if (rs.next()) {
                data = rs.getInt("todayOnlineTime");
            }
            rs.close();
            psu.close();
        }
        catch (SQLException ex) {
            System.err.println("?????????????????????????????????" + ex.getMessage());
        }
        return data;
    }
    
    public int ?????????????????????() {
        int data = 0;
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement psu = con.prepareStatement("SELECT totalOnlineTime FROM characters WHERE id = ?");
            psu.setInt(1, this.c.getPlayer().getId());
            final ResultSet rs = psu.executeQuery();
            if (rs.next()) {
                data = rs.getInt("totalOnlineTime");
            }
            rs.close();
            psu.close();
        }
        catch (SQLException ex) {
            System.err.println("??????????????????????????????" + ex.getMessage());
        }
        return data;
    }
    
    public int ??????????????????() {
        int count = 0;
        for (final ChannelServer chl : ChannelServer.getAllInstances()) {
            count += chl.getPlayerStorage().getAllCharacters().size();
        }
        return count;
    }
    
    public static int ?????????????????????????????????(final int a, final int b, final int c) {
        int data = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE characterid =" + a + " && inventorytype = " + b + " && position = " + c + "");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ++data;
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("????????????????????????????????? - ????????????????????????" + Ex);
        }
        return data;
    }
    
    public static int ????????????????????????(final int a, final int b, final int c) {
        int data = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE characterid =" + a + " && inventorytype = " + b + " && position = " + c + "");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getInt("itemid");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("????????????????????????????????? - ????????????????????????" + Ex);
        }
        return data;
    }
    
    public static int ?????????????????????????????????(final int a, final int b, final int c) {
        int data = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE characterid =" + a + " && itemid = " + b + " && inventorytype = " + c + "");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ++data;
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("????????????????????????????????? - ????????????????????????" + Ex);
        }
        return data;
    }
    
    public static int ????????????????????????() {
        int data = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT MAX(level) as DATA FROM characters WHERE gm = 0");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getInt("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("?????????????????????????????? - ????????????????????????" + Ex);
        }
        return data;
    }
    
    public static int ??????????????????() {
        int level = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT  `level` FROM characters WHERE gm = 0 ORDER BY `level` DESC LIMIT 1");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    level = rs.getInt("level");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("???????????????????????? - ????????????????????????" + Ex);
        }
        return level;
    }
    
    public static int ????????????????????????() {
        int data = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT MAX(fame) as DATA FROM characters WHERE gm = 0");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getInt("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("?????????????????????????????? - ????????????????????????" + Ex);
        }
        return data;
    }
    
    public static String ??????????????????????????????() {
        String name = "";
        String level = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT `name`, `fame` FROM characters WHERE gm = 0 ORDER BY `fame` DESC LIMIT 1");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("name");
                    level = rs.getString("fame");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("???????????????????????? - ????????????????????????" + Ex);
        }
        return String.format("%s", name);
    }
    
    public static int ????????????????????????() {
        int data = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT MAX(meso) as DATA FROM characters WHERE gm = 0");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getInt("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("?????????????????????????????? - ????????????????????????" + Ex);
        }
        return data;
    }
    
    public static String ??????????????????????????????() {
        String name = "";
        String level = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT `name`, `meso` FROM characters WHERE gm = 0 ORDER BY `meso` DESC LIMIT 1");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("name");
                    level = rs.getString("meso");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("???????????????????????? - ????????????????????????" + Ex);
        }
        return String.format("%s", name);
    }
    
    public static int ????????????????????????() {
        int data = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT MAX(totalOnlineTime) as DATA FROM characters WHERE gm = 0");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getInt("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("?????????????????????????????? - ????????????????????????" + Ex);
        }
        return data;
    }
    
    public static String ??????????????????????????????() {
        String name = "";
        String level = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT `name`, `totalOnlineTime` FROM characters WHERE gm = 0 ORDER BY `totalOnlineTime` DESC LIMIT 1");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("name");
                    level = rs.getString("totalOnlineTime");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("???????????????????????? - ????????????????????????" + Ex);
        }
        return String.format("%s", name);
    }
    
    public static String ??????????????????????????????() {
        String name = "";
        String level = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT `name`, `todayOnlineTime` FROM characters WHERE gm = 0 ORDER BY `todayOnlineTime` DESC LIMIT 1");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("name");
                    level = rs.getString("todayOnlineTime");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("???????????????????????? - ????????????????????????" + Ex);
        }
        return String.format("%s", name);
    }
    
    public static String ????????????????????????() {
        String name = "";
        String level = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT `name`, `GP` FROM guilds  ORDER BY `GP` DESC LIMIT 1");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("name");
                    level = rs.getString("GP");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("???????????????????????? - ????????????????????????" + Ex);
        }
        return String.format("%s", name);
    }
    
    public static String ????????????????????????(final int guildId) {
        String data = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT rank1title as DATA FROM guilds WHERE guildid = ?");
            ps.setInt(1, guildId);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("???????????????????????? - ????????????????????????" + Ex);
        }
        return data;
    }
    
    public static String ???????????????????????????(final int guildId) {
        String data = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT rank2title as DATA FROM guilds WHERE guildid = ?");
            ps.setInt(1, guildId);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("???????????????????????? - ????????????????????????" + Ex);
        }
        return data;
    }
    
    public static String ??????????????????????????????(final int guildId) {
        String data = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT rank3title as DATA FROM guilds WHERE guildid = ?");
            ps.setInt(1, guildId);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("???????????????????????? - ????????????????????????" + Ex);
        }
        return data;
    }
    
    public static String ??????????????????????????????(final int guildId) {
        String data = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT rank4title as DATA FROM guilds WHERE guildid = ?");
            ps.setInt(1, guildId);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("???????????????????????? - ????????????????????????" + Ex);
        }
        return data;
    }
    
    public static String ??????????????????????????????(final int guildId) {
        String data = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT rank5title as DATA FROM guilds WHERE guildid = ?");
            ps.setInt(1, guildId);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("???????????????????????? - ????????????????????????" + Ex);
        }
        return data;
    }
    
    public static String ??????????????????ID(final int guildId) {
        String data = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT leader as DATA FROM guilds WHERE guildid = ?");
            ps.setInt(1, guildId);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("???????????????????????? - ????????????????????????" + Ex);
        }
        return data;
    }
    
    public static int ???????????????(final int a) {
        int data = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM characters ");
            try (final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (rs.getInt("guildid") == a) {
                        ++data;
                    }
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("????????????????????????????????????" + Ex);
        }
        return data;
    }
    
    public String ???????????????() {
        int ?????? = 1;
        final StringBuilder name = new StringBuilder();
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM characters  WHERE gm = 0 order by level desc");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt("level") < (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("?????????????????????")) && rs.getInt("level") > 30) {
                    if (?????? < 10) {
                        final String ???????????? = rs.getString("name");
                        final String ?????? = this.??????(rs.getInt("job"));
                        name.append("Top.#e#d").append(??????).append("#n#k   ");
                        name.append("#b").append(????????????).append("#k");
                        for (int j = 13 - ????????????.getBytes().length; j > 0; --j) {
                            name.append(" ");
                        }
                        name.append("  ").append(??????).append("");
                        for (int j = 15 - ??????.getBytes().length; j > 0; --j) {
                            name.append(" ");
                        }
                        name.append("  Lv.#d").append(rs.getInt("level")).append("#k\r\n");
                        ++??????;
                    }
                    else if (?????? >= 10 && ?????? <= 99) {
                        final String ???????????? = rs.getString("name");
                        final String ?????? = this.??????(rs.getInt("job"));
                        name.append("Top.#e#d").append(??????).append("#n#k  ");
                        name.append("#b").append(????????????).append("#k");
                        for (int j = 13 - ????????????.getBytes().length; j > 0; --j) {
                            name.append(" ");
                        }
                        name.append("  ").append(??????).append("");
                        for (int j = 15 - ??????.getBytes().length; j > 0; --j) {
                            name.append(" ");
                        }
                        name.append("  Lv.#d").append(rs.getInt("level")).append("#k\r\n");
                        ++??????;
                    }
                    else {
                        if (?????? <= 99) {
                            continue;
                        }
                        final String ???????????? = rs.getString("name");
                        final String ?????? = this.??????(rs.getInt("job"));
                        name.append("Top.#e#d").append(??????).append("#n#k ");
                        name.append("#b").append(????????????).append("#k");
                        for (int j = 13 - ????????????.getBytes().length; j > 0; --j) {
                            name.append(" ");
                        }
                        name.append("  ").append(??????).append("");
                        for (int j = 15 - ??????.getBytes().length; j > 0; --j) {
                            name.append(" ");
                        }
                        name.append("  Lv.#d").append(rs.getInt("level")).append("#k\r\n");
                        ++??????;
                    }
                }
            }
        }
        catch (SQLException ex) {}
        name.append("\r\n\r\n");
        return name.toString();
    }
    
    public String ???????????????() {
        final int ?????? = 1;
        final StringBuilder name = new StringBuilder();
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM characters  WHERE gm = 0 order by level desc");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt("level") == (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("?????????????????????"))) {
                    final String ???????????? = rs.getString("name");
                    final String ?????? = this.??????(rs.getInt("job"));
                    final int ???????????? = rs.getInt("guildid");
                    name.append("    ");
                    name.append("#b").append(????????????).append("#k");
                    for (int j = 13 - ????????????.getBytes().length; j > 0; --j) {
                        name.append(" ");
                    }
                    name.append("  ").append(??????).append("");
                    for (int j = 15 - ??????.getBytes().length; j > 0; --j) {
                        name.append(" ");
                    }
                    name.append("??????.#d").append(??????????????????(????????????)).append("#k\r\n");
                }
            }
        }
        catch (SQLException ex) {}
        name.append("\r\n\r\n");
        return name.toString();
    }
    
    public String ?????????????????????() {
        final int ?????? = 1;
        final StringBuilder name = new StringBuilder();
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM monsterbook   order by level desc");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt("level") == (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("?????????????????????"))) {
                    final String ???????????? = rs.getString("name");
                    final String ?????? = this.??????(rs.getInt("job"));
                    final int ???????????? = rs.getInt("guildid");
                    name.append("    ");
                    name.append("#b").append(????????????).append("#k");
                    for (int j = 13 - ????????????.getBytes().length; j > 0; --j) {
                        name.append(" ");
                    }
                    name.append("  ").append(??????).append("");
                    for (int j = 15 - ??????.getBytes().length; j > 0; --j) {
                        name.append(" ");
                    }
                    name.append("??????.#d").append(??????????????????(????????????)).append("#k\r\n");
                }
            }
        }
        catch (SQLException ex) {}
        name.append("\r\n\r\n");
        return name.toString();
    }
    
    public String ???????????????() {
        int ?????? = 1;
        final StringBuilder name = new StringBuilder();
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM characters WHERE gm = 0 order by meso desc LIMIT 20 ");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt("meso") > 0) {
                    if (?????? < 10) {
                        final String ???????????? = rs.getString("name");
                        final String ?????? = rs.getString("meso");
                        name.append("Top.#e#d").append(??????).append("#n#k   ");
                        name.append("#b").append(????????????).append("#k");
                        for (int j = 13 - ????????????.getBytes().length; j > 0; --j) {
                            name.append(" ");
                        }
                        name.append("     Meso.#d").append(??????).append("#n\r\n");
                        ++??????;
                    }
                    else {
                        if (?????? < 10 || ?????? > 20) {
                            continue;
                        }
                        final String ???????????? = rs.getString("name");
                        final String ?????? = rs.getString("meso");
                        name.append("Top.#e#d").append(??????).append("#n#k  ");
                        name.append("#b").append(????????????).append("#k");
                        for (int j = 13 - ????????????.getBytes().length; j > 0; --j) {
                            name.append(" ");
                        }
                        name.append("     Meso.#d").append(??????).append("#n\r\n");
                        ++??????;
                    }
                }
            }
        }
        catch (SQLException ex) {}
        name.append("\r\n\r\n");
        return name.toString();
    }
    
    public String ???????????????() {
        int ?????? = 1;
        final StringBuilder name = new StringBuilder();
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM characters WHERE gm = 0 order by totalOnlineTime desc LIMIT 20 ");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt("totalOnlineTime") > 0) {
                    if (?????? < 10) {
                        final String ???????????? = rs.getString("name");
                        final String ????????? = rs.getString("totalOnlineTime");
                        final String ????????? = rs.getString("todayOnlineTime");
                        name.append("Top.#e#d").append(??????).append("#n#k   ");
                        name.append("#b").append(????????????).append("#k");
                        for (int j = 13 - ????????????.getBytes().length; j > 0; --j) {
                            name.append(" ");
                        }
                        name.append("     (tal/day).#d[").append(?????????).append(" / ").append(?????????).append("])\r\n");
                        ++??????;
                    }
                    else {
                        if (?????? < 10 || ?????? > 20) {
                            continue;
                        }
                        final String ???????????? = rs.getString("name");
                        final String ????????? = rs.getString("totalOnlineTime");
                        final String ????????? = rs.getString("todayOnlineTime");
                        name.append("Top.#e#d").append(??????).append("#n#k  ");
                        name.append("#b").append(????????????).append("#k");
                        for (int j = 13 - ????????????.getBytes().length; j > 0; --j) {
                            name.append(" ");
                        }
                        name.append("     (tal/day).#d[").append(?????????).append(" / ").append(?????????).append("])\r\n");
                        ++??????;
                    }
                }
            }
        }
        catch (SQLException ex) {}
        name.append("\r\n\r\n");
        return name.toString();
    }
    
    public String ?????????????????????() {
        int ?????? = 1;
        final StringBuilder name = new StringBuilder();
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM inventoryequipment order by itemlevel desc LIMIT 20");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt("itemlevel") > 0) {
                    if (?????? < 10) {
                        final int ??????ID = ??????id????????????(rs.getInt("inventoryitemid"));
                        final String ???????????? = ??????ID?????????(??????ID);
                        if (??????ID???GM(??????ID) != 0) {
                            continue;
                        }
                        final int ??????IP = ??????id????????????ID(rs.getInt("inventoryitemid"));
                        name.append("Top.#e#d").append(??????).append("#n#k   ");
                        name.append("?????????:#b").append(????????????).append("#k");
                        for (int j = 15 - ????????????.getBytes().length; j > 0; --j) {
                            name.append(" ");
                        }
                        name.append(" lv.#r").append(rs.getInt("itemlevel")).append("#k #b#t").append(??????IP).append("##k\r\n");
                        ++??????;
                    }
                    else {
                        if (?????? < 10 || ?????? > 20) {
                            continue;
                        }
                        final int ??????ID = ??????id????????????(rs.getInt("inventoryitemid"));
                        final String ???????????? = ??????ID?????????(??????ID);
                        if (??????ID???GM(??????ID) != 0) {
                            continue;
                        }
                        final int ??????IP = ??????id????????????ID(rs.getInt("inventoryitemid"));
                        name.append("Top.#e#d").append(??????).append("#n#k  ");
                        name.append("?????????:#b").append(????????????).append("#k");
                        for (int j = 15 - ????????????.getBytes().length; j > 0; --j) {
                            name.append(" ");
                        }
                        name.append(" lv.#r").append(rs.getInt("itemlevel")).append("#k #b#t").append(??????IP).append("##k\r\n");
                        ++??????;
                    }
                }
            }
        }
        catch (SQLException ex) {}
        name.append("\r\n\r\n");
        return name.toString();
    }
    
    public static int ??????id????????????ID(final int a) {
        int data = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE inventoryitemid = " + a + "");
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                data = rs.getInt("itemid");
            }
            ps.close();
        }
        catch (SQLException ex) {}
        return data;
    }
    
    public static int ??????id????????????(final int a) {
        int data = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE inventoryitemid = " + a + "");
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                data = rs.getInt("characterid");
            }
            ps.close();
        }
        catch (SQLException ex) {}
        return data;
    }
    
    public static int ???????????????????????????() {
        int data = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM characters");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt("todayOnlineTime") > 0) {
                    data += rs.getInt("todayOnlineTime");
                }
            }
        }
        catch (SQLException ex) {}
        return data;
    }
    
    public static int ???????????????????????????(final int a) {
        int data = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM characters");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt("guildid") == a && rs.getInt("todayOnlineTime") > 0) {
                    data += rs.getInt("todayOnlineTime");
                }
            }
        }
        catch (SQLException ex) {}
        return data;
    }
    
    public static int ??????ID???GM(final int id) {
        int data = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT gm as DATA FROM characters WHERE id = ?");
            ps.setInt(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getInt("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("??????ID????????????????????????");
        }
        return data;
    }
    
    public void ??????????????????(final int a) {
        this.getMap(a).removeDrops();
    }
    
    public String ??????(final int a) {
        return MapleCarnivalChallenge.getJobNameById(a);
    }
    
    public String ????????????() {
        final String ??????1 = MapleCarnivalChallenge.getJobNameById(MapleParty.???????????? - 1);
        final String ??????2 = MapleCarnivalChallenge.getJobNameById(MapleParty.????????????);
        final String ??????3 = MapleCarnivalChallenge.getJobNameById(MapleParty.???????????? + 1);
        final String ??????4 = ??????1 + "," + ??????2 + "," + ??????3;
        return ??????4;
    }
    
    public String ????????????(final int id) {
        final StringBuilder name = new StringBuilder();
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM mysterious WHERE f = " + id + "");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final int ?????? = rs.getInt("id");
                final int ?????? = rs.getInt("itemid");
                final int ?????? = rs.getInt("??????");
                final int ?????? = rs.getInt("??????");
                final int ?????? = rs.getInt("??????");
                name.append("   #L").append(??????).append("# #v").append(??????).append("# #b#t").append(??????).append("##k x ").append(??????).append("");
                name.append(" #d[???/???]:#b").append(??????).append("#k/#b").append(??????).append("#k#l\r\n");
            }
        }
        catch (SQLException ex) {}
        return name.toString();
    }
    
    public void ????????????(final int id) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM mysterious WHERE f = " + id + "");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final int ?????? = rs.getInt("id");
                final int ?????? = rs.getInt("itemid");
                final int ?????? = rs.getInt("??????");
                final int ?????? = rs.getInt("??????");
                final int ?????? = rs.getInt("??????");
                this.gainItem(??????, (short)??????);
            }
        }
        catch (SQLException ex) {}
    }
    
    public void ??????() {
        final MapleMap map = this.c.getPlayer().getMap();
        final double range = Double.POSITIVE_INFINITY;
        for (final MapleMapObject monstermo : map.getMapObjectsInRange(this.c.getPlayer().getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER))) {
            final MapleMonster mob = (MapleMonster)monstermo;
            map.killMonster(mob, this.c.getPlayer(), true, false, (byte)1);
        }
    }
    
    public void setzb(final int slot) {
        try {
            final int cid = this.getPlayer().getAccountID();
            final Connection con = DatabaseConnection.getConnection();
            try (final PreparedStatement ps = con.prepareStatement("UPDATE accounts SET money =money+ " + slot + " WHERE id = " + cid + "")) {
                ps.executeUpdate();
            }
        }
        catch (SQLException ex) {
            ex.getStackTrace();
        }
    }
    
    @Override
    public void openWeb(final String web) {
        this.c.sendPacket(MaplePacketCreator.openWeb(web));
    }
    
    public final boolean getPartyBosslog(final String bossid, final int lcishu) {
        final MapleParty party = this.getPlayer().getParty();
        for (final MaplePartyCharacter pc : party.getMembers()) {
            final MapleCharacter chr = World.getStorage(this.getChannelNumber()).getCharacterById(pc.getId());
            if (chr != null && chr.getBossLog(bossid) >= lcishu) {
                return false;
            }
        }
        return true;
    }
    
    public void setPartyBosslog(final String bossid) {
        final MapleParty party = this.getPlayer().getParty();
        for (final MaplePartyCharacter pc : party.getMembers()) {
            final MapleCharacter chr = World.getStorage(this.getChannelNumber()).getCharacterById(pc.getId());
            if (chr != null) {
                chr.setBossLog(bossid);
            }
        }
    }
    
    public int gainGachaponItem(final int id, final int quantity) {
        return this.gainGachaponItem(id, quantity, this.c.getPlayer().getMap().getStreetName() + " - " + this.c.getPlayer().getMap().getMapName());
    }
    
    public int gainGachaponItem(final int id, final int quantity, final String msg) {
        try {
            if (!MapleItemInformationProvider.getInstance().itemExists(id)) {
                return -1;
            }
            final IItem item = MapleInventoryManipulator.addbyId_Gachapon(this.c, id, (short)quantity);
            if (item == null) {
                return -1;
            }
            final byte rareness = GameConstants.gachaponRareItem(item.getItemId());
            if (rareness > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "] " + this.c.getPlayer().getName(), " : ??????????????????!", item, rareness, this.getPlayer().getClient().getChannel()));
            }
            return item.getItemId();
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public int gainGachaponItem(final int id, final int quantity, final String msg, final int ??????) {
        try {
            if (!MapleItemInformationProvider.getInstance().itemExists(id)) {
                return -1;
            }
            final IItem item = MapleInventoryManipulator.addbyId_Gachapon(this.c, id, (short)quantity);
            if (item == null) {
                return -1;
            }
            if (?????? > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[ " + msg + " ] : ??????????????? [ " + this.c.getPlayer().getName(), " ] ???????????????", item, (byte)0, this.getPlayer().getClient().getChannel()));
            }
            return item.getItemId();
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public int gainGachaponItem2(final int id, final int quantity, final String msg, final int ??????) {
        try {
            if (!MapleItemInformationProvider.getInstance().itemExists(id)) {
                return -1;
            }
            final IItem item = MapleInventoryManipulator.addbyId_Gachapon(this.c, id, (short)quantity);
            if (item == null) {
                return -1;
            }
            if (?????? > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[ " + msg + " ] : ??????????????? [ " + this.c.getPlayer().getName(), " ] ???????????????", item, (byte)0, this.getPlayer().getClient().getChannel()));
            }
            return item.getItemId();
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public int gainGachaponItem3(final int id, final int quantity, final String msg, final int ??????) {
        try {
            if (!MapleItemInformationProvider.getInstance().itemExists(id)) {
                return -1;
            }
            final IItem item = MapleInventoryManipulator.addbyId_Gachapon(this.c, id, (short)quantity);
            if (item == null) {
                return -1;
            }
            if (?????? > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[ " + msg + " ] : ??????????????? [ " + this.c.getPlayer().getName(), " ] ???????????????", item, (byte)0, this.getPlayer().getClient().getChannel()));
            }
            return item.getItemId();
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public merchant_main getMerchant_main() {
        return merchant_main.getInstance();
    }
    
    public void logToFile_chr(final String path, final String msg) {
        FileoutputUtil.logToFile(path, msg);
    }
    
    public void gainmoneym(final int slot) {
        this.gainmoneym(this.c.getPlayer().getAccountID(), slot);
    }
    
    public void gainmoneym(final int cid, final int slot) {
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            final PreparedStatement ps = con.prepareStatement("UPDATE accounts SET moneym =moneym+" + slot + " WHERE id = " + cid + "");
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException ex) {
            ex.getStackTrace();
        }
        FileoutputUtil.logToFile("??????/??????????????????/?????????????????????.txt", " ?????????(" + ((this.c.getPlayer().getAccountID() == cid) ? "??????" : Integer.valueOf(cid)) + ") " + slot + "");
    }
    
    public void setmoneym(final int slot) {
        this.setmoneym(this.c.getPlayer().getAccountID(), slot);
    }
    
    public void setmoneym(final int cid, final int slot) {
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            final PreparedStatement ps = con.prepareStatement("UPDATE accounts SET moneym = " + slot + " WHERE id = " + cid + "");
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException ex) {
            ex.getStackTrace();
        }
        FileoutputUtil.logToFile("??????/??????????????????/?????????????????????.txt", " ?????????(" + ((this.c.getPlayer().getAccountID() == cid) ? "??????" : Integer.valueOf(cid)) + ") " + slot + "");
    }
    
    public int getmoneym() {
        int moneyb = 0;
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            final int cid = this.getPlayer().getAccountID();
            ResultSet rs;
            try (final PreparedStatement limitCheck = con.prepareStatement("SELECT * FROM accounts WHERE id=" + cid + "")) {
                rs = limitCheck.executeQuery();
                if (rs.next()) {
                    moneyb = rs.getInt("moneym");
                }
            }
            rs.close();
        }
        catch (SQLException ex) {
            ex.getStackTrace();
        }
        return moneyb;
    }
    
    public boolean checkHold(final int itemid, int quantity) {
        byte need_solt = 0;
        while (quantity > 0) {
            ++need_solt;
            if (quantity < 32767) {
                break;
            }
            quantity -= 32767;
        }
        return this.c.getPlayer().getInventory(GameConstants.getInventoryType(itemid)).getNumFreeSlot() >= need_solt;
    }
    
    public void ??????????????????????????????() {
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (final MapleCharacter mch : cserv.getPlayerStorage().getAllCharacters()) {
                mch.modifyCSPoints(1, 1000);
                MapleInventoryManipulator.addById(mch.getClient(), 2340000, (short)1, "");
                MapleInventoryManipulator.addById(mch.getClient(), 4000463, (short)5, "");
                mch.dropMessage(-11, "[??????????????????] ???????????????????????????????????????" + "??????" + 1000 + " ???." + "????????????1?????????????????????5???.");
                mch.dropMessage(-1, "[??????????????????] ???????????????????????????????????????" + "?????? " + 1000 + " ???." + "????????????1?????????????????????5???.");
            }
        }
        for (final ChannelServer cserv2 : ChannelServer.getAllInstances()) {
            for (final MapleCharacter mch : cserv2.getPlayerStorage().getAllCharacters()) {
                mch.startMapEffect("[??????????????????] ?????????1000?????????????????????1?????????????????????5???.???????????????????????????", 5121006);
            }
        }
    }
    
    public int ???????????????() {
        int ???????????? = 0;
        try {
            final int cid = 4001128;
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement limitCheck = con.prepareStatement("SELECT * FROM shijiexianzhidengji WHERE huoyaotongid=" + cid + "");
            final ResultSet rs = limitCheck.executeQuery();
            if (rs.next()) {
                ???????????? = rs.getInt("xianzhidengji");
            }
            limitCheck.close();
            rs.close();
        }
        catch (SQLException ex) {}
        return ????????????;
    }
    
    public int ??????????????????() {
        int ????????? = 0;
        try {
            final int cid = 4001128;
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement limitCheck = con.prepareStatement("SELECT * FROM shijiexianzhidengji WHERE huoyaotongid=" + cid + "");
            final ResultSet rs = limitCheck.executeQuery();
            if (rs.next()) {
                ????????? = rs.getInt("dangqianshuliang");
            }
            limitCheck.close();
            rs.close();
        }
        catch (SQLException ex) {}
        return ?????????;
    }
    
    public int ?????????????????????() {
        int ?????????????????? = 0;
        try {
            final int cid = 4001128;
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement limitCheck = con.prepareStatement("SELECT * FROM shijiexianzhidengji WHERE huoyaotongid=" + cid + "");
            final ResultSet rs = limitCheck.executeQuery();
            if (rs.next()) {
                ?????????????????? = rs.getInt("zongshuliang");
            }
            limitCheck.close();
            rs.close();
        }
        catch (SQLException ex) {}
        return ??????????????????;
    }
    
    public void ?????????????????????(final int slot) {
        try {
            final int cid = 4001128;
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE shijiexianzhidengji SET dangqianshuliang =dangqianshuliang+ " + slot + " WHERE huoyaotongid = " + cid + "");
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException ex) {}
    }
    
    public void ????????????????????????(final int slot) {
        try {
            final int cid = 4001128;
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE shijiexianzhidengji SET zongshuliang =zongshuliang+ " + slot + " WHERE huoyaotongid = " + cid + "");
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException ex) {}
    }
    
    public void ??????????????????(final int slot) {
        try {
            final int cid = 4001128;
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE shijiexianzhidengji SET xianzhidengji =xianzhidengji+ " + slot + " WHERE huoyaotongid = " + cid + "");
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException ex) {}
    }
    
    public void ?????????????????????(final int slot) {
        try {
            final int cid = 4001128;
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE shijiexianzhidengji SET dangqianshuliang =dangqianshuliang- " + slot + " WHERE huoyaotongid = " + cid + "");
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException ex) {}
    }
    
    public void spawnChaosZakum(final int x, final int y) {
        final MapleMap mapp = this.c.getChannelServer().getMapFactory().getMap(this.c.getPlayer().getMapId());
        mapp.spawnChaosZakum(x, y);
    }
    
    public void spawnZakum(final int x, final int y) {
        final MapleMap mapp = this.c.getChannelServer().getMapFactory().getMap(this.c.getPlayer().getMapId());
        mapp.spawnZakum(x, y);
    }
}
