package App;

import java.io.Serializable;

/**
 * Megadja a helyek és a nap koordinátáit, az ellipszis fókuszpontjait.
 */
public class Position implements Serializable{
    private int x, y;   ///< Az adott hely x és y koordinátája

    /**
     * A pozíció konstruktora
     * @param x - a hely x koordinátája
     * @param y - a hely y koordinátája
     */
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Az x koordináta getterje
     * @return hely x koordiátája
     */
    public int getX(){ return x;}

    /**
     * Az y koordináta getterje
     * @return hely y koordiátája
     */
    public int getY(){ return y;}

    /**
     * Az x koordináta setterje
     * @param x - hely x koordiátája
     */
    public void setX(int x) { this.x = x;}

    /**
     * Az y koordináta setterje
     * @param y - hely y koordiátája
     */
    public void setY(int y) { this.y = y;}
}
