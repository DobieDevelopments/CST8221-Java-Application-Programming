import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
/**
 * File name: ServerChatUI.java
 * Author: Seongyeop Jeong(040885882), John dobie(040659609)
 * Course: CST8221 JAP, Lab Section: 302
 * Assignment: 2
 * Date: December 7th, 2018
 * Professor: Daniel Cormier
 * Purpose: 
 * Class list: ServerChatUI, WindowController, Controller
 */

/**
 * This class is to connect the server with the socket
 * 
 * @author Seongyeop Jeong(040885882), John dobie(040659609)
 * @version 1.0
 * @see ServerChatUI
 * @since 1.8.0_181
 */
public class ServerChatUI extends JFrame implements Accessible{
  /** serialVersioUID = {@value} */
  private static final long serialVersionUID = 3711442076771908175L;

  JTextArea chatArea; // reference of JTextArea for the console, which is the display area 
  JTextField msgField; // reference of JTextField for message field 
  JButton sendButton; // object of the button for sending 
  ObjectOutputStream outputStream; // object of the objecOutputStream for output stream
  Socket socket; // an object of Socket
  ConnectionWrapper connection;// an object of the ConnectionWrapper for the server

  /**
   * it is default constructor for the server chat ui, set frame through the createUI method, and call runclient method 
   * @param socket it is to initialize socket
   */
  public ServerChatUI(Socket socket) throws IOException {
    this.socket = socket;
    setFrame(createUI());
    runClient();
  }
  /**
   * This method is to set ContentPane passing the pane and add WindowListener 
   * 
   * @param pane it is to pass the JPanel so that it can set the content pane
   */
  public final void setFrame(JPanel pane) {

    setContentPane(pane);
    addWindowListener(new WindowController());
  }
  /**
   * this method is to initializes the object of the ConnectionWrapper with socket. 
   * then it will take the connection to create streams so that it will initialize the outputStream filed.
   * so then it creates an runnable object by using the ChatRunnable class, and constructor
   * this will create the thread by passing the runnable reference, then it will make them all with the constructor, 
   * and will starts the thread
   */
  private void runClient() {
    connection = new ConnectionWrapper(socket);
    try {
      connection.createStreams();
      outputStream = connection.getOutputStream();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    Runnable runnable = new ChatRunnable<ServerChatUI>(this, connection);
    Thread thread = new Thread(runnable);
    thread.start();
  }
  /**
   * This method is to create the graphic user interface for the server chat 
   * @return mainPanel it is for the server chat GUI
   */
  public JPanel createUI() {
    Controller handler = new Controller();
    this.setLayout(new BorderLayout());
    // panels
    JPanel mainPanel = new JPanel();
    JPanel msgPanel = new JPanel(new BorderLayout());
    JPanel chatDisplayPanel = new JPanel(new BorderLayout());

    //////// Set Dimensions////////////////
    msgPanel.setPreferredSize(new Dimension(580, 60));
    chatDisplayPanel.setPreferredSize(new Dimension(580, 395));
    // MESSAGE Title border
    Border border = BorderFactory.createEmptyBorder(3, 3, 3, 3);
    TitledBorder title = BorderFactory
        .createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 10), "MESSAGE");
    title.setTitleJustification(TitledBorder.LEFT);
    msgPanel.setBorder(new CompoundBorder(title, border));
    // text filed
    msgField = new JTextField();
    msgField.setEditable(true);
    msgField.setHorizontalAlignment(JTextField.LEFT);
    msgField.setBackground(Color.WHITE);
    msgField.setMargin(new Insets(0, 5, 0, 0));
    msgField.setText("Type message");
    msgField.requestFocus();
    msgField.setPreferredSize(new Dimension(455, msgField.getPreferredSize().height));

    // Send button
    sendButton = new JButton("Send");
    sendButton.setPreferredSize(new Dimension(85, msgField.getPreferredSize().height));
    sendButton.setMnemonic('S');
    sendButton.setToolTipText("Send (Alt S)");
    sendButton.setEnabled(true);
    sendButton.addActionListener(handler);

    // CHAT Title border
    Border chatBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
    TitledBorder chatTitle = BorderFactory
        .createTitledBorder(BorderFactory.createLineBorder(Color.BLUE, 10), "CHAT DISPLAY");
    chatTitle.setTitleJustification(TitledBorder.CENTER);
    chatDisplayPanel.setBorder(new CompoundBorder(chatTitle, chatBorder));
    // CHAT text field
    chatArea = new JTextArea(30, 45);
    chatArea.setEditable(false);
    // chatArea.setHorizontalAlignment(JTextField.LEFT);
    chatArea.setBackground(Color.WHITE);
    chatArea.setMargin(new Insets(0, 5, 0, 0));
    chatArea.requestFocus();

    // scroll
    JScrollPane scrollPane = new JScrollPane(chatArea);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    // add components
    msgPanel.add(msgField, BorderLayout.WEST);
    msgPanel.add(sendButton, BorderLayout.EAST);
    chatDisplayPanel.add(scrollPane);
    mainPanel.add(msgPanel);
    mainPanel.add(chatDisplayPanel);

    // this.getContentPane().add(scrollPane);
    return mainPanel;
  }
  /**
   * This class is to extends window adapter
   * 
   * @author Seongyeop Jeong(040885882), John dobie(040659609)
   * @version 1.0
   * @see WindowController
   * @since 1.8.0_181
   */
  private class WindowController extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
      super.windowClosing(e);
      System.out.println("ServerUI Window closing!");
      try {
        if(socket != null && !socket.isClosed())
        outputStream.writeObject(ChatProtocolConstants.DISPLACMENT +ChatProtocolConstants.CHAT_TERMINATOR +ChatProtocolConstants.LINE_TERMINATOR);
        System.out.println("Closing Chat!");
        connection.closeConnection();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      finally
      {
        dispose();
      }
      System.out.println("Chat closed!");
      System.exit(0);
    }
    
    @Override 
    public void windowClosed(WindowEvent e) 
    {
      super.windowClosed(e);
      System.out.println("Server UI Closed!");
    }
  }
  /**
   * This class is to implement ActionListener
   * 
   * @author Seongyeop Jeong(040885882), John dobie(040659609)
   * @version 1.0
   * @see Controller
   * @since 1.8.0_181
   */
  private class Controller implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      if(e.getSource() == sendButton)
      send();
    }
    /**
     * This method is to get the text from message field which assigns it already
     * as it appends the display by adding the line terminator and output stream 
     */
    private void send()
    {
      String sendMessage = msgField.getText();
      getDisplay().append(sendMessage + ChatProtocolConstants.LINE_TERMINATOR);
      try {
        if(socket != null && !socket.isClosed())
        outputStream.writeObject(ChatProtocolConstants.DISPLACMENT + sendMessage + ChatProtocolConstants.LINE_TERMINATOR);
        outputStream.flush();
      } catch (IOException e) {
        getDisplay().append(e.getMessage());
      }
    }
  }

  @Override
  public JTextArea getDisplay() {
    return chatArea;
  }

  @Override
  public void closeChat() {
    try {
      connection.closeConnection();
    } catch (IOException e) {
      e.printStackTrace();
    }
    dispose();
  }
}
