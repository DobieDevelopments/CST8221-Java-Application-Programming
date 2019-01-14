import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/**
 * File name: ConnectionWrapper.java
 * Author: Seongyeop Jeong(040885882), John dobie(040659609) 
 * Course: CST8221 JAP, Lab Section: 302
 * Assignment: 2
 * Date: December 7th, 2018
 * Professor: Daniel Cormier
 * Purpose: This class it to set and get the streams and socket  
 * Class list: ConnectionWrapper
 */

/**
 * This class is to set and get the output / input streams and socket 
 * by getting the information of the socket which is from out of the class 
 * 
 * @author Seongyeop Jeong(040885882), John dobie(040659609)
 * @version 1.0
 * @see ConnectionWrapper
 * @since 1.8.0_181
 */
public class ConnectionWrapper {
  private ObjectOutputStream outputStream ;
  private ObjectInputStream inputStream ;
  private Socket socket;
  
  ConnectionWrapper(Socket socket)
  {
    this.socket = socket;
  }
  
  Socket getSocket() 
  {
    return this.socket;
  }
  
  ObjectOutputStream getOutputStream() 
  {
    return outputStream;
  }
  
  ObjectInputStream getInputStream()
  {
    return inputStream;
  }
  
  ObjectInputStream createObjectIStreams() throws IOException 
  {
    return inputStream = new ObjectInputStream(socket.getInputStream());
  }
  
  ObjectOutputStream createObjectOStreams() throws IOException 
  {
    outputStream = new ObjectOutputStream(socket.getOutputStream());
    outputStream.flush();
    return outputStream;
  }
  
  void createStreams() throws IOException
  {
    createObjectOStreams();
    createObjectIStreams();
  }
  
  public void closeConnection()throws IOException 
  {
    if(inputStream != null && !socket.isClosed())
    inputStream.close();
    
    if(socket != null && !socket.isClosed() && outputStream != null)
      outputStream.close();
    
    if(socket != null && !socket.isClosed())
    socket.close();
    
    outputStream = null;
    inputStream = null;
    socket = null;
  }
}
