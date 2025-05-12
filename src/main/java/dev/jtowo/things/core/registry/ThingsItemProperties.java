package dev.jtowo.things.core.registry;

import dev.jtowo.things.Things;
import dev.jtowo.things.common.component.CodecValue;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ThingsItemProperties {
    public static void register() {
        ItemProperties.register(ThingsItems.PHASE_SABER.get(), ResourceLocation.fromNamespaceAndPath(Things.MOD_ID, "active"),
                (stack, level, entity, seed) -> {
                    CodecValue<Boolean> active = stack.get(ThingsDataComponents.ACTIVE);
                    if (active == null || !active.value())
                        return 0.0f;
                    return 1.0f;
                });
    }
}
