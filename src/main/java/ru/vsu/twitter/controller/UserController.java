package ru.vsu.twitter.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.twitter.dto.user.UserCreateRequest;
import ru.vsu.twitter.dto.user.UserResponse;
import ru.vsu.twitter.dto.user.UserUpdateRequest;
import ru.vsu.twitter.service.user.UserService;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Возвращает всех пользователей")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.geAllUsers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Возвращает пользователя по id")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    @Operation(summary = "Добавляет пользователя")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserCreateRequest userCreateRequest) throws NoSuchAlgorithmException {
        return ResponseEntity.ok(userService.saveUser(userCreateRequest));
    }

    @PutMapping
    @Operation(summary = "Обновляет пользователя")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.ok(userService.updateUser(userUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаляет пользователя по id")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/authorize")
    @Operation(summary = "Возвращает пользователя по логину и паролю")
    public ResponseEntity<UserResponse> authorize(@RequestParam String login, @RequestParam String password) throws NoSuchAlgorithmException {
        return ResponseEntity.ok(userService.findByLoginAndPassword(login, password));
    }

    @GetMapping("/search")
    @Operation(summary = "Возвращает пользователя по совпадению логина")
    public ResponseEntity<List<UserResponse>> search(@RequestParam String filter, @RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(userService.searchByFilter(filter, page, size));
    }
}
