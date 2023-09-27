package pl.pavetti.simpleevents.event;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;

import java.util.Arrays;
import java.util.List;

public class EatFoodEvent extends Event {

    private List<Material> food = Arrays.asList(
            Material.APPLE,
            Material.BAKED_POTATO,
            Material.BEETROOT,
            Material.BEETROOT_SOUP,
            Material.BREAD,
            Material.CAKE,
            Material.CARROT,
            Material.CHORUS_FRUIT,
            Material.COOKED_BEEF,
            Material.COOKED_CHICKEN,
            Material.COOKED_COD,
            Material.COOKED_MUTTON,
            Material.COOKED_PORKCHOP,
            Material.COOKED_RABBIT,
            Material.COOKED_SALMON,
            Material.COOKIE,
            Material.DRIED_KELP,
            Material.ENCHANTED_GOLDEN_APPLE,
            Material.GOLDEN_APPLE,
            Material.GOLDEN_CARROT,
            Material.SPIDER_EYE,
            Material.ROTTEN_FLESH,
            Material.MELON,
            Material.MUSHROOM_STEW,
            Material.POISONOUS_POTATO,
            Material.POTATO,
            Material.PUMPKIN_PIE,
            Material.RABBIT_STEW,
            Material.TROPICAL_FISH,
            Material.BEEF,
            Material.CHICKEN,
            Material.COD,
            Material.MUTTON,
            Material.PORKCHOP,
            Material.RABBIT,
            Material.SALMON,
            Material.TROPICAL_FISH,
            Material.PUFFERFISH);
    public EatFoodEvent(Plugin plugin, EventData data) {
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
    public void onEat(PlayerItemConsumeEvent event){
        if(running){
            if(food.contains(event.getItem().getType())){
                addScore(event.getPlayer(),1);
            }
        }
    }
}
