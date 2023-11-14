package sockets;

import java.net.Socket;

/**
 *
 * @author daniel
 */
public interface SocketEventListener {
    void onConnectionEstablished(Socket socket);
}
