package pl.pavetti.simpleevents.event;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;

import java.util.Arrays;
import java.util.List;

public class WoodCutEvent extends Event {

    private final List<Material> woods = Arrays.asList(
            Material.OAK_WOOD,
            Material.SPRUCE_WOOD,
            Material.BIRCH_WOOD,
            Material.JUNGLE_WOOD,
            Material.ACACIA_WOOD,
            Material.DARK_OAK_WOOD,
            Material.DARK_OAK_LOG,
            Material.BIRCH_LOG,
            Material.SPRUCE_LOG,
            Material.JUNGLE_LOG,
            Material.ACACIA_LOG,
            Material.OAK_LOG);

    public WoodCutEvent(Plugin plugin, EventData data) {
        super(plugin, data);
    }


    @EventHandler
    public void onWoodCut(BlockBreakEvent event){
        if(running){
            if(woods.contains(event.getBlock().getType())) addScore(event.getPlayer(),1 );
        }
    }
}
