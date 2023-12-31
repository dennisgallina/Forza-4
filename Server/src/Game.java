import java.io.IOException;

public class Game extends Thread {
    public ServerTCP serverTCP; // Server su cui è situata la partita
    public boolean state; // Stato della partita: true -> in corso; false -> non in corso
    public PlayGround playGround; // Griglia di gioco
    public Player player1; // Giocatore 1
    public Player player2; // Giocatore 2
    public Player currentPlayer; // Giocatore corrente

    public Game(ServerTCP serverTCP, Player player1, Player player2) {
        this.serverTCP = serverTCP;
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
    
        try {
            // Invia lo stato del gioco a tutti i client
            serverTCP.sendAtAll("start");
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        currentPlayer.clientRequest = null;
        // Continua a eseguire il gioco finché la partita è attiva ed entrambi i giocatori sono connessi
        while (this.state == true && state && player1.isAlive() && player2.isAlive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            try {
                if (currentPlayer.clientRequest != null) {
                    manageRequest(currentPlayer.clientRequest);
                    currentPlayer.clearRequest();
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        this.state = false; // Finisce la partita
        return;
    } 

    // Gestione del comando richisto
    private void manageRequest(ClientRequest clientRequest) throws IOException, InterruptedException {
        switch (clientRequest.command) {
            case "insert": // Richiede l'inserimento di una Pawn
                if (currentPlayer.equals(player1))
                    serverTCP.sendAtAll("turn;" + player2.name);
                else
                    serverTCP.sendAtAll("turn;" + player1.name);

                Thread.sleep(100);
                
                playGround.insert(currentPlayer.pawnsColor, clientRequest.positionX); // Inserisce la Pawn
                serverTCP.sendAtAll("refresh;" + playGround.getPawns()); // Comunica lo stato del gioco a tutti i client

                // Verifica se c'è un vincitore
                if (playGround.checkWin() == null) {}
                else if (playGround.checkWin().equals("red")) 
                    serverTCP.sendAtAll("winner;Player 1"); // Comunica il vincitore a tutti i client
                else if (playGround.checkWin().equals("yellow"))
                    serverTCP.sendAtAll("winner;Player 2"); // Comunica il vincitore a tutti i client
                
                currentPlayer = (currentPlayer == player1) ? player2 : player1; // Cambia turno
                break;

            case "disconnect": // Richiede la disconnessione
                currentPlayer.connection.close(); // Disconnette il giocatore

                // Comunica la disconnessione all'altro giocatore
                if (currentPlayer.equals(player1))
                    serverTCP.send("finish", player2.connection);
                else if (currentPlayer.equals(player2))
                    serverTCP.send("finish", player1.connection);

                this.state = false; // Finisce la partita
                break;

            default: // Richiede un comando inesistente
                serverTCP.send("command not recognized", currentPlayer.connection);
                break;
        }
    }    
}