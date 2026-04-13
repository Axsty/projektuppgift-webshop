package se.iths.axel.projektuppgiftwebshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import se.iths.sofia.webshopmailservice.MailService;

@SpringBootTest
class ProjektuppgiftWebshopApplicationTests {

    @MockitoBean
    private MailService mailService;

    @Test
    void contextLoads() {
    }

}
