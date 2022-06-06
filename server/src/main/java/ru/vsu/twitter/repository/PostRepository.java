package ru.vsu.twitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.twitter.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
