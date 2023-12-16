package pl.pavetti.simpleevents.command.SimpleEventSubCommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pavetti.simpleevents.api.timsixth.SubCommand;
import pl.pavetti.simpleevents.config.Settings;
import pl.pavetti.simpleevents.manager.EventManager;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.util.PlayerUtil;

public class StartSubCommand implements SubCommand {

    private final Settings settings;
    private final EventManager eventManager;

    public StartSubCommand(Settings settings, EventManager eventManager) {
        this.settings = settings;
        this.eventManager = eventManager;
    }

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        String prefix = settings.getPrefix();

        //check command format correctness
        if (args.length >= 3) {
            //with time argument
            if (!args[2].matches("-?\\d+")) {
                //check time argument time correctness
                PlayerUtil.sendMessage(
                    player,
                    prefix,
                    settings.getBadArgumentTimeSEStart()
                );
                return true;
            }
            String eventId = args[1];
            int duration = Integer.parseInt(args[2]);
            return checkEventCorrectAndStart(prefix, player, eventId, duration);
        } else if (args.length == 2) {
            //without time argument
            String eventId = args[1];
            return checkEventCorrectAndStart(prefix, player, eventId, 0);
        } else {
            //bad format
            PlayerUtil.sendMessage(
                player,
                prefix,
                settings.getBadCmdUseSEStart()
            );
            return true;
        }
    }

    private boolean checkEventCorrectAndStart(
        String prefix,
        Player player,
        String eventId,
        int duration
    ) {
        //check is simple event with given id exist
        if (!eventManager.isSimpleEvent(eventId)) {
            PlayerUtil.sendMessage(
                player,
                prefix,
                settings.getNoEventFound().replace("{EVENT}", eventId)
            );
            return true;
        }
        if (eventManager.isRunning()) {
            PlayerUtil.sendMessage(
                player,
                prefix,
                settings.getEventAlreadyActive()
            );
            return true;
        }
        Event event = eventManager.getRegisteredEvents().get(eventId);
        if (duration == 0) {
            duration = event.getData().getDefaultDuration();
        }
        //start simple event
        eventManager.startSimpleEvent(duration, event);
        PlayerUtil.sendMessage(
            player,
            prefix,
            settings.getSuccessfulStartEvent()
        );
        return false;
    }

    @Override
    public String getName() {
        return "start";
    }
}
