package ru.vsu.twitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.twitter.entity.Likes;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long>{
}
