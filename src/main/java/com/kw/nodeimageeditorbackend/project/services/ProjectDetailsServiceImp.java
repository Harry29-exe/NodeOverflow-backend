package com.kw.nodeimageeditorbackend.project.services;

import com.kw.nodeimageeditorbackend.project.dto.ProjectDetailsDto;
import com.kw.nodeimageeditorbackend.project.dto.ProjectDetailsList;
import com.kw.nodeimageeditorbackend.project.entities.ProjectCollaboratorEntity;
import com.kw.nodeimageeditorbackend.project.entities.ProjectEntity;
import com.kw.nodeimageeditorbackend.project.repositories.ProjectRepository;
import com.kw.nodeimageeditorbackend.project.requests.GetFilteredProjectDetailsRequest;
import com.kw.nodeimageeditorbackend.user.entities.UserEntity;
import com.kw.nodeimageeditorbackend.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProjectDetailsServiceImp implements ProjectDetailsService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectDetailsServiceImp(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProjectDetailsList getUserProjectsDetails(
            Long userId,
            Integer pageIndex,
            Integer resultPerPage,
            Boolean findCollaborationProjects
    ) {
        List<ProjectEntity> usersProjects;
        Integer allResults;
        Pageable pageable = PageRequest.of(pageIndex, resultPerPage);

        if (findCollaborationProjects) {
            var userEntity = new UserEntity();
            userEntity.setId(userId);
            var collaborator = new ProjectCollaboratorEntity();
            collaborator.setCollaborator(userEntity);
            usersProjects = projectRepository.findByProjectOwnerOrCollaboratorsContaining(userEntity, collaborator, pageable);
            allResults = projectRepository.countAllByProjectOwnerIdOrCollaboratorsContaining(userId, collaborator);
        } else {
            usersProjects = projectRepository.findAllByProjectOwnerId(userId, pageable);
            allResults = projectRepository.countAllByProjectOwnerId(userId);
        }

        return new ProjectDetailsList(usersProjects.stream()
                .map(ProjectDetailsDto::new)
                .collect(Collectors.toList()), allResults, pageIndex * resultPerPage);
    }

    @Override
    public ProjectDetailsList searchProjects(
            GetFilteredProjectDetailsRequest request,
            Long userId, Integer pageIndex, Integer resultPerPage,
            Boolean findCollaborationProjects
    ) {

        var userAsOwner = new UserEntity();
        userAsOwner.setId(userId);
        var userAsCollaborator = new ProjectCollaboratorEntity();
        userAsCollaborator.setCollaborator(userAsOwner);

        List<ProjectEntity> userProjectEntities = projectRepository
                .findAllByProjectOwnerIdAndCreationDateBetweenAndLastModifiedBetween(
                        userId,
                        request.getCreatedAfter(), request.getModifiedBefore(),
                        request.getModifiedAfter(), request.getModifiedBefore());

        if(findCollaborationProjects) {
            userProjectEntities.addAll(projectRepository
                    .findAllByCollaboratorsContainingAndCreationDateBetweenAndLastModifiedBetween(
                            userAsCollaborator,
                            request.getCreatedAfter(), request.getModifiedBefore(),
                            request.getModifiedAfter(), request.getModifiedBefore()
                    ));
        }

        var projects = userProjectEntities
                .stream().map(ProjectDetailsDto::new)
                .collect(Collectors.toList());
        Integer allResultsCount = projects.size();

       projects = this.filterProjects(projects, request.getSearchPhrase());
       int resultsFrom = pageIndex * resultPerPage;
       if( resultsFrom + resultPerPage <= projects.size()) {
           return new ProjectDetailsList(
                   projects.subList(resultsFrom, resultsFrom + resultPerPage),
                   allResultsCount, resultsFrom);
       } else if (resultsFrom < projects.size()) {
           return new ProjectDetailsList(
                   projects.subList(resultsFrom, projects.size()),
                   allResultsCount, resultsFrom);
       }

       return new ProjectDetailsList(new LinkedList<>(), allResultsCount, resultsFrom);
    }

    private List<ProjectDetailsDto> filterProjects(
            List<ProjectDetailsDto> projectsDetails2,
            String searchPhrase
    ) {

        List<String> searchingPhrases = List.of(searchPhrase.split("\\s"));
        var projectsDetails =
                projectsDetails2.stream().map(details ->
                        new DetailsWithAccuracy(details, 0d))
                        .collect(Collectors.toList());

        for (var details : projectsDetails) {
            for (var phrase : searchingPhrases) {
                var metadata = details.projectDetails.getMetadata();
                details.accuracy += metadata.getTitle().contains(phrase) ? 3 : 0;
                metadata.getTags().forEach(tag -> details.accuracy += tag.contains(phrase) ? 1 : 0);
            }
        }

        projectsDetails = projectsDetails.stream()
                .filter(f -> f.accuracy > 0)
                .collect(Collectors.toList());
        projectsDetails.sort(Comparator.comparingDouble(d -> -d.accuracy));

        return projectsDetails.stream()
                .map(details -> details.projectDetails)
                .collect(Collectors.toList());
    }

    @AllArgsConstructor
    private static class DetailsWithAccuracy {
        private final ProjectDetailsDto projectDetails;
        private Double accuracy;
    }

}
