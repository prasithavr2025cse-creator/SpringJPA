package com.uam.hub.service;

import com.uam.hub.entity.Product;
import com.uam.hub.exception.ResourceNotFoundException;
import com.uam.hub.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    public Product createProduct(Product product) {
        if (product.getActive() == null) product.setActive(true);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product details) {
        Product product = getProductById(id);
        if (details.getCode() != null) product.setCode(details.getCode());
        if (details.getName() != null) product.setName(details.getName());
        if (details.getDescription() != null) product.setDescription(details.getDescription());
        if (details.getCategory() != null) product.setCategory(details.getCategory());
        if (details.getUnitPrice() != null) product.setUnitPrice(details.getUnitPrice());
        if (details.getActive() != null) product.setActive(details.getActive());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}
