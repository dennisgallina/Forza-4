import java.io.IOException;

public class Game extends Thread{
    public boolean state; // Stato della partita: true -> in corso; false -> non in corso
    public PlayGround playGround; // Griglia di gioco
    public ThreadClient player1; // Giocatore 1
    public ThreadClient player2; // Giocatore 2
    public ThreadClient currentPlayer; // Giocatore corrente

    public Game() {
        this.state = false;
        this.playGround = new PlayGround();
        this.player1 = null;
        this.player2 = null;
        this.currentPlayer = null;
    }

    public Game(ThreadClient player1, ThreadClient player2) {
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
    
        // Invia lo stato del gioco a entrambi i client
        try {
            player1.send(playGround.getGamePlayGround());
            player2.send(playGround.getGamePlayGround());
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
            
            // Invia lo stato del gioco a entrambi i client
            player1.send(playGround.getGamePlayGround() + ";" + vincitore);
            player2.send(playGround.getGamePlayGround() + ";" + vincitore);
        }
        else if (clientRequest.command.equals("disconnect")) { // Se richiede di disconnettersi
            currentPlayer.socketClient.close(); // Disconnette il giocatore
            // Invia l'attesa all'altro giocatore
            if (currentPlayer.equals(player1))
                player2.send("finish");
            else if (currentPlayer.equals(player2))
                player1.send("finish");

            this.state = false;
            return;
        }
        
        currentPlayer = (currentPlayer == player1) ? player2 : player1; // Cambia turno
    }    
}