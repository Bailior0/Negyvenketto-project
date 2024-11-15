package App;

import java.io.Serializable;

/**
 * Nyersanyag számlálásért felelős osztály
 */
public class ResourceCount implements Serializable{
    private int iron;       ///< A vasak száma
    private int uran;       ///< Az uránok száma
    private int coal;       ///< A szenek száma
    private int ice;        ///< A jegek száma

    /**
    *   A nyersanyagszámláló konstruktora, ami inicializálja a nyersanyagok mennyiségét
    *   @param iron - vas mennyisége
    *   @param uran - urán mennyisége
    *   @param coal - szén mennyisége
    *   @param ice - jég mennyisége
    */
    public ResourceCount(int iron, int uran, int coal, int ice) {
        this.iron = iron;
        this.uran = uran;
        this.coal = coal;
        this.ice = ice;
    }

    /**
     * A nyersanyagszámláló konstruktora
     */
    public ResourceCount() {}

    /**
     * A szenek getteléséért felelős metódus
     * @return szén mennyisége
     */
    public int getCoal() {
        return coal;
    }

    /**
     * A jegek getteléséért felelős metódus
     * @return jég mennyisége
     */
    public int getIce() {
        return ice;
    }

    /**
     * A vasak getteléséért felelős metódus
     * @return vas mennyisége
     */
    public int getIron() {
        return iron;
    }

    /**
     * Az uránok getteléséért felelős metódus
     * @return urán mennyisége
     */
    public int getUran() {
        return uran;
    }

    /**
     * Egy nyersanyag szám szummázásért felelős metódus
     * @return a nyersanyagok összességének mennyisége
     */
    public int getSum(){
        return iron + uran + coal + ice;
    }

    /**
     * Visszajelzésért felelős metódus
     * @return 10 - a nyersanyagok összességének mennyisége
     */
    public int getFeedback() {
        return 10 - getSum();
    }

    /**
    *   toString metódus a kiiratáshoz
    *   @return nyersanyagok mennyiségei
    */
    @Override
    public String toString(){
        return String.format("iron: %d  uran: %d  coal: %d  ice: %d", iron, uran, coal, ice);
    }
}