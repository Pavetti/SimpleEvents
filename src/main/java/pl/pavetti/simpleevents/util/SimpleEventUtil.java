package pl.pavetti.simpleevents.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.stream.Collectors;

@UtilityClass
public class SimpleEventUtil {
    public String formatTime(int seconds){
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%d.%02d", minutes, remainingSeconds);
    }

    public String[] getFormatTop(Map<UUID,Integer> map,int rankingLines,String formatPattern){
        Map<UUID, Integer> top = getTop(map,rankingLines);
        String[] formatTop = new String[rankingLines];
        if(rankingLines != 0) {
            int count = 0;
            for (Map.Entry<UUID, Integer> entry : top.entrySet()) {
                formatTop[count] = formatPattern.replace("{POSITION}", String.valueOf(count + 1))
                        .replace("{PLAYER}", Bukkit.getOfflinePlayer(entry.getKey()).getName())
                        .replace("{SCORE}", String.valueOf(entry.getValue()));
                count++;
            }
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
        if(rankingLines != 0) {
            int count = 0;
            for (Map.Entry<UUID, Integer> entry : sortedMap.entrySet()) {
                top.put(entry.getKey(), entry.getValue());
                count++;
                if (count == rankingLines) {
                    break;
                }
            }
        }else {
            for (Map.Entry<UUID, Integer> entry : sortedMap.entrySet()) {
                top.put(entry.getKey(), entry.getValue());
                break;

            }
        }
        return top;
    }
}
