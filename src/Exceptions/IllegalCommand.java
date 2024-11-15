package Exceptions;

/**
 * Illegális parancsot kezelő kivétel
 */
public class IllegalCommand extends java.lang.Exception {

    /**
     * Illegális parancsot megvalósulásakor keletkező konstruktor
     * @param m - üzenet a felhasználónak
     */
    public IllegalCommand(String m){
        super(m);
    }
}
