import java.io.IOException;
import java.net.Socket;

public class App {
    public static void main(String[] args) throws IOException {
        ServerTCP server = new ServerTCP(666); // Crea un'istanza della classe ServerTCP

        // Attesa del primo giocatore
        Socket connectionPlayer1 = server.acceptConnection();
        while (connectionPlayer1 == null) 
            connectionPlayer1 = server.acceptConnection();
        
        Player player1 = new Player("Player 1", "red", connectionPlayer1); // Creazione del Player 1
        server.send("wait", connectionPlayer1); // Tiene il giocatore in attesa fino alla connessione dell'avversario

        // Attesa del secondo giocatore
        Socket connectionPlayer2 = server.acceptConnection();
        while (connectionPlayer2 == null) 
            connectionPlayer2 = server.acceptConnection();
        
        Player player2 = new Player("Player 2", "yellow", connectionPlayer2); // Creazione del Player 2

        Game game = new Game(player1, player2); // Crea un'istanza della partita
        game.start(); // Inizia la partita

        while (game.isAlive()) {}

        if (!server.serverSocket.isClosed()) 
            server.close();
    }
}
