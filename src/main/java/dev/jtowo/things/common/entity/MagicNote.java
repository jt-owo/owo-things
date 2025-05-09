package dev.jtowo.things.common.entity;

import dev.jtowo.things.core.registry.ThingsEntities;
import dev.jtowo.things.core.registry.ThingsItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class MagicNote extends ThrowableItemProjectile {
    private static final float LIFETIME_TICKS = 40f;

    public MagicNote(EntityType<MagicNote> type, Level level) {
        super(type, level);
    }

    public MagicNote(Level level, LivingEntity shooter, int rgb) {
        super(ThingsEntities.MAGIC_NOTE.get(), shooter, level);
        setNoGravity(true);
        setColor(rgb);
    }

    public void setColor(int rgb) {
        DyedItemColor color = new DyedItemColor(rgb, false);
        ItemStack noteItem = new ItemStack(getDefaultItem());
        noteItem.set(DataComponents.DYED_COLOR, color);
        setItem(noteItem);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ThingsItems.NOTE.get();
    }

    private ParticleOptions getParticle() {
        return ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, 0);
    }

    /**
     * Handles an entity event received from a {@link net.minecraft.network.protocol.game.ClientboundEntityEventPacket}.
     */
    @Override
    public void handleEntityEvent(byte id) {
        if (id == EntityEvent.DEATH) {
            ParticleOptions particleOptions = this.getParticle();

            for (int i = 0; i < 8; i++) {
                this.level().addParticle(particleOptions, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    /**
     * Called when the NoteEntity hits an entity
     */
    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), 5.0F);
    }

    /**
     * Called when this NoteEntity hits a block or entity.
     */
    @Override
    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide && result instanceof BlockHitResult) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount >= LIFETIME_TICKS) {
            remove(RemovalReason.DISCARDED);
        }
    }

    /**
     * Moves this NoteEntity slightly upwards.
     */
    public void animate() {
        Vec3 delta = getDeltaMovement();
        double dy = delta.y + (0.05 - delta.y) * 0.2;
        setDeltaMovement(delta.x, dy, delta.z);
    }
}