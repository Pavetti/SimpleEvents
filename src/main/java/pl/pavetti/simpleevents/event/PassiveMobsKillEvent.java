package pl.pavetti.simpleevents.event;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;

public class PassiveMobsKillEvent extends Event {

    public PassiveMobsKillEvent(Plugin plugin, EventData data) {
        super(plugin, data);
    }

    @EventHandler
    public void onPassiveMobKillEvent(EntityDeathEvent event) {
        if (running) {
            if (
                event.getEntity() instanceof Creature &&
                !(event.getEntity() instanceof Monster)
            ) {
                if (event.getEntity().getKiller() != null) addScore(
                    event.getEntity().getKiller(),
                    1
                );
            }
        }
    }
}
