import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
    public ServerSocket serverSocket; // Socket del Server
    public ConnectionsManager connectionsManager; // Gestore delle connessioni create

    public ServerTCP(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.connectionsManager = new ConnectionsManager();
    }

    // Accetta una connessione dal Client
    public Socket acceptConnection() throws IOException {
        Socket socket = serverSocket.accept();  // Accetta la connessione
        System.out.println("Richiesta del Client " + socket.getInetAddress().toString() + ":connect");
        if (connectionsManager.add(socket))
            return socket;
        return null;
    }

    // Invia un messaggio a un Client specifico
    public void send(String message, Socket connection) throws IOException {
        PrintWriter out = new PrintWriter(connection.getOutputStream(), true); // Crea un PrintWriter per inviare dati al client
        out.println(message);  // Invia il messaggio al client
    }

    // Invia un messaggio a tutti i client
    public void sendAtAll(String message) throws IOException {
        for (Socket connection : connectionsManager.connections ) 
            send(message, connection);
    }


    // Chiude le connessioni e il Server
    public void close() throws IOException {
        connectionsManager.closeAll(); // Chiude tutte le connesioni con i client
        if (!serverSocket.isClosed()) 
            serverSocket.close(); // Chiude la socket del server
    }

    public Socket getPlayer1Connection() {
        return connectionsManager.connections.size() > 0 ? connectionsManager.connections.get(0) : null;
    }

    public Socket getPlayer2Connection() {
        return connectionsManager.connections.size() > 1 ? connectionsManager.connections.get(1) : null;
    }
}
