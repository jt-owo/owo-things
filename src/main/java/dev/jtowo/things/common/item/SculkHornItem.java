package dev.jtowo.things.common.item;

import dev.jtowo.things.common.item.base.RaycastItem;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SculkHornItem extends RaycastItem {
    private static final int COOLDOWN = 20;

    public SculkHornItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        player.getCooldowns().addCooldown(this, COOLDOWN);

        if (!level.isClientSide) {
            raytrace(level, player);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public float getRaycastRange() {
        return 10.0f;
    }

    @Override
    public float getRaycastDamage() {
        return 10.0f;
    }

    @Override
    public float getRaycastKnockback() {
        return 0.75f;
    }

    @Override
    public ParticleOptions getRaycastParticleOptions() {
        return ParticleTypes.SONIC_BOOM;
    }

    @Override
    public int getRaycastParticleCount() {
        return 10;
    }

    @Override
    public SoundEvent getRaycastSound() {
        return SoundEvents.WARDEN_SONIC_BOOM;
    }
}
