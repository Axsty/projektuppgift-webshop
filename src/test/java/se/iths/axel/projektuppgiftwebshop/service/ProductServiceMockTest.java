package se.iths.axel.projektuppgiftwebshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.iths.axel.projektuppgiftwebshop.model.Product;
import se.iths.axel.projektuppgiftwebshop.repository.ProductRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceMockTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    // returnerar produkter per kategori
    @Test
    void getProducts_shouldReturnProductsByCategory() {
        Product product = new Product();
        product.setName("Byxa");
        product.setCategory("kläder");
        product.setPrice(399);
        product.setImageUrl("img");

        when(productRepository.findByCategoryIgnoreCase("kläder"))
                .thenReturn(List.of(product));

        List<Product> result = productService.getProducts("kläder");

        assertEquals(1, result.size());
        assertEquals("Byxa", result.get(0).getName());
        verify(productRepository).findByCategoryIgnoreCase("kläder");
    }

    // Om category är null ska alla produkter hämtas
    @Test
    void getProducts_shouldReturnAllProductsSorted_whenCategoryIsNull() {
        Product product = new Product();
        product.setName("Iphone");
        product.setCategory("elektronik");
        product.setPrice(15000);
        product.setImageUrl("img");

        when(productRepository.findAllByOrderByCategoryAsc()).thenReturn(List.of(product));

        List<Product> result = productService.getProducts(null);

        assertEquals(1, result.size());
        verify(productRepository).findAllByOrderByCategoryAsc();
    }


    // sparar produkt
    @Test
    void save_shouldCallRepositoryAndSaveProduct() {
        Product product = new Product();
        product.setName("Flaska");
        product.setCategory("hem");
        product.setPrice(99);
        product.setImageUrl("img");

        productService.save(product);

        verify(productRepository).save(product);
    }


}
