package pl.pavetti.simpleevents.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pavetti.simpleevents.api.timsixth.ParentCommand;
import pl.pavetti.simpleevents.config.PlayerDataFile;
import pl.pavetti.simpleevents.config.Settings;
import pl.pavetti.simpleevents.manager.EventManager;
import pl.pavetti.simpleevents.manager.ScoreboardManager;
import pl.pavetti.simpleevents.util.PlayerUtil;

import java.util.Optional;
import java.util.UUID;

public class ScoreboardToggleCommand extends ParentCommand {

    private final Settings settings;
    private final PlayerDataFile playerDataFile;
    private final ScoreboardManager scoreBoardManager;
    private final EventManager eventManager;
    public ScoreboardToggleCommand(Settings settings, PlayerDataFile playerDataFile, EventManager eventManager) {
        super("", false, true, false, settings);
        this.settings = settings;
        this.playerDataFile = playerDataFile;
        this.scoreBoardManager = eventManager.getScoreBoardManager();
        this.eventManager = eventManager;
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
                playerDataFile.setPlayerDataScoreShow(player.getUniqueId(), false);
                if(eventManager.isRunning()){
                    scoreBoardManager.closeBoard(player);
                }
                scoreBoardManager.removeScoreboard(player);
            }else {
                PlayerUtil.sendMessage(player, settings.getPrefix(), settings.getSuccessfulSwitchScoreShowToEnable());
                playerDataFile.setPlayerDataScoreShow(player.getUniqueId(), true);
                scoreBoardManager.registerScoreboard(player);
                if(eventManager.isRunning()){
                    scoreBoardManager.registerScoreboard(player,eventManager.getCurrentEvent().getData());
                }else {
                    scoreBoardManager.registerScoreboard(player);
                }
            }
        }
        else {
            playerDataFile.setPlayerDataScoreShow(player.getUniqueId(), false);
            PlayerUtil.sendMessage(player, settings.getPrefix(), settings.getSuccessfulSwitchScoreShowToDisable());
            if(eventManager.isRunning()){
                scoreBoardManager.closeBoard(player);
            }
            scoreBoardManager.removeScoreboard(player);
        }
        return false;
    }
}
