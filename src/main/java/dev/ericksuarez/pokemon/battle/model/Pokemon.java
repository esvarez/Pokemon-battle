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
public class Pokemon {
    private String name;

    private List<Moves> moves;

    private List<Types> types;
}
