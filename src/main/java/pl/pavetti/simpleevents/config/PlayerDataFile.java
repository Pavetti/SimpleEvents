package pl.pavetti.simpleevents.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import pl.pavetti.simpleevents.model.PlayerData;
import pl.pavetti.simpleevents.util.ConfigurationSectionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


public class PlayerDataFile {

    @Getter(AccessLevel.NONE)
    private final ConfigFile configFile;
    private final Map<UUID, PlayerData> playerData = new HashMap<>();

    public PlayerDataFile(ConfigFile configFile) {
        this.configFile = configFile;
        load();
    }

    private void load(){
        ConfigurationSection configuration = ConfigurationSectionUtils.getMainSection(configFile.getYmlPlayerData(),"playerData");
        for (String key : configuration.getKeys(false)) {
            playerData.put(UUID.fromString(key),configuration.getObject(key, PlayerData.class));
        }
    }

    public Optional<PlayerData> getPlayerDataOf(UUID uuid){
        if(playerData.containsKey(uuid)) return Optional.of(playerData.get(uuid));
        return Optional.empty();
    }

    public void setPlayerDataScoreShow(UUID uuid, boolean bool){
        ConfigurationSection configuration = ConfigurationSectionUtils.getMainSection(configFile.getYmlPlayerData(),"playerData");
        PlayerData newPlayerData = new PlayerData(bool);
        playerData.put(uuid,newPlayerData);
        configuration.set(uuid.toString(),newPlayerData);
        save();
    }

    public void save(){
        try {
            configFile.getYmlPlayerData().save(configFile.getPlayerDataFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}