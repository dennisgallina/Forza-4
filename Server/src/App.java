import java.io.IOException;
import java.net.Socket;

public class App {
    public static void main(String[] args) throws IOException {
        ServerTCP server = new ServerTCP(666); // Crea un'istanza della classe ServerTCP

        // Attesa del primo giocatore
        Socket player1Socket = server.acceptConnection();
        while (player1Socket == null) 
            player1Socket = server.acceptConnection();
        
        ThreadClient threadPlayer1 = new ThreadClient(player1Socket, new Player("Player 1", "red"));
        threadPlayer1.send("wait"); // Tiene il giocatore in attesa fino alla connessione dell'avversario

        // Attesa del secondo giocatore
        Socket player2Socket = server.acceptConnection();
        while (player2Socket == null) 
            player2Socket = server.acceptConnection();
        
        ThreadClient threadPlayer2 = new ThreadClient(player2Socket, new Player("Player 2", "yellow"));

        Game game = new Game(threadPlayer1, threadPlayer2); // Crea un'istanza della partita
        game.start(); // Inizia la partita

        while (game.isAlive()) {}

        if (!server.serverSocket.isClosed()) 
            server.close();
    }
}
