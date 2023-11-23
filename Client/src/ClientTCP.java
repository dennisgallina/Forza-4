import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientTCP extends Thread {
    private Socket connection; // Connessione del Client
    private String serverIp; // IP del Server
    private int serverPort; // Porta del Server
    private BufferedReader input; // Ricezione Dati
    private PrintWriter output; // Invio Dati
    public RequestAtServer requestAtServer; // Richiesta al Server
    public List<ServerResponse> serverResponses; // Risposte ricevuta dal Server

    public ClientTCP(String serverIp, int serverPort) throws IOException {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.connection = new Socket(this.serverIp, this.serverPort); // Aggiunta dei dati del Server
        this.input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        this.output = new PrintWriter(connection.getOutputStream(), true);
        this.serverResponses = new ArrayList<>();
    }

    public void run() {
        // Resta in ascolto del Server finché la Connessione è attiva
        while (isConnected()) {
            try {
                Thread.sleep(100);
                String dataFromServer = input.readLine(); // Riceve dati dal Server 
                manageDataFromServer(dataFromServer); // Gestisce i dati ricevuti dal Server
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 
        }
    } 

    private void manageDataFromServer(String dataFromServer) {
        if (dataFromServer != null) {
            System.out.println("Risposta da Server " + serverIp + ":" + dataFromServer); // Output in console

            String[] splittedDataFromServer = dataFromServer.split(";"); // Divide i dati: [0] -> comando; [1] -> descrizione comando
            serverResponses.add(new ServerResponse(splittedDataFromServer)); // Aggiunge alla coda
        }
    }

    // Invia la richiesta al Server
    public void send(RequestAtServer requestAtServer) throws IOException {
        System.out.println("Richiesta al Server " + serverIp + ":" + requestAtServer.toString());
        output.println(requestAtServer.toString());
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

    public boolean haveResponsesFromServer() {
        if (serverResponses.isEmpty())
            return false;
        else 
            return true;
    }

    public ServerResponse getOldResponse() {
        if (haveResponsesFromServer())
            return serverResponses.get(0);
        else
            return null;
    }

    public void removeOldResponse() {
        serverResponses.remove(0);
    }
}
