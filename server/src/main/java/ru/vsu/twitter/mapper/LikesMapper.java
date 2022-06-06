package ru.vsu.twitter.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.vsu.twitter.dto.likes.LikesCreateRequest;
import ru.vsu.twitter.dto.likes.LikesResponse;
import ru.vsu.twitter.dto.likes.LikesUpdateRequest;
import ru.vsu.twitter.entity.Likes;



@Mapper
@Component
public interface LikesMapper {
    Likes likesCreateRequestToLikes(LikesCreateRequest likesCreateRequest);

    LikesResponse likesToLikesResponse(Likes likes);

    Likes updateRequestToLikes(LikesUpdateRequest likesUpdateRequest);
}
