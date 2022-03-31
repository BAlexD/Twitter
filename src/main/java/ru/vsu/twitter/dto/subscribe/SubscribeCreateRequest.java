package ru.vsu.twitter.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeCreateRequest {
    private Long profileId; //TODO добавить связь
    private String subscriberId; //TODO добавить связь
}
