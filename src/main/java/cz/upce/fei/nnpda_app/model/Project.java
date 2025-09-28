package cz.upce.fei.nnpda_app.model;

import cz.upce.fei.nnpda_app.enums.ProjectStatus;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "projects")
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    @Column(unique = true)
    private String name;

    @Nonnull
    private String description;

    @Nonnull
    private ProjectStatus active;

    //USER

}
