public class Game {
    public boolean state; // Stato della partita: true -> in corso; false -> non in corso
    public PlayGround playGround; // Griglia di gioco
    public Player player1; // Giocatore 1
    public Player player2; // Giocatore 2
    public char turn; // Turno di quale giocatore

    public Game() {
        this.state = false;
        this.playGround = new PlayGround();
        this.player1 = null;
        this.player2 = null;
        this.turn = '0';
    }
}
