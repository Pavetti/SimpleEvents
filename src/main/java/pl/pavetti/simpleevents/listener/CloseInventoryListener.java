package pl.pavetti.simpleevents.listener;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.pavetti.simpleevents.config.ConfigFile;
import pl.pavetti.simpleevents.config.EventDataFile;
import pl.pavetti.simpleevents.config.Settings;
import pl.pavetti.simpleevents.inventoryholder.SetPrizeItemsInventoryHolder;
import pl.pavetti.simpleevents.manager.EventManager;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;
import pl.pavetti.simpleevents.util.ConfigurationSectionUtils;
import pl.pavetti.simpleevents.util.PlayerUtil;

import java.util.*;
import java.util.stream.Collectors;

public class CloseInventoryListener implements Listener {

    private final ConfigFile configFile;
    private final Settings settings;
    private final EventDataFile eventDataFile;
    private final EventManager eventManager;

    public CloseInventoryListener(ConfigFile configFile, Settings settings, EventDataFile eventDataFile, EventManager eventManager) {
        this.configFile = configFile;
        this.settings = settings;
        this.eventDataFile = eventDataFile;
        this.eventManager = eventManager;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        Inventory inventory = event.getView().getTopInventory();

        if(inventory.getHolder() instanceof SetPrizeItemsInventoryHolder){
            String eventID = ((SetPrizeItemsInventoryHolder) inventory.getHolder()).getEventId();
            setItems(inventory,(eventID));
            PlayerUtil.sendMessage((Player) event.getPlayer(), settings.getPrefix(), settings.getSuccessfulSetItemPrize().replace("{EVENT}",eventID));
        }
    }
    private void setItems(Inventory inventory, String eventId){
        ConfigurationSection configuration = ConfigurationSectionUtils.getMainSection(configFile.getYmlEventData(),"eventsData");

        //creating new ItemStack list form items form inventory
        List<ItemStack> items = new ArrayList<>(Arrays.asList(inventory.getContents())).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        //serializing items to maps
        List<Map<String, Object>> itemsMap = items.stream()
                .filter(Objects::nonNull)
                .map(ItemStack::serialize)
                .collect(Collectors.toList());

        //add list of serialized items to file
        Optional<String> sectionOptional = eventDataFile.getEventDataSectionNameByID(eventId);
        if(!sectionOptional.isPresent()){
            //send error message
            for (HumanEntity viewer : inventory.getViewers()) {
                PlayerUtil.sendMessage((Player) viewer, settings.getPrefix(), settings.getNoEventFoundInEventDataFile());
            }
        }
        //set new prize in .yml
        String section = sectionOptional.get();
        configuration.set(section + ".prizeItems",itemsMap);
        eventDataFile.save();

        //getting old eventData from EventManager and adding new item prize
        EventData eventData = EventDataFile.createEventData(configuration,section,items);
        String id = eventData.getId();

        //setting new eventData to Map in eventDataFile
        eventDataFile.getEventsData().put(section,eventData);

        //updating Event in Map in EventManger
        Event event = eventManager.getRegisteredEvents().get(id);
        event.setData(eventData);
        eventManager.getRegisteredEvents().put(id, event);
    }
}
