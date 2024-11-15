package App;

import java.util.Random;
import Exceptions.ActionFailed;

/** 
 * Az ufókat reprezentáló osztály. 
 * Az ufók képesek mozogni, teleportálni és nyersanyagot lopni, de fúrni nem tudnak. 
 * Napvihar és robbanás hatására elpusztulnak, de el tudnak bújni üreges aszteroidánban.
 */
public class Ufo extends Worker implements Steppable {
    private Inventory inventory;        ///< Az ufók tárolója, ami végtelen mennyiségű nyersanyagot képes eltárolni
    private static int count = 0;       ///< Az ufo azonosításához szükséges szám komponens
    private boolean isdeletable;        ///< Az Ufo törölhetősége

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
    *   Az ufo konstruktora, ami beállítja az ufo helyét is
    *   @param place - a hely, amin az ufo alapból található
    *   @param af - az aszteroidamező, amin az ufo alapból található
    */
    public Ufo(Place place, AsteroidField af)
    {
        super(af);
        this.isHidden = false;
        this.place=place;
        inventory = new Inventory();
        TurnHandler.addSteppable(this);
        ID = "UF" + (++count);
        isdeletable=false;
    }

    /**
    *   Kiszedi a nyersanyagot a megfúrt aszteroidából, és beleteszi az ufo tárolójába.
    *   Kibanyassza az aktualis aszteroidabol a nyersanyagot, de elotte ellenorzi, hogy van-e az aszteroidaban.
    */
    public Resource stealResource() {
        if(canAct)
        {
            Resource res = place.beingMined();
            if(res!=null) 
            { 
                inventory.addResource(res);
            }
            return res;
        }
        else
        {
            System.out.println("Ufo can't steal.");
            return null;
        }
    }

    /**
    *   toString metódus a kiiratáshoz
    *   @return Ufo
    */
    @Override
    public String toString(){
        return "ufo";
    }
    
    /**
    *   Az ufo nem képes fúrásra, így ha ez a metódus meghívodik nem történik semmi.
    */
    public void drill() {}

    /**
    *   Step függvény, ami irányítja az ufo cselekvéseit
    *   Ha az Ufo egy megfúrt, nyersanyagot tartalmazó aszteroidán van, akkor mindenképpen ellopja azt, 
    *   máskülönben elmozog vagy elteleportál
    */
    public void Step() {
        Random rand = new Random();
        try{
            if(!isdeletable)
            {
                if (stealResource()==null)
                {
                    if(rand.nextInt(2)==0)
                    { 
                        teleport();
                    }
                    else
                    {
                        if(place.getNeighbours().size() > 0)
                            move(place.getNeighbours().get(rand.nextInt(place.getNeighbours().size())));
                    }
                }   
                canAct = true;
            }
        }catch(ActionFailed af) {
            try{
                if(place.getNeighbours().size() > 0)
                    move(place.getNeighbours().get(rand.nextInt(place.getNeighbours().size())));
                canAct = true;
            }
            catch(ActionFailed afa)
            {
                System.out.println(afa.getMessage());
            }
        }
    }

    /**
     *   Az ufo megsemmisüléséért felelős metódus
     */
    @Override
    public void destroy() {
        isdeletable = true;
        for(Resource r : inventory.getResourcesList()){
            r.destroy();
        }
        inventory.getResourcesList().clear();
    }
}
