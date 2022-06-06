package ru.vsu.twitter.dto.comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentsCreateRequest {
    private String post_id;
    private String user_id;
    private String content;
}
