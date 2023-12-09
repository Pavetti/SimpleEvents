package pl.pavetti.simpleevents.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;

public class MoveEvent extends Event {


    public MoveEvent(Plugin plugin, EventData data) {
        super(plugin, data);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        if(running){
            Player player = event.getPlayer();
            if (player.isFlying() || player.isGliding() || player.getVehicle() != null) return;

            int distance = (event.getTo().getBlockX()-event.getFrom().getBlockX())^2 + (event.getTo().getBlockZ()-event.getFrom().getBlockZ())^2;
            addScore(event.getPlayer(), (int) Math.sqrt(distance));
        }
    }
}
