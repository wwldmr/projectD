package com.projectD.admin.config;

import com.projectD.admin.client.AuthClient;
import com.projectD.admin.controller.AuthController;
import com.projectD.admin.controller.MainController;
import com.projectD.admin.dto.LoginRequest;
import com.projectD.admin.dto.UserDto;
import com.projectD.admin.utils.JwtUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = {MainController.class, AuthController.class})
@Import(SecurityConfig.class)
@TestPropertySource(properties = "jwt.secret=lhgadhkjfsaofewjvkbjvdksdfjefjfaejkkas")
class SecurityConfigTest {

    private static final String SECRET = "lhgadhkjfsaofewjvkbjvdksdfjefjfaejkkas";

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private AuthClient authClient;

    @MockitoBean
    private JwtUtils jwtUtils;

    @Test
    void authLoginIsAccessibleWithoutToken() {
        when(authClient.validate(any(LoginRequest.class)))
                .thenReturn(Mono.just(new UserDto("admin", List.of("ROLE_ADMIN"))));
        when(jwtUtils.generateToken(any(UserDto.class))).thenReturn("token-123");

        webTestClient.post()
                .uri("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"username\":\"admin\",\"password\":\"admin\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.token").isEqualTo("token-123");
    }

    @Test
    void securedEndpointReturnsUnauthorizedWithoutToken() {
        webTestClient.get()
                .uri("/secured")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void adminEndpointReturnsForbiddenForNonAdminRole() {
        String token = tokenWithRoles(List.of("ROLE_USER"));

        webTestClient.get()
                .uri("/admin")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void adminEndpointAllowsAdminRole() {
        String token = tokenWithRoles(List.of("ROLE_ADMIN"));

        webTestClient.get()
                .uri("/admin")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("admin data");
    }

    private String tokenWithRoles(List<String> roles) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
        Date now = new Date();

        return Jwts.builder()
                .claim("roles", roles)
                .subject("tester")
                .issuedAt(now)
                .expiration(new Date(now.getTime() + 60_000))
                .signWith(key)
                .compact();
    }
}
