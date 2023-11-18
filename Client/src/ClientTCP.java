import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTCP extends Thread{
    private Socket socket; // Connessione del Client
    private BufferedReader input; // Ricezione Dati
    private PrintWriter output; // Invio Dati
    public String serverResponse; // Risposta ricevuta dal Server

    public ClientTCP(String serverIp, int serverPort) throws IOException {
        this.socket = new Socket(serverIp, serverPort);
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new PrintWriter(socket.getOutputStream(), true);
    }

    public void run() {
        // Resta in ascolto del Server finché la Connessione non viene chiusa
        while (!socket.isClosed()) {
            try {
                serverResponse = input.readLine(); // Riceve dati dal Server
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    } 

    // Invia dei dati al Server
    public void send(String data) throws IOException {
        output.println(data);
    }

    // Chiude la Connessione col Server
    public void close() throws IOException {
        input.close();
        output.close();
        socket.close();
    }
}
