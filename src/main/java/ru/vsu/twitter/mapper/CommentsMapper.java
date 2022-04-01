package ru.vsu.twitter.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.vsu.twitter.dto.comments.CommentsCreateRequest;
import ru.vsu.twitter.dto.comments.CommentsResponse;
import ru.vsu.twitter.dto.comments.CommentsUpdateRequest;
import ru.vsu.twitter.entity.Comments;



@Mapper
@Component
public interface CommentsMapper {
    Comments commentsCreateRequestToComments(CommentsCreateRequest commentsCreateRequest);

    CommentsResponse commentsToCommentsResponse(Comments comments);

    Comments updateRequestToComments(CommentsUpdateRequest commentsUpdateRequest);
}
