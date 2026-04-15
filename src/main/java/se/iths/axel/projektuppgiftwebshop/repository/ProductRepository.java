package se.iths.axel.projektuppgiftwebshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.axel.projektuppgiftwebshop.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(String category);


    List<Product> findAllByOrderByCategoryAsc();

    List<Product> findByCategoryIgnoreCase(String category);
}
