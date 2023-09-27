package pl.pavetti.simpleevents.manager;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import pl.pavetti.simpleevents.api.ScoreboardWrapper;
import pl.pavetti.simpleevents.config.Settings;
import pl.pavetti.simpleevents.model.EventData;
import pl.pavetti.simpleevents.util.EventUtil;
import static pl.pavetti.simpleevents.util.ChatUtil.chatColor;

@Setter
@Getter
public class ScoreBoardManager {

    //variable linesCount determine amount of lines in scoreboard
    private int linesAmount = 0;
    private ScoreboardWrapper board;

    private final int rankingPlaces;
    private final Settings settings;
    private Scoreboard deafultScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();;

    public ScoreBoardManager(Settings settings, int rankingPlaces){
        this.settings = settings;
        this.rankingPlaces = rankingPlaces;
    }

    public void createScoreBoard(int time,  EventData data){

        board = new ScoreboardWrapper(chatColor(data.getName()));
        board.addBlankSpace();
        linesAmount++;

        for (String line : data.getDescription()) {
            board.addLine(chatColor(line));
            linesAmount++;
        }

        board.addBlankSpace();
        linesAmount++;

        for (int i = 1; i <= rankingPlaces; i++){
            board.addLine(settings.getScoreboardRankingLineFormat().replace("{POSITION}",String.valueOf(i))
                    .replace("{PLAYER}","")
                    .replace("{SCORE}", ""));
            linesAmount++;
        }

        board.addBlankSpace();
        linesAmount++;
        board.addLine(chatColor(settings.getScoreboardTimeLineFormat().replace("{TIME}", EventUtil.formatTime(time))));
        linesAmount++;
    }
    public void updateScoreboard(int time, String[] top){
        board.setLine(linesAmount - 1,settings.getScoreboardTimeLineFormat().replace("{TIME}", EventUtil.formatTime(time)));
        int index;

        for (int i = 0; i < rankingPlaces; i++) {
            index = this.linesAmount - (2 + rankingPlaces) + i;
            if(top[i] != null){
                board.setLine(index,top[i]);
            }else{
                board.setLine(index,settings.getScoreboardRankingLineFormat().replace("{POSITION}",String.valueOf(i + 1))
                        .replace("{PLAYER}","")
                        .replace("{SCORE}", ""));
            }
        }
    }
    public void showScoreBoardForALl(){
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(board.getScoreboard());
        }
    }

    public void closeScoreBoardForALl(){
        if(deafultScoreboard != null){
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.setScoreboard(deafultScoreboard);
//            Scoreboard scoreboard = player.getScoreboard();
//            Objective objective = scoreboard.getObjective("dummy");
//            if (objective != null) {
//                objective.unregister();
//            }
//            for (String entry : scoreboard.getEntries()) {
//                scoreboard.resetScores(entry);
//            }
//            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
        }

    }

    public void reset(){
        linesAmount = 0;
    }
}
