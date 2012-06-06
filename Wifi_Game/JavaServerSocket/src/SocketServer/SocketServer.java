package SocketServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: richard
 * Date: 5/31/12
 * Time: 10:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class SocketServer {
    private static ServerSocket serverSocket=null;

    public static void main(String[] args){
        try{
            serverSocket = new ServerSocket(4444);
        }catch(IOException e){
            System.out.println("Couldn't listen to port 4444");
            System.exit(-1);
        }

        Socket clientSocket = null;
        try{
            clientSocket = serverSocket.accept();
        }catch (IOException e){
            System.out.println("Accept failed on port 4444");
            System.exit(-1);
        }

    }
}
