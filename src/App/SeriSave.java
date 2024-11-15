package App;

import java.util.ArrayList;
import java.io.Serializable;

/**
 *  A statikus objektumainkból, egy példány, hogy szerializálni tudjuk
 */
public class SeriSave implements Serializable {
    public ArrayList<Steppable> steppables; ///< Léptetők listája
    public int robotCount;                  ///< Robotok száma
    public int ufoCount;                    ///< Ufok száma
    public int coalCount;                   ///< Szenek száma
    public int ironCount;                   ///< Vasak száma
    public int uranCount;                   ///< Uránok száma
    public int iceCount;                    ///< Jegek száma
    public int asteroidCount;               ///< Aszteroidák száma
    public int gateCount;                   ///< Kapuk száma
    public int playerCount;                 ///< Játékosok száma
    public int villagerCount;               ///< Telepesek száma
}
