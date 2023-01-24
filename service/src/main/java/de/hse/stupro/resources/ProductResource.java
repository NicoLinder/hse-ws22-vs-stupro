package de.hse.stupro.resources;

import de.hse.stupro.dao.ProductDao;
import de.hse.stupro.model.Product;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/products")
public class ProductResource {

    @Inject
    ProductDao productDao;

    @ConfigProperty(name = "discount.percent", defaultValue = "0")
    String discountPercent;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAvailableProducts() {
        var allProducts = productDao.getAllProducts();

        // Sort out products that are currently not in Stock
        List<Product> productsInStock = allProducts.stream().filter(product -> product.getStock() > 0).toList();

        // Apply the configured discount
        int modifier = (100 - Integer.parseInt(discountPercent)) / 100;
        productsInStock.forEach(product -> product.setPrice(product.getPrice() * modifier));
        return productsInStock;
    }
}
