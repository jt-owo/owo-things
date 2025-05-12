package dev.jtowo.things.core.registry;

import dev.jtowo.things.Things;
import dev.jtowo.things.common.component.CodecValue;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ThingsDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Things.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CodecValue<Boolean>>> ACTIVE = DATA_COMPONENT_TYPES.registerComponentType(
            "active",
            builder -> builder
                    // The codec to read/write the data to disk
                    .persistent(CodecValue.BOOL_CODEC)
                    // The codec to read/write the data across the network
                    .networkSynchronized(CodecValue.BOOL_STREAM_CODEC)
    );

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
