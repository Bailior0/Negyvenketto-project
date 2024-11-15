package GUI.Panels;

import java.awt.*;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JPanel;

import Controller.Controller;

/**
 * Az irányítópanelt megvalósító osztály
 */
public class ControlPanel extends JPanel {
    private HashMap<String, JButton> buttons = new HashMap<>(); ///< Az irányítópanel gombjait és szövegeit tároló lista

    /**
     * Az irányítópanel konstruktora
     */
    public ControlPanel() {
        buttons.put("Exit", new JButton("Exit"));
        buttons.put("End Turn", new JButton("End Turn"));
        buttons.put("Place Gate", new JButton("Place Gate"));
        buttons.put("Build Robot", new JButton("Build Robot"));
        buttons.put("Build Gate", new JButton("Build Gate"));
        buttons.put("Hide", new JButton("Hide"));
        buttons.put("Mine", new JButton("Mine"));
        buttons.put("Drill", new JButton("Drill"));
        buttons.put("Place Resource", new JButton("Place Resource"));
        buttons.put("Move", new JButton("Move"));
        buttons.put("Teleport", new JButton("Teleport"));

        initButtons();
    }

    /**
     * Az irányítópanel gombjainak beállítása
     */
    private void initButtons() {
        Iterator<JButton> i = buttons.values().iterator();
        while(i.hasNext()) {
            JButton button = i.next();
            button.setBackground(Color.LIGHT_GRAY);
            button.setFont(new Font("Arial", Font.PLAIN, 15));
        }

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 2;
        c.insets = new Insets(5, 5, 5, 20);
        this.add(buttons.get("Exit"), c);
        buttons.get("Exit").addActionListener(e -> Controller.exitGame());

        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.insets = new Insets(5, 0, 5, 5);
        this.add(buttons.get("End Turn"), c);
        buttons.get("End Turn").addActionListener(e ->  Controller.endTurn());

        c.gridx = 3;
        c.gridy = 0;
        this.add(buttons.get("Place Gate"), c);
        buttons.get("Place Gate").addActionListener(e ->  Controller.placeGate());

        c.gridx = 4;
        c.gridy = 0;
        this.add(buttons.get("Build Robot"), c);
        buttons.get("Build Robot").addActionListener(e -> Controller.buildRobots());

        c.gridx = 5;
        c.gridy = 0;
        this.add(buttons.get("Build Gate"), c);
        buttons.get("Build Gate").addActionListener(e -> Controller.buildGates());

        c.gridx = 6;
        c.gridy = 0;
        this.add(buttons.get("Hide"), c);
        buttons.get("Hide").addActionListener(e -> Controller.hide());

        c.gridx = 2;
        c.gridy = 1;
        this.add(buttons.get("Mine"), c);
        buttons.get("Mine").addActionListener(e -> Controller.mine());

        c.gridx = 3;
        c.gridy = 1;
        this.add(buttons.get("Drill"), c);
        buttons.get("Drill").addActionListener(e -> Controller.drill());

        c.gridx = 4;
        c.gridy = 1;
        this.add(buttons.get("Place Resource"), c);
        buttons.get("Place Resource").addActionListener(e -> Controller.placeResource());

        c.gridx = 5;
        c.gridy = 1;
        this.add(buttons.get("Move"), c);
        buttons.get("Move").addActionListener(e -> Controller.move());

        c.gridx = 6;
        c.gridy = 1;
        this.add(buttons.get("Teleport"), c);
        buttons.get("Teleport").addActionListener(e -> Controller.teleport());
    }
}