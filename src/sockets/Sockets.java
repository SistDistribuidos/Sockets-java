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
        SocketServer socket = new SocketServer(Integer.parseInt(args[0]));
        socket.startServer();
    }
    
}
