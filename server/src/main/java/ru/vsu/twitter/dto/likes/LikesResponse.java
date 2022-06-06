package ru.vsu.twitter.dto.likes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikesResponse {
    private Long id;
    private String post_id;
    private String user_id;
}
