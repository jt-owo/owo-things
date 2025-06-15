package dev.jtowo.things.common.item;

import dev.jtowo.things.Things;
import dev.jtowo.things.common.item.base.ToggleableItem;
import dev.jtowo.things.core.registry.ThingsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PhaseSaberItem extends SwordItem implements ToggleableItem {
    private static final ChatFormatting DESCRIPTION_FORMAT = ChatFormatting.GRAY;
    private static final ChatFormatting CRYSTAL_EMPTY_FORMAT = ChatFormatting.WHITE;

    public PhaseSaberItem(Properties properties) {
        super(Tiers.DIAMOND, properties);
    }

    public static ItemAttributeModifiers createAttributes(float attackDamage, float attackSpeed) {
        return createAttributes(Tiers.DIAMOND, attackDamage, attackSpeed);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (hasCrystal(stack)) {
            toggle(stack, player);

            float pitch;
            SoundEvent sound;
            if (isEnabled(stack)) {
                stack.set(DataComponents.ATTRIBUTE_MODIFIERS, createAttributes(6.5f, -2.4F));
                pitch = 2.0f;
                sound = SoundEvents.BEACON_ACTIVATE;
            } else {
                stack.set(DataComponents.ATTRIBUTE_MODIFIERS, createAttributes(-3.0f, -2.4F));
                pitch = 2.0f;
                sound = SoundEvents.BEACON_DEACTIVATE;
            }

            level.playSound(
                    player, player.getX(), player.getY(), player.getZ(),
                    sound, SoundSource.PLAYERS, 2.0f, pitch
            );

            return InteractionResultHolder.consume(stack);
        }

        return InteractionResultHolder.fail(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        DyedItemColor color = stack.get(DataComponents.DYED_COLOR);
        Component text;
        if (color != null) {
            text = Component.literal("Custom").withColor(color.rgb());
        } else {
            text = Component.translatable("mco.configure.world.slot.empty").withStyle(CRYSTAL_EMPTY_FORMAT);
        }

        tooltipComponents.add(
                Component.translatable("item." + Things.MOD_ID + ".phase_saber.crystal_description", text)
                        .withStyle(DESCRIPTION_FORMAT)
        );

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity miningEntity) {
        return true;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public boolean shouldCauseReequipAnimation(@NotNull ItemStack oldStack, @NotNull ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack) {
        if (oldStack.is(newStack.getItem())) return false;
        return super.shouldCauseBlockBreakReset(oldStack, newStack);
    }

    public boolean hasCrystal(ItemStack stack) {
        return stack.get(DataComponents.DYED_COLOR) != null;
    }

    public static ItemStack create(int rgb) {
        ItemStack stack = new ItemStack(ThingsItems.PHASE_SABER.get());
        stack.set(DataComponents.DYED_COLOR, new DyedItemColor(rgb, false));
        return stack;
    }
}
