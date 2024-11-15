package App;

import java.io.Serializable;

/**
 * Ez egy abstract osztály, ami általánosan a nyersanyagokat reprezentálja.
 */
public abstract class Resource implements Serializable {
    private Asteroid asteroid;  ///< Az az aszteroida, aminek a magjában a nyersanyag található
    protected String ID;        ///< A nyersanyag azonosítója

    /**
    *   Az a metódus, ami visszaadja a nyersanyag azonosítóját
    *   @return a nyersanyag azonosítóját
    */
    public String getID()
    {
        return ID;
    }

    /**
     * Ez a konstruktor a paraméterben megadott aszteroidára helyezi a nyersanyagot
     * @param a - megadott aszteroida, amire a nyersanyag kerül
     */
    public Resource(Asteroid a){
        asteroid = a;
    }

    /**
     * Ez a konstruktor egy nem aszteriodában lévő nyersanyagot hoz létre
     */
    public Resource() {}

    /**
     * A nyersanyagot egy aszteroidába állító metódus
     * Ezt meg kell hívni, ha behelyezték a nyersanyagot egy aszteroidába, vagy kibányászták onnan
     * @param asteroid - megadott aszteroida, amire a nyersanyag kerül
     */
    public void setAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
    }

    /**
     * Lekérdezhető, hogy a nyersanyag melyik aszteroidán van
     * @return nyersanyag aszteroidája
     */
    public Asteroid getAsteroid() {
        return asteroid;
    }

    /**
     * Meghívódik, amikor a felszínre kerül kerül a nyersanyag
     */
    public void exposed() {}
    
    /**
     * Nyersanyagokat összehasonlító metódus
     * Igazzal tér vissza, ha azonos típus
     * @param r - megadott nyersanyag, amit össze akarunk hasonlítani
     * @return igaz, ha a típusok egyeznek
     */
    public abstract boolean isCompatibleWith(Resource r);

    /**
     * A nyersanyag megsemmisítéséért felelős metódus
     */
    public void destroy() {}
}
