package com.kw.nodeimageeditorbackend.project.services;

import com.kw.nodeimageeditorbackend.project.dto.ProjectDto;
import com.kw.nodeimageeditorbackend.project.requests.CreateNewProjectRequest;
import com.kw.nodeimageeditorbackend.project.requests.SaveProjectRequest;
import com.kw.nodeimageeditorbackend.security.user.AppAuthentication;
import org.springframework.security.core.Authentication;

public interface ProjectService {


    //    List<ProjectDetailsDto> getUserProjectsDetails2(Long userId);

    ProjectDto getProject(Long projectId, AppAuthentication authentication, boolean withProjectDetails);

    void createProject(CreateNewProjectRequest request, AppAuthentication authentication);

    void saveProjectData(SaveProjectRequest request, AppAuthentication authentication);

    //TODO
    void updateProjectDetails(ProjectDto projectDto, AppAuthentication authentication);

    void deleteProject(Long projectId, AppAuthentication authentication);


}
