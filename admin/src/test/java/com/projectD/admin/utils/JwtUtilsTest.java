package com.projectD.admin.utils;

import com.projectD.admin.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtUtilsTest {

    private static final String SECRET = "lhgadhkjfsaofewjvkbjvdksdfjefjfaejkkas";

    @Test
    void generateTokenCreatesTokenWithSubjectRolesAndLifetime() {
        JwtUtils jwtUtils = new JwtUtils();
        ReflectionTestUtils.setField(jwtUtils, "secret", SECRET);
        ReflectionTestUtils.setField(jwtUtils, "jwtLifetime", Duration.ofMinutes(30));
        jwtUtils.init();

        UserDto user = new UserDto("admin", List.of("ROLE_ADMIN"));

        String token = jwtUtils.generateToken(user);

        assertNotNull(token);
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();

        assertEquals("admin", claims.getSubject());
        assertEquals(List.of("ROLE_ADMIN"), claims.get("roles", List.class));
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
        long diff = claims.getExpiration().getTime() - claims.getIssuedAt().getTime();
        assertTrue(diff > 0);
        assertTrue(diff <= Duration.ofMinutes(30).toMillis() + 2000);
    }
}
