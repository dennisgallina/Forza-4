import java.io.IOException;

public class Game {
    public boolean state; // Stato della partita: true -> in corso; false -> non in corso
    public PlayGround playGround; // Griglia di gioco
    public ThreadClient player1; // Giocatore 1
    public ThreadClient player2; // Giocatore 2
    public ThreadClient currentPlayer; // Giocatore corrente

    public Game() {
        this.state = false;
        this.playGround = new PlayGround();
        this.player1 = null;
        this.player2 = null;
        this.currentPlayer = null;
    }

    public void start() {
        this.state = true;
        this.currentPlayer = player1; // Inizia il giocatore 1
    }

    public synchronized boolean insertPawn(String color, int positionX) throws IOException {
        // Controlla se è il turno del giocatore corretto
        if ((currentPlayer == player1 && color.equals("red")) || (currentPlayer == player2 && color.equals("yellow"))) {
            if (playGround.insert(color, positionX)) {
                // Cambia turno
                currentPlayer = (currentPlayer == player1) ? player2 : player1;
    
                // Invia lo stato del gioco a entrambi i client
                player1.send(playGround.getGamePlayGround());
                player2.send(playGround.getGamePlayGround());
    
                return true;
            }
        }
        return false;
    }    
}