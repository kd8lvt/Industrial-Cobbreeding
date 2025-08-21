package com.kd8lvt.industrial_cobbreeding.attributes;

import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.simibubi.create.content.logistics.item.filter.attribute.ItemAttribute;
import com.simibubi.create.content.logistics.item.filter.attribute.ItemAttributeType;
import ludichat.cobbreeding.PokemonEgg;
import ludichat.cobbreeding.components.CobbreedingComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FormAttribute implements ItemAtttribute {
    public static final DataComponentType<PokemonProperties> PROPS = CobbreedingComponents.POKEMON_PROPERTIES.get();

    public final String value;
    private FormAttribute(String value) {
        this.value=value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean appliesTo(ItemStack stack, Level world) {
        return return stack.getItem() instanceof PokemonEgg;
    }

    @Override
    public ItemAttributeType getType() {
        return new Type();
    }

    @Override
    public String getTranslationKey() {
        return value;
    }

    public static class Type implements ItemAttributeType {
        @Override
        public @NotNull ItemAttribute createAttribute() {
            return new FormAttribute("normal");
        }

        @Override
        public List<ItemAttribute> getAllAttributes(ItemStack stack, Level level) {
            if (stack.get(PROPS) instanceof PokemonProperties props) return List.of(new FormAttribute(props.getForm()));
            return List.of();
        }

        @Override
        public MapCodec<? extends ItemAttribute> codec() {
            return Codec.STRING.xmap(FormAttribute::new, FormAttribute::value)
                    .fieldOf("value");
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, ? extends ItemAttribute> streamCodec() {
            return ByteBufCodecs.STRING_UTF8
                    .map(FormAttribute::new, FormAttribute::value);
        }
    }
}
