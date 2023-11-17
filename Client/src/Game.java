import java.io.IOException;

public class Game extends Thread {
    public Graphic graphic;
    public PlayGround playGround; // Griglia di gioco
    public ClientTCP clientTCP; // Comunicazione col Server
    public RequestAtServer requestAtServer; // Richiesta al Server

    public Game() throws IOException {
        this.playGround = new PlayGround();
        this.clientTCP = new ClientTCP("null", 0);
        this.graphic = new Graphic();
        this.requestAtServer = new RequestAtServer();
    }

    public void run() {
        boolean isPressed = false;

        graphic.createLobby();
        do {
            isPressed = graphic.isButtonConnectPressed();
        } while (!isPressed);

        while (true) {
            if (isPressed = graphic.isButtonDiconnectPressed()) {
                requestAtServer.command = "disconnect";
                try {
                    clientTCP.send(requestAtServer.command);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    // Gestione della risposta da parte del Server
    public void manageResponse() throws IOException {
        String[] dataFromServer = clientTCP.data.split(";"); // Dati ricevuti dal Server: [0] -> command; [1] -> Pawns/Winner
        String command = dataFromServer[0]; // Comando ricevuto dal Server
        // Gestione del comando ricevuto dal Server
        switch (command) {
            // Conferma della connessione
            case "connection accepted":
                graphic.createWaitingScreen();
                break;
            // Attesa dell'avversario
            case "wait":
                graphic.createWaitingScreen();
                break;
            // Inizio Game
            case "start":
                graphic.createCamp(playGround.rows, playGround.columns);
                break;
            // Aggiornamento del PlayGround
            case "refresh":
                String[] pawns = dataFromServer[1].split(","); // Pawns sono divise dalla virgola
                insertPawns(pawns); // Inserisce le Pawns nel campo da gioco
                break;
            // Fine Game (l'avversario si è disconnesso)
            case "finish":
                graphic.Disconnect();
                break;
            // Esito vincitore, di conseguenza fine Game
            case "winner":
                graphic.WinnerScreenCreator();
                clientTCP.close();
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
