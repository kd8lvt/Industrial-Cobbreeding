package com.kd8lvt.industrial_cobbreeding.registry;

import com.cobblemon.mod.common.CobblemonBlockEntities;
import com.kd8lvt.industrial_cobbreeding.display_sources.PastureDisplayLinkHandler;
import com.simibubi.create.api.behaviour.display.DisplaySource;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.LevelEvent;

import static com.kd8lvt.industrial_cobbreeding.IndustrialCobbreeding.LOGGER;

@EventBusSubscriber
public final class DisplaySourceRegistry {

    @SubscribeEvent
    public static void registerAll(LevelEvent.Load e) {
        LOGGER.info("Registering Display Sources...");
        register(CobblemonBlockEntities.PASTURE,new PastureDisplayLinkHandler());
    }

    // I would'be been smashing my face into a wall trying to figure this out until I remembered
    // that Create Train Navigator has display link functionality.
    // https://github.com/MisterJulsen/Create-Train-Navigator/blob/1.21.1/common/src/main/java/de/mrjulsen/crn/registry/ModExtras.java
    private static void register(BlockEntityType<?> be, DisplaySource source) {
        DisplaySource.BY_BLOCK_ENTITY.add(be, source);
    }
}
