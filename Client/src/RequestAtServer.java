import java.io.IOException;

public class RequestAtServer {
    public String comando; // Azione richiesta
    public int posizioneX; // Colonna scelta dal giocatore

    public RequestAtServer() {
        this.comando = "";
        this.posizioneX = 0;
    }

    public void sendAtServer(String comando, ClientTCP tcp, int positionX) throws IOException
    {
        switch (comando) {
            case "":
                tcp.start();
                break;
            case "insert":
                String cmd = comando + ";" + positionX;
                tcp.send(cmd);
                break;
            case "disconnect":
                tcp.send(comando);
                tcp.close();
            default:    
                tcp.send(comando);
                break;
        }
    }
}
