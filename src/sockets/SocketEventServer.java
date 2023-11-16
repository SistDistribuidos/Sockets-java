/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel
 */
public class SocketEventServer extends Thread {
    
    private ServerSocket serverSocket;
    private List<SocketEventListener> connectionListeners = new ArrayList<>();
    
    public SocketEventServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    
    public void addConnectionListener(SocketEventListener listener) {
        connectionListeners.add(listener);
    }
    
    public void run() {
        while (true) {
            try {
                Socket connection = serverSocket.accept();
                notifyConnectionListeners(connection);
            } catch (IOException ex) {
                Logger.getLogger(SocketEventServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void notifyConnectionListeners(Socket socket) {
        for (SocketEventListener listener : connectionListeners) {
            listener.onConnectionEstablished(socket);
        }
    }

}
