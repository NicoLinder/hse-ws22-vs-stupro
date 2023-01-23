package de.hse.stupro;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/products")
public class ProductFrontendApi {

    @RestClient ProductClient productClient;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String invoke(){
        return productClient.get();
    }
}
