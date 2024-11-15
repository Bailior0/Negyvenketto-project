package Exceptions;

/**
 * Rossz szintaxot kezelő kivétel
 */
public class WrongSyntax extends java.lang.Exception{
    private String systaxMsg;   ///< Az elvárt szintaxis

    /**
     * A rossz szintaxis kivételkezelését megvalósító konstruktor
     * @param bad - a rosszul megadott szintaxis
     * @param syntaxMSG - az elvárt szintaxis
     */
    public WrongSyntax(String bad, String syntaxMSG){
        super(bad);
        this.systaxMsg = syntaxMSG;
    }

    /**
     * A rossz szintaxis kivételkezelését megvalósító konstruktor
     * @param msg - üzenet a felhasználónak a hibáról
     */
    public WrongSyntax(String msg){
        super("");
        systaxMsg = msg;
    }

    /**
     * Az elvárt szintaxist visszaadó metódus
     * @return az elvárt szintaxis
     */
    public String getSytaxMSG() { return systaxMsg; }
}
