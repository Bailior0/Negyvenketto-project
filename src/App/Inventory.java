package App;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.Iterator;

/**
 * A munkások inventoriját reprezentáló osztály
 * Az osztály feladata, hogy kezelje a nyersanyag listák feltöltését és üritését, valamint ezen felül tárolja a Gate-et.
 */
public class Inventory implements Serializable {
	private ArrayList<Resource> resources;	///> Az inventoryban levő nyersanyagok listája
	private ArrayList<Gate> gates; 			///> Az inventory-ban tárolt teleportkapuk, ebben foglalnak helyet

	/**
	 * Alapkonstruktor inicializálja a listákat.
	 */
	public Inventory() {
		resources = new ArrayList<>();
		gates = new ArrayList<>();
	}

	/**
	 * Konstruktor, ami egy Inventory példányból készít egy másik ugyanolyan Inventoryt
	 * @param i - másolandó inventory
	 */
	public Inventory(Inventory i) {
		resources = i.resources;
		gates = i.gates;
	}

	/**
	 * Resourcelist getterje
	 * @return nyersanyagok listája
	 */
	public ArrayList<Resource> getResourcesList(){ return resources; }

	/**
	 * Gatelist getterje
	 * @return kapuk listája
	 */
	public ArrayList<Gate> getGatesList(){ return gates; }

	/**
	 * Visszadja mennyi nyersanyag van összesen az inventoryban
	 * @return nyersanyagok száma
	 */
	public int resSum(){ return resources.size(); }


	/**
	 * Hozzáadja a paraméterként megkapott nyersanyagot a resources listához
	 * @param res - a kívánt nyersanyag
	 */
	public void addResource(Resource res) { this.resources.add(res); }

	/**
	 * Hozzáad egy kaput a listához
	 * @param gate - a kívánt kapu
	 */
	public void addGate(Gate gate) { this.gates.add(gate); } 			


	/**
	 * Törli az első kaput a listából
	 * @param g - a kívánt kapu
	 */
	public void remove(Gate g) { this.gates.remove(g); }

	
	/**
	 * Megnézi, hogy a paraméterként kapott nyersanyagokból van-e elég az inventoryban
	 * Végig iterál a nyersanyaglistán.
	 * Ha kell az adott nyersanyag, akkor megnézi, hogy üres-e a költségnek az ellenőrző listája.
	 * @param b - az adott nyersanyaglista
	 * @return igaz, ha van elegendő nyersanyag az inventoryban
	 */
	public boolean isEnough(BillOfMaterials b) {
		boolean a = false;
		for (int i = 0; i < resources.size(); i++)
			if (b.isNeeded(resources.get(i)))
				if (b.isEmpty()) {
					a = true;
					break;
				}
		return a;
	}

	/**
	 * Nyersanyagköltés az inventoryból
	 * Végig iterál az inventory nyersanyagain, 
	 * Ha a költségnek megfelelő nyersanyagot talál, akkor kitörli az inventoryból a nyersanyagot.
	 * @param b - elkölteni kívánt nyersanyagok listája
	 */
	public void spend(BillOfMaterials b) {
		Iterator<Resource> i = resources.iterator();
		while(i.hasNext()){
			Resource r = i.next();
			if (b.isNeeded(r))
			{
				i.remove();
			}
		}
	}

	/**
	 * Összefésüli az inventoryt egy másik inventoryval.
	 * Lemásolja az inventoryt
	 * Hozzáadja a paraméterként megkapott inventory nyersanyagait és teleportkapujait
	 * Végül visszatér az összefésült inventoryval
	 * @param i - a megadott inventory, amivel össze akarjuk fésülni az inventorynkat
	 * @return az összefésült inventory
	 */
	public Inventory merge(Inventory i) {
		Inventory merged = new Inventory(this);
		merged.resources.addAll(i.resources);
		merged.gates.addAll(i.gates);
		return merged;
	}

	/**
	 * toString metódus a kiiratáshoz
	 * @return Inventory
	 */
    public ResourceCount getResourceCount(){
		int iron = 0, coal = 0, uran = 0, ice = 0;
		for (Resource res : getResourcesList()) {
			if(res instanceof Iron) iron++;
			if(res instanceof Coal) coal++;
			if(res instanceof Uran) uran++;
			if(res instanceof Ice) ice++;
		}
        return new ResourceCount(iron, uran, coal, ice);
    }
}   
