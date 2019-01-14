import javax.swing.JTextArea;
/**
 * File name: Accessible.java
 * Author: Seongyeop Jeong(040885882), John dobie(040659609)
 * Course: CST8221 JAP, Lab Section: 302
 * Assignment: 2
 * Date: December 7th, 2018
 * Professor: Daniel Cormier
 * Purpose: This class is the interface to provide the interface to the ChatRunnable.java
 * Class list: Controller
 */

/**
 * This class is the interface to provide the interface to the ChatRunnable.java
 * @author Seongyeop Jeong(040885882), John dobie(040659609)
 * @version 1.0
 * @see Accessible
 * @since 1.8.0_181
 */
public interface Accessible {
  JTextArea getDisplay();
  void closeChat();
}
