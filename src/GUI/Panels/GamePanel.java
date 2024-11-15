package GUI.Panels;

import App.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.io.IOException;
import Controller.Controller;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Játékpanelt megvalósító osztály
 */
public class GamePanel extends JPanelWithBackground {
    private final int size = 30;                                                                ///< Az aszteroidák mérete
    private AsteroidField af;                                                                   ///< Aszteroidamező
    private JButton currentAsteroidButton;                                                      ///< A jelenlegi aszteroida gombja
    HashMap<Place, JButton> placeButtons = new HashMap<>();                                     ///< A helyeket és gombjaikat tároló lista
    HashMap<Place, HashMap<Position,Color>> orbit= new HashMap<>();                             ///< A pályákat tároló lista, amiben a helyek és a pályájukat jelző pozíciók, illetve színek vannak.
    
    private Icon astBasicIcon = new ImageIcon("resources/Asteroids/ast_basic.png");             ///< Fúratlan aszteroida ikonja
    private Icon astCoalIcon = new ImageIcon("resources/Asteroids/ast_coal.png");               ///< Szenet tartalmazó aszteroida ikonja
    private Icon astEmptyIcon = new ImageIcon("resources/Asteroids/ast_empty.png");             ///< Üres aszteroida ikonja
    private Icon astIceIcon = new ImageIcon("resources/Asteroids/ast_ice.png");                 ///< Jeget tartalmazó aszteroida ikonja
    private Icon astIronIcon = new ImageIcon("resources/Asteroids/ast_iron.png");               ///< Vasat tartalmazó aszteroida ikonja
    private Icon astUranIcon = new ImageIcon("resources/Asteroids/ast_uran.png");               ///< Uránt tartalmazó aszteroida ikonja
    private Icon gateIcon = new ImageIcon("resources/GameObjects/teleportgate_gamepanel.png");  ///< Teleportkapu ikonja

    ArrayList<Color> colors = new ArrayList<>();                                                ///< Színeket tároló lista
    Random random = new Random();                                                               ///< Random szám generálásához

