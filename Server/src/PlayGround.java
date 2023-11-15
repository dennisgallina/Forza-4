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

    // Inserisce la Pawn nel PlayGround
    public synchronized boolean insert(String color, int positionXPawn) {
        if (isColumnFull(positionXPawn))
            return false;

        // Creazione e inserimento Pawn nel PlayGround
        Pawn pawn = new Pawn(color, positionXPawn, columnHeights[positionXPawn]);
        pawns.add(pawn);
        columnHeights[positionXPawn]++;
        return true;
    }

    // Controlla se la colonna richiesta è piena: true -> piena; false -> non è piena 
    public boolean isColumnFull(int positionX) {
        return columnHeights[positionX] >= rows;
    }

    // Questo metodo controlla se un giocatore ha vinto
    public String checkWin() {
        // Scorre tutte le pedine
        for (Pawn pawn : pawns) {
            String color = pawn.color;
            int posX = pawn.positionX;
            int posY = pawn.positionY;

            // Controlla se ci sono 4 pedine dello stesso colore in fila orizzontalmente
            if (checkLine(color, posX, posY, 1, 0) >= 4)
                return color;

            // Controlla se ci sono 4 pedine dello stesso colore in fila verticalmente
            if (checkLine(color, posX, posY, 0, 1) >= 4)
                return color;

            // Controlla se ci sono 4 pedine dello stesso colore in fila diagonalmente da sinistra a destra
            if (checkLine(color, posX, posY, 1, 1) >= 4)
                return color;

            // Controlla se ci sono 4 pedine dello stesso colore in fila diagonalmente da destra a sinistra
            if (checkLine(color, posX, posY, 1, -1) >= 4)
                return color;
        }
        // Se nessuna delle condizioni sopra è vera, nessun giocatore ha vinto
        return null;
    }

    // Controlla se ci sono 4 pedine dello stesso colore in fila in una certa direzione
    private int checkLine(String color, int posX, int posY, int dirX, int dirY) {
        int count = 0;
        // Controlla le 4 posizioni nella direzione specificata
        for (int i = 0; i < 4; i++) {
            // Se la pedina in questa posizione è dello stesso colore, incrementa il contatore
            if (getPawn(posX + dirX * i, posY + dirY * i).color.equals(color))
                count++;
            else
                // Se la pedina non è dello stesso colore, interrompe il ciclo
                break;
        }
        // Restituisce il numero di pedine dello stesso colore in fila
        return count;
    }

    // Restituisce la pedina in una certa posizione
    private Pawn getPawn(int posX, int posY) {
        // Scorre tutte le pedine
        for (Pawn pawn : pawns) {
            // Se la pedina è nella posizione specificata, la restituisce
            if (pawn.positionX == posX && pawn.positionY == posY)
                return pawn;
        }
        
        return null;
    }

    // Restituisce una stringa rappresentante lo stato attuale del campo da gioco
    public String getGamePlayGround() {
        // Crea un StringBuilder per costruire la stringa
        StringBuilder sb = new StringBuilder();
        // Scorre le righe del campo da gioco dal basso verso l'alto
        for (int y = 0; y < rows; y++) {
            // Scorre le colonne del campo da gioco da sinistra a destra
            for (int x = 0; x < columns; x++) {
                // Ottiene la pedina nella posizione corrente
                Pawn pawn = getPawn(x, y);
                // Se la posizione è vuota, aggiunge '0' alla stringa
                if (pawn.color == null) {
                    sb.append('0'); // Casella vuota
                } 
                // Se la posizione contiene una pedina rossa, aggiunge '1' alla stringa
                else if (pawn.color.equals("red")) {
                    sb.append('1'); // Pedina rossa
                } 
                // Se la posizione contiene una pedina gialla, aggiunge '2' alla stringa
                else if (pawn.color.equals("yellow")) {
                    sb.append('2'); // Pedina gialla
                }
            }
            // Aggiunge un carattere di nuova riga alla fine di ogni riga
            sb.append(';');
        }
        // Restituisce la stringa costruita
        return sb.toString();
    }
}

