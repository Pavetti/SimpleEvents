package pl.pavetti.simpleevents.tabcompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import pl.pavetti.simpleevents.manager.SimpleEventsManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleEventTabCompleter implements TabCompleter {
    private final SimpleEventsManager simpleEventsManager;

    public SimpleEventTabCompleter(SimpleEventsManager simpleEventsManager) {
        this.simpleEventsManager = simpleEventsManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if(args.length == 1){
            completions.addAll(Arrays.asList("start","end","setprize","auto"));
        }
        else if (args.length == 2 && args[0].equalsIgnoreCase("start")) {
            completions.addAll(simpleEventsManager.getSimpleEvents().keySet());
        }
        else if(args.length == 3 && args[0].equalsIgnoreCase("start")){
            completions.add("<time>");
        }
        else if (args.length == 2 && args[0].equalsIgnoreCase("setprize")){
            completions.addAll(simpleEventsManager.getSimpleEvents().keySet());
        }
        else if (args.length == 2 && args[0].equalsIgnoreCase("auto")) {
            completions.addAll(Arrays.asList("on","off"));
        }
        return completions;
    }
}
