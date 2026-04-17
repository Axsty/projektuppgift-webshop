package se.iths.axel.projektuppgiftwebshop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import se.iths.sofia.webshopmailservice.MailService;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {

    @Bean
    @Primary
    public MailService mailService() {
        return mock(MailService.class);
    }

}
