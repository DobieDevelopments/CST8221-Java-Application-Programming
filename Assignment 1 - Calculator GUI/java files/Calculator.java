package calculator;

/*
 * File Header File name: [Calculator.java ] 
 * Author: [ John Dobie, 040 659 609] 
 * Course: CST8221 _ Java Application Programming 
 * Lab Section: [302] 
 * Assignment: [1] 
 * Date: [October 17th, 2018]
 * Professor: [Daniel Cormier] 
 * Purpose: [The core of our program that will call methods for running the splashscreen and calculator app]
 */

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * This class runs our program, creating the calculator object as necessary.
 * 
 * @author John Dobie
 * @version 1.0
 * @see calculator
 * @since 1.8.0_121
 */
public class Calculator {
  public static void main(String[] args) {
    short ss_duration =
        5000; /*
               * the initial duration for the splashscreen to display (in milliseconds)
               */
    short ss_width,
        ss_height = ss_width = 350; /*
                                     * the height and width of the splashscreen (in pixels)
                                     */

    // check if the user specified an alternate splashscreen duration
    try {
      if (args.length > 0)
        ss_duration = (short) Integer.parseInt(args[0]);
    } catch (NumberFormatException e) {
      System.err.println("Invalid number format, the default of 5 seconds will be used.");
    }

    CalculatorSplashScreen splashScreen = /*
                                           * the splashscreen object for 'loading' our application
                                           * behind
                                           */
        new CalculatorSplashScreen(ss_duration, ss_width, ss_height);
    splashScreen.display();

    CalculatorViewController calculator =
        new CalculatorViewController(); /*
                                         * our calculator application object
                                         */

    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        JFrame frame =
            new JFrame("Calculator.exe"); /*
                                           * the frame for our calculator to be displayed on
                                           */
        frame.setMinimumSize(new Dimension(380, 520));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);

        // add our calculator gui to the window's frame
        frame.add(calculator);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      }
    });
  }
}
