package dev.jtowo.things.common.item;

import dev.jtowo.things.Things;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CeremonialDaggerItem extends Item {
    private static final float DEFAULT_ATTACK_DAMAGE = 6.5F;
    private static final float DEFAULT_ATTACK_SPEED = -2.0F;

    public CeremonialDaggerItem(Properties properties) {
        super(properties);
    }

    public static Tool createToolProperties() {
        return new Tool(List.of(), 1.0F, 2);
    }

    public static ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, DEFAULT_ATTACK_DAMAGE, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, DEFAULT_ATTACK_SPEED, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND
                )
                .build();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown())
            tooltipComponents.add(Component.translatable("item." + Things.MOD_ID + ".ceremonial_dagger.desc_shift_down"));
        else
            tooltipComponents.add(Component.translatable("item." + Things.MOD_ID + ".ceremonial_dagger.desc"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
