package dev.ericksuarez.pokemon.battle.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.http.HttpClient;

@Slf4j
@Component
public class PokemonApiClient extends HttpClientBase {

    @Value("${application.pokemonApi.path}")
    private String path;

    @Autowired
    public PokemonApiClient(HttpClient httpClient, ObjectMapper objectMapper) {
        super(httpClient, objectMapper);
    }




}
