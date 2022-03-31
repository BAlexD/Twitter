package ru.vsu.twitter.dto.post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private Long userId; //TODO добавить связь
    private String content;
}
