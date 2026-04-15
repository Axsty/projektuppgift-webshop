package se.iths.axel.projektuppgiftwebshop.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import se.iths.axel.projektuppgiftwebshop.model.Cart;
import se.iths.axel.projektuppgiftwebshop.model.OrderItem;
import se.iths.sofia.webshopmailservice.MailService;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final MailService service;

    public OrderService(MailService service) {
        this.service = service;
    }

    public void sendOrderConfirmation(Cart cart, UserDetails userDetails) {
        String username = userDetails.getUsername();

        String orderDetails = "Datum: " + LocalDateTime.now() + "\n" + "Produkter: ";

        for (OrderItem item : cart.getItems()) {
            orderDetails += item.getProductName() + " x" + item.getQuantity() + " - " + item.getPrice() + " kr" + "\n";
        }

        orderDetails += "\n" + "Total belopp: " + cart.getTotalPrice() + " kr";

        service.sendOrderConfirmationMail(username, username, orderDetails);
    }
}
