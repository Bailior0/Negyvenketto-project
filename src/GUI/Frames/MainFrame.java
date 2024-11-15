package GUI.Frames;

import java.awt.Dimension;
import java.io.IOException;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.Font;

import Controller.*;
import GUI.Panels.JPanelWithBackground;

/**
 * Főmenüt megvalósító osztály
 */
public class MainFrame extends JFrame{

    /**
     * Főmenü konstruktora
     */
    public MainFrame() {
        super("Asteroid mining - Negyvenketto Productions");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(1600, 1168));
        this.setLocationRelativeTo(null);
        init();
    }

    /**
     * Főmenü elemeinek beállítását végző metódus
     * Hozzáadja a gombokat, hátteret a főmenühöz
     */
    public void init()
    {
        try {
            JPanelWithBackground panel = new JPanelWithBackground("resources/Backgrounds/Menu.png", 1600, 1168);
            BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
            panel.setLayout(layout);

            ArrayList<JButton> listOfButtons = new ArrayList<JButton>();
            
            JButton startGame = new JButton("Start Game");
            startGame.addActionListener(e -> Controller.startGame());

            JButton continueGame = new JButton("Continue Game");
            continueGame.addActionListener(e -> Controller.continueGame());

            JButton exitGame = new JButton("Exit Game");
            exitGame.addActionListener(e -> System.exit(0));

            listOfButtons.add(startGame);
            listOfButtons.add(continueGame);
            listOfButtons.add(exitGame);

            
            for (JButton jButton : listOfButtons) {
                panel.add(Box.createRigidArea(new Dimension(100, 150)));
                jButton.setAlignmentX(CENTER_ALIGNMENT);
                jButton.setBackground(Color.LIGHT_GRAY);
                jButton.setMinimumSize(new Dimension(300, 100));
                jButton.setFont(new Font("Arial", Font.PLAIN, 50));
                panel.add(jButton);
            }
            this.add(panel);
        }catch(IOException e) {
            System.out.println("Rossz volt a file beolvasás a Main frameben");
        }
    }
}