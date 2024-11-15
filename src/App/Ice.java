package App;

/**
 * A jég nyersanyagát kezelő osztály.
 * Az osztály felelőssége a vízjég nyersanyag kezelése, 
 * ami kibányászható és ezt segíti, illetve el tud szublimálni ez a nyersanyag napközelben, amit ez az osztály valósít meg.
 */
public class Ice extends Resource implements Steppable {
    private static int count = 0;   ///< A jég azonosításához szükséges szám komponens
    private boolean isdeletable;    ///< A jég törölhetősége

    /**
     * A jég konstruktora, ami beállítja a jég helyét is
     * @param a - az aszteroida, amin a jég található
     */
    public Ice(Asteroid a) {
        super(a);
        ID = "IC" + (++count);
        isdeletable=false;
        TurnHandler.addSteppable(this);
    }

    /**
     * A jég konstruktora
     */
    public Ice() {
        super();
        ID = "IC" + (++count);
        isdeletable=false;
        TurnHandler.addSteppable(this);
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
    *   A jég kitettségéért felelős metódus. 
    *   Napközelséget és kérget ellenőriz
    *   Aszteroida jegét eltünteti, avagy null-ra állítja
    */
    @Override
    public void exposed() {
        if (getAsteroid() == null)
            return;
        if(getAsteroid().isCloseToSun() && getAsteroid().getHardness() == 0) {                           
            getAsteroid().setResource(null);
            isdeletable=true;
        }
    }

    /**
    *   Egy tetszőleges nyersanyag-jég összehasonlításáért felelős metódus
    *   Igazat ad vissza, ha a megkapott nyersanyaggal kompatibilis az osztály nyersanyaga
    *   @param r - a megadott nyersanyag, amivel vizsgáljuk a kompatibilitást
    *   @return igaz, ha kompatibilis a megadott nyersanyag
    */
    @Override
    public boolean isCompatibleWith(Resource r) {
        return r instanceof Ice;
    }

    /**
    *   toString metódus a kiiratáshoz
    *   @return Ice
    */
    @Override
    public String toString(){
        return "ice";
    }

    /**
    *   Step függvény, ami minden lépésnél meghívja a jég kitettségéért felelős metódusát
    */
    @Override
    public void Step(){
        exposed();
    }

    /**
     * Törölhetőség getterje
     * @return törölhetőség
     */
    @Override
    public boolean isDeletable() {
        return isdeletable;
    }

    /**
     * A jég megsemmisítéséért felelős metódus
     */
    @Override
    public void destroy() { isdeletable = true; }
}