package se.iths.axel.projektuppgiftwebshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.iths.axel.projektuppgiftwebshop.model.AppUser;
import se.iths.axel.projektuppgiftwebshop.service.AppUserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final AppUserService service;

    public RegistrationController(AppUserService service) {
        this.service = service;
    }

    @GetMapping
    public String showRegisterForm() {
        return "register-form";
    }

    @PostMapping
    public String registerNewAppUser(@ModelAttribute AppUser appUser) {
        service.registerNewAppUser(appUser);
        return "redirect:/";
    }
}
