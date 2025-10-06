package cz.upce.fei.nnpda_app.model;

import cz.upce.fei.nnpda_app.dto.Project.ProjectResponseDto;
import cz.upce.fei.nnpda_app.enums.ProjectStatus;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "app_projects")
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    @Column(unique = true)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status = ProjectStatus.ACTIVE;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;

    public ProjectResponseDto toResponseDto() {

    return new ProjectResponseDto(this.id, this.name, this.description, this.status);
    }

}
