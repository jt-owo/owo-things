package dev.jtowo.things.common.item.base;

import dev.jtowo.things.core.registry.ThingsDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ToggleableItem {
    default boolean isEnabled(ItemStack stack) {
        return stack.getOrDefault(ThingsDataComponents.ACTIVE, false);
    }

    default void toggle(ItemStack stack, Player player) {
        stack.update(ThingsDataComponents.ACTIVE, true, value -> !value);
        player.displayClientMessage(Component.translatable("owothings.toggle_item", stack.getDisplayName(), isEnabled(stack) ? Component.translatable("owothings.enabled") : Component.translatable("owothings.disabled")), true);
    }
}
