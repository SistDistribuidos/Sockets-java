package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author daniel
 */
public class ThreadServerSocket extends Thread {
    
    private DataInputStream in;
    private DataOutputStream out;
    private Socket sc;
    
    public ThreadServerSocket(Socket sc) {
        this.sc = sc;
    }
    
    @Override
    public void run() {
        try {
                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());
  
                String mensaje = in.readUTF();
                System.out.println(mensaje);
                
        } catch(Exception e) {
            
        }
    }
}
