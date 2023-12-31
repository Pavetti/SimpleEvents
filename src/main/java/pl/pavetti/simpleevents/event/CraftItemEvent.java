package pl.pavetti.simpleevents.event;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import pl.pavetti.simpleevents.model.Event;
import pl.pavetti.simpleevents.model.EventData;

public class CraftItemEvent extends Event {

    public CraftItemEvent(Plugin plugin, EventData data) {
        super(plugin, data);
    }

    @EventHandler
    public void onCraftItem(org.bukkit.event.inventory.CraftItemEvent event) {
        if (running) {
            ItemStack craftedItem = event.getInventory().getResult(); //Get result of recipe
            Inventory Inventory = event.getInventory(); //Get crafting inventory
            ClickType clickType = event.getClick();
            int realAmount = craftedItem.getAmount();
            if (clickType.isShiftClick()) {
                int lowerAmount = craftedItem.getMaxStackSize() + 1000; //Set lower at recipe result max stack size + 1000 (or just highter max stacksize of reciped item)
                for (ItemStack actualItem : Inventory.getContents()) { //For each item in crafting inventory{
                    if (
                        actualItem.getType() != Material.AIR &&
                        actualItem.getType() != Material.CAVE_AIR &&
                        actualItem.getType() != Material.VOID_AIR &&
                        lowerAmount > actualItem.getAmount() &&
                        !actualItem.getType().equals(craftedItem.getType())
                    ) lowerAmount = actualItem.getAmount(); //if slot is not air && lowerAmount is highter than this slot amount && it's not the recipe amount //Set new lower amount
                }
                //Calculate the final amount : lowerAmount * craftedItem.getAmount
                realAmount = lowerAmount * craftedItem.getAmount();
            }
            addScore((Player) event.getWhoClicked(), realAmount);
        }
    }
}
