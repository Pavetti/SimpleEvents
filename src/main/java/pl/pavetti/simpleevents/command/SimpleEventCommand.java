package pl.pavetti.simpleevents.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pavetti.simpleevents.api.timsixth.ParentCommand;
import pl.pavetti.simpleevents.command.SimpleEventSubCommand.EndSubCommand;
import pl.pavetti.simpleevents.command.SimpleEventSubCommand.ItemPrizeSubCommand;
import pl.pavetti.simpleevents.command.SimpleEventSubCommand.StartSubCommand;
import pl.pavetti.simpleevents.config.EventDataFile;
import pl.pavetti.simpleevents.config.Settings;
import pl.pavetti.simpleevents.manager.EventManager;
import pl.pavetti.simpleevents.util.PlayerUtil;


public class SimpleEventCommand extends ParentCommand {
    private final Settings settings;
    public SimpleEventCommand(Settings settings, EventManager eventManager, EventDataFile eventDataFile) {
        super("se.admin", true, true, true, settings);
        this.settings = settings;

        getSubCommands().add(new StartSubCommand(settings, eventManager));
        getSubCommands().add(new EndSubCommand(eventManager, settings));
        getSubCommands().add(new ItemPrizeSubCommand(settings,eventDataFile, eventManager));
    }

    @Override
    protected boolean executeCommand(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;

        //sends to player info about subcommands
        PlayerUtil.sendMessage(player,settings.getHelp());

        return false;
    }

}
