import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadClient extends Thread {
    private Socket socketClient; // Il socket del client
    private Player player; // Il giocatore associato a questo thread
    private Game game; // Il gioco a cui partecipa il giocatore

    public ThreadClient(Socket socketClient, Player player, Game game) {
        this.socketClient = socketClient; 
        this.player = player; 
        this.game = game; 
    }

    public void run() {
        try {
            // Crea un BufferedReader per leggere i dati in arrivo dal client
            BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        
            String request;
            // Continua a leggere le richieste dal client finché non riceve "disconnect" (cioè il client si disconnette)
            while ((request = in.readLine()) != "disconnect") {
                // Gestisce la richiesta del client
                manageRequest(new RichiestaDalClient(request));
            }
        
            send("disconnection confirmed");

            // Chiude il BufferedReader e il socket del client
            in.close();
            socketClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

    // Metodo per inviare un messaggio al client
    public void send(String message) throws IOException {
        // Crea un PrintWriter per inviare dati al client
        PrintWriter out = new PrintWriter(socketClient.getOutputStream(), true);
        // Invia il messaggio al client
        out.println(message);
    }

    // Metodo per gestire le richieste dal client
    public void manageRequest(RichiestaDalClient richiestaDalClient) throws IOException {
        // Se il comando è "insert", inserisce una pedina nel gioco
        if (richiestaDalClient.command.equals("insert")) {
            game.insertPawn(player.color, richiestaDalClient.positionX);
        } 
    }
}
