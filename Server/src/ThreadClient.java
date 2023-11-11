import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadClient extends Thread {
    private Socket socketClient; // Socket inviata dal Client
    private Player player; // Player da gestire
    private RichiestaDalClient richiestaDalClient;

    public ThreadClient(Socket socketClient, Player player) {
        this.socketClient = socketClient;
        this.player = player;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            PrintWriter out = new PrintWriter(socketClient.getOutputStream(), true);
    
            String request;
            while ((request = in.readLine()) != null) {
                // Gestisce 
                gestioneRisposta(new RichiestaDalClient(request));
    
                // Genera una risposta in base al comando e alla posizione
                String response = "";
                out.println(response);
            }
    
            in.close();
            out.close();
            socketClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }      

    public void gestioneRisposta(RichiestaDalClient richiestaDalClient) {
        if (richiestaDalClient.comando.equals("insert")) {
            
        }
    }
}