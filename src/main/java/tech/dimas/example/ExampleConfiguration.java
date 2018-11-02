package tech.dimas.example;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import tech.dimas.example.client.NetworkClientFactory;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class ExampleConfiguration extends Configuration {
    @Valid
    @NotNull
    private NetworkClientFactory networkClient = new NetworkClientFactory();

    @JsonProperty("networkClient")
    public NetworkClientFactory getNetworkClient() {
        return networkClient;
    }

    @JsonProperty("networkClient")
    public void setNetworkClient(NetworkClientFactory networkClient) {
        this.networkClient = networkClient;
    }
}
