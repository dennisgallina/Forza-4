import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Graphic extends JFrame {
    private boolean buttonConnectPressed;  // Variabile di stato per il pulsante "Connetti"
    private boolean buttonDisconnectPressed;  // Variabile di stato per il pulsante "Disconnetti"
    public boolean buttonPawnPressed;  // Variabile di stato per il pulsante "Pedina"
    public int buttonPawnPressedY;  // Coordinata Y della pedina premuta
    public int buttonPawnPressedX;  // Coordinata X della pedina premuta
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
    
        // Imposta le dimensioni della finestra a 800x600
        gameFrame.setSize(800, 600);
    
        // Imposta l'operazione di chiusura della finestra
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        try {
            // Utilizziamo getClass().getResourceAsStream per ottenere un InputStream dal classpath
            InputStream stream = getClass().getResourceAsStream("images/sfondo.jpg");
            if (stream != null) {
                // Leggi l'immagine dallo stream
                BufferedImage backgroundImage = ImageIO.read(stream);
                
                // Ridimensiona l'immagine alla dimensione della finestra
                Image resizedImage = backgroundImage.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
                
                // Imposta il contenuto della finestra con l'immagine di sfondo ridimensionata
                gameFrame.setContentPane(new JLabel(new ImageIcon(resizedImage)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        // Crea un pulsante "PLAY!!" con dimensioni e stile predefiniti
        JButton btnGioca = new JButton("PLAY!!");
        btnGioca.setPreferredSize(new Dimension(150, 45));
    
        // Imposta lo stile del testo del pulsante
        Font buttonFont = new Font(btnGioca.getFont().getName(), Font.BOLD, 30);
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
        JPanel emptyPanel = new JPanel();
        gameFrame.add(emptyPanel, BorderLayout.CENTER);
        gameFrame.remove(emptyPanel);

        updateTitle("Forza 4 - Ricerca di un avversario");  // Aggiorna il titolo della finestra

        gameFrame.setSize(800, 600);  // Imposta le dimensioni della finestra in modo più grande
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Imposta l'operazione di chiusura della finestra
        gameFrame.setVisible(true);  // Rende la finestra visibile
        gameFrame.setLayout(new BorderLayout());  // Imposta il layout della finestra

        //addImageToFrame(gameFrame);  // Aggiungi l'immagine al centro della finestra

        // Pannello principale che conterrà il testo e la barra di attesa
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Pannello contenente il testo informativo e la barra di attesa
        JPanel textAndProgressBarPanel = new JPanel(new BorderLayout());
        textAndProgressBarPanel.setBackground(new Color(240, 240, 240));

        // Etichetta che indica che la ricerca di un avversario è in corso
        JLabel waitingLabel = new JLabel("RICERCA DI UN AVVERSARIO IN CORSO...");
        waitingLabel.setForeground(Color.BLUE);
        Font labelFont = waitingLabel.getFont();
        waitingLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, 20));

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

    //metodo mostra campo da gioco
    public void showPlayGround(int rows, int columns, Pawn[][] pawns, String playerName, String currentPlayerName) {
        // Ripristina la finestra
        resetFrame();
    
        // Aggiorna il titolo della finestra
        updateTitle("Forza 4 - Partita");
    
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
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
    
                final int[] position = {row, column};
    
                buttons[row][column].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Esegui azioni in base alla pedina premuta
                        buttonPawnPressed = true;
                        buttonPawnPressedY = position[0];
                        buttonPawnPressedX = position[1];
                    }
                });
            }
        }
    
        // Aggiungi un pannello per il pulsante "DISCONNETTI!!" in rosso sopra il campo da gioco
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton disconnectButton = new JButton("DISCONNETTI!!");
        disconnectButton.setForeground(Color.BLACK);
        if (playerName.equals("Player 1"))
            disconnectButton.setBackground(Color.RED);
        else
            disconnectButton.setBackground(Color.YELLOW);
    
        disconnectButton.setFont(new Font("Arial", Font.PLAIN, 20));  // Modifica la dimensione del font a 30
    
        disconnectButton.setPreferredSize(new Dimension(190, 30));  // Modifica le dimensioni del pulsante
        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aggiungi azioni per la disconnessione
                buttonDisconnectPressed = true;
            }
        });
        buttonPanel.add(disconnectButton);
    
        // Aggiungi un'etichetta per il nome del giocatore sopra la scacchiera
        JLabel playerNameLabel = new JLabel("Giocatore: " + playerName);
        playerNameLabel.setHorizontalAlignment(JLabel.CENTER);
    
        // Aggiungi il pannello del pulsante sopra l'etichetta del giocatore
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(playerNameLabel, BorderLayout.CENTER);
        mainPanel.add(boardPanel, BorderLayout.SOUTH);
    
        gameFrame.add(mainPanel);
    
        JLabel statusLabel = new JLabel("Turno: " + currentPlayerName);
        gameFrame.add(statusLabel, BorderLayout.SOUTH);
    
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }
    

    // Mostra la schermata di disconnessione
    public void showDisconnect() {
        // Ripristina la finestra
        resetFrame();

        gameFrame.setSize(800, 600);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(new BorderLayout());

        // Pannello principale che conterrà il testo e la barra di attesa
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Pannello contenente il testo informativo e la barra di attesa
        JPanel textAndProgressBarPanel = new JPanel(new BorderLayout());
        textAndProgressBarPanel.setBackground(new Color(240, 240, 240));

        // Etichetta che indica la disconnessione
        JLabel disconnectLabel = new JLabel("DISCONNESSIONE IN CORSO...");
        disconnectLabel.setForeground(Color.BLUE);
        Font labelFont = disconnectLabel.getFont();
        disconnectLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, 20));

        textAndProgressBarPanel.add(disconnectLabel, BorderLayout.NORTH);

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
        gameFrame.setVisible(true);

        // Simula un ritardo per l'animazione di caricamento (rimuovi questa parte nel tuo codice reale)
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Chiudi il frame dopo il ritardo simulato (rimuovi o sostituisci con la tua logica di connessione)
                gameFrame.dispose();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void showEnemyDisconnected(){
        // Ripristina la finestra
        resetFrame();

        gameFrame.setSize(800, 600);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(new BorderLayout());

        // Pannello principale che conterrà il testo e la barra di attesa
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Pannello contenente il testo informativo e la barra di attesa
        JPanel textAndProgressBarPanel = new JPanel(new BorderLayout());
        textAndProgressBarPanel.setBackground(new Color(240, 240, 240));

        // Etichetta che indica la disconnessione
        JLabel disconnectLabel = new JLabel("L'AVVERSARIO SI È DISCONNESSO, DISCONNESSIONE IN CORSO...");
        disconnectLabel.setForeground(Color.BLUE);
        Font labelFont = disconnectLabel.getFont();
        disconnectLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, 20));

        textAndProgressBarPanel.add(disconnectLabel, BorderLayout.NORTH);

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
        gameFrame.setVisible(true);

        // Simula un ritardo per l'animazione di caricamento (rimuovi questa parte nel tuo codice reale)
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Chiudi il frame dopo il ritardo simulato (rimuovi o sostituisci con la tua logica di connessione)
                gameFrame.dispose();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    // Mostra la schermata di vincita/perdita
    public void showWinnerScreen(String player, String WinnerPlayer) {
        resetFrame();  // Ripristina la finestra

        updateTitle("Forza 4 - Esito Partita");  // Aggiorna il titolo della finestra

        gameFrame.setSize(800, 600);  // Imposta le dimensioni della finestra
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Imposta l'operazione di chiusura della finestra
        gameFrame.setLayout(new BorderLayout());  // Imposta il layout della finestra

        CustomPanel backgroundPanel = new CustomPanel();
        JPanel textPanel = new JPanel();

        if (player.equals(WinnerPlayer))
        {
            try {
                JLabel label = new JLabel("CONGRATULAZIONI, HAI VINTO!!");

                // Esempio utilizzando le risorse del classpath
                InputStream inputStream = getClass().getResourceAsStream("images/winner.jpg");
                Image originalImage = ImageIO.read(inputStream);
        
                int newWidth = gameFrame.getWidth();
                int newHeight = gameFrame.getHeight();
        
                Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                ImageIcon backgroundIcon = new ImageIcon(scaledImage);

                backgroundPanel.setBI(backgroundIcon);
                    
                textPanel.setOpaque(false);

                label.setForeground(Color.YELLOW);
                label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 35));

                textPanel.add(label);

                // Aggiungi l'animazione di lampeggio continuo
                Timer flickerTimer = new Timer(500, new ActionListener() {
                    private boolean visible = true;
        
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        visible = !visible;
                        label.setVisible(visible);
                    }
                });
                flickerTimer.setInitialDelay(0);
                flickerTimer.start();
        
                // Aggiungi l'effetto di coriandoli
                Timer coriandoliTimer = new Timer(50, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        backgroundPanel.generateCoriandoli();
                        backgroundPanel.updateCoriandoli();
                        backgroundPanel.repaint();
                    }
                });
                coriandoliTimer.setInitialDelay(0);
                coriandoliTimer.start();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
            backgroundPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
            backgroundPanel.add(textPanel);

            gameFrame.add(backgroundPanel, BorderLayout.CENTER);
            gameFrame.setVisible(true);
        } 
        else if (!player.equals(WinnerPlayer)) {
            try {
                InputStream inputStream = getClass().getResourceAsStream("images/gameOver.jpg");
                Image originalImage = ImageIO.read(inputStream);
        
                int newWidth = gameFrame.getWidth();
                int newHeight = gameFrame.getHeight();
        
                Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                ImageIcon backgroundIcon = new ImageIcon(scaledImage);
        
                backgroundPanel.setBI(backgroundIcon);
                
                backgroundPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
                backgroundPanel.add(textPanel);

                gameFrame.add(backgroundPanel, BorderLayout.CENTER);
                gameFrame.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Mostra la schermata di fine gioco
    public void showFinishScreen() {
        // Aggiorna il titolo della finestra
        updateTitle("Forza 4 - Partita Terminata");

        // Imposta le dimensioni della finestra a 800x600
        gameFrame.setSize(800, 600);
        // Imposta l'operazione di chiusura della finestra
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Imposta il layout della finestra
        gameFrame.setLayout(new BorderLayout());

        try {
            // Utilizziamo getClass().getResourceAsStream per ottenere un InputStream dal classpath
            InputStream stream = getClass().getResourceAsStream("/images/finish.jpg");
            if (stream != null) {
                // Leggi l'immagine dallo stream
                BufferedImage finishImage = ImageIO.read(stream);
                
                // Ridimensiona l'immagine alla dimensione della finestra
                Image resizedImage = finishImage.getScaledInstance(800, 600, Image.SCALE_SMOOTH);

                // Aggiungi l'immagine al centro della finestra
                JLabel backgroundLabel = new JLabel(new ImageIcon(resizedImage));
                gameFrame.add(backgroundLabel, BorderLayout.CENTER);
                backgroundLabel.setLayout(new FlowLayout());

                
                JPanel textPanel = new JPanel();
                textPanel.setOpaque(false);  // Imposta il pannello come trasparente

                JLabel finishLabel = new JLabel("GIOCO TERMINATO!");
                finishLabel.setForeground(Color.WHITE);  // Imposta il colore del testo a bianco
                finishLabel.setFont(new Font(finishLabel.getFont().getName(), Font.PLAIN, 30));  // Imposta la dimensione del testo a 30 punti

                textPanel.add(finishLabel);
                backgroundLabel.add(textPanel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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