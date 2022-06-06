package ru.vsu.twitter.dto.comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentsResponse {
    private Long id;
    private String post_id;
    private String user_id;
    private String content;
}
