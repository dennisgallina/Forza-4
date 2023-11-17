import java.io.IOException;

public class Game {
    public PlayGround playGround; // Griglia di gioco
    public ClientTCP clientTCP; // Comunicazione col Server

    public Game(ClientTCP clientTCP) {
        this.playGround = new PlayGround();
        this.clientTCP = clientTCP;
    }

    public void manageResponse() throws IOException {
        while (true) {
            String[] serverResponse = clientTCP.data.split(";"); // Dati ricevuti dal Server
            String command = serverResponse[0];

            // Gestione della risposta del Server
            switch (command) {
                case "connection accepted":
                    // Gestisce la connessione accettata
                    break;

                case "wait":
                    // Gestisce l'attesa della connessione per l'altro avversario
                    break;

                case "start":
                    // Gestisce l'inizio della partita
                    break;

                case "refresh":
                    // Gestisce il refresh del campo
                    String[] pawns = serverResponse[1].split(",");
                    insertPawns(pawns);
                    break;

                case "finish":
                    // Gestisce la disconnessione dell'avversario
                    break;

                case "winner":
                    // Gestisce la vittoria di uno dei due giocatori
                    break;

                case "command not recognized":
                    // Gestisce un comando non riconosciuto
                    break;
            
                default:
                    break;
            }
        }
    }

    // Inserisce le Pawns ricevute dal Server: 0 -> Pawn non presente, 1 -> Pawn red, 2 -> Pawn yellow
    public void insertPawns(String[] pawns)
    {
        playGround.clear(); // Svuota il campo attuale per non sovrapporre l'inserimento

        for (int row = 1; row < playGround.rows; row++) { // Scorre le righe dal basso verso l'alto 
            for (int column = 1, countPawns = 0; column < playGround.columns; column++, countPawns++) { // Scorre le colonne della riga
                if (pawns[countPawns].equals("1")) {
                    playGround.insert(new Pawn("red", column, row)); // Inserimento Pawn red
                } else if (pawns[countPawns].equals("2"))
                    playGround.insert(new Pawn("yellow", column, row)); // Inserimento Pawn yellow
            }
        }
    }
}
