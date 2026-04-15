package se.iths.axel.projektuppgiftwebshop.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import se.iths.axel.projektuppgiftwebshop.model.Cart;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/orderconfirmation")
public class OrderConfirmationController {

    @GetMapping
    public String orderConfirmation(@AuthenticationPrincipal UserDetails userDetails, @SessionAttribute("cart") Cart cart, Model model) {
        LocalDateTime orderPlaced = LocalDateTime.now();

        model.addAttribute("orderPlaced", orderPlaced);
        model.addAttribute("cart", cart);
        model.addAttribute("userDetails", userDetails);

        return "order-confirmation";
    }
}
