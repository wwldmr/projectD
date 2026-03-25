package com.projectD.multisimbox.repository;

import com.projectD.multisimbox.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @EntityGraph(attributePaths = {"group", "group.roles"})
    Optional<User> findByLogin(String login);
}
