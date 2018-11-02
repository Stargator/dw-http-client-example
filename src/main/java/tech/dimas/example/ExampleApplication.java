package tech.dimas.example;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import tech.dimas.example.client.NetworkClient;
import tech.dimas.example.resources.NetworkResource;

public class ExampleApplication extends Application<ExampleConfiguration> {

    public static void main(final String[] args) throws Exception {
        new ExampleApplication().run(args);
    }

    @Override
    public String getName() {
        return "Example";
    }

    @Override
    public void initialize(final Bootstrap<ExampleConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );
    }

    @Override
    public void run(final ExampleConfiguration configuration,
                    final Environment environment) {

        NetworkClient client = configuration.getNetworkClient().build(environment, configuration);

        environment.jersey().register(new NetworkResource(client));
    }

}
