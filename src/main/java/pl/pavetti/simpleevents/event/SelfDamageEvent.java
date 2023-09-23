package pl.pavetti.simpleevents.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;
@Deprecated
public class SelfDamageEvent extends Event {
    /*
    selfDamageEvent:
        id: SELF_DAMAGE
        displayName: '&6&lMaster of Careless'
        defaultDuration: 360
        description:
          - '&5The player who'
          - '&5deal the most'
          - '&5self damage'
          - '&5without dead wins'
        prizeEconomy: 3000
        prizeItems:
    * */
    public SelfDamageEvent(Plugin plugin, EventData data) {
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
    public void onPlayerSelfDamage(EntityDamageByBlockEvent event){
        if (running){

            addScore((Player) event.getEntity(), (int) event.getFinalDamage());
        }
    }

    @EventHandler
    public void onPlayerDead(EntityDeathEvent event){
        if(running){
            if(event.getEntity() instanceof Player) clearScore((Player) event.getEntity());
        }
    }
}
