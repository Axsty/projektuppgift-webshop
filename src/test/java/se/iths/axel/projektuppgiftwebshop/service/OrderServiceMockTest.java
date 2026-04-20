package se.iths.axel.projektuppgiftwebshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import se.iths.axel.projektuppgiftwebshop.model.Cart;
import se.iths.axel.projektuppgiftwebshop.model.Order;
import se.iths.axel.projektuppgiftwebshop.model.Product;
import se.iths.axel.projektuppgiftwebshop.repository.OrderRepository;
import se.iths.sofia.webshopmailservice.MailService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceMockTest {

    @Mock
    private MailService mailService;

    @Mock
    private OrderRepository repository;

    @InjectMocks
    private OrderService orderService;

    private Cart cart;
    private UserDetails userDetails;


    @BeforeEach
    void setUp() {
        cart = new Cart();

        Product product = new Product();
        product.setName("Macbook Pro");
        product.setPrice(20000);
        product.setCategory("elektronik");
        product.setImageUrl("img");

        cart.addItem(product, 2);

        userDetails = User.withUsername("test@test.se")
                .password("123")
                .roles("USER")
                .build();
    }

    @Test
    void placeOrder_shouldThrowException_whenCartIsEmpty() {
        Cart emptyCart = new Cart();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> orderService.placeOrder(emptyCart, userDetails)
        );
        assertEquals("Kundvagnen är tom", exception.getMessage());
    }

    // Testar hela flödet
    // - order sparas via repository - kundvagnen töms - mail skickas
    @Test
    void placeOrder_shouldSaveOrder_clearCart_andSendMail() {
        when(repository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order order = orderService.placeOrder(cart, userDetails);

        assertNotNull(order);
        assertEquals("test@test.se", order.getUsername());
        assertEquals(40000, order.getPrice());
        assertEquals(1, order.getOrderItems().size());
        assertTrue(cart.isEmpty());
        verify(repository).save(any(Order.class));
        verify(mailService).sendOrderConfirmationMail(
                eq("test@test.se"),
                eq("test@test.se"),
                anyString()
        );

    }


}
