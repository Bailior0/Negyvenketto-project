package GUI.Panels;

import java.awt.*;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import App.*;
import App.Robot;
import Controller.Controller;

import static javax.swing.ScrollPaneConstants.*;

/**
 * Telepespanelt megvalósító osztály
 */
public class VillagerPanel extends JPanel{
    private Place a;                                                                        ///< Hely, amin a telepesek vannak
    private Icon villagerIcon = new ImageIcon("resources/GameObjects/villager_mini.png");   ///< Telepes képe
    private Icon robotIcon = new ImageIcon("resources/GameObjects/Robot_mini.png");         ///< Robot képe
    private Icon ufoIcon = new ImageIcon("resources/GameObjects/Ufo_mini.png");             ///< Ufo képe
    private JButton currentVillButton;                                                      ///< A jelenlegi telepest jelző gomb

    /**
     * A telepespanel konstruktora
     * Beállítja a gombokat, paneleket
     * @param a - hely, amin a telepesek tartózkodnak
     */
    public VillagerPanel(Place a) {
        this.a = a;
        this.setLayout(new BorderLayout());
        
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        JPanel mainPanel = new JPanel(layout);
        JScrollPane scrollpane = new JScrollPane(mainPanel);
        scrollpane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
        if(a != null) {
            for (Worker w : a.getWorkers()) {
                JButton villagerButton;
                if (w instanceof Villager) {
                    if(!Controller.IsCurrentPlayerVillager((Villager)w))
                        continue;
                        villagerButton = new JButton(villagerIcon);
                        villagerButton.addActionListener(e -> {
                            if (currentVillButton != null)
                                currentVillButton.setBorder(new LineBorder(Color.BLACK, 2));
                            Controller.selectVillager((Villager) w);
                            JButton jbutton = (JButton) (e.getSource());
                            jbutton.setMaximumSize(new Dimension(40, 40));
                            jbutton.setBorder(
                                    new LineBorder(Controller.players.get(Controller.getGame().getCurrentPlayer()), 2));
                            currentVillButton = jbutton;
                        });
                }else if(w instanceof Robot){
                    villagerButton = new JButton(robotIcon);
                    villagerButton.setEnabled(false);
                }else if (w instanceof Ufo) {
                    villagerButton = new JButton(ufoIcon);
                    villagerButton.setEnabled(false);
                } else {
                    villagerButton = new JButton("How could thiss miklós vagyok és csillámpóni lettem")
                ;   villagerButton.setEnabled(false);
                }
                villagerButton.setMaximumSize(new Dimension(40, 40));
                villagerButton.setBorder(new LineBorder(Color.BLACK, 2));
                mainPanel.add(villagerButton);
            }
            this.setPreferredSize(new Dimension(150, 65));
            this.add(scrollpane);
        }
    }

    /**
     * A hely gettere
     * @return hely
     */
    public Place getAsteroid() {
        return a;
    }
}
