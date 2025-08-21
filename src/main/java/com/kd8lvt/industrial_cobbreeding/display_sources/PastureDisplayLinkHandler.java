package com.kd8lvt.industrial_cobbreeding.display_sources;

import com.cobblemon.mod.common.block.entity.PokemonPastureBlockEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.simibubi.create.content.redstone.displayLink.DisplayLinkContext;
import com.simibubi.create.content.redstone.displayLink.source.ValueListDisplaySource;
import net.createmod.catnip.data.IntAttached;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.stream.Stream;

// At least this one doesn't crash. It also doesn't *work* but... it doesn't CRASH.
// Progress!
// Still could use help here tho q-q
public class PastureDisplayLinkHandler extends ValueListDisplaySource {

    //Method adapted from one written by @emperdog on Virtuositas' discord
    @Override
    public Stream<IntAttached<MutableComponent>> provideEntries(DisplayLinkContext context, int maxRows) {
        BlockEntity src = context.getSourceBlockEntity();
        if (!(src instanceof PokemonPastureBlockEntity pasture)) return Stream.empty();

        return pasture.getTetheredPokemon().stream() //tetheredPokemon could be private (half of this method was tbh lol)
            .map(t -> {
                Pokemon pokemon = t.getPokemon();
                MutableComponent speciesName = pokemon.getSpecies().getTranslatedName();
                MutableComponent pokemonName = pokemon.getNickname() != null
                    ? Component.literal("%1$S (%2$s)".formatted(pokemon.getNickname(), speciesName)) // Nickname (Species Name)
                    : speciesName;                                                                   // Just the Species Name
                return IntAttached.with(pasture.getTetheredPokemon().indexOf(t), pokemonName);
            })
            .sorted(IntAttached.comparator()).limit(maxRows);
    }

    @Override
    public Component getName() {
        return Component.translatable("industrialcobbreeding.display_source.pasture");
    }

    @Override
    protected boolean valueFirst() {
        return false;
    }
}
