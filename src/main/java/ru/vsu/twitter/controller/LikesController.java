package ru.vsu.twitter.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.twitter.dto.likes.LikesCreateRequest;
import ru.vsu.twitter.dto.likes.LikesResponse;
import ru.vsu.twitter.dto.likes.LikesUpdateRequest;
import ru.vsu.twitter.service.likes.LikesService;


import java.util.List;
@RestController
@RequestMapping("/likes")
public class LikesController {
    private final LikesService likesService;

    public LikesController(LikesService likesService) {this.likesService = likesService;}

    @GetMapping
    @Operation(summary = "Возвращает все лайки")
    public ResponseEntity<List<LikesResponse>> getAllLikes() {
        return ResponseEntity.ok(likesService.geAllLikes());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Возвращает лайки по id")
    public ResponseEntity<LikesResponse> getLikesById(@PathVariable Long id) {
        return ResponseEntity.ok(likesService.getLikesById(id));
    }

    @PostMapping
    @Operation(summary = "Добавляет лайк")
    public ResponseEntity<LikesResponse> addLikes(@RequestBody LikesCreateRequest likesCreateRequest) {
        return ResponseEntity.ok(likesService.saveLikes(likesCreateRequest));
    }

    @PutMapping
    @Operation(summary = "Обнавляет лайк")
    public ResponseEntity<LikesResponse> updateLikes(@RequestBody LikesUpdateRequest likesUpdateRequest) {
        return ResponseEntity.ok(likesService.updateLikes(likesUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаляет лайк по id")
    public ResponseEntity<?> deleteLikes(@PathVariable Long id) {
        likesService.deleteLikesById(id);
        return ResponseEntity.ok().build();
    }
}
