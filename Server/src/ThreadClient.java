import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadClient extends Thread {
    public Player player; // Il giocatore associato a questo thread
    public Socket socketClient; // Il socket del client
    public ClientRequest clientRequest; // Richiesta del client

    public ThreadClient(Socket socketClient, Player player) {
        this.player = player; 
        this.socketClient = socketClient; 
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()))) {
            // Continua a leggere le richieste dal client finché la socket non viene chiusa
            while (!socketClient.isClosed()) 
                clientRequest = new ClientRequest(in.readLine()); // Gestisce la richiesta del client
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

    // Metodo per inviare un messaggio al client
    public void send(String message) throws IOException {
        // Crea un PrintWriter per inviare dati al client
        try (PrintWriter out = new PrintWriter(socketClient.getOutputStream(), true)) {
            // Invia il messaggio al client
            out.println(message);
        }
    }
}
