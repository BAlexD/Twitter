package ru.vsu.twitter.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.vsu.twitter.dto.post.PostCreateRequest;
import ru.vsu.twitter.dto.post.PostUpdateRequest;
import ru.vsu.twitter.dto.post.*;
import ru.vsu.twitter.entity.Post;

@Mapper
@Component
public interface PostMapper {
    Post PostCreateRequestToPost(PostCreateRequest postCreateRequest);

    PostResponse postToPostResponse(Post post);

    Post updateRequestToPost(PostUpdateRequest postUpdateRequest);
}
