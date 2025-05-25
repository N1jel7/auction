package by.n1jel.auction.config;


import by.n1jel.auction.filter.JwtFilter;
import by.n1jel.auction.handler.CustomAccessDeniedHandler;
import by.n1jel.auction.handler.CustomLogoutHandler;
import by.n1jel.auction.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtFilter jwtFilter;
    private final UserServiceImpl userService;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomLogoutHandler customLogoutHandler;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/v1/lots/login/**", "/api/v1/lots/register/**", "/api/v1/lots/refresh_token/**")
                            .permitAll();
                    auth.anyRequest().authenticated();
                })
                .userDetailsService(userService)
                .exceptionHandling(e -> {
                    e.accessDeniedHandler(customAccessDeniedHandler);
                    e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(log -> {
                    log.logoutUrl("/logout");
                    log.addLogoutHandler(customLogoutHandler);
                    log.logoutSuccessHandler((request, response, authentication) ->
                            SecurityContextHolder.clearContext());
                });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}