package pl.pavetti.simpleevents.model;

import java.util.*;
import lombok.*;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
@Getter
@ToString
public class EventData {

    /*
     *   This class reparent data about event form yml file
     */
    private final String id;
    private final String name;
    private final int defaultDuration;
    private final List<String> description;
    private final double prizeEconomy;
    private final List<ItemStack> prizeItems;
}
