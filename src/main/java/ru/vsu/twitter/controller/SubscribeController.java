package ru.vsu.twitter.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.twitter.dto.subscribe.SubscribeCreateRequest;
import ru.vsu.twitter.dto.subscribe.SubscribeResponse;
import ru.vsu.twitter.dto.subscribe.SubscribeUpdateRequest;
import ru.vsu.twitter.service.subscribe.SubscribeService;

import java.util.List;

@RestController
@RequestMapping("/subscribe")
public class SubscribeController {
    private final SubscribeService subscribeService;

    public SubscribeController(SubscribeService subscribeService) {
        this.subscribeService = subscribeService;
    }

    @GetMapping
    @Operation(summary = "Возвращает все подписки пользователей")
    public ResponseEntity<List<SubscribeResponse>> getAllSubscribes() {
        return ResponseEntity.ok(subscribeService.geAllSubscribes());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Возвращает подписку по id")
    public ResponseEntity<SubscribeResponse> getSubscribeById(@PathVariable Long id) {
        return ResponseEntity.ok(subscribeService.getSubscribeById(id));
    }

    @PostMapping
    @Operation(summary = "Добавляет новую подписку")
    public ResponseEntity<SubscribeResponse> addSubscribe(@RequestBody SubscribeCreateRequest subscribeCreateRequest) {
        return ResponseEntity.ok(subscribeService.saveSubscribe(subscribeCreateRequest));
    }

    @PutMapping
    @Operation(summary = "Обновляет подписку")
    public ResponseEntity<SubscribeResponse> updateSubscribe(@RequestBody SubscribeUpdateRequest subscribeUpdateRequest) {
        return ResponseEntity.ok(subscribeService.updateSubscribe(subscribeUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаляет подписку по id")
    public ResponseEntity<?> deleteSubscribe(@PathVariable Long id) {
        subscribeService.deleteSubscribeById(id);
        return ResponseEntity.ok().build();
    }
}
