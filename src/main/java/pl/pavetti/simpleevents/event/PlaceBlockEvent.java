package pl.pavetti.simpleevents.event;

import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;

public class PlaceBlockEvent extends Event {

    public PlaceBlockEvent(Plugin plugin, EventData data) {
        super(plugin, data);
    }

    @EventHandler
    public void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent event) {
        if (running) {
            addScore(event.getPlayer(), 1);
        }
    }
}
