package pl.pavetti.simpleevents.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;

public class DealDamageEvent extends Event {
    public DealDamageEvent(Plugin plugin, EventData data) {
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
    public void onDealDamage(EntityDamageByEntityEvent event){
        if(running){
            if(event.getDamager() instanceof Player){
                addScore((Player) event.getDamager(), (int) event.getFinalDamage());
            }
        }
    }
}
