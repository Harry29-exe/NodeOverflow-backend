package com.kw.nodeimageeditorbackend.entities.project;

import com.kw.nodeimageeditorbackend.entities.enums.AccessModifier;
import com.kw.nodeimageeditorbackend.entities.user.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class ProjectEntity {

    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private UserEntity projectOwner;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ProjectCollaboratorEntity> collaborators;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ProjectTagEntity> tags;

    @OneToOne(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private ProjectDataEntity projectData;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_modifier")
    private AccessModifier accessModifier;

    @Column(name = "title")
    private String title;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "last_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;
}
