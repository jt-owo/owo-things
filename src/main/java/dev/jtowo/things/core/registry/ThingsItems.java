package dev.jtowo.things.core.registry;

import dev.jtowo.things.Things;
import dev.jtowo.things.common.item.CeremonialDaggerItem;
import dev.jtowo.things.common.item.GuitarItem;
import dev.jtowo.things.common.item.SculkHornItem;
import dev.jtowo.things.common.item.base.DebugRaycastItem;
import dev.jtowo.things.common.item.base.ThingsSmithingTemplateItem;
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
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    .component(DataComponents.TOOL, GuitarItem.createToolProperties())));

    public static final DeferredItem<Item> NOTE = ITEMS.register("note",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SCULK_HORN = ITEMS.register("sculk_horn",
            () -> new SculkHornItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    .component(DataComponents.TOOL, SculkHornItem.createToolProperties())));

    public static final DeferredItem<Item> DEBUG_RAYCAST_STICK = ITEMS.register("debug_raycast_stick",
            () -> new DebugRaycastItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));

    public static final DeferredItem<Item> SYMPHONIC_UPGRADE_SMITHING_TEMPLATE = ITEMS.register("symphonic_smithing_template",
            ThingsSmithingTemplateItem::createSymphonicUpgradeTemplate);

    public static final DeferredItem<Item> CEREMONIAL_DAGGER = ITEMS.register("ceremonial_dagger",
            () -> new CeremonialDaggerItem(new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)
                    .component(DataComponents.TOOL, CeremonialDaggerItem.createToolProperties())
                    .attributes(CeremonialDaggerItem.createAttributes())));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
