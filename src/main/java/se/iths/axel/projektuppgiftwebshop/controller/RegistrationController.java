package se.iths.axel.projektuppgiftwebshop.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String showRegisterForm(Model model) {
        model.addAttribute("appUser", new AppUser());
        return "register-form";
    }

    @PostMapping
    public String registerNewAppUser(@Valid @ModelAttribute AppUser appUser,
                                     BindingResult result) {

        if (result.hasErrors()) {
            return "register-form";
        }

        service.registerNewAppUser(appUser);
        return "redirect:/";
    }
}
