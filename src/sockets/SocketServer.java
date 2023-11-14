/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author daniel
 */
public class SocketServer {
    private int port;
    private List<SocketEventListener> connectionListeners = new ArrayList<>();
    private Map<UUID, Socket> listClients = new HashMap<>();
    
    public SocketServer() {
        this.port = 8000;
    }
    
    public SocketServer(int port) {
        this.port = port;
    }
    
    public void addConnectionListener(SocketEventListener listener) {
        connectionListeners.add(listener);
    }
    
    public void startServer() {
            ServerSocket servidor = null;
            Socket sc = null;
            
        try {
            servidor = new ServerSocket(port);
            System.out.println("Servidor Iniciado");
              
            while(true){
                sc = servidor.accept();
                notifyConnectionListeners(sc);
                listClients.put(UUID.randomUUID(), sc);
                ThreadServerSocket client = new ThreadServerSocket(sc);
                client.start();
                
                System.out.println("El numero de clientes es "+listClients.size());
            }
            
        } catch (IOException ex) {
            System.out.println("Ocurrio un erro durante la conexion  "+ ex.getMessage());
        }
    }
    
    private void notifyConnectionListeners(Socket socket) {
        for (SocketEventListener listener : connectionListeners) {
            listener.onConnectionEstablished(socket);
        }
    }
}
