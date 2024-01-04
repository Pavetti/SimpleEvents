package pl.pavetti.simpleevents.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.pavetti.simpleevents.api.fastboard.FastBoard;
import pl.pavetti.simpleevents.config.PlayerDataFile;
import pl.pavetti.simpleevents.config.Settings;
import pl.pavetti.simpleevents.model.EventData;
import pl.pavetti.simpleevents.util.EventUtil;

import java.util.*;

import static pl.pavetti.simpleevents.util.ChatUtil.chatColor;

public class ScoreboardManager {
    private final Map<UUID, FastBoard> boards = new HashMap<>();
    private final Settings settings;
    private final int rankingPlaces;
    private final PlayerDataFile playerDataFile;
    private int linesAmount = 0;
    private List<String> currentTemplate = null;

    public ScoreboardManager(Settings settings, PlayerDataFile playerDataFile) {
        this.settings = settings;
        this.rankingPlaces = settings.getScoreboardRankingLines();
        this.playerDataFile = playerDataFile;
    }

    public void registerScoreboard(Player player){
        boards.put(player.getUniqueId(), null);
    }
    public void registerScoreboard(Player player, EventData data){
        FastBoard board = new FastBoard(player);
        board.updateTitle(chatColor(data.getName()));
        board.updateLines(currentTemplate);
        boards.put(player.getUniqueId(),board);
    }

    public void removeScoreboard(Player player) {
        FastBoard board = boards.remove(player.getUniqueId());
        if (board != null) {
            board.delete();
        }
    }

    public void createNewScoreboard(int time, EventData data){
        this.currentTemplate = createTemplate(time,data);
        for (UUID uuid : boards.keySet()) {
            FastBoard board = boards.get(uuid);
            if(board == null){
                Player player = Bukkit.getPlayer(uuid);
                if(player == null) continue;
                board = new FastBoard(player);
                board.updateTitle(chatColor(data.getName()));
                board.updateLines(currentTemplate);
                boards.put(uuid,board);
            }
        }
    }

    public void updateScoreboard(int time, String[] top){
        for (FastBoard board : boards.values()) {
            board.updateLine(
                    linesAmount - 1,
                    settings
                            .getScoreboardTimeLineFormat()
                            .replace("{TIME}", EventUtil.formatTime(time))
            );
            int index;

            for (int i = 0; i < rankingPlaces; i++) {
                index = this.linesAmount - (2 + rankingPlaces) + i;
                if (top[i] != null) {
                    board.updateLine(index, top[i]);
                } else {
                    board.updateLine(
                            index,
                            settings
                                    .getScoreboardRankingLineFormat()
                                    .replace("{POSITION}", String.valueOf(i + 1))
                                    .replace("{PLAYER}", "")
                                    .replace("{SCORE}", "")
                    );
                }
            }
        }
    }
    public void closeBoardForAll(){
        for (UUID uuid : boards.keySet()) {
            closeBoard(Bukkit.getPlayer(uuid));
        }
    }
    public void closeBoard(Player player){
        UUID uuid = player.getUniqueId();
        if(boards.containsKey(uuid)){
            boards.get(uuid).delete();
            boards.put(uuid,null);
        }
    }

    public void reset(){
        linesAmount = 0;
    }

    private List<String> createTemplate(int time, EventData data){
        List<String> lines = new ArrayList<>();
        lines.add("");
        linesAmount++;
        for (String descLine : data.getDescription()) {
            lines.add(chatColor(descLine));
            linesAmount++;
        }
        lines.add("");
        linesAmount++;
        for (int i = 1; i <= rankingPlaces; i++) {
            lines.add(settings
                    .getScoreboardRankingLineFormat()
                    .replace("{POSITION}", String.valueOf(i))
                    .replace("{PLAYER}", "")
                    .replace("{SCORE}", "")
            );
            linesAmount++;
        }
        lines.add("");
        linesAmount++;
        lines.add(chatColor(settings
                        .getScoreboardTimeLineFormat()
                        .replace("{TIME}", EventUtil.formatTime(time))
        ));
        linesAmount++;
        return lines;
    }

    public boolean containPlayer(Player player){
        return boards.containsKey(player.getUniqueId());
    }
}
