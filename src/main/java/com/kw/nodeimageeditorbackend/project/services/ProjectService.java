package com.kw.nodeimageeditorbackend.project.services;

import com.kw.nodeimageeditorbackend.project.dto.ProjectDto;
import com.kw.nodeimageeditorbackend.project.requests.CreateNewProjectRequest;
import com.kw.nodeimageeditorbackend.project.requests.SaveProjectRequest;

public interface ProjectService {


    //    List<ProjectDetailsDto> getUserProjectsDetails2(Long userId);

    ProjectDto getProject(Long projectId, Long userId, boolean withProjectDetails);

    void createProject(CreateNewProjectRequest request, Long ownerId);

    void saveProjectData(SaveProjectRequest request, Long ownerId);

    //TODO
    void updateProjectDetails(ProjectDto projectDto, Long ownerId);

    void deleteProject(Long projectId, Long ownerId);


}
