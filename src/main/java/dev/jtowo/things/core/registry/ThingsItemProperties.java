package dev.jtowo.things.core.registry;

import dev.jtowo.things.Things;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ThingsItemProperties {
    public static void register() {
        ItemProperties.register(ThingsItems.PHASE_SABER.get(), ResourceLocation.fromNamespaceAndPath(Things.MOD_ID, "active"),
                (stack, level, entity, seed) -> stack.getOrDefault(ThingsDataComponents.ACTIVE, false) ? 1.0f : 0.0f);
    }
}
