package pl.pavetti.simpleevents.model;

import lombok.*;
import org.bukkit.inventory.ItemStack;
import java.util.*;

@RequiredArgsConstructor
@Getter
@ToString
public class SimpleEventData {

    private final String id;
    private final String name;
    private final List<String> description;
    private final String winMessage;
    private final int prizeEconomy;
    private final List<ItemStack> prizeItems;
}
