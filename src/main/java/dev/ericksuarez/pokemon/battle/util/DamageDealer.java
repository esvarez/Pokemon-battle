package dev.ericksuarez.pokemon.battle.util;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
public class DamageDealer {
    private Map<String, Set<String>> doubleDamageTypes;
    private Map<String, Set<String>> halfDamageTypes;

    public DamageDealer() {
        doubleDamageTypes = new HashMap<>();
        halfDamageTypes = new HashMap<>();
    }

    public Set<String> addToDoubleDamageTypes(String name, Set<String> types) {
        return doubleDamageTypes.put(name, types);
    }

    public Set<String> addToHalfDamageTypes(String name, Set<String> types) {
        return halfDamageTypes.put(name, types);
    }
}
