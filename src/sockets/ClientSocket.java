package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author daniel
 */
public class ClientSocket {
    
    private String host;
    private int port;
    private DataInputStream in;
    private DataOutputStream out;
    private Socket sc;
    
    
    public ClientSocket(String host, int port) {
        this.port = port;
        this.host = host;
    }
    
    public void connect() {
        try {
            sc = new Socket(host, port);
            
            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());
            
            String message ="CLiente X. Hola";
            out.writeUTF(message);
            
            message = in.readUTF();
            System.out.println(message);
            
        } catch (IOException ex) {
            System.out.println("Error durante intento de conexion");
        }
    }
    
    public void disconnect() {
        try {
            sc.close();
        } catch (Exception e) {
            System.out.println("Error durante la conexion");
        }
    }

    public static void main(String args[]) {
        ClientSocket client = new ClientSocket("127.0.0.1",5000);
        client.connect();
    }

}
