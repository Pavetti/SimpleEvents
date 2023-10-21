package pl.pavetti.simpleevents.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pavetti.simpleevents.api.timsixth.ParentCommand;
import pl.pavetti.simpleevents.config.PlayerDataFile;
import pl.pavetti.simpleevents.config.Settings;
import pl.pavetti.simpleevents.util.PlayerUtil;

import java.util.UUID;

public class SEScoreCommand extends ParentCommand {
    private final Settings settings;
    private final PlayerDataFile playerDataFile;
    public SEScoreCommand(Settings settings, PlayerDataFile playerDataFile) {
        super("", true, true, false, settings);
        this.settings = settings;

        this.playerDataFile = playerDataFile;
    }

    @Override
    protected boolean executeCommand(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;

        if(args.length < 1){
            PlayerUtil.sendMessage(player,settings.getPrefix(),settings.getBadCmdUseSEScoreShow());
            return true;
        }

        if(args[0].equals("on")){
            playerDataFile.setPlayerDataScoreShow(player.getUniqueId(),true);
            PlayerUtil.sendMessage(player,settings.getPrefix(),settings.getSuccessfulSwitchScoreShow().replace("{SWITCH}","ON"));
        } else if (args[0].equals("off")) {
            playerDataFile.setPlayerDataScoreShow(player.getUniqueId(),false);
            PlayerUtil.sendMessage(player,settings.getPrefix(),settings.getSuccessfulSwitchScoreShow().replace("{SWITCH}","OFF"));
        }else {
            PlayerUtil.sendMessage(player,settings.getPrefix(),settings.getBadCmdUseSEScoreShow());
        }


        return false;
    }

}
