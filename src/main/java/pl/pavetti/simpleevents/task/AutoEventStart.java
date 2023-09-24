package pl.pavetti.simpleevents.task;

import org.bukkit.scheduler.BukkitRunnable;
import pl.pavetti.simpleevents.config.Settings;
import pl.pavetti.simpleevents.manager.EventManager;
import pl.pavetti.simpleevents.model.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        Optional<Event> eventOptional = randomChoiceEvent();
        eventOptional.ifPresent(event ->{
            if(!eventManager.isRunning()){
                eventManager.startSimpleEvent(event.getData().getDefaultDuration(),event);
            }
        });
    }

    private Optional<Event> randomChoiceEvent(){
        if(eventsToAutoStart.size() == 0) return Optional.empty();
        else if(eventsToAutoStart.size() == 1) return Optional.of(eventManager.getRegisteredEvents().get(eventsToAutoStart.get(0)));
        else {
            Event event;
            String eventId;
            int randomInt;
            do {
                randomInt = random.nextInt(eventsToAutoStart.size());
                eventId = eventsToAutoStart.get(randomInt);
                event = eventManager.getRegisteredEvents().get(eventId);
            } while (eventId.equals(lastEventId) || eventId == null);
            lastEventId = eventId;
            return Optional.of(event);
        }

    }

}
