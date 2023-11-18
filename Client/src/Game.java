import java.io.IOException;

public class Game extends Thread {
    public boolean state; // Stato della partita: true -> game in corso, false -> game non iniziato/finito
    public Graphic graphic; // Grafica della partita
    public PlayGround playGround; // Griglia di gioco
    public ClientTCP clientTCP; // Comunicazione col Server
    public RequestAtServer requestAtServer; // Richiesta al Server

    public Game(Graphic graphic, ClientTCP clientTCP) throws IOException {
        this.state = false;
        this.graphic = graphic;
        this.playGround = new PlayGround();
        this.clientTCP = clientTCP;
        this.requestAtServer = new RequestAtServer();
    }

    public void run() {
        state = true; // Avvia del Game

        // Mentre il Game è in corso
        while (state == true) {
            if (graphic.isButtonDiconnectPressed()) {
                requestAtServer.command = "disconnect";
                state = false; // Il Game è finito
                graphic.Disconnect();
                graphic.showDisconnect();
                try {
                    clientTCP.send(requestAtServer.command);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            // Tutte le altre condizioni per i vari bottoni premuti
            // Esempio: quando inserisce la pedina
        } 
        
        // Quando il Game è finito
        try {
            clientTCP.close(); // Chiude la connessione
            graphic.createFinishScreen();
            graphic.showFinishScreen();
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
                graphic.createWaitingScreen();
                graphic.showWaitingScreen();
                break;
            // Inizio Game
            case "start":
                graphic.createGame(playGround.rows, playGround.columns);
                graphic.showGame();
                break;
            // Aggiornamento del PlayGround
            case "refresh":
                String[] pawns = dataFromServer[1].split(","); // Pawns sono divise dalla virgola
                insertPawns(pawns); // Inserisce le Pawns nel campo da gioco

                graphic.createGame(playGround.rows, playGround.columns);
                graphic.showGame();
                break;
            // Fine Game (l'avversario si è disconnesso)
            case "finish":
                this.state = false;
                break;
            // Esito vincitore, di conseguenza fine Game
            case "winner":
                graphic.createWinnerScreen();
                graphic.showWinnerScreen();
                this.state = false;
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
