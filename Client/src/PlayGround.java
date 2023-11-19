public class PlayGround {
    public int columns; // Numero di colonne della griglia
    public int rows; // Numero di righe della griglia
    public Pawn[][] pawns; // Pedine all'interno della griglia

    public PlayGround() {
        this.columns = 6;
        this.rows = 7;
        this.pawns = new Pawn[this.rows][this.columns];
    }

    public void insert(Pawn pawn) {
        pawns[pawn.positionY][pawn.positionX] = pawn;
    }
}