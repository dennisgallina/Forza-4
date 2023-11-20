import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Player extends Thread {
    public String name; // Nome del giocatore
    public String pawnsColor; // Colore delle pedine
    public Socket connection; // Il socket del client
    public ClientRequest clientRequest; // Richiesta del client

    public Player(String name, String pawnsColor, Socket connection) {
        this.name = name;
        this.pawnsColor = pawnsColor;
        this.connection = connection; 
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            // Continua a leggere le richieste dal client finché la socket non viene chiusa
            while (!connection.isClosed()) {
                clientRequest = new ClientRequest(in.readLine()); // Gestisce la richiesta del client
                if (clientRequest.command != null)
                    System.out.println(clientRequest.toConsole());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
}
