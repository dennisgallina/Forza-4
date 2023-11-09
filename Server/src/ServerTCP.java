// Importa le classi necessarie
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// Definisce la classe ServerTCP
public class ServerTCP {
    // Dichiarazione di un ServerSocket
    private ServerSocket serverSocket;

    // Costruttore della classe ServerTCP
    public ServerTCP(int porta) throws IOException {
        // Inizializza il ServerSocket sulla porta specificata
        this.serverSocket = new ServerSocket(porta);
    }

    // Metodo per accettare le connessioni dei client
    public void accettaConnessioni() throws IOException {
        // Continua ad accettare connessioni finché il server è in esecuzione
        while (true) {
            // Accetta una connessione dal client
            Socket clientSocket = serverSocket.accept();
            // Crea un nuovo ThreadClient per gestire la connessione
            ThreadClient threadClient = new ThreadClient(clientSocket);
            // Avvia il ThreadClient
            threadClient.start();
        }
    }

    // Metodo per chiudere il server
    public void chiudi() throws IOException {
        // Chiude il ServerSocket
        serverSocket.close();
    }
}
