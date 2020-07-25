package dev.ericksuarez.pokemon.battle.service;

import dev.ericksuarez.pokemon.battle.client.PokemonApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattleService {

    private PokemonApiClient pokemonApiClient;

    @Autowired
    public BattleService(PokemonApiClient pokemonApiClient) {
        this.pokemonApiClient = pokemonApiClient;
    }

    public void findPokemon(String name) {
        var pokemon = pokemonApiClient.findPokemonByName(name);
        System.out.println(pokemon.get());
    }
}
