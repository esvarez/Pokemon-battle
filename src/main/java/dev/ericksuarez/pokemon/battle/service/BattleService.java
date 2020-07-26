package dev.ericksuarez.pokemon.battle.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dev.ericksuarez.pokemon.battle.client.PokemonApiClient;
import dev.ericksuarez.pokemon.battle.model.AnalysisResponse;
import dev.ericksuarez.pokemon.battle.model.Pokemon;
import dev.ericksuarez.pokemon.battle.model.Type;
import dev.ericksuarez.pokemon.battle.model.TypeDetails;
import dev.ericksuarez.pokemon.battle.model.Types;
import dev.ericksuarez.pokemon.battle.util.DamageDealer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BattleService {

    private Type type;

    private TypeDetails typeDetails;

    private DamageDealer damageDealer;

    private PokemonApiClient pokemonApiClient;

    @Autowired
    public BattleService(DamageDealer damageDealer, PokemonApiClient pokemonApiClient) {
        this.damageDealer = damageDealer;
        this.pokemonApiClient = pokemonApiClient;
    }

    public AnalysisResponse battleAnalysis(String idPokemon, String idPokemon2) {
        // TODO improve: use future to make async calls
        var pokemon = pokemonApiClient.findPokemonByIdentifier(idPokemon);
        var pokemon2 = pokemonApiClient.findPokemonByIdentifier(idPokemon2);

        System.out.println(pokemon + "\n" + pokemon2);
        checkExistTypes(pokemon);

        var strongTypes = getDamageList(pokemon.getTypes(), doubleDamageList);
        var weakTypes = getDamageList(pokemon.getTypes(), halfDamageList);
        log.info("event=retrieveDamageLists doubleDamageList={}, halfDamageList={}", doubleDamageList, halfDamageList);

        boolean doubleDamage = matchTypes(pokemon2.getTypes(), strongTypes);
        boolean halfDamage = matchTypes(pokemon2.getTypes(), weakTypes);

        String displayDoubleDamage = doubleDamage
                ? " deal double damage to "
                : " deal normal damage to ";
        String displayHalfDamage = halfDamage
                ? " receive half damage from "
                : " receive normal damage from ";

        return AnalysisResponse.builder()
                .battle(pokemon.getName() + " vs " + pokemon2.getName())
                .doubleDamage(pokemon.getName() + displayDoubleDamage + pokemon2.getName())
                .halfDamage(pokemon.getName() + displayHalfDamage + pokemon2.getName())
                .build();
    }

    private void checkExistTypes(Pokemon pokemon) {
        log.info("event=checkingDamageLists pokemon={}", pokemon);
        pokemon.getTypes().stream()
                .map(types -> types.getType())
                .filter(type -> !damageDealer.getDoubleDamageTypes().containsKey(type.getName()))
                .peek(type -> log.info("event=typeAddedToDoubleDamageList type={}", type))
                .forEach(this::addTypeToDoubleDamageList);

        pokemon.getTypes().stream()
                .map(types -> types.getType())
                .filter(type -> !damageDealer.getHalfDamageTypes().containsKey(type.getName()))
                .peek(type -> log.info("event=typeAddedToHalfDamageList type={}", type))
                .forEach(this::addTypeToHalfDamageList);
    }

    private void addTypeToDoubleDamageList(Type type) {
        var typeDetails = pokemonApiClient.findTypeByUrl(type.getUrl());
        var types = typeDetails.getDamageRelations().getDoubleDamageTo().stream()
                .map(doubleDamageTypes -> doubleDamageTypes.getName())
                .collect(Collectors.toSet());
        log.info("event=typeDoubleDamageListRetrieved types={}", types);
        damageDealer.addToDoubleDamageTypes(type.getName(), types);
    }

    private void addTypeToHalfDamageList(Type type) {
        //TODO improve: avoid double call of the same request
        var typeDetails = pokemonApiClient.findTypeByUrl(type.getUrl());
        var types = typeDetails.getDamageRelations().getHalfDamageFrom().stream()
                .map(halfDamageTypes -> halfDamageTypes.getName())
                .collect(Collectors.toSet());
        log.info("event=typeHalfDamageListRetrieved types={}", types);
        damageDealer.addTohalfDamageTypes(type.getName(), types);
    }

    private Set<String> getDamageList(List<Types> typesList, Function<String, Stream<String>> damageList) {
        return typesList.stream()
                .map(types -> types.getType().getName())
                .flatMap(damageList)
                .collect(Collectors.toSet());
    }

    private boolean matchTypes(List<Types> typesList, Set<String> damageList) {
        log.info("event=findingTypeOnDamageList typeList={}, damageList={}", typesList, damageList);
        return typesList.stream()
                .map(types -> types.getType().getName())
                .anyMatch(type -> damageList.contains(type));
    }

    private Function<String, Stream<String>> doubleDamageList = type -> damageDealer.getDoubleDamageTypes().get(type).stream();
    private Function<String, Stream<String>> halfDamageList = type -> damageDealer.getHalfDamageTypes().get(type).stream();
}
