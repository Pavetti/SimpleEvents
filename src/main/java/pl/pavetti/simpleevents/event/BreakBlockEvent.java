package pl.pavetti.simpleevents.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;

public class BreakBlockEvent extends Event {

    public BreakBlockEvent(Plugin plugin, EventData eventData) {
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
    public void onBlockBreak(BlockBreakEvent event){
        if(running){
            Player player = event.getPlayer();
            addScore(player,1);
        }
    }
}
