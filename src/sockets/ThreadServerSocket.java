package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author daniel
 */
public class ThreadServerSocket extends Thread {
    
    private DataInputStream in;
    private DataOutputStream out;
    private Socket sc;
    private UUID uuid;
    private List<SocketEventListener> disconnectListeners = new ArrayList<>();
    
    public ThreadServerSocket(Socket sc, UUID uuid) {
        this.sc = sc;
        this.uuid = uuid;
    }
    
    public void addDisconnectionListener(SocketEventListener listener) {
      disconnectListeners.add(listener);
    }
    
    @Override
    public void run() {
        try {
                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());
  
                String mensaje = in.readUTF();
                System.out.println(mensaje);
                
                mensaje = in.readUTF();
                System.out.println(mensaje);
                
        } catch(Exception e) {
            System.err.println("La conexion se ha cerrado ..");
            notifyDisconnectionListeners(sc);
        }
    }
    
    private void notifyDisconnectionListeners(Socket socket) {
        for (SocketEventListener listener : disconnectListeners) {
            listener.onDisconnect(socket, uuid);
        }
    }
}
