package com.projectD.multisimbox.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAuthorityService {
    private final JdbcTemplate jdbcTemplate;

    public List<String> findAuthoritiesByLogin(String login) {
        List<String> roles = jdbcTemplate.query(
                """
                SELECT r.authority
                FROM "user" u
                JOIN "group" g ON g.id = u.group_id
                LEFT JOIN group_role gr ON gr.group_id = g.id
                LEFT JOIN role r ON r.id = gr.role_id
                WHERE u.login = ?
                """,
                (rs, rowNum) -> rs.getString(1),
                login
        );

        List<String> filtered = roles.stream()
                .filter(value -> value != null && !value.isBlank())
                .distinct()
                .toList();

        if (!filtered.isEmpty()) {
            return filtered;
        }

        String groupName = jdbcTemplate.query(
                """
                SELECT g.name
                FROM "user" u
                JOIN "group" g ON g.id = u.group_id
                WHERE u.login = ?
                """,
                rs -> rs.next() ? rs.getString(1) : null,
                login
        );

        if ("admins".equalsIgnoreCase(groupName)) {
            return List.of("ROLE_ADMIN", "ROLE_USER");
        }
        if ("users".equalsIgnoreCase(groupName)) {
            return List.of("ROLE_USER");
        }

        return new ArrayList<>();
    }
}
