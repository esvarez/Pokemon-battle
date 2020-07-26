package dev.ericksuarez.pokemon.battle.controller;

import java.util.Optional;
import java.util.OptionalInt;

import dev.ericksuarez.pokemon.battle.model.AnalysisResponse;
import dev.ericksuarez.pokemon.battle.model.CommonMovesPaged;
import dev.ericksuarez.pokemon.battle.model.ComparePokemonsDto;
import dev.ericksuarez.pokemon.battle.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BattleController {

    private BattleService battleService;

    @Autowired
    public BattleController(BattleService battleService) {
        this.battleService = battleService;
    }

    @GetMapping("/battle/{pokemon}/vs/{pokemon2}")
    public AnalysisResponse battlePokemon(@PathVariable String pokemon, @PathVariable String pokemon2) {
        return battleService.battleAnalysis(pokemon, pokemon2);
    }

    @GetMapping("/compare")
    public CommonMovesPaged compare(@RequestParam("pokemons") String[] pokemons, @RequestParam Optional<String> lang,
                                    @RequestParam Optional<Integer> limit, @RequestParam Optional<Integer> page) {
        return battleService.comparePokemon(pokemons, lang, limit, page);
    }
}
