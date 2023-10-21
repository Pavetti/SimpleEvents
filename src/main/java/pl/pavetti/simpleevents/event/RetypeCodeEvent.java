package pl.pavetti.simpleevents.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import pl.pavetti.simpleevents.SimpleEvents;
import pl.pavetti.simpleevents.config.PlayerDataFile;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;
import pl.pavetti.simpleevents.model.PlayerData;
import pl.pavetti.simpleevents.util.ChatUtil;

import java.util.Optional;
import java.util.Random;

public class RetypeCodeEvent extends Event {

    private final Random random;
    private final Plugin plugin;
    private final PlayerDataFile playerData;
    private boolean generate;
    private String currentCode;
    private static final String CHARS = "qwertyuiopasdfghjklzxcvbnm1234567890@#%&";

    public RetypeCodeEvent(Plugin plugin, EventData data) {
        super(data);
        this.plugin = plugin;
        this.playerData = ((SimpleEvents) plugin).getPlayerData();
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
        this.random = new Random();
    }
    @Override
    public void start() {
        score.clear();
        running = true;
        generate = true;
        runnable();
    }

    @Override
    public void stop() {
        running = false;
        generate = false;
    }
    @EventHandler
    public void onCodeRetype(AsyncPlayerChatEvent event){
        if(running){
            if(event.getMessage().equals(currentCode)){
                addScore(event.getPlayer(), 1);
                currentCode = null;
            }
        }
    }
    private void runnable(){
        new BukkitRunnable(){
            String code;
            @Override
            public void run() {
                code = generateCode();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Optional<PlayerData> playerDataOptional = playerData.getPlayerDataOf(player.getUniqueId());
                    if(playerDataOptional.isPresent()){
                        if(!playerDataOptional.get().isScoreboardShow()) continue;
                    }
                    player.sendMessage("");
                    player.sendMessage(ChatUtil.chatColor("&f------->- &a" + code + " &f-<------"));
                    player.sendMessage("");
                }
                currentCode = code;

                if(!generate) this.cancel();
            }
        }.runTaskTimer(plugin,20,200);
    }

    private String generateCode(){
        int rnd;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            rnd = random.nextInt(CHARS.length()-1);
            stringBuilder.append(CHARS.charAt(rnd));
        }
        return  stringBuilder.toString();
    }
}
