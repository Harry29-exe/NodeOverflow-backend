package com.kw.nodeimageeditorbackend.project.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "project_data")
public class ProjectDataEntity {

    @Id
    @Column(name = "project_id")
    private Long id;

    //TODO String powinien być mappowanny do jsonb
    @Column(name = "project_data")
    private String projectData;

    @OneToOne
    @MapsId
    @JoinColumn(name = "project_id")
    private ProjectEntity project;
}
