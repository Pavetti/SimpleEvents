package pl.pavetti.simpleevents.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;

public class GetDamageNoDeathEvent extends Event {

    public GetDamageNoDeathEvent(Plugin plugin, EventData data) {
        super(plugin, data);
    }

    @EventHandler
    public void onPlayerSelfDamage(EntityDamageEvent event) {
        if (running) {
            if (event.getEntity() instanceof Player) {
                addScore(
                    (Player) event.getEntity(),
                    (int) event.getFinalDamage()
                );
            }
        }
    }

    @EventHandler
    public void onPlayerDead(EntityDeathEvent event) {
        if (running) {
            if (event.getEntity() instanceof Player) clearScore(
                (Player) event.getEntity()
            );
        }
    }
}
