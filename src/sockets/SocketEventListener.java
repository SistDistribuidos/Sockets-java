package sockets;

import java.net.Socket;
import java.util.UUID;

/**
 *
 * @author daniel
 */
public interface SocketEventListener {
    void onConnectionEstablished(Socket socket);
    void onDisconnect(Socket socket, UUID uuid);
}
