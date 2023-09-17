package pl.pavetti.simpleevents.command.SimpleEventSubCommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pavetti.simpleevents.api.timsixth.SubCommand;
import pl.pavetti.simpleevents.config.Settings;
import pl.pavetti.simpleevents.manager.SimpleEventsManager;

public class StartSubCommand implements SubCommand {
    private final Settings settings;
    private final SimpleEventsManager simpleEventsManager;

    public StartSubCommand(Settings settings, SimpleEventsManager simpleEventsManager) {
        this.settings = settings;
        this.simpleEventsManager = simpleEventsManager;
    }

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        //check command format correctness
        if (args.length < 3) {
            player.sendMessage(settings.getBadCmdUseSEStart());
            return true;
        }

        //check time argument time correctness
        if (!args[2].matches("-?\\d+")) {
            player.sendMessage(settings.getBadArgumentTimeSEStart());
            return true;
        }

        String eventId = args[1];
        int duration = Integer.parseInt(args[2]);

        //check is simple event with given id exist
        if (!simpleEventsManager.isSimpleEvent(eventId)) {
            player.sendMessage(settings.getNoSimpleEventFound().replace("{EVENT}",eventId));
            return true;
        }

        //start simple event
        simpleEventsManager.startSimpleEvent(duration,simpleEventsManager.getSimpleEvents().get(eventId));
        player.sendMessage(settings.getSuccessfulStartSimpleEvent());
        return false;
    }

    @Override
    public String getName() {
        return "start";
    }
}
