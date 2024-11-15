package App;

import java.util.ArrayList;

import Exceptions.InventoryFull;
import Exceptions.ActionFailed;

/**
*   Az aszteroidákon tartózkodó telepeseket reprezentáló osztály.
*   Egy munkás el tud bújni egy üreges aszteroidában, tud fúrni aszteroidát, 
*   tud mozogni másik aszteroidára, ki tudja bányászni az aszteroida nyersanyagát és lehelyezhet aszteroidára nyersanyagot.
*   Ha eléri a napvihar, miközben nincs elbújva, megsemmisül. 
*   Ha radioaktív anyagot tartalmazó, megfúrt, napközeli aszteroidán tartózkodik, felrobban.
*/
public class Villager extends Worker {
    private Inventory inventory;    ///< A telepes inventorija
    private static int count = 0;   ///< A telepes azonosításához szükséges szám komponens
    private Player player;          ///< A telepest irányító játékos

    /**
    *   A telepesek konstruktora, ami beállítja a kezdőhelyüket és az aszteroidamezőjüket
    *   @param player - a telepes játékosa
    *   @param startPos - a telepes kezdőhelye
    *   @param af - a telepes aszteroidamezője
    */
    public Villager(Player player, Place startPos, AsteroidField af) {
        super(af);
        this.setPlace(startPos);
        inventory = new Inventory();
        this.player = player;
        player.getVillagers().add(this);
        ID = "V" + (++count);
    }

