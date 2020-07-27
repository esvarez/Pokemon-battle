package dev.ericksuarez.pokemon.battle.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class BattleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void battlePokemon_grassDealDoubleDamageToWater_returnAnalysis() throws Exception {
        mockMvc.perform(get("/api/battle/{idPokemon}/vs/{idPokemon2}", 1, 8))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.battle", is("bulbasaur vs wartortle")))
                .andExpect(jsonPath("$.double_damage", is("bulbasaur deal double damage to wartortle")));
    }

    @Test
    public void battlePokemon_fireReceiveHalfDamageFromIce_returnAnalysis() throws Exception {
        mockMvc.perform(get("/api/battle/{idPokemon}/vs/{idPokemon2}", 6, "jynx"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.battle", is("charizard vs jynx")))
                .andExpect(jsonPath("$.half_damage", is("charizard receive half damage from jynx")));
    }

    @Test
    public void compare_leaveDefaultValues_returnMovesPaged() throws Exception {
        mockMvc.perform(get("/api/compare?pokemons={idPokemon}", "1,2,3"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lang", is("en")))
                .andExpect(jsonPath("$.page", is(1)))
                .andExpect(jsonPath("$.moves", is(10)));
    }

    @Test
    public void compare_translateMoves_returnMovesPaged() throws Exception {
        mockMvc.perform(get("/api/compare?pokemons={idPokemon}&lang={lang}&page={page}&limit={limit}"
                , "1,2,3", "es", 2, 5))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lang", is("es")))
                .andExpect(jsonPath("$.page", is(2)))
                .andExpect(jsonPath("$.moves", is(5)));
    }
}
