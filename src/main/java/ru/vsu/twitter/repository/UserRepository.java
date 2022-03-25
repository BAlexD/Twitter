package ru.vsu.twitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.twitter.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
