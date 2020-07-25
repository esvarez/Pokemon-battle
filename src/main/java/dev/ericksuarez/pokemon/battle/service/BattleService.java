package dev.ericksuarez.pokemon.battle.service;

import dev.ericksuarez.pokemon.battle.client.PokemonApiClient;
import dev.ericksuarez.pokemon.battle.error.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static dev.ericksuarez.pokemon.battle.util.DamageDealer.strongTypes;
import static dev.ericksuarez.pokemon.battle.util.DamageDealer.weakTypes;

@Slf4j
@Service
public class BattleService {

    private PokemonApiClient pokemonApiClient;

    @Autowired
    public BattleService(PokemonApiClient pokemonApiClient) {
        this.pokemonApiClient = pokemonApiClient;
    }

    public void findPokemon(String name) {
        var pokemon = pokemonApiClient.findPokemon(name);
        System.out.println(pokemon.get());
    }

    public String battleAnalysis(String idPokemon, String idPokemon2) {
        var pokemon = pokemonApiClient.findPokemon(idPokemon);
        var pokemon2 = pokemonApiClient.findPokemon(idPokemon2);

        weakTypes

        return "";
    }
}
