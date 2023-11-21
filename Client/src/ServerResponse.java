public class ServerResponse {
    public String command; 
    public String description;

    public ServerResponse(String[] splittedDatFromServer) {
        this.command = splittedDatFromServer[0];
        
        if (splittedDatFromServer.length > 1)
            this.description = splittedDatFromServer[1];
    }
}
