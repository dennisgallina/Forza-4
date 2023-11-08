public class Player {
    public String name; // Nome del giocatore
    public boolean isLogged; // Stato della connessione: true -> connesso; false -> non connesso

    public Player() {
        this.name = null;
        this.isLogged = false;
    }

    public Player(String name) {
        this.name = name;
        this.isLogged = true;
    }
}