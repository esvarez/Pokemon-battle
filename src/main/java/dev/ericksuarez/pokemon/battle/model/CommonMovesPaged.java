package dev.ericksuarez.pokemon.battle.model;

import java.util.List;

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
public class CommonMovesPaged {
    private String pokemonsCompared;
    private String lang;
    private List<String> moveList;
    private String previous;
    private String next;
    private int page;
    private int pages;
    private int moves;
    private int totalMoves;
}
