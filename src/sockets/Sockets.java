package sockets;

/**
 *
 * @author daniel
 */
public class Sockets {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SocketServer socket = new SocketServer(5000);
        socket.addConnectionListener(new MyConnectionListener());
        socket.startServer();
    }
    
}
