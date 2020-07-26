package dev.ericksuarez.pokemon.battle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AnalysisResponse {
    private String battle;
    private String doubleDamage;
    private String halfDamage;
}
