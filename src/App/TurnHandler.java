package App;

import java.util.ArrayList;
import java.util.Iterator;
import Controller.Controller;

import Exceptions.*;

import java.io.Serializable;

/**
 * A játékmenet köreinek folyamatosságát vezérlő osztály.
 */
public class TurnHandler implements Serializable {
    private static ArrayList<Steppable> steppables = new ArrayList<Steppable>();    ///< A körvezérlő léptetői
    private Game game;                                                              ///< A játék, amiben a körvezérlő részt vesz
    private int currentPlayer = 0;                                                  ///< Az éppen soron lévő játékos indexe

    /**
    *   A körvezérlő konstruktora, ami beállítja a játékot, amiben a körvezérlő részt vesz
    *   @param game - a játék, amiben a körvezérlő részt vesz
    */
    public TurnHandler(Game game) {
        this.game = game;
        currentPlayer = 0;
    }

    /**
     * Sorra hívja meg a játékosok köreit, illetve ha minden játékos jött, akkor meghívja a léptethető dolgok logikáját.
     */
    public void TakeTurns() {
        for (Player player : game.getPlayers()) { 
            player.turn();
        }
        
        for (Steppable steppable : steppables) {
            steppable.Step();
        }
        game.nextTurn();
    }

    /**
     * A léptetők getterje
     * @return léptetők listája
     */
    public static ArrayList<Steppable> getsteppables()
    {
        return steppables;
    }

    /**
     * A léptetők setterje
     * @param list - az adott léptethető dolog, amit hozzá akarunk adni
     */
    public static void setSteppables(ArrayList<Steppable> list){
        steppables = list;
    }

    /**
     * Visszaadja a jelenleg körön levő játékost
     * @return körön levő játékos
     */
    public Player getCurrentPlayer() {
        return game.getPlayers().get(currentPlayer);
    }

    /**
     * Kör befejezését végző metódus
     * @throws Victory - ha a játékos megnyeri a játékot
     * @throws Lose - ha a játékos elveszti a játékot
     */
    public void endTurn() throws Victory, Lose {
        game.checkCondition();
        if (currentPlayer == game.getPlayers().size() - 1) {
            currentPlayer = 0;
            Iterator<Steppable> i = steppables.iterator();
            while(i.hasNext()){
                Steppable s = i.next();
                s.Step();
                if(s.isDeletable()) 
                    i.remove();
            }
            Iterator<Player> ip = game.getPlayers().iterator();
            while(ip.hasNext()) {
                Player p = ip.next();
                if (p.getVillagers().size() == 0) {
                    Controller.removePlayer(p);
                    ip.remove();
                    if(game.getPlayers().size() == 0)
                        throw new Lose("There are no players left to play");
                }
                for (Villager vl : p.getVillagers()) {
                    vl.nextTurn();
                }
            }
            game.getAsteroidField().setPlaceNeighbours();
            game.nextTurn();
        }
        else{
            currentPlayer++;
        } 
    }

    /**
     * Hozzáadja a paraméterben kapott léptethető dolgot a léptethető dolgokhoz.
     * @param s - az adott léptethető dolog, amit hozzá akarunk rakni
     */
    public static void addSteppable(Steppable s) {
        steppables.add(s);
    }

    /**
     * Eltávolítja a paraméterben megadott léptethető dolgot a léptethető dolgokból
     * @param s - az adott léptethető dolog, amit el akarunk távolítani
     */
    public static void removeSteppable(Steppable s) {
        steppables.remove(s);
    }
}
