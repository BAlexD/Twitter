package ru.vsu.twitter.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.twitter.entity.Comments;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
//    @Override
//    public List<Comments> findAll(Sort sort);
}
