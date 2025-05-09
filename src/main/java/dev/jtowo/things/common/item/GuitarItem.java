package dev.jtowo.things.common.item;

import dev.jtowo.things.common.entity.MagicNote;
import dev.jtowo.things.common.item.base.MusicWeaponItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GuitarItem extends MusicWeaponItem {
    private static final List<Integer> NOTE_COLORS =
            List.of(
                    -8923392,
                    -6963200,
                    -5069568,
                    -3373568,
                    -1940224,
                    -835328,
                    -254464,
                    -131057,
                    -589773,
                    -1572774,
                    -3211133,
                    -5373783,
                    -7995188
            );

    public GuitarItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.pass(stack);
    }


    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity livingEntity, @NotNull ItemStack stack, int remainingUseDuration) {
        if (!(livingEntity instanceof Player player)) return;
        if (remainingUseDuration % 3 == 0) {
            if (!level.isClientSide) {
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.NOTE_BLOCK_GUITAR, SoundSource.PLAYERS, 1.0f, 1.0F / (level.getRandom().nextFloat() * 0.4f + 1.2f) + 1 * 0.5f);

                int projectileCount = EnchantmentHelper.processProjectileCount((ServerLevel) level, stack, player, 1);
                float projectileSpread = EnchantmentHelper.processProjectileSpread((ServerLevel) level, stack, player, 0.0F);
                float f1 = projectileCount <= 1 ? 0.0F : 2.0F * projectileSpread / (float) (projectileCount - 1);
                float f2 = (float) ((projectileCount - 1) % 2) * f1 / 2.0F;
                float f3 = 1.0F;

                for (int i = 0; i < projectileCount; i++) {
                    float angle = f2 + f3 * (float) ((i + 1) / 2) * f1;
                    f3 = -f3;

                    MagicNote magicNote = new MagicNote(level, player, NOTE_COLORS.get(player.getRandom().nextInt(NOTE_COLORS.size())));
                    magicNote.shootFromRotation(player, player.getXRot(), player.getYRot() + angle, 0.0F, 0.2f, 2.0f);
                    magicNote.animate();
                    level.addFreshEntity(magicNote);
                    // stack.hurtAndBreak(this.getDurabilityUse(stack), player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
                }
            }
        }
    }
}
