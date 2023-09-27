package pl.pavetti.simpleevents.task;

import org.bukkit.scheduler.BukkitRunnable;
import pl.pavetti.simpleevents.config.Settings;
import pl.pavetti.simpleevents.manager.EventManager;
import pl.pavetti.simpleevents.model.Event;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class AutoEventStart extends BukkitRunnable {

    private final EventManager eventManager;
    private final Settings  settings;
    private String lastEventId;
    private final Random random = new Random();
    private final List<String> eventsToAutoStart = new ArrayList<>();

    public AutoEventStart(EventManager eventManager, Settings settings) {
        this.eventManager = eventManager;
        this.settings = settings;

        for (String eventId : settings.getEventsAutoActive()) {
            if(eventManager.getRegisteredEvents().keySet().contains(eventId)){
                eventsToAutoStart.add(eventId);
            }
        }
    }
    @Override
    public void run() {
        Optional<Event> eventOptional = randomChoiceEvent();
        eventOptional.ifPresent(event ->{
            if(!eventManager.isRunning()){
                if(isTime()) {
                    eventManager.startSimpleEvent(event.getData().getDefaultDuration(), event);
                }
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

    private boolean isTime(){
        //This function return true if current time is bigger than ActiveTimeForm and  smaller than ActiveTimeTo
        ZonedDateTime zonedDateTime = ZonedDateTime.now(settings.getTimeZone());
        LocalTime currentTime = zonedDateTime.toLocalTime();
        if(!settings.isImposedActiveTime()) return true;
        return currentTime.isAfter(settings.getActiveTimeFrom()) && currentTime.isBefore(settings.getActiveTimeTo());
    }
}
