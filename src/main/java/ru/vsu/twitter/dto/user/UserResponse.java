package ru.vsu.twitter.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
