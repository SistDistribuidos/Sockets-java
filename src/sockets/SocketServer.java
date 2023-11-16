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
public class SocketServer implements SocketEventListener{
    private int port;
    private Map<UUID, ThreadServerSocket> listClients = new HashMap<>();
    private SocketEventServer socketEvent;
    private ServerSocket servidor;
    
    public SocketServer() {
        this.port = 8000;
    }
    
    public SocketServer(int port) {
        this.port = port;
    }
    

    
    public void startServer() {     
        try {
            servidor = new ServerSocket(port);
            System.out.println("Servidor Iniciado");
            socketEvent = new SocketEventServer(servidor);
            socketEvent.addConnectionListener(this);
            socketEvent.start();
        } catch (IOException ex) {
            System.out.println("Ocurrio un error durante la conexion  "+ ex.getMessage());
        }
    }
    


    @Override
    public void onConnectionEstablished(Socket socket) {
        UUID uuid = UUID.randomUUID();
        ThreadServerSocket client = new ThreadServerSocket(socket, uuid);
        client.addDisconnectionListener(this);
        listClients.put(uuid, client);
        client.start();
        
        System.out.println("Numero de usuarios "+listClients.size());
    }

    @Override
    public void onDisconnect(Socket socket, UUID uuid) {
        listClients.remove(uuid);
        
        System.out.println("Usuario desconectado ,Nro de usuarios " + listClients.size());
    }
}
