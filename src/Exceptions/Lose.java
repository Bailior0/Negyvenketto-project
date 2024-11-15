package Exceptions;

import java.lang.Exception;

/**
 * Veszteséget kezelő kivétel
 */
public class Lose extends Exception {

    /**
     * Veszítés megvalósulásakor keletkező konstruktor
     * @param playerId - vesztes játékos azonosítója
     */
    public Lose(String playerId) {
        super(playerId);
    }
}
