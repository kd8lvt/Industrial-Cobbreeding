package com.kd8lvt.industrial_cobbreeding.registry;

import com.kd8lvt.industrial_cobbreeding.attributes.EggAttribute;
import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.logistics.item.filter.attribute.ItemAttributeType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegisterEvent;

import static com.kd8lvt.industrial_cobbreeding.IndustrialCobbreeding.MOD_ID;

public final class AttributeTypeRegistry {
    private static RegisterEvent reg;

    public static void registerAll(RegisterEvent reg) {
        AttributeTypeRegistry.reg=reg;

        register("pokemon_egg", new EggAttribute.Type());
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID,path);
    }

    public static <T extends ItemAttributeType> void register(String id, T thing) {
        reg.register(CreateBuiltInRegistries.ITEM_ATTRIBUTE_TYPE.key(),id(id),()->thing);
    }
}
