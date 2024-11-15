package Controller;

import App.*;
import GUI.Frames.AsteroidFrame;
import GUI.Frames.MainFrame;
import Exceptions.*;

import java.util.List;
import java.util.HashMap;
import java.util.Random;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;

/**
 * A játék vezérlőjét megvalósító osztály
 */
public class Controller {
    private static Villager currentVillager;                            ///> Jelenlegi telepes
    private static Game game;                                           ///> Játék
    private static AsteroidFrame frame;                                 ///> Aszteroidakeret
    private static MainFrame mainFrame;                                 ///> Főmenükeret
    private static Gate selectedGate;                                   ///> Kiválasztott kapu
    private static boolean isMoving = false;                            ///> Mozgási állapot
    private static final int asteroidNumber = 18;                        ///> Aszteroidák száma
    private static final int villagerNumber = 4;                        ///> Aszteroidák száma
    public static HashMap<Player, Color> players = new HashMap<>();     ///> Játékosokat és a jáátékosok színeit tartalmazó lista
    
    /**
     * Mozgási állapot gettere
     * @return mozgási állapot
     */
    public static boolean getisMoving() {return isMoving;}


    /**
     * Aszteroidakeret setterje
     * @param af - aszteroidakeret
     */
    public static void setAsteroidFrame(AsteroidFrame af){
        frame = af;
    }

    /**
     * Főmenükeret setterje
     * @param mf - főmenükeret
     */
    public static void setMainFrame(MainFrame mf){
        mainFrame = mf;
    }

    /**
     * A jelenlegi kapu getterje
     * @return jelenlegi kapu
     */
    public static Gate getCurrentGate() {
        return selectedGate;
    }

    /**
     * Telepes mozgatásáért felelős metódus
     */
    public static void move() {
        if(currentVillager == null)
            JOptionPane.showMessageDialog(frame, "No selected villager!");
        else{
            frame.highlightAst(currentVillager.getPlace().getNeighbours());
            isMoving = true;
        }
    }

    /**
     * Jelenlegi telepes setterje
     * @param villager - telepes
     */
    public static void setCurrentVillager(Villager villager) {
        currentVillager = villager;
    }

    /**
     * Jelenlegi telepes getterje
     * @return jelenlegi telepes
     */
    public static Villager getCurrentVillager() {
        return currentVillager;
    }
    
    /**
     * Játék getterje
     * @return játék
     */
    public static Game getGame() {
        return game;
    }

    /**
     * Telepes elbújásáért felelős metódus
     */
    public static void hide() {
        try {
            if(currentVillager == null)
                throw new ActionFailed("No villager selected!");
            currentVillager.hide();
            frame.Update();
        } catch(ActionFailed actionFaile) {
            JOptionPane.showMessageDialog(frame, actionFaile.getMessage());
        }
    }

    /**
     * Telepes elteleportálásáért felelős metódus
     */
    public static void teleport() {
        try{
            if(currentVillager != null) { 
                currentVillager.teleport();
                frame.Update();
            }
            else {
                throw new ActionFailed("No villager selected");
            }
        } catch(ActionFailed actionfailed) {
            JOptionPane.showMessageDialog(frame, actionfailed.getMessage());
        }
    }

    /**
     * Telepes fúrásáért felelős metódus
     */
    public static void drill() {
        try{
            if(currentVillager != null) {
                currentVillager.drill();
                currentVillager = null;
                frame.Update();
            }
            else {
                throw new ActionFailed("No villager selected");
            }
        } catch(ActionFailed actionfailed) {
            JOptionPane.showMessageDialog(frame, actionfailed.getMessage());
        }
    }

    /**
     * Telepes bányászásáért felelős metódus
     */
    public static void mine() {
        try {
            if(currentVillager != null) {
            currentVillager.mine();
            frame.Update();
        }
        else
            throw new ActionFailed("No villager selected");
        } catch (InventoryFull inventoryFull){
            JOptionPane.showMessageDialog(frame, inventoryFull.getMessage());
        } catch (ActionFailed actionfailed){
            JOptionPane.showMessageDialog(frame, actionfailed.getMessage());
        }
    }

