package se.iths.axel.projektuppgiftwebshop.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.iths.axel.projektuppgiftwebshop.model.Product;
import se.iths.axel.projektuppgiftwebshop.repository.ProductRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        productRepository.deleteAll();
    }

    // produkter filtreras korrekt baserat på kategori
    @Test
    void getProducts_shouldReturnProductsByCategory() {
        Product p1 = new Product();
        p1.setName("Vas");
        p1.setCategory("hem");
        p1.setPrice(399);
        p1.setImageUrl("img1");

        Product p2 = new Product();
        p2.setName("Iphone 17 Pro");
        p2.setCategory("elektronik");
        p2.setPrice(14999);
        p2.setImageUrl("img2");

        productRepository.saveAll(List.of(p1, p2));

        List<Product> result = productService.getProducts("hem");

        assertEquals(1, result.size());
        assertEquals("Vas", result.get(0).getName());
    }

    // testar att en produkt sparas korrekt i db
    @Test
    void save_shouldSaveProductInDatabase() {
        Product product = new Product();
        product.setName("Vattenflaska");
        product.setCategory("hem");
        product.setPrice(99);
        product.setImageUrl("img3");

        productService.save(product);

        List<Product> allProducts = productRepository.findAll();

        assertEquals(1, allProducts.size());
        assertEquals("Vattenflaska", allProducts.get(0).getName());
    }


}
