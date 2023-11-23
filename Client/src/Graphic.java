import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Graphic extends JFrame {
    private boolean buttonConnectPressed;  // Variabile di stato per il pulsante "Connetti"
    private boolean buttonDisconnectPressed;  // Variabile di stato per il pulsante "Disconnetti"
    private boolean buttonPawnPressed;  // Variabile di stato per il pulsante "Pedina"
    public int buttonPawnPressedY;  // Coordinata Y della pedina premuta
    public int buttonPawnPressedX;  // Coordinata X della pedina premuta

    // Aggiungi l'immagine a ogni finestra
    private ImageIcon titleIcon = new ImageIcon("images/sfondo.jpg");
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

        JButton btnGioca;

        // Imposta le dimensioni dello schermo a 1920x1080
        gameFrame.setSize(1920, 1080);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Imposta lo sfondo con l'immagine sfondo.jpg direttamente sul ContentPane
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            gameFrame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(classLoader.getResourceAsStream("images/sfondo.jpg")))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Crea il bottone "PLAY!!" e imposta le dimensioni
        btnGioca = new JButton("GIOCA");
        btnGioca.setPreferredSize(new Dimension(200, 50));

        // Imposta la dimensione del testo in grassetto e più grande
        Font buttonFont = new Font(btnGioca.getFont().getName(), Font.BOLD, 24);
        btnGioca.setFont(buttonFont);

        // Aggiungi un listener per gestire il clic sul pulsante
        btnGioca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Quando il pulsante viene premuto, ritorna true
                buttonConnectPressed = true;
            }
        });

        // Posiziona il pulsante utilizzando i margini
        Insets insets = getInsets();
        btnGioca.setMargin(new Insets(insets.top + 10, 10, 10, 10));

        // Imposta il layout manager del ContentPane a null
        gameFrame.setLayout(null);

        // Centra il pulsante orizzontalmente
        Dimension size = btnGioca.getPreferredSize();
        btnGioca.setBounds((gameFrame.getWidth() - size.width) / 2, insets.top, size.width, size.height);

        // Aggiungi il bottone direttamente al ContentPane
        gameFrame.add(btnGioca);

        // Aggiungi un Timer per cambiare costantemente il colore del pulsante
        Timer colorTimer = new Timer(100, new ActionListener() {
            float hue = 0; // Hue iniziale

            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambia gradualmente il colore
                hue = (hue + 0.01f) % 1.0f;
                Color newColor = Color.getHSBColor(hue, 1, 1);
                btnGioca.setForeground(newColor);
            }
        });

        // Avvia il timer
        colorTimer.start();

        // Imposta le proprietà della finestra
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);

        // Rendi la finestra visibile dopo aver applicato tutte le modifiche
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
        // Ripristina la finestra
        // (Se il metodo resetFrame() è definito altrove nel codice, commenta questa riga)

        // Aggiorna il titolo della finestra
        updateTitle("Forza 4 - Disconnesso");

        // Imposta le dimensioni della finestra
        gameFrame.setSize(1920, 1080);

        // Imposta l'operazione di chiusura della finestra
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Imposta il layout della finestra
        gameFrame.setLayout(new BorderLayout());

        try {
            // Carica l'immagine di sfondo
            File imgFile = new File("images/sfondo.jpg");
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
            JLabel disconnectedLabel = new JLabel("<html><font color='white' size='9'><b>L'AVVERSARIO SI È DISCONNESSO!!</b></font></html>");

            textPanel.add(disconnectedLabel);

            // Aggiungi il pannello del testo al JLabel di sfondo
            backgroundLabel.add(textPanel);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rendi la finestra visibile
        gameFrame.setVisible(true);
    }

    // Mostra la schermata di vincita/perdita
    public void showWinnerScreen() {
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

        JLabel winnerLabel = new JLabel("HAI VINTO!");
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