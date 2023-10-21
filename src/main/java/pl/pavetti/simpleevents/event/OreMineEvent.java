package pl.pavetti.simpleevents.event;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;

import java.util.Arrays;
import java.util.List;

public class OreMineEvent extends Event {

    private final List<Material> ores = Arrays.asList(
            Material.COAL_ORE,
            Material.IRON_ORE,
            Material.REDSTONE_ORE,
            Material.LAPIS_ORE,
            Material.DIAMOND_ORE,
            Material.EMERALD_ORE,
            Material.NETHER_QUARTZ_ORE,
            Material.GOLD_ORE);

    public OreMineEvent(Plugin plugin, EventData data) {
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
    public void onOreBreak(BlockBreakEvent event){
        if(running){
            if(ores.contains(event.getBlock().getType())) addScore(event.getPlayer(), 1);
        }
    }
}
