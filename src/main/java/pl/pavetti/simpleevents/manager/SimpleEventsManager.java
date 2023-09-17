package pl.pavetti.simpleevents.manager;

import lombok.AccessLevel;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import pl.pavetti.simpleevents.SimpleEvents;
import pl.pavetti.simpleevents.config.ConfigFile;
import pl.pavetti.simpleevents.config.Settings;
import pl.pavetti.simpleevents.exception.NoCorrectSimpleEventDataException;
import pl.pavetti.simpleevents.model.SimpleEvent;
import pl.pavetti.simpleevents.model.SimpleEventData;
import pl.pavetti.simpleevents.api.ScoreboardWrapper;
import pl.pavetti.simpleevents.util.SimpleEventUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
@Getter
public class SimpleEventsManager {

    @Getter(AccessLevel.NONE)
    private final ConfigFile configFile;
    private final Economy economy;
    private final Settings settings;
    private final int rankingLinesAmount;

    private SimpleEvents plugin;

    private final Map<String, SimpleEvent> simpleEvents = new HashMap<>();
    private final List<String> eventsClassNames = new ArrayList<>();
    private final ScoreBoardManager scoreBoardManager;

    private boolean isRunning = false;
    private ScoreboardWrapper board;

    public SimpleEventsManager(SimpleEvents plugin, ConfigFile configFile, Settings settings, Economy economy) {
        this.configFile = configFile;
        this.settings = settings;
        this.economy = economy;
        this.plugin = plugin;
        this.rankingLinesAmount = settings.getScoreboardRankingLines();
        scoreBoardManager = new ScoreBoardManager(settings,rankingLinesAmount);

        //names of class that has to be registered as SimpleEvent
        eventsClassNames.addAll(Arrays.asList("ThrowEnderPerlSimpleEvent"));
        loadSimpleEventsData(plugin);

    }

    private void loadSimpleEventsData(SimpleEvents plugin) {

        ConfigurationSection simpleEventsDataSection = configFile.getYmlSimpleEvents().getConfigurationSection("simpleEventsData");

        if (simpleEventsDataSection == null){
            throw new NoCorrectSimpleEventDataException("Cant load data form simpleEventsData.yml because is empty. Try to rebuild this file.");
        }
        //load simpleEvent data and register it
        for (String simpleEventKey : simpleEventsDataSection.getKeys(false)) {
            //makes simpleEventClassName by adding to simpleEventKey "SimpleEvent" on End and setting to upper case first letter
            String simpleEventClassName = simpleEventKey.replaceFirst("^.", String.valueOf(Character.toUpperCase(simpleEventKey.charAt(0)))).concat("SimpleEvent");
            if (eventsClassNames.contains(simpleEventClassName)) {
                //makes SimpleEventData from simpleEventsDataSection using simpleEventKey
                SimpleEventData simpleEventData = new SimpleEventData(
                        simpleEventsDataSection.getString(simpleEventKey + ".id"),
                        simpleEventsDataSection.getString(simpleEventKey + ".displayName"),
                        simpleEventsDataSection.getStringList(simpleEventKey + ".description"),
                        simpleEventsDataSection.getString(simpleEventKey + ".messageForWinner"),
                        simpleEventsDataSection.getInt(simpleEventKey + ".prizeEconomy"),
                        (List<ItemStack>) simpleEventsDataSection.get(simpleEventKey + ".prizeItems"));

                //getting instance of class which extend SimpleEvent by String simpleEventClassName (reflection)
                try {
                    Class<?> clazz = Class.forName("pl.pavetti.simpleevents.simpleevent." + simpleEventClassName);
                    Constructor<?> constructor = clazz.getConstructor(Plugin.class, SimpleEventData.class);
                    Object instance = constructor.newInstance(plugin, simpleEventData);
                    SimpleEvent simpleEvent = (SimpleEvent) instance;
                    simpleEvents.put(simpleEvent.getData().getId(),simpleEvent);
                } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                         InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                throw new NoCorrectSimpleEventDataException("One or more of sections with simple events have different names than they should. Try to rebuild this file.");
            }
        }
    }

    public void startSimpleEvent(int time,SimpleEvent simpleEvent) {
        simpleEvent.start();
        isRunning = true;
        scoreBoardManager.createScoreBoard(time,simpleEvent.getData());
        scoreBoardManager.showScoreBoardForALl();
        new BukkitRunnable() {
            int second = time;
            @Override
            public void run() {
                if (second > 0) {
                    //refreshing every second
                    scoreBoardManager.updateScoreboard(second, SimpleEventUtil.getFormatTop(simpleEvent.getScore(), rankingLinesAmount));
                    second--;
                } else {
                    plugin.getServer().broadcastMessage("Player wins the event");
                    //end of mini event
                    makeWinner(SimpleEventUtil.getTop(simpleEvent.getScore(),rankingLinesAmount), simpleEvent);
                    simpleEvent.stop();
                    scoreBoardManager.closeScoreBoardForALl();
                    scoreBoardManager.reset();
                    isRunning = false;
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 20); // 20 ticks = 1 second
    }


    private void makeWinner(Map<UUID,Integer> top, SimpleEvent simpleEvent){
        if(!top.isEmpty()) {
            Map.Entry<UUID, Integer> firstPlayerMapEntry = top.entrySet().iterator().next();
            UUID uuid = firstPlayerMapEntry.getKey();
            String nick = Bukkit.getOfflinePlayer(uuid).getName();
            int score = firstPlayerMapEntry.getValue();

            int prizeEconomy = simpleEvent.getData().getPrizeEconomy();
            List<ItemStack> prizeItems= simpleEvent.getData().getPrizeItems();

            Optional<Player> playerOptional = Optional.ofNullable(Bukkit.getPlayerExact(nick));
            if(playerOptional.isPresent()){
                Player player = playerOptional.get();
                givePrizeEconomy(player,prizeEconomy);
                givePrizeItems(player,prizeItems);

            }
            sendGlobalMessage(Bukkit.getOfflinePlayer(uuid));
        }
    }

    private void givePrizeEconomy(Player player,int amount){
        if(economy != null && amount != 0) economy.depositPlayer(player,amount);
    }

    private void givePrizeItems(Player player, List<ItemStack> items){
        if(items != null) {
            for (ItemStack item : items) {
                player.getInventory().addItem(item);
            }
        }
    }

    private void sendGlobalMessage(OfflinePlayer player){
        //TODO GLOBAL message
        //send broadcast message about player who win event
        plugin.getServer().broadcastMessage("Player " + player.getName() + "wins the event");
    }

    public boolean isSimpleEvent(String id){
        return simpleEvents.containsKey(id);
    }
}