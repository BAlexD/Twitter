package ru.vsu.twitter.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
