package pl.pavetti.simpleevents.command.SimpleEventSubCommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pavetti.simpleevents.api.timsixth.SubCommand;
import pl.pavetti.simpleevents.config.Settings;
import pl.pavetti.simpleevents.manager.EventManager;
import pl.pavetti.simpleevents.util.PlayerUtil;

public class EndSubCommand implements SubCommand {

    private final EventManager eventManager;
    private final Settings settings;

    public EndSubCommand(EventManager eventManager, Settings settings) {
        this.eventManager = eventManager;
        this.settings = settings;
    }

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if(eventManager.isRunning()){
            eventManager.setGoEnd(true);
            PlayerUtil.sendMessage(player,settings.getPrefix(),settings.getSuccessfulEndEvent());
        }else PlayerUtil.sendMessage(player,settings.getPrefix(),settings.getNothing());
        return false;
    }

    @Override
    public String getName() {
        return "end";
    }
}