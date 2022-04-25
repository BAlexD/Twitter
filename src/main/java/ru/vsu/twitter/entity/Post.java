package ru.vsu.twitter.entity;

import lombok.*;
import ru.vsu.twitter.dto.user.UserResponse;

import javax.persistence.*;
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "posts")

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String content;
}
