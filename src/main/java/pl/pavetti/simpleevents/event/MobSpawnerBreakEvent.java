package pl.pavetti.simpleevents.event;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;

public class MobSpawnerBreakEvent extends Event {
    public MobSpawnerBreakEvent(Plugin plugin, EventData data) {
        super(data);
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
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
    public void onSpawnerBreak(BlockBreakEvent event){
        if(running){
            if(event.getBlock().getType() == Material.SPAWNER){
                addScore(event.getPlayer(), 1);
            }
        }
    }
}
