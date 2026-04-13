package se.iths.axel.projektuppgiftwebshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.iths.axel.projektuppgiftwebshop.model.Product;
import se.iths.axel.projektuppgiftwebshop.service.ProductService;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String showProducts(@RequestParam(required = false) String category, Model model) {

        List<Product> products = productService.getProducts(category);

        model.addAttribute("products", products);

        return "products";

    }

}