    /**
     * Telepes kapuépítéséért felelős metódus
     */
    public static void buildGates() {
        try {
            if (currentVillager != null) {
                currentVillager.buildGates();
                frame.Update();
            }
            else
                throw new ActionFailed("No villager selected");
        } catch (ActionFailed actionfailed){
            JOptionPane.showMessageDialog(frame, actionfailed.getMessage());
        } catch (InventoryFull inventoryFull){
            JOptionPane.showMessageDialog(frame, inventoryFull.getMessage());
        } finally{
            selectedGate = null;
        }
    }

    /**
     * Telepes robotépítéséért felelős metódus
     */
    public static void buildRobots() {
        try {
            if(currentVillager == null) throw new ActionFailed("No villager selected");
            currentVillager.buildRobot();
            frame.Update();
        } catch(ActionFailed actionfailed) {
            JOptionPane.showMessageDialog(frame, actionfailed.getMessage());
        }
    }

    /**
     * Telepes kapulehelyezéséért felelős metódus
     */
    public static void placeGate() {
        try {
            if(selectedGate!=null)
            {
                currentVillager.placeGate(selectedGate);
                frame.Update();
            }
            else
                JOptionPane.showMessageDialog(frame, "No gate selected");
        } catch(ActionFailed actionfailed){
            JOptionPane.showMessageDialog(frame, actionfailed.getMessage());
        } finally{
            selectedGate = null;
        }
    }

    /**
     * Telepes nyersanyaglehelyezéséért felelős metódus
     */
    public static void placeResource() {
        try {
            if(currentVillager == null) throw new ActionFailed("No villager selected");
            Object[] options = {"Uran","Ice", "Coal","Iron","Back"};
            int n = JOptionPane.showOptionDialog(frame, "What resource would you place down?", "Villager inventory",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[4]);
            Resource r = null;
            if(n != 4 && n != -1){
                switch(n){
                    case 0:
                        r = new Uran();
                        break;
                    case 1:
                        r = new Ice();
                        break;
                    case 2:
                        r = new Coal();
                        break;
                    case 3:
                        r = new Iron();
                }
                currentVillager.placeResource(r);
                frame.Update();
            }
        } catch(ActionFailed actionfailed){
            JOptionPane.showMessageDialog(frame, actionfailed.getMessage());
        }
    }

    /**
     * Játék kezdéséért felelős metódus
     */
    public static void startGame() {
        initField();
        frame.resetPanels();
        frame.setAsteroidField(game.getAsteroidField());
        mainFrame.setVisible(false);
        frame.setVisible(true);
    }

