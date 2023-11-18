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

    public Graphic(){
        buttonConnectPressed = false;
        buttonDisconnectPressed = false;
    }

    //creazione lobby
    public void createLobby() {
        // Imposta il titolo e le dimensioni della finestra
        setTitle("Lobby Creator");
        setSize(500, 500);
    
        // Chiudi l'applicazione quando la finestra viene chiusa
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        // Imposta il layout principale della finestra
        setLayout(new BorderLayout());
    
        // Aggiungi uno sfondo con un'immagine a tua scelta al centro della finestra
        ImageIcon backgroundIcon = new ImageIcon("path/del/tuo/file/immagine.jpg");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        add(backgroundLabel, BorderLayout.CENTER);
        backgroundLabel.setLayout(new FlowLayout());
    
        // Crea un pannello per il pulsante con uno sfondo grigio chiaro
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(192, 192, 192));
    
        // Crea un pulsante con la scritta "GIOCA!!" in giallo e dimensioni specifiche
        JButton playButton = new JButton("GIOCA!!");
        playButton.setForeground(Color.YELLOW);
        playButton.setPreferredSize(new Dimension(150, 50));
    
        // Aggiungi un ascoltatore per gestire l'azione del pulsante
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Imposta la variabile di stato quando il pulsante viene premuto
                buttonConnectPressed = true;
    
                // Mostra un messaggio di avviso
                JOptionPane.showMessageDialog(null, "Lobby creata!");
            }
        });
    
        // Aggiungi il pulsante al pannello
        buttonPanel.add(playButton);
    
        // Aggiungi il pannello con il pulsante allo sfondo
        backgroundLabel.add(buttonPanel);
    }

    //controllo bottone connetti
    public boolean isButtonConnectPressed() {
        return buttonConnectPressed;
    }

    //controllo bottone disconneti
    public boolean isButtonDiconnectPressed() {
        return buttonDisconnectPressed;
    }

    //schermata vincita
    public void createWaitingScreen() {
        // Imposta il titolo e le dimensioni della finestra
        setTitle("Waiting Screen");
        setSize(500, 500);
    
        // Chiudi l'applicazione quando la finestra viene chiusa
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        // Imposta il layout principale della finestra
        setLayout(new BorderLayout());
    
        // Aggiungi uno sfondo con un'immagine a tua scelta al centro della finestra
        ImageIcon backgroundIcon = new ImageIcon("path/del/tuo/file/immagine.jpg");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        add(backgroundLabel, BorderLayout.CENTER);
        backgroundLabel.setLayout(new FlowLayout());
    
        // Crea un pannello per la scritta con uno sfondo grigio chiaro
        JPanel textPanel = new JPanel();
        textPanel.setBackground(new Color(192, 192, 192));
    
        // Crea una JLabel con la scritta "Attendere connessione..." in giallo
        JLabel waitingLabel = new JLabel("Attendere connessione...");
        waitingLabel.setForeground(Color.YELLOW);
    
        // Imposta il font per rendere il testo più evidente
        Font labelFont = waitingLabel.getFont();
        waitingLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, 20));
    
        // Aggiungi la JLabel al pannello
        textPanel.add(waitingLabel);
    
        // Aggiungi il pannello con la scritta allo sfondo
        backgroundLabel.add(textPanel);
    }

    //creo il campo da gioco
    public void createCamp(int rows, int col){
        char[][] board; // La matrice per memorizzare il campo di gioco
        JButton[][] buttons; // Una matrice di bottoni per rappresentare il campo di gioco graficamente
        JFrame frame; // La finestra principale dell'applicazione
        JLabel statusLabel; // Una label per visualizzare lo stato corrente del gioco

        frame = new JFrame("Forza 4"); // Creazione della finestra
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Richiesta all'utente di inserire il numero di righe e colonne
        int righe = rows;
        int colonne = col;

        board = new char[righe][colonne]; // Inizializzazione della matrice del campo di gioco
        buttons = new JButton[righe][colonne]; // Inizializzazione della matrice di bottoni

        JPanel boardPanel = new JPanel(new GridLayout(righe, colonne)); // Creazione di un pannello con layout a griglia per i bottoni
        for (int i = 0; i < righe; i++) {
            for (int j = 0; j < colonne; j++) {
                board[i][j] = ' '; // Inizializzazione del campo di gioco con spazi vuoti
                buttons[i][j] = new JButton(); // Creazione di un nuovo bottone
                buttons[i][j].setPreferredSize(new Dimension(80, 80)); // Impostazione delle dimensioni del bottone
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40)); // Impostazione del font del testo del bottone
                boardPanel.add(buttons[i][j]); // Aggiunta del bottone al pannello
            }
        }

        frame.add(boardPanel, BorderLayout.CENTER); // Aggiunta del pannello con i bottoni al centro della finestra

        statusLabel = new JLabel("Turno: Giocatore X"); // Creazione di una label per visualizzare lo stato del gioco
        frame.add(statusLabel, BorderLayout.SOUTH); // Aggiunta della label in basso alla finestra

        frame.pack(); // Adattamento delle dimensioni della finestra in base al contenuto
        frame.setLocationRelativeTo(null); // Posizionamento della finestra al centro dello schermo
        frame.setVisible(true); // Rendere la finestra visibile
    }
    
    //l'avversario si è disconnesso
    public void Disconnect() {
        // Imposta il titolo e le dimensioni della finestra
        setTitle("Opponent Disconnected Screen");
        setSize(500, 500);
    
        // Chiudi l'applicazione quando la finestra viene chiusa
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        // Imposta il layout principale della finestra
        setLayout(new BorderLayout());
    
        // Aggiungi uno sfondo con un'immagine a tua scelta al centro della finestra
        ImageIcon backgroundIcon = new ImageIcon("path/del/tuo/file/immagine_sfondo.jpg");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        add(backgroundLabel, BorderLayout.CENTER);
        backgroundLabel.setLayout(new FlowLayout());
    
        // Crea un pannello per la scritta con uno sfondo grigio chiaro
        JPanel textPanel = new JPanel();
        textPanel.setBackground(new Color(192, 192, 192));
    
        // Crea una JLabel con la scritta "L'avversario si è disconnesso" in giallo
        JLabel disconnectedLabel = new JLabel("L'avversario si è disconnesso");
        disconnectedLabel.setForeground(Color.YELLOW);
        disconnectedLabel.setFont(new Font(disconnectedLabel.getFont().getName(), Font.PLAIN, 24));
    
        // Aggiungi la JLabel al pannello
        textPanel.add(disconnectedLabel);
    
        // Aggiungi il pannello con la scritta allo sfondo
        backgroundLabel.add(textPanel);
    }
    //hai vinto
    public void WinnerScreenCreator() {
        // Imposta il titolo e le dimensioni della finestra
        setTitle("Winner Screen");
        setSize(500, 500);
    
        // Chiudi l'applicazione quando la finestra viene chiusa
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        // Imposta il layout principale della finestra
        setLayout(new BorderLayout());
    
        // Aggiungi uno sfondo con un'immagine a tua scelta al centro della finestra
        ImageIcon backgroundIcon = new ImageIcon("path/del/tuo/file/immagine_sfondo.jpg");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        add(backgroundLabel, BorderLayout.CENTER);
        backgroundLabel.setLayout(new FlowLayout());
    
        // Crea un pannello per la scritta con uno sfondo grigio chiaro
        JPanel textPanel = new JPanel();
        textPanel.setBackground(new Color(192, 192, 192));
    
        // Crea una JLabel con la scritta "HAI VINTO!!!" in giallo
        JLabel winnerLabel = new JLabel("HAI VINTO!!!");
        winnerLabel.setForeground(Color.YELLOW);
        winnerLabel.setFont(new Font(winnerLabel.getFont().getName(), Font.PLAIN, 24));
    
        // Aggiungi la JLabel al pannello
        textPanel.add(winnerLabel);
    
        // Crea un'icona con un'immagine di una coppa a tua scelta
        ImageIcon trophyIcon = new ImageIcon("path/del/tuo/file/immagine_coppa.jpg");
        JLabel trophyLabel = new JLabel(trophyIcon);
    
        // Aggiungi il pannello con la scritta e l'icona della coppa allo sfondo
        backgroundLabel.add(textPanel);
        backgroundLabel.add(trophyLabel);
    }
    //messaggio errore
    public void messagError()
    {
        JOptionPane.showMessageDialog(null, "Comando errato!");
    }
}
