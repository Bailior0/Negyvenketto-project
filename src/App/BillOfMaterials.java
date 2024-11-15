package App;

import java.util.List;
import java.io.Serializable;

/**
 * Példányai egy adott nyersanyag-készletet írnak le.
 */
public class BillOfMaterials implements Serializable{                  
    private List<Resource> checklist;   ///< A szükséges nyersanyagok listája

    /**
     * Nyersanyagkészlet konstruktora
     * @param list - szükséges nyersanyagok listája
     */
    public BillOfMaterials(List<Resource> list) {
        checklist = list;
    }

    /**
     * A szükséges nyersanyagok listájának getterje
     * @return szükséges nyersanyagok listája
     */
    public List<Resource> getchecklist() { return checklist; }
    
    /**
     * A checklist ürességének lekérdezéséért felelős metódus
     * @return checklist üressége
     */
    public boolean isEmpty() { return checklist.isEmpty(); }
    
    /**
     * Egy nyersanyag szükségességének eldöntéséért felelős metódus
     * Végig megyünk a checklisten, és ha találunk r-el megegyező nyersanyagot:
     * Kihúzzuk a checklistről és visszatérünk igazzal
     * @param r - szükségesség szempontjából vizsgálandó nyersanyag
     * @return szükségesség
     */
    public boolean isNeeded(Resource r) {
        boolean b = false;
        for (int i = 0; i < checklist.size(); i++) {
            if (r.isCompatibleWith(checklist.get(i))) {
                checklist.remove(i);
                b = true;
                break;
            }
        }
        return b;
    }

    /**
	 * toString metódus a kiiratáshoz
	 * @return BillingOfMaterials
	 */
    @Override
    public String toString(){
        return "billingofmaterials";
    }
}
