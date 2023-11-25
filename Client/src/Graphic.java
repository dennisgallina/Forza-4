import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

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
        // Ripristina la finestra
        resetFrame();  

        // Aggiorna il titolo della finestra
        updateTitle("Forza 4 - Lobby");  

        // Imposta le dimensioni della finestra a 1920x1080
        gameFrame.setSize(1920, 1080);

        // Imposta l'operazione di chiusura della finestra
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            // Utilizziamo getClass().getResourceAsStream per ottenere un InputStream dal classpath
            InputStream stream = getClass().getResourceAsStream("/images/sfondo.jpg");
            if (stream != null) 
                // Imposta il contenuto della finestra con un'immagine di sfondo
                gameFrame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(stream))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Crea un pulsante "PLAY!!" con dimensioni e stile predefiniti
        JButton btnGioca = new JButton("PLAY!!");
        btnGioca.setPreferredSize(new Dimension(200, 50));

        // Imposta lo stile del testo del pulsante
        Font buttonFont = new Font(btnGioca.getFont().getName(), Font.BOLD, 24);
        btnGioca.setFont(buttonFont);

        // Aggiungi un listener per gestire il clic sul pulsante
        btnGioca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Quando il pulsante viene premuto, imposta una variabile (presumibilmente dichiarata altrove) a true
                buttonConnectPressed = true;
            }
        });

        // Ottieni i margini della finestra
        Insets insets = gameFrame.getInsets();

        // Imposta i margini del pulsante
        btnGioca.setMargin(new Insets(insets.top + 10, 10, 10, 10));

        // Imposta il layout della finestra a null
        gameFrame.setLayout(null);

        // Posiziona il pulsante al centro orizzontalmente
        Dimension size = btnGioca.getPreferredSize();
        btnGioca.setBounds((gameFrame.getWidth() - size.width) / 2, insets.top, size.width, size.height);

        // Aggiungi il pulsante alla finestra
        gameFrame.add(btnGioca);

        // Crea un timer per cambiare il colore del testo del pulsante dinamicamente
        Timer colorTimer = new Timer(100, new ActionListener() {
            float hue = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambia gradualmente il colore del testo
                hue = (hue + 0.01f) % 1.0f;
                Color newColor = Color.getHSBColor(hue, 1, 1);
                btnGioca.setForeground(newColor);
            }
        });

        // Avvia il timer
        colorTimer.start();

        // Ridimensiona la finestra in base ai componenti aggiunti
        gameFrame.pack();

        // Centra la finestra sullo schermo
        gameFrame.setLocationRelativeTo(null);

        // Rendi la finestra visibile
        gameFrame.setVisible(true);
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

        // Pannello principale che conterrà il testo e la barra di attesa
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Pannello contenente il testo informativo e la barra di attesa
        JPanel textAndProgressBarPanel = new JPanel(new BorderLayout());
        textAndProgressBarPanel.setBackground(new Color(240, 240, 240));

        // Etichetta che indica che la ricerca di un avversario è in corso
        JLabel waitingLabel = new JLabel("RICERCA DI UN AVVERSARIO IN CORSO");
        waitingLabel.setForeground(Color.BLUE);
        Font labelFont = waitingLabel.getFont();
        waitingLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, 48));

        textAndProgressBarPanel.add(waitingLabel, BorderLayout.NORTH);

        // Barra di progresso indeterminata per indicare l'attesa
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        textAndProgressBarPanel.add(progressBar, BorderLayout.CENTER);

        // Aggiungi il pannello di testo e barra di attesa al pannello principale
        contentPanel.add(textAndProgressBarPanel, BorderLayout.CENTER);

        // Imposta il pannello principale come contenuto della finestra
        gameFrame.setContentPane(contentPanel);

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
        gameFrame.setSize(1920, 1080);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(new BorderLayout());

        try {
            // Carica l'immagine di sfondo
            File imgFile = new File("images/disconnect.jpg");
            Image backgroundImage = ImageIO.read(imgFile);

            // Crea un JLabel con l'immagine di sfondo
            JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
            gameFrame.add(backgroundLabel, BorderLayout.CENTER);
            backgroundLabel.setLayout(new FlowLayout());

            // Crea il pannello del testo
            JPanel textPanel = new JPanel();
            textPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            textPanel.setOpaque(false);

            // Crea la JLabel con la scritta ancora più grande e bianca
            JLabel disconnectedLabel = new JLabel("L'AVVERSARIO SI È DISCONNESSO!!");
            disconnectedLabel.setForeground(Color.WHITE);
            disconnectedLabel.setFont(new Font(disconnectedLabel.getName(), Font.PLAIN, 48));

            textPanel.add(disconnectedLabel);

            // Aggiungi il pannello del testo al JLabel di sfondo
            backgroundLabel.add(textPanel);

        } catch (IOException e) {
            e.printStackTrace();
        }

        gameFrame.setVisible(true);
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
        winnerLabel.setFont(new Font(winnerLabel.getFont().getName(), Font.PLAIN, 48));

        textPanel.add(winnerLabel);
        backgroundLabel.add(textPanel);
    }

    // Mostra la schermata di fine gioco
    public void showFinishScreen() {
        // Aggiorna il titolo della finestra
        gameFrame.setTitle("Forza 4 - Partita Terminata");
        
        // Imposta le dimensioni della finestra a 1920x1080
        gameFrame.setSize(1920, 1080);

        // Imposta l'operazione di chiusura della finestra
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Imposta il layout della finestra
        gameFrame.setLayout(new BorderLayout());

        // Aggiungi l'immagine al centro della finestra
        JLabel backgroundLabel = new JLabel();
        gameFrame.add(backgroundLabel, BorderLayout.CENTER);
        backgroundLabel.setLayout(new FlowLayout());

        // Aggiungi l'immagine
        ImageIcon imageIcon = new ImageIcon("images/finish.jpg");  // Sostituisci con il percorso effettivo dell'immagine
        backgroundLabel.setIcon(imageIcon);

        //da scegliere cosa fare
        /*JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);  // Imposta il pannello come trasparente

        JLabel finishLabel = new JLabel("GIOCO TERMINATO!");
        finishLabel.setForeground(Color.WHITE);  // Imposta il colore del testo a bianco
        finishLabel.setFont(new Font(finishLabel.getFont().getName(), Font.PLAIN, 48));  // Imposta la dimensione del testo a 72 punti

        textPanel.add(finishLabel);
        backgroundLabel.add(textPanel);*/

        // Rendi la finestra visibile
        gameFrame.setVisible(true);
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