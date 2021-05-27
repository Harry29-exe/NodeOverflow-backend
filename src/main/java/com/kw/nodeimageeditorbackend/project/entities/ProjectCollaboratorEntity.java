package com.kw.nodeimageeditorbackend.project.entities;

import com.kw.nodeimageeditorbackend.user.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_collaborators")
public class ProjectCollaboratorEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
