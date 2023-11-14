import java.io.IOException;
import java.net.Socket;

public class App {
    public static void main(String[] args) throws IOException {
        ServerTCP server = new ServerTCP(666); // Crea un'istanza della classe ServerTCP

        // Attesa del primo giocatore
        Socket connectionPlayer1 = server.acceptConnection();
        while (connectionPlayer1 == null) 
            connectionPlayer1 = server.acceptConnection();
        
        Client player1 = new Client(connectionPlayer1, new Player("Player 1", "red"));
        server.send("wait", connectionPlayer1); // Tiene il giocatore in attesa fino alla connessione dell'avversario

        // Attesa del secondo giocatore
        Socket connectionPlayer2 = server.acceptConnection();
        while (connectionPlayer2 == null) 
            connectionPlayer2 = server.acceptConnection();
        
        Client player2 = new Client(connectionPlayer2, new Player("Player 2", "yellow"));

        Game game = new Game(player1, player2); // Crea un'istanza della partita
        game.start(); // Inizia la partita

        while (game.isAlive()) {}

        if (!server.serverSocket.isClosed()) 
            server.close();
    }
}
