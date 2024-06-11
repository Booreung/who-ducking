package hello.config;

import hello.security.handler.CustomAuthenticationFailureHandler;
import hello.security.CustomClientRegistration;
import hello.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomClientRegistration clientRegistration;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/oauth2/**", "/login", "/register/**").permitAll()
                .requestMatchers("/css/**", "/jpg/**", "/png/**", "/js/**").permitAll()
                .requestMatchers("/board").hasRole("ROLE_USER")
                .anyRequest().authenticated()
        );

        http.csrf(csrf -> csrf.disable());
        http.formLogin(login -> login.disable());
        http.httpBasic(basic -> basic.disable());

        http.oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .clientRegistrationRepository(clientRegistration.clientRegistrationRepository())
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                        .failureHandler(customAuthenticationFailureHandler))
                .logout(logout -> logout.logoutSuccessUrl("/"));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}