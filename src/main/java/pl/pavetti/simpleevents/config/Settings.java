package pl.pavetti.simpleevents.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import pl.pavetti.simpleevents.SimpleEvents;
import pl.pavetti.simpleevents.exception.NoCorretTimeFormatException;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    private boolean globalWinMessage;
    //auto active
    private boolean autoActive;
    private List<String> eventsAutoActive;
    private int interval;
    private boolean imposedActiveTime;
    private ZoneId timeZone;
    private LocalTime activeTimeFrom;
    private LocalTime activeTimeTo;
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
    private String badCmdUseSEScoreShow;
    private String successfulStartEvent;
    private String successfulEndEvent;
    private String successfulSetItemPrize;
    private String successfulSwitchScoreShow;
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
        givePrize = configuration.getBoolean("settings.givePrize");
        globalWinMessage = configuration.getBoolean("settings.globalWinMessage");
        itemPrizeInventoryTitle = chatColor(configuration.getString("settings.itemPrizeInventoryTitle"));
        //auto active
        loadTimes(configuration);
        interval = configuration.getInt("settings.autoActive.interval") * 20;
        autoActive = configuration.getBoolean("settings.autoActive.autoActive");
        imposedActiveTime = configuration.getBoolean("settings.autoActive.imposedActiveTime");
        eventsAutoActive = configuration.getStringList("settings.autoActive.eventsAutoActive");
        //scoreboard config
        scoreboardTimeLineFormat = chatColor(configuration.getString("settings.scoreboard.timeLineFormat"));
        scoreboardRankingLineFormat = chatColor(configuration.getString("settings.scoreboard.rankingLineFormat"));
        scoreboardRankingLines = configuration.getInt("settings.scoreboard.rankingLines");
        //messages
        help = chatColor(configuration.getStringList("settings.messages.help"));
        winMessage = chatColor(configuration.getStringList("settings.messages.winMessage"));
        noPermission = chatColor(configuration.getString("settings.messages.noPermission"));
        noEventFound = chatColor(configuration.getString("settings.messages.noEventFound"));
        badCmdUseSEScoreShow = chatColor(configuration.getString("settings.messages.badCmdUseSEScoreShow"));
        badCmdUseSEStart = chatColor(configuration.getString("settings.messages.badCmdUseSEStart"));
        badArgumentTimeSEStart = chatColor(configuration.getString("settings.messages.badArgumentTimeSEStart"));
        badCmdUseSEScoreShow = chatColor(configuration.getString("settings.messages.badCmdUseSEScoreShow"));
        successfulStartEvent = chatColor(configuration.getString("settings.messages.successfulStartEvent"));
        successfulEndEvent = chatColor(configuration.getString("settings.messages.successfulEndEvent"));
        successfulSetItemPrize = chatColor(configuration.getString("settings.messages.successfulSetItemPrize"));
        successfulSwitchScoreShow = chatColor(configuration.getString("settings.messages.successfulSwitchScoreShow"));
        messageForWinner = chatColor(configuration.getString("settings.messages.messageForWinner"));
        nothing = chatColor(configuration.getString("settings.messages.nothing"));
        eventAlreadyActive = chatColor(configuration.getString("settings.messages.eventAlreadyActive"));
        badCmdUseSESetPrize = chatColor(configuration.getString("settings.messages.badCmdUseSESetPrize"));
        noEventFoundInEventDataFile = chatColor(configuration.getString("settings.messages.noEventFoundInEventDataFile"));
    }

    private void loadTimes(FileConfiguration configuration){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            timeZone = ZoneId.of(configuration.getString("settings.autoActive.timeZone"));
            if(timeZone == null) throw new NoCorretTimeFormatException("In correct timeZone section value. Check your config file");

            activeTimeFrom = LocalTime.parse(configuration.getString("settings.autoActive.activeTimeFrom"), formatter);
            activeTimeTo = LocalTime.parse(configuration.getString("settings.autoActive.activeTimeTo"), formatter);
        } catch (DateTimeParseException e) {
            throw new NoCorretTimeFormatException("In correct activeTimeFrom or activeTimeTo section value format. Check your config file.");
        }
    }
}