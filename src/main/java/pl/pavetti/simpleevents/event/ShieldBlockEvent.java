package pl.pavetti.simpleevents.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;

public class ShieldBlockEvent extends Event {


    public ShieldBlockEvent(Plugin plugin, EventData data) {
        super(plugin, data);
    }

    @EventHandler
    public void onShieldBlockEBE(EntityDamageByEntityEvent event){
        if(running) {
            if (event.getEntity() instanceof Player) {
                if(event.getDamage(EntityDamageEvent.DamageModifier.BLOCKING) != 0) addScore((Player) event.getEntity(), 1);
            }
        }
    }

    @EventHandler
    public void onShieldBlockEBB(EntityDamageByBlockEvent event){
        if(running) {
            if (event.getEntity() instanceof Player) {
                if(event.getDamage(EntityDamageEvent.DamageModifier.BLOCKING) != 0) addScore((Player) event.getEntity(), 1);
            }
        }
    }
}
