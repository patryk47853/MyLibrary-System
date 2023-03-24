package pl.patrykjava.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {

    private String username;
    private String email;
    private String password;

    private Timestamp createdAt;

    private boolean enabled;
}
