package Exceptions;

import java.lang.Exception;

/**
 * Megtelt inventoryt kezelő kivétel
 */
public class InventoryFull extends Exception{
    
    /**
     * Megtelt inventory megvalósulásakor keletkező konstruktor
     * @param message - üzenet a felhasználónak
     */
    public InventoryFull(String message) {
        super(message);
    }
}
