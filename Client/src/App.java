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
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, InterruptedException {
        Graphic graphic = new Graphic(); // Crea la Graphic
        graphic.showLobby(); // Visualizza la Lobby
        while (!graphic.isButtonConnectPressed()) {Thread.sleep(100);} // Attende che il bottone per connettersi venga premuto
       
        String[] serverData = readServerDataFromXML("src/ServerData.xml"); // Legge l'IP e la Porta del Server dal file XML
        ClientTCP clientTCP = new ClientTCP(serverData[0], Integer.parseInt(serverData[1])); // serverData[0] -> IP, serverData[1] -> Porta

        clientTCP.start(); // Avvia il Thread per ascoltare il Server
        while (!clientTCP.isConnected()) {} // Attende la connessione col Server

        Game game = new Game(graphic, clientTCP); // Crea il Game
        game.start(); // Avvia il Game
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
