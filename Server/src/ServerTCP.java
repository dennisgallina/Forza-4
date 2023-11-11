import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
    // Dichiarazione di un ServerSocket
    private ServerSocket serverSocket;

    // Costruttore della classe ServerTCP
    public ServerTCP(int porta) throws IOException {
        // Inizializza il ServerSocket sulla porta specificata
        this.serverSocket = new ServerSocket(porta);
    }

    // Metodo per accettare una connessione dal client
    public Socket accettaConnessione() throws IOException {
        // Accetta una connessione dal client e la restituisce
        return serverSocket.accept();
    }

    // Metodo per chiudere il server
    public void chiudi() throws IOException {
        // Chiude il ServerSocket
        serverSocket.close();
    }
}