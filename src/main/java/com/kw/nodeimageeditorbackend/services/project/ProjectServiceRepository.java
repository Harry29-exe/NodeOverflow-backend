package com.kw.nodeimageeditorbackend.services.project;

import com.kw.nodeimageeditorbackend.dto.ProjectDetailsDto;
import com.kw.nodeimageeditorbackend.dto.ProjectDto;
import com.kw.nodeimageeditorbackend.entities.project.ProjectEntity;
import com.kw.nodeimageeditorbackend.exceptions.persistence.EntityDoesNotExist;
import com.kw.nodeimageeditorbackend.repositories.project.ProjectRepository;
import com.kw.nodeimageeditorbackend.repositories.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceRepository implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectServiceRepository(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ProjectDetailsDto> getUserProjectsDetails(Long userId) {
        List<ProjectEntity> projectEntities = projectRepository.findAllByProjectOwnerId(userId);

        return projectEntities.stream()
                .map(ProjectDetailsDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDto getProject(Long projectId, boolean withProjectDetails) {
        var projectEntity = projectRepository.findOneById(projectId);

        return new ProjectDto(projectEntity.orElseThrow(EntityDoesNotExist::new));
    }
}
