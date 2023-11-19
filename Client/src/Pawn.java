public class Pawn {
    public String color; // Colore della pedina: rossa; gialla
    public int positionX; // Posizione orizzontale nella griglia
    public int positionY; // Posizione verticale nella griglia

    public Pawn() {
        this.color = null;
        this.positionX = 0;
        this.positionY = 0;
    }

    public Pawn(String color, int positionX, int positionY) {
        this.color = color;
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
