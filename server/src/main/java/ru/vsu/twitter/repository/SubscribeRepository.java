package ru.vsu.twitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.twitter.entity.Subscribe;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long>{
}
