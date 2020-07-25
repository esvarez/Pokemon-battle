package dev.ericksuarez.pokemon.battle.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DamageDealer {
    public static Map<String, Set<String>> doubleDamageTypes;

    public static Map<String, Set<String>> halfDamageTypes;

    static {
        doubleDamageTypes = new HashMap<>();
        halfDamageTypes = new HashMap<>();
    }
}
