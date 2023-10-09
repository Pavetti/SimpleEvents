package pl.pavetti.simpleevents;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import pl.pavetti.simpleevents.api.MetricsLite;
import pl.pavetti.simpleevents.api.UpdateChecker;
import pl.pavetti.simpleevents.listener.CloseInventoryListener;
import pl.pavetti.simpleevents.command.SimpleEventCommand;
import pl.pavetti.simpleevents.config.ConfigFile;
import pl.pavetti.simpleevents.config.EventDataFile;
import pl.pavetti.simpleevents.config.Settings;
import pl.pavetti.simpleevents.manager.EventManager;
import pl.pavetti.simpleevents.tabcompleter.SimpleEventTabCompleter;
import pl.pavetti.simpleevents.task.AutoEventStart;

public final class SimpleEvents extends JavaPlugin {

    private ConfigFile configFile;
    private Settings settings;
    private EventDataFile  eventData;
    private EventManager eventManager;
    private Economy economy;

    @Override
    public void onEnable() {
        // Plugin startup logic
        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - No Vault dependency found! Some features will not be able to use.", getDescription().getName()));
        }
        MetricsLite metrics = new MetricsLite(this, 19944);

        initConfiguration();
        registerCommand();
        registerTabCompleter();
        registerListener();
        registerTask();

        updateCheck();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    private void initConfiguration() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        configFile = new ConfigFile(this);
        settings = new Settings(this);
        eventData = new EventDataFile(configFile);

        eventManager = new EventManager(this,configFile,settings,economy, eventData);
    }

    private void registerCommand(){
        getCommand("simpleevent").setExecutor(new SimpleEventCommand(settings, eventManager,eventData));
    }

    private void registerTabCompleter(){
        getCommand("simpleevent").setTabCompleter(new SimpleEventTabCompleter(eventManager));
    }

    private void registerListener(){
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new CloseInventoryListener(configFile, settings, eventData, eventManager), this);
    }

    private void registerTask(){
        if(settings.isAutoActive()) new AutoEventStart(eventManager,settings).runTaskTimer(this,6000,settings.getInterval());
    }

    private void updateCheck(){
        new UpdateChecker(this, 112876).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("[SimpleEvents] There is not a new update available.");
            } else {
                Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[SimpleEvents] THERE IS A NEW UPDATE AVAILABLE!");
            }
        });
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }
}
