package de.hse.stupro.resources;

import de.hse.stupro.dao.ProductDao;
import de.hse.stupro.model.Product;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/products")
public class ProductResource {

    @Inject
    ProductDao productDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAvailableProducts() {
        var allProducts = productDao.getAllProducts();
        //return allProducts.stream().filter(product -> product.getStock() > 0).collect(Collectors.toList());
        return allProducts;
    }
}
