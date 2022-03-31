package ru.vsu.twitter.controller;
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
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.geAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping
    public ResponseEntity<PostResponse> addPost(@RequestBody PostCreateRequest postCreateRequest) {
        return ResponseEntity.ok(postService.savePost(postCreateRequest));
    }

    @PutMapping
    public ResponseEntity<PostResponse> updatePost(@RequestBody PostUpdateRequest postUpdateRequest) {
        return ResponseEntity.ok(postService.updatePost(postUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postService.deletePostById(id);
        return ResponseEntity.ok().build();
    }
}
