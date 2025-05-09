package dev.jtowo.things.common.item.base;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DebugRaycastItem extends RaycastItem {
    public DebugRaycastItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (!level.isClientSide) {
            raytrace(level, player);
        }
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }

    @Override
    public float getRaycastRange() {
        return DEFAULT_RAYCAST_RANGE;
    }

    @Override
    public float getRaycastDamage() {
        return DEFAULT_RAYCAST_DAMAGE;
    }

    @Override
    public float getRaycastKnockback() {
        return DEFAULT_RAYCAST_KNOCKBACK;
    }

    @Override
    public ParticleOptions getRaycastParticleOptions() {
        return DEFAULT_RAYCAST_PARTICLES;
    }

    @Override
    public int getRaycastParticleCount() {
        return DEFAULT_RAYCAST_PARTICLE_COUNT;
    }

    @Override
    public SoundEvent getRaycastSound() {
        return DEFAULT_RAYCAST_SOUND;
    }
}