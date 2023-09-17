package pl.pavetti.simpleevents.command.SimpleEventSubCommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pavetti.simpleevents.api.timsixth.SubCommand;
import pl.pavetti.simpleevents.config.Settings;
import pl.pavetti.simpleevents.manager.SimpleEventsManager;
import pl.pavetti.simpleevents.util.PlayerUtil;

public class EndSubCommand implements SubCommand {

    private final SimpleEventsManager manager;
    private final Settings settings;

    public EndSubCommand(SimpleEventsManager simpleEventsManager, Settings settings) {
        this.manager = simpleEventsManager;
        this.settings = settings;
    }

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if(manager.isRunning()){
            manager.setGoEnd(true);
            PlayerUtil.sendMessage(player,settings.getPrefix(),settings.getSuccessfulEndSimpleEvent());
        }else PlayerUtil.sendMessage(player,settings.getPrefix(),settings.getNothing());
        return false;
    }

    @Override
    public String getName() {
        return "end";
    }
}
