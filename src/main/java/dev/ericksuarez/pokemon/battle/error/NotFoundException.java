package dev.ericksuarez.pokemon.battle.error;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super("Resource not found: " + message);
    }
}
