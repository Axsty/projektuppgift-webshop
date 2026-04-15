package se.iths.axel.projektuppgiftwebshop.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.iths.axel.projektuppgiftwebshop.model.Cart;
import se.iths.axel.projektuppgiftwebshop.model.Product;
import se.iths.axel.projektuppgiftwebshop.repository.ProductRepository;
import se.iths.axel.projektuppgiftwebshop.service.OrderService;

@Controller
@SessionAttributes("cart")
public class CartController {
    private final ProductRepository productRepository;
    private final OrderService orderService;

    public CartController(ProductRepository productRepository, OrderService orderService) {
        this.productRepository = productRepository;
        this.orderService = orderService;
    }

    @ModelAttribute("cart")
    public Cart createCart() {
        return new Cart();
    }

    @GetMapping("/cart")
    public String showCart(@ModelAttribute("cart") Cart cart, Model model) {

        model.addAttribute("items", cart.getItems());
        model.addAttribute("total", cart.getTotalPrice());

        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@ModelAttribute("cart") Cart cart, @RequestParam Long productId, @RequestParam int quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("product not found"));
        cart.addItem(product, quantity);

        return "redirect:/products";
    }

    @PostMapping("/checkout")
    public String checkout(@ModelAttribute("cart") Cart cart, @AuthenticationPrincipal UserDetails userDetails) {

        orderService.sendOrderConfirmation(cart, userDetails);

        return "redirect:/orderconfirmation";
    }

}
