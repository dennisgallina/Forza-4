import java.util.ArrayList;
import java.util.List;

public class PlayGround {
    public List<Pawn> pawns; // Pedine all'interno della griglia
    public int colums; // Numero di colonne della grigia
    public int rows; // Numero di righe della griglia

    public PlayGround() {
        this.pawns = new ArrayList<>();
        this.colums = 6;
        this.rows = 7;
    }

    // Inserisce la Pawn nel PlayGround
    public boolean insert(String color, int positionXPawn) {
        if (columFull(positionXPawn) == false)
            return false;

        // Creazione e inserimento Pawn nel PlayGround
        Pawn pawn = new Pawn(color, positionXPawn, getFreePositionY(positionXPawn));
        pawns.add(pawn);
        return true;
    }

    // Trova la posizione libera nella colonna richiesta
    private int getFreePositionY(int positionX) {
        int freePositionY = 0;

        // Scorre tutte le Pawn
        for (int i = 0; i < pawns.size(); i++) {
            // Se la Pawn è nella colonna interessata
            if (pawns.get(i).positionX == positionX) {
                // Trova a posizione libera sulla colonna
                if (pawns.get(i).positionY > freePositionY)
                    freePositionY = pawns.get(i).positionY + 1;
            }
        } return freePositionY;
    }

    // Controlla se la colonna richiesta è piena: true -> piena; false -> non è piena 
    public boolean columFull(int positionX) {
        // Scorre tutte le Pawn
        for (int i = 0; i < pawns.size(); i++) {
            // Se la Pawn è nella colonna interessata
            if (pawns.get(i).positionX == positionX) {
                // Controlla che non sia quella più in cima, altrimenti significa che la colonna è piena
                if (pawns.get(i).positionY == rows) 
                    return true;
            }
        } return false;
    }
}
