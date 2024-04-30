package com.example.gamezoneproject.domain.userToken;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TemporaryTokenRepository extends CrudRepository<TemporaryToken,Long> {
    Optional<TemporaryToken> findByToken(String name);
}
