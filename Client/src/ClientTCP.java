import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTCP {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String ip;
    private int porta;

    public ClientTCP(String ip, int porta) throws IOException {
        this.ip = ip;
        this.porta = porta;
        this.socket = new Socket(ip, porta);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void invia(String data) throws IOException {
        out.println(data);
    }

    public String ricevi() throws IOException {
        String response = in.readLine();
        return response;
    }

    public void chiudi() throws IOException {
        ip = ""; 
        porta = -1;
        in.close();
        out.close();
        socket.close();
    }
}
