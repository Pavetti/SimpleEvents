package pl.pavetti.simpleevents.model;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

@SerializableAs("playerData")
@Getter
@RequiredArgsConstructor
public class PlayerData implements ConfigurationSerializable {

    private final boolean scoreboardShow;

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("scoreboardShow", scoreboardShow);

        return map;
    }

    public static PlayerData deserialize(Map<String, Object> map) {
        return new PlayerData((boolean) map.get("scoreboardShow"));
    }
}
