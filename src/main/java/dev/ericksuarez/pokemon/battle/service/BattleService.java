package dev.ericksuarez.pokemon.battle.service;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import dev.ericksuarez.pokemon.battle.client.PokemonApiClient;
import dev.ericksuarez.pokemon.battle.model.AnalysisResponse;
import dev.ericksuarez.pokemon.battle.model.CommonMovesPaged;
import dev.ericksuarez.pokemon.battle.model.Move;
import dev.ericksuarez.pokemon.battle.model.Moves;
import dev.ericksuarez.pokemon.battle.model.Name;
import dev.ericksuarez.pokemon.battle.model.Pokemon;
import dev.ericksuarez.pokemon.battle.model.Type;
import dev.ericksuarez.pokemon.battle.model.TypeDetails;
import dev.ericksuarez.pokemon.battle.model.Types;
import dev.ericksuarez.pokemon.battle.util.DamageDealer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BattleService {

    private Type type;

    private TypeDetails typeDetails;

    private Environment environment;

    private DamageDealer damageDealer;

    private PokemonApiClient pokemonApiClient;

    @Autowired
    public BattleService(Environment environment, DamageDealer damageDealer, PokemonApiClient pokemonApiClient) {
        this.environment = environment;
        this.damageDealer = damageDealer;
        this.pokemonApiClient = pokemonApiClient;
    }

    public AnalysisResponse battleAnalysis(String idPokemon, String idPokemon2) {
        // TODO improve: use future to make async calls
        var pokemon = pokemonApiClient.findPokemonByIdentifier(idPokemon);
        var pokemon2 = pokemonApiClient.findPokemonByIdentifier(idPokemon2);

        checkExistTypes(pokemon.getTypes());

        var strongTypes = getDamageList(pokemon.getTypes(), doubleDamageList);
        var weakTypes = getDamageList(pokemon.getTypes(), halfDamageList);
        log.info("event=retrieveDamageLists strongTypes={}, weakTypes={}", strongTypes, weakTypes);

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

    public CommonMovesPaged comparePokemon(String[] pokemons, Optional<String> lang, Optional<Integer> movesPage,
                                           Optional<Integer> page) {
        final int DEFAULT_LIMIT = 10;
        final int DEFAULT_PAGE = 1;
        final String DEFAULT_LANG = "en";
        int limit = movesPage.orElse(DEFAULT_LIMIT);
        int pageNumber = page.orElse(DEFAULT_PAGE);
        int skip = (pageNumber - 1) * limit;

        var pokemonsToProcess = Arrays.stream(pokemons)
                .map(pokemonApiClient::findPokemonByIdentifier)
                .distinct()
                .collect(Collectors.toList());

        String pokemonNames = pokemonsToProcess.stream()
                .map(Pokemon::getName)
                .collect(Collectors.joining(","));

        var commonMoves = pokemonsToProcess.stream()
                .flatMap(pokemon -> pokemon.getMoves().stream())
                .map(Moves::getMove)
                .collect(Collectors.groupingBy(move -> move, Collectors.counting()))
                .entrySet().stream()
                .filter(key -> key.getValue() == pokemonsToProcess.size())
                .map(map -> map.getKey())
                .collect(Collectors.toList());

        var moves = translateMoves(commonMoves, lang, limit, skip);

        String pokemonIds = pokemonsToProcess.stream()
                .map(Pokemon::getId)
                .collect(Collectors.joining(","));

        var movesPaged = getPaginateValues(pokemonIds, lang.orElse(DEFAULT_LANG), pageNumber, limit, commonMoves.size());

        return movesPaged.toBuilder()
                .pokemonsCompared(pokemonNames)
                .moveList(moves)
                .build();
    }

    private void checkExistTypes(List<Types> typesList) {
        log.info("event=checkingDamageLists typesList={}", typesList);
        typesList.stream()
                .map(types -> types.getType())
                .filter(type -> !damageDealer.getDoubleDamageTypes().containsKey(type.getName()))
                .peek(type -> log.info("event=typeAddedToDoubleDamageList type={}", type))
                .forEach(this::addTypeToDoubleDamageList);

        typesList.stream()
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

    private CommonMovesPaged getPaginateValues(String pokemonIds, String lang, int pageNumber, int limit, int totalMoves) {
        String host = InetAddress.getLoopbackAddress().getHostName() + ":" +  environment.getProperty("server.port")
                + "/api/compare?pokemons=" + pokemonIds;
        String basicValues = "&lang=" + lang + "&limit=" + limit;

        int totalPages = totalMoves / limit;
        String previous = pageNumber == 1
                ? "none"
                : host + "&page=" + (pageNumber - 1) + basicValues;
        String next = pageNumber == totalPages
                ? "none"
                : host + "&page=" + (pageNumber + 1) + basicValues;

        return CommonMovesPaged.builder()
                .lang(lang)
                .page(pageNumber)
                .previous(previous)
                .next(next)
                .totalMoves(totalMoves)
                .pages(totalPages)
                .moves(limit)
                .build();
    }

    private List<String> translateMoves(List<Move> moves, Optional<String> lang, int limit, int skip) {
        if (lang.isPresent()) {
            String language = lang.get();
            return moves.stream()
                    .skip(skip)
                    .map(Move::getUrl)
                    .map(pokemonApiClient::findMoveByUrl)
                    .flatMap(moveDetails -> moveDetails.getNames().stream())
                    .filter(name -> name.getLanguage().getName().equals(language))
                    .map(Name::getName)
                    .limit(limit)
                    .collect(Collectors.toList());
        } else {
            return moves.stream()
                    .skip(skip)
                    .map(Move::getName)
                    .limit(limit)
                    .collect(Collectors.toList());
        }
    }

    private Function<String, Stream<String>> doubleDamageList = type -> damageDealer.getDoubleDamageTypes().get(type).stream();
    private Function<String, Stream<String>> halfDamageList = type -> damageDealer.getHalfDamageTypes().get(type).stream();
}
