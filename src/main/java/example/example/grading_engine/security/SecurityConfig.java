package example.example.grading_engine.security;

import example.example.grading_engine.model.entity.User;
import example.example.grading_engine.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.Duration;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    AccessDeniedHandler accessDeniedHandler() {
        AccessDeniedHandlerImpl handler = new AccessDeniedHandlerImpl();
        handler.setErrorPage("/accessDenied"); // 🔥 redirect target
        return handler;
    }

    private final JwtAuthenticationFilter jwtFilter;
    private final CustomOAuth2UserService oauthUserService;
    private final JwtUtil jwtUtil;

    public SecurityConfig(
            JwtAuthenticationFilter jwtFilter,
            CustomOAuth2UserService oauthUserService,
            JwtUtil jwtUtil
    ) {
        this.jwtFilter = jwtFilter;
        this.oauthUserService = oauthUserService;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    SecurityFilterChain filterChain(
            HttpSecurity http,
            UserRepository userRepository
    ) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/accessDenied").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/faculty/**").hasRole("FACULTY")
                        .requestMatchers("/hod/**").hasRole("HOD")
                        .requestMatchers("/student/**").hasRole("STUDENT")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(accessDeniedHandler())
                )
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(u -> u.userService(oauthUserService))
                        .successHandler((req, res, authResult) -> {

                            OAuth2User oauthUser =
                                    (OAuth2User) authResult.getPrincipal();

                            String email = oauthUser.getAttribute("email");

                            User user = userRepository
                                    .findByEmail(email)
                                    .orElseThrow();

                            String token = jwtUtil.generateToken(user);

                            ResponseCookie cookie =
                                    ResponseCookie.from("ACCESS_TOKEN", token)
                                            .httpOnly(true)
                                            .secure(false)
                                            .path("/")
                                            .maxAge(Duration.ofDays(1))
                                            .sameSite("Lax")
                                            .build();

                            res.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

                            // ✅ After login, redirect back to a safe endpoint
                            res.sendRedirect("/auth/me");
                        })
                );

        http.addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}