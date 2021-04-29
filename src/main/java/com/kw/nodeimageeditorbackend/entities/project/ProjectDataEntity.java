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

    //TODO potencjalnie typ "byte" nie zadziała
    @Column(name = "project_data")
    private byte[] projectData;

    @OneToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;
}
