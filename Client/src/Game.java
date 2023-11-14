import java.io.IOException;

public class Game {
    public PlayGround playGround; // Griglia di gioco
    public ClientTCP clientTCP; // Comunicazione col Server

    public Game(ClientTCP clientTCP) {
        this.playGround = new PlayGround();
        this.clientTCP = clientTCP;
    }

    public void manageDataFromServer() throws IOException
    {
        while(true)
        {
            String[] dataFromServer = clientTCP.data.split(";"); // Dati ricevuti dal Server

            // Divisione dei dati in Pawns e Winner
            String[] pawns;
            String winner = "";
            if (!dataFromServer[dataFromServer.length].equals("0") && !dataFromServer[dataFromServer.length].equals("1") && !dataFromServer[dataFromServer.length].equals("2")) {
                pawns = dataFromServer;
                pawns[pawns.length] = null;
                winner = dataFromServer[dataFromServer.length];
            } else
                pawns = dataFromServer;

            insertPawns(pawns);

            if(winner.equals("Player 1")) 
                clientTCP.close();
            else if(winner.equals("Player 2"))
                clientTCP.close();
        }
    }

    public void insertPawns(String[] vett)
    {
        for(int row = 1; row < playGround.rows; row++)
        {
            for(int column = 1, countPawns = 0; column < playGround.columns; column++, countPawns++)
            {
                if(vett[countPawns].equals("0")){}
                else if(vett[countPawns].equals("1")){
                    playGround.insert(new Pawn("yellow", column, row));
                }
            }
        }
    }
}
