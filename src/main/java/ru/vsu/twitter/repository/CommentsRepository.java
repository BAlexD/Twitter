package ru.vsu.twitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.twitter.entity.Comments;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
}
