package pl.pavetti.simpleevents.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.pavetti.simpleevents.manager.EventManager;
import pl.pavetti.simpleevents.manager.ScoreboardManager;


public class PlayerQuitListener implements Listener {

    private final ScoreboardManager scoreboardManager;

    public PlayerQuitListener(EventManager eventManager) {
        this.scoreboardManager = eventManager.getScoreBoardManager();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        scoreboardManager.removeScoreboard(event.getPlayer());
    }
}
