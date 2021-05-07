package com.kw.nodeimageeditorbackend.project.services;

import com.kw.nodeimageeditorbackend.project.dto.ProjectDetailsDto;
import com.kw.nodeimageeditorbackend.project.requests.GetFilteredProjectDetailsRequest;

import java.util.List;

public interface ProjectDetailsService {

    List<ProjectDetailsDto> searchProjects(GetFilteredProjectDetailsRequest searchPhrase, Long userId, Boolean findCollaborationProjects);

    List<ProjectDetailsDto> getUserProjectsDetails(Long userId, Boolean findCollaborationProjects);

}
