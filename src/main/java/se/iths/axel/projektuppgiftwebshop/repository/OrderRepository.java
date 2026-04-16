package se.iths.axel.projektuppgiftwebshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.axel.projektuppgiftwebshop.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
