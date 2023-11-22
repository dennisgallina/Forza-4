public class RequestAtServer {
    public String command; // Azione richiesta
    public int positionX; // Colonna scelta dal giocatore

    public RequestAtServer(String command, int positionX) {
        this.command = command;
        this.positionX = positionX;
    }

    public RequestAtServer(String command) {
        this.command = command;
        this.positionX = -1;
    }

    public String toString() {
        if (positionX == -1)
            return command;
        else
            return command + ";" + positionX;
    }
}
