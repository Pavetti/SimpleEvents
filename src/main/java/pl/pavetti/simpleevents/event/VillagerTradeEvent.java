package pl.pavetti.simpleevents.event;

import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;
@Deprecated
public class VillagerTradeEvent extends Event {
    //This event is not completely done
    public VillagerTradeEvent(Plugin plugin, EventData data) {
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


}
