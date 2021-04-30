package com.kw.nodeimageeditorbackend.services.project;

import com.kw.nodeimageeditorbackend.dto.ProjectDetailsDto;
import com.kw.nodeimageeditorbackend.dto.ProjectDto;

import java.util.List;

public interface ProjectService {


//    List<ProjectDetailsDto> getUserProjectsDetails2(Long userId);

    List<ProjectDetailsDto> getUserProjectsDetails(Long userId);

    ProjectDto getProject(Long projectId, boolean withProjectDetails);
}
