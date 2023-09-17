package pl.pavetti.simpleevents.model;


import lombok.Getter;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Getter
public abstract class SimpleEvent implements Listener {

    //class extended simple event has to has proper section in simpleEventsData.yml
    //name of class has to be the same plus "SimpleEvent" on end and upper case first letter
    //like:  class name - ExampleSimpleEvent, simpleEventsData.yml section name: example
    //       class name - SecondExampleSimpleEvent, simpleEventsData.yml section name: secondExample

    protected SimpleEventData data;
    protected boolean running = false;
    protected Map<UUID,Integer> score = new HashMap<>();

    abstract public void start();

    abstract public void stop();
}
