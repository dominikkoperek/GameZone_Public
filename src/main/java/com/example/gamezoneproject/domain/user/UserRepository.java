package com.example.gamezoneproject.domain.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Map;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByLoginIgnoreCase(String login);

    Optional<User> findByEmailIgnoreCase(String email);
    Optional<User> findByToken_id(Long id);

}
