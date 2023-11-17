import java.io.IOException;

public class Game {
    public Graphic graphic;
    public PlayGround playGround; // Griglia di gioco
    public ClientTCP clientTCP; // Comunicazione col Server
    public RequestAtServer req;

    public Game() throws IOException {
        this.playGround = new PlayGround();
        this.clientTCP = new ClientTCP("null", 0);
        this.graphic = new Graphic();
        this.req = new RequestAtServer();
    }

    public void manageDataFromServer() throws IOException
    {
        boolean prem = false;
        graphic.createLobby();
        do{
            prem = graphic.isButtonConnectPressed();
        } while(!prem);
        req.sendAtServer("", clientTCP, 0);
        while (true)
        {
            if(prem = graphic.isButtonDiconnectPressed())
                req.sendAtServer("disconnect", clientTCP, 0);
            
            String[] dataFromServer = clientTCP.data.split(";"); // Dati ricevuti dal Server
            switch (dataFromServer[0]) {
                case "wait":
                    graphic.createWaitingScreen();
                    break;
                case "start":
                    graphic.createCamp(playGround.rows, playGround.columns);
                    break;
                case "refresh":
                    insertPawns(dataFromServer);
                    
                    break;
                case "finish":
                    graphic.Disconnect();
                    break;
                case "winner":
                    graphic.WinnerScreenCreator();
                    clientTCP.close();
                    break;
                default:
                graphic.messagError();
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
