package com.kd8lvt.industrial_cobbreeding.display_link;

import com.cobblemon.mod.common.block.entity.PokemonPastureBlockEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.simibubi.create.api.behaviour.display.DisplaySource;
import com.simibubi.create.content.redstone.displayLink.DisplayLinkContext;
import com.simibubi.create.content.redstone.displayLink.target.DisplayBoardTarget;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTargetStats;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

public class PastureDisplayLinkHandler extends DisplaySource {
    @Override
    public List<MutableComponent> provideText(DisplayLinkContext context, DisplayTargetStats stats) {
        BlockEntity src = context.getSourceBlockEntity();
        if (!(src instanceof PokemonPastureBlockEntity pasture)) return List.of();

        return pasture.getTetheredPokemon().stream().map(t->{
            Pokemon pkmn = t.getPokemon();
            if (pkmn != null) return pkmn.getDisplayName();
            return EMPTY_LINE;
        }).toList();
    }
}
