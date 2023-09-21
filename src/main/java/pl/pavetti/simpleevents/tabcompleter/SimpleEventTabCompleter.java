package pl.pavetti.simpleevents.tabcompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import pl.pavetti.simpleevents.manager.EventManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleEventTabCompleter implements TabCompleter {
    private final EventManager eventManager;

    public SimpleEventTabCompleter(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        //TODO wylonczyc complete gdy nie ma permisji
        List<String> completions = new ArrayList<>();

        if(args.length == 1){
            completions.addAll(Arrays.asList("start","end","itemprize"));
        }
        else if (args.length == 2 && args[0].equalsIgnoreCase("start")) {
            completions.addAll(eventManager.getRegisteredEvents().keySet());
        }
        else if(args.length == 3 && args[0].equalsIgnoreCase("start")){
            completions.add("<time>");
        }
        else if (args.length == 2 && args[0].equalsIgnoreCase("itemprize")){
            completions.addAll(eventManager.getRegisteredEvents().keySet());
        }
        return completions;
    }
}
