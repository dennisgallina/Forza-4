public class PlayGround {
    public static final String COLOR_RED = "red";
    public static final String COLOR_YELLOW = "yellow";

    public int columns; // Numero di colonne della griglia
    public int rows; // Numero di righe della griglia
    public Pawn[][] pawns; // Pedine all'interno della griglia
    public int[] columnHeights; // Altezza di ogni colonna

    public PlayGround() {
        this.columns = 6;
        this.rows = 7;
        this.pawns = new Pawn[this.rows][this.columns];
        this.columnHeights = new int[this.rows];
    }

    // Inserisce la Pawn nel PlayGround
    public synchronized boolean insert(String color, int positionXPawn) {
        if (isColumnFull(positionXPawn)) {
            return false;
        }

        // Creazione e inserimento Pawn nel PlayGround
        Pawn pawn = new Pawn(color, positionXPawn, columnHeights[positionXPawn]);
        pawns[pawn.positionY][pawn.positionX] = pawn;
        columnHeights[positionXPawn]++;
        return true;
    }

    // Controlla se la colonna richiesta è piena: true -> piena; false -> non è piena 
    public boolean isColumnFull(int positionX) {
        return columnHeights[positionX] >= rows;
    }

    public String checkWin() {
        // Controlla le vittorie nelle colonne
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y <= rows - 4; y++) {
                if (checkLine(x, y, 0, 1, 4)) {
                    return getNonEmptyColor(x, y); // Vittoria trovata
                }
            }
        }
    
        // Controlla le vittorie nelle righe
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x <= columns - 4; x++) {
                if (checkLine(x, y, 1, 0, 4)) {
                    return getNonEmptyColor(x, y); // Vittoria trovata
                }
            }
        }
    
        // Controlla le vittorie nelle diagonali (\)
        for (int x = 0; x <= columns - 4; x++) {
            for (int y = 0; y <= rows - 4; y++) {
                if (checkLine(x, y, 1, 1, 4)) {
                    return getNonEmptyColor(x, y); // Vittoria trovata
                }
            }
        }
    
        // Controlla le vittorie nelle diagonali (/)
        for (int x = 3; x < columns; x++) {
            for (int y = 0; y <= rows - 4; y++) {
                if (checkLine(x, y, -1, 1, 4)) {
                    return getNonEmptyColor(x, y); // Vittoria trovata
                }
            }
        }
    
        return null; // Nessuna vittoria trovata
    }
    
    // Metodo di supporto per ottenere il colore alla posizione specificata
    private String getNonEmptyColor(int posX, int posY) {
        Pawn pawn = getPawn(posX, posY);
        return (pawn != null) ? pawn.color : null;
    }
    
    // Metodo di supporto per verificare una linea di pedine
    private boolean checkLine(int startX, int startY, int deltaX, int deltaY, int length) {
        String color = getNonEmptyColor(startX, startY);
    
        if (color == null) {
            return false; // La posizione iniziale è vuota
        }
    
        for (int i = 0; i < length; i++) {
            String currentColor = getNonEmptyColor(startX + i * deltaX, startY + i * deltaY);
            if (currentColor == null || !color.equals(currentColor)) {
                return false; // La linea non è continua o contiene pedine di colore diverso
            }
        }
        return true; // La linea contiene pedine dello stesso colore
    }
    

    // Restituisce la pedina in una certa posizione
    private Pawn getPawn(int posX, int posY) {
        if (posX >= 0 && posX < columns && posY >= 0 && posY < rows) {
            return pawns[posY][posX];
        }
        return null;
    }

    // Restituisce una stringa rappresentante lo stato attuale del campo da gioco
    public String getPawns() {
        // Crea un StringBuilder per costruire la stringa
        StringBuilder sb = new StringBuilder();
        // Scorre le righe del campo da gioco dal basso verso l'alto
        for (int y = 0; y < rows; y++) {
            // Scorre le colonne del campo da gioco da sinistra a destra
            for (int x = 0; x < columns; x++) {
                // Ottiene la pedina nella posizione corrente
                Pawn pawn = getPawn(x, y);
                // Se la posizione è vuota, aggiunge '0' alla stringa
                if (pawn == null) {
                    sb.append('0'); // Casella vuota
                } 
                // Se la posizione contiene una pedina rossa, aggiunge '1' alla stringa
                else if (COLOR_RED.equals(pawn.color)) {
                    sb.append('1'); // Pedina rossa
                } 
                // Se la posizione contiene una pedina gialla, aggiunge '2' alla stringa
                else if (COLOR_YELLOW.equals(pawn.color)) {
                    sb.append('2'); // Pedina gialla
                }

                // Aggiunge un carattere di nuova riga alla fine di ogni riga
                sb.append(',');
            }
        }
        // Restituisce la stringa costruita
        return sb.toString();
    }
}
