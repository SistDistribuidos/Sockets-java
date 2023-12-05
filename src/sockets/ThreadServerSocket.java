package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
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
            out.writeUTF("Welcome to server");
            boolean bandera = false;
            while (true) {
                String message = in.readUTF();
                bandera = (bandera || (message.equalsIgnoreCase("OPEN"))) && (!message.equalsIgnoreCase("CLOSE"));
                if (bandera) {
                    out.writeUTF("La conexion esta abierta ...");
                    long tamanio = in.readLong();
                    message = in.readUTF();
                    FileOutputStream saveElement = new FileOutputStream("/home/daniel/results/" + message);
                    long nroPaquetes = in.readLong();
                    long bytesRecibidos = 0;
                    int bytesRead;
                    byte[] buffer = new byte[4096];
                    while (bytesRecibidos < tamanio && (bytesRead = in.read(buffer, 0, (int) Math.min(buffer.length, tamanio - bytesRecibidos))) != -1) {
                        saveElement.write(buffer, 0, bytesRead);
                        bytesRecibidos += bytesRead;
                    }
                    
                    saveElement.close();
                    System.out.println("Archivo recibido satisfactoriamente ");
                } else {
                    String response = "Respuesta para " + this.uuid.toString() + "mensaje " + message;
                    out.writeUTF(response);
                }
            }

        } catch (Exception e) {
            notifyDisconnectionListeners(sc);
        }
    }

    private void notifyDisconnectionListeners(Socket socket) {
        for (SocketEventListener listener : disconnectListeners) {
            listener.onDisconnect(socket, uuid);
        }
    }
}
