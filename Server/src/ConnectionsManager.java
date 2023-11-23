import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConnectionsManager {
    public List<Socket> connections;

    public ConnectionsManager() {
        connections = new ArrayList<>();
    }

    public boolean add(Socket socket) {
        if (socket == null)
            return false;
            
        connections.add(socket);
        return true;
    }

    public void closeAll() throws IOException {
        for (Socket connection : connections) 
            connection.close();
    }
}
