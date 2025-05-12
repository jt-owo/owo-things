package dev.jtowo.things.core.registry;

import dev.jtowo.things.Things;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ThingsTags {
    public static class Items {
        public static final TagKey<Item> PHASE_SABERS = createTag("phase_sabers");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(Things.MOD_ID, name));
        }
    }
}
