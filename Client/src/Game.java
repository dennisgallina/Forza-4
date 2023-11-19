import java.io.IOException;

public class Game extends Thread {
    public boolean state; // Stato della partita: true -> game in corso, false -> game non iniziato/finito
    public Graphic graphic; // Grafica della partita
    public PlayGround playGround; // Griglia di gioco
    public String currentPlayerName; // Nome del giocatore del turno corrente
    public ClientTCP clientTCP; // Comunicazione col Server
    public RequestAtServer requestAtServer; // Richiesta al Server

    public Game(Graphic graphic, ClientTCP clientTCP) throws IOException {
        this.state = false;
        this.graphic = graphic;
        this.playGround = new PlayGround();
        this.currentPlayerName = "Player 1";
        this.clientTCP = clientTCP;
        this.requestAtServer = new RequestAtServer();
    }

    public void run() {
        while (!state) {
            try {
                manageResponse();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // Mentre il Game è in corso
        while (state == true) {
            // Richiede di inserire una pedina
            if (graphic.isButtonPawnPressed()) 
                requestAtServer.command = "insert;" + graphic.buttonPawnPressedX; // Prepara la richiesta al Server

            // Richiede di disconnettersi
            if (graphic.isButtonDiconnectPressed()) {
                requestAtServer.command = "disconnect"; // Prepara la richiesta al Server
                state = false; // Il Game è finito
                graphic.Disconnect(); graphic.showDisconnect(); // Crea e visualizza la schermata di disconnessione
            }

            try {
                clientTCP.send(requestAtServer.command); // Invia la richiesta al Server
                manageResponse(); // Gestisce la risposta ricevuta dal Server
                clientTCP.cleanResponse(); // Dopo aver eseguito il comando lo pulisce per evitare ripetizioni
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } 
        
        // Quando il Game è finito
        try {
            clientTCP.close(); // Chiude la connessione
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // Gestione della risposta da parte del Server
    public void manageResponse() throws IOException {
        String[] dataFromServer = clientTCP.serverResponse.split(";"); // Dati ricevuti dal Server: [0] -> command; [1] -> Pawns/Winner
        String command = dataFromServer[0]; // Comando ricevuto dal Server
        // Gestione del comando ricevuto dal Server
        switch (command) {
            // Attesa dell'avversario
            case "wait":
                graphic.createWaitingScreen(); graphic.showWaitingScreen(); // Crea e visualizza la schermata di attesa dell'avversario
                break;
            // Inizio Game
            case "start":
                state = true;
                graphic.createPlayGround(playGround.rows, playGround.columns, playGround.pawns, currentPlayerName); graphic.showPlayGround();
                break;
            // Cambia il turno del giocatore
            case "turn":
                currentPlayerName = dataFromServer[1];
            // Aggiornamento del PlayGround
            case "refresh":
                String[] pawns = dataFromServer[1].split(","); // Pawns sono divise dalla virgola
                insertPawns(pawns); // Inserisce le Pawns nel campo da gioco
                graphic.createPlayGround(playGround.rows, playGround.columns, playGround.pawns, currentPlayerName); graphic.showPlayGround();
                break;
            // Fine Game (l'avversario si è disconnesso)
            case "finish":
                this.state = false;
                graphic.createFinishScreen(); graphic.showFinishScreen();
                break;
            // Esito vincitore, di conseguenza fine Game
            case "winner":
                this.state = false;
                graphic.createWinnerScreen(); graphic.showWinnerScreen();
                break;
            // Non riconosciuto
            case "command not recognized":
                graphic.messagError();
                break;
            default:
                break;
        }
    }

    // Inserisce le Pawns ricevute dal Server: 0 -> Pawn non presente, 1 -> Pawn red, 2 -> Pawn yellow
    public void insertPawns(String[] pawns) {
        for (int row = 0; row < playGround.rows; row++) { // Scorre le righe dal basso verso l'alto 
            for (int column = 0, countPawns = 0; column < playGround.columns; column++, countPawns++) { // Scorre le colonne della riga
                if (pawns[countPawns].equals("1")) {
                    playGround.insert(new Pawn("red", column, row)); // Inserimento Pawn red
                } else if (pawns[countPawns].equals("2"))
                    playGround.insert(new Pawn("yellow", column, row)); // Inserimento Pawn yellow
            }
        }
    }
}
