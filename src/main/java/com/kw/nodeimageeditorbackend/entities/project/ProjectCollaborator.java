package com.kw.nodeimageeditorbackend.entities.project;

import com.kw.nodeimageeditorbackend.entities.user.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "project_collaborators")
public class ProjectCollaborator {

    @Id
    @Column
    private Long id;

    @OneToOne
    @JoinColumn(name = "collaborator_id")
    private UserEntity collaborator;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @Column(name = "canwrite")
    private Boolean canWrite;

    @Column(name = "canfork")
    private Boolean canFork;
}
