import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class App {
    static ServerSocket socket; // Socket del Server
    static RichiestaDelClient richiestaDelClient; // Gestione della Richiesta del Client in corso

    public static void main(String[] args) throws Exception {
        socket = new ServerSocket(666);

        // In attesa di richieste
        while (true) {
            if (riceviDalClient()) // Riceve il pacchetto dal Client e lo inserisce in un oggetto RichiestaClient
                gestioneRichiesta(); // Gestisce la richiesta ricevuta dal Client e si occupa di inviare al Client le risposte
        }
    }

    // Riceve un pacchetto dal Client e lo inserisce in un oggetto RichiestaClient
    private static boolean riceviDalClient() throws IOException, ParserConfigurationException, SAXException, TransformerException {
        byte[] buff = new byte[1500];
        DatagramPacket packetDalClient = new DatagramPacket(buff, buff.length);
        socket.accept(packetDalClient); // Ricezione di un pacchetto UDP

        richiestaDelClient = new RichiestaDelClient(packetDalClient);

        return true;
    }

    // Gestisce la richiesta ricevuta dal Client e si occupa di inviare al Client le risposte
    private static void gestioneRichiesta() throws IOException, ParserConfigurationException, SAXException, TransformerException {
        System.out.println(richiestaDelClient.ToShowConsole()); // Mostra in console la richiesta ricevuta dal client
    } 

    // Invia al Client un pacchetto
    public static void inviaAlClient(byte[] datiBytes) throws IOException {
        DatagramPacket rispostaAlClient = new DatagramPacket(datiBytes, datiBytes.length);
        rispostaAlClient.setAddress(richiestaDelClient.ipClient);
        rispostaAlClient.setPort(richiestaDelClient.portaClient);

        socket.send(rispostaAlClient); // Invia un pacchetto UDP
    }
}