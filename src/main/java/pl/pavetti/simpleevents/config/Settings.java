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
    private List<String> eventsAutoStart;
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
    private String noEventFound;
    private String badArgumentTimeSEStart;
    private String badCmdUseSEStart;
    private String badCmdUseSEAuto;
    private String successfulStartEvent;
    private String successfulEndEvent;
    private String successfulSetItemPrize;
    private String messageForWinner;
    private String nothing;
    private String eventAlreadyActive;
    private String badCmdUseSESetPrize;
    private String itemPrizeInventoryTitle;
    private String noEventFoundInEventDataFile;

    public Settings(SimpleEvents plugin) {
        this.plugin = plugin;
        load();
    }

    private void load(){
        FileConfiguration configuration = plugin.getConfig();

        //basic config
        prefix = chatColor(configuration.getString("prefix"));
        givePrizeWhenEndedByCmd = configuration.getBoolean("givePrizeWhenEndedByCmd");
        eventsAutoStart = configuration.getStringList("eventsAutoStart");
        autoStart = configuration.getBoolean("autoStart");
        givePrize = configuration.getBoolean("givePrize");
        globalWinMessage = configuration.getBoolean("globalWinMessage");
        interval = configuration.getInt("interval");
        itemPrizeInventoryTitle = chatColor(configuration.getString("itemPrizeInventoryTitle"));
        //scoreboard config
        scoreboardTimeLineFormat = chatColor(configuration.getString("scoreboard.timeLineFormat"));
        scoreboardRankingLineFormat = chatColor(configuration.getString("scoreboard.rankingLineFormat"));
        scoreboardRankingLines = configuration.getInt("scoreboard.rankingLines");
        //messages
        help = chatColor(configuration.getStringList("messages.help"));
        winMessage = chatColor(configuration.getStringList("messages.winMessage"));
        noPermission = chatColor(configuration.getString("messages.noPermission"));
        noEventFound = chatColor(configuration.getString("messages.noEventFound"));
        badCmdUseSEAuto = chatColor(configuration.getString("messages.badCmdUseSEAuto"));
        badCmdUseSEStart = chatColor(configuration.getString("messages.badCmdUseSEStart"));
        badArgumentTimeSEStart = chatColor(configuration.getString("messages.badArgumentTimeSEStart"));
        successfulStartEvent = chatColor(configuration.getString("messages.successfulStartEvent"));
        successfulEndEvent = chatColor(configuration.getString("messages.successfulEndEvent"));
        successfulSetItemPrize = chatColor(configuration.getString("messages.successfulSetItemPrize"));
        messageForWinner = chatColor(configuration.getString("messages.messageForWinner"));
        nothing = chatColor(configuration.getString("messages.nothing"));
        eventAlreadyActive = chatColor(configuration.getString("messages.eventAlreadyActive"));
        badCmdUseSESetPrize = chatColor(configuration.getString("messages.badCmdUseSESetPrize"));
        noEventFoundInEventDataFile = chatColor(configuration.getString("messages.noEventFoundInEventDataFile"));
    }
}
