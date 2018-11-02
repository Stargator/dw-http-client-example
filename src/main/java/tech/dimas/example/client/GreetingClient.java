package tech.dimas.example.client;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.function.Function;

public class GreetingClient {

    private static final Logger LOG = LoggerFactory.getLogger(GreetingClient.class);
    private final HttpClient client;
    private final URI baseUri;

    public GreetingClient(HttpClient client, URI baseUri) {
        this.client = client;
        this.baseUri = baseUri;
    }

    public String greet() {
        return execute(HttpGet::new, "/greeting");
    }

    private String execute(Function<URI, HttpRequestBase> createRequest, String path) {
        HttpRequestBase request = createRequest.apply(UriBuilder.fromUri(baseUri).path(path).build());

        LOG.debug("Request Line: {}", request.getRequestLine());
        LOG.debug("Config: {}", request.getConfig());
        LOG.debug("URI: {}", request.getURI());
        LOG.debug("Protocol Version: {}", request.getProtocolVersion());
        LOG.debug("Headers: {}", Arrays.toString(request.getAllHeaders()));

        try {
            return client.execute(request, new BasicResponseHandler());
        } catch (IOException e) {
            throw new RuntimeException("Error checking greeting", e);
        }
    }
}
