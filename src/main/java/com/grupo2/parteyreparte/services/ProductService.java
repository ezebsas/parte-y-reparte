package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.models.User;
import com.grupo2.parteyreparte.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
        private static final String PRODUCT_WITH_ID = "Product with id = ";
        private static final String ALREADY_EXISTS = " already exists";
        private static final String DOES_NOT_EXIST = " does not exist";
        private static final String PRODUCT_IS_FULL = "Product is full";

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {

        return this.productRepository.getAll();
    }

    public Product createProduct(Product product) {
        boolean exists = product.getId() != null && productRepository.existsById(product.getId());

        if (exists) {
            throw new IllegalArgumentException(PRODUCT_WITH_ID + product.getId() + ALREADY_EXISTS);
        }

        this.productRepository.createProduct(product);

        return product;
    }

    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(PRODUCT_WITH_ID + id + DOES_NOT_EXIST));
    }

    public Product updateProduct(String id, Product product) {
        boolean exists = productRepository.existsById(id);

        if (!exists) {
            throw new NoSuchElementException(PRODUCT_WITH_ID + id + DOES_NOT_EXIST);
        }

        return this.productRepository.update(id, product);
    }

    public Product patchProduct(String id, Product product) {
        boolean exists = productRepository.existsById(id);

        if (!exists) {
            throw new NoSuchElementException(PRODUCT_WITH_ID + id + DOES_NOT_EXIST);
        }

        Product existingProduct = this.productRepository.findById(id).get();

        if (product.getName() != null) {
            existingProduct.setName(product.getName());
        }

        if (product.getImage() != null) {
            existingProduct.setImage(product.getImage());
        }

        if (product.getLink() != null) {
            existingProduct.setLink(product.getLink());
        }

        if (product.getDeadline() != null) {
            existingProduct.setDeadline(product.getDeadline());
        }

        if (product.getMaxPeople() != 0) {
            existingProduct.setMaxPeople(product.getMaxPeople());
        }

        if (product.getMinPeople() != 0) {
            existingProduct.setMinPeople(product.getMinPeople());
        }

        Double totalCost = product.getTotalCost();
        if (totalCost != null) {
            existingProduct.setTotalCost(totalCost);
        }


        if (product.getSuscribers() != null) {
            existingProduct.setSuscribers(product.getSuscribers());
        }

        if (product.getState() != null) {
            existingProduct.setState(product.getState());
        }

        return this.productRepository.update(id, existingProduct);
    }

    public Product subscribeUser(String productId, String userId) {
        Product product = getProductById(productId);
        /*
        User user = userService.getUserById(userId);
        */

        User user = new User(userId, "name", 21, "email", null);

        if (product.isFull()) {
            throw new IllegalStateException(PRODUCT_IS_FULL);
        }

        product.suscribeUser(user);

        this.productRepository.update(productId, product);
        return product;
    }

    public List<User> getParticipants(String id) {
        Product product = getProductById(id);
        return product.getSuscribers();
    }

    public Product closeProduct(String id) {
        Product product = getProductById(id);
        product.close();
        this.productRepository.update(id, product);
        return product;
    }
}
