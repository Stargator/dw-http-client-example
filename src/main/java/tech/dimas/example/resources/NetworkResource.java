package tech.dimas.example.resources;

import tech.dimas.example.client.NetworkClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/check-greeting")
public class NetworkResource {

    private final NetworkClient client;

    public NetworkResource(NetworkClient client) {
        this.client = client;
    }

    @GET
    public String checkGreeting() {
        String result = client.getNetworks();
        return String.format("Greeting was \"%s\"", result);
    }
}
