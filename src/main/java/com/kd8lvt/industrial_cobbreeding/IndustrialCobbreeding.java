package com.kd8lvt.industrial_cobbreeding;

import com.kd8lvt.industrial_cobbreeding.registry.ItemAttributeTypeRegistry;
import com.kd8lvt.industrial_cobbreeding.registry.DisplaySourceRegistry;
import com.mojang.logging.LogUtils;
import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.api.registry.CreateRegistries;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.level.LevelEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(IndustrialCobbreeding.MOD_ID)
public class IndustrialCobbreeding {
    public static final String MOD_ID = "industrial_cobbreeding";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final CreateRegistrate C_REG = CreateRegistrate.create(MOD_ID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null)
            .setTooltipModifierFactory(item ->
                    new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
            );

    public IndustrialCobbreeding(IEventBus modEventBus, ModContainer modContainer) {
        C_REG.registerEventListeners(modEventBus);

        ItemAttributeTypeRegistry.registerAll();
    }
}
