package ru.vsu.twitter.service.user;

import org.springframework.stereotype.Service;
import ru.vsu.twitter.dto.user.UserCreateRequest;
import ru.vsu.twitter.dto.user.UserResponse;
import ru.vsu.twitter.dto.user.UserUpdateRequest;
import ru.vsu.twitter.entity.User;
import ru.vsu.twitter.mapper.UserMapper;
import ru.vsu.twitter.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponse> geAllUsers() {
        return userRepository.findAll().stream().
                map(userMapper::userToUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isEmpty()) {
            throw new RuntimeException("there is no user with such id");
        }
        return userMapper.userToUserResponse(userById.get());
    }

    public UserResponse saveUser(UserCreateRequest userCreateRequest) {
        User user = userMapper.UserCreateRequestToUser(userCreateRequest);
        return userMapper.userToUserResponse(userRepository.save(user));
    }

    public UserResponse updateUser(UserUpdateRequest userUpdateRequest) {
        if (!userRepository.existsById(userUpdateRequest.getId())) {
            throw new RuntimeException("there is no user with such id");
        }
        User user = userMapper.updateRequestToUser(userUpdateRequest);
        return userMapper.userToUserResponse(userRepository.save(user));
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("there is no user with such id");
        }
        userRepository.deleteById(id);
    }
}
