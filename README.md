# Example

How to start the Example application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/app.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

You can check the client against the example basic auth server on fwd.app.

To talk to the example server, you can set the username and password in config.yml:

```yaml
networkClient:
  baseUri: https://fwd.app/api/
  username: Anyone     # Use proper login credentials
  password: P@ssw0rd!  # Use proper login credentials
```
