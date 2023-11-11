public class RichiestaDalClient {
    public String comando; // Azione richiesta
    public int posizioneX; // Colonna scelta dal giocatore

    public RichiestaDalClient(String request) {
        String[] parametriRichiesta = request.split(";");

        this.comando = parametriRichiesta[0]; // Prende il comando da eseguire

        // Nel caso il comando sia di posizionare la pedina inserisce la colonna
        if (parametriRichiesta.length < 2) 
            this.posizioneX = Integer.parseInt(parametriRichiesta[1]);
            
    }
}
