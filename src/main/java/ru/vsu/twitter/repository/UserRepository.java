package ru.vsu.twitter.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.twitter.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findTopByLoginAndPassword(String login, String password);
    List<User> findByLoginContaining(String filter, Pageable page);
    Boolean existsByLogin(String login);
}
