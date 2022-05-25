package ru.vsu.twitter.controller;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.twitter.dto.post.*;
import ru.vsu.twitter.service.post.PostService;


import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    @Operation(summary = "Возвращает все посты")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.geAllPosts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Возвращает пост по id")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping
    @Operation(summary = "Добавляет новый пост")
    public ResponseEntity<PostResponse> addPost(@RequestBody PostCreateRequest postCreateRequest) {
        return ResponseEntity.ok(postService.savePost(postCreateRequest));
    }

    @PutMapping
    @Operation(summary = "Обновляет пост")
    public ResponseEntity<PostResponse> updatePost(@RequestBody PostUpdateRequest postUpdateRequest) {
        return ResponseEntity.ok(postService.updatePost(postUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаляет пост по id")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postService.deletePostById(id);
        return ResponseEntity.ok().build();
    }
}
