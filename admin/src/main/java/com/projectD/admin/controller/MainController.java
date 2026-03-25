package com.projectD.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
public class MainController {

    @GetMapping("/secured")
    public Mono<String> secured() {
        return Mono.just("secured data");
    }

    @GetMapping("/admin")
    public Mono<String> admin() {
        return Mono.just("admin data");
    }

    @GetMapping("/info")
    public Mono<String> info(Mono<Authentication> auth) {
        return auth.map(Authentication::getName);
    }
}
