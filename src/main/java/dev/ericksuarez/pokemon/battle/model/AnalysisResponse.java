package dev.ericksuarez.pokemon.battle.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AnalysisResponse {
    private String battle;
    private String battleTypes;
    private String doubleDamage;
    private String halfDamage;
}
