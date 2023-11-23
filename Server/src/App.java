import java.io.IOException;
import java.net.Socket;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerTCP serverTCP = new ServerTCP(666); // Crea un'istanza della classe ServerTCP

        // Attesa del primo giocatore
        Socket connectionPlayer1 = serverTCP.acceptConnection();
        while (connectionPlayer1 == null) 
            connectionPlayer1 = serverTCP.acceptConnection();

        serverTCP.send("connection accepted;Player 1", connectionPlayer1);
        
        Player player1 = new Player("Player 1", "red", connectionPlayer1); // Creazione del Player 1
        serverTCP.send("wait", connectionPlayer1); // Tiene il giocatore in attesa fino alla connessione dell'avversario

        // Attesa del secondo giocatore
        Socket connectionPlayer2 = serverTCP.acceptConnection();
        while (connectionPlayer2 == null)  
            connectionPlayer2 = serverTCP.acceptConnection();
            
        serverTCP.send("connection accepted;Player 2", connectionPlayer2);
        
        Player player2 = new Player("Player 2", "yellow", connectionPlayer2); // Creazione del Player 2

        Game game = new Game(serverTCP, player1, player2); // Crea un'istanza della partita
        game.start(); // Inizia la partita

        while (game.isAlive()) {}

        if (!serverTCP.serverSocket.isClosed()) 
            serverTCP.close();
    }
}
