import java.io.IOException;
import java.net.Socket;

public class App {
    public static void main(String[] args) throws IOException {
        ServerTCP server = new ServerTCP(666); // Crea un'istanza della classe ServerTCP

        // Attesa del primo giocatore
        Socket player1Socket = server.acceptConnection();
        while (player1Socket == null) 
            player1Socket = server.acceptConnection();
        
        Client player1 = new Client(player1Socket, new Player("Player 1", "red"));
        server.send("wait", player1Socket); // Tiene il giocatore in attesa fino alla connessione dell'avversario

        // Attesa del secondo giocatore
        Socket player2Socket = server.acceptConnection();
        while (player2Socket == null) 
            player2Socket = server.acceptConnection();
        
        Client player2 = new Client(player2Socket, new Player("Player 2", "yellow"));

        Game game = new Game(player1, player2); // Crea un'istanza della partita
        game.start(); // Inizia la partita

        while (game.isAlive()) {}

        if (!server.serverSocket.isClosed()) 
            server.close();
    }
}
