package Controller;

import GUI.Frames.AsteroidFrame;
import GUI.Frames.MainFrame;

/**
 * A main osztály, ahonnan a további műveletek indulnak
 */
public class Main {

    /**
     * Legfőbb metódus
     * @param args - parancssori argumentumok
     */
    public static void main(String[] args){
        MainFrame frame = new MainFrame();
        AsteroidFrame asteroidFrame = new AsteroidFrame();
        Controller.setAsteroidFrame(asteroidFrame);
        Controller.setMainFrame(frame);
        frame.setVisible(true);
    }
}