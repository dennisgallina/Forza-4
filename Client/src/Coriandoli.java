import java.awt.*;

public class Coriandoli {
    private int x;
    private int y;
    private int speed;
    private Color color;

    public Coriandoli(int x, int y, int speed, Color color) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.color = color;
    }

    public int getY() {
        return y;
    }

    public void update() {
        y += speed;
    }

    public void draw(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRect(x, y, 5, 5);
    }
}