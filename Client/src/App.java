import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class App {
    private static Game game;
    private static PlayGroundWindow playGroundPanel;
    private static JPanel lobbyWindow;
    private static JFrame frame; // Dichiarazione della finestra

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowLobby();
        });
    }

    private static void createAndShowLobby() {
        // Creazione della finestra principale per il gioco
        frame = new JFrame("Forza 4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Creazione di un pannello per la lobby
        lobbyWindow = new LobbyWindow();

        // Aggiunta del pannello della lobby alla finestra
        frame.add(lobbyWindow, BorderLayout.CENTER);

        // Impostazione delle dimensioni della finestra a
        frame.setSize(500, 500);

        frame.setLocationRelativeTo(null); // Posizionamento della finestra al centro dello schermo
        frame.setVisible(true); // Rendere la finestra visibile
    }

    private static void createAndShowGrid() {
        // Creazione della finestra principale per il gioco
        frame = new JFrame("Forza 4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Inizializzazione di un'istanza del gioco
        game = new Game();

        // Creazione del pannello del campo di gioco
        playGroundPanel = new PlayGroundWindow(game.playGround);

        // Aggiunta del pannello della lobby alla finestra
        frame.add(lobbyWindow, BorderLayout.CENTER);

        // Impostazione delle dimensioni della finestra a
        frame.setSize(600, 800);

        frame.setLocationRelativeTo(null); // Posizionamento della finestra al centro dello schermo
        frame.setVisible(true); // Rendere la finestra visibile
    }

    // Questo metodo verrà chiamato quando il server trova una partita
    public static void startGame() {
        // Nascondi il pannello della lobby
        lobbyWindow.setVisible(false);

        // Mostra il campo da gioco
        createAndShowGrid();

        // Aggiungi il pannello del campo di gioco alla finestra
        frame.add(playGroundPanel, BorderLayout.CENTER);
        frame.revalidate(); // Aggiornamento del layout della finestra
    }
}

class LobbyWindow extends JPanel {
    public LobbyWindow() {
        // Crea un pulsante "Cerca Partita" nella lobby
        JButton searchButton = new JButton("Cerca Partita");
        
        // Imposta l'aspetto personalizzato del pulsante
        searchButton.setFont(new Font("Arial", Font.BOLD, 20));
        searchButton.setBackground(new Color(0, 102, 204));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);

        // Crea un bordo arrotondato intorno al pulsante
        int borderThickness = 2; // Spessore del bordo
        int borderRadius = 15; // Raggio del bordo arrotondato
        Border border = new CompoundBorder(
            new LineBorder(new Color(0, 102, 204), borderThickness),
            new EmptyBorder(borderRadius, borderThickness, borderRadius, borderThickness)
        );
        searchButton.setBorder(border);
        
        // Imposta le dimensioni del pulsante
        searchButton.setPreferredSize(new Dimension(200, 100));
        
        // Imposta un layout per centrare il pulsante nella lobby
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Margine
        add(searchButton, gbc);
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.startGame();
            }
        });
    }
}

class PlayGroundWindow extends JPanel {
    private List<Pawn> pawns;

    public PlayGroundWindow(PlayGround playGround) {
        this.pawns = playGround.pawns;
        int columns = playGround.columns;
        int rows = playGround.rows;

        // Utilizza un layout GridLayout per organizzare i pulsanti pedina in una griglia
        setLayout(new GridLayout(rows, columns));

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                // Crea un pulsante pedina personalizzato con forma rotonda
                PawnButton button = new PawnButton(row, col);
                add(button);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Disegna le pedine qui in base allo stato del gioco
        // Utilizza la lista `pawns` per ottenere le informazioni sulle pedine
    }
}

class PawnButton extends JButton {
    private int row;
    private int col;

    public PawnButton(int row, int col) {
        this.row = row;
        this.col = col;

        // Imposta le dimensioni dei pulsanti pedina
        setPreferredSize(new Dimension(70, 70));

        // Crea un ascoltatore di eventi per gestire i clic sul pulsante
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gestisci il clic sul pulsante della pedina
                // Ad esempio, aggiungi una pedina al giocatore corrente, ecc.
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getBackground());

        // Disegna una forma ovale per rappresentare la pedina
        g.fillOval(10, 10, getWidth() - 20, getHeight() - 20);
    }
}
