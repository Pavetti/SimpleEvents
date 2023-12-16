package pl.pavetti.simpleevents.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;

public class DeathEvent extends Event {

    public DeathEvent(Plugin plugin, EventData data) {
        super(plugin, data);
    }

    @EventHandler
    public void onPlayerDeath(EntityDeathEvent event) {
        if (running) {
            if (event.getEntity() instanceof Player) {
                addScore((Player) event.getEntity(), 1);
            }
        }
    }
}
