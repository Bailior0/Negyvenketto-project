package GUI.Panels;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Image;
import java.awt.Graphics;

/**
 * Háttérrel rendelkező panelt megvalósító osztály
 */
public class JPanelWithBackground extends JPanel {
  private Image backgroundImage;  ///< Háttér képe
  private int width;              ///< Panel szélessége
  private int height;             ///< Panel magassága

  /**
    * Panel konstruktora
    * @param filename - háttérkép file neve
    * @param width - panel szélessége
    * @param height - panel magassága
    * @throws IOException - képfile betöltésének sikertelenségét jelző kivétel
    */
  public JPanelWithBackground(String filename, int width, int height) throws IOException {
    this.height = height;
    backgroundImage = ImageIO.read(new File(filename));
    this.width = width;
  }

  /**
   * A háttérkép kirajzolásáért felelős metódus
   * @param g - grafikus paraméter
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(backgroundImage, 0, 0, width, height, this);
  }
}
