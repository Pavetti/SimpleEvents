package pl.pavetti.simpleevents.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;

public class CatchFishEvent extends Event {

    public CatchFishEvent(Plugin plugin, EventData data) {
        super(plugin, data);
    }

    @EventHandler
    public void onFishCatch(PlayerFishEvent event) {
        if (running) {
            if (event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
                addScore(event.getPlayer(), 1);
            }
        }
    }
}
