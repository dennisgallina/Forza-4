import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadClient extends Thread {
    private Socket socketClient; // Socket inviata dal Client
    private RichiestaDalClient richiestaDalClient;

    public ThreadClient(Socket socketClient) {
        this.socketClient = socketClient;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            PrintWriter out = new PrintWriter(socketClient.getOutputStream(), true);
    
            String request;
            while ((request = in.readLine()) != null) {
                richiestaDalClient = new RichiestaDalClient();
    
                String response = "Risposta al client"; // Sostituisci con la tua risposta
                out.println(response);
            }
    
            in.close();
            out.close();
            socketClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
}