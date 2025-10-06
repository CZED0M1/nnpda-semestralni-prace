package cz.upce.fei.nnpda_app.dto.User;

import lombok.Data;

@Data
public class UserChangePasswordDto {
    private String oldPassword;
    private String newPassword;
}
