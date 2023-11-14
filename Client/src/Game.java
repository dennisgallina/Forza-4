import java.io.IOException;

public class Game {
    public PlayGround playGround; // Griglia di gioco
    public ClientTCP clientTCP;

    public Game(ClientTCP clientTCP) {
        this.playGround = new PlayGround();
        this.clientTCP = clientTCP;
    }

    public void getGameDaServer() throws IOException
    {
        while(true)
        {
            String[] data = clientTCP.data.split(";");

            if(data[data.length].equals(player1.name))
            {
                insertPawns(data);
                
                tcp.close();
                playGround.fine();
            }
            else if(data[data.length].equals(player2.name))
            {
                insertPawns(data);
                tcp.close();
                playGround.fine();
            }
            else{
                insertPawns(data);
            }
        }
    }

    public void insertPawns(String[] vett)
    {
        int k = 0;
        for(int y = 1; y < playGround.rows; y++)
        {
            for(int x = 1; x < playGround.columns; x++)
            {
                if(vett[k].equals("0")){}
                else if(vett[k].equals("1")){
                    playGround.insert(new Pawn("yellow", x, y));
                }
            }
        }
    }
}
