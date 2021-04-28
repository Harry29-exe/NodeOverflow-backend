package com.kw.nodeimageeditorbackend.repositories.project;

import com.kw.nodeimageeditorbackend.entities.project.ProjectEntity;
import com.kw.nodeimageeditorbackend.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    List<ProjectEntity> findAllByProjectOwner(UserEntity projectOwner);

}
