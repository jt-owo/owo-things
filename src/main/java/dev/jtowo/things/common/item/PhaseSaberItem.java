package dev.jtowo.things.common.item;

import dev.jtowo.things.Things;
import dev.jtowo.things.common.component.CodecValue;
import dev.jtowo.things.core.registry.ThingsDataComponents;
import dev.jtowo.things.core.registry.ThingsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PhaseSaberItem extends Item {
    private static final ChatFormatting DESCRIPTION_FORMAT = ChatFormatting.GRAY;
    private static final ChatFormatting CRYSTAL_EMPTY_FORMAT = ChatFormatting.WHITE;

    public PhaseSaberItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (hasCrystal(stack)) {
            float pitch;
            SoundEvent sound;
            if (!isActive(stack)) {
                stack.set(ThingsDataComponents.ACTIVE, CodecValue.TRUE);
                pitch = 2.0f;
                sound = SoundEvents.BEACON_ACTIVATE;
            } else {
                stack.set(ThingsDataComponents.ACTIVE, CodecValue.FALSE);
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
    public boolean onLeftClickEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull Entity entity) {
        if (entity instanceof LivingEntity target) {
            if (isActive(stack)) {
                if (target.hurt(player.damageSources().playerAttack(player), 7.0f)) {
                    Vec3 direction = player.getViewVector(1.0f);
                    target.knockback(0.2f, -direction.x, -direction.z);
                    return true;
                }
            }
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public void appendHoverText(ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        DyedItemColor color = stack.get(DataComponents.DYED_COLOR);
        Component text;
        if (color != null) {
            int rgb = color.rgb();
            text = Component.literal("Custom").withColor(rgb);
        } else {
            text = Component.translatable("mco.configure.world.slot.empty").withStyle(CRYSTAL_EMPTY_FORMAT);
        }

        tooltipComponents.add(Component.translatable("item." + Things.MOD_ID + ".phase_saber.crystal_description", text)
                .withStyle(DESCRIPTION_FORMAT));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public boolean hasCrystal(ItemStack stack) {
        return stack.get(DataComponents.DYED_COLOR) != null;
    }

    public boolean isActive(ItemStack stack) {
        CodecValue<Boolean> active = stack.get(ThingsDataComponents.ACTIVE);
        return active != null && active.value();
    }

    public static ItemStack create(int rgb) {
        ItemStack stack = new ItemStack(ThingsItems.PHASE_SABER.get());
        stack.set(DataComponents.DYED_COLOR, new DyedItemColor(rgb, false));
        return stack;
    }
}
