public class RichiestaDalClient {
    public String command; // Azione richiesta
    public int positionX; // Colonna scelta dal giocatore

    public RichiestaDalClient(String request) {
        String[] parametriRichiesta = request.split(";");

        this.command = parametriRichiesta[0]; // Prende il comando da eseguire

        // Nel caso il comando sia di posizionare la pedina inserisce la colonna
        if (parametriRichiesta.length >= 2) 
            this.positionX = Integer.parseInt(parametriRichiesta[1]);
    }
}