    /**
    * Visszatér a count értékével.
    * @return id számlálója 
    */
    public static int getcount() {
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
     * A telepes következő körbe való átlépéséért felelő metódus
     */
    public void nextTurn() {
        canAct = true;
    }

    /**
    *   A telepes bányászásáért felelős metódus. 
    *   Kiszedi a nyersanyagot a megfúrt aszteroidából, és beleteszi a telepes tárolójába.
    *   Ha a telepes inventorijában már van 10 nyersanyag, akkor nem lehet többet bányászni
    *   Más esetben lehet bányászni, és a kibányászott nyersanyag belekerül a telepes inventorijába
    *   @throws InventoryFull - ha az inventory megtelt kivételt dob
    *   @throws ActionFailed - sikertelen művelet esetén kivételt dob
    */
    public void mine() throws InventoryFull, ActionFailed {
        if (canAct && !isHidden) 
        {
        Resource res;
        if (inventory.resSum() == 10) {
            throw new InventoryFull("Your inventory is full!");
        } else if ((res = place.beingMined()) != null) {
            inventory.addResource(res);
            canAct = false;
            return;
        } else {
            throw new ActionFailed("There is no resource in the asteroid.");
        }
        }
        else
            throw new ActionFailed("This Villager is too tired to mine or it is hidden.");
    }

    /**
    *   A telepes nyersanyag lehelyezéséért felelős metódus
    *   Ha az aszteroida megfúrt, van a lehelyezendő nyersanyagból, és
    *   le lehet helyezni a nyersanyagot az aszteroidára, akkor lehelyezi
    *   @param r - az adott nyersanyag, amit le kívánunk helyezni
    *   @throws ActionFailed - sikertelen művelet esetén kivételt dob
    */
    public void placeResource(Resource r) throws ActionFailed{
        if (canAct && !isHidden) 
        {
            ArrayList<Resource> list = new ArrayList<>();
            list.add(r);
            ArrayList<Resource> trueList = new ArrayList<>();
            for(int i = 0; i < list.size(); i++)
                trueList.add(list.get(i));
            BillOfMaterials b = new BillOfMaterials(list);

            if (inventory.isEnough(b)) {
                place.placeResource(r);
                inventory.spend(new BillOfMaterials(trueList));
                canAct = false;
            } else {
                throw new ActionFailed(ID + " Villager does not have the needed resource.");
            }
        }
        else
            throw new ActionFailed("This Villager is too tired to place resource or it is hidden.");
    }

    /**
    *   A kapuk építéséért felelős metódus
    *   Ha a telepes rendelkezik a szükséges nyersanyagokkal, megépülhet a robot
    *   Ha nincs elegendő nyersanyag, nincs robot
    *   @throws ActionFailed - sikertelen művelet esetén kivételt dob
    *   @throws InventoryFull - ha az inventory megtelt kivételt dob
    */
    public void buildGates() throws ActionFailed, InventoryFull {
        if (canAct && !isHidden) 
        {
            if (inventory.getGatesList().size() < 2) {
                Gate newGate = new Gate(af);
                Gate newGate2 = new Gate(af);
                newGate.setPair(newGate2);
                newGate2.setPair(newGate);
                if (inventory.isEnough(Gate.getCost())) {
                    inventory.spend(Gate.getCost());
                    inventory.addGate(newGate);
                    inventory.addGate(newGate2);
                    canAct = false;
                } else {
                    throw new ActionFailed(ID + " couldn't build the gates, because it doesn't have enough resources.\nYou need: 2 iron, 1 ice, 1 uran");
                }
            } else
                throw new InventoryFull(ID + " couldn't build the gates, because it doesn't have enough free space.");
        }
        else
            throw new ActionFailed("This Villager is too tired to build gates or it is hidden.");
    }

    /**
    *   Robot építéséért felelős metódus
    *   Ha a telepes rendelkezik a szükséges nyersanyagokkal, megépülhet a robot
    *   Ha nincs elegendő nyersanyag, nincs robot
    *   @throws ActionFailed - sikertelen művelet esetén kivételt dob
    */
    public void buildRobot() throws ActionFailed {
        if (canAct && !isHidden) 
        {
            if (inventory.isEnough(Robot.getCost())) {
                inventory.spend(Robot.getCost());
                Robot robi = new Robot(af);
                place.accept(robi);
                robi.setPlace(place);
                canAct = false;
            } else {
                throw new ActionFailed(ID + " couldn't build the robot, because it doesn't have enough resources.\nYou need: 1 iron, 1 coal, 1 uran");
            }
        }
        else
            throw new ActionFailed("This Villager is too tired to build robot or it is hidden.");
    }

    /**
    *   Kapu lehelyezéséért felelős metódus
    *   Ha mindkét kapu lehelyezésre került, a kapuk aktiválódnak
    *   A lehelyezett kaput eltávolítjuk az inventoriból
    *   @param gate - a lehelyezni kívánt kapu
    *   @throws ActionFailed - sikertelen művelet esetén kivételt dob
    */
    public void placeGate(Gate gate) throws ActionFailed {
        if (canAct&&!isHidden) 
        {
            Place location = this.getPlace();
            Gate g2 = gate.getPair();

            location.addChild(gate);
            af.add(gate);
            gate.setPlaced(true);

            if (g2.isPlaced()) {
                g2.setActive(true);
                gate.setActive(true);
                af.setPlaceNeighboursFor(g2);
            }
            af.setPlaceNeighboursFor(location);
            af.setPlaceNeighboursFor(gate);
            inventory.remove(gate);
            canAct = false;
        }
        else
            throw new ActionFailed("This Villager is too tired to place gate or it is hidden.");
    }

    /**
    *   A telepes felrobbanásáért felelős metódus
    */
    @Override
    public void atExplosion() {
        this.destroy();
    }

    /**
    *   A telepes inventoriját visszaadó metódus
    *   @return a telepes inventorija
    */
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
    *   toString metódus a kiiratáshoz
    *   @return Villager
    */
    @Override
    public String toString() {
        return "villager";
    }

    /**
     * A telepes megsemmisüléséért felelős metódus
     */
    @Override
    public void destroy() {

        for (Gate g : inventory.getGatesList()) {
            g.explode();
        }
        inventory.getGatesList().clear();
        for (Resource r : inventory.getResourcesList()) {
            r.destroy();
        }
        inventory.getResourcesList().clear();
        player.removeVillager(this);
    }
}
