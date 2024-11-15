package App;

/**
 * A szén nyersanyagot reprezentáló objektum, ami az aszteroida magjában előfordulhat.
 */
public class Coal extends Resource {
    private static int count = 0;   ///< A szén azonosításához szükséges szám komponens

    /**
     * A szén konstruktora, ami beállítja a szén helyét is
     * @param a - az aszteroida, amin a szén található
     */
    public Coal(Asteroid a) { 
        super(a); 
        ID= "C" + (++count);
    }

    /**
     * A szén konstruktora
     */
    public Coal() {
        super();
        ID = "C" + (++count);
    }

    /**
    * Beállítja a count szamlalo értékét.
    * @param c - id számlálója 
    */
    public static void setcount(int c) {
        count = c;
    }

    /**
    * Visszatér a count értékével.
    * @return count - id számlálója 
    */
    public static int getcount() 
    {
        return count;
    }

    /**
    *   Egy tetszőleges nyersanyag-szén összehasonlításáért felelős metódus
    *   Igazat ad vissza, ha a megkapott nyersanyaggal kompatibilis az osztály nyersanyaga
    *   @param r - a megadott nyersanyag, amivel vizsgáljuk a kompatibilitást
    *   @return igaz, ha kompatibilis a megadott nyersanyag
    */
    @Override
    public boolean isCompatibleWith(Resource r) { return r instanceof Coal; }
    
    /**
    *   toString metódus a kiiratáshoz
    *   @return Coal
    */
    @Override
    public String toString(){
        return "coal";
    }
}
