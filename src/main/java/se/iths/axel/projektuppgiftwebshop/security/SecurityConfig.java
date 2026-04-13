package se.iths.axel.projektuppgiftwebshop.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authorization.EnableMultiFactorAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.FactorGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.ott.RedirectOneTimeTokenGenerationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMultiFactorAuthentication(
        authorities = {
                FactorGrantedAuthority.PASSWORD_AUTHORITY,
                FactorGrantedAuthority.OTT_AUTHORITY
        }
)
public class SecurityConfig {

    private final MyUserDetailsService userDetailsService;

    public SecurityConfig(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   OttSuccessHandler ottSuccessHandler) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/register",
                                "/login",
                                "/login/ott",
                                "/ott/sent",
                                "/cookie-policy",
                                "/privacy-policy",
                                "/css/**",
                                "/js/**"
                        ).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .oneTimeTokenLogin(ott -> ott
                        .tokenGenerationSuccessHandler(ottSuccessHandler)
                        .defaultSuccessUrl("/products", true)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                );
        return http.build();
    }


    @Bean
    public RedirectOneTimeTokenGenerationSuccessHandler redirectOneTimeTokenGenerationSuccessHandler() {
        return new RedirectOneTimeTokenGenerationSuccessHandler("/ott/sent");
    }
}