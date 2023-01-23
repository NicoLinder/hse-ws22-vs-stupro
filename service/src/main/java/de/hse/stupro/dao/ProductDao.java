package de.hse.stupro.dao;

import de.hse.stupro.model.Product;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ProductDao {

    @Inject
    EntityManager entityManager;

    public Product getProductByItemNumber(long itemNumber) {
        return entityManager.find(Product.class, itemNumber);
    }

    public List<Product> getAllProducts() {
        Query query = entityManager.createQuery("SELECT p FROM Product p");
        return query.getResultList();
    }

    @Transactional
    public Product save(Product product) {
        if(entityManager.find(Product.class, product.getItemNumber()) != null) {
            product = entityManager.merge(product);
        } else {
            entityManager.persist(product);
        }
        return product;
    }

    @Transactional
    public void delete(long itemNumber) {
        Product product = entityManager.find(Product.class, itemNumber);
        if(product != null)
            entityManager.remove(product);
    }
}
