package dev.ericksuarez.pokemon.battle.util;

import dev.ericksuarez.pokemon.battle.model.DamageRelations;
import dev.ericksuarez.pokemon.battle.model.Move;
import dev.ericksuarez.pokemon.battle.model.Moves;
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
import java.util.Optional;

public class UtilTest {

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
                .name("charmander")
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
