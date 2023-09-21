package pl.pavetti.simpleevents.model;

import lombok.*;
import org.bukkit.inventory.ItemStack;
import java.util.*;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class EventData {
    public EventData(String id, String name, List<String> description, double prizeEconomy, List<ItemStack> prizeItems) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.prizeEconomy = prizeEconomy;
        this.prizeItems = prizeItems;
    }

    private final String id;
    private final String name;
    private final List<String> description;
    private final double prizeEconomy;
    private List<ItemStack> prizeItems;
}
