import java.io.IOException;

public class Game extends Thread{
    public ServerTCP serverTCP; // Server su cui è situata la partita
    public boolean state; // Stato della partita: true -> in corso; false -> non in corso
    public PlayGround playGround; // Griglia di gioco
    public Player player1; // Giocatore 1
    public Player player2; // Giocatore 2
    public Player currentPlayer; // Giocatore corrente

    public Game(Player player1, Player player2) {
        this.state = false;
        this.playGround = new PlayGround();
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
    }

    public void run() {
        this.state = true; // Avvia la partita
        player1.start(); // Avvia il thread per il giocatore 1
        player2.start(); // Avvia il thread per il giocatore 2
    
        // Invia lo stato del gioco a tutti i client
        try {
            serverTCP.sendAtAll(playGround.getGamePlayGround());
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        // Continua a eseguire il gioco finché la partita è attiva ed entrambi i giocatori sono connessi
        while (this.state == true && state && player1.isAlive() && player2.isAlive()) {
            try {
                manageRequest(currentPlayer.clientRequest);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        this.state = false;
        return;
    } 

    private void manageRequest(ClientRequest clientRequest) throws IOException {
        switch (clientRequest.command) {
            case "insert":
                playGround.insert(currentPlayer.pawnsColor, clientRequest.positionX); // Inserisce la Pawn

                String vincitore = null;
                if (playGround.checkWin() == null) {}
                else if (playGround.checkWin().equals("red")) 
                    vincitore = player1.name;
                else if (playGround.checkWin().equals("yellow"))
                    vincitore = player2.name;
                
                serverTCP.sendAtAll(playGround.getGamePlayGround() + ";" + vincitore); // Invia lo stato del gioco a tutti i client
                currentPlayer = (currentPlayer == player1) ? player2 : player1; // Cambia turno
                break;

            case "disconnect":
                currentPlayer.connection.close(); // Disconnette il giocatore
                // Invia l'attesa all'altro giocatore
                if (currentPlayer.equals(player1))
                    serverTCP.send("finish", player2.connection);
                else if (currentPlayer.equals(player2))
                    serverTCP.send("finish", player1.connection);

                this.state = false;
                break;

            default:
                serverTCP.send("command not recognized", currentPlayer.connection);
                break;
        }
    }    
}