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

import static dev.ericksuarez.pokemon.battle.util.UtilTest.buildResponse;
import static dev.ericksuarez.pokemon.battle.util.UtilTest.buildPokemon;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    public void findPokemonByIdentifier_pokemonExist_returnPokemon() {
        var pokemon = pokemonApiClient.findPokemonByIdentifier("charmander");

        assertNotNull(pokemon);
    }

    @Test
    public void findPokemonByIdentifier_pokemonNotExist_throwException() throws IOException, InterruptedException {
        when(httpClient.send(any(HttpRequest.class), any()))
                .thenReturn(buildResponse(404));

        var thrown = assertThrows(NotFoundException.class,
                () -> pokemonApiClient.findPokemonByIdentifier("agumon"));

        assertTrue(thrown.getMessage().contains("Resource not found"));
    }

    @Test
    public void findPokemonByIdentifier_numberValid_returnPokemon() {
        var pokemon = pokemonApiClient.findPokemonByIdentifier("4");

        assertNotNull(pokemon);
    }

    @Test
    public void findPokemonByIdentifier_numberInvalid_throwException() throws IOException, InterruptedException {
        when(httpClient.send(any(HttpRequest.class), any()))
                .thenReturn(buildResponse(404));

        var thrown = assertThrows(NotFoundException.class,
                () -> pokemonApiClient.findPokemonByIdentifier("159478"));

        assertTrue(thrown.getMessage().contains("Resource not found"));
    }
}
