import java.io.IOException;

public class RequestAtServer {
    public String command; // Azione richiesta
    public int positionX; // Colonna scelta dal giocatore

    public RequestAtServer() {
        this.command = "";
        this.positionX = 0;
    }
}