    /**
     * Játékpanel konstruktorja
     * @throws IOException - ha sikertelen a bemenet/kimenet
     */
    public GamePanel() throws IOException{
        super("resources/Backgrounds/Game.png", 1000, 700);
        setLayout(null);
        Icon sunIcon = new ImageIcon("resources/GameObjects/sun.png");
        JLabel sun = new JLabel(sunIcon);

        this.add(sun);
        sun.setBounds(Sun.SunPos().getX()-25, Sun.SunPos().getY()-25, 50, 50);
        currentAsteroidButton = null;

        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.CYAN);
        colors.add(Color.GREEN);
        colors.add(Color.MAGENTA);
        colors.add(Color.ORANGE);
        colors.add(Color.PINK);
        colors.add(Color.YELLOW);
    }

    /**
     * Az aszteroidapálya kirajzolásáért felelős metódus
     * @param g - grafikus paraméter
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(orbit.size()>0)
        {
            for(Place p : orbit.keySet())
            {
                HashMap<Position,Color> hm = orbit.get(p);
                for(Position pos : hm.keySet())
                {
                    g.setColor(hm.get(pos));
                    g.drawOval(pos.getX(), pos.getY(), 5, 5);
                }
            }
        }
    }

    /**
     * A helyek gombjainak beállítását végző metódus
     * @param button - gomb, ami a helyet jelképezi
     * @param p - hely, amit egy gomb fog jelképez
     */
    private void buttonSetup(JButton button, Place p){
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setBounds(p.getPosition().getX() - size / 2, p.getPosition().getY() - size / 2, size, size);
        placeButtons.put(p, button);
        this.add(button);
    }

    /**
     * A aszteroidapályát beállító metódus
     * @param p - beállítandó hely
     */
    private void orbitSetUp(Place p) 
    {
        HashMap<Position,Color> l = new HashMap<>();
        Color color = colors.get(random.nextInt(colors.size()));
        for(int i = 0; i < p.getPeriod(); i++){
            Position pos = new Position(p.getPosition().getX(), p.getPosition().getY());
            p.Step();
            l.put(pos,color);
        }
        orbit.put(p,l);        
    }

    /**
     * Az aszteroidamező beállítását végző metódus
     * @param af - aszteroidamező
     */
    public void setAsteroidField(AsteroidField af){
        this.af = af;
        for (Place p : af.getPlaces()) {
            if(p instanceof Asteroid)
                orbitSetUp(p);
        }
        for(Place p : af.getPlaces()){
            JButton button=null;
            if(p instanceof Asteroid){
                Asteroid a = (Asteroid)p;
                if (a.getHardness() == 0) {
                    if(a.getResource() == null)
                        button = new JButton(astEmptyIcon);
                    else if(a.getResource() instanceof Uran){
                        button = new JButton(astUranIcon);
                    }
                    else if(a.getResource() instanceof Coal)
                        button = new JButton(astCoalIcon);
                    else if(a.getResource() instanceof Ice)
                        button =  new JButton(astIceIcon);
                    else if(a.getResource() instanceof Iron)
                        button = new JButton(astIronIcon);
                    else
                        button = new JButton("Whut the fuck");
                } else{
                    button = new JButton(astBasicIcon);
                }
                button.addActionListener(e -> selectPlaceListener(e, p));
            } else if (p instanceof Gate){
                button = new JButton(gateIcon);
                button.addActionListener(e -> selectPlaceListener(e, p));
            } else
                button = new JButton("howTheFuck");
            buttonSetup(button, p);

        }
    }

    /**
     * Szomszédos aszteroidák kiemelését végző metódus
     * @param neighbours - szomszédos aszteroidák listája
     */
    public void HighlightAst(List<Place> neighbours){
        for(Place a : neighbours){
            placeButtons.get(a).setBorder(new LineBorder(Controller.players.get(Controller.getGame().getCurrentPlayer()), 2));
            placeButtons.get(a).setBorderPainted(true);
        }
    }

    /**
     * Újraállítja az aszteroid
     */
    public void resetHighlight()  {
        for  (JButton b : placeButtons.values())  {
            b.setBorderPainted(false);
        }
    }

    /**
     * A helyek frissítéséért felelős metódus
     */
    public void update() {
        for(Place p : af.getPlaces()) {
            if (p instanceof Asteroid) {
                Asteroid a = (Asteroid) p;
                if (a.getHardness() == 0) {
                    if (a.getResource() == null)
                        placeButtons.get(a).setIcon(astEmptyIcon);
                    else if (a.getResource() instanceof Uran) {
                        placeButtons.get(a).setIcon(astUranIcon);
                    } else if (a.getResource() instanceof Coal)
                        placeButtons.get(a).setIcon(astCoalIcon);
                    else if (a.getResource() instanceof Ice)
                        placeButtons.get(a).setIcon(astIceIcon);
                    else if (a.getResource() instanceof Iron)
                        placeButtons.get(a).setIcon(astIronIcon);
                    else
                        placeButtons.get(a).setText("WhutThuPlum");
                } else {
                    placeButtons.get(a).setIcon(astBasicIcon);
                }
            } else if (p instanceof Gate) {
                if (placeButtons.get(p) == null) {
                    JButton b = new JButton(gateIcon);
                    b.addActionListener(e -> selectPlaceListener(e, p));
                    buttonSetup(b, p);
                    b.repaint();
                }
            }
            placeButtons.get(p).setBounds(p.getPosition().getX() - size / 2, p.getPosition().getY() - size / 2, size, size);
        }
    }

    /**
     * A helyek kiválasztásának listenerje
     * @param e - esemény
     * @param p - kiválasztott hely
     */
    private void selectPlaceListener(ActionEvent e, Place p)
    {
        if(!Controller.getisMoving())
        {
            Controller.selectPlace(p);
            if(currentAsteroidButton != null)
                currentAsteroidButton.setBorderPainted(false);
            JButton jbutton = (JButton) (e.getSource());
            jbutton.setBorder(new LineBorder(Controller.players.get(Controller.getGame().getCurrentPlayer()), 2));
            currentAsteroidButton = jbutton;
            jbutton.setBorderPainted(true);
        }
        else
        {
            Controller.selectPlace(p);
            currentAsteroidButton.setBorder(new LineBorder(Controller.players.get(Controller.getGame().getCurrentPlayer()), 2));
            currentAsteroidButton.setBorderPainted(true);
        }
    }

    /**
     * A helyek törlődésének leellenőrzéséért felelős metódus
     */
    public void checkDelete(){
        Iterator<Place> i = placeButtons.keySet().iterator();
        while(i.hasNext()) {
            Place p = i.next();
            if(!(af.getPlaces().contains(p))){
                this.remove(placeButtons.get(p));
                if(p instanceof Asteroid)
                    orbit.remove(p);
                i.remove();
            }
        }
        this.repaint();
    }
    
    /**
     * A napkitörés vizualizálásáért felelős metódus
     * @param p - hely, amit napvihar ér
     */
    public void atSolarStorm(Place p)
    {
        placeButtons.get(p).setBorder(new LineBorder(Color.RED, 4));
        placeButtons.get(p).setBorderPainted(true);
    }
    
}
