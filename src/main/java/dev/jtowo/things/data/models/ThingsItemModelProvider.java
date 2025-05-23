package dev.jtowo.things.data.models;

import dev.jtowo.things.Things;
import dev.jtowo.things.core.registry.ThingsItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

public class ThingsItemModelProvider extends ItemModelProvider {
    public ThingsItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Things.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ThingsItems.NOTE.get());
        basicItem(ThingsItems.SYMPHONIC_UPGRADE_SMITHING_TEMPLATE.get());
        handheldItem(ThingsItems.CEREMONIAL_DAGGER.get());
        handheldItem(ThingsItems.SCULK_HORN.get());
    }

    @SuppressWarnings({"UnusedReturnValue, unused"})
    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Things.MOD_ID, "item/" + item.getId().getPath()));
    }
}
