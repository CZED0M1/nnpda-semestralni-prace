package cz.upce.fei.nnpda_app.dto.User;

import cz.upce.fei.nnpda_app.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String username;
    @Email(message = "Email není ve správném formátu")
    private String email;
    @Size(min = 6, message = "Heslo musí mít alespoň 6 znaků")
    private String password;

    public User toUser() {
        return new User(username, email, password);
    }

}
