package pl.pavetti.simpleevents.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.pavetti.simpleevents.SimpleEvents;

import java.io.File;

@Getter
public class ConfigFile {

    @Getter(AccessLevel.NONE)
    private final SimpleEvents plugin;


    private File eventDataFile;
    private File playerDataFile;

    private YamlConfiguration ymlEventData;
    private YamlConfiguration ymlPlayerData;
    public ConfigFile(SimpleEvents plugin) {
        this.plugin = plugin;
        createFiles();
        loadFiles();
    }

    private void createFiles() {
        eventDataFile = createFile("eventData.yml");
        playerDataFile = createFile("playerData.yml");
    }

    private File createFile(String name) {
        if (!plugin.getDataFolder().mkdir()) {
            plugin.getDataFolder().mkdirs();
        }
        File file = new File(plugin.getDataFolder(), name);
        if (!file.exists()) {
            plugin.saveResource(name, true);
        }
        return file;
    }

    private void loadFiles() {
        ymlEventData = YamlConfiguration.loadConfiguration(eventDataFile);
        ymlPlayerData = YamlConfiguration.loadConfiguration(playerDataFile);
    }
}