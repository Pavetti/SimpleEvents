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

    private String prefix;
    private List<String> help;
    private String noPermission;
    private String noSimpleEventFound;
    private String badCmdUseSEStart;
    private String badCmdUseSEAuto;
    private String successfulStartSimpleEvent;
    private String successfulEndSimpleEvent;
    private String successfulSetPrize;
    private String successfulSetAutoSwitch;
    private String badArgumentTimeSEStart;
    private boolean givePrizeWhenEndedByCmd;
    private boolean autoStart;
    private boolean givePrize;
    private int interval;
    private List<String> simpleEventsAutoStart;
    private String scoreboardTimeLine;
    private int scoreboardRankingLines;


    public Settings(SimpleEvents plugin) {
        this.plugin = plugin;
        load();
    }

    private void load(){
        FileConfiguration configuration = plugin.getConfig();

        prefix = chatColor(configuration.getString("prefix"));
        help = chatColor(configuration.getStringList("messages.help"));
        noPermission = chatColor(configuration.getString("messages.noPermission"));
        noSimpleEventFound = chatColor(configuration.getString("messages.noSimpleEventFound"));
        badCmdUseSEAuto = chatColor(configuration.getString("messages.badCmdUseSEAuto"));
        badCmdUseSEStart = chatColor(configuration.getString("messages.badCmdUseSEStart"));
        badArgumentTimeSEStart = chatColor(configuration.getString("messages.badArgumentTimeSEStart"));
        successfulStartSimpleEvent = chatColor(configuration.getString("messages.successfulStartSimpleEvent"));
        successfulEndSimpleEvent = chatColor(configuration.getString("messages.successfulEndSimpleEvent"));
        successfulSetAutoSwitch = chatColor(configuration.getString("messages.successfulSetAutoSwitch"));
        successfulSetPrize = chatColor(configuration.getString("messages.successfulSetPrize"));
        givePrizeWhenEndedByCmd = configuration.getBoolean("givePrizeWhenEndedByCmd");
        autoStart = configuration.getBoolean("autoStart");
        givePrize = configuration.getBoolean("givePrize");
        interval = configuration.getInt("interval");
        simpleEventsAutoStart = configuration.getStringList("simpleEventsAutoStart");
        scoreboardTimeLine = chatColor(configuration.getString("scoreboardTimeLine"));
        scoreboardRankingLines = configuration.getInt("scoreboardRankingLines");
    }
}
