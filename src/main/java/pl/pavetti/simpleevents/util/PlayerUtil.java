package pl.pavetti.simpleevents.util;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

import java.util.List;

@UtilityClass
public class PlayerUtil {
    public static void sendMessage(Player player, List<String> lines){
        lines.forEach(player::sendMessage);
    }
    public static void sendMessage(Player player,String prefix, String message){
        player.sendMessage(prefix + message);
    }

}
