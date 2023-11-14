import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTCP extends Thread{
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String ip;
    private int port;
    public String data;

    public ClientTCP(String ip, int porta) throws IOException {
        this.ip = ip;
        this.port = porta;
        this.socket = new Socket(ip, porta);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run() {
        while (!socket.isClosed()) {
            try {
                data = in.readLine();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    } 

    public void send(String data) throws IOException {
        out.println(data);
    }

    public void close() throws IOException {
        ip = ""; 
        port = -1;
        in.close();
        out.close();
        socket.close();
    }
}
