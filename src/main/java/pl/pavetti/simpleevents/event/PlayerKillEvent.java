package pl.pavetti.simpleevents.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;

public class PlayerKillEvent extends Event {


    public PlayerKillEvent(Plugin plugin, EventData data) {
        super(plugin, data);
    }

    @EventHandler
    public void onKillPlayerByPlayer(EntityDeathEvent event){
        if(running) {
            if (event.getEntity() instanceof Player) {
                if (event.getEntity().getKiller() != null) addScore(event.getEntity().getKiller(), 1);
            }
        }
    }
}
