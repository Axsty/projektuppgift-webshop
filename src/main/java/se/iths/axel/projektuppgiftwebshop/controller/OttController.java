package se.iths.axel.projektuppgiftwebshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OttController {

    @GetMapping("/ott/sent")
    public String ottSent() {
        return "ott-sent";
    }
}