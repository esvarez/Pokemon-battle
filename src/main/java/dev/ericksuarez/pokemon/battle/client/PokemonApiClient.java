package dev.ericksuarez.pokemon.battle.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.ericksuarez.pokemon.battle.model.MoveDetails;
import dev.ericksuarez.pokemon.battle.model.Pokemon;
import dev.ericksuarez.pokemon.battle.model.TypeDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Optional;

@Slf4j
@Component
public class PokemonApiClient extends HttpClientBase {

    @Value("${application.pokemonApi.path}")
    private String path;

    @Value("${application.pokemonApi.findPokemon}")
    private String findPokemon;

    /*@Value("${application.pokemonApi.findType}")
    private String findType;*/

    @Autowired
    public PokemonApiClient(HttpClient httpClient, ObjectMapper objectMapper) {
        super(httpClient, objectMapper);
    }

    public Pokemon findPokemonByIdentifier(String identifier) {
        log.info("event=findPokemonByNameInvoked identifier={}", identifier);
        var pokemon = findByUrl(URI.create(path + findPokemon + identifier), Pokemon.class);
        log.info("event=pokemonRetrieved pokemon={}", pokemon);
        return pokemon;
    }

    public TypeDetails findTypeByUrl(String url) {
        log.info("event=findTypeByUrlInvoked url={}", url);
        var typeDetails = findByUrl(URI.create(url), TypeDetails.class);
        log.info("event=typeRetrieved typeDetails={}", typeDetails);
        return typeDetails;
    }

    public MoveDetails findMoveByUrl(String url) {
        log.info("event=findMoveByUrl url={}", url);
        var move = findByUrl(URI.create(url), MoveDetails.class);
        log.info("event=moveRetrieved move={}", move);
        return move;
    }

    private <T> T findByUrl(URI url, Class<T> tClass) {
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(url)
                .header("Content-Type", "application/json")
                .build();
        return makeRequest(request, tClass);
    }

}
