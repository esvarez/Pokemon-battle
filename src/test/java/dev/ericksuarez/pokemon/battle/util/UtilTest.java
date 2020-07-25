package dev.ericksuarez.pokemon.battle.util;

import dev.ericksuarez.pokemon.battle.model.Move;
import dev.ericksuarez.pokemon.battle.model.Moves;
import dev.ericksuarez.pokemon.battle.model.Pokemon;
import dev.ericksuarez.pokemon.battle.model.Type;
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
 //Pokemon(name=charmander, moves=[Moves(move=Move(name=mega-punch, url=https://pokeapi.co/api/v2/move/5/)), Moves(move=Move(name=fire-punch, url=https://pokeapi.co/api/v2/move/7/)), Moves(move=Move(name=thunder-punch, url=https://pokeapi.co/api/v2/move/9/)), Moves(move=Move(name=scratch, url=https://pokeapi.co/api/v2/move/10/)), Moves(move=Move(name=swords-dance, url=https://pokeapi.co/api/v2/move/14/)), Moves(move=Move(name=cut, url=https://pokeapi.co/api/v2/move/15/)), Moves(move=Move(name=mega-kick, url=https://pokeapi.co/api/v2/move/25/)), Moves(move=Move(name=headbutt, url=https://pokeapi.co/api/v2/move/29/)), Moves(move=Move(name=body-slam, url=https://pokeapi.co/api/v2/move/34/)), Moves(move=Move(name=take-down, url=https://pokeapi.co/api/v2/move/36/)), Moves(move=Move(name=double-edge, url=https://pokeapi.co/api/v2/move/38/)), Moves(move=Move(name=leer, url=https://pokeapi.co/api/v2/move/43/)), Moves(move=Move(name=bite, url=https://pokeapi.co/api/v2/move/44/)), Moves(move=Move(name=growl, url=https://pokeapi.co/api/v2/move/45/)), Moves(move=Move(name=ember, url=https://pokeapi.co/api/v2/move/52/)), Moves(move=Move(name=flamethrower, url=https://pokeapi.co/api/v2/move/53/)), Moves(move=Move(name=submission, url=https://pokeapi.co/api/v2/move/66/)), Moves(move=Move(name=counter, url=https://pokeapi.co/api/v2/move/68/)), Moves(move=Move(name=seismic-toss, url=https://pokeapi.co/api/v2/move/69/)), Moves(move=Move(name=strength, url=https://pokeapi.co/api/v2/move/70/)), Moves(move=Move(name=dragon-rage, url=https://pokeapi.co/api/v2/move/82/)), Moves(move=Move(name=fire-spin, url=https://pokeapi.co/api/v2/move/83/)), Moves(move=Move(name=dig, url=https://pokeapi.co/api/v2/move/91/)), Moves(move=Move(name=toxic, url=https://pokeapi.co/api/v2/move/92/)), Moves(move=Move(name=rage, url=https://pokeapi.co/api/v2/move/99/)), Moves(move=Move(name=mimic, url=https://pokeapi.co/api/v2/move/102/)), Moves(move=Move(name=double-team, url=https://pokeapi.co/api/v2/move/104/)), Moves(move=Move(name=smokescreen, url=https://pokeapi.co/api/v2/move/108/)), Moves(move=Move(name=defense-curl, url=https://pokeapi.co/api/v2/move/111/)), Moves(move=Move(name=reflect, url=https://pokeapi.co/api/v2/move/115/)), Moves(move=Move(name=bide, url=https://pokeapi.co/api/v2/move/117/)), Moves(move=Move(name=fire-blast, url=https://pokeapi.co/api/v2/move/126/)), Moves(move=Move(name=swift, url=https://pokeapi.co/api/v2/move/129/)), Moves(move=Move(name=skull-bash, url=https://pokeapi.co/api/v2/move/130/)), Moves(move=Move(name=rest, url=https://pokeapi.co/api/v2/move/156/)), Moves(move=Move(name=rock-slide, url=https://pokeapi.co/api/v2/move/157/)), Moves(move=Move(name=slash, url=https://pokeapi.co/api/v2/move/163/)), Moves(move=Move(name=substitute, url=https://pokeapi.co/api/v2/move/164/)), Moves(move=Move(name=snore, url=https://pokeapi.co/api/v2/move/173/)), Moves(move=Move(name=curse, url=https://pokeapi.co/api/v2/move/174/)), Moves(move=Move(name=protect, url=https://pokeapi.co/api/v2/move/182/)), Moves(move=Move(name=scary-face, url=https://pokeapi.co/api/v2/move/184/)), Moves(move=Move(name=belly-drum, url=https://pokeapi.co/api/v2/move/187/)), Moves(move=Move(name=mud-slap, url=https://pokeapi.co/api/v2/move/189/)), Moves(move=Move(name=outrage, url=https://pokeapi.co/api/v2/move/200/)), Moves(move=Move(name=endure, url=https://pokeapi.co/api/v2/move/203/)), Moves(move=Move(name=swagger, url=https://pokeapi.co/api/v2/move/207/)), Moves(move=Move(name=fury-cutter, url=https://pokeapi.co/api/v2/move/210/)), Moves(move=Move(name=attract, url=https://pokeapi.co/api/v2/move/213/)), Moves(move=Move(name=sleep-talk, url=https://pokeapi.co/api/v2/move/214/)), Moves(move=Move(name=return, url=https://pokeapi.co/api/v2/move/216/)), Moves(move=Move(name=frustration, url=https://pokeapi.co/api/v2/move/218/)), Moves(move=Move(name=dynamic-punch, url=https://pokeapi.co/api/v2/move/223/)), Moves(move=Move(name=dragon-breath, url=https://pokeapi.co/api/v2/move/225/)), Moves(move=Move(name=iron-tail, url=https://pokeapi.co/api/v2/move/231/)), Moves(move=Move(name=metal-claw, url=https://pokeapi.co/api/v2/move/232/)), Moves(move=Move(name=hidden-power, url=https://pokeapi.co/api/v2/move/237/)), Moves(move=Move(name=sunny-day, url=https://pokeapi.co/api/v2/move/241/)), Moves(move=Move(name=crunch, url=https://pokeapi.co/api/v2/move/242/)), Moves(move=Move(name=ancient-power, url=https://pokeapi.co/api/v2/move/246/)), Moves(move=Move(name=rock-smash, url=https://pokeapi.co/api/v2/move/249/)), Moves(move=Move(name=beat-up, url=https://pokeapi.co/api/v2/move/251/)), Moves(move=Move(name=heat-wave, url=https://pokeapi.co/api/v2/move/257/)), Moves(move=Move(name=will-o-wisp, url=https://pokeapi.co/api/v2/move/261/)), Moves(move=Move(name=facade, url=https://pokeapi.co/api/v2/move/263/)), Moves(move=Move(name=focus-punch, url=https://pokeapi.co/api/v2/move/264/)), Moves(move=Move(name=brick-break, url=https://pokeapi.co/api/v2/move/280/)), Moves(move=Move(name=secret-power, url=https://pokeapi.co/api/v2/move/290/)), Moves(move=Move(name=air-cutter, url=https://pokeapi.co/api/v2/move/314/)), Moves(move=Move(name=overheat, url=https://pokeapi.co/api/v2/move/315/)), Moves(move=Move(name=rock-tomb, url=https://pokeapi.co/api/v2/move/317/)), Moves(move=Move(name=aerial-ace, url=https://pokeapi.co/api/v2/move/332/)), Moves(move=Move(name=dragon-claw, url=https://pokeapi.co/api/v2/move/337/)), Moves(move=Move(name=dragon-dance, url=https://pokeapi.co/api/v2/move/349/)), Moves(move=Move(name=natural-gift, url=https://pokeapi.co/api/v2/move/363/)), Moves(move=Move(name=fling, url=https://pokeapi.co/api/v2/move/374/)), Moves(move=Move(name=flare-blitz, url=https://pokeapi.co/api/v2/move/394/)), Moves(move=Move(name=dragon-pulse, url=https://pokeapi.co/api/v2/move/406/)), Moves(move=Move(name=dragon-rush, url=https://pokeapi.co/api/v2/move/407/)), Moves(move=Move(name=shadow-claw, url=https://pokeapi.co/api/v2/move/421/)), Moves(move=Move(name=fire-fang, url=https://pokeapi.co/api/v2/move/424/)), Moves(move=Move(name=captivate, url=https://pokeapi.co/api/v2/move/445/)), Moves(move=Move(name=hone-claws, url=https://pokeapi.co/api/v2/move/468/)), Moves(move=Move(name=flame-burst, url=https://pokeapi.co/api/v2/move/481/)), Moves(move=Move(name=flame-charge, url=https://pokeapi.co/api/v2/move/488/)), Moves(move=Move(name=round, url=https://pokeapi.co/api/v2/move/496/)), Moves(move=Move(name=echoed-voice, url=https://pokeapi.co/api/v2/move/497/)), Moves(move=Move(name=incinerate, url=https://pokeapi.co/api/v2/move/510/)), Moves(move=Move(name=inferno, url=https://pokeapi.co/api/v2/move/517/)), Moves(move=Move(name=fire-pledge, url=https://pokeapi.co/api/v2/move/519/)), Moves(move=Move(name=work-up, url=https://pokeapi.co/api/v2/move/526/)), Moves(move=Move(name=confide, url=https://pokeapi.co/api/v2/move/590/)), Moves(move=Move(name=power-up-punch, url=https://pokeapi.co/api/v2/move/612/))], types=[Types(slot=1, type=Type(name=fire, url=https://pokeapi.co/api/v2/type/10/))])

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
