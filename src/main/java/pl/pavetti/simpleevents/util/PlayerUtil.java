package pl.pavetti.simpleevents.util;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

import java.util.List;

@UtilityClass
public class PlayerUtil {
    public static void sendMessage(Player player, List<String> lines){
        lines.forEach(player::sendMessage);
    }

}
