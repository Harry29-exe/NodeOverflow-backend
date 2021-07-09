package com.kw.nodeimageeditorbackend.project.services;

import com.kw.nodeimageeditorbackend.project.dto.ProjectDetailsList;
import com.kw.nodeimageeditorbackend.project.requests.GetFilteredProjectDetailsRequest;
import com.kw.nodeimageeditorbackend.security.user.AppAuthentication;

public interface ProjectDetailsService {

    ProjectDetailsList searchProjects(AppAuthentication authentication, GetFilteredProjectDetailsRequest searchPhrase, Integer pageIndex, Integer resultPerPage, Boolean findCollaborationProjects);

    ProjectDetailsList getUserProjectsDetails(AppAuthentication authentication, Integer pageIndex, Integer resultPerPage, Boolean findCollaborationProjects);

}
