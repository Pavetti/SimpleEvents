package pl.pavetti.simpleevents.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import pl.pavetti.simpleevents.SimpleEvents;

import java.util.List;

import static pl.pavetti.simpleevents.util.ChatUtil.chatColor;
@Getter
public class Settings {

    @Getter(AccessLevel.NONE)
    private final SimpleEvents plugin;

    //basic config
    private String prefix;
    private boolean givePrize;
    private boolean givePrizeWhenEndedByCmd;
    private boolean autoStart;
    private List<String> simpleEventsAutoStart;
    private int interval;
    private boolean globalWinMessage;
    //scoreboard config
    private String scoreboardTimeLineFormat;
    private String scoreboardRankingLineFormat;
    private int scoreboardRankingLines;
    //messages
    private List<String> winMessage;
    private List<String> help;
    private String noPermission;
    private String noSimpleEventFound;
    private String badArgumentTimeSEStart;
    private String badCmdUseSEStart;
    private String badCmdUseSEAuto;
    private String successfulStartSimpleEvent;
    private String successfulEndSimpleEvent;
    private String successfulSetPrize;
    private String successfulSetAutoSwitch;
    private String messageForWinner;

    public Settings(SimpleEvents plugin) {
        this.plugin = plugin;
        load();
    }

    private void load(){
        FileConfiguration configuration = plugin.getConfig();

        //basic config
        prefix = chatColor(configuration.getString("prefix"));
        givePrizeWhenEndedByCmd = configuration.getBoolean("givePrizeWhenEndedByCmd");
        simpleEventsAutoStart = configuration.getStringList("simpleEventsAutoStart");
        autoStart = configuration.getBoolean("autoStart");
        givePrize = configuration.getBoolean("givePrize");
        globalWinMessage = configuration.getBoolean("globalWinMessage");
        interval = configuration.getInt("interval");
        //scoreboard config
        scoreboardTimeLineFormat = chatColor(configuration.getString("scoreboard.timeLineFormat"));
        scoreboardRankingLineFormat = chatColor(configuration.getString("scoreboard.rankingLineFormat"));
        scoreboardRankingLines = configuration.getInt("scoreboard.rankingLines");
        //messages
        help = chatColor(configuration.getStringList("messages.help"));
        winMessage = chatColor(configuration.getStringList("messages.winMessage"));
        noPermission = chatColor(configuration.getString("messages.noPermission"));
        noSimpleEventFound = chatColor(configuration.getString("messages.noSimpleEventFound"));
        badCmdUseSEAuto = chatColor(configuration.getString("messages.badCmdUseSEAuto"));
        badCmdUseSEStart = chatColor(configuration.getString("messages.badCmdUseSEStart"));
        badArgumentTimeSEStart = chatColor(configuration.getString("messages.badArgumentTimeSEStart"));
        successfulStartSimpleEvent = chatColor(configuration.getString("messages.successfulStartSimpleEvent"));
        successfulEndSimpleEvent = chatColor(configuration.getString("messages.successfulEndSimpleEvent"));
        successfulSetAutoSwitch = chatColor(configuration.getString("messages.successfulSetAutoSwitch"));
        successfulSetPrize = chatColor(configuration.getString("messages.successfulSetPrize"));
        messageForWinner = chatColor(configuration.getString("messages.messageForWinner"));
    }
}
