package dev.jtowo.things.core.registry;

import dev.jtowo.things.Things;
import dev.jtowo.things.common.entity.MagicNote;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ThingsEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Things.MOD_ID);

    public static final Supplier<EntityType<MagicNote>> MAGIC_NOTE =
            ENTITY_TYPES.register("magic_note", () -> EntityType.Builder.<MagicNote>of(MagicNote::new, MobCategory.MISC)
                    .sized(0.10f, 0.10f).build("magic_note"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
