package com.projectD.multisimbox.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityBootstrap implements ApplicationRunner {
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        Long roleUserId = upsertRole("ROLE_USER");
        Long roleAdminId = upsertRole("ROLE_ADMIN");

        Long usersGroupId = upsertGroup("users");
        Long adminsGroupId = upsertGroup("admins");

        linkGroupRole(usersGroupId, roleUserId);
        linkGroupRole(adminsGroupId, roleUserId);
        linkGroupRole(adminsGroupId, roleAdminId);

        upsertUser("user", "user123", "user@projectd.local", usersGroupId);
        upsertUser("admin", "admin123", "admin@projectd.local", adminsGroupId);
    }

    private Long upsertRole(String authority) {
        return jdbcTemplate.queryForObject(
                """
                INSERT INTO role(authority)
                VALUES (?)
                ON CONFLICT (authority)
                DO UPDATE SET authority = EXCLUDED.authority
                RETURNING id
                """,
                Long.class,
                authority
        );
    }

    private Long upsertGroup(String name) {
        return jdbcTemplate.queryForObject(
                """
                INSERT INTO \"group\"(name, is_active)
                VALUES (?, true)
                ON CONFLICT (name)
                DO UPDATE SET is_active = EXCLUDED.is_active
                RETURNING id
                """,
                Long.class,
                name
        );
    }

    private void linkGroupRole(Long groupId, Long roleId) {
        jdbcTemplate.update(
                """
                INSERT INTO group_role(group_id, role_id)
                VALUES (?, ?)
                ON CONFLICT (group_id, role_id)
                DO NOTHING
                """,
                groupId,
                roleId
        );
    }

    private void upsertUser(String login, String rawPassword, String email, Long groupId) {
        jdbcTemplate.update(
                """
                INSERT INTO \"user\"(login, email, status, auth_type, password_hash, group_id)
                VALUES (?, ?, 'ACTIVE', 'LOCAL', ?, ?)
                ON CONFLICT (login)
                DO UPDATE SET
                    email = EXCLUDED.email,
                    status = EXCLUDED.status,
                    auth_type = EXCLUDED.auth_type,
                    password_hash = EXCLUDED.password_hash,
                    group_id = EXCLUDED.group_id
                """,
                login,
                email,
                passwordEncoder.encode(rawPassword),
                groupId
        );
    }
}
