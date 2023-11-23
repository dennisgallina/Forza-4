import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Graphic extends JFrame {
    private boolean buttonConnectPressed;  // Variabile di stato per il pulsante "Connetti"
    private boolean buttonDisconnectPressed;  // Variabile di stato per il pulsante "Disconnetti"
    public boolean buttonPawnPressed;  // Variabile di stato per il pulsante "Pedina"
    public int buttonPawnPressedY;  // Coordinata Y della pedina premuta
    public int buttonPawnPressedX;  // Coordinata X della pedina premuta

    // Aggiungi l'immagine a ogni finestra
    private ImageIcon titleIcon = new ImageIcon("images/title.png");
    private JFrame gameFrame;  // Finestra principale del gioco

    // Costruttore
    public Graphic() {
        this.buttonConnectPressed = false;
        this.buttonDisconnectPressed = false;
        this.buttonPawnPressed = false;
        this.buttonPawnPressedY = -1;
        this.buttonPawnPressedX = -1;

        this.gameFrame = new JFrame("Forza 4");  // Inizializza la finestra principale
    }

    // Aggiungi l'immagine a ogni finestra
    private void addImageToFrame(JFrame frame) {
        JLabel backgroundLabel = new JLabel(titleIcon);
        frame.add(backgroundLabel, BorderLayout.CENTER);
    }

    public void resetFrame() {
        // Rimuovi tutti i componenti dalla content panel
        gameFrame.getContentPane().removeAll();
        gameFrame.getContentPane().revalidate();
        gameFrame.getContentPane().repaint();
    
        // Reimposta le variabili di stato
        buttonConnectPressed = false;
        buttonDisconnectPressed = false;
        buttonPawnPressed = false;
        buttonPawnPressedY = -1;
        buttonPawnPressedX = -1;
    }

    // Mostra la Lobby
    public void showLobby() {
        resetFrame();  // Ripristina la finestra

        updateTitle("Forza 4 - Lobby");  // Aggiorna il titolo della finestra

        gameFrame.setSize(600, 600);  // Imposta le dimensioni della finestra
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Imposta l'operazione di chiusura della finestra
        gameFrame.setVisible(true);  // Rende la finestra visibile

        addImageToFrame(gameFrame);  // Aggiungi l'immagine al titolo

        JPanel contentPanel = new JPanel();  // Crea un pannello per il contenuto della finestra
        contentPanel.setLayout(new BorderLayout());  // Imposta il layout del pannello principale

        // Crea un pannello con il titolo
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 102, 204));


        JLabel titleLabel = new JLabel("Benvenuto in Forza 4");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        titlePanel.add(titleLabel);
        contentPanel.add(titlePanel, BorderLayout.NORTH);

        // Crea un pannello per il pulsante con uno sfondo grigio chiaro
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));

        // Crea un pulsante con la scritta "GIOCA" in blu e dimensioni specifiche
        JButton playButton = new JButton("GIOCA");
        playButton.setForeground(new Color(0, 102, 204));  // Colore blu
        playButton.setPreferredSize(new Dimension(150, 50));

        // Aggiungi un ascoltatore per gestire l'azione del pulsante
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Imposta la variabile di stato quando il pulsante viene premuto
                buttonConnectPressed = true;
            }
        });

        // Aggiungi il pulsante al pannello
        buttonPanel.add(playButton);

        // Aggiungi il pannello del pulsante al pannello principale
        contentPanel.add(buttonPanel, BorderLayout.CENTER);

        // Crea un pannello decorativo
        JPanel decorationPanel = new JPanel();
        decorationPanel.setBackground(new Color(0, 102, 204));

        contentPanel.add(decorationPanel, BorderLayout.SOUTH);

        // Imposta il pannello principale come contenuto della finestra della lobby
        gameFrame.setContentPane(contentPanel);

        // Imposta le proprietà della finestra
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
    }

    // Mostra la schermata di attesa con un aspetto moderno
    public void showWaitingScreen() {
        resetFrame();  // Ripristina la finestra

        updateTitle("Forza 4 - Ricerca di un avversario");  // Aggiorna il titolo della finestra

        gameFrame.setSize(800, 600);  // Imposta le dimensioni della finestra in modo più grande
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Imposta l'operazione di chiusura della finestra
        gameFrame.setVisible(true);  // Rende la finestra visibile
        gameFrame.setLayout(new BorderLayout());  // Imposta il layout della finestra

        addImageToFrame(gameFrame);  // Aggiungi l'immagine al centro della finestra

        JPanel contentPanel = new JPanel(new BorderLayout());

        // Pannello con il testo e la barra di attesa
        JPanel textAndProgressBarPanel = new JPanel(new BorderLayout());
        textAndProgressBarPanel.setBackground(new Color(240, 240, 240));

        JLabel waitingLabel = new JLabel("RICERCA DI UN AVVERSARIO IN CORSO");
        waitingLabel.setForeground(Color.BLUE);
        Font labelFont = waitingLabel.getFont();
        waitingLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, 20));

        textAndProgressBarPanel.add(waitingLabel, BorderLayout.NORTH);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);  // Barra di progresso indeterminata per indicare l'attesa
        textAndProgressBarPanel.add(progressBar, BorderLayout.CENTER);

        contentPanel.add(textAndProgressBarPanel, BorderLayout.CENTER);

        gameFrame.setContentPane(contentPanel);  // Imposta il pannello principale come contenuto della finestra

        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
    }

    // Mostra il campo da gioco
    public void showPlayGround(int rows, int columns, Pawn[][] pawns, String playerName, String currentPlayerName) {
        resetFrame();  // Ripristina la finestra
    
        updateTitle("Forza 4 - Partita");  // Aggiorna il titolo della finestra
    
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Imposta l'operazione di chiusura della finestra
    
        char[][] board = new char[rows][columns];
        JButton[][] buttons = new JButton[rows][columns];
    
        JPanel boardPanel = new JPanel(new GridLayout(rows, columns));
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                board[row][column] = ' ';
                buttons[row][column] = new JButton();

                if (pawns[row][column] != null) {
                    if (pawns[row][column].color.equals("red")) {
                        buttons[row][column].setEnabled(false);
                        buttons[row][column].setBackground(Color.RED);
                    } else if (pawns[row][column].color.equals("yellow")) {
                        buttons[row][column].setEnabled(false);
                        buttons[row][column].setBackground(Color.YELLOW);
                    }
                }

                buttons[row][column].setPreferredSize(new Dimension(80, 80));
                buttons[row][column].setFont(new Font("Arial", Font.PLAIN, 40));
                boardPanel.add(buttons[row][column]);

                // Use final array to make variables effectively final
                final int[] position = {row, column};

                // Aggiungi un ActionListener al bottone
                buttons[row][column].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        buttonPawnPressed = true;
                        buttonPawnPressedY = position[0];
                        buttonPawnPressedX = position[1];
                    }
                });
            }
        }
    
        addImageToFrame(gameFrame);  // Aggiungi l'immagine al centro della finestra
    
        // Aggiungi un'etichetta per il nome del giocatore sopra la scacchiera
        JLabel playerNameLabel = new JLabel("Giocatore: " + playerName);
        playerNameLabel.setHorizontalAlignment(JLabel.CENTER);  // Allinea il testo al centro
        gameFrame.add(playerNameLabel, BorderLayout.NORTH);
    
        gameFrame.add(boardPanel, BorderLayout.CENTER);
    
        JLabel statusLabel = new JLabel("Turno: " + currentPlayerName);
        gameFrame.add(statusLabel, BorderLayout.SOUTH);
    
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
    }

    // Mostra la schermata di disconnessione
    public void showDisconnect() {
        resetFrame();  // Ripristina la finestra

        updateTitle("Forza 4 - Disconnesso");  // Aggiorna il titolo della finestra

        gameFrame.setSize(500, 500);  // Imposta le dimensioni della finestra
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Imposta l'operazione di chiusura della finestra
        gameFrame.setLayout(new BorderLayout());  // Imposta il layout della finestra

        addImageToFrame(gameFrame);  // Aggiungi l'immagine al centro della finestra

        JLabel backgroundLabel = new JLabel();
        gameFrame.add(backgroundLabel, BorderLayout.CENTER);
        backgroundLabel.setLayout(new FlowLayout());

        JPanel textPanel = new JPanel();
        textPanel.setBackground(new Color(192, 192, 192));

        JLabel disconnectedLabel = new JLabel("L'avversario si è disconnesso.");
        disconnectedLabel.setForeground(Color.YELLOW);
        disconnectedLabel.setFont(new Font(disconnectedLabel.getFont().getName(), Font.PLAIN, 24));

        textPanel.add(disconnectedLabel);
        backgroundLabel.add(textPanel);
    }

    // Mostra la schermata di vincita/perdita
    public void showWinnerScreen(String player, String WinnerPlayer) {
        resetFrame();  // Ripristina la finestra

        updateTitle("Forza 4 - Esito Partita");  // Aggiorna il titolo della finestra

        gameFrame.setSize(500, 500);  // Imposta le dimensioni della finestra
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Imposta l'operazione di chiusura della finestra
        gameFrame.setLayout(new BorderLayout());  // Imposta il layout della finestra

        addImageToFrame(gameFrame);  // Aggiungi l'immagine al centro della finestra

        JLabel backgroundLabel = new JLabel();
        gameFrame.add(backgroundLabel, BorderLayout.CENTER);
        backgroundLabel.setLayout(new FlowLayout());

        JPanel textPanel = new JPanel();
        textPanel.setBackground(new Color(192, 192, 192));

        JLabel winnerLabel = new JLabel();
        if (player.equals(WinnerPlayer))
            winnerLabel = new JLabel("HAI VINTO!");
        else if (!player.equals(WinnerPlayer))
            winnerLabel = new JLabel("HAI PERSO!");
            
        winnerLabel.setForeground(Color.YELLOW);
        winnerLabel.setFont(new Font(winnerLabel.getFont().getName(), Font.PLAIN, 24));

        textPanel.add(winnerLabel);
        backgroundLabel.add(textPanel);
    }

    // Mostra la schermata di fine gioco
    public void showFinishScreen() {
        resetFrame();  // Ripristina la finestra

        updateTitle("Forza 4 - Partita Terminata");  // Aggiorna il titolo della finestra

        gameFrame.setSize(500, 500);  // Imposta le dimensioni della finestra
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Imposta l'operazione di chiusura della finestra
        gameFrame.setLayout(new BorderLayout());  // Imposta il layout della finestra

        addImageToFrame(gameFrame);  // Aggiungi l'immagine al centro della finestra

        JLabel backgroundLabel = new JLabel();
        gameFrame.add(backgroundLabel, BorderLayout.CENTER);
        backgroundLabel.setLayout(new FlowLayout());

        JPanel textPanel = new JPanel();
        textPanel.setBackground(new Color(192, 192, 192));

        JLabel finishLabel = new JLabel("Gioco terminato!");
        finishLabel.setForeground(Color.YELLOW);
        finishLabel.setFont(new Font(finishLabel.getFont().getName(), Font.PLAIN, 24));

        textPanel.add(finishLabel);
        backgroundLabel.add(textPanel);
    }

    // Messaggio di errore
    public void messagError() {
        JOptionPane.showMessageDialog(null, "Comando errato!");
    }

    // Controllo bottone connetti
    public boolean isButtonConnectPressed() {
        return buttonConnectPressed;
    }

    // Controllo bottone disconnetti
    public boolean isButtonDiconnectPressed() {
        return buttonDisconnectPressed;
    }

    // Controllo bottone disconnetti
    public boolean isButtonPawnPressed() {
        return buttonPawnPressed;
    }

    // Aggiorna il titolo della finestra
    private void updateTitle(String title) {
        gameFrame.setTitle(title);
    }
}