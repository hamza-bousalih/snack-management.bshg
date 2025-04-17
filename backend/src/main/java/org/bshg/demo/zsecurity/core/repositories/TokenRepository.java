package org.bshg.demo.zsecurity.core.repositories;

import org.bshg.demo.zsecurity.core.entities.Token;
import org.bshg.demo.zsecurity.core.entities.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository< Token, Long> {
    Optional< Token> findByTokenAndType(String token, TokenType type);
}
