import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Graphic extends JFrame {
    private boolean buttonConnectPressed;
    private boolean buttonDisconnectPressed;
    private boolean buttonPawnPressed;
    public int buttonPawnPressedY;
    public int buttonPawnPressedX;

    private JFrame lobbyFrame; // Finestra Lobby
    private JFrame waitingFrame; // Finestra Attesa dell'avversario
    private JFrame playGroundFrame; // Finestra Campo
    private JFrame disconnectFrame; // Finestra Disconnessione
    private JFrame winnerFrame; // Finestra Esito della partita
    private JFrame finishFrame; // Finestra Fine partita

    public Graphic() {
        this.buttonConnectPressed = false;
        this.buttonDisconnectPressed = false;
        this.buttonPawnPressed = false;
        this.buttonPawnPressedY = -1;
        this.buttonPawnPressedX = -1;

        this.lobbyFrame = new JFrame("Forza 4 - Lobby");
        this.waitingFrame = new JFrame("Forza 4 - Ricerca di un avversario");
        this.playGroundFrame = new JFrame("Forza 4 - Partita");
        this.disconnectFrame = new JFrame("Forza 4 - Disconnesso");
        this.winnerFrame = new JFrame("Forza 4 - Esito Partita");
        this.finishFrame = new JFrame("Forza 4 - Partita Terminata");
    }

    // Crea Lobby
    public void createLobby() {
        lobbyFrame.setSize(500, 500);
        lobbyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        // Crea un pannello per il pulsante con uno sfondo grigio chiaro
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));

        // Crea un pulsante con la scritta "GIOCA" in blu e dimensioni specifiche
        JButton playButton = new JButton("GIOCA");
        playButton.setForeground(new Color(0, 102, 204)); // Colore blu
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

        // Aggiungi il pannello del pulsante al pannello principale
        contentPanel.add(buttonPanel, BorderLayout.CENTER);

        // Imposta il pannello principale come contenuto della finestra della lobby
        lobbyFrame.setContentPane(contentPanel);

        // Imposta le proprietà della finestra
        lobbyFrame.pack();
        lobbyFrame.setLocationRelativeTo(null);
        lobbyFrame.setVisible(true);
    }

    // Visualizza la Lobby
    public void showLobby() {
        lobbyFrame.setVisible(true);
    }

    // Crea la schermata di attesa
    public void createWaitingScreen() {
        waitingFrame.setSize(500, 500);
        waitingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        waitingFrame.setLayout(new BorderLayout());

        JLabel backgroundLabel = new JLabel();
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
    public void createPlayGround(int rows, int columns, Pawn[][] pawns, String currentPlayerName) {
        playGroundFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        char[][] board = new char[rows][columns];
        JButton[][] buttons = new JButton[rows][columns];

        JPanel boardPanel = new JPanel(new GridLayout(rows, columns));
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                board[row][column] = ' ';
                buttons[row][column] = new JButton();

                if (pawns[row][column].color.equals("red")) {
                    buttons[row][column].setEnabled(false);
                    buttons[row][column].setBackground(Color.RED);
                } else if (pawns[row][column].color.equals("yellow")) {
                    buttons[row][column].setEnabled(false);
                    buttons[row][column].setBackground(Color.YELLOW);
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

        playGroundFrame.add(boardPanel, BorderLayout.CENTER);

        JLabel statusLabel = new JLabel("Turno: " + currentPlayerName);
        playGroundFrame.add(statusLabel, BorderLayout.SOUTH);

        playGroundFrame.pack();
        playGroundFrame.setLocationRelativeTo(null);
    }

    // Visualizza la partita
    public void showPlayGround() {
        playGroundFrame.setVisible(true);
    }

    // Crea la schermata di disconnessione
    public void Disconnect() {
        disconnectFrame.setSize(500, 500);
        disconnectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        disconnectFrame.setLayout(new BorderLayout());

        JLabel backgroundLabel = new JLabel();
        disconnectFrame.add(backgroundLabel, BorderLayout.CENTER);
        backgroundLabel.setLayout(new FlowLayout());

        JPanel textPanel = new JPanel();
        textPanel.setBackground(new Color(192, 192, 192));

        JLabel disconnectedLabel = new JLabel("L'avversario si è disconnesso.");
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

        JLabel backgroundLabel = new JLabel();
        winnerFrame.add(backgroundLabel, BorderLayout.CENTER);
        backgroundLabel.setLayout(new FlowLayout());

        JPanel textPanel = new JPanel();
        textPanel.setBackground(new Color(192, 192, 192));

        JLabel winnerLabel = new JLabel("HAI VINTO!");
        winnerLabel.setForeground(Color.YELLOW);
        winnerLabel.setFont(new Font(winnerLabel.getFont().getName(), Font.PLAIN, 24));

        textPanel.add(winnerLabel);

        // Puoi aggiungere ulteriori componenti o personalizzazioni in base alle tue esigenze

        backgroundLabel.add(textPanel);
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

        JLabel backgroundLabel = new JLabel();
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

    // Controllo bottone connetti
    public boolean isButtonConnectPressed() {
        return buttonConnectPressed;
    }

    // Controllo bottone disconneti
    public boolean isButtonDiconnectPressed() {
        return buttonDisconnectPressed;
    }

    // Controllo bottone disconneti
    public boolean isButtonPawnPressed() {
        return buttonPawnPressed;
    }
}
