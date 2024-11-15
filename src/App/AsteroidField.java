package App;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Aszteroidamezőt megvalósító osztály
 * Tartalmazza a Naprendszerben lévő aszteroidákat, napot és kapukat. 
 * Az aszteroidák inicializálásáért és élettartamáért felelős osztály.
 */
public class AsteroidField implements Serializable {        
    private Sun sun;                  ///> Napot tartalmazó objektum
	private ArrayList<Place> places;  ///> Helyeket tartalmazó lista

    /**
     * Aszteroidamező konstruktora
     */
	public AsteroidField()
	{
        sun = new Sun(this);
		places = new ArrayList<>();
	}

    /**
     * Nap getterje
     * @return nap
     */
	public Sun getSun() { return sun; }

    /**
     * Helyek getterje
     * @return helyek listája
     */
    public ArrayList<Place> getPlaces() { return places; }
    
    /**
     * A naprendszer hely bővítéséért felelős metódus
     * @param place - a hely, amivel bővíteni szeretnénk az aszteroidamezőt
     */
    public void add(Place place) { places.add(place); }

    /**
     * A naprendszer hely szűkítéséért felelős metódus
     * @param place - a hely, amivel csökkenteni szeretnénk az aszteroidamezőt
     */
    public void remove(Place place) { places.remove(place); }

    /**
     * Megkeresi az adott azonosítójú helyet
     * @param id - a megkeresendő hely azonosítója
     * @return keresett hely
     */
    public Place Find(String id) {
        for (Place p : places) {
            if (p.getID().equals(id))
                return p;
        }
        return null;
    }

    /**
     * Megkeresi az adott azonosítójú munkást
     * @param id - a megkeresendő munkás azonosítója
     * @return keresett munkás
     */
    public Worker find_worker(String id){
        for (Place p : places) {
            for (Worker w : p.getWorkers()) {
                if (w.getID().equals(id)) {
                    return w;
                }
            }
        }
        return null;
    }

    /**
     * Megkeresi az adott azonosítójú nyersanyagot
     * @param id - a megkeresendő nyersanyag azonosítója
     * @return keresett nyersanyag
     */
    public Resource find_resource(String id) {
        for (Place p : places) {
            if (p.containsRes(id))
                return ((Asteroid) p).getResource();
            for (Worker w : p.getWorkers()) {
                for (Resource r : w.getInventory().getResourcesList()) {
                    if (r.getID().equals(id))
                        return r;
                }
            }
        }
        return null;
    }

    /**
     * Beállítja a helyek szomszédsági tulajdonságait
     */
    public void setPlaceNeighbours()
    {
        for (int i = 0; i < places.size(); i++) {
            ArrayList<Place> newNeighbours = new ArrayList<Place>();
            for (int j = 0; j < places.size(); j++) {
                if (i != j && places.get(i).distancefromPlace(places.get(j)) < DistanceMetric.Negyvenketto * 4)
                    newNeighbours.add(places.get(j));
            }
            places.get(i).setNeighbours(newNeighbours);
        }
    }

    /**
     * Beállítja a szomszédsági tulajdonságait egy megadott helynek
     * @param place - a hely, aminek változtatjuk a szomszédsági tulajdonságait
     */
    public void setPlaceNeighboursFor(Place place)
    {
        for (int i = 0; i < places.size(); i++)
        {
            if (places.get(i).getID().equals(place.getID())) 
            {
                ArrayList<Place> newNeighbours= new ArrayList<Place>();
                for(int j=0;j<places.size();j++)
                {
                    if (i != j && places.get(i).distancefromPlace(places.get(j)) < DistanceMetric.Negyvenketto * 4)
                    newNeighbours.add(places.get(j));
                }
                places.get(i).setNeighbours(newNeighbours);
            }
        }
    }
}
