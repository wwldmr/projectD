package com.projectD.multisimbox.controller;

import com.projectD.multisimbox.dto.LoginRequest;
import com.projectD.multisimbox.dto.UserDto;
import com.projectD.multisimbox.entity.User;
import com.projectD.multisimbox.repository.UserRepository;
import com.projectD.multisimbox.service.UserAuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthorityService userAuthorityService;

    @PostMapping("/validate")
    public ResponseEntity<UserDto> validate(@RequestBody LoginRequest request) {
        User user = userRepository.findByLogin(request.username())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getPasswordHash() == null || !passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new RuntimeException("Bad credentials");
        }

        List<String> roles = userAuthorityService.findAuthoritiesByLogin(user.getLogin());

        return ResponseEntity.ok(new UserDto(user.getLogin(), roles));
    }
}
