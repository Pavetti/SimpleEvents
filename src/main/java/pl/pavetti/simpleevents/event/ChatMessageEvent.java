package pl.pavetti.simpleevents.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;

public class ChatMessageEvent extends Event {


    public ChatMessageEvent(Plugin plugin, EventData data) {
        super(plugin, data);
    }

    @EventHandler
    public void onSendMessage(AsyncPlayerChatEvent event){
        if(running){
            addScore(event.getPlayer(), 1);
        }
    }
}
