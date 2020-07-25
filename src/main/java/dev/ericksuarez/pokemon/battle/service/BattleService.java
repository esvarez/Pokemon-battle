package dev.ericksuarez.pokemon.battle.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dev.ericksuarez.pokemon.battle.client.PokemonApiClient;
import dev.ericksuarez.pokemon.battle.model.Pokemon;
import dev.ericksuarez.pokemon.battle.model.Type;
import dev.ericksuarez.pokemon.battle.model.Types;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static dev.ericksuarez.pokemon.battle.util.DamageDealer.doubleDamageTypes;
import static dev.ericksuarez.pokemon.battle.util.DamageDealer.halfDamageTypes;

@Slf4j
@Service
public class BattleService {

    private PokemonApiClient pokemonApiClient;

    @Autowired
    public BattleService(PokemonApiClient pokemonApiClient) {
        this.pokemonApiClient = pokemonApiClient;
    }

    public void findPokemon(String name) {
        var pokemon = pokemonApiClient.findPokemonByIdentifier(name);
        System.out.println(pokemon);
    }

    public String battleAnalysis(String idPokemon, String idPokemon2) {
        var pokemon = pokemonApiClient.findPokemonByIdentifier(idPokemon);
        var pokemon2 = pokemonApiClient.findPokemonByIdentifier(idPokemon2);


        log.info("maps before checking doubleDame={} halfDamage={}", doubleDamageTypes, halfDamageTypes);
        checkExistTypes(pokemon);
        log.info("maps after checking doubleDame={} halfDamage={}", doubleDamageTypes, halfDamageTypes);

        System.out.println(pokemon.getName() + " vs " + pokemon2.getName());

        var strongTypes = getDamageList(pokemon.getTypes(), doubleDamageList);
        var weakTypes = getDamageList(pokemon.getTypes(), halfDamageList);

        boolean doubleDamage = matchTypes(pokemon2.getTypes(), strongTypes);
        boolean halfDamage = matchTypes(pokemon2.getTypes(), weakTypes);

        if (doubleDamage) {
            System.out.println(pokemon.getName() + " deal double damage to " + pokemon2.getName());
        } else {
            System.out.println(pokemon.getName() + " deal normal damage to " + pokemon2.getName());
        }

        if (halfDamage) {
            System.out.println(pokemon.getName() + " receive half damage from " + pokemon2.getName());
        } else {
            System.out.println(pokemon.getName() + " receive normal damage from " + pokemon2.getName());
        }
        return "";
    }

    private void checkExistTypes(Pokemon pokemon) {
        log.info("event=checkingDamageLists pokemon={}", pokemon);
        pokemon.getTypes().stream()
                .map(types -> types.getType())
                .filter(type -> !doubleDamageTypes.containsKey(type.getName()))
                .peek(this::addTypeToDoubleDamageList);

        pokemon.getTypes().stream()
                .map(types -> types.getType())
                .filter(type -> !halfDamageTypes.containsKey(type.getName()))
                .peek(this::addTypeToHalfDamageList);
    }

    private void addTypeToDoubleDamageList(Type type) {
        log.info("event=addingTypeToDoubleDamageList Type={}", type);
        var typeDetails = pokemonApiClient.findTypeByUrl(type.getUrl());
        var types = typeDetails.getDamageRelations().getDoubleDamageTo().stream()
                .map(doubleDamageTypes -> doubleDamageTypes.getName())
                .collect(Collectors.toSet());
        log.info("event=typeDoubleDamageListRetrieved types={}", types);
        doubleDamageTypes.put(type.getName(), types);
    }

    private void addTypeToHalfDamageList(Type type) {
        log.info("event=addingTypeToHalfDamageList Type={}", type);
        var typeDetails = pokemonApiClient.findTypeByUrl(type.getUrl());
        var types = typeDetails.getDamageRelations().getHalfDamageFrom().stream()
                .map(halfDamageTypes -> halfDamageTypes.getName())
                .collect(Collectors.toSet());
        log.info("event=typeHalfDamageListRetrieved types={}", types);
        halfDamageTypes.put(type.getName(), types);
    }

    private Set<String> getDamageList(List<Types> typesList, Function<String, Stream<String>> damageList) {
        return typesList.stream()
                .map(types -> types.getType().getName())
                .flatMap(damageList)
                .collect(Collectors.toSet());
    }

    private boolean matchTypes(List<Types> typesList, Set<String> damageList) {
        return typesList.stream()
                .map(types -> types.getType().getName())
                .anyMatch(type -> damageList.contains(type));
    }

    private Function<String, Stream<String>> doubleDamageList = type -> doubleDamageTypes.get(type).stream();
    private Function<String, Stream<String>> halfDamageList = type -> halfDamageTypes.get(type).stream();
}
