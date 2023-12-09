package pl.pavetti.simpleevents.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;

public class EnchantItemEvent extends Event {


    public EnchantItemEvent(Plugin plugin, EventData data) {
        super(plugin, data);
    }

    @EventHandler
    public void onEnchantItem(org.bukkit.event.enchantment.EnchantItemEvent event){
        if(running){
            addScore((Player) event.getView().getPlayer(),1);
        }
    }
}
