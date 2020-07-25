package dev.ericksuarez.pokemon.battle.controller;

import dev.ericksuarez.pokemon.battle.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BattleController {

    private BattleService battleService;

    @Autowired
    public BattleController(BattleService battleService) {
        this.battleService = battleService;
    }

    @GetMapping("/")
    public String foo() {
        battleService.findPokemon("charrrmander");
        return "ok";
    }
}
