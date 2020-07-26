package dev.ericksuarez.pokemon.battle.util;

import dev.ericksuarez.pokemon.battle.model.DamageRelations;
import dev.ericksuarez.pokemon.battle.model.Language;
import dev.ericksuarez.pokemon.battle.model.Move;
import dev.ericksuarez.pokemon.battle.model.MoveDetails;
import dev.ericksuarez.pokemon.battle.model.Moves;
import dev.ericksuarez.pokemon.battle.model.Name;
import dev.ericksuarez.pokemon.battle.model.Pokemon;
import dev.ericksuarez.pokemon.battle.model.Type;
import dev.ericksuarez.pokemon.battle.model.TypeDetails;
import dev.ericksuarez.pokemon.battle.model.Types;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UtilTest {
    private static Map<String, MoveDetails> map;

    static {
        map = new HashMap<>();
        map.put("https://pokeapi.co/api/v2/move/9/", MoveDetails.builder()
                .name("thunder-punch")
                .names(Arrays.asList(
                        Name.builder()
                                .name("Puño Trueno")
                                .language(Language.builder()
                                        .name("es")
                                        .build())
                                .build(),
                        Name.builder()
                                .name("Tuonopugno")
                                .language(Language.builder()
                                        .name("it")
                                        .build())
                                .build()
                ))
                .build());
        map.put("https://pokeapi.co/api/v2/move/10/", MoveDetails.builder()
                .name("scratch")
                .names(Arrays.asList(
                        Name.builder()
                                .name("Arañazo")
                                .language(Language.builder()
                                        .name("es")
                                        .build())
                                .build(),
                        Name.builder()
                                .name("Graffio")
                                .language(Language.builder()
                                        .name("it")
                                        .build())
                                .build()
                ))
                .build());
        map.put("https://pokeapi.co/api/v2/move/5/", MoveDetails.builder()
                .name("mega-punch")
                .names(Arrays.asList(
                        Name.builder()
                                .name("Megapuño")
                                .language(Language.builder()
                                        .name("es")
                                        .build())
                                .build(),
                        Name.builder()
                                .name("Megapugno")
                                .language(Language.builder()
                                        .name("it")
                                        .build())
                                .build()
                ))
                .build());
        map.put("https://pokeapi.co/api/v2/move/7/", MoveDetails.builder()
                .name("fire-punch")
                .names(Arrays.asList(
                        Name.builder()
                                .name("Puño Fuego")
                                .language(Language.builder()
                                        .name("es")
                                        .build())
                                .build(),
                        Name.builder()
                                .name("Fuocopugno")
                                .language(Language.builder()
                                        .name("it")
                                        .build())
                                .build()
                ))
                .build());
        map.put("https://pokeapi.co/api/v2/move/15/", MoveDetails.builder()
                .name("cut")
                .names(Arrays.asList(
                        Name.builder()
                                .name("Corte")
                                .language(Language.builder()
                                        .name("es")
                                        .build())
                                .build(),
                        Name.builder()
                                .name("Taglio")
                                .language(Language.builder()
                                        .name("it")
                                        .build())
                                .build()
                ))
                .build());
        map.put("https://pokeapi.co/api/v2/move/19/", MoveDetails.builder()
                .name("fly")
                .names(Arrays.asList(
                        Name.builder()
                                .name("Vuelo")
                                .language(Language.builder()
                                        .name("es")
                                        .build())
                                .build(),
                        Name.builder()
                                .name("Volo")
                                .language(Language.builder()
                                        .name("it")
                                        .build())
                                .build()
                ))
                .build());
        map.put("https://pokeapi.co/api/v2/move/17/", MoveDetails.builder()
                .name("wing-attack")
                .names(Arrays.asList(
                        Name.builder()
                                .name("Ataque Ala")
                                .language(Language.builder()
                                        .name("es")
                                        .build())
                                .build(),
                        Name.builder()
                                .name("Attacco d’Ala")
                                .language(Language.builder()
                                        .name("it")
                                        .build())
                                .build()
                ))
                .build());

    }

    public static MoveDetails getMoveDetails(String url) {
        return map.get(url);
    }

    public static Pokemon buildPokemon() {
        var types = Collections.singletonList(Types.builder()
                .slot(1)
                .type(Type.builder()
                        .name("fire")
                        .url("https://pokeapi.co/api/v2/type/10/")
                        .build())
                .build());
        var moves = Arrays.asList(
                Moves.builder()
                        .move(Move.builder()
                                .name("mega-punch")
                                .url("https://pokeapi.co/api/v2/move/5/")
                                .build())
                        .build(),
                Moves.builder()
                        .move(Move.builder()
                                .name("thunder-punch")
                                .url("https://pokeapi.co/api/v2/move/9/")
                                .build())
                        .build(),
                Moves.builder()
                        .move(Move.builder()
                                .name("scratch")
                                .url("https://pokeapi.co/api/v2/move/10/")
                                .build())
                        .build(),
                Moves.builder()
                        .move(Move.builder()
                                .name("fire-punch")
                                .url("https://pokeapi.co/api/v2/move/7/")
                                .build())
                        .build()
        );

        return Pokemon.builder()
                .id("4")
                .name("charmander")
                .types(types)
                .moves(moves)
                .build();
    }

    public static Pokemon buildCharmander() {
        String name = "charmander";
        String id = "4";
        var types = Collections.singletonList(Types.builder()
                .slot(1)
                .type(Type.builder()
                        .name("fire")
                        .url("https://pokeapi.co/api/v2/type/10/")
                        .build())
                .build());
        var moves = Arrays.asList(
                Moves.builder()
                        .move(Move.builder()
                                .name("mega-punch")
                                .url("https://pokeapi.co/api/v2/move/5/")
                                .build())
                        .build(),
                Moves.builder()
                        .move(Move.builder()
                                .name("thunder-punch")
                                .url("https://pokeapi.co/api/v2/move/9/")
                                .build())
                        .build(),
                Moves.builder()
                        .move(Move.builder()
                                .name("scratch")
                                .url("https://pokeapi.co/api/v2/move/10/")
                                .build())
                        .build(),
                Moves.builder()
                        .move(Move.builder()
                                .name("fire-punch")
                                .url("https://pokeapi.co/api/v2/move/7/")
                                .build())
                        .build()
        );
        return buildPokemon(name, id, types, moves);
    }

    public static Pokemon buildCharmeleon() {
        String name = "charmeleon";
        String id = "5";
        var types = Collections.singletonList(Types.builder()
                .slot(1)
                .type(Type.builder()
                        .name("fire")
                        .url("https://pokeapi.co/api/v2/type/10/")
                        .build())
                .build());
        var moves = Arrays.asList(
                Moves.builder()
                        .move(Move.builder()
                                .name("mega-punch")
                                .url("https://pokeapi.co/api/v2/move/5/")
                                .build())
                        .build(),
                Moves.builder()
                        .move(Move.builder()
                                .name("thunder-punch")
                                .url("https://pokeapi.co/api/v2/move/9/")
                                .build())
                        .build(),
                Moves.builder()
                        .move(Move.builder()
                                .name("cut")
                                .url("https://pokeapi.co/api/v2/move/15/")
                                .build())
                        .build(),
                Moves.builder()
                        .move(Move.builder()
                                .name("fire-punch")
                                .url("https://pokeapi.co/api/v2/move/7/")
                                .build())
                        .build()
        );
        return buildPokemon(name, id, types, moves);
    }

    public static Pokemon buildCharizard() {
        String name = "charizard";
        String id = "6";
        var types = Collections.singletonList(Types.builder()
                .slot(1)
                .type(Type.builder()
                        .name("fire")
                        .url("https://pokeapi.co/api/v2/type/10/")
                        .build())
                .build());
        var moves = Arrays.asList(
                Moves.builder()
                        .move(Move.builder()
                                .name("mega-punch")
                                .url("https://pokeapi.co/api/v2/move/5/")
                                .build())
                        .build(),
                Moves.builder()
                        .move(Move.builder()
                                .name("fly")
                                .url("https://pokeapi.co/api/v2/move/19/")
                                .build())
                        .build(),
                Moves.builder()
                        .move(Move.builder()
                                .name("wing-attack")
                                .url("https://pokeapi.co/api/v2/move/17/")
                                .build())
                        .build(),
                Moves.builder()
                        .move(Move.builder()
                                .name("fire-punch")
                                .url("https://pokeapi.co/api/v2/move/7/")
                                .build())
                        .build()
        );
        return buildPokemon(name, id, types, moves);
    }

    public static Pokemon buildPokemon(String name, String id, List<Types> types, List<Moves> moves) {
        return Pokemon.builder()
                .id(id)
                .name(name)
                .types(types)
                .moves(moves)
                .build();
    }

    public static TypeDetails buildTypeDetails() {
        return buildTypeDetails("fire");
    }


    public static TypeDetails buildTypeDetails(String name) {
        return TypeDetails.builder()
                .name(name)
                .damageRelations(DamageRelations.builder()
                        .doubleDamageTo(Arrays.asList(
                                Type.builder()
                                        .name("bug")
                                        .build(),
                                Type.builder()
                                        .name("grass")
                                        .build(),
                                Type.builder()
                                        .name("steel")
                                        .build()))
                        .halfDamageFrom(Arrays.asList(
                                Type.builder()
                                        .name("ice")
                                        .build(),
                                Type.builder()
                                        .name("fire")
                                        .build(),
                                Type.builder()
                                        .name("steel")
                                        .build()))
                        .build())
                .build();
    }

    public static HttpResponse buildResponse() {
        return buildResponse(200);
    }

    public static HttpResponse buildResponse(int statusCode) {
        return new HttpResponse() {
            @Override
            public int statusCode() {
                return statusCode;
            }

            @Override
            public HttpRequest request() {
                return null;
            }

            @Override
            public Optional<HttpResponse> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }

            @Override
            public Object body() {
                return "bodyTest";
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return null;
            }
        };
    }
}
