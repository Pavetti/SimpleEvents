package pl.pavetti.simpleevents.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;

public class PlayerKillEvent extends Event {
    public PlayerKillEvent(Plugin plugin, EventData data) {
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
    //TODO test it
    @EventHandler
    public void onKillPlayerByPlayer(EntityDeathEvent event){
        if(event.getEntity() instanceof Player){
            addScore(event.getEntity().getKiller(), 1);
        }
    }
}
