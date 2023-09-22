package pl.pavetti.simpleevents.task;

import org.bukkit.scheduler.BukkitRunnable;
import pl.pavetti.simpleevents.config.Settings;
import pl.pavetti.simpleevents.manager.EventManager;
import pl.pavetti.simpleevents.model.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AutoEventStart extends BukkitRunnable {

    private final EventManager eventManager;
    private String lastEventId;
    private final Random random = new Random();
    private final List<String> eventsToAutoStart = new ArrayList<>();

    public AutoEventStart(EventManager eventManager, Settings settings) {
        this.eventManager = eventManager;

        for (String eventId : settings.getEventsAutoStart()) {
            if(eventManager.getRegisteredEvents().keySet().contains(eventId)){
                eventsToAutoStart.add(eventId);
            }
        }
    }
    //TODO test it
    @Override
    public void run() {
        Event event = randomChoiceEvent();
        if(!eventManager.isRunning()){
            eventManager.startSimpleEvent(event.getData().getDefaultDuration(),event);
        }
    }

    private Event randomChoiceEvent(){
        Event event = null;
        String eventId = null;
        int randomInt;
        do{
            randomInt = random.nextInt(eventsToAutoStart.size());
            eventId = eventsToAutoStart.get(randomInt);
            event = eventManager.getRegisteredEvents().get(eventId);

        }while (eventId.equals(lastEventId) || eventId == null);
        lastEventId = eventId;
        return event;

    }

}
