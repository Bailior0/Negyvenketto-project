package App;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.Serializable;
import java.lang.Math;
import java.util.Random;
import Exceptions.*;
import Exceptions.ActionFailed;

/**
 * Egy helyet reprezentáló osztály.
 * Ezen munkások tartózkodhatnak, illetve szomszédos helyekre mozoghatnak. 
 * Lehet napközelben, illetve naptávolban, valamit meg tudja mondani a szomszédait.
 */
public abstract class Place implements Steppable, Serializable {
	protected final double PI = 3.14159265358979;	///< Pi
	protected double period;						///< A hely periódusa
	private Ellipse ellipse;						///< Az ellipszis, amin a hely tartózkodik
	private Position position;						///< A hely pozíciója
	private ArrayList<Place> neighbours;			///< A hellyel szomszédos helyek listája
	protected ArrayList<Worker> workers;			///< A helyen tartózkodó munkások
	protected String ID;							///< A hely azonosítója
	protected AsteroidField af;						///< A helyet tartalmazó aszteroidamező
	protected boolean isdeletable = false;			///< Indikálja, hogy az aszteroida törölhető-e
	protected int t;								///< A hely keringési ideje

	/**
    *   Az a metódus, ami visszaadja a hely azonosítóját
    *   @return a hely azonosítója
    */
    public String getID() { return this.ID;}

	/**
	 * A hely konstruktora
	 * @param af - A hely aszteroidamezője
	 */
	public Place(AsteroidField af) {
		Random rand = new Random();
		t = rand.nextInt(100);
        this.period = rand.nextDouble() * 5 + 1;
		this.neighbours = new ArrayList<Place>();
		this.workers = new ArrayList<Worker>();
		this.ellipse = new Ellipse();
		this.position = new Position(0, 0);
		this.af = af;
		TurnHandler.addSteppable(this);
	}

	/**
	 * A hely konstruktora
	 * @param ellipse - az ellipszis, amin a hely tartózkodik
	 * @param period - keringési idő 1 = 1 kör
	 * @param af - A hely aszteroidamezője
	 */
	public Place(Ellipse ellipse, double period, AsteroidField af) {
		Random rand = new Random();
		t = rand.nextInt(100);
		this.neighbours = new ArrayList<Place>();
		this.workers = new ArrayList<Worker>();
		this.ellipse = ellipse;
		this.period = period;
		this.position = new Position(0, 0);
		this.af = af;
		TurnHandler.addSteppable(this);
	}

	/**
	 * Robbanásért felelős függvény, ami alapértelmezetten semmit nem csinál, de felül tudják írni.
	 */
	public void explode(){}

	/**
	 * A szomszédlista getterje
	 * @return szomszédok listája
	 */
	public ArrayList<Place> getNeighbours() { 
		return this.neighbours;
	}

	/**
	 * Az ellipszis getterje
	 * @return hely ellipszise
	 */
	public Ellipse getEllipse() { 
		return this.ellipse; 
	}

	/**
	 * A pozíció getterje
	 * @return hely pozíciója
	 */
	public Position getPosition() { 
		return this.position; 
	}

	/**
	 * A munkások getterje
	 * @return munkások listája
	 */
	public ArrayList<Worker> getWorkers() { 
		return this.workers; 
	}
	
	/**
	 * A szögsebesség getterje
	 * @return szögsebesség
	 */
	public double getPeriod() {
		return this.period;
	}


	/**
	 * Az ellipszis setterje
	 * @param ellipse - kívánt ellipszis
	 */
	public void setEllipse(Ellipse ellipse) { 
		this.ellipse = ellipse; 
	}

	/**
	 * A pozíció setterje
	 * @param pos - kívánt pozíció
	 */
	public void setPosition(Position pos) {
		this.position = pos;
	}

	/**
	 * Hozzáad egy helyet a szomszédokhoz
	 * @param pl - az új szomszéd
	 */
	public void addNeighbour(Place pl) { 
		neighbours.add(pl); 
	}
	
	/**
	 * Hozzáad egy munkást a helyhez
	 * @param w - az új munkás
	 */
	public void accept(Worker w) { 
		workers.add(w); 
	}
	

	/**
	 * Elvesz egy munkást a helyről
	 * @param w - a munkás, akit eltávolítunk
	 */
	public void remove(Worker w) { 
		workers.remove(w);
	}

	/**
	 * Beállítja a szomszédokat a paraméterként megkapott listára
	 * @param places - a lista, ahová a szomszédságok kerülnek
	 */
	public void setNeighbours(ArrayList<Place> places) {
		this.neighbours = places;
	}

	/**
	 * Nyersanyag lehelyezéséért felelős metódus
	 * @param r - a lehelyezendő nyersanyag
	 * @throws ActionFailed - sikertelen esemény esetén kivételt dob
	 */
	public void placeResource(Resource r) throws ActionFailed 
	{
		throw new ActionFailed("You are not on an Asteroid. This is a " + toString() + " with " +ID + " ID.");
	}

	/**
	 * A hely megfúrásáért felelős metódus
	 * @throws ActionFailed - sikertelen fúrás esetén kivételt dob
	 */
	public void beingDrilled() throws ActionFailed { throw new ActionFailed("Cannot drill common place"); }
	
