import java.io.IOException;

public class Game {
    public ClientTCP clientTCP; // Comunicazione col Server
    public boolean state; // Stato della partita: true -> game in corso, false -> game non iniziato/finito
    public Graphic graphic; // Grafica della partita
    public PlayGround playGround; // Griglia di gioco
    public String playerName;
    public String currentPlayerName; // Nome del giocatore del turno corrente

    public Game(Graphic graphic, ClientTCP clientTCP) throws IOException {
        this.clientTCP = clientTCP;
        this.state = false;
        this.graphic = graphic;
        this.playGround = new PlayGround();
        this.currentPlayerName = "Player 1";
    }

    public void start() throws IOException, InterruptedException {
        // Fase di attesa dell'avverario
        while (!state) {
            Thread.sleep(100);
            if (clientTCP.haveResponsesFromServer()) // Controlla ci siano risposte dal Server in coda
                manageResponse();
        }

        // Mentre il Game è in corso
        while (state == true) {
            Thread.sleep(100);
            // Richiede di inserire una pedina
            if (graphic.isButtonPawnPressed()) {
                clientTCP.send(new RequestAtServer("insert", graphic.buttonPawnPressedX)); // Invia la richiesta al Server
                graphic.buttonPawnPressed = false;
            }

            // Richiede di disconnettersi
            if (graphic.isButtonDiconnectPressed()) {
                state = false; // Il Game è finito
                graphic.showDisconnect(); // Crea e visualizza la schermata di disconnessione
                clientTCP.send(new RequestAtServer("disconnect")); // Invia la richiesta al Server
            }

            if (clientTCP.haveResponsesFromServer()) // Controlla ci siano risposte dal Server in coda
                manageResponse();
        } 

        clientTCP.close(); // Chiude la connessione
    }

    // Gestione della risposta da parte del Server
    public void manageResponse() throws IOException, InterruptedException {
        ServerResponse serverResponse = clientTCP.getOldResponse();
        // Gestione del comando ricevuto dal Server
        switch (serverResponse.command) {
            case "connection accepted":
                this.playerName = serverResponse.description; 
                clientTCP.removeOldResponse();
                break;
            // Attesa dell'avversario
            case "wait":
                graphic.showWaitingScreen(); // Visualizza la schermata di attesa dell'avversario
                clientTCP.removeOldResponse();
                break;
            // Inizio Game
            case "start":
                state = true;
                graphic.showPlayGround(playGround.rows, playGround.columns, playGround.pawns, playerName, currentPlayerName);
                clientTCP.removeOldResponse();
                break;
            // Cambia il turno del giocatore
            case "turn":
                currentPlayerName = serverResponse.description;
                clientTCP.removeOldResponse();
                break;
            // Aggiornamento del PlayGround
            case "refresh":
                String[] pawns = serverResponse.description.split(","); // Pawns sono divise dalla virgola
                insertPawns(pawns); // Inserisce le Pawns nel campo da gioco
                graphic.showPlayGround(playGround.rows, playGround.columns, playGround.pawns, playerName, currentPlayerName); //Visualizza il campo da gioco
                clientTCP.removeOldResponse();
                break;
            // Fine Game 
            case "finish":
                this.state = false;
                graphic.showEnemyDisconnected();
                clientTCP.removeOldResponse();
                break;
            // Esito vincitore, di conseguenza fine Game
            case "winner":
                graphic.showWinnerScreen(playerName, serverResponse.description); 
                clientTCP.removeOldResponse();
                break;
            // Non riconosciuto
            case "command not recognized":
                graphic.messagError(); // Visualizza messaggio di errore
                clientTCP.removeOldResponse();
                break;
            default:
                break;
        }
    }

    // Inserisce le Pawns ricevute dal Server: 0 -> Pawn non presente, 1 -> Pawn red, 2 -> Pawn yellow
    public void insertPawns(String[] pawns) {
        int countPawns = 0;
        for (int row = 0; row < playGround.rows; row++) { // Scorre le righe dal basso verso l'alto 
            for (int column = 0; column < playGround.columns; column++, countPawns++) { // Scorre le colonne della riga
                if (pawns[countPawns].equals("1")) {
                    playGround.insert(new Pawn("red", column, row)); // Inserimento Pawn red
                } else if (pawns[countPawns].equals("2"))
                    playGround.insert(new Pawn("yellow", column, row)); // Inserimento Pawn yellow
            }
        }
    }
}
