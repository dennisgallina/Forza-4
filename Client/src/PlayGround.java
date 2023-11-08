import java.util.ArrayList;
import java.util.List;

public class PlayGround {
    public List<Pawn> pawns; // Pedine all'interno della griglia
    public int columns; // Numero di colonne della grigia
    public int rows; // Numero di righe della griglia

    public PlayGround() {
        this.pawns = new ArrayList<>();
        this.columns = 6;
        this.rows = 7;
    }
}
