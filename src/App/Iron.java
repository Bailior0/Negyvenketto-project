package App;

/**
 * A vas nyersanyagát kezelő osztály. 
 * “Oda tudja adni” magát egy Inventory osztálynak, amikor kiveszi egy telepes az aszteroidából.
 */
public class Iron extends Resource {
    private static int count = 0;   ///< A vas azonosításához szükséges szám komponens

    /**
     * A vas konstruktora, ami beállítja a vas helyét is
     * @param a - az aszteroida, amin a vas található
     */
    public Iron(Asteroid a) {
        super(a);
        ID = "I" + (++count);     
    }

    /**
     * A vas konstruktora
     */
    public Iron() { 
        super(); 
        ID = "I" + (++count);
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
    *   Egy tetszőleges nyersanyag-vas összehasonlításáért felelős metódus
    *   Igazat ad vissza, ha a megkapott nyersanyaggal kompatibilis az osztály nyersanyaga
    *   @param r - a megadott nyersanyag, amivel vizsgáljuk a kompatibilitást
    *   @return igaz, ha kompatibilis a megadott nyersanyag
    */
    @Override
    public boolean isCompatibleWith(Resource r) {
        return r instanceof Iron;
    }

    /**
    *   toString metódus a kiiratáshoz
    *   @return Iron
    */
    @Override
    public String toString(){
        return "iron";
    }
}