	/**
	 * Nyersanyag bányászásáért felelős metódus
	 * @return null
	 */
	public Resource beingMined() { return null; }

	/**
	 * A helyen való elbújásért felelős metódus
	 * @param w - a munkás, aki el kíván bújni
	 * @throws ActionFailed - sikertelen esemény esetén kivételt dob
	 */
	public void hide(Worker w) throws ActionFailed{
		throw new ActionFailed("I am a freakin gate u serious?");
	}

	/**
	 * Megvizsgálja, hogy megvannak-e a győzelemhez szükséges nyersanyagok
	 * @throws Victory - ha a játékos megnyeri a játékot
	 */
	public void beingChecked(List<Player> players) throws Victory { }

	/**
	 * Egy munkás elteleportálásáért felelős metódus
	 * @param w - a munkás, aki el akar teleportálni
	 * @throws ActionFailed - sikertelen esemény esetén kivételt dob
	 */
	public void teleport(Worker w) throws ActionFailed {
		throw new ActionFailed("Bruh u r serious?? i mma freakin asteroid...");
	}

	/**
	 * A napkitörésért felelős metódus
	 */
	public void atSolarStorm() {
		Iterator<Worker> i = workers.iterator();
		while(i.hasNext()){
			if(i.next().atSolarStorm());
				i.remove();
		}
	}


	/**
     * Megtalálja a szomszédokat az azonosítójuk alapján
     * @param id - a kívánt hely azonosítója
     * @return a megtalált hely
     */
	public Place FindNeighbour(String id)
    {
        for (int i = 0; i < neighbours.size(); i++)
        {
            if(neighbours.get(i).getID().equals(id))
				return neighbours.get(i);
        }
        return null;
    }

	/**
	 * A helyszínek a nekik megadott logika szerint cselekszenek
	 */
	public void Step() {
		if(!isdeletable){
			double alfa = 2 * this.PI / this.period * t++;
			double theta = this.ellipse.getRotatedAngle() * PI / 180;
			double Rx = this.ellipse.getMajorAxis();
			double Ry = this.ellipse.getMinorAxis();
			position.setX((int)(Rx * Math.cos(alfa) * Math.cos(theta) - Ry * Math.sin(alfa) * Math.sin(theta)) + this.ellipse.getPosition().getX());
			position.setY((int)(Rx * Math.cos(alfa) * Math.sin(theta) + Ry * Math.sin(alfa) * Math.cos(theta)) + this.ellipse.getPosition().getY());
		}
	}

	/**
	 * Visszaadja, hogy a hely tartalmaz-e nyersanyagot
	 * @param id - a hely azonosítója
	 * @return false
	 */
	public boolean containsRes(String id){
		return false;
	}

	/**
	 * A hely egyéni attributumait adja vissza
	 */
	public abstract String uniqueAttr();


	/**
    * Az hely és a nap távolságát visszaadó metódus
    * @return az hely és a nap távolsága
    */
    public double distanceFromSun() {
        return Math.sqrt(Math.pow(Sun.SunPos().getX() - getPosition().getX(), 2)+ Math.pow(Sun.SunPos().getY() - getPosition().getY(), 2));
    }


	/**
    * Az hely naptávolságát ellenőrző metódus
    * @return igaz, ha napközelben van
    */    
	public boolean isCloseToSun() { return distanceFromSun() < 3 * DistanceMetric.Negyvenketto; }

	/**
	 * Visszaadja, hogy mekkora a tavolsag ket hely kozott.
	 * @param pl - a hely referenciaja
	 * @return a tavolsag a ket hely kozott.
	 */
	public double distancefromPlace(Place pl) {
		return Math.sqrt(Math
                .pow(pl.getPosition().getX() - getPosition().getX(), 2)
                + Math.pow(pl.getPosition().getY() - getPosition().getY(), 2));
	}

	/**
	 * Beállít egy hirarchikus kapcsolatot két place között,
	 * ez alapesetben nem engedélyezett.
	 * @param child A hierarchiailag alacsonyabb szintű place bojektum
	 * @throws ActionFailed - sikertelen művelet esetén kivételt dob
	 */
	public void addChild(Place child) throws ActionFailed {
		throw new ActionFailed("Common places cannot have children");
	}

	/**
	 * Töröl egy hirarchikus kapcsolatot két place között,
	 * ez alapesetben nem engedélyezett.
	 * @param child A hierarchiailag alacsonyabb szintű place bojektum
	 * @throws ActionFailed - sikertelen művelet esetén kivételt dob
	 */
	public void removeChild(Place child) throws ActionFailed {
		throw new ActionFailed("Common places cannot have children");
	}

	/**
	 * Beállít egy hirarchikus kapcsolatot két place között,
	 * ez alapesetben nem engedélyezett.
	 * @param boss A hierarchiailag magasabb szintű place objektum
	 * @throws ActionFailed - sikertelen művelet esetén kivételt dob
	 */
	public void setFonok(Place boss) throws ActionFailed {
		throw new ActionFailed("Common places cannot be bossed");
	}

	/**
	 * Törölhetőség getterje
	 * @return törölhetőség
	 */
	public boolean isDeletable(){ return isdeletable; }
}
 