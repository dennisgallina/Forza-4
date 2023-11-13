public class Player {
    public String name; // Nome del giocatore
    public String color; // Colore delle pedine
    public boolean isLogged; // Stato della connessione: true -> connesso; false -> non connesso

    public Player() {
        this.name = null;
        this.color = null;
        this.isLogged = false;
    }

    public Player(String name, String color) {
        this.name = name;
        this.color = color;
        this.isLogged = true;
    }
}