package com.kw.nodeimageeditorbackend.services.project;

import com.kw.nodeimageeditorbackend.dto.ProjectDto;

import java.util.List;

public interface ProjectService {

    List<ProjectDto> getUserProjects(Long userId);

}
