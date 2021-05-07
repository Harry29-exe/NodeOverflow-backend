package com.kw.nodeimageeditorbackend.project.services;

import com.kw.nodeimageeditorbackend.project.dto.ProjectDetailsDto;
import com.kw.nodeimageeditorbackend.project.dto.ProjectDto;
import com.kw.nodeimageeditorbackend.project.requests.CreateNewProjectRequest;
import com.kw.nodeimageeditorbackend.user.dto.UserDto;

import java.util.List;

public interface ProjectService {


    //    List<ProjectDetailsDto> getUserProjectsDetails2(Long userId);
    List<ProjectDetailsDto> searchProjects(String searchPhrase);

    List<ProjectDetailsDto> getUserProjectsDetails(Long userId);

    ProjectDto getProject(Long projectId, boolean withProjectDetails);

    void saveProject(ProjectDto projectDto);

    void createProject(CreateNewProjectRequest request, Long ownerId);

    void updateProject(ProjectDto projectDto);

    void deleteProject(Long projectId);


}
