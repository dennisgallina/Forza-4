import java.net.DatagramPacket;
import java.net.InetAddress;

public class RichiestaDelClient {
    public InetAddress ipClient; // Indirizzo IP
    public Integer portaClient; // Porta di comunicazione
    public String comando; // Azione richiesta

    public RichiestaDelClient(DatagramPacket packetDalClient) {
        // Converte in String la richiesta e la divide per i suoi parametri
        String richiestaRicevuta = new String(packetDalClient.getData(), 0, packetDalClient.getLength());
        String[] parametriRichiesta = richiestaRicevuta.split(";");

        // Memorizza le informazioni del Client
        this.ipClient = packetDalClient.getAddress();
        this.portaClient = packetDalClient.getPort();

        this.comando = parametriRichiesta[0];
    }

    public String ToShowConsole() {
        return "Richiesta ricevuta dal Client " + ipClient + " " + portaClient + ": " + 
        comando + ";" + idCassiere + ";" + idProdotto + ";" + quantitàProdotto;
    }
}
