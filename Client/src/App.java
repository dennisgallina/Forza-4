import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Forza 4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        Game game = new Game();

        PlayGroundPanel playGroundPanel = new PlayGroundPanel(game.playGround);
        frame.add(playGroundPanel, BorderLayout.CENTER);

        JButton playButton = new JButton("Play");
        playButton.addActionListener(e -> startGame(game, playGroundPanel));
        frame.add(playButton, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void startGame(Game game, PlayGroundPanel playGroundPanel) {
        // Inizia il gioco e gestisci la logica qui
        // Ad esempio, assegna i giocatori, imposta lo stato del gioco su "in corso", ecc.
    }
}

class PlayGroundPanel extends JPanel {
    private List<Pawn> pawns;

    public PlayGroundPanel(PlayGround playGround) {
        this.pawns = playGround.pawns;
        int columns = playGround.columns;
        int rows = playGround.rows;

        setLayout(new GridLayout(rows, columns));

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
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

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gestisci il clic sul pulsante della pedina
                // Ad esempio, aggiungi una pedina al giocatore corrente, ecc.
            }
        });
    }
}
