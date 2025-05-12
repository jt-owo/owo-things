package dev.jtowo.things.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public enum PhaseSaberColor {
    WHITE(16383998, Items.WHITE_DYE, Items.STONE, "white"),
    LIGHT_GRAY(10329495, Items.LIGHT_GRAY_DYE, Items.STONE, "light_gray"),
    GRAY(4673362, Items.GRAY_DYE, Items.GUNPOWDER, "gray"),
    BLACK(1908001, Items.BLACK_DYE, Items.COAL, "black"),
    BROWN(8606770, Items.BROWN_DYE, Items.COOKIE, "brown"),
    RED(11546150, Items.RED_DYE, Items.REDSTONE, "red"),
    ORANGE(16351261, Items.ORANGE_DYE, Items.COPPER_INGOT, "orange"),
    YELLOW(16701501, Items.YELLOW_DYE, Items.GOLD_INGOT, "yellow"),
    LIME(8439583, Items.LIME_DYE, Items.EMERALD, "lime"),
    GREEN(6192150, Items.GREEN_DYE, Items.TURTLE_SCUTE, "green"),
    CYAN(1481884, Items.CYAN_DYE, Items.PRISMARINE_SHARD, "cyan"),
    LIGHT_BLUE(3847130, Items.LIGHT_BLUE_DYE, Items.DIAMOND, "light_blue"),
    BLUE(3949738, Items.BLUE_DYE, Items.LAPIS_LAZULI, "blue"),
    PURPLE(8991416, Items.PURPLE_DYE, Items.AMETHYST_SHARD, "purple"),
    MAGENTA(13061821, Items.MAGENTA_DYE, Items.CHORUS_FRUIT, "magenta"),
    PINK(15961002, Items.PINK_DYE, Items.DRAGON_BREATH, "pink");

    private final int rgb;
    private final Item item;
    private final Item ingredient;
    private final String key;

    PhaseSaberColor(int rgb, Item item, Item ingredient, String key) {
        this.rgb = rgb;
        this.item = item;
        this.ingredient = ingredient;
        this.key = key;
    }

    public int rgb() {
        return this.rgb;
    }

    public Item item() {
        return this.item;
    }

    public Item ingredient() {
        return this.ingredient;
    }

    public String key() {
        return this.key;
    }
}
