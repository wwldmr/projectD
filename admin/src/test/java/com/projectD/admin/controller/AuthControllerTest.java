package com.projectD.admin.controller;

import com.projectD.admin.client.AuthClient;
import com.projectD.admin.dto.JwtResponse;
import com.projectD.admin.dto.LoginRequest;
import com.projectD.admin.dto.UserDto;
import com.projectD.admin.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthClient authClient;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthController authController;

    @Test
    void loginReturnsTokenWhenCredentialsAreValid() {
        LoginRequest request = new LoginRequest("admin", "admin");
        UserDto user = new UserDto("admin", List.of("ROLE_ADMIN"));

        when(authClient.validate(request)).thenReturn(Mono.just(user));
        when(jwtUtils.generateToken(user)).thenReturn("token-123");

        StepVerifier.create(authController.login(request))
                .assertNext(response -> {
                    JwtResponse body = response.getBody();
                    org.junit.jupiter.api.Assertions.assertNotNull(body);
                    org.junit.jupiter.api.Assertions.assertEquals("token-123", body.token());
                })
                .verifyComplete();

        verify(authClient).validate(request);
        verify(jwtUtils).generateToken(user);
    }

    @Test
    void loginPropagatesErrorFromAuthClient() {
        LoginRequest request = new LoginRequest("admin", "wrong");
        RuntimeException expected = new RuntimeException("invalid credentials");
        when(authClient.validate(request)).thenReturn(Mono.error(expected));

        StepVerifier.create(authController.login(request))
                .expectErrorMatches(error -> error == expected)
                .verify();

        verify(authClient).validate(request);
    }
}
