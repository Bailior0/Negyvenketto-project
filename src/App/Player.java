package App;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * A játékost reprezentáló osztály. 
 * Ennek segítségével irányíthatja a játékos a telepeseket és irányíthatja a játék folyását és a játék köreit.
 */
public class Player implements Serializable {
    private ArrayList<Villager> villagers;  ///< A játékoshoz tartozó telepesek listája
    private String ID;                      ///< A játékos azonosítója
    private static int count = 0;           ///< A játékos azonosításához szükséges szám komponens

    /**
    *   Az a metódus, ami visszaadja a játékos azonosítóját
    *   @return a játékos azonosítója
    */
    public String getID() {
        return ID;
    }

    /**
    *   A játékos konstruktora
    */
    public Player() {
        this.villagers = new ArrayList<Villager>();
        ID = "P" + (++count);
    }

    /**
     * Hozzárendelünk egy telepest a játékoshoz
     * @param v - a telepes, amit hozzá akarunk adni a játékoshoz
     */
    public void addVillagers(Villager v) {
        this.villagers.add(v);
    }

    /**
     * Visszaadja a játékoshoz tartozó telepeseket
     * @return a játékoshoz tartozó telepesek listája
     */
    public ArrayList<Villager> getVillagers() {
        return this.villagers;
    }

    /**
     * A játékostól elveszi a telepest
     * @param v - a telepes, amit el akarunk venni a játékostól
     */
    public void removeVillager(Villager v) {
        this.villagers.remove(v);
    }

    /**
     * Megtalálja a telepeseket az azonosítójuk alapján
     * @param id - a kívánt telepes azonosítója
     * @return a megtalált telepes
     */
    public Villager Find(String id)
    {
        for (int i = 0; i < villagers.size(); i++)
        {
            if(villagers.get(i).getID().equals(id))
                return villagers.get(i);
        }
        return null;
    }

    /**
     * A játékos számának getterje
     * @return játékos száma
     */
    public static int getcount() { return count; }

    /**
     * A játékos számának setterje
     * @param number - játékos száma
     */
    public static void setcount(int number) { count = number; }

    /**
     * A játékos a következő fordulóba megy
     */
    public void turn() { }
}
