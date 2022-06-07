package ru.vsu.twitter.service.user;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.vsu.twitter.dto.user.UserCreateRequest;
import ru.vsu.twitter.dto.user.UserResponse;
import ru.vsu.twitter.dto.user.UserUpdateRequest;
import ru.vsu.twitter.entity.User;
import ru.vsu.twitter.mapper.UserMapper;
import ru.vsu.twitter.repository.PostRepository;
import ru.vsu.twitter.repository.SubscribeRepository;
import ru.vsu.twitter.repository.UserRepository;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PostRepository postService;
    private final SubscribeRepository subscribeService;

    public UserService(UserRepository userRepository, UserMapper userMapper, PostRepository postService, SubscribeRepository subscribeService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.postService = postService;
        this.subscribeService = subscribeService;
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

    public UserResponse saveUser(UserCreateRequest userCreateRequest) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(userCreateRequest.getPassword().getBytes());
        byte[] digest = md5.digest();
        String hashPass = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        userCreateRequest.setPassword(hashPass);
        if (userRepository.existsByLogin(userCreateRequest.getLogin())){
            throw new RuntimeException("User already exist");
        }
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
    subscribeService.deleteAllByProfileIdOrSubscriberId(id, id);
    postService.deleteAllByUserId(id);
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("there is no user with such id");
        }
        userRepository.deleteById(id);
    }

    public List<UserResponse> findAllByIds(List<Long> ids){
        return userRepository.
                findAllById(ids).stream()
                .map(userMapper::userToUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse findByLoginAndPassword(String login, String password) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(password.getBytes());
        byte[] digest = md5.digest();
        String hashPass = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        User user = userRepository.
                findTopByLoginAndPassword(login, hashPass)
                .orElseThrow(() -> new RuntimeException("User unregistrated"));
        return userMapper.userToUserResponse(user);
    }

    public List<UserResponse> searchByFilter(String filter, Integer page, Integer size){
        return userRepository.findByLoginContaining(filter, PageRequest.of(page, size)).stream().
                map(userMapper::userToUserResponse).
                collect(Collectors.toList());
    }
}
