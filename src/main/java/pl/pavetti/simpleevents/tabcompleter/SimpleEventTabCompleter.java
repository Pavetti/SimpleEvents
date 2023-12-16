package pl.pavetti.simpleevents.tabcompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.pavetti.simpleevents.manager.EventManager;

public class SimpleEventTabCompleter implements TabCompleter {

    private final EventManager eventManager;

    public SimpleEventTabCompleter(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public List<String> onTabComplete(
        @NotNull CommandSender sender,
        @NotNull Command command,
        @NotNull String label,
        String[] args
    ) {
        List<String> completions = new ArrayList<>();

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("se.admin")) return completions;
        }

        if (args.length == 1) {
            completions.addAll(Arrays.asList("start", "end", "itemprize"));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("start")) {
            completions.addAll(eventManager.getRegisteredEvents().keySet());
        } else if (args.length == 3 && args[0].equalsIgnoreCase("start")) {
            completions.add("<time>");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("itemprize")) {
            completions.addAll(eventManager.getRegisteredEvents().keySet());
        }
        return completions;
    }
}
