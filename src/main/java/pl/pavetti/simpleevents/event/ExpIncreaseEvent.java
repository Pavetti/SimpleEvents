package pl.pavetti.simpleevents.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;

public class ExpIncreaseEvent extends Event {

    public ExpIncreaseEvent(Plugin plugin, EventData data) {
        super(plugin, data);
    }

    @EventHandler
    public void onExpIncrease(PlayerExpChangeEvent event) {
        if (running) {
            int amount = event.getAmount();
            if (amount > 0) addScore(event.getPlayer(), amount);
        }
    }
}
