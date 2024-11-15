package Exceptions;

import java.lang.Exception;

/**
 * Sikertelen műveletet kezelő kivétel
 */
public class ActionFailed extends Exception{

    /**
     * Sikertelen művelet megvalósulásakor keletkező konstruktor
     * @param str - üzenet a felhasználónak
     */
    public ActionFailed(String str){
        super(str);
    }
}
