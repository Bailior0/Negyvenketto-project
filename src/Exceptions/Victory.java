package Exceptions;

import java.lang.Exception;

/**
 * Győzelmet kezelő kivétel
 */
public class Victory extends Exception {

    /**
     * Győzel megvalósulásakor keletkező konstruktor
     * @param playerId - győztes játékos azonosítója
     */
    public Victory(String playerId) {
        super(playerId);
    }
}
