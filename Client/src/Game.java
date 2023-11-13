import java.io.IOException;

public class Game {
    public boolean state; // Stato della partita: true -> in corso; false -> non in corso
    public PlayGround playGround; // Griglia di gioco
    public Player player1; // Giocatore 1
    public Player player2; // Giocatore 2
    public char turn; // Turno di quale giocatore
    public ClientTCP tcp;

    public Game(ClientTCP c) {
        this.state = false;
        this.playGround = new PlayGround();
        this.player1 = null;
        this.player2 = null;
        this.turn = '0';
        this.tcp = c;
    }

    public void getGameDaServer() throws IOException
    {
        while(true)
        {
            String str = tcp.ricevi();
            String[] vett = str.split(";");

            if(vett[vett.length].equals(player1.name))
            {
                putPawns(vett);
                
                tcp.chiudi();
                playGround.fine();
                player1 = new Player();
                player1 = new Player();
            }
            else if(vett[vett.length].equals(player2.name))
            {
                putPawns(vett);
                tcp.chiudi();
                playGround.fine();
                player1 = new Player();
                player1 = new Player();
            }
            else{
                putPawns(vett);
            }
        }
    }

    public void putPawns(String[] vett)
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
