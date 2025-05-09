package dev.jtowo.things.data.loot;

import dev.jtowo.things.Things;
import dev.jtowo.things.common.loot.modifier.AddItemModifier;
import dev.jtowo.things.core.registry.ThingsItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class ThingsGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ThingsGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Things.MOD_ID);
    }

    @Override
    protected void start() {
        add("symphonic_upgrade_smithing_template_from_warden",
                new AddItemModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/warden")).build()
                }, ThingsItems.SYMPHONIC_UPGRADE_SMITHING_TEMPLATE.get()));
    }
}
