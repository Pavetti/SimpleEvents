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
        prefix = chatColor(configuration.getString("settings.prefix"));
        givePrizeWhenEndedByCmd = configuration.getBoolean("settings.givePrizeWhenEndedByCmd");
        eventsAutoStart = configuration.getStringList("settings.eventsAutoStart");
        autoStart = configuration.getBoolean("settings.autoStart");
        givePrize = configuration.getBoolean("settings.givePrize");
        globalWinMessage = configuration.getBoolean("settings.globalWinMessage");
        interval = configuration.getInt("settings.interval") * 20;
        itemPrizeInventoryTitle = chatColor(configuration.getString("settings.itemPrizeInventoryTitle"));
        //scoreboard config
        scoreboardTimeLineFormat = chatColor(configuration.getString("settings.scoreboard.timeLineFormat"));
        scoreboardRankingLineFormat = chatColor(configuration.getString("settings.scoreboard.rankingLineFormat"));
        scoreboardRankingLines = configuration.getInt("settings.scoreboard.rankingLines");
        //messages
        help = chatColor(configuration.getStringList("settings.messages.help"));
        winMessage = chatColor(configuration.getStringList("settings.messages.winMessage"));
        noPermission = chatColor(configuration.getString("settings.messages.noPermission"));
        noEventFound = chatColor(configuration.getString("settings.messages.noEventFound"));
        badCmdUseSEAuto = chatColor(configuration.getString("settings.messages.badCmdUseSEAuto"));
        badCmdUseSEStart = chatColor(configuration.getString("settings.messages.badCmdUseSEStart"));
        badArgumentTimeSEStart = chatColor(configuration.getString("settings.messages.badArgumentTimeSEStart"));
        successfulStartEvent = chatColor(configuration.getString("settings.messages.successfulStartEvent"));
        successfulEndEvent = chatColor(configuration.getString("settings.messages.successfulEndEvent"));
        successfulSetItemPrize = chatColor(configuration.getString("settings.messages.successfulSetItemPrize"));
        messageForWinner = chatColor(configuration.getString("settings.messages.messageForWinner"));
        nothing = chatColor(configuration.getString("settings.messages.nothing"));
        eventAlreadyActive = chatColor(configuration.getString("settings.messages.eventAlreadyActive"));
        badCmdUseSESetPrize = chatColor(configuration.getString("settings.messages.badCmdUseSESetPrize"));
        noEventFoundInEventDataFile = chatColor(configuration.getString("settings.messages.noEventFoundInEventDataFile"));
    }
}
