package pl.pavetti.simpleevents.inventoryholder;

import lombok.Getter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
@Getter
public class SetPrizeItemsInventoryHolder implements InventoryHolder {
    private final String eventId;

    public SetPrizeItemsInventoryHolder(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
