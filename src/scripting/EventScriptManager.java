package scripting;

import java.util.Iterator;
import tools.FilePrinter;
import javax.script.ScriptEngine;
import javax.script.Invocable;
import java.util.LinkedHashMap;
import handling.channel.ChannelServer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;

public class EventScriptManager extends AbstractScriptManager
{
    private final Map<String, EventEntry> events;
    private final AtomicInteger runningInstanceMapId;
    
    public final int getNewInstanceMapId() {
        return this.runningInstanceMapId.addAndGet(1);
    }
    
    public EventScriptManager(final ChannelServer cserv, final String[] scripts) {
        this.events = new LinkedHashMap<String, EventEntry>();
        this.runningInstanceMapId = new AtomicInteger(0);
        for (final String script : scripts) {
            if (!script.equals("")) {
                final Invocable iv = this.getInvocable("事件/" + script + ".js", null);
                if (iv != null) {
                    this.events.put(script, new EventEntry(script, iv, new EventManager(cserv, iv, script)));
                }
            }
        }
    }
    
    public final EventManager getEventManager(final String event) {
        final EventEntry entry = (EventEntry)this.events.get(event);
        if (entry == null) {
            return null;
        }
        return entry.em;
    }
    
    public final void init() {
        for (final EventEntry entry : this.events.values()) {
        }
    }
    
    public final void cancel() {
        for (final EventEntry entry : this.events.values()) {
            entry.em.cancel();
        }
    }
    
    private static class EventEntry
    {
        public String script;
        public Invocable iv;
        public EventManager em;
        
        public EventEntry(final String script, final Invocable iv, final EventManager em) {
            this.script = script;
            this.iv = iv;
            this.em = em;
        }
    }
}
