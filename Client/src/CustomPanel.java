import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CustomPanel extends JPanel {
    private ImageIcon backgroundIcon;
    private final List<Coriandoli> coriandoliList = new ArrayList<>();

    public CustomPanel() {
        this.backgroundIcon = null;
    }

    public void setBI(ImageIcon backgroundImage) {
        this.backgroundIcon = backgroundImage;
    }

    public void generateCoriandoli() {
        int coriandoloX = (int) (Math.random() * getWidth());
        int coriandoloSpeed = (int) (Math.random() * 5) + 1;
        Color coriandoloColor = getRandomColor();

        Coriandoli newCoriandolo = new Coriandoli(coriandoloX, 0, coriandoloSpeed, coriandoloColor);
        coriandoliList.add(newCoriandolo);
    }

    public void updateCoriandoli() {
        List<Coriandoli> toRemove = new ArrayList<>();
        for (Coriandoli coriandolo : coriandoliList) {
            coriandolo.update();
            if (coriandolo.getY() > getHeight()) {
                toRemove.add(coriandolo);
            }
        }
        coriandoliList.removeAll(toRemove);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
        for (Coriandoli coriandolo : coriandoliList) {
            coriandolo.draw(graphics);
        }
    }

    private Color getRandomColor() {
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        return new Color(r, g, b);
    }
}
