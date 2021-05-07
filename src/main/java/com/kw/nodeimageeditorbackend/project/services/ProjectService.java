package com.kw.nodeimageeditorbackend.project.services;

import com.kw.nodeimageeditorbackend.project.dto.ProjectDetailsDto;
import com.kw.nodeimageeditorbackend.project.dto.ProjectDto;
import com.kw.nodeimageeditorbackend.project.requests.CreateNewProjectRequest;
import com.kw.nodeimageeditorbackend.project.requests.SaveProjectRequest;

import java.util.List;

public interface ProjectService {


    //    List<ProjectDetailsDto> getUserProjectsDetails2(Long userId);
    List<ProjectDetailsDto> searchProjects(String searchPhrase);

    List<ProjectDetailsDto> getUserProjectsDetails(Long userId);

    ProjectDto getProject(Long projectId, Long userId, boolean withProjectDetails);

    void createProject(CreateNewProjectRequest request, Long ownerId);

    void saveProjectData(SaveProjectRequest request, Long ownerId);

    void updateProjectDetails(ProjectDto projectDto, Long ownerId);

    void deleteProject(Long projectId, Long ownerId);


}
