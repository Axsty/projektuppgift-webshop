package se.iths.axel.projektuppgiftwebshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {
        "se.iths.axel.projektuppgiftwebshop",
        "se.iths.sofia.webshopmailservice"
})
public class ProjektuppgiftWebshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjektuppgiftWebshopApplication.class, args);
    }


}
