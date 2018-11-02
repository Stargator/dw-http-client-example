package tech.dimas.example.client;

import org.apache.http.HttpHeaders;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.dimas.example.ExampleConfiguration;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.function.Function;

public class NetworkClient {

    private static final Logger LOG = LoggerFactory.getLogger(NetworkClient.class);
    private final HttpClient client;
    private final URI baseUri;
    private ExampleConfiguration clientConfig;

    public NetworkClient(HttpClient client, URI baseUri, ExampleConfiguration clientConfig) {
        this.client = client;
        this.baseUri = baseUri;
        this.clientConfig = clientConfig;
    }

    public String getNetworks() {
        return execute(HttpGet::new, "/networks");
    }

    private String execute(Function<URI, HttpRequestBase> createRequest, String path) {
        HttpRequestBase request = createRequest.apply(UriBuilder.fromUri(baseUri).path(path).build());

        final byte[] credentialBytes = (clientConfig.getNetworkClient().getUsername() + " "
                + clientConfig.getNetworkClient().getPassword()).getBytes(StandardCharsets.UTF_8);

        final String authorization = Base64.getEncoder().encodeToString(credentialBytes);

        request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + authorization);
        request.setProtocolVersion(new ProtocolVersion("HTTP", 2, 0));

        LOG.debug("Request Line: {}", request.getRequestLine());
        LOG.debug("Config: {}", request.getConfig());
        LOG.debug("URI: {}", request.getURI());
        LOG.debug("Protocol Version: {}", request.getProtocolVersion());
        LOG.debug("Headers: {}", Arrays.toString(request.getAllHeaders()));

        try {
            String response =  client.execute(request, new BasicResponseHandler());
            LOG.debug("Response: {}", response);
            return response;

        } catch (IOException e) {
            throw new RuntimeException("Error checking retrieving networks", e);
        }
    }
}
