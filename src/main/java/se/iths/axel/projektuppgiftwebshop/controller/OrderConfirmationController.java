package se.iths.axel.projektuppgiftwebshop.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import se.iths.axel.projektuppgiftwebshop.model.Order;

@Controller
@RequestMapping("/orderconfirmation")
public class OrderConfirmationController {

    @GetMapping
    public String orderConfirmation(@AuthenticationPrincipal UserDetails userDetails,
                                    @ModelAttribute Order order,
                                    Model model) {

        model.addAttribute("orderDate", order.getOrderDate());
        model.addAttribute("order", order);
        model.addAttribute("userDetails", userDetails);

        return "order-confirmation";
    }
}
