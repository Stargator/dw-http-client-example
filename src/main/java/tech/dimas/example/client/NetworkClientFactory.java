package tech.dimas.example.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.client.HttpClientConfiguration;
import io.dropwizard.setup.Environment;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

public class NetworkClientFactory {

    @Valid
    @NotNull
    private URI baseUri = URI.create("http://localhost:8080/");

    @NotEmpty
    private String username = "Alfonso";

    @NotEmpty
    private String password = "password";

    @Valid
    @NotNull
    private HttpClientConfiguration httpClient = new HttpClientConfiguration();

    @JsonProperty("baseUri")
    public URI getBaseUri() {
        return baseUri;
    }

    @JsonProperty("baseUri")
    public void setBaseUri(URI baseUri) {
        this.baseUri = baseUri;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("httpClient")
    public HttpClientConfiguration getHttpClient() {
        return httpClient;
    }

    @JsonProperty("httpClient")
    public void setHttpClient(HttpClientConfiguration httpClient) {
        this.httpClient = httpClient;
    }

    public NetworkClient build(Environment environment) {

        CredentialsProvider credsProvider = new BasicCredentialsProvider();

        credsProvider.setCredentials(
                new AuthScope(baseUri.getHost(), baseUri.getPort()),
                new UsernamePasswordCredentials(username, password)
        );


        final HttpClient httpClient =
                new HttpClientBuilder(environment)
                        .using(this.httpClient)
                        .using(credsProvider)
                        .build("network-client");

        return new NetworkClient(httpClient, baseUri);
    }
}
