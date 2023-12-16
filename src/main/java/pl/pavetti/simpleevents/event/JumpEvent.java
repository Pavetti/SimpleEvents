package pl.pavetti.simpleevents.event;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;

public class JumpEvent extends Event {

    public JumpEvent(Plugin plugin, EventData data) {
        super(plugin, data);
    }

    @EventHandler
    public void onPlayerJump(PlayerMoveEvent event) {
        if (running) {
            Player player = event.getPlayer();
            Vector velocity = player.getVelocity();
            // Check if the player is moving "up"
            if (velocity.getY() > 0) {
                // Default jump velocity
                double jumpVelocity = 0.42F; // Default jump velocity
                PotionEffect jumpPotion = player.getPotionEffect(
                    PotionEffectType.JUMP
                );
                if (jumpPotion != null) {
                    // If player has jump potion add it to jump velocity
                    jumpVelocity +=
                    (double) ((float) jumpPotion.getAmplifier() + 1) * 0.1F;
                }
                // Check if player is not on ladder and if jump velocity calculated is equals to player Y velocity
                if (
                    player.getLocation().getBlock().getType() !=
                        Material.LADDER &&
                    Double.compare(velocity.getY(), jumpVelocity) == 0
                ) {
                    addScore(event.getPlayer(), 1);
                }
            }
        }
    }
}
