package App;

import java.util.ArrayList;
import java.util.List;

import java.util.Iterator;

import Exceptions.*;

/**
 * Az aszteroida objektumot megvalósító osztály 
 * Az osztály feladata, hogy tárolja az aszteroida viselkedéséhez szükséges metódusokat
 * és tárolja a magjában megtalálható nyersanyagot
 */
public class Asteroid extends Place {     
    private Resource resource;                              ///< Aszteroid magjában található nyersanyagát tároló objektum
    private int hardness;                                   ///< Az aszteroid köpenyének vastagsága
    private static int count = 0;                           ///< Az aszteroida azonosító száma
    private ArrayList<Place> childs = new ArrayList<>();    ///< Az aszteroida gyerekei

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
    * Az aszteroida konstruktora
    * @param ellipse - Az ellipszis pálya, amelyen az aszteroida rajta van
    * @param period - A körülfordulási idő a pályának
    * @param af - A játék aszteroidamezője
    */
	public Asteroid(Ellipse ellipse, double period, AsteroidField af) 
	{
		super(ellipse, period, af);
        this.resource = null;
        this.ID = "A"+(++count);
        isdeletable=false;
        Step();
	}

    /**
    * Az aszteroida konstruktora
    * @param ellipse - Az ellipszis pálya, amelyen az aszteroida rajta van
    * @param period - A körülfordulási idő a pályának
    * @param resource - Az aszteroida magjában megtalálható nyersanyag
    * @param af - A játék aszteroidamezője
    */
    public Asteroid(Ellipse ellipse, double period, Resource resource, AsteroidField af)          
    {
        super(ellipse, period, af);
        this.resource = resource;
        this.ID = "A" + (++count);
        isdeletable=false;
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
    * Az aszteroida robbanását megvalósító metódus
    * Minden rajta dolgozó munkás felrobban
    */
    @Override
    public void explode() {
        for (int i = 0; i < this.getWorkers().size(); i++) {
            getWorkers().get(i).atExplosion();
        }
        try{
            Iterator<Place> i = childs.iterator();
            while(i.hasNext()) {
                Place p = i.next();
                p.explode();
                p.setFonok(null);
                i.remove();
            } 
        } catch(ActionFailed a){
            System.out.println("Asteroid Explode actionfailed");
        }
    
        af.remove(this);
        isdeletable = true;
        if(resource != null)
            resource.destroy();
    }

    /**
     * Gyereket adunk helyhez
     * @param g - a hely, amihez gyereket adnánk
     * @throws ActionFailed - sikertelen művelet esetén kivételt dob
     */
    @Override
    public void addChild(Place g) throws ActionFailed {
        childs.add(g);
        g.setFonok(this);
    }

    /**
     * Gyereket veszünk el helytől
     * @param g - a hely, amitől gyereket vennénk el
     * @throws ActionFailed - sikertelen művelet esetén kivételt dob
     */
    @Override
    public void removeChild(Place g) throws ActionFailed {
        childs.remove(g);
        g.setFonok(null);
    }

    /**
    * Az aszteroida magjának nyersanyagát visszaadó metódus
    * @return aszteroida mag nyersanyaga
    */
    public Resource getResource() { return resource; }

    /**
    * Az aszteroida magjának nyersanyagát beállító metódus
    * @param r - az aszteroid magjának nyersanyaga 
    */
    public void setResource(Resource r) {
        this.resource = r;
        if (r != null)
            r.setAsteroid(this);
    }

    /**
    * Az aszteroida köpenyének vastagságát beállító metódus
    * @param hard - aszteroida köpenyének vastagsága
    */
    public void setHardness(int hard){ hardness = hard; }

    /**
    * Az aszteroida köpenyének vastagságát visszaadó metódus
    * @return aszteroida köpenyének vastagsága
    */
    public int getHardness(){ return hardness; }

    /**
    * Az aszteroida fúrásánál meghívódó metódus
    * Sikeres fúrás esetén 1-gyel vékonyodik az aszteroid köpenye 
    * Csak akkor fúrhatunk ha van mit fúrni
    * @throws ActionFailed - a művelet végrehajtásának sikertelensége esetén dobódik
    */
    @Override
    public void beingDrilled() throws ActionFailed {                
        if (hardness > 0) {
            hardness--;
        } else
            throw new ActionFailed("This asteroid is already penetrated");
    }
    
    /**
    * Az aszteroida nyersanyag lerakásánál meghívódó metódus
    * Sikeres nyersanyag lerakás esetén az üreges aszteroidába helyezzük a nyersanyagunkat 
    * @param r - a nyersanyag, amelyet lehelyezünk
    * @throws ActionFailed - a művelet végrehajtásának sikertelensége esetén dobódik
    */
    @Override
    public void placeResource(Resource r) throws ActionFailed{     
        if(resource != null)
            throw new ActionFailed(String.format("Asteroid is not empty. %s cannot be placed.", r.getID()));
        if(hardness != 0)
            throw new ActionFailed(String.format("Asteroid is not drilled: %s cannot be placed.", r.getID()));
        for(Worker w : workers)
            w.setHidden(false);
        resource = r;
        r.setAsteroid(this);
    }

    /**
    * Az aszteroida bányászás során meghívódó metódus
    * Sikeres bányászás esetén kinyerjük a nyersanyagot az aszteroidából 
    * @return az aszteroida magjának nyersanya siker esetén, egyébként null
    */
    @Override
    public Resource beingMined() {             
        if(resource != null && hardness==0){     
            Resource temp = resource;
			resource = null;                   
            return temp;                       
        }
        else{
            return null;                       
        }
    }

    /**
    * Egy telepes elbújtatása az aszteroidán
    * @param w - Az elbújtatni kívánó munkás
    * @throws ActionFailed - a művelet végrehajtásának sikertelensége esetén dobódik
    */
    @Override
    public void hide(Worker w) throws ActionFailed { 
	    if(!w.isHidden()) {
	        if(resource != null)
	            throw new ActionFailed("The asteroid is not empty! Cannot hide");
            if (hardness == 0) {                
                w.setHidden(true);           
            }else {
                throw new ActionFailed("The asteroid is not yet penetrated");
            }
        } else{
	        w.setHidden(false);              
        }
    }


    /**
    * Az aszteroidán lévő telepesek nyersanyagkészletét ellenőrző metódus
    * Ha elég nyersanyag gyűlik össze az aszteroidán, akkor a játék győzelemmel zárul a játékosnak
    * @param players - játékosok, akiket lecsekkolunk
    * @throws Victory - ha a játékos megnyeri a játékot
    */
    @Override
    public void beingChecked(List<Player> players) throws Victory {
        Inventory mergedInventory = new Inventory();
        for (Player player : players) {
            ArrayList<Villager> playerVillagersByPlaces = new ArrayList<>();
            for (Villager v : player.getVillagers()){
                if(v.getPlace().equals(this)) playerVillagersByPlaces.add(v);
            }
            for (Villager v : playerVillagersByPlaces) {
                mergedInventory = mergedInventory.merge(v.getInventory());
            }
            boolean isEnough = mergedInventory.isEnough(Game.winningCost());
            if(isEnough) throw new Exceptions.Victory(player.getID() + " has won the game!\nWell done!");
        }  
    }

    /**
    * Az aszteroida ToString() metódusa
    * @return Asteroid
    */
    @Override
    public String toString(){ return "Asteroid"; }

    /**
	 * Az aszteroida egyéni attributumait adja vissza
     * @return aszteroida mag nyersanyagának neve, azonosítója, és az aszteroida köpenye
	 */
    @Override
    public String uniqueAttr(){
        if(resource != null) {
            return "Resource: "+ resource.toString() + " Resource ID: " + resource.getID() + " Hardness: " + hardness;
        }
        return "Resource: Empty Resource ID: 0 Hardness: " + hardness;
    }

    /**
	 * Ellenőrzi, hogy aszteroida magjában megtalálható-e, az alábbi ID-vel rendelkező nyersanyag
     * @param id - A nyersanyag id, amelyet keresni kívánunk
     * @return igaz, ha megtaláltuk a keresett nyersanyagot
	 */
    @Override
    public boolean containsRes(String id){
        return id.equals(resource.getID());
    }
}