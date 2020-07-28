package dev.ericksuarez.pokemon.battle.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.ericksuarez.pokemon.battle.model.AnalysisResponse;
import dev.ericksuarez.pokemon.battle.model.CommonMovesPaged;
import dev.ericksuarez.pokemon.battle.service.BattleService;

@RestController
@RequestMapping("/api")
public class BattleController {

    private BattleService battleService;

    @Autowired
    public BattleController(BattleService battleService) {
        this.battleService = battleService;
    }

    /**
     * Return if the first pokemon have advantage over the second to compare the types of the pokemons
     * @param pokemon  pokemon id it could be the name or the number id
     * @param pokemon2 pokemon id of the second pokemon
     * @return return an object with the types of the pokemons, if the first deal double (or normal) damage and if
     * receive half (or normal) damage form the second
     * @see AnalysisResponse
     */
    @GetMapping("/battle/{pokemon}/vs/{pokemon2}")
    public AnalysisResponse battlePokemon(@PathVariable String pokemon, @PathVariable String pokemon2) {
        return battleService.battleAnalysis(pokemon, pokemon2);
    }

    /**
     * Return the common moves among the pokemons only if all of them have the same move
     * @param pokemons Required Array String with the name or id of the pokemons
     * @param lang Optional value for the language of the moves, default value: en
     * @param limit Optional value to limit the number of moves per page, default value 10
     * @param page Optional value to show the number of page, default value 1
     * @return common moves among the pokemons in a paged format
     * Note: If you send only one pokemon, returns all moves
     * @see CommonMovesPaged
     */
    @GetMapping("/compare")
    public CommonMovesPaged compare(@RequestParam("pokemons") String[] pokemons, @RequestParam Optional<String> lang,
                                    @RequestParam Optional<Integer> limit, @RequestParam Optional<Integer> page) {
        return battleService.comparePokemon(pokemons, lang, limit, page);
    }

    @GetMapping("/deploy")
    public String deploy() {
        return "Deployed v5";
    }
}
