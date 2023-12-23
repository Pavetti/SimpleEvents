package pl.pavetti.simpleevents.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.pavetti.simpleevents.manager.EventManager;
import pl.pavetti.simpleevents.manager.ScoreBoardManager;

public class PlayerJoinListener implements Listener {

    private final boolean updateAvailable;
    private final EventManager eventManager;
    private final ScoreBoardManager scoreBoardManager;

    public PlayerJoinListener(boolean updateAvailable, EventManager eventManager ) {
        this.updateAvailable = updateAvailable;
        this.eventManager = eventManager;
        this.scoreBoardManager = eventManager.getScoreBoardManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //FOR OPERATOR
        if (player.isOp() && updateAvailable) {
            player.sendMessage(
                "﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉"
            );
            player.sendMessage(ChatColor.LIGHT_PURPLE + "[SimpleEvents] THERE IS A NEW UPDATE AVAILABLE!");
            player.sendMessage(ChatColor.LIGHT_PURPLE + "https://www.spigotmc.org/resources/simpleevents-server-events-system.112876/");
            player.sendMessage(
                "﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍"
            );
        }

        if(eventManager.isRunning()){
            scoreBoardManager.showScoreboard(player);
        }

    }
}
