package App;

/**
 * A léptető interface
 */
public interface Steppable {

    /**
     * Egy léptethető dolog léptetése
     */
    public void Step();

    /**
     * A léptethető dolog ID-jének lekérése
     */
    public String getID();

    /**
     * Visszaadja, hogy törölhető-e az objektum a léptethető dolgok között
     */
    public boolean isDeletable();
}
