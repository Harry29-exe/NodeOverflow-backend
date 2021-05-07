package com.kw.nodeimageeditorbackend.project.repositories;

import com.kw.nodeimageeditorbackend.project.entities.ProjectCollaboratorEntity;
import com.kw.nodeimageeditorbackend.project.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    @Query(value = "SELECT p FROM ProjectEntity p JOIN ProjectCollaboratorEntity c ON (c.id = :collaboratorId)")
    List<ProjectEntity> findAllByCollaboratorId(Long collaboratorId);

    List<ProjectEntity> findAllByProjectOwnerId(Long id);

    List<ProjectEntity> findByCollaboratorsContaining(ProjectCollaboratorEntity collaborator);

    List<ProjectEntity> findAllByCreationDateBetweenAndLastModifiedBetween(Date beforeCreationDate, Date afterCreationDate, Date beforeLastModified, Date afterLastModified);

    Optional<ProjectEntity> findOneById(Long id);
}
