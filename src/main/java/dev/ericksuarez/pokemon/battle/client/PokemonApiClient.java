package dev.ericksuarez.pokemon.battle.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ericksuarez.pokemon.battle.model.Pokemon;
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
    private String pokemon;

    private final String findPokemon = path + pokemon;

    private final String template = "%s%s";

    @Autowired
    public PokemonApiClient(HttpClient httpClient, ObjectMapper objectMapper) {
        super(httpClient, objectMapper);
    }

    public Optional<Pokemon> findPokemon(String identifier) {
        log.info("event=findPokemonByNameInvoked identifier={}", identifier);
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(path + pokemon + identifier))
                .header("Content-Type", "application/json")
                .build();
        var pokemon = makeRequest(request, Pokemon.class);
        log.info("event=pokemonRetrieved pokemon={}", pokemon);
        return Optional.of(pokemon);
    }
}
