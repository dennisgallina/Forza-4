import java.util.ArrayList;
import java.util.List;

public class PlayGround {
    public List<Pawn> pawns; // Pedine all'interno della griglia
    public int columns; // Numero di colonne della griglia
    public int rows; // Numero di righe della griglia

    public PlayGround() {
        this.pawns = new ArrayList<>();
        this.columns = 6;
        this.rows = 7;
    }

    public void insert(Pawn pawn) {
        pawns.add(pawn);
    }

    public void clear() {
        pawns.clear();
    }
}