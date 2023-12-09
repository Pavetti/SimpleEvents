package pl.pavetti.simpleevents.model;


import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public abstract class Event implements Listener {
    /*
    *   class extended simple event has to has proper section in eventData.yml
    *    name of class has to be the same plus "SimpleEvent" on end and upper case first letter
    *    like:  class name - ExampleSimpleEvent, eventData.yml section name: example
    *           class name - SecondExampleSimpleEvent, eventData.yml section name: secondExample
    */

    public Event(Plugin plugin, EventData data) {
        this.data = data;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    protected EventData data;
    protected boolean running = false;
    protected Map<UUID,Integer> score = new HashMap<>();

    public void start(){
        score.clear();
        running = true;
    }

   public void stop(){
        running = false;
   }

    protected void addScore(Player player, int amount) {
        if(!player.hasPermission("se.noattend")) {
            if (running && score.containsKey(player.getUniqueId())) {
                int currentAmount = score.get(player.getUniqueId());
                score.put(player.getUniqueId(), currentAmount + amount);
            } else if (running && !score.containsKey(player.getUniqueId())) {
                score.put(player.getUniqueId(), amount);
            }
        }
    }

    protected void clearScore(Player player) {
        if (running && score.containsKey(player.getUniqueId())) {
            score.put(player.getUniqueId(), 0);
        }
    }
}
