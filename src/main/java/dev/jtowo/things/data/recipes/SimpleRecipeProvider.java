package dev.jtowo.things.data.recipes;

import dev.jtowo.things.Things;
import dev.jtowo.things.core.registry.ThingsItems;
import dev.jtowo.things.data.recipes.builder.PhaseSaberColorRecipeBuilder;
import dev.jtowo.things.util.PhaseSaberColor;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class SimpleRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public SimpleRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ThingsItems.MAGICAL_GUITAR.get())
                .pattern("  D")
                .pattern("ED ")
                .pattern("SE ")
                .define('D', Items.POLISHED_DEEPSLATE)
                .define('E', Items.ECHO_SHARD)
                .define('S', Items.SCULK_SENSOR)
                .unlockedBy("has_echo_shard", has(Items.ECHO_SHARD))
                .save(recipeOutput);

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ThingsItems.SYMPHONIC_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.of(Items.GOAT_HORN),
                        Ingredient.of(Items.ECHO_SHARD),
                        RecipeCategory.COMBAT,
                        ThingsItems.SCULK_HORN.get())
                .unlocks("has_echo_shard", has(Items.ECHO_SHARD))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Things.MOD_ID, "smithing/sculk_horn"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ThingsItems.PHASE_SABER.get())
                .pattern("NBN")
                .pattern("NRN")
                .pattern("NNN")
                .define('N', Items.NETHERITE_INGOT)
                .define('B', Items.BEACON)
                .define('R', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .unlockedBy("has_beacon", has(Items.BEACON))
                .save(recipeOutput);

        for (PhaseSaberColor color : PhaseSaberColor.values()) {
            PhaseSaberColorRecipeBuilder.build(color)
                    .unlockedBy("has_phase_saber", has(ThingsItems.PHASE_SABER))
                    .save(recipeOutput);
        }

        super.buildRecipes(recipeOutput);
    }
}
