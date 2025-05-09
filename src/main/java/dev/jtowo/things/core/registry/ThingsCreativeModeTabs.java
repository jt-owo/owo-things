package dev.jtowo.things.core.registry;

import dev.jtowo.things.Things;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ThingsCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Things.MOD_ID);

    @SuppressWarnings("unused")
    public static final Supplier<CreativeModeTab> ITEMS_TAB = CREATIVE_MODE_TABS.register("owothings_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ThingsItems.MAGICAL_GUITAR.get())).title(Component.translatable("creativetab." + Things.MOD_ID + ".items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ThingsItems.DEBUG_RAYCAST_STICK);
                        output.accept(ThingsItems.MAGICAL_GUITAR);
                        output.accept(ThingsItems.SCULK_HORN);
                        output.accept(ThingsItems.SYMPHONIC_UPGRADE_SMITHING_TEMPLATE);
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
