package server.events;

import java.util.Iterator;
import client.MapleStat;
import java.util.Map.Entry;
import tools.Pair;
import tools.MaplePacketCreator;
import server.Timer.EventTimer;
import server.maps.MapleMap;
import client.MapleCharacter;
import java.util.concurrent.ScheduledFuture;

public class MapleOxQuiz extends MapleEvent
{
    private ScheduledFuture<?> oxSchedule;
    private ScheduledFuture<?> oxSchedule2;
    private int timesAsked;
    
    public MapleOxQuiz(final int channel, final int[] mapid) {
        super(channel, mapid);
        this.timesAsked = 0;
    }
    
    private void resetSchedule() {
        if (this.oxSchedule != null) {
            this.oxSchedule.cancel(false);
            this.oxSchedule = null;
        }
        if (this.oxSchedule2 != null) {
            this.oxSchedule2.cancel(false);
            this.oxSchedule2 = null;
        }
    }
    
    @Override
    public void onMapLoad(final MapleCharacter chr) {
        if (chr.getMapId() == this.mapid[0] && !chr.isGM()) {
            chr.canTalk(false);
        }
    }
    
    @Override
    public void reset() {
        super.reset();
        this.getMap(0).getPortal("join00").setPortalState(false);
        this.resetSchedule();
        this.timesAsked = 0;
    }
    
    @Override
    public void unreset() {
        super.unreset();
        this.getMap(0).getPortal("join00").setPortalState(true);
        this.resetSchedule();
    }
    
    @Override
    public void startEvent() {
        this.sendQuestion();
    }
    
    public void sendQuestion() {
        this.sendQuestion(this.getMap(0));
    }
    
    public void sendQuestion(final MapleMap toSend) {
        if (this.oxSchedule2 != null) {
            this.oxSchedule2.cancel(false);
        }
        this.oxSchedule2 = EventTimer.getInstance().schedule((Runnable)new Runnable() {
            @Override
            public void run() {
                int number = 0;
                final int enditem = 9 - timesAsked;
                for (final MapleCharacter mc : toSend.getCharactersThreadsafe()) {
                    if (mc.isGM() || !mc.isAlive()) {
                        ++number;
                    }
                }
                if (toSend.getCharactersSize() - number <= 1 || timesAsked == 10) {
                    toSend.broadcastMessage(MaplePacketCreator.serverNotice(6, "???????????????????????????"));
                    MapleOxQuiz.this.unreset();
                    for (final MapleCharacter chr : toSend.getCharactersThreadsafe()) {
                        if (chr != null && !chr.isGM() && chr.isAlive()) {
                            chr.canTalk(true);
                            MapleOxQuiz.this.givePrize(chr);
                            MapleOxQuiz.this.warpBack(chr);
                        }
                    }
                    return;
                }
                final Entry<Pair<Integer, Integer>, server.events.MapleOxQuizFactory.MapleOxQuizEntry> question = MapleOxQuizFactory.getInstance().grabRandomQuestion();
                toSend.broadcastMessage(MaplePacketCreator.showOXQuiz((int)Integer.valueOf(((Pair<Integer, Integer>)question.getKey()).left), (int)Integer.valueOf(((Pair<Integer, Integer>)question.getKey()).right), true));
                toSend.broadcastMessage(MaplePacketCreator.getClock(12));
                if (oxSchedule != null) {
                    oxSchedule.cancel(false);
                }
                oxSchedule = EventTimer.getInstance().schedule((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        toSend.broadcastMessage(MaplePacketCreator.showOXQuiz((int)(Integer)((Pair)question.getKey()).left, (int)(Integer)((Pair)question.getKey()).right, false));
                        timesAsked++;
                        for (final MapleCharacter chr : toSend.getCharactersThreadsafe()) {
                            if (chr != null && !chr.isGM() && chr.isAlive()) {
                                if (!MapleOxQuiz.this.isCorrectAnswer(chr, ((server.events.MapleOxQuizFactory.MapleOxQuizEntry)question.getValue()).getAnswer())) {
                                    chr.getStat().setHp(0);
                                    chr.updateSingleStat(MapleStat.HP, 0);
                                }
                                else {
                                    chr.dropMessage(6, "???????????????:" + timesAsked + "???/????????????????????????:" + enditem + "???");
                                    chr.gainExp(3000, true, true, false);
                                }
                            }
                        }
                        MapleOxQuiz.this.sendQuestion();
                    }
                }, 12000L);
            }
        }, 10000L);
    }
    
    private boolean isCorrectAnswer(final MapleCharacter chr, final int answer) {
        final double x = chr.getPosition().getX();
        final double y = chr.getPosition().getY();
        if ((x > -234.0 && y > -26.0 && answer == 0) || (x < -234.0 && y > -26.0 && answer == 1)) {
            chr.dropMessage(6, "?????????????????????");
            return true;
        }
        chr.dropMessage(6, "?????????????????????");
        return false;
    }
}
