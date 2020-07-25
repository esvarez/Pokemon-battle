package dev.ericksuarez.pokemon.battle.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DamageRelations {
    private List<Type> doubleDamageTo;
    private List<Type> halfDamageFrom;
}
