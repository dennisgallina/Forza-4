import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Graphic extends JFrame {
    private boolean buttonConnectPressed;
    private boolean buttonDisconnectPressed;

    private JFrame lobbyFrame; // Finestra Lobby
    private JFrame waitingFrame; // Finestra Attesa dell'avversario
    private JFrame gameFrame; // Finestra Partita
    private JFrame disconnectFrame; // Finestra Disconnessione
    private JFrame winnerFrame; // Finestra Esito della partita
    private JFrame finishFrame; // Finestra Fine partita

    public Graphic() {
        this.buttonConnectPressed = false;
        this.buttonDisconnectPressed = false;

        this.lobbyFrame = new JFrame("Forza 4 - Lobby");
        this.waitingFrame = new JFrame("Forza 4 - Ricerca di un avversario");
        this.gameFrame = new JFrame("Forza 4 - Partita");
        this.disconnectFrame = new JFrame("Forza 4 - Disconnesso");
        this.winnerFrame = new JFrame("Forza 4 - Esito Partita");
        this.finishFrame = new JFrame("Forza 4 - Partita Terminata");
    }

    // Controllo bottone connetti
    public boolean isButtonConnectPressed() {
        return buttonConnectPressed;
    }

    // Controllo bottone disconneti
    public boolean isButtonDiconnectPressed() {
        return buttonDisconnectPressed;
    }

    // Crea Lobby
    public void createLobby() {
        // Crea una nuova finestra per la lobby
        lobbyFrame.setSize(500, 500);
        lobbyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        lobbyFrame.setLayout(new BorderLayout());
    
        // Aggiungi uno sfondo con un'immagine a tua scelta al centro della finestra
        ImageIcon backgroundIcon = new ImageIcon(/*"path/del/tuo/file/immagine.jpg"*/);
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        lobbyFrame.add(backgroundLabel, BorderLayout.CENTER);
        backgroundLabel.setLayout(new FlowLayout());
    
        // Crea un pannello per il pulsante con uno sfondo grigio chiaro
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(192, 192, 192));
    
        // Crea un pulsante con la scritta "GIOCA" in giallo e dimensioni specifiche
        JButton playButton = new JButton("GIOCA");
        playButton.setForeground(Color.YELLOW);
        playButton.setPreferredSize(new Dimension(150, 50));
    
        // Aggiungi un ascoltatore per gestire l'azione del pulsante
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Imposta la variabile di stato quando il pulsante viene premuto
                buttonConnectPressed = true;
    
                // Mostra un messaggio di avviso
                JOptionPane.showMessageDialog(null, "ATTESA SERVER");
            }
        });
    
        // Aggiungi il pulsante al pannello
        buttonPanel.add(playButton);
    
        // Aggiungi il pannello con il pulsante allo sfondo
        backgroundLabel.add(buttonPanel);
    
        // Aggiungi il backgroundLabel alla finestra della lobby
        lobbyFrame.add(backgroundLabel, BorderLayout.CENTER);
    }
    
    // Visualizza Lobby
    public void showLobby() {
        lobbyFrame.setVisible(true);
    }

    // Crea la schermata di attesa
    public void createWaitingScreen() {
        waitingFrame.setSize(500, 500);
        waitingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        waitingFrame.setLayout(new BorderLayout());

        ImageIcon backgroundIcon = new ImageIcon(/*"path/del/tuo/file/immagine.jpg"*/);
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        waitingFrame.add(backgroundLabel, BorderLayout.CENTER);
        backgroundLabel.setLayout(new FlowLayout());

        JPanel textPanel = new JPanel();
        textPanel.setBackground(new Color(192, 192, 192));

        JLabel waitingLabel = new JLabel("Attendere connessione...");
        waitingLabel.setForeground(Color.YELLOW);

        Font labelFont = waitingLabel.getFont();
        waitingLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, 20));

        textPanel.add(waitingLabel);
        backgroundLabel.add(textPanel);
    }

    // Visualizza la schermata di attesa
    public void showWaitingScreen() {
        waitingFrame.setVisible(true);
    }

    // Crea il campo da gioco
    public void createGame(int rows, int columns) {
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int righe = rows;
        int colonne = columns;

        char[][] board = new char[righe][colonne];
        JButton[][] buttons = new JButton[righe][colonne];

        JPanel boardPanel = new JPanel(new GridLayout(righe, colonne));
        for (int i = 0; i < righe; i++) {
            for (int j = 0; j < colonne; j++) {
                board[i][j] = ' ';
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(80, 80));
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                boardPanel.add(buttons[i][j]);
            }
        }

        gameFrame.add(boardPanel, BorderLayout.CENTER);

        JLabel statusLabel = new JLabel("Turno: Giocatore X");
        gameFrame.add(statusLabel, BorderLayout.SOUTH);

        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
    }

    // Visualizza la partita
    public void showGame() {
        gameFrame.setVisible(true);
    }

    // Crea la schermata di disconnessione
    public void Disconnect() {
        disconnectFrame.setSize(500, 500);
        disconnectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        disconnectFrame.setLayout(new BorderLayout());

        ImageIcon backgroundIcon = new ImageIcon("path/del/tuo/file/immagine_sfondo.jpg");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        disconnectFrame.add(backgroundLabel, BorderLayout.CENTER);
        backgroundLabel.setLayout(new FlowLayout());

        JPanel textPanel = new JPanel();
        textPanel.setBackground(new Color(192, 192, 192));

        JLabel disconnectedLabel = new JLabel("L'avversario si è disconnesso");
        disconnectedLabel.setForeground(Color.YELLOW);
        disconnectedLabel.setFont(new Font(disconnectedLabel.getFont().getName(), Font.PLAIN, 24));

        textPanel.add(disconnectedLabel);
        backgroundLabel.add(textPanel);
    }

    // Visualizza la schermata di disconnessione
    public void showDisconnect() {
        disconnectFrame.setVisible(true);
    }

    // Visualizza la schermata di vincita/perdita
    public void createWinnerScreen() {
        winnerFrame.setSize(500, 500);
        winnerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        winnerFrame.setLayout(new BorderLayout());

        ImageIcon backgroundIcon = new ImageIcon("path/del/tuo/file/immagine_sfondo.jpg");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        winnerFrame.add(backgroundLabel, BorderLayout.CENTER);
        backgroundLabel.setLayout(new FlowLayout());

        JPanel textPanel = new JPanel();
        textPanel.setBackground(new Color(192, 192, 192));

        JLabel winnerLabel = new JLabel("HAI VINTO!!!");
        winnerLabel.setForeground(Color.YELLOW);
        winnerLabel.setFont(new Font(winnerLabel.getFont().getName(), Font.PLAIN, 24));

        textPanel.add(winnerLabel);

        ImageIcon trophyIcon = new ImageIcon("path/del/tuo/file/immagine_coppa.jpg");
        JLabel trophyLabel = new JLabel(trophyIcon);

        backgroundLabel.add(textPanel);
        backgroundLabel.add(trophyLabel);
    }

    // Visualizza la schermata di esito partita
    public void showWinnerScreen() {
        winnerFrame.setVisible(true);
    }

    // Schermata di fine gioco
    public void createFinishScreen() {
        finishFrame.setSize(500, 500);
        finishFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finishFrame.setLayout(new BorderLayout());

        ImageIcon backgroundIcon = new ImageIcon("path/del/tuo/file/immagine_sfondo_finish.jpg");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        finishFrame.add(backgroundLabel, BorderLayout.CENTER);
        backgroundLabel.setLayout(new FlowLayout());

        JPanel textPanel = new JPanel();
        textPanel.setBackground(new Color(192, 192, 192));

        JLabel finishLabel = new JLabel("Gioco terminato!");
        finishLabel.setForeground(Color.YELLOW);
        finishLabel.setFont(new Font(finishLabel.getFont().getName(), Font.PLAIN, 24));

        textPanel.add(finishLabel);

        // Puoi aggiungere ulteriori componenti o personalizzazioni in base alle tue esigenze

        backgroundLabel.add(textPanel);
    }

    public void showFinishScreen() {
        finishFrame.setVisible(true);
    }

    // Messaggio di errore
    public void messagError() {
        JOptionPane.showMessageDialog(null, "Comando errato!");
    }
}
