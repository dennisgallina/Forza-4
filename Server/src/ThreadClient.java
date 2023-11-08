import java.net.Socket;

public class ThreadClient extends Thread {
    private Socket socketClient; // Socket inviata dal Client

    public ThreadClient() {
        this.socketClient = new Socket();
    }

    public ThreadClient(Socket socketClient) {
        this.socketClient = socketClient;
    }

    public void run() {
        // Codice eseguito in un thread separato
    }
}