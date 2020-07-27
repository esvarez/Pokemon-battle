package dev.ericksuarez.pokemon.battle.error;

/**
 * Exception class used to handle the 404 error
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super("Resource not found: " + message);
    }
}
