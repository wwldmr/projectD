package com.projectD.admin.controller;

import com.projectD.admin.client.AuthClient;
import com.projectD.admin.dto.JwtRequest;
import com.projectD.admin.dto.JwtResponse;
import com.projectD.admin.dto.LoginRequest;
import com.projectD.admin.dto.UserDto;
import com.projectD.admin.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthClient authClient;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public Mono<ResponseEntity<JwtResponse>> login(@RequestBody LoginRequest request) {
        return authClient.validate(request)
                .map(user -> new JwtResponse(jwtUtils.generateToken(user)))
                .map(ResponseEntity::ok);
    }
}
