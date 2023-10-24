package pl.pavetti.simpleevents.util;

import lombok.experimental.UtilityClass;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@UtilityClass
public class PlayerUtil {
    public static void sendMessage(Player player, List<String> lines){
        lines.forEach(player::sendMessage);
    }
    public static void sendMessage(Player player,String prefix, String message){
        player.sendMessage(prefix + message);
    }
    public static void addItem(Player player, ItemStack itemStack){
        boolean drop = true;
        for (int i = 0; i < player.getInventory().getContents().length; i++) {
            if(i == 36 || i == 37 || i == 38 || i == 39 || i == 40) continue;
            if (player.getInventory().getItem(i) == null) {
                System.out.println(i);
                player.getInventory().setItem(i, itemStack);
                drop = false;
                break;
            }
        }
        if(drop) player.getWorld().dropItem(player.getLocation(),itemStack);
    }
}
