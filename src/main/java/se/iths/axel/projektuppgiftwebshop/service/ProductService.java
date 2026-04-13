package se.iths.axel.projektuppgiftwebshop.service;

import org.springframework.stereotype.Service;
import se.iths.axel.projektuppgiftwebshop.model.Product;
import se.iths.axel.projektuppgiftwebshop.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(String category) {
        if (category != null && !category.isEmpty()) {
            return productRepository.findByCategory(category);
        }

        return productRepository.findAll();
    }
}
