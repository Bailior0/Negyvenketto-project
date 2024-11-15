package App;

import java.io.Serializable;
import Exceptions.*;

/**
*   Az aszteroidákon tartózkodó munkásokat reprezentáló osztály. 
*   Egy munkás el tud bújni egy üreges aszteroidában, tud fúrni aszteroidát, tud mozogni másik aszteroidára. 
*   Ha eléri a napvihar, miközben nincs elbújva, megsemmisül. 
*   Ha radioaktív anyagot tartalmazó, megfúrt, napközeli aszteroidán tartózkodik, felrobban.
*/
public abstract class Worker implements Serializable{
    protected boolean canAct;               ///< Tárolja, hogy a munkás cselekedhet-e még a körben
    protected boolean isHidden = false;     ///< Tárolja, hogy a munkás éppen elbújt állapotban van-e
    protected Place place;                  ///< Az a hely, amin a munkás helyezkedik
    protected String ID;                    ///< A munkás azonosítója
    protected AsteroidField af;             ///< Az aszteroidamező, amin a telepes tartózkodik

    /**
     *  A worker konstruktorja, a leszármazottak meghívják
     *  @param af Az aszteroidamező amiben a worker van
     */
    public Worker(AsteroidField af){
        this.af = af;
        canAct=true;
    }

    /**
    *   Az a metódus, ami visszaadja a munkás azonosítóját
    *   @return a munkás azonosítója
    */
    public String getID() {
        return this.ID;
    }

    /**
     * Az a metódus, ami visszaadja, hogy a munkás éppen képes e cselekvésre
     * @return a munkás cselekvési állapota
     */
    public boolean CanAct() {
        return this.canAct;
    }

    /**
    *   Az a metódus, ami visszaadja, hogy a munkás éppen elbújt állapotban van
    *   @return a munkás elbújtsági állapota
    */
    public boolean isHidden() {
        return this.isHidden;
    }

    /**
    *   A munkás inventoriját visszaadó metódus, alap esetben egy üres inventory-t ad vissza
    *   @return a munkás inventorija
    */
    public Inventory getInventory(){ 
        return new Inventory(); 
    }

    /**
    *   A munkás helyét visszaadó metódus
    *   @return a hely, ahol a munkás tartózkodik
    */
    public Place getPlace() { return this.place; }
    
    /**
    *   A munkás helyét beállító metódus
    *   @param newLocation - a munkás új helye
    */
    public void setPlace(Place newLocation) { this.place = newLocation; }


    /**
    *   Az a metódus, ami beállítja a munkás elbújtsági állapotát
    *   @param hidden - a munkás jelenlegi elbújtsági állapota
    */
    public void setHidden(boolean hidden) {
        this.isHidden = hidden;
    }

    /**
    *   Eggyel csökkenti annak az aszteroidának a keménységét, amin a munkás éppen helyezkedik 
    *   @throws ActionFailed - sikertelen művelet esetén kivételt dob
    */
    public void drill() throws ActionFailed {
        if(canAct == false)
            throw new ActionFailed("This villager is too tired to drill");
        if(isHidden)
            throw new ActionFailed("This villager is hidden, so can't drill");
        this.place.beingDrilled();
        this.canAct=false;
    }
    
    /**
    *   A munkást a kívánt helyre átmozgató metódus
    *   Hogyha a kívánt hely éppen szomszédos a jelenlegi hellyel, a munkás átmozog a kívánt helyre
    *   Máskülönben nincs mozgás
    *   @param p - a kívánt hely, ahova mozogni szeretne a munkás
    *   @throws ActionFailed - sikertelen művelet esetén kivételt dob
    */
    public void move(Place p) throws ActionFailed {
        if(canAct == false)
            throw new ActionFailed("This Worker is too tired to move");
        if(isHidden)
            throw new ActionFailed("This Worker is Hidden, so can't move.");
        if(this.place.getNeighbours().contains(p)){
            this.place.remove(this);                     
            p.accept(this);
            setPlace(p);
            this.canAct = false;
        } else{
            throw new ActionFailed("That place is not in the neighbourhood");
        }
    }
    
    /**
    *   A munkás felrobbanásáért felelős metódus
    */
    public void atExplosion() { }
    
    /**
    *   A munkás napviharba kerüléséért felelős metódus
    *   Ha a munkás nincs éppen elbújva, amikor a napvihar érte, akkor megsemmisül
    *   Ha a munkás éppen el van bújva, amikor a napvihar érte, akkor épségben marad
    *   @return igaz, ha a munkás nincs elbújva, hamis, ha el van bújva
    */
    public boolean atSolarStorm() {
        if (!this.isHidden) {
            this.destroy();
            return true;
        }
        return false;
    }
    
    /**
    *   A munkás megsemmisüléséért felelős metódus
    */
    public abstract void destroy();
    
    /**
    *   A munkás elbújásáért felelős metódus
    *   @throws ActionFailed - sikertelen művelet esetén kivételt dob
    */
    public void hide() throws ActionFailed {
        if(canAct) {
            this.place.hide(this);
            this.canAct = false;
        }
        else
            throw new ActionFailed("This Villager is too tired to hide.");
    }

    /**
    *   A munkást teleportkapukkal elteleportáló metódus
    *   @throws ActionFailed - sikertelen művelet esetén kivételt dob
    */
    public void teleport() throws ActionFailed {
        this.place.teleport(this);
        this.canAct = false;
    }
}
