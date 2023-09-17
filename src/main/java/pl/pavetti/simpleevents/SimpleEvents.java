package pl.pavetti.simpleevents;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import pl.pavetti.simpleevents.command.SimpleEventCommand;
import pl.pavetti.simpleevents.config.ConfigFile;
import pl.pavetti.simpleevents.config.Settings;
import pl.pavetti.simpleevents.manager.SimpleEventsManager;
import pl.pavetti.simpleevents.tabcompleter.SimpleEventTabCompleter;

public final class SimpleEvents extends JavaPlugin {

    private ConfigFile configFile;
    private Settings settings;
    private SimpleEventsManager simpleEventsManager;
    private Economy economy;

    @Override
    public void onEnable() {
        // Plugin startup logic
        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - No Vault dependency found! Some features will not be able to use.", getDescription().getName()));
        }

        initConfiguration();
        registerCommand();
        registerTabCompleter();
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
        simpleEventsManager = new SimpleEventsManager(this,configFile,settings,economy);
    }

    private void registerCommand(){
        getCommand("simpleevent").setExecutor(new SimpleEventCommand(settings,simpleEventsManager));
    }

    private void registerTabCompleter(){
        getCommand("simpleevent").setTabCompleter(new SimpleEventTabCompleter(simpleEventsManager));
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
