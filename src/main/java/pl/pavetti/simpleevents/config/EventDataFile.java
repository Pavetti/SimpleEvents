package pl.pavetti.simpleevents.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import pl.pavetti.simpleevents.exception.NoCorrectEventDataException;
import pl.pavetti.simpleevents.model.EventData;
import pl.pavetti.simpleevents.util.ConfigurationSectionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
@Getter
public class EventDataFile {

    @Getter(AccessLevel.NONE)
    private final ConfigFile configFile;

    //<Section,EventData>
    private final Map<String, EventData> eventsData = new HashMap<>();

    public EventDataFile(ConfigFile configFile) {
        this.configFile = configFile;
        load();
    }

    public void load(){
        ConfigurationSection configuration = ConfigurationSectionUtils.getMainSection(configFile.getYmlEventData(),"eventsData");
        if(configuration == null){
            throw new NoCorrectEventDataException("Can not load event data cause eventData.yml is empty. Try to rebuild this file.");
        }

        for (String section : configuration.getKeys(false)) {
            //creating list of prize items form file
            List<Map<String, Object>> itemsMapList = (List<Map<String, Object>>) configuration.getList(section + ".prizeItems");
            List<ItemStack> itemStackList = null;
            if (itemsMapList != null) {
                itemStackList = itemsMapList.stream()
                        .map(ItemStack::deserialize)
                        .collect(Collectors.toList());
            }
            //creating EventData
            EventData eventData = createEventData(configuration,section,itemStackList);


            eventsData.put(section, eventData);
        }
    }

    public Optional<EventData> getEventDataByID(String id){
        return eventsData.values()
                .stream()
                .filter(eventData -> eventData.getId().equals(id))
                .findAny();
    }

    public Optional<String> getEventDataSectionNameByID(String id){
        ConfigurationSection configuration = ConfigurationSectionUtils.getMainSection(configFile.getYmlEventData(),"eventsData");
        for (String key : configuration.getKeys(false)) {
            if(configuration.getString(key + ".id").equals(id)){
                return Optional.of(key);
            }
        }
        return Optional.empty();
    }

    public static EventData createEventData(ConfigurationSection configuration, String section, List<ItemStack> items){
        return  new EventData(
                configuration.getString( section + ".id"),
                configuration.getString(section + ".displayName"),
                configuration.getInt(section + ".defaultDuration"),
                configuration.getStringList(section + ".description"),
                configuration.getDouble(section + ".prizeEconomy"),
                items);
    }
    public void save(){
        try {
            configFile.getYmlEventData().save(configFile.getEventDataFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
