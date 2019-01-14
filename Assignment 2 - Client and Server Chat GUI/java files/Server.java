import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
/**
 * File name: Server.java
 * Author: Seongyeop Jeong(040885882), John dobie(040659609)
 * Course: CST8221 JAP, Lab Section: 302
 * Assignment: 2
 * Date: December 7th, 2018
 * Professor: Daniel Cormier
 * Purpose: This is for the server.java
 * Class list: Server
 */

/**
 * This class is to set and get the output / input streams and socket 
 * by getting the information of the socket which is from out of the class 
 * 
 * @author Seongyeop Jeong(040885882), John dobie(040659609)
 * @version 1.0
 * @see Server
 * @since 1.8.0_181
 */
public class Server {
  public static void main(String[] args) throws IOException {
    int port = 65535;
    if (args.length > 0) {
      try {
        port = Integer.parseInt(args[0]);
        System.out.println("Using port: " + port);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Using default port: " + port);
    }

    ServerSocket serverSocket = null;
    int friend = 0;
    try {
      serverSocket = new ServerSocket(port);
      while (true) {
        Socket socket = serverSocket.accept();
        if (socket.getSoLinger() != -1)
          socket.setSoLinger(true, 5);
        if (!socket.getTcpNoDelay())
          socket.setTcpNoDelay(true);
        System.out.println("Connecting to a client Socket[addr=" + socket.getInetAddress()
            + ", port=" + socket.getPort() + ", localport=" + socket.getLocalPort() + "]");
        friend++;
        final String title = "John and Seongyeop's friend " + friend;
        Server server = new Server();
        server.launchClient(socket, title);
      }
    } catch (IOException e) {
      System.out.println("Failed to create a ServerSocket at the port: " + port);
    }
  }
  /**
   * This method is to invoke the object of the serverChatUI
   * 
   * @param socket it is the socket object which is passed through the object of the serverChatUI
   * @param title it is the string variable to set the title for the ServerChatUI
   */
  private void launchClient(Socket socket, String title) throws IOException {
    ServerChatUI chatUI = new ServerChatUI(socket);
    chatUI.setTitle(title);

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
