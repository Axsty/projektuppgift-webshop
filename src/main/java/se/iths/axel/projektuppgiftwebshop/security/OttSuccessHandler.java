package se.iths.axel.projektuppgiftwebshop.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.authentication.ott.RedirectOneTimeTokenGenerationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import se.iths.sofia.webshopmailservice.MailService;

import java.io.IOException;

@Component
public class OttSuccessHandler implements OneTimeTokenGenerationSuccessHandler {

    private final MailService mailService;
    private final RedirectOneTimeTokenGenerationSuccessHandler redirectOneTimeTokenGenerationSuccessHandler;

    public OttSuccessHandler(MailService mailService, RedirectOneTimeTokenGenerationSuccessHandler redirectOneTimeTokenGenerationSuccessHandler) {
        this.mailService = mailService;
        this.redirectOneTimeTokenGenerationSuccessHandler = redirectOneTimeTokenGenerationSuccessHandler;
    }


    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       OneTimeToken oneTimeToken) throws IOException, ServletException {

        String link = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/login/ott")
                .queryParam("token", oneTimeToken.getTokenValue())
                .toUriString();

        String email = oneTimeToken.getUsername();

        mailService.sendOttMail(email, link);

        redirectOneTimeTokenGenerationSuccessHandler.handle(request, response, oneTimeToken);

    }
}
