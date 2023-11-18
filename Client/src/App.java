import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class App {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        Graphic graphic = new Graphic();
        while (!graphic.isButtonConnectPressed()) {} // Metre il bottone per connettersi non viene premuto

        // Dopo aver premuto il bottone per connettersi richiede al Server di connettersi
        String[] serverData = readServerDataFromXML("ServerData.xml"); // Legge l'IP e la Porta dal file XML
        ClientTCP clientTCP = new ClientTCP(serverData[0], Integer.parseInt(serverData[1])); // serverData[0] -> IP, serverData[1] -> Porta
        clientTCP.start(); // Avvia il Thread per ascoltare il Server
        clientTCP.send("connect"); // Richiede la connessione al Server
        while (!clientTCP.serverResponse.equals("connection accepted")) {} // Mentre il Server non invia la conferma di connessione

        Game game = new Game(graphic, clientTCP);
        game.start(); // Avvia il Thread per gestire il Game
    }

    private static String[] readServerDataFromXML(String filePath) throws ParserConfigurationException, SAXException, IOException {
        String[] serverData = new String[2]; // Array per IP e porta

        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("server");
        Node node = nodeList.item(0);

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            serverData[0] = element.getElementsByTagName("ip").item(0).getTextContent();
            serverData[1] = element.getElementsByTagName("port").item(0).getTextContent();
        }

        return serverData;
    }
}
