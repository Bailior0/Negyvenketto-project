package App;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import Exceptions.*;

/**
 * Teleporkaput megvalósító osztály
 * Egy teleportálásra használható kapu, ami két aszteroidát összeköt, 
 * így egy telepes át tud menni egy messzebbi aszteroidára. 
 * Tudja, hogy a kapu aktív-e, illetve, hogy melyik másik kapu az ő párja, 
 * melyik aszteroidán van és, hogy le van-e téve.
 */
public class Gate extends Place {
    private Gate pair;              ///< A kapu párja
    private boolean isMegkergult;   ///< Megkergültség állapota
    private boolean isPlaced;       ///< Lehejezettség állapota
    private boolean isActive;       ///< Aktivitás állapota
    private Place fonok;            ///< Főnökség
    private static int count = 0;   ///< A kapu azonosításához szükséges szám komponens
    private int ranx, rany;         ///< A kapu pozícióját befolyásoló koordináták

    /**
    * Visszatér az isdeletable értékével.
    * @return isdeletable - törölhető-e a steppableből. 
    */
    @Override
    public boolean isDeletable() 
    {
        return isdeletable;
    }

    /**
     * Kapu megkergülését visszaadó metódus
     * @return - megkergülés
     */
    public boolean isMegkergult() {
        return isMegkergult;
    }

    /**
     * Kapu konstruktora
     * @param af - kapu aszteroidamzője
     */
    public Gate(AsteroidField af) {
        super(af);
        getEllipse().setMajor(10);
        getEllipse().setMinor(10);
        this.isMegkergult = false;
        this.isPlaced = false;
        this.isActive = false;
        this.ID = "G" + (++count);
        isdeletable = false;
        Step();
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
     * Kapupár getterje
     * @return kapu párja
     */
    public Gate getPair() { return this.pair; }
    
    /**
     * Lehelyezettség getterje
     * @return lehelyezettség
     */
    public boolean isPlaced() { return this.isPlaced; }
    
    /**
     * Aktivitás getterje
     * @return aktivitás
     */
    public boolean isActive() { return this.isActive; }

    /**
     * Főnök getterje
     * @return főnök
     */
    public Place getFonok() { return this.fonok; }

    /**
     * Kapupár setterje
     * @param pair - kapu párja
     */
    public void setPair(Gate pair) { this.pair = pair; }

    /**
     * Lehelyezettség setterje
     * @param b - lehelyezettség
     */
    public void setPlaced(boolean b) { this.isPlaced = b; }

    /**
     * Aktivitás setterje
     * @param b - aktivitás
     */
    public void setActive(boolean b) { this.isActive = b; }

    /**
     * A teleportkapu pár megépítéséhez szükséges nyersanyagért felelős metódus
     * Létrehoz egy vasból egy szénből és egy uránból álló listát
     * @return szükséges nyersanyagok listája
     */
    public static BillOfMaterials getCost() {
        ArrayList<Resource> list = new ArrayList<>();
        list.add(new Iron());
        list.add(new Iron());
        list.add(new Ice());
        list.add(new Uran());     
        return new BillOfMaterials(list);          
    }    

    /**
	 * A napkitörésért felelős metódus
	 */
    @Override
    public void atSolarStorm() {
        Iterator<Worker> i = workers.iterator();
		while(i.hasNext()){
			i.next().atSolarStorm();
			i.remove();
		}
        Random ran = new Random();
        period *= ran.nextInt(3);
        this.isMegkergult = true;
    }

    /**
     * Főnök setterje
     * @param place - főnök
     * @throws ActionFailed - sikertelen művelet esetén kivételt dob
     */
    @Override
    public void setFonok(Place place) throws ActionFailed {
        Random ran = new Random();
        this.fonok = place;
        ranx = ran.nextInt(DistanceMetric.Negyvenketto );
        rany = (int)Math.sqrt((Math.pow(DistanceMetric.Negyvenketto , 2) - Math.pow(ranx, 2)));
        if(place!=null)
            setPosition(new Position(place.getPosition().getX() + ranx, place.getPosition().getY() + rany));
    }

    /**
     * A megkergülést irányító step
     */
    public void Step() {
        Random ran = new Random();
        if(!isdeletable){
            if (!isPlaced)
                return;
            if(isMegkergult)
            {
                try{
                    if(this.fonok.getNeighbours().size() > 0)
                        this.fonok.getNeighbours().get(ran.nextInt(this.fonok.getNeighbours().size())).addChild(this);
                } catch(ActionFailed af) {
                    System.out.println("Gate step actionfailed");
                }
            }
            if(this.fonok != null){
                setPosition(new Position(fonok.getPosition().getX() + ranx, fonok.getPosition().getY() + rany));
            }
        }
    }

   	/**
	 * Kapu körpáyájának létrehozása
     * @param pos - körpálya pozíciója
	 */
	public void orbitCircular(Position pos) {
		Position newPos;
        double alfa = 2 * PI / getPeriod() * t++;
		newPos = new Position((int)(getEllipse().getMajorAxis() * Math.cos(alfa)) + pos.getX(),
                              (int)(getEllipse().getMinorAxis() * Math.sin(alfa)) + pos.getY()); 
        setPosition(newPos);
    }


    /**
     * Egy munkás elteleportálásáért felelős metódus
     * A teleportkapu pár getterelése
     * Ha aktív a teleportkapu eltüntetjük róla a munkást beállítjuk a helyét a teleportkapu párra. 
     * Erről a teleportkapu pár is tudomást szerez, és a munkás itt lesz megtalálható
     * @param w - teleportálni kívánó munkás
     * @throws ActionFailed - a művelet végrehajtásának sikertelensége esetén dobódik
     */
    @Override
    public void teleport(Worker w) throws ActionFailed {
        Gate dest = getPair();       
        if(this.isActive) {
            this.remove(w);
            w.setPlace(dest);
			dest.accept(w);
        }else {
            throw new ActionFailed("Get outta here, i am not ready yet boiiiii");
        }
    }

    /**
	 * toString metódus a kiiratáshoz
	 * @return Gate
	 */
    @Override
    public String toString() {
        return "Gate";
    }

    /**
     * A kapu szétesik, minden rajta lévő dolgozó meghal, illetve szétesik a párja is.
     */
    @Override
    public void explode() {
        if (isdeletable) {
            return;
        } 
        Iterator<Worker> i = workers.iterator();
        while(i.hasNext()){
            i.next().atExplosion();
            i.remove();
        }
        workers.clear();
        if (isPlaced)
            af.remove(this);
        isdeletable = true;
        if(pair != null){
            pair.explode();
            pair = null;
        }
    }

    /**
	 * A hely egyéni attributumait adja vissza
     * @return kapupár azonosítója
	 */
    @Override
    public String uniqueAttr() {
        return "Pair:" + pair.getID();
    }
}
