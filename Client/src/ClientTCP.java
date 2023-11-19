import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTCP extends Thread{
    private Socket connection; // Connessione del Client
    private BufferedReader input; // Ricezione Dati
    private PrintWriter output; // Invio Dati
    public String serverResponse; // Risposta ricevuta dal Server

    public ClientTCP(String serverIp, int serverPort) throws IOException {
        this.connection = new Socket(serverIp, serverPort); // Aggiunta dei dati del Server
        this.input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        this.output = new PrintWriter(connection.getOutputStream(), true);
    }

    public void run() {
        // Resta in ascolto del Server finché la Connessione è attiva
        while (isConnected()) {
            try {
                serverResponse = input.readLine(); // Riceve dati dal Server
                if (serverResponse != null)
                    System.out.println("Risposta dal Server:" + serverResponse);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    } 

    public void connect() throws IOException {
        send("connect");
    }

    // Invia dei dati al Server
    public void send(String data) throws IOException {
        output.println(data);
    }

    // Chiude la Connessione col Server
    public void close() throws IOException {
        input.close();
        output.close();
        connection.close();
    }

    public boolean isConnected() {
        if (connection.isConnected())
            return true;
        else 
            return false;
    }

    public void cleanResponse() {
        serverResponse = null;
    }
}
