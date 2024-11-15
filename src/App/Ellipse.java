package App;

import java.io.Serializable;
import java.util.Random;

/**
 * Egy ellipszis pályát reprezentál, amin egy égitest kering.
 */
public class Ellipse implements Serializable{
    private Position position;      ///< Az ellipszis pozíciója
    private int minorAxis;          ///< Az ellipszis melléktengelyének hossza
    private int majorAxis;          ///< Az ellipszis főtengelyének hossza
    private double rotatedAngle;    ///< Az ellipszis ferdesége

    /**
     * Az ellipszis konstruktora
     */
    public Ellipse() {
        this.position = Sun.SunPos();
        this.minorAxis = 100;
        this.majorAxis = 200;
        this.rotatedAngle = new Random().nextInt(180);
    }

    /**
     * Az ellipszis konstruktora
     * @param major - főtengely hossza
     * @param minor - melléktengely hossza
     */
    public Ellipse(int major, int minor) {
        this.position = Sun.SunPos();
        this.minorAxis = minor;
        this.majorAxis = major;
        this.rotatedAngle = new Random().nextInt(180);
    }

    /**
     * Az ellipszis konstruktora
     * @param major - főtengely hossza
     * @param minor - melléktengely hossza
     * @param rotatedAngle - ferdesége
     */
    public Ellipse(int major, int minor, int rotatedAngle) {
        this.position = Sun.SunPos();
        this.minorAxis = minor;
        this.majorAxis = major;
        this.rotatedAngle = rotatedAngle;
    }

    /**
     * Pozíció getterje
     * @return pozíció
     */
    public Position getPosition() { return this.position; }

    /**
     * Melléktengely getterje
     * @return melléktengely
     */
    public int getMinorAxis() { return this.minorAxis; }

    /**
     * Főtengely getterje
     * @return főtengely
     */
    public int getMajorAxis() { return this.majorAxis; }

    /**
     * Ferdesége getterje
     * @return ferdesége
     */
    public double getRotatedAngle() { return this.rotatedAngle; }

    /**
     * Pozíció setterje
     * @param p - pozíció
     */
    public void setPosition(Position p) { this.position = p; }

    /**
     * Melléktengely setterje
     * @param m - melléktengely
     */
    public void setMinor(int m) { this.minorAxis = m; }

    /**
     * Főtengely setterje
     * @param m - főtengely
     */
    public void setMajor(int m) { this.majorAxis = m; }

    /**
     * Létrehoz ellipszist két argumentumból
     * @param arg1 - főtengely
     * @param arg2 - melléktengely
     * @throws NumberFormatException - Ha a string formátuma nem megfelelő
     */
    public static Ellipse parse(String arg1, String arg2) throws NumberFormatException {
        return new Ellipse(Integer.parseInt(arg1), Integer.parseInt(arg2));
    }
}
