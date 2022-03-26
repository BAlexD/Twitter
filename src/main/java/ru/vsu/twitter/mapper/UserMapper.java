package ru.vsu.twitter.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.vsu.twitter.dto.user.UserCreateRequest;
import ru.vsu.twitter.dto.user.UserResponse;
import ru.vsu.twitter.dto.user.UserUpdateRequest;
import ru.vsu.twitter.entity.User;

@Mapper
@Component
public interface UserMapper {
    User UserCreateRequestToUser(UserCreateRequest userCreateRequest);

    UserResponse userToUserResponse(User user);

    User updateRequestToUser(UserUpdateRequest userUpdateRequest);
}
