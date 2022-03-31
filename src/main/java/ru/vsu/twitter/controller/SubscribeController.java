package ru.vsu.twitter.controller;

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
    public ResponseEntity<List<SubscribeResponse>> getAllSubscribes() {
        return ResponseEntity.ok(subscribeService.geAllSubscribes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscribeResponse> getSubscribeById(@PathVariable Long id) {
        return ResponseEntity.ok(subscribeService.getSubscribeById(id));
    }

    @PostMapping
    public ResponseEntity<SubscribeResponse> addSubscribe(@RequestBody SubscribeCreateRequest subscribeCreateRequest) {
        return ResponseEntity.ok(subscribeService.saveSubscribe(subscribeCreateRequest));
    }

    @PutMapping
    public ResponseEntity<SubscribeResponse> updateSubscribe(@RequestBody SubscribeUpdateRequest subscribeUpdateRequest) {
        return ResponseEntity.ok(subscribeService.updateSubscribe(subscribeUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubscribe(@PathVariable Long id) {
        subscribeService.deleteSubscribeById(id);
        return ResponseEntity.ok().build();
    }
}
