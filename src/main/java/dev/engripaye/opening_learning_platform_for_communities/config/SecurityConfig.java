package dev.engripaye.opening_learning_platform_for_communities.config;

import dev.engripaye.opening_learning_platform_for_communities.model.User;
import dev.engripaye.opening_learning_platform_for_communities.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final UserService userService;


    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        OidcUserService delegate = new OidcUserService();

        OAuth2UserService<OidcUserRequest, OidcUser> customOidcUserService = (OidcUserRequest req) -> {
            OidcUser oidcUser = delegate.loadUser(req);
            var user = userService.upsertFromOidc(oidcUser);

            Set<SimpleGrantedAuthority> authorities = new HashSet<>();

            // keep oidc scope and authorities
            authorities.addAll(oidcUser.getAuthorities().stream().map(a -> new SimpleGrantedAuthority(a.getAuthority())).collect(Collectors.toSet()));

            // add app roles
            if(user.getRole() != null) {
                authorities.addAll(user.getRole().stream().map(r -> new SimpleGrantedAuthority(r.name())).collect(Collectors.toSet()));
            }
            return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo(), "email");
        };

        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/public/**", "/error").permitAll()
                        .requestMatchers("/teacher/**").hasRole("TEACHER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(customOidcUserService))
                        )
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll());

        return httpSecurity.build();
    }
}
