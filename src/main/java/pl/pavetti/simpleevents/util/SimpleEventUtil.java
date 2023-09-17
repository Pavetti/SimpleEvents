package pl.pavetti.simpleevents.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.util.*;

@UtilityClass
public class SimpleEventUtil {
    public String formatTime(int seconds){
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%d.%02d", minutes, remainingSeconds);
    }

    public String[] getFormatTop(Map<UUID,Integer> map,int rankingLines){
        Map<UUID, Integer> top = getTop(map,rankingLines);
        String[] formatTop = new String[rankingLines];
        int count = 0;
        for (Map.Entry<UUID, Integer> entry : top.entrySet()) {
            formatTop[count] = ChatUtil.chatColor("&6&l " + (count + 1) + "► &r&b" + Bukkit.getOfflinePlayer(entry.getKey()).getName() + " " + entry.getValue());
            count++;
        }
        return formatTop;
    }
    public Map<UUID,Integer> getTop(Map<UUID,Integer> map,int rankingLines){
        // Convert map to list
        List<Map.Entry<UUID, Integer>> list = new ArrayList<>(map.entrySet());
        // Sort list by integer value (up to down)
        list.sort(Map.Entry.<UUID, Integer>comparingByValue().reversed());
        // Creating LinkedHashMap to save order
        Map<UUID, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<UUID, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        Map<UUID, Integer> top = new LinkedHashMap<>();
        int count = 0;
        for (Map.Entry<UUID, Integer> entry : sortedMap.entrySet()) {
            if (count == rankingLines - 1) {
                break;
            }
            top.put(entry.getKey(), entry.getValue());
            count++;
        }
        return top;
    }
}
