package com.kw.nodeimageeditorbackend.project.services;

import com.kw.nodeimageeditorbackend.project.dto.ProjectDetailsDto;
import com.kw.nodeimageeditorbackend.project.entities.ProjectEntity;
import com.kw.nodeimageeditorbackend.project.repositories.ProjectRepository;
import com.kw.nodeimageeditorbackend.project.requests.GetFilteredProjectDetailsRequest;
import com.kw.nodeimageeditorbackend.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProjectDetailsServiceRepository implements ProjectDetailsService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectDetailsServiceRepository(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ProjectDetailsDto> getUserProjectsDetails(Long userId, Boolean findCollaborationProjects) {
        var usersProjects = projectRepository.findAllByProjectOwnerId(userId);
        if (findCollaborationProjects) {
            var projects = projectRepository.findAllByCollaboratorId(userId);
            usersProjects.addAll(projects);
        }

        return usersProjects.stream()
                .map(ProjectDetailsDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDetailsDto> searchProjects(
            GetFilteredProjectDetailsRequest request,
            Long userId,
            Boolean findCollaborationProjects) {
        var usersProjectsEntity = projectRepository
                .findAllByProjectOwnerIdAndCreationDateBetweenAndLastModifiedBetween(
                        userId,
                        request.getCreateBefore(), request.getCreatedAfter(),
                        request.getModifiedBefore(), request.getModifiedAfter());
        if (findCollaborationProjects) {
            var projectsEntity = projectRepository.findAllByCollaboratorId(userId);
            usersProjectsEntity.addAll(projectsEntity);
        }

        var projects = usersProjectsEntity
                .stream().map(ProjectDetailsDto::new)
                .collect(Collectors.toList());
        return this.filterProjects(projects, request.getSearchPhrase());
    }

    private List<ProjectDetailsDto> filterProjects(
            List<ProjectDetailsDto> projectsDetails2,
            String searchPhrase) {

        List<String> searchingPhrases = List.of(searchPhrase.split("\\s"));
        var projectsDetails =
                projectsDetails2.stream().map(details ->
                        new DetailsWithAccuracy(details, 0d))
                        .collect(Collectors.toList());

        for (var details : projectsDetails) {
            for (var phrase : searchingPhrases) {
                var metadata = details.projectDetails.getMetadata();
                details.accuracy += metadata.getTitle().contains(phrase)? 3: 0;
                metadata.getTags().forEach(tag -> details.accuracy += tag.contains(phrase)? 1: 0);
            }
        }

        projectsDetails.sort(Comparator.comparingDouble(d -> d.accuracy));
        return projectsDetails.stream()
                .map(details -> details.projectDetails)
                .collect(Collectors.toList());
    }

    @AllArgsConstructor
    private static class DetailsWithAccuracy {
        private ProjectDetailsDto projectDetails;
        private Double accuracy;
    }

}
