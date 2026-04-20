package se.iths.axel.projektuppgiftwebshop.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.iths.axel.projektuppgiftwebshop.model.AppUser;
import se.iths.axel.projektuppgiftwebshop.service.AppUserService;

@Controller
@RequestMapping("/")
public class HomeController {
    
    private final AppUserService appUserService;

    public HomeController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/privacy")
    public String privacy() {
        return "privacy";
    }

    @GetMapping("/cookies")
    public String cookies() {
        return "cookies";
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {
        String username = authentication.getName();

        AppUser user = appUserService.findByUsername(username);
        model.addAttribute("user", user);

        return "profile";
    }

    @PostMapping("/profile/send-info")
    public String sendUserInfo(Authentication authentication, Model model) {
        String username = authentication.getName();

        appUserService.sendUserInfoByEmail(username);

        AppUser user = appUserService.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("message", "Dina uppgifter har skickats via mail.");

        return "profile";
    }

    @PostMapping("/profile/delete")
    public String deleteAccount(Authentication authentication) {
        String username = authentication.getName();

        appUserService.deleteUserByUsername(username);

        return "redirect:/logout";
    }
}
