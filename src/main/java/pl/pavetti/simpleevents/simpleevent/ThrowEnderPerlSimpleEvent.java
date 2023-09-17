package pl.pavetti.simpleevents.simpleevent;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.SimpleEvent;
import pl.pavetti.simpleevents.model.SimpleEventData;

import java.util.HashMap;
import java.util.UUID;

public class ThrowEnderPerlSimpleEvent extends SimpleEvent {
    private static final int COOLDOWN_TIME_IN_SECOND = 1;
    private final HashMap<UUID, Long> cooldowns = new HashMap<>();

    public ThrowEnderPerlSimpleEvent(Plugin plugin, SimpleEventData simpleEventData) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        data = simpleEventData;
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

    public void addThrewPerl(Player player, int amount) {

        if (running && score.containsKey(player.getUniqueId())) {
            int currentAmount = score.get(player.getUniqueId());
            score.put(player.getUniqueId(), currentAmount + amount);
        }
        else if (running && !score.containsKey(player.getUniqueId())) {
            score.put(player.getUniqueId(), amount);
        }
    }

    @EventHandler
    public void onPlayerThrowPearl(PlayerInteractEvent event){

        if(running){
            Player player = event.getPlayer();
            ItemStack item = event.getItem();
            if(event.getAction().toString().contains("RIGHT_CLICK") && item != null && item.getType() == Material.ENDER_PEARL){
                if (!hasCooldown(player)) {
                    addThrewPerl(player,1);
                    setCooldown(player);
                }
            }
        }
    }
    private boolean hasCooldown(Player player) {

        if (cooldowns.containsKey(player.getUniqueId())) {
            long cooldownTime = cooldowns.get(player.getUniqueId());
            long currentTime = System.currentTimeMillis();
            return (currentTime - cooldownTime) < COOLDOWN_TIME_IN_SECOND * 1000;
        }
        return false;
    }

    private void setCooldown(Player player) {
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
    }
}
