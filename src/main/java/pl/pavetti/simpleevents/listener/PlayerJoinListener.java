package pl.pavetti.simpleevents.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final boolean updateAvailable;

    public PlayerJoinListener(boolean updateAvailable) {
        this.updateAvailable = updateAvailable;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.isOp() && updateAvailable) {
            player.sendMessage(
                "﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉"
            );
            player.sendMessage(
                ChatColor.LIGHT_PURPLE +
                "[SimpleEvents] THERE IS A NEW UPDATE AVAILABLE!"
            );
            player.sendMessage(
                "﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍"
            );
        }
    }
}
