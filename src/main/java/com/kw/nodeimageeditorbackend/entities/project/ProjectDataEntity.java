package com.kw.nodeimageeditorbackend.entities.project;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
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
    @JoinColumn(name = "project_id")
    private ProjectEntity project;
}
