package calculator;

/*
 * File Header File name: [CalculatorSplashScreen.java ] 
 * Author: [ John Dobie, 040 659 609] 
 * Course: CST8221 _ Java Application Programming 
 * Lab Section: [302] 
 * Assignment: [1] 
 * Date: [October 17th, 2018] 
 * Professor: [Daniel Cormier] 
 * Purpose: [To display a splashscreen for the user while the application loads up]
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 * This class displays the splash screen loading gif for our calculator app.
 * 
 * @author John Dobie
 * @version 1.0
 * @see calculator
 * @since 1.8.0_121
 */
public class CalculatorSplashScreen extends JWindow {
  
  /**
   * {@value}
   */
  private static final long serialVersionUID = -7621485825045810905L;

  /**
   * 5000
   */
  private final int duration;
  /**
   * 350
   */
  private final int width;
  /**
   * 350
   */
  private final int height;
  // 350 width and height

  /**
   * The constructor for our CalculatorSplashScreen class, which sets up our splash screen with a
   * duration and it's dimensions.
   * 
   * @param duration how long the splashscreen should display for.
   * @param width the width of the splashscreen window
   * @param height the height of the splashscreen window
   */
  public CalculatorSplashScreen(int duration, int width, int height) {
    this.duration = duration;
    this.width = width;
    this.height = height;
  }

  /**
   * The display method for showing our splash screen, setting the dimensions with previously
   * allocated values, and setting up our animated gif to display correctly.
   */
  public void display() {
    Dimension screen = Toolkit.getDefaultToolkit()
        .getScreenSize(); /*
                           * get our screen size and store it in a variable for needed access
                           */
    int x = (screen.width - width) / 2; // the x coordinate for our window location
    int y = (screen.height - height) / 2; // the y coordinate for our window location
    setBounds(x, y, width, height);

    JPanel panel = new JPanel(new BorderLayout()); // the panel for our splashscreen

    JLabel name = new JLabel("John Dobie, 040 659 609",
        JLabel.CENTER); /*
                         * the label for my name and student ID
                         */
    name.setFont(new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 14));
    name.setForeground(Color.WHITE);
    name.setBackground(new Color(38, 38, 38));
    name.setOpaque(true);

    JLabel image = new JLabel(new ImageIcon(getClass()
        .getResource("splash.gif"))); /*
                                       * the animated image we will use for the splashscreen
                                       */

    panel.add(image, BorderLayout.CENTER);
    panel.add(name, BorderLayout.SOUTH);
    panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));
    setContentPane(panel);
    setVisible(true);

    int cycles = 100; // number of cycles to run(animate) the opacity fade over
    float timer = 0.0f, // initial sleep timer value
        timerIncrement = duration / cycles, // increment the timer by a set amount each cycle
        alphaMax = 1.0f, // maximum alpha Value
        alpha = 0.0f, // initial alpha value
        alphaIncrement = 1 / (float) cycles; // increment alpha value by 1% each cycle
    this.setOpacity(alpha); // initialize opacity to zero
    while (timer < duration) { // while we haven't reached the specified splashscreen duration
      alpha += alphaIncrement;
      // sleep application for duration while it 'loads'
      try {
        Thread.sleep(duration / cycles);
      } catch (InterruptedException e) {
        /* log an error here? *//* e.printStackTrace(); */}
      if (alpha <= alphaMax)
        this.setOpacity(alpha);
      timer += timerIncrement;
    }

    // destroy the window and release all resources
    dispose();
  }
}
