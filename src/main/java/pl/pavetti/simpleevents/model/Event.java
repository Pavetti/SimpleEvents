package pl.pavetti.simpleevents.model;


import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Getter
@Setter
public abstract class Event implements Listener {

    //class extended simple event has to has proper section in eventData.yml
    //name of class has to be the same plus "SimpleEvent" on end and upper case first letter
    //like:  class name - ExampleSimpleEvent, eventData.yml section name: example
    //       class name - SecondExampleSimpleEvent, eventData.yml section name: secondExample

    protected EventData data;
    protected boolean running = false;
    protected Map<UUID,Integer> score = new HashMap<>();

    abstract public void start();

    abstract public void stop();
}
