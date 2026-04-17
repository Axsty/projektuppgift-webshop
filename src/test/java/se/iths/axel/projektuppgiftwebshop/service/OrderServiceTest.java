package se.iths.axel.projektuppgiftwebshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import se.iths.axel.projektuppgiftwebshop.TestConfig;
import se.iths.axel.projektuppgiftwebshop.model.Cart;
import se.iths.axel.projektuppgiftwebshop.model.Order;
import se.iths.axel.projektuppgiftwebshop.model.Product;
import se.iths.axel.projektuppgiftwebshop.repository.OrderRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestConfig.class)
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
    }

    // testar att en order sparas i db vid checkout
    @Test
    void placeOrder_shouldSaveOrderInDatabase() {
        Cart cart = new Cart();

        Product product = new Product();
        product.setName("T-shirt");
        product.setPrice(200);
        product.setCategory("kläder");
        product.setImageUrl("img");

        cart.addItem(product, 2);

        UserDetails userDetails = User.withUsername("test@test.se")
                .password("123")
                .roles("USER")
                .build();

        Order order = orderService.placeOrder(cart, userDetails);

        List<Order> orders = orderRepository.findAll();

        assertEquals(1, orders.size());
        assertEquals("test@test.se", orders.get(0).getUsername());
        assertEquals(400, orders.get(0).getPrice());
        assertNotNull(order.getOrderDate());
        assertTrue(cart.isEmpty());

    }

    // testar att ett fel kastas om kundvagnen är tom
    @Test
    void placeOrder_shouldThrowException_whenCartIsEmpty() {
        Cart emptyCart = new Cart();

        UserDetails userDetails = User.withUsername("test@test.se")
                .password("123")
                .roles("USER")
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderService.placeOrder(emptyCart, userDetails));

        assertEquals("Kundvagnen är tom", exception.getMessage());
        assertEquals(0, orderRepository.findAll().size());

    }


}