import java.io.IOException;

public class Game extends Thread{
    public ServerTCP serverTCP; // Server su cui è situata la partita
    public boolean state; // Stato della partita: true -> in corso; false -> non in corso
    public PlayGround playGround; // Griglia di gioco
    public Client player1; // Giocatore 1
    public Client player2; // Giocatore 2
    public Client currentPlayer; // Giocatore corrente

    public Game() {
        this.state = false;
        this.playGround = new PlayGround();
        this.player1 = null;
        this.player2 = null;
        this.currentPlayer = null;
    }

    public Game(Client player1, Client player2) {
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
        if (clientRequest.command.equals("insert")) {// Se richiede di inserire una Pawn
            playGround.insert(currentPlayer.player.color, clientRequest.positionX); // Inserisce la Pawn

            String vincitore = null;
            if (playGround.checkWin() == null) {}
            else if (playGround.checkWin().equals("red")) 
                vincitore = player1.player.name;
            else if (playGround.checkWin().equals("yellow"))
                vincitore = player2.player.name;
            
            // Invia lo stato del gioco a tutti i client
            serverTCP.sendAtAll(playGround.getGamePlayGround() + ";" + vincitore);
        }
        else if (clientRequest.command.equals("disconnect")) { // Se richiede di disconnettersi
            currentPlayer.socketClient.close(); // Disconnette il giocatore
            // Invia l'attesa all'altro giocatore
            if (currentPlayer.equals(player1))
                serverTCP.send("finish", player2.socketClient);
            else if (currentPlayer.equals(player2))
                serverTCP.send("finish", player1.socketClient);

            this.state = false;
            return;
        }
        
        currentPlayer = (currentPlayer == player1) ? player2 : player1; // Cambia turno
    }    
}