package GUI.Panels;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.util.HashMap;


import java.awt.*;
import App.Asteroid;
import App.Gate;
import App.Place;
import App.ResourceCount;
import App.Villager;
import Controller.Controller;

/**
 * A leíráspanelt megvalósító osztály
 */
public class DescriptionPanel extends JPanel {
    private JScrollPane scrollpane;                                                         ///< Leíráspanel görgetősávja
    private Icon gateIcon = new ImageIcon("resources/GameObjects/teleportgate_mini.png");   ///< Teleportkapu ikonja
    private JPanel gatePanel;                                                               ///< Teleportkapu panelja
    private HashMap<Gate, JButton> gateMap = new HashMap<Gate, JButton>();                  ///< Teleportkapukat és gombjaikat tároló lista
    JLabel uranLabel;                                                                       ///< Uránt jelző címke
    JLabel ironLabel;                                                                       ///< Vasat jelző címke
    JLabel iceLabel;                                                                        ///< Jeget jelző címke
    JLabel coalLabel;                                                                       ///< Szenet jelző címke
    JLabel statusHiddenLabel;                                                               ///< Elbújtságot jelző címke
    JLabel statusCanActLabel;                                                               ///< Cselekvési képességet jelző címke
    Villager villager;                                                                      ///< A leíráspanel telepese
    Place place;                                                                            ///< A leíráspanel helye
    JLabel hardnessText;                                                                    ///< Vastagságot jelző címke
    JLabel resourceText;                                                                    ///< Nyersanyagot jelző címke
    JLabel isClostToSunText;                                                                ///< Napközeliséget jelző címke
    JLabel isActive;                                                                        ///< Aktivitást jelző címke
    JPanel mainPanel;                                                                       ///< Főpanelt jelző címke

    /**
     * Telepes inventorijában levő teleportkapuk beállítása
     * @param v - telepes
     */
    private void initGates(Villager v){
        gatePanel = new JPanel(new FlowLayout());
        for(Gate gate : v.getInventory().getGatesList()){
            JButton b = new JButton(gateIcon);
            b.setMaximumSize(new Dimension(100, 50));
            gateMap.put(gate, b);
            gateMap.get(gate).addActionListener(e -> Controller.selectGate(gate));
            gatePanel.add(gateMap.get(gate));
        }
        mainPanel.add(gatePanel);
    }

    /**
     * A kapuk frissítését végző metódus
     * A játékban szereplő összes kaput frissíti
     */
    private void updateGates(){
        if(gatePanel != null)
            mainPanel.remove(gatePanel);
        gateMap = new HashMap<>();
        gatePanel = new JPanel(new FlowLayout());
        for(Gate gate : villager.getInventory().getGatesList()){
            JButton b = new JButton(gateIcon);
            b.setMaximumSize(new Dimension(50, 50));
            gateMap.put(gate, b);
            gateMap.get(gate).addActionListener(e -> Controller.selectGate(gate));
            gatePanel.add(gateMap.get(gate));
        }
        mainPanel.add(gatePanel);
    }

