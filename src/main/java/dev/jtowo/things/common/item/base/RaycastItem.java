package dev.jtowo.things.common.item.base;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class RaycastItem extends MusicWeaponItem {
    protected static final float DEFAULT_RAYCAST_RANGE = 10.0f;
    protected static final float DEFAULT_RAYCAST_RADIUS = 0.0f;

    protected static final float DEFAULT_RAYCAST_DAMAGE = 5.0f;
    protected static final float DEFAULT_RAYCAST_KNOCKBACK = 0.5f;

    protected static final ParticleOptions DEFAULT_RAYCAST_PARTICLES = ParticleTypes.END_ROD;
    protected static final int DEFAULT_RAYCAST_PARTICLE_COUNT = 30;

    protected static final SoundEvent DEFAULT_RAYCAST_SOUND = SoundEvents.STONE_BREAK;

    public RaycastItem(Properties properties) {
        super(properties);
    }

    /**
     * Performs a ray trace to check for blocks & entities along the ray's path.
     *
     * @param level  The level.
     * @param player The player performing the raytrace.
     */
    public void raytrace(Level level, Player player) {
        Vec3 startPos = player.getEyePosition(1.0F);
        Vec3 direction = player.getViewVector(1.0F);
        Vec3 endPos = startPos.add(direction.scale(getRaycastRange()));

        BlockHitResult blockHit = level.clip(new ClipContext(startPos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));

        if (blockHit.getType() != HitResult.Type.MISS) {
            endPos = blockHit.getLocation();
        }

        List<EntityHitResult> entityResults = performEntityRayTrace(level, player, direction, startPos, endPos);

        for (EntityHitResult entityResult : entityResults) {
            Entity entity = entityResult.getEntity();
            if (entity instanceof LivingEntity target) {
                float damage = getRaycastDamage();
                float knockback = getRaycastKnockback();
                if (target.hurt(target.damageSources().playerAttack(player), damage) && knockback > 0) {
                    target.knockback(knockback, -direction.x, -direction.z);
                }
            }
        }

        particles(level, startPos, endPos);

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                getRaycastSound(), SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    /**
     * Performs a ray trace to check for entities along the ray's path.
     *
     * @param level     The level.
     * @param player    The player performing the raytrace.
     * @param direction The direction of the raytrace.
     * @param start     The start position of the raytrace.
     * @param end       The end position of the raytrace
     * @return List of {@link EntityHitResult}. If no entities were hit, List will be empty.
     */
    private List<EntityHitResult> performEntityRayTrace(Level level, Player player, Vec3 direction, Vec3 start, Vec3 end) {
        List<EntityHitResult> entityHitResults = new ArrayList<>();
        float radius = getRaycastRadius();
        if (radius > 0) {
            AABB rayBoundingBox = player.getBoundingBox().inflate(getRaycastRadius()).expandTowards(direction.scale(getRaycastRange() - 1));
            List<Entity> entities = level.getEntities(player, rayBoundingBox, (e) -> e instanceof LivingEntity entity && entity != player);

            for (Entity entity : entities) {
                AABB entityBoundingBox = entity.getBoundingBox();
                if (rayBoundingBox.intersects(entityBoundingBox)) {
                    entityHitResults.add(new EntityHitResult(entity));
                }
            }
        } else {
            List<Entity> entities = level.getEntities(player, player.getBoundingBox().expandTowards(direction.scale(getRaycastRange())), (e) -> e instanceof LivingEntity entity && entity != player);

            for (Entity entity : entities) {
                AABB entityBoundingBox = entity.getBoundingBox();
                Optional<Vec3> intersection = entityBoundingBox.clip(start, end);
                if (intersection.isPresent()) {
                    entityHitResults.add(new EntityHitResult(entity));
                }
            }
        }

        return entityHitResults;
    }

    private void particles(Level level, Vec3 start, Vec3 end) {
        Vec3 direction = end.subtract(start).normalize();
        int particleCount = getRaycastParticleCount();
        ParticleOptions particleOptions = getRaycastParticleOptions();
        if (particleOptions != null) {
            for (int i = 0; i < particleCount; i++) {
                float factor = i / (float) particleCount;
                Vec3 particlePos = start.add(direction.scale(factor * end.distanceTo(start)));
                ((ServerLevel) level).sendParticles(particleOptions, particlePos.x, particlePos.y, particlePos.z, 1, 0, 0, 0, 0);
            }
        }
    }

    public abstract float getRaycastRange();

    public float getRaycastRadius() {
        return DEFAULT_RAYCAST_RADIUS;
    }

    public abstract float getRaycastDamage();

    public abstract float getRaycastKnockback();

    public abstract ParticleOptions getRaycastParticleOptions();

    public abstract int getRaycastParticleCount();

    public abstract SoundEvent getRaycastSound();
}