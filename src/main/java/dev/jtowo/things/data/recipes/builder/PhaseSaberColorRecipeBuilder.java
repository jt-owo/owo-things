package dev.jtowo.things.data.recipes.builder;

import dev.jtowo.things.Things;
import dev.jtowo.things.common.item.PhaseSaberItem;
import dev.jtowo.things.core.registry.ThingsItems;
import dev.jtowo.things.util.PhaseSaberColor;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;

import java.util.LinkedHashMap;
import java.util.Map;

public final class PhaseSaberColorRecipeBuilder {
    private final Ingredient dye;
    private final Ingredient ingredient;
    private final PhaseSaberColor color;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    private PhaseSaberColorRecipeBuilder(Ingredient dye, Ingredient ingredient, PhaseSaberColor color) {
        this.dye = dye;
        this.ingredient = ingredient;
        this.color = color;
    }

    public static PhaseSaberColorRecipeBuilder build(PhaseSaberColor color) {
        return new PhaseSaberColorRecipeBuilder(Ingredient.of(color.item()), Ingredient.of(color.ingredient()), color);
    }

    public PhaseSaberColorRecipeBuilder unlockedBy(String key, Criterion<?> criterion) {
        this.criteria.put(key, criterion);
        return this;
    }

    public void save(RecipeOutput output) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Things.MOD_ID, "smithing/phase_saber_%s".formatted(color.key()));
        isValid(id);

        Advancement.Builder advancement$Builder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        criteria.forEach(advancement$Builder::addCriterion);

        SmithingTransformRecipe recipe = new SmithingTransformRecipe(dye, Ingredient.of(ThingsItems.PHASE_SABER), ingredient, PhaseSaberItem.create(color.rgb()));
        output.accept(id, recipe, advancement$Builder.build(id.withPrefix("recipes/" + RecipeCategory.COMBAT.getFolderName() + "/")));
    }

    private void isValid(ResourceLocation location) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + location);
        }
    }
}