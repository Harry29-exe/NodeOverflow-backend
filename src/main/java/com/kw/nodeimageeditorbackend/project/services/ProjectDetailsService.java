package com.kw.nodeimageeditorbackend.project.services;

import com.kw.nodeimageeditorbackend.project.dto.ProjectDetailsList;
import com.kw.nodeimageeditorbackend.project.requests.GetFilteredProjectDetailsRequest;

public interface ProjectDetailsService {

    ProjectDetailsList searchProjects(GetFilteredProjectDetailsRequest searchPhrase, Long userId, Integer pageIndex, Integer resultPerPage, Boolean findCollaborationProjects);

    ProjectDetailsList getUserProjectsDetails(Long userId, Integer pageIndex, Integer resultPerPage, Boolean findCollaborationProjects);

}
