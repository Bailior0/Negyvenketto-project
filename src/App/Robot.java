package App;

import java.util.ArrayList;
import java.util.Random;
import Exceptions.*;

/**
 * A robot irányításáért, illetve kezeléséért felelős osztály.
 */
public class Robot extends Worker implements Steppable{
    private Random rand = new Random(); ///< Egy véletlenszám generálásához szükséges objektum
    private static int count = 0;       ///< A robot azonosításához szükséges szám komponens
    private boolean isdeletable;        ///< A robot törölhetősége

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
    * Visszatér a count értékével.
    * @return count - id számlálója 
    */
    public static int getcount() 
    {
        return count;
    }
    /**
    * Beállítja a count szamlalo értékét.
    * @param c - id számlálója 
    */
    public static void setcount(int c) {
        count = c;
    }

    /**
    * A robot konstruktora
    * @param af - A naprendszer 
    */
    public Robot(AsteroidField af) {
        super(af);
        TurnHandler.addSteppable(this);
        isdeletable=false;
        ID = "R" + (++count);
    }
    
    /**
     * Ez a metódus akkor hívódik meg, ha felrobban egy aszteroidának a magja. 
     * Ekkor egy szomszédos aszteroidára kerül a robot
     * Lekérjük a helyünk szomszédjait
     * A robot lekerül a helyről ahol eddig volt és másik aszteroidára repül
     * Erről a robotot is értesítjük
     */
    @Override
    public void atExplosion() {
        ArrayList<Place> neighbours = this.getPlace().getNeighbours();
        if(neighbours != null && neighbours.size() != 0) {
            neighbours.get(0).accept(this);
            this.setPlace(neighbours.get(0));
        }else {
            isdeletable = true;
        }
    }

    /**
     * Visszaadja az építés költségét, azaz a szükséges nyersanyagokat
     * Létrehoz egy vasból egy szénből és egy uránból álló listát
     * A listából létrehotta a cost-ot
     * @return az építéshez szükséges nyersanyagok listája
     */
    public static BillOfMaterials getCost() {
        ArrayList<Resource> list = new ArrayList<>();
        list.add(new Iron());
        list.add(new Coal());
        list.add(new Uran());
        return new BillOfMaterials(list);
    }

    /**
    *   toString metódus a kiiratáshoz
    *   @return Robot
    */
    @Override
    public String toString(){
        return "robot";
    }

    /**
     * A Step függvény hívja, ha fúrni akar.
     * Lekezeli, hogy sikeres-e a fúrás, és ha nem, akkor random lép egyet.
     * @throws ActionFailed - a művelet végrehajtásának sikertelensége esetén dobódik
     */
    private void drillMove() throws ActionFailed{
        drill();
    }

    /**
    *   Step függvény, ami irányítja a robot cselekvéseit
    *   Ha a robot egy nem megfúrt aszteroidán van, akkor mindenképpen addig fúrja, amíg lehet, 
    *   máskülönben elmozog vagy elteleportál
    */
    public void Step() {
        if(!isdeletable)
        {
            try{
                drillMove();
            }
            catch(ActionFailed af)
            {
                try{
                    if(rand.nextInt(2)==0)
                    { 
                        teleport();
                    }
                    else
                    {
                        if(place.getNeighbours().size() > 0)
                            move(place.getNeighbours().get(rand.nextInt(place.getNeighbours().size())));
                    }
                }catch(ActionFailed afa)
                {
                    try{
                        if(place.getNeighbours().size() > 0)
                            move(place.getNeighbours().get(rand.nextInt(place.getNeighbours().size())));
                    }catch(ActionFailed afak)
                    {
                        System.out.println(afak.getMessage());
                    }
                }
            }
            canAct = true;
        }
    }

     /**
     *   A robot megsemmisüléséért felelős metódus
     */
    @Override
    public void destroy() 
    {   
        isdeletable = true;
    }
}
