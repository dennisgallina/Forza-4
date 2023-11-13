import java.net.Socket;

public class App {
    public static void main(String[] args) throws Exception {
        Game game = new Game(); // Crea un'istanza della partita
        ServerTCP server = new ServerTCP(666); // Crea un'istanza della classe ServerTCP

        // Attesa del primo giocatore
        Socket connessionePlayer1 = null;
        while (connessionePlayer1 == null) {
            connessionePlayer1 = server.accettaConnessione(); // Accetta la prima connessione
        }

        // Crea e avvia un nuovo ThreadClient per gestire la connessione
        ThreadClient threadPlayer1 = new ThreadClient(connessionePlayer1, new Player("Player 1", "red"), game);
        game.player1 = threadPlayer1;
        threadPlayer1.start(); // Avvia il thread per il giocatore 1

        // Attesa del secondo giocatore
        Socket connessionePlayer2 = null;
        while (connessionePlayer2 == null) {
            connessionePlayer2 = server.accettaConnessione(); // Accetta la seconda connessione
        }

        // Crea e avvia un nuovo ThreadClient per gestire la connessione
        ThreadClient threadPlayer2 = new ThreadClient(connessionePlayer2, new Player("Player 2", "yellow"), game);
        game.player2 = threadPlayer2;
        threadPlayer2.start(); // Avvia il thread per il giocatore 2

        // Inizia il gioco
        game.start();
    }
}
