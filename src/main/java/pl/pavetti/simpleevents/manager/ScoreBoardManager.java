package pl.pavetti.simpleevents.manager;

import static pl.pavetti.simpleevents.util.ChatUtil.chatColor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import pl.pavetti.simpleevents.api.ScoreboardWrapper;
import pl.pavetti.simpleevents.config.PlayerDataFile;
import pl.pavetti.simpleevents.config.Settings;
import pl.pavetti.simpleevents.model.EventData;
import pl.pavetti.simpleevents.model.PlayerData;
import pl.pavetti.simpleevents.util.EventUtil;

@Setter
@Getter
public class ScoreBoardManager {

    //variable linesCount determine amount of lines in scoreboard
    private int linesAmount = 0;
    private ScoreboardWrapper board;

    private final int rankingPlaces;
    private final Settings settings;
    private final PlayerDataFile playerDataFile;
    private final Map<UUID, Scoreboard> oldScores = new HashMap<>();

    public ScoreBoardManager(
        Settings settings,
        int rankingPlaces,
        PlayerDataFile playerData
    ) {
        this.settings = settings;
        this.rankingPlaces = rankingPlaces;
        this.playerDataFile = playerData;
    }

    public void createScoreBoard(int time, EventData data) {
        board = new ScoreboardWrapper(chatColor(data.getName()));
        board.addBlankSpace();
        linesAmount++;

        for (String line : data.getDescription()) {
            board.addLine(chatColor(line));
            linesAmount++;
        }

        board.addBlankSpace();
        linesAmount++;

        for (int i = 1; i <= rankingPlaces; i++) {
            board.addLine(
                settings
                    .getScoreboardRankingLineFormat()
                    .replace("{POSITION}", String.valueOf(i))
                    .replace("{PLAYER}", "")
                    .replace("{SCORE}", "")
            );
            linesAmount++;
        }

        board.addBlankSpace();
        linesAmount++;
        board.addLine(
            chatColor(
                settings
                    .getScoreboardTimeLineFormat()
                    .replace("{TIME}", EventUtil.formatTime(time))
            )
        );
        linesAmount++;
    }

    public void updateScoreboard(int time, String[] top) {
        board.setLine(
            linesAmount - 1,
            settings
                .getScoreboardTimeLineFormat()
                .replace("{TIME}", EventUtil.formatTime(time))
        );
        int index;

        for (int i = 0; i < rankingPlaces; i++) {
            index = this.linesAmount - (2 + rankingPlaces) + i;
            if (top[i] != null) {
                board.setLine(index, top[i]);
            } else {
                board.setLine(
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

    public void showScoreBoardForALl() {
        saveScores();
        for (Player player : Bukkit.getOnlinePlayers()) {
            Optional<PlayerData> playerDataOptional =
                playerDataFile.getPlayerDataOf(player.getUniqueId());
            if (playerDataOptional.isPresent()) {
                if (playerDataOptional.get().isScoreboardShow()) {
                    player.setScoreboard(board.getScoreboard());
                }
            } else {
                player.setScoreboard(board.getScoreboard());
            }
        }
    }

    private void saveScores() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            oldScores.put(
                onlinePlayer.getUniqueId(),
                onlinePlayer.getScoreboard()
            );
        }
    }

    public void closeScoreBoardForALl() {
        oldScores.forEach(
            ((uuid, scoreboard) -> {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
                    if (offlinePlayer.isOnline()) {
                        Player player = (Player) offlinePlayer;
                        player.setScoreboard(scoreboard);
                    }
                })
        );
    }

    public void reset() {
        linesAmount = 0;
    }
}
