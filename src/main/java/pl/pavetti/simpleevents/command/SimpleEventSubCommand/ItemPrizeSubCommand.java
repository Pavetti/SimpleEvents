package pl.pavetti.simpleevents.command.SimpleEventSubCommand;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.pavetti.simpleevents.api.timsixth.SubCommand;
import pl.pavetti.simpleevents.config.EventDataFile;
import pl.pavetti.simpleevents.config.Settings;
import pl.pavetti.simpleevents.inventoryholder.SetPrizeItemsInventoryHolder;
import pl.pavetti.simpleevents.manager.EventManager;
import pl.pavetti.simpleevents.model.EventData;
import pl.pavetti.simpleevents.util.PlayerUtil;

import java.util.List;
import java.util.Optional;

public class ItemPrizeSubCommand implements SubCommand {

    private  final Settings settings;
    private final EventDataFile eventDataFile;
    private final EventManager eventManager;

    public ItemPrizeSubCommand(Settings settings, EventDataFile eventDataFile, EventManager eventManager) {
        this.settings = settings;
        this.eventDataFile = eventDataFile;
        this.eventManager = eventManager;
    }

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        String prefix = settings.getPrefix();
        //check command format correctness
        if(args.length < 2){
            PlayerUtil.sendMessage(player,prefix,settings.getBadCmdUseSESetPrize());
            return true;
        }
        String id = args[1];
        //check is simple event with given id exist
        if (!eventManager.getRegisteredEvents().containsKey(id)) {
            PlayerUtil.sendMessage(player,prefix,settings.getNoEventFound().replace("{EVENT}",id));
            return true;
        }
        Inventory inventory = Bukkit.createInventory(new SetPrizeItemsInventoryHolder(id),27,settings.getItemPrizeInventoryTitle());
        Optional<EventData> eventDataOptional = eventDataFile.getEventDataByID(id);
        if (!eventDataOptional.isPresent()){
            PlayerUtil.sendMessage(player,prefix,settings.getNoEventFoundInEventDataFile());
            return true;
        }
        EventData eventData = eventDataOptional.get();
        List<ItemStack> items = eventData.getPrizeItems();
        if(items != null) {
            for (ItemStack item : items) {
                if (item != null) {
                    inventory.addItem(item);
                }
            }
        }
        player.openInventory(inventory);
        return false;
    }

    @Override
    public String getName() {
        return "itemprize";
    }
}