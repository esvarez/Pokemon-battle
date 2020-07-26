package dev.ericksuarez.pokemon.battle.controller;

import dev.ericksuarez.pokemon.battle.model.AnalysisResponse;
import dev.ericksuarez.pokemon.battle.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class BattleController {

    private BattleService battleService;

    @Autowired
    public BattleController(BattleService battleService) {
        this.battleService = battleService;
    }

    @GetMapping("battle/{pokemon}/vs/{pokemon2}")
    public AnalysisResponse battlePokemon(@PathVariable String pokemon, @PathVariable String pokemon2) {
        return battleService.battleAnalysis(pokemon, pokemon2);
    }
}
