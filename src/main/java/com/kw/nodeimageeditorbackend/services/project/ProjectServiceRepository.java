package com.kw.nodeimageeditorbackend.services.project;

import com.kw.nodeimageeditorbackend.dto.ProjectDto;
import com.kw.nodeimageeditorbackend.entities.project.ProjectEntity;
import com.kw.nodeimageeditorbackend.entities.user.UserEntity;
import com.kw.nodeimageeditorbackend.repositories.project.ProjectRepository;
import com.kw.nodeimageeditorbackend.repositories.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceRepository implements ProjectService {
    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    public ProjectServiceRepository(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ProjectDto> getUserProjects(Long userId) {
        UserEntity user = userRepository.getOne(userId);
        List<ProjectEntity> projectEntities = projectRepository.findAllByProjectOwner(user);
        List<ProjectDto> projects = new ArrayList<>();

        projectEntities.forEach(projectEntity -> projects.add(new ProjectDto(projectEntity)));
        return projects;
    }

}