    /**
     * Telepes leíráspaneljének konstruktora
     * Beállítja a címkéket, paneleket
     * @param v - telepes
     */
    public DescriptionPanel(Villager v) {
        super();
        villager = v;
        this.setLayout(new BorderLayout());
        mainPanel = new JPanel();
        BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(layout);
        scrollpane = new JScrollPane(mainPanel);

        ResourceCount rc = v.getInventory().getResourceCount();

        JLabel vID = new JLabel("Villager ID: " + v.getID());

        JLabel inventory = new JLabel("Inventory");
        inventory.setFont(new Font("Tahoma", Font.BOLD, 14));
        uranLabel = new JLabel("Uran: " + rc.getUran());
        ironLabel = new JLabel("Iron: "+ rc.getIron());
        iceLabel = new JLabel("Ice: " + rc.getIce());
        coalLabel = new JLabel("Coal: " + rc.getCoal());
        
        statusHiddenLabel = new JLabel("Hidden Status: " + (v.isHidden() ? "Hidden" : "Not Hidden"));
        statusCanActLabel = new JLabel("Tired: " + (!v.CanAct() ? "Yes" : "No"));
        mainPanel.add(vID);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 30)));
        mainPanel.add(inventory);
        mainPanel.add(uranLabel);
        mainPanel.add(ironLabel);
        mainPanel.add(iceLabel);
        mainPanel.add(coalLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 30)));
        mainPanel.add(statusHiddenLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 30)));
        mainPanel.add(statusCanActLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 30)));
        JLabel gatesList = new JLabel("Gate List: ");
        gatesList.setFont(new Font("Tahoma", Font.BOLD, 14));
        this.setPreferredSize(new Dimension(200, 500));
        mainPanel.add(gatesList);
        initGates(v);
        mainPanel.add(gatePanel);
        this.add(scrollpane);
        
    }

    /**
     * A helyek leíráspaneljának konstruktora
     * Beállítja a címkéket, paneleket
     * @param place - hely
     */
    public DescriptionPanel(Place place) {
        super();
        this.place = place;
        this.setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel();
        BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(layout);
        scrollpane = new JScrollPane(mainPanel);
        JLabel aID;
        aID = new JLabel(place.toString()+ " ID: " + place.getID());
        if(place instanceof Asteroid) {
            Asteroid a = (Asteroid)place;
            hardnessText = new JLabel("Hardness: " + a.getHardness());
            if (a.getHardness() <= 0) {
                resourceText = new JLabel("Resource: " + (a.getResource() == null ? "Empty" : a.getResource().toString()));
            } else {
                resourceText = new JLabel("Resource: Unknown");
            }

            isClostToSunText = new JLabel(a.isCloseToSun() ? "This asteroid is close to sun" : "This asteroid is far from the sun");
        } else{
            Gate g = (Gate)place;
            hardnessText = new JLabel("Pair ID: " + g.getPair().getID());

            resourceText = new JLabel("Megkergult status: " + (g.isMegkergult() ? "Kerge" : "Kergen't"));
            isActive = new JLabel("Active status: " + (g.isActive() ? "Active" : "Inactive"));
            mainPanel.add(Box.createRigidArea(new Dimension(10, 30)));
            mainPanel.add(isActive);
            mainPanel.add(Box.createRigidArea(new Dimension(10, 30)));
        }
        isClostToSunText = new JLabel("Is close to sun? " + (place.isCloseToSun() ? "Yes" : "No"));
        mainPanel.add(aID);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 30)));
        mainPanel.add(hardnessText);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 30)));
        
        mainPanel.add(resourceText);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 30)));
        mainPanel.add(isClostToSunText);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 30)));
        JLabel neighbours = new JLabel("Neighbours");
        mainPanel.add(neighbours);
        for (Place p : place.getNeighbours()) {
            JLabel neighbour = new JLabel(p.getID());
            mainPanel.add(neighbour);
        }
        this.setPreferredSize(new Dimension(200, 500));
        this.add(scrollpane);
    }

    /**
     * A játék frissítését végző metódus
     * Frissíti a leíráspanelt a telepesekre és a helyekre
     */
    public void Update(){
        if(villager != null){
            ResourceCount rc = villager.getInventory().getResourceCount();
            uranLabel.setText("Uran: " + rc.getUran());
            ironLabel.setText("Iron: "+ rc.getIron());
            iceLabel.setText("Ice: " + rc.getIce());
            coalLabel.setText("Coal: " + rc.getCoal());

            statusHiddenLabel.setText("Hidden Status: " + (villager.isHidden() ? "Hidden" : "Not Hidden"));
            statusCanActLabel.setText("Tired: " + (!villager.CanAct() ? "Yes" : "No"));
            updateGates();
        } else if(place != null){
            if(place instanceof Asteroid) {
                Asteroid a = (Asteroid)place;
                hardnessText.setText("Hardness: " + a.getHardness());
                if (a.getHardness() <= 0) {
                    resourceText.setText("Resource: " + (a.getResource() == null ? "Empty" : a.getResource().toString()));
                } else {
                    resourceText.setText("Resource: Unknown");
                }
            }
            else{
                Gate g = (Gate)place;
                if(hardnessText == null){
                    hardnessText = new JLabel("");
                    mainPanel.add(hardnessText);
                }
                hardnessText.setText("Pair ID: " + g.getPair().getID());
                if(resourceText == null){
                    resourceText = new JLabel("");
                    mainPanel.add(resourceText);
                }
                resourceText.setText("Megkergult status: " + (g.isMegkergult() ? "Kerge" : "Kergen't"));
                if(isActive == null){
                    isActive = new JLabel("");
                    mainPanel.add(isActive);
                }
                isActive.setText("Active status: " + (g.isActive() ? "Active" : "Inactive"));
            }
            isClostToSunText = new JLabel("Is close to sun? " + (place.isCloseToSun() ? "Yes" : "No"));
        }
    }
}
