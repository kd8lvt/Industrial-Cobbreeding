package com.kd8lvt.industrial_cobbreeding.attributes;

import com.cobblemon.mod.common.pokemon.IVs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.simibubi.create.content.logistics.item.filter.attribute.ItemAttribute;
import com.simibubi.create.content.logistics.item.filter.attribute.ItemAttributeType;
import ludichat.cobbreeding.PokemonEgg;
import ludichat.cobbreeding.components.CobbreedingComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

// This is so janky... and also crashes. Help. q-q
public record EggAttribute(String type, String value) implements ItemAttribute {
    @Override
    public boolean appliesTo(ItemStack stack, Level world) {
        return stack.getItem() instanceof PokemonEgg;
    }

    @Override
    public ItemAttributeType getType() {
        return new Type();
    }

    @Override
    public String getTranslationKey() {
        return !Objects.equals(value, "null") ? type : "none";
    }

    public static class Type implements ItemAttributeType {
        @Override
        public @NotNull ItemAttribute createAttribute() {
            return new EggAttribute("dummy", "dummy");
        }

        @Override
        public List<ItemAttribute> getAllAttributes(ItemStack stack, Level level) {
            ArrayList<ItemAttribute> ret = new ArrayList<>(List.of());
            String props = stack.get(CobbreedingComponents.EGG_INFO);
            Arrays.stream(FilterableTraits.values())
                    .forEach(trait -> ret.add(new EggAttribute(trait.toString().toLowerCase(), trait.valueFor(props))));

            return ret;
        }

        @Override
        public MapCodec<? extends ItemAttribute> codec() {
            return RecordCodecBuilder.mapCodec((RecordCodecBuilder.Instance<EggAttribute> inst) ->
                    inst.group(
                            Codec.STRING.fieldOf("type").forGetter(EggAttribute::type),
                            Codec.STRING.fieldOf("value").forGetter(EggAttribute::value)
                    ).apply(inst, EggAttribute::new)
            );
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, ? extends ItemAttribute> streamCodec() {
            return ByteBufCodecs.fromCodec(codec().codec()); //lazy kd is lazy
        }
    }

    public static String getProperty(String props, String property) {
        return Arrays.stream(props.split(" ")).filter(s -> s.startsWith("%s=".formatted(property))).limit(1).toList().getFirst().replace("%s=".formatted(property), "");
    }

    public enum FilterableTraits {
        SHINY,
        FORM,
        MAX_IVS,
        MAX_HP,
        MAX_ATK,
        MAX_DEF,
        MAX_SPATK,
        MAX_SPDEF,
        MAX_SPE,
        NATURE,
        ABILITY;

        String valueFor(String props) { //Cursed, but works.
            if (props == null) return "Bad Egg??";
            return switch (this) {
                case FORM -> EggAttribute.getProperty(props, "form");
                case SHINY -> EggAttribute.getProperty(props, "shiny");
                case MAX_IVS -> {
                    AtomicInteger max = new AtomicInteger(0);
                    IVs.createRandomIVs(0).forEach(entry -> {
                        if (Integer.parseInt(EggAttribute.getProperty(props, "%s_iv".formatted(entry.getKey().toString()))) >= 31)
                            max.addAndGet(1);
                    });
                    yield String.valueOf(max.get());
                }
                case MAX_HP -> String.valueOf(Integer.parseInt(EggAttribute.getProperty(props, "HP_iv")) >= 31);
                case MAX_ATK -> String.valueOf(Integer.parseInt(EggAttribute.getProperty(props, "ATTACK_iv")) >= 31);
                case MAX_DEF -> String.valueOf(Integer.parseInt(EggAttribute.getProperty(props, "DEFENCE_iv")) >= 31);
                case MAX_SPATK -> String.valueOf(Integer.parseInt(EggAttribute.getProperty(props, "SPECIAL_ATTACK_iv")) >= 31);
                case MAX_SPDEF -> String.valueOf(Integer.parseInt(EggAttribute.getProperty(props, "SPECIAL_DEFENCE_iv")) >= 31);
                case MAX_SPE -> String.valueOf(Integer.parseInt(EggAttribute.getProperty(props, "SPEED_iv")) >= 31);
                case NATURE -> EggAttribute.getProperty(props, "nature");
                case ABILITY -> EggAttribute.getProperty(props, "ability");
            };
        }
    }
}
