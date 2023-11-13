import java.util.ArrayList;
import java.util.List;

public class PlayGround {
    public List<Pawn> pawns; // Pedine all'interno della griglia
    public int[] columnHeights; // Altezza di ogni colonna
    public int columns; // Numero di colonne della griglia
    public int rows; // Numero di righe della griglia

    public PlayGround() {
        this.pawns = new ArrayList<>();
        this.columnHeights = new int[7];
        this.columns = 6;
        this.rows = 7;
    }

    public void insert(Pawn p)
    {
        pawns.add(p);
    }

    public void fine()
    {
        pawns.clear();
    }
}