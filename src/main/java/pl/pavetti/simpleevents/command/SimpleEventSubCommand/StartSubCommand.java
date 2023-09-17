package pl.pavetti.simpleevents.command.SimpleEventSubCommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pavetti.simpleevents.api.timsixth.SubCommand;
import pl.pavetti.simpleevents.config.Settings;
import pl.pavetti.simpleevents.manager.SimpleEventsManager;
import pl.pavetti.simpleevents.util.PlayerUtil;

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
        String prefix = settings.getPrefix();

        //check command format correctness
        if (args.length < 3) {
            PlayerUtil.sendMessage(player,prefix,settings.getBadCmdUseSEStart());
            return true;
        }

        //check time argument time correctness
        if (!args[2].matches("-?\\d+")) {
            PlayerUtil.sendMessage(player,prefix,settings.getBadArgumentTimeSEStart());
            return true;
        }

        String eventId = args[1];
        int duration = Integer.parseInt(args[2]);

        //check is simple event with given id exist
        if (!simpleEventsManager.isSimpleEvent(eventId)) {
            PlayerUtil.sendMessage(player,prefix,settings.getNoSimpleEventFound().replace("{EVENT}",eventId));
            return true;
        }

        if(simpleEventsManager.isRunning()){
            PlayerUtil.sendMessage(player,prefix,settings.getEventAlreadyActive());
            return true;
        }

        //start simple event
        simpleEventsManager.startSimpleEvent(duration,simpleEventsManager.getSimpleEvents().get(eventId));
        PlayerUtil.sendMessage(player,prefix,settings.getSuccessfulStartSimpleEvent());
        return false;
    }

    @Override
    public String getName() {
        return "start";
    }
}
