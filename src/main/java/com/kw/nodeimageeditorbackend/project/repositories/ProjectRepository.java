package com.kw.nodeimageeditorbackend.project.repositories;

import com.kw.nodeimageeditorbackend.project.entities.ProjectCollaboratorEntity;
import com.kw.nodeimageeditorbackend.project.entities.ProjectEntity;
import com.kw.nodeimageeditorbackend.user.entities.UserEntity;
import org.springframework.data.domain.Pageable;
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

    Integer countAllByProjectOwnerId(Long id);

    Integer countAllByProjectOwnerIdOrCollaboratorsContaining(Long id, ProjectCollaboratorEntity collaborator);

    List<ProjectEntity> findAllByProjectOwnerId(Long id, Pageable pageable);

    List<ProjectEntity> findByProjectOwnerOrCollaboratorsContaining(UserEntity owner, ProjectCollaboratorEntity collaborator, Pageable pageable);

    List<ProjectEntity> findAllByProjectOwnerIdAndCreationDateBetweenAndLastModifiedBetween(Long ownerId, Date afterCreationDate, Date beforeCreationDate, Date afterLastModified, Date beforeLastModified, Pageable pageable);

    List<ProjectEntity> findAllByProjectOwnerIdAndCreationDateBetweenAndLastModifiedBetween(Long ownerId, Date afterCreationDate, Date beforeCreationDate, Date afterLastModified, Date beforeLastModified);

    List<ProjectEntity> findAllByCollaboratorsContainingAndCreationDateBetweenAndLastModifiedBetween(ProjectCollaboratorEntity collaborator, Date afterCreationDate, Date beforeCreationDate, Date afterLastModified, Date beforeLastModified, Pageable pageable);

    List<ProjectEntity> findAllByCollaboratorsContainingAndCreationDateBetweenAndLastModifiedBetween(ProjectCollaboratorEntity collaborator, Date afterCreationDate, Date beforeCreationDate, Date afterLastModified, Date beforeLastModified);
}