    /**
     * Játék folytatásáért felelős metódus
     */
    public static void continueGame() {
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("gamesave.save"));
            game = (Game) is.readObject();
            game.loadGame();
            is.close();
            List<Player> playersList = game.getPlayers();
            players.put(playersList.get(0), Color.BLUE);
            players.put(playersList.get(1), Color.YELLOW);
            frame.setAsteroidField(game.getAsteroidField());
            mainFrame.setVisible(false);
            frame.setVisible(true);
        } catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(frame, "There is no save file yet.");
        } 
        catch(IOException e){
            e.printStackTrace();
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * Játékból való kilépésért felelős metódus
     */
    public static void exitGame(){
        try {
            FileOutputStream fos = new FileOutputStream("gamesave.save");
            ObjectOutputStream ois = new ObjectOutputStream(fos);
            game.saveGame();
            ois.writeObject(game);
            ois.close();
            selectedGate=null;
            isMoving=false;
            currentVillager=null;
            players.clear();
            frame.resetPanels();
            frame.setVisible(false);
            mainFrame.setVisible(true);
        } catch (IOException e) {
            System.out.println("hát ez jó nagy szívás lul");
            e.printStackTrace();
        }
    }

    /**
     * Kör befejezéséért felelős metódus
     */
    public static void endTurn() {
        try{
            frame.resetHighlights();
            frame.ResetInformationPanel();
            game.getTurnHandler().endTurn();
            frame.checkDelete();
            frame.Update();
            
        }
        catch(Exceptions.Victory v)
        {
            JOptionPane.showMessageDialog(frame, v.getMessage());
            exitGame();
            
        }
        catch(Exceptions.Lose l)
        {
            JOptionPane.showMessageDialog(frame, l.getMessage());
            exitGame();
        } finally{
            currentVillager = null;
            selectedGate = null;
        }
    }

    /**
     * Játékpálya létrehozásáért, a játék elemeinek beállításáért felelős metódus
     * @return létrehozott játék
     */
    public static Game initField(){
        game = new Game();
        game.setPlayers(2);
        List<Player> playersList = game.getPlayers();
        players.put(playersList.get(0), Color.BLUE);
        players.put(playersList.get(1), Color.YELLOW);
 
        Random random = new Random();

        for (int i = 0; i < asteroidNumber; i++) {
            int whichResource = random.nextInt(5);
            Asteroid asteroid =null;
            if(i<asteroidNumber/3)
                asteroid = new Asteroid(new Ellipse(random.nextInt(100-50) + 50, random.nextInt(100-50) + 50), random.nextInt(26) + 50, game.getAsteroidField());
            else if(i<(asteroidNumber/3)*2)
                asteroid = new Asteroid(new Ellipse(random.nextInt(200-100) + 100, random.nextInt(200-100) + 100), random.nextInt(26) + 50, game.getAsteroidField());
            else
                asteroid = new Asteroid(new Ellipse(random.nextInt(250-200) + 200, random.nextInt(250-200) + 200), random.nextInt(26) + 50, game.getAsteroidField());
            asteroid.setResource(randomResource(whichResource));
            asteroid.setHardness(random.nextInt(5));
            game.getAsteroidField().add(asteroid);
        }
        for (Player player : game.getPlayers()) {
            for(int i=0; i<villagerNumber; i++)
            {
                Place ast = game.getAsteroidField().getPlaces().get(random.nextInt(asteroidNumber));
                Villager v = new Villager(player, ast, game.getAsteroidField());
                v.getInventory().addResource(randomResource(random.nextInt(4)));
                v.setPlace(ast);
                ast.accept(v);
            }
        }
        game.getAsteroidField().getPlaces().get(0).accept(new Ufo(game.getAsteroidField().getPlaces().get(0), game.getAsteroidField()));
        game.getAsteroidField().setPlaceNeighbours();
        return game;
    }

    /**
     * Nyersanyagot randomizáló metódus
     * @param randomnum - szám, ami alapján a nyersanyag kiválasztásra kerül
     * @return random kiválasztott nyersanyag
     */
    private static Resource randomResource(int randomnum) {
        switch (randomnum) {
            case 0:
                return new Iron();
            case 1:
                return new Ice();
            case 2:
                return new Uran();
            case 3:
                return new Coal();
            default:
                return null;
        }
    }

    /**
     * Hely kiválasztásáért felelős metódus
     * @param p - kiválasztani kívánt hely
     */
    public static void selectPlace(Place p){
        if(!isMoving){
            currentVillager = null;
            frame.selectPlace(p);
        } else{
            try{
                if(currentVillager != null) {
                    frame.selectPlace(currentVillager.getPlace());
                    currentVillager.move(p);
                    currentVillager = null;
                    frame.Update();
                }
            } catch(ActionFailed e){
                JOptionPane.showMessageDialog(frame, e.getMessage());
            }
            frame.resetHighlights();
            isMoving = false;
        }
    }

    /**
     * Telepes kiválasztásáért felelős metódus
     * @param v - kiválasztani kívánt telepes
     */
    public static void selectVillager(Villager v) {
            frame.selectVillager(v);
            currentVillager = v;
    }

    /**
     * Megadaj, hogy a paraméterben kapott telepes a jellenlegi játékos telepese, vagy sem
     * @param v - telepes, akit le szeretnénk ellenőrízni
     * @return igaz, ha a telepes a játékosé
     */
    public static boolean IsCurrentPlayerVillager(Villager v)
    {
        return game.getCurrentPlayer().Find(v.getID()) != null;
    }

    /**
     * Kapu kiválasztásáért felelős metódus
     * @param g - kiválasztani kívánt kapu
     */
    public static void selectGate(Gate g){
        if(!isMoving)
            selectedGate = g;
        isMoving = false;
    }

    /**
     * Játékost eltávolító metódus
     * @param player - eltávolítandó játékos
     */
    public static void removePlayer(Player player){
        players.remove(player);
        JOptionPane.showMessageDialog(frame, String.format("%s player has exttinguished and cannot continue!", player.getID()));
    }

    /**
     * Napvihar keletkezéséért felelős metódus
     * @param p - hely, amit napvihar ér
     */
    public static void solarStorm(Place p){
        if(p!=null)
            frame.SolarStorm(p);
        else
        { 
            JOptionPane.showMessageDialog(frame, "Stolar storm swept through the asteroid field! Creatures on the highlighted places in red bracket has been affected.");
            frame.resetHighlights();
        }
    }
}