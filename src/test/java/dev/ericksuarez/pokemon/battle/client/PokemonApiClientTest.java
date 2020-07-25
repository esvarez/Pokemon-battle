package dev.ericksuarez.pokemon.battle.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ericksuarez.pokemon.battle.error.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Optional;

import static dev.ericksuarez.pokemon.battle.util.UtilTest.buildResponse;
import static dev.ericksuarez.pokemon.battle.util.UtilTest.buildPokemon;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PokemonApiClientTest {
    @Mock
    private HttpClient httpClient;

    @Mock
    private ObjectMapper objectMapper;

    private PokemonApiClient pokemonApiClient;

    @Before
    public void setUp() throws IOException, InterruptedException {
        this.pokemonApiClient = new PokemonApiClient(httpClient, objectMapper);
        when(httpClient.send(any(HttpRequest.class), any()))
                .thenReturn(buildResponse());
        when(objectMapper.readValue(any(byte[].class), any(Class.class)))
                .thenReturn(buildPokemon());
        ReflectionTestUtils.setField(pokemonApiClient, "path", "http://testpath/");
        ReflectionTestUtils.setField(pokemonApiClient, "pokemon", "poketest/");
    }

    @Test
    public void findPokemonByName_pokemonExist_returnPokemon() {
        var pokemon = pokemonApiClient.findPokemonByName("charmander");

        assertTrue(pokemon.isPresent());
    }

    @Test
    public void findPokemonByName_pokemonNotExist_throwException() throws IOException, InterruptedException {
        when(httpClient.send(any(HttpRequest.class), any()))
                .thenReturn(buildResponse(404));

        var thrown = assertThrows(NotFoundException.class,
                () -> pokemonApiClient.findPokemonByName("agumon"));

        assertTrue(thrown.getMessage().contains("Resource not found"));
    }

    @Test
    public void findPokemonById_numberValid_returnPokemon() {
        var pokemon = pokemonApiClient.findPokemonById(4);

        assertTrue(pokemon.isPresent());
    }

    @Test
    public void findPokemonById_numberInvalid_throwException() throws IOException, InterruptedException {
        when(httpClient.send(any(HttpRequest.class), any()))
                .thenReturn(buildResponse(404));

        var thrown = assertThrows(NotFoundException.class,
                () -> pokemonApiClient.findPokemonById(159478));

        assertTrue(thrown.getMessage().contains("Resource not found"));
    }
}
