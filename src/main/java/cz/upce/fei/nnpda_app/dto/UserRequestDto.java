package cz.upce.fei.nnpda_app.dto;

import cz.upce.fei.nnpda_app.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String username;
    private String email;
    private String password;

    public User toUser() {
        return new User(username, email, password);
    }

}
