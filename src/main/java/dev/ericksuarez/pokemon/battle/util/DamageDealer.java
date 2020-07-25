package dev.ericksuarez.pokemon.battle.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DamageDealer {
    public static Map<String, Set<String>> strongTypes;

    public static Map<String, Set<String>> weakTypes;

    static {
        strongTypes = new HashMap<>();
        weakTypes = new HashMap<>();
    }
}
