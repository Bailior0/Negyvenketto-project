package App;

import java.util.ArrayList;
import java.util.Iterator;

import Controller.Controller;

import java.io.Serializable;

/**
 * A napot megvalósító osztály
 * Vezérli a napkitöréseket.
 */
public class Sun implements Steppable, Serializable {
    private Position position;              ///< A nap pozíciója
    private AsteroidField asteroidField;    ///< Az aszteroidamező, ami a napot tartalmazza
    private final int frequency;            ///< A napkitörések gyakoriságát tárolja
    private int count;                      ///< Számláló a napkitörésekhez
    private double sunRadius = 500;         ///< A nap sugara
 
    /**
     * A nap konstruktora, ami beállítja az aszteroidamezőt
     * @param asteroidField - a nap aszteroidamezője
     */
    public Sun(AsteroidField asteroidField) {
        this.position = new Position(500, 250);
        this.asteroidField = asteroidField;
        this.frequency = 15;
        this.count = 0;
        TurnHandler.addSteppable(this);
    }

    /**
     * A napvihart vezérlő metódus
     * Lekérdezzük az összes létező helyet a naprendszerünkben
     */
    public void solarStorm() {
        double sunRadiusRan = Math.random() * 150 + 50;
        sunRadius = sunRadiusRan;
        ArrayList<Place> filteredPlaces = new ArrayList<Place>();
        for (Place place : asteroidField.getPlaces()) {
            if((place.getPosition().getX() - 500) * (place.getPosition().getX() - 500) + (place.getPosition().getY() - 250) * (place.getPosition().getY() - 250) <= (sunRadius * sunRadius)) {
                filteredPlaces.add(place);
            }
        }
        Iterator<Place> it = filteredPlaces.iterator();
        while (it.hasNext())
        {
            Place p =it.next();
            p.atSolarStorm();
            Controller.solarStorm(p);
        }
        Controller.solarStorm(null);
    }

    /**
     * Visszaadja a nap pozícióját
     * @return a nap pozíciója
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Folyamatosan csökkenti 1-el a napvihar közeledtét
     */
    public void Step() {
        if (this.count < this.frequency) {
            this.count++;
        }
        if (this.count == frequency) {
            solarStorm();
            this.count = 0;
        }
    }

    /**
     * A nap sugarának setterje
     * @param num - a Nap új sugara
     */
    public void setRadius(int num){
        this.sunRadius = num;
    }

    /**
     * A nap azonosítójának getterje
     * @return S1
     */
    @Override
    public String getID() {
        return "S1";
    }

    /**
     * A nap törölhetőségének getterje
     * @return false
     */
    @Override
    public boolean isDeletable(){ return false; }

    /**
     * A nap pozíciójának getterje
     * @return Nap pozíciója
     */
    public static Position SunPos(){
        return new Position(500, 250);
    }
}
