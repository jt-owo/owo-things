package dev.jtowo.things;

import dev.jtowo.things.core.registry.ThingsCreativeModeTabs;
import dev.jtowo.things.core.registry.ThingsEntities;
import dev.jtowo.things.core.registry.ThingsItems;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(Things.MOD_ID)
public class Things {
    public static final String MOD_ID = "owothings";

    @SuppressWarnings("unused")
    public Things(IEventBus eventBus, ModContainer modContainer) {
        eventBus.addListener(this::commonSetup);

        ThingsCreativeModeTabs.register(eventBus);
        ThingsItems.register(eventBus);
        ThingsEntities.register(eventBus);

        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ThingsEntities.MAGIC_NOTE.get(), ThrownItemRenderer::new);
        }

        @SubscribeEvent
        public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
            event.register(
                    (stack, tintIndex) -> tintIndex > 0
                            ? -1
                            : DyedItemColor.getOrDefault(stack, -1),
                    ThingsItems.NOTE.value()
            );
        }
    }
}
