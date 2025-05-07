package dev.jtowo.things.core.registry;

import dev.jtowo.things.Things;
import dev.jtowo.things.common.item.GuitarItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ThingsItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Things.MOD_ID);

    public static final DeferredItem<Item> MAGICAL_GUITAR = ITEMS.register("magical_guitar",
            () -> new GuitarItem(new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .component(DataComponents.TOOL, GuitarItem.createToolProperties())));

    public static final DeferredItem<Item> NOTE = ITEMS.register("note",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
