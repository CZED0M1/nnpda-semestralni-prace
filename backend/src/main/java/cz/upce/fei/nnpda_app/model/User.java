package cz.upce.fei.nnpda_app.model;

import cz.upce.fei.nnpda_app.dto.User.UserResponseDto;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "app_users")
@NoArgsConstructor
@RequiredArgsConstructor
public class User implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    @Column(unique = true)
    private String username;

    @Nonnull
    private String email;

    private UUID resetToken;

    private LocalDateTime resetTokenExpiry;

    @Nonnull
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;

    public UserResponseDto toResponseDto() {
       return new UserResponseDto(this.id,this.username,this.email);
    }
    @Override
    public String toString() {
        return "User{id=" + id + "}";
    }
}
