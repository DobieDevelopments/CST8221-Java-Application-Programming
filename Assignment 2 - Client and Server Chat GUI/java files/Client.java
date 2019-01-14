import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
/**
 * File name: Client.java
 * Author: John dobie(040659609), Seongyeop Jeong(040885882)
 * Course: CST8221 JAP, Lab Section: 302
 * Assignment: 2
 * Date: December 7th, 2018
 * Professor: Daniel Cormier
 * Purpose: This class is invoke the client UI so that it will be caused the run method through runnable, 
 *          which is called the dispatch thread of the system EventQueue.
 * Class list: Controller
 */

/**
 * This class is for the client to invoke the UI
 * @author John dobie(040659609), Seongyeop Jeong(040885882)
 * @version 1.0
 * @see Client
 * @since 1.8.0_181
 */
public class Client 
{
  public static void main(String[] args)
  {
    ClientChatUI chatUI = new ClientChatUI("John and Seongyeop's ClientChatUI");
    
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        chatUI.setMinimumSize(new Dimension(588, 500));
        chatUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatUI.setLocationByPlatform(true);
        chatUI.setLocationRelativeTo(null);
        chatUI.setResizable(false);

        chatUI.setVisible(true);
      }
    });
  }
}
