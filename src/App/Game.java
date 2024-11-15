package App;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Exceptions.Victory;

/**
 * A játék állapotáéert és menetéséért felelős osztály
 */
public class Game implements Serializable{
    private AsteroidField asteroidField;                        ///< Az aszteroidamező, amiben a játék folyik
    private List<Place> places;                                 ///< A helyek, amiken a játék folyik
    private TurnHandler turnHandler = new TurnHandler(this);    ///< A játék körvezérlője
    private List<Player> players = new ArrayList<>();           ///< A játékban rész vevő játékosok
    private int turn;                                           ///< Körök száma
    private SeriSave save = new SeriSave();                     ///< Objektumok statikus változói elmentve

    /**
     * Játék konstruktora
     */
    public Game(){
		this.places = new ArrayList<>();
        this.turn = 1;
        
        TurnHandler.getsteppables().clear();
        this.asteroidField = new AsteroidField();
        Robot.setcount(0);
        Ufo.setcount(0);
        Coal.setcount(0);
        Iron.setcount(0);
        Uran.setcount(0);
        Ice.setcount(0);
        Asteroid.setcount(0);
        Gate.setcount(0);
        Player.setcount(0);
        Villager.setcount(0);
    }

    /**
     * Körvezérlő getterje
     * @return körvezérlő
     */
    public TurnHandler getTurnHandler() { return this.turnHandler; }

    /**
     * Aszteroidamező getterje
     * @return aszteroidamező
     */
    public AsteroidField getAsteroidField() { return this.asteroidField; }
    
    /**
     * Játékosok getterje
     * @return játékoslista
     */
    public List<Player> getPlayers() { return this.players; }
    
    /**
     * Kör számának getterje
     * @return jelenlegi kör száma
     */
    public int getTurn() { return this.turn; }

    /**
     * Játékosok setterje
     * @param number - kívánt játékosszám
     */
    public void setPlayers(int number){
        for(int i = 0; i < number; i++){
            players.add(new Player());
        }
    }

    /**
     * Győzelmi költséget visszaadó statikus függvény
     * Győzelmi költség inicializálása, majd visszaadása
     * @return győzelmi lista
     */
    public static BillOfMaterials winningCost() {
        List<Resource> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) { // legyen 2 mert az volt megadva, bár ennek semmi értelme
            list.add(new Coal());
            list.add(new Ice());
            list.add(new Iron());
            list.add(new Uran());
        }
        return new BillOfMaterials(list);
    }
 
    /**
     * A játékos győzelméért felelős metódus
     * @throws Victory - ha a játékos megnyeri a játékot
     */
    public void checkCondition() throws Victory {
        places = asteroidField.getPlaces();
        for (Place place : places) {
            place.beingChecked(this.players);
        }
    }

    /**
     * Játékos azonosító alapú keresése
     * @param id - a keresett játékos azonosítója
     * @return keresett játékos
     */
    public Player Find(String id)
    {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getID().equals(id))
                return players.get(i);
        }
        return null;
    }

    /**
     * Játék mentése
     */
    public void saveGame(){
        save.steppables = TurnHandler.getsteppables();
        save.robotCount = Robot.getcount();
        save.ufoCount = Ufo.getcount();
        save.coalCount = Coal.getcount();
        save.ironCount = Iron.getcount();
        save.uranCount = Uran.getcount();
        save.iceCount = Ice.getcount();
        save.asteroidCount = Asteroid.getcount();
        save.gateCount = Gate.getcount();
        save.playerCount = Player.getcount();
        save.villagerCount = Villager.getcount();
    }

    /**
     * Játék betöltése
     */
    public void loadGame(){
        TurnHandler.setSteppables(save.steppables);
        Robot.setcount(save.robotCount);
        Ufo.setcount(save.ufoCount);
        Coal.setcount(save.coalCount);
        Iron.setcount(save.ironCount);
        Uran.setcount(save.uranCount);
        Ice.setcount(save.iceCount);
        Asteroid.setcount(save.asteroidCount);
        Gate.setcount(save.gateCount);
        Villager.setcount(save.villagerCount);
        Player.setcount(save.playerCount);
    }

    /**
     * Következő körbe lépés
     */
    public void nextTurn() {
        this.turn++;
    }

    /**
     * A jelenleg játszó játékos getterje
     * @return jelenlegi játékos
     */
    public Player getCurrentPlayer(){
        return turnHandler.getCurrentPlayer();
    }
}
