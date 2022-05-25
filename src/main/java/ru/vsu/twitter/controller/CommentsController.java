package ru.vsu.twitter.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.twitter.dto.comments.*;
import ru.vsu.twitter.service.comments.CommentsService;


import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentsController {
    private final CommentsService commentsService;

    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @GetMapping
    @Operation(summary = "Возвращает все комментарии")
    public ResponseEntity<List<CommentsResponse>> getAllComments() {
        return ResponseEntity.ok(commentsService.geAllComments());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Возвращает лайк по id")
    public ResponseEntity<CommentsResponse> getCommentsById(@PathVariable Long id) {
        return ResponseEntity.ok(commentsService.getCommentsById(id));
    }

    @PostMapping
    @Operation(summary = "Добавляет комментарий")
    public ResponseEntity<CommentsResponse> addComments(@RequestBody CommentsCreateRequest commentsCreateRequest) {
        return ResponseEntity.ok(commentsService.saveComments(commentsCreateRequest));
    }

    @PutMapping
    @Operation(summary = "Обновляет комментарий")
    public ResponseEntity<CommentsResponse> updateComments(@RequestBody CommentsUpdateRequest commentsUpdateRequest) {
        return ResponseEntity.ok(commentsService.updateComments(commentsUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаляет комментарий по id")
    public ResponseEntity<?> deleteComments(@PathVariable Long id) {
        commentsService.deleteCommentsById(id);
        return ResponseEntity.ok().build();
    }

}
