public class ServerResponse {
    public String command; 
    public String description;

    public ServerResponse(String[] splittedDatFromServer) {
        this.command = splittedDatFromServer[0];
        this.description = splittedDatFromServer[1];
    }
}
