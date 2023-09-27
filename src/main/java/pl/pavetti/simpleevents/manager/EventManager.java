package pl.pavetti.simpleevents.manager;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import pl.pavetti.simpleevents.SimpleEvents;
import pl.pavetti.simpleevents.config.ConfigFile;
import pl.pavetti.simpleevents.config.EventDataFile;
import pl.pavetti.simpleevents.config.Settings;
import pl.pavetti.simpleevents.exception.NoCorrectEventDataException;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;
import pl.pavetti.simpleevents.util.PlayerUtil;
import pl.pavetti.simpleevents.util.EventUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
@Getter
@Setter
public class EventManager {

    @Getter(AccessLevel.NONE)
    private final ConfigFile configFile;
    private final Economy economy;
    private final Settings settings;
    private final EventDataFile eventDataFile;
    private final int rankingLinesAmount;

    private SimpleEvents plugin;

    private final Map<String, Event> registeredEvents = new HashMap<>();
    private final List<String> eventsClassNames = new ArrayList<>();
    private final ScoreBoardManager scoreBoardManager;

    private boolean isRunning = false;
    private boolean goEnd = false;

    public EventManager(SimpleEvents plugin, ConfigFile configFile, Settings settings, Economy economy, EventDataFile eventDataFile) {
        this.configFile = configFile;
        this.settings = settings;
        this.economy = economy;
        this.plugin = plugin;
        this.rankingLinesAmount = settings.getScoreboardRankingLines();
        scoreBoardManager = new ScoreBoardManager(settings,rankingLinesAmount);
        this.eventDataFile = eventDataFile;

        //names of class that has to be registered as SimpleEvent
        eventsClassNames.addAll(Arrays.asList("ThrowEnderPerlEvent","BreakBlockEvent","PlaceBlockEvent","PassiveMobsKillEvent"
                                                ,"HostileMobsKillEvent","DealDamageEvent","CatchFishEvent","PlayerKillEvent"
                                                ,"DeathEvent", "CraftItemEvent", "EnchantItemEvent","EatFoodEvent","JumpEvent"
                                                ,"MoveEvent","GetDamageNoDeathEvent"));
        loadSimpleEvents(plugin);

    }

    public void loadSimpleEvents(SimpleEvents plugin) {

        //load simpleEvent data and register it
        for (String classEventName : eventsClassNames) {

            //change classEventName to section name in eventData.yml
            String section = (classEventName.substring(0, 1).toLowerCase() + classEventName.substring(1)).substring(0, classEventName.length());
            EventData eventData = this.eventDataFile.getEventsData().get(section);
            if(eventData == null){
                throw new NoCorrectEventDataException("Can not load event data form eventData.yml cause one or more event data section have got changed names. Try to rebuild this file.");
            }

            //getting instance of class which extend SimpleEvent by String simpleEventClassName (reflection)
            try {
                Class<?> clazz = Class.forName("pl.pavetti.simpleevents.event." + classEventName);
                Constructor<?> constructor = clazz.getConstructor(Plugin.class, EventData.class);
                Object instance = constructor.newInstance(plugin, eventData);
                Event event = (Event) instance;
                registeredEvents.put(event.getData().getId(), event);
            } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                     InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void startSimpleEvent(int time, Event event) {
        event.start();
        isRunning = true;
        scoreBoardManager.createScoreBoard(time, event.getData());
        scoreBoardManager.showScoreBoardForALl();
        new BukkitRunnable() {
            int second = time;
            @Override
            public void run() {
                if (second > 0) {
                    //refreshing every second
                    scoreBoardManager.updateScoreboard(second, EventUtil.getFormatTop(event.getScore()
                            , rankingLinesAmount, settings.getScoreboardRankingLineFormat()));
                    second--;
                }else {
                    //end of event by times up
                    makeWinner(EventUtil.getTop(event.getScore(),rankingLinesAmount)
                            , event, settings.isGivePrize());
                    event.stop();
                    scoreBoardManager.closeScoreBoardForALl();
                    scoreBoardManager.reset();
                    isRunning = false;
                    this.cancel();
                }
                if (goEnd) {
                    //end of event by use command
                    makeWinner(EventUtil.getTop(event.getScore(),rankingLinesAmount)
                            , event, (settings.isGivePrize() && settings.isGivePrizeWhenEndedByCmd()));
                    event.stop();
                    scoreBoardManager.closeScoreBoardForALl();
                    scoreBoardManager.reset();
                    isRunning = false;
                    goEnd = false;
                    this.cancel();
                }

            }
        }.runTaskTimer(plugin, 0, 20); // 20 ticks = 1 second
    }


    private void makeWinner(Map<UUID,Integer> top, Event event, boolean givePrize){
        if(!top.isEmpty()) {
            //gets information about winner
            Map.Entry<UUID, Integer> firstPlayerMapEntry = top.entrySet().iterator().next();
            UUID uuid = firstPlayerMapEntry.getKey();
            String nick = Bukkit.getOfflinePlayer(uuid).getName();
            int score = firstPlayerMapEntry.getValue();


            double prizeEconomy = event.getData().getPrizeEconomy();
            List<ItemStack> prizeItems = event.getData().getPrizeItems();
            //if player on server gives prizes
            Optional<Player> playerOptional = Optional.ofNullable(Bukkit.getPlayerExact(nick));
            if(playerOptional.isPresent()){
                Player player = playerOptional.get();
                if(givePrize) {

                    givePrizeEconomy(player, prizeEconomy);
                    givePrizeItems(player, prizeItems);

                }
                if(!settings.isGlobalWinMessage()){
                    PlayerUtil.sendMessage(player,settings.getPrefix(), settings.getMessageForWinner());
                }
            }
            if(settings.isGlobalWinMessage()){
                sendGlobalMessage(nick,score, event.getData().getName());
            }
        }
    }

    private void givePrizeEconomy(Player player,double amount){
        if(economy != null && amount != 0) economy.depositPlayer(player,amount);
    }

    private void
    givePrizeItems(Player player, List<ItemStack> items){
        if(items != null) {
            for (ItemStack item : items) {
                PlayerUtil.addItem(player,item);
            }
        }
    }

    private void sendGlobalMessage(String name,int score,String eventName){
        for (String line : settings.getWinMessage()) {
            line = line.replace("{NICK}",name)
                    .replace("{SCORE}",String.valueOf(score))
                    .replace("{EVENT}",eventName);
            plugin.getServer().broadcastMessage(line);
        }
    }

    public boolean isSimpleEvent(String id){
        return registeredEvents.containsKey(id);
    }
}
