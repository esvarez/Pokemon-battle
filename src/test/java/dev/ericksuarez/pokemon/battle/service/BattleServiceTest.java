package dev.ericksuarez.pokemon.battle.service;

import dev.ericksuarez.pokemon.battle.client.PokemonApiClient;
import dev.ericksuarez.pokemon.battle.model.Move;
import dev.ericksuarez.pokemon.battle.model.Moves;
import dev.ericksuarez.pokemon.battle.model.Pokemon;
import dev.ericksuarez.pokemon.battle.model.Type;
import dev.ericksuarez.pokemon.battle.model.Types;
import dev.ericksuarez.pokemon.battle.util.DamageDealer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static dev.ericksuarez.pokemon.battle.util.UtilTest.buildPokemon;
import static dev.ericksuarez.pokemon.battle.util.UtilTest.buildResponse;
import static dev.ericksuarez.pokemon.battle.util.UtilTest.buildTypeDetails;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BattleServiceTest {

    @Mock
    private DamageDealer damageDealer;

    @Mock
    private PokemonApiClient pokemonApiClient;

    private BattleService battleService;

    @Before
    public void setUp() {
        this.battleService = new BattleService(damageDealer, pokemonApiClient);
    }

    @Test
    public void battleAnalysis_emptyMaps_returnAnalysisResponse() {
        when(pokemonApiClient.findPokemonByIdentifier(anyString()))
                .thenReturn(buildPokemon());
        when(pokemonApiClient.findTypeByUrl(anyString()))
                .thenReturn(buildTypeDetails());

        when(damageDealer.getDoubleDamageTypes()).thenReturn();
        when(damageDealer.getHalfDamageTypes()).thenReturn(new HashMap<>());

        when(damageDealer.getDoubleDamageTypes()).thenAnswer(new Answer<Map>() {
            private int count = 1;

            public Map<String, Set<String>> answer(InvocationOnMock invocation) {
                if (count++ % 2 == 0)
                    return new HashMap<>();
                else {
                    var map = new HashMap<String, Set<String>>();
                    var set = new HashSet<String>();
                    set.add("steel");
                    set.add("bug");
                    set.add("grass");
                    map.put("fire", set);
                    return map;
                }

            }
        });

        final var analysis = battleService.battleAnalysis("1","2");
    }

    @Test
    public void battleAnalysis_fireDealDoubleDamage_returnAnalysisResponse() {
        when(pokemonApiClient.findPokemonByIdentifier(anyString())).thenAnswer(new Answer<Pokemon>() {
            private int count = 0;
            private String name = "weedle";
            private List<Types> types = Arrays.asList(Types.builder()
                    .slot(1)
                    .type(Type.builder()
                            .name("bug")
                            .url("https://pokeapi.co/api/v2/type/7/")
                            .build())
                    .build(),
                    Types.builder()
                            .slot(2)
                            .type(Type.builder()
                                    .name("poison")
                                    .url("https://pokeapi.co/api/v2/type/4/")
                                    .build())
                            .build()
                    );
            private List<Moves> moves = Arrays.asList(
                    Moves.builder()
                            .move(Move.builder()
                                    .name("poison-sting")
                                    .url("https://pokeapi.co/api/v2/move/40/")
                                    .build())
                            .build());
            public Pokemon answer(InvocationOnMock invocation) {
                if (count++ % 2 == 0)
                    return buildPokemon();
                else
                    return buildPokemon(name, types, moves);
            }
        });

        final var analysis = battleService.battleAnalysis("1","2");
    }
}
