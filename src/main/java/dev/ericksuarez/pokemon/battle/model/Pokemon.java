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
// TODO improve: create one model for battle with only 'types' variable and other for compare only with 'moves'
public class Pokemon {
    private String name;
    private String id;
    private List<Moves> moves;
    private List<Types> types;
}
