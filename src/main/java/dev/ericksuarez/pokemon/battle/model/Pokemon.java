package dev.ericksuarez.pokemon.battle.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
// TODO model could be improved: create one for battle with only Types variable and other for compare with Moves
public class Pokemon {
    private String name;

    private List<Moves> moves;

    private List<Types> types;
}
