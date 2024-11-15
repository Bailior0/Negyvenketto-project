package GUI.Frames;

import App.AsteroidField;
import App.Villager;
import GUI.Panels.*;
import App.Place;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * Az aszteroidakeretet megvalósító osztály
 */
public class AsteroidFrame extends JFrame {
    private GamePanel gamePanel;                                        ///> Játékpanel, bal felső panel
    private ControlPanel controlPanel = new ControlPanel();             ///> Irányítópanel, bal alsó panel
    private InformationPanel informationPanel = new InformationPanel(); ///> Információspanel, jobb oldali panel

    /**
     * Az aszteroidakeret elemeit beállító metódus
     */
    private void initComp(){
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.add(gamePanel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.01;
        c.weighty = 1;
        c.gridwidth = 1;
        c.gridheight = 2;

        this.add(informationPanel, c);


        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 0.01;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.add(controlPanel, c);
    }

    /**
     * Az aszteroidakeret konstruktora
     */
    public AsteroidFrame() {
        super("Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setMinimumSize((new Dimension(1200, 700)));
    }

    /**
     * Aszteroidát kiválasztó metódus
     * @param a - kiválasztani kívánt aszteroida
     */
    public void selectPlace(Place a)  {
        informationPanel.setSelectedPlace(a);
    }

    /**
     * Telepest kiválasztó metódus
     * @param v - kiválasztani kívánt telepes
     */
    public void selectVillager(Villager v)
    {
        informationPanel.setSelectedVillager(v);
    }

    /**
     * Aszteroidamező setterje
     * @param af - aszteroidamező
     */
    public void setAsteroidField(AsteroidField af) {
        gamePanel.setAsteroidField(af);
    }
    
    /**
     * Játékpanelt frissítő metódus
     */
    public void Update() {
        gamePanel.update();
        informationPanel.Update();
    }
    
    /**
     * Az objektumokat kirajzoló metódus
     */
    public void DrawAllObjects() {}

    /**
     * Aszteroidát kiemelő metódus
     * @param neighbours - aszteroida szomszédainak listája
     */
    public void highlightAst(List<Place> neighbours) {
        gamePanel.HighlightAst(neighbours);
    }

    /**
     * Aszteroidát kiemelésének újraállítása
     */
    public void resetHighlights(){
        gamePanel.resetHighlight();
    }

    /**
     * Az információs panelt újratöltő metódus
     */
    public void ResetInformationPanel(){
        informationPanel.resetPanels();
    }

    /**
     * Az összes panelt újratöltő metódus
     */
    public void resetPanels() {
        informationPanel.resetPanels();
        try{
            gamePanel = new GamePanel();
            initComp();
        } catch(IOException e){
            e.printStackTrace();
            System.exit(2);
        }
    }

    /**
     * A játék törlését ellenőrző metódus
     */
    public void checkDelete(){
        gamePanel.checkDelete();
    }

    /**
     * Napvihar keletkezéséért felelős metódus
     * @param p - hely, amit napvihar ér
     */
    public void SolarStorm(Place p)
    {
        gamePanel.atSolarStorm(p);
    }
}
