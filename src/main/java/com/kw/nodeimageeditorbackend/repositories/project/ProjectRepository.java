package com.kw.nodeimageeditorbackend.repositories.project;

import com.kw.nodeimageeditorbackend.entities.project.ProjectCollaboratorEntity;
import com.kw.nodeimageeditorbackend.entities.project.ProjectEntity;
import com.kw.nodeimageeditorbackend.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    @Query(value = "SELECT p FROM ProjectEntity p JOIN ProjectCollaboratorEntity c ON (c.id = :collaboratorId)")
    List<ProjectEntity> findAllByCollaboratorId(Long collaboratorId);

//    List<ProjectEntity> findAllByProjectOwner(UserEntity projectOwner);

    List<ProjectEntity> findAllByProjectOwnerId(Long id);

    List<ProjectEntity> findByCollaboratorsContaining(ProjectCollaboratorEntity collaborator);

    Optional<ProjectEntity> findOneById(Long id);

}
