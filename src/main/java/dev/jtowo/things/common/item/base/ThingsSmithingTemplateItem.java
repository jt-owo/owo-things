package dev.jtowo.things.common.item.base;

import dev.jtowo.things.Things;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

public class ThingsSmithingTemplateItem extends SmithingTemplateItem {
    private static final ChatFormatting TITLE_FORMAT = ChatFormatting.GRAY;
    private static final ChatFormatting DESCRIPTION_FORMAT = ChatFormatting.BLUE;
    private static final Component SYMPHONIC_UPGRADE = Component.translatable(
                    Util.makeDescriptionId("upgrade", ResourceLocation.fromNamespaceAndPath(Things.MOD_ID, "symphonic_upgrade"))
            )
            .withStyle(TITLE_FORMAT);
    private static final Component SYMPHONIC_UPGRADE_APPLIES_TO = Component.translatable(
                    Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(Things.MOD_ID, "smithing_template.symphonic_upgrade.applies_to"))
            )
            .withStyle(DESCRIPTION_FORMAT);
    private static final Component SYMPHONIC_UPGRADE_INGREDIENTS = Component.translatable(
                    Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(Things.MOD_ID, "smithing_template.symphonic_upgrade.ingredients"))
            )
            .withStyle(DESCRIPTION_FORMAT);
    private static final Component SYMPHONIC_UPGRADE_BASE_SLOT_DESCRIPTION = Component.translatable(
            Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(Things.MOD_ID, "smithing_template.symphonic_upgrade.base_slot_description"))
    );
    private static final Component SYMPHONIC_UPGRADE_ADDITIONS_SLOT_DESCRIPTION = Component.translatable(
            Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(Things.MOD_ID, "smithing_template.symphonic_upgrade.additions_slot_description"))
    );

    private static final ResourceLocation EMPTY_SLOT_INSTRUMENT = ResourceLocation.fromNamespaceAndPath(Things.MOD_ID, "item/empty_slot_instrument");
    private static final ResourceLocation EMPTY_SLOT_SHARD = ResourceLocation.fromNamespaceAndPath(Things.MOD_ID, "item/empty_slot_shard");

    private ThingsSmithingTemplateItem(Component appliesTo, Component ingredients, Component upgradeDescription, Component baseSlotDescription, Component additionsSlotDescription, List<ResourceLocation> baseSlotEmptyIcons, List<ResourceLocation> additionalSlotEmptyIcons, FeatureFlag... requiredFeatures) {
        super(appliesTo, ingredients, upgradeDescription, baseSlotDescription, additionsSlotDescription, baseSlotEmptyIcons, additionalSlotEmptyIcons, requiredFeatures);
    }

    public static ThingsSmithingTemplateItem createSymphonicUpgradeTemplate() {
        return new ThingsSmithingTemplateItem(
                SYMPHONIC_UPGRADE_APPLIES_TO,
                SYMPHONIC_UPGRADE_INGREDIENTS,
                SYMPHONIC_UPGRADE,
                SYMPHONIC_UPGRADE_BASE_SLOT_DESCRIPTION,
                SYMPHONIC_UPGRADE_ADDITIONS_SLOT_DESCRIPTION,
                createSymphonicUpgradeIconList(),
                createSypmphonicUpgradeMaterialList()
        );
    }

    private static List<ResourceLocation> createSymphonicUpgradeIconList() {
        return List.of(
                EMPTY_SLOT_INSTRUMENT
        );
    }

    private static List<ResourceLocation> createSypmphonicUpgradeMaterialList() {
        return List.of(EMPTY_SLOT_SHARD);
    }
}
