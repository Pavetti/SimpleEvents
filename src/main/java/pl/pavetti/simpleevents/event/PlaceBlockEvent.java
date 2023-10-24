package pl.pavetti.simpleevents.event;

import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;

public class PlaceBlockEvent extends Event {
    public PlaceBlockEvent(Plugin plugin, EventData eventData) {
        super(eventData);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @Override
    public void start() {
        score.clear();
        running = true;

    }
    @Override
    public void stop() {
        running = false;
    }

    @EventHandler
    public void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent event){
        if(running){
            addScore(event.getPlayer(), 1);
        }
    }
}
