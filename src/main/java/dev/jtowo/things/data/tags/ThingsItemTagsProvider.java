package dev.jtowo.things.data.tags;

import dev.jtowo.things.Things;
import dev.jtowo.things.core.registry.ThingsItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ThingsItemTagsProvider extends ItemTagsProvider {
    public ThingsItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                  CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, Things.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(ItemTags.DURABILITY_ENCHANTABLE)
                .add(ThingsItems.MAGICAL_GUITAR.get());

        tag(ItemTags.CROSSBOW_ENCHANTABLE)
                .add(ThingsItems.MAGICAL_GUITAR.get());
    }
}
