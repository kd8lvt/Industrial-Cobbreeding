package com.kd8lvt.industrial_cobbreeding.registry;

import com.kd8lvt.industrial_cobbreeding.attributes.EggAttribute;
import com.simibubi.create.api.registry.CreateRegistries;
import com.simibubi.create.content.logistics.item.filter.attribute.ItemAttributeType;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import static com.kd8lvt.industrial_cobbreeding.IndustrialCobbreeding.C_REG;
import static com.kd8lvt.industrial_cobbreeding.IndustrialCobbreeding.LOGGER;

public final class ItemAttributeTypeRegistry {
    public static RegistryEntry<ItemAttributeType,ItemAttributeType> EGG;

    public static void registerAll() {
        LOGGER.info("Registering Item Attributes...");
        EGG=register("pokemon_egg", EggAttribute.Type::new);
    }

    public static RegistryEntry<ItemAttributeType, ItemAttributeType> register(String id, NonNullSupplier<ItemAttributeType> thing) {
        return C_REG.simple(id,CreateRegistries.ITEM_ATTRIBUTE_TYPE,thing);
    }
}
