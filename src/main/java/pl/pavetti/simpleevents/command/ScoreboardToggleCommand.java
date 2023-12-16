package pl.pavetti.simpleevents.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pavetti.simpleevents.api.timsixth.ParentCommand;
import pl.pavetti.simpleevents.config.PlayerDataFile;
import pl.pavetti.simpleevents.config.Settings;
import pl.pavetti.simpleevents.util.PlayerUtil;

import java.util.Optional;
import java.util.UUID;

public class ScoreboardToggleCommand extends ParentCommand {

    private final Settings settings;
    private final PlayerDataFile playerDataFile;

    public ScoreboardToggleCommand(Settings settings, PlayerDataFile playerDataFile) {
        super("", false, true, false, settings);
        this.settings = settings;
        this.playerDataFile = playerDataFile;
    }

    @Override
    protected boolean executeCommand(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;
        UUID uuid = player.getUniqueId();
        Optional<Boolean> scoreShowOptional = playerDataFile.getPlayerDataScoreShow(uuid);

        if(scoreShowOptional.isPresent()){
            boolean scoreShow = scoreShowOptional.get();
            if(scoreShow){
                PlayerUtil.sendMessage(player, settings.getPrefix(), settings.getSuccessfulSwitchScoreShowToDisable());
            }else {
                PlayerUtil.sendMessage(player, settings.getPrefix(), settings.getSuccessfulSwitchScoreShowToEnable());
            }
            scoreShow = !scoreShow;
            playerDataFile.setPlayerDataScoreShow(player.getUniqueId(), scoreShow);
        }
        else {
            playerDataFile.setPlayerDataScoreShow(player.getUniqueId(), false);
            PlayerUtil.sendMessage(player, settings.getPrefix(), settings.getSuccessfulSwitchScoreShowToDisable());
        }
        return false;
    }
}
