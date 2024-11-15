package GUI.Panels;

import java.awt.*;

import javax.swing.JPanel;

import App.Place;
import App.Villager;

/**
 * Az információs panelt megvalósító osztály
 */
public class InformationPanel extends JPanel{
    private VillagerPanel villagerPanel;                                ///< Telepesek panelja, felső
    private DescriptionPanel descPanel;                                 ///< Leírás panelja, alsó

    GridBagConstraints descConstraint = new GridBagConstraints();       ///< Az leírás panel görgetősávja
    GridBagConstraints villPanelContraint = new GridBagConstraints();   ///< A telepes panel görgetősávja

    /**
     * Az információs panel konstruktora
     */
    public InformationPanel() {
        super();
    }

    /**
     * A kiválasztott hely beállítása az információs panelen
     * @param p - kiválasztott hely
     */
    public void setSelectedPlace(Place p) {
        if(descPanel != null)
            this.remove(descPanel);
        if(villagerPanel != null)
            this.remove(villagerPanel);
        villagerPanel = new VillagerPanel(p);
        descPanel = new DescriptionPanel(p);

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        villPanelContraint.fill = GridBagConstraints.BOTH;
        villPanelContraint.weightx = 1;
        villPanelContraint.weighty = 0.1;
        villPanelContraint.gridx = 0;
        villPanelContraint.gridy = 0;
        villPanelContraint.gridwidth = 1;
        villPanelContraint.gridheight = 1;
        this.add(villagerPanel, villPanelContraint);
        descConstraint.fill = GridBagConstraints.VERTICAL;
        descConstraint.anchor = GridBagConstraints.FIRST_LINE_START;
        descConstraint.weightx = 1;
        descConstraint.weighty = 1;
        descConstraint.gridx = 0;
        descConstraint.gridy = 1;
        descConstraint.gridwidth = 1;
        descConstraint.gridheight = 1;
        this.add(descPanel, descConstraint);
        this.revalidate();
    }

    /**
     * A kiválasztott telepes beállítása az információs panelen
     * @param v - kiválasztott telepes
     */
    public void setSelectedVillager(Villager v) {
        this.remove(descPanel);
        descPanel = new DescriptionPanel(v);
        this.add(descPanel, descConstraint);
        this.revalidate();
    }

    /**
     * Az információs panel frissítéséért felelős metódus
     */
    public void Update(){
        if(descPanel != null)
        {
            descPanel.Update();
        }
        if(villagerPanel != null) {
            Place a = villagerPanel.getAsteroid();
            this.remove(villagerPanel);
            villagerPanel = new VillagerPanel(a);
            this.add(villagerPanel, villPanelContraint);
        }
        revalidate();
    }

    /**
     * A panelek eltávolításáért felelős metódus
     */
    public void resetPanels(){
        if(descPanel != null) {
            this.remove(descPanel);
            descPanel = null;
        }
        if(villagerPanel != null){
            this.remove(villagerPanel);
            villagerPanel = null;
        }
    }
}
