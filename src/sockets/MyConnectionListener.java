package sockets;

import java.net.Socket;

/**
 *
 * @author daniel
 */
class MyConnectionListener implements SocketEventListener {

    @Override
    public void onConnectionEstablished(Socket socket) {
        System.out.println("New user connected " + socket.getLocalAddress());
    }
}
