package App;

/**
*   Az urán nyersanyagot reprezentáló objektum, ami az aszteroida magjában előfordulhat. 
*   Ha egy napközeli aszteroida magjában található és a stabilitása eléri a nullás szintet, 
*   az aszteroida felrobban, a rajta tartózkozók pedig vagy megsemmisülnek, vagy elrepülnek.
*/
public class Uran extends Resource implements Steppable{
    private int stableness = 3;             ///< Az urán stabilitási szintje
    private String ID;                      ///< Az urán azonosítója
    private static int count = 0;           ///< Az urán azonosításához szükséges szám komponens
    private boolean isDeletable = false;    ///< Indikálja, hogy törölhető-e az objektum.

    /**
     * Törölhetőség getterje
     * @return törölhetőség
     */
    @Override
    public boolean isDeletable(){ return isDeletable; }

    /**
     * Az urán megsemmisítéséért felelős metódus
     */
    @Override
    public void destroy() { isDeletable = true; }

    /**
    *   Az a metódus, ami visszaadja az urán azonosítóját
    *   @return az urán azonosítóját
    */
    public String getID() {
        return ID;
    }

    /**
     * Beállítja az urán stabilitását
     * @param s - a kívánt stabilitás
     */
    public void setStableness(int s) { stableness = s; }

    /**
    *   Az urán konstruktora, ami beállítja az urán helyét is
    *   @param a - az aszteroida, amin az urán található
    */
    public Uran(Asteroid a) { 
        super(a); 
        ID = "U" + (++count); 
        TurnHandler.addSteppable(this);
    }            

    /**
    *   Az urán konstruktora
    */
    public Uran() {
        super();
        ID = "U" + (++count);
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
    *   Az urán kitettségéért felelős metódus. 
    *   Ha harmadik alkalommal van napközeli aszteroida magjában, az urán és az aszteroida felrobban.
    */
    @Override
    public void exposed() {
        if (getAsteroid() == null)
            return;
        if(getAsteroid().isCloseToSun() && getAsteroid().getHardness() == 0) {                            
            stableness--;
            if(stableness == 0)
            {
                getAsteroid().explode();
                isDeletable = true;
            }
        }
    }

    /**
    *   Egy tetszőleges nyersanyag-urán összehasonlításáért felelős metódus
    *   Igazat ad vissza, ha a megkapott nyersanyaggal kompatibilis az osztály nyersanyaga
    *   @param r - a megadott nyersanyag, amivel vizsgáljuk a kompatibilitást
    *   @return igaz, ha kompatibilis a megadott nyersanyag
    */
    @Override
    public boolean isCompatibleWith(Resource r) {
        return r instanceof Uran;           
    }

    /**
    *   toString metódus a kiiratáshoz
    *   @return Uran
    */
    @Override
    public String toString(){
        return "uran";
    }

    /**
    *   Step függvény, ami minden lépésnél meghívja az urán kitettségéért felelős metódusát
    */
    @Override
    public void Step(){
        if(!isDeletable)
            exposed();
    }
}
