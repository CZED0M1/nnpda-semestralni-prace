package cz.upce.fei.nnpda_app.dto.User;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestPasswordDto {
    private UUID token;
    @Size(min=6)
    private String newPassword;
}
