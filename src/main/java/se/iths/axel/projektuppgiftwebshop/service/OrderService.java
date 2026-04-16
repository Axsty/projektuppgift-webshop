package se.iths.axel.projektuppgiftwebshop.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import se.iths.axel.projektuppgiftwebshop.model.Cart;
import se.iths.axel.projektuppgiftwebshop.model.Order;
import se.iths.axel.projektuppgiftwebshop.model.OrderItem;
import se.iths.axel.projektuppgiftwebshop.repository.OrderRepository;
import se.iths.sofia.webshopmailservice.MailService;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class OrderService {

    private final MailService service;
    private final OrderRepository repository;

    public OrderService(MailService service, OrderRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    public Order placeOrder(Cart cart, UserDetails userDetails) {

        if (cart == null || cart.isEmpty()) {
            throw new IllegalArgumentException("Kundvagnen är tom");
        }

        String username = userDetails.getUsername();

        String orderDetails = "Datum: " + LocalDateTime.now() + "\n" + "Produkter: ";

        for (OrderItem item : cart.getItems()) {
            orderDetails += item.getProductName() + " x" + item.getQuantity() + " - " + item.getPrice() + "kr" + "\n";
        }
        orderDetails += "\n" + "Total belopp: " + cart.getTotalPrice() + "kr";

        Order order = repository.save(cartToOrder(cart, userDetails));
        cart.clearCart();
        service.sendOrderConfirmationMail(username, username, orderDetails);

        return order;
    }

    private Order cartToOrder(Cart cart, UserDetails userDetails) {
        Order order = new Order();

        order.setOrderItems(new ArrayList<>(cart.getItems()));
        order.setPrice(cart.getTotalPrice());
        order.setUsername(userDetails.getUsername());
        order.setOrderDate(LocalDateTime.now());

        return order;
    }
}
