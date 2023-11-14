import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client extends Thread {
    public Player player; // Il giocatore associato a questo thread
    public Socket socketClient; // Il socket del client
    public ClientRequest clientRequest; // Richiesta del client

    public Client(Socket socketClient, Player player) {
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
}
