package ru.vsu.twitter.service.comments;

import org.springframework.stereotype.Service;
import ru.vsu.twitter.dto.comments.CommentsCreateRequest;
import ru.vsu.twitter.dto.comments.CommentsResponse;
import ru.vsu.twitter.dto.comments.CommentsUpdateRequest;
import ru.vsu.twitter.entity.Comments;
import ru.vsu.twitter.mapper.CommentsMapper;
import ru.vsu.twitter.repository.CommentsRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final CommentsMapper commentsMapper;

    public CommentsService(CommentsRepository commentsRepository, CommentsMapper commentsMapper) {
        this.commentsRepository = commentsRepository;
        this.commentsMapper = commentsMapper;
    }

    public List<CommentsResponse> geAllComments() {
        return commentsRepository.findAll().stream().
                map(commentsMapper::commentsToCommentsResponse)
                .collect(Collectors.toList());
    }

    public CommentsResponse getCommentsById(Long id) {
        Optional<Comments> commentsById = commentsRepository.findById(id);
        if (commentsById.isEmpty()) {
            throw new RuntimeException("there is no comments with such id");
        }
        return commentsMapper.commentsToCommentsResponse(commentsById.get());
    }

    public CommentsResponse saveComments(CommentsCreateRequest commentsCreateRequest) {
        Comments comments = commentsMapper.commentsCreateRequestToComments(commentsCreateRequest);
        return commentsMapper.commentsToCommentsResponse(commentsRepository.save(comments));
    }

    public CommentResponse updateComments(CommentUpdateRequest commentUpdateRequest) {
        if (!commentsRepository.existsById(commentUpdateRequest.getId())) {
            throw new RuntimeException("there is no comments with such id");
        } else {
            Comment comment = commentsMapper.updateRequestToComments(commentUpdateRequest);
            return commentsMapper.commentsToCommentsResponse(this.commentsRepository.save(comment));
        }
    }

    public void deleteCommentsById(Long id) {
        if (!commentsRepository.existsById(id)) {
            throw new RuntimeException("there is no user with such id");
        } else {
            commentsRepository.deleteById(id);
        }
    }

    public List<CommentResponse> getAllCommentByPostId(Long id) {
        List<CommentResponse> commentResponses = commentsRepository.findAllByPostId(id).stream();
        return commentResponses.stream().map(commentsMapper::commentsToCommentsResponse).collect(Collectors.toList())
    }

    public Long getCommentCountByPostId(Long id) {
        return commentsRepository.countCommentsByPostId(id);
    }
}
}
