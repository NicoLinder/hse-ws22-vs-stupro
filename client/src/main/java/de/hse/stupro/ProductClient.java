package de.hse.stupro;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * The REST Client interface.
 * Notice the `baseUri`. It uses `stork://` as URL scheme indicating that the called service uses Stork to locate and
 * select the service instance. The `OrderService` part is the service name. This is used to configure Stork discovery
 * and selection in the `application.properties` file.
 */
@RegisterRestClient(baseUri = "stork://order-service/products")
public interface ProductClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    String get();
}
