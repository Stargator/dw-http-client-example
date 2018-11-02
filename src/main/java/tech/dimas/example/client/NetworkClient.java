package tech.dimas.example.client;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicResponseHandler;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.function.Function;

public class NetworkClient {

    private final HttpClient client;
    private final URI baseUri;

    public NetworkClient(HttpClient client, URI baseUri) {
        this.client = client;
        this.baseUri = baseUri;
    }

    public String getNetworks() {
        return execute(HttpGet::new, "/networks");
    }

    private String execute(Function<URI, HttpRequestBase> createRequest, String path) {
        HttpRequestBase request = createRequest.apply(UriBuilder.fromUri(baseUri).path(path).build());

        try {
            return client.execute(request, new BasicResponseHandler());
        } catch (IOException e) {
            throw new RuntimeException("Error checking retrieving networks", e);
        }
    }
}
