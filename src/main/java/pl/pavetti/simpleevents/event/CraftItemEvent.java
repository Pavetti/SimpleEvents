package pl.pavetti.simpleevents.event;

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
        super(data);
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @Override
    public void start() {
        score.clear();
        running = true;
    }

    @Override
    public void stop() {
        running = false;
    }
    //TODO more tests
    @EventHandler
    public void onCraftItem(org.bukkit.event.inventory.CraftItemEvent event){
        if(running){
            ItemStack craftedItem = event.getInventory().getResult(); //Get result of recipe
            Inventory Inventory = event.getInventory(); //Get crafting inventory
            ClickType clickType = event.getClick();
            int realAmount = craftedItem.getAmount();
            if(clickType.isShiftClick()) {
                int lowerAmount = craftedItem.getMaxStackSize() + 1000; //Set lower at recipe result max stack size + 1000 (or just highter max stacksize of reciped item)
                for(ItemStack actualItem : Inventory.getContents()){ //For each item in crafting inventory{
                    if(!actualItem.getType().isAir() && lowerAmount > actualItem.getAmount() && !actualItem.getType().equals(craftedItem.getType())) //if slot is not air && lowerAmount is highter than this slot amount && it's not the recipe amount
                        lowerAmount = actualItem.getAmount(); //Set new lower amount
                }
                //Calculate the final amount : lowerAmount * craftedItem.getAmount
                realAmount = lowerAmount * craftedItem.getAmount();
            }
            addScore((Player) event.getWhoClicked(),realAmount);
        }
    }
}
