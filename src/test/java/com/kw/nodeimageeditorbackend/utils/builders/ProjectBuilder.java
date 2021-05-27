package com.kw.nodeimageeditorbackend.utils.builders;

import com.kw.nodeimageeditorbackend.project.entities.*;
import com.kw.nodeimageeditorbackend.user.entities.UserEntity;

import java.util.ArrayList;
import java.util.Date;

public class ProjectBuilder {
    private static long projectId = 1L;
    private static long collaboratorId = 1L;
    private static long tagId = 1L;
    private ProjectEntity projectEntity = new ProjectEntity();

    public ProjectBuilder(UserEntity owner, String title) {
        projectEntity.setId(projectId++);
        this.setData("");
        projectEntity.setCollaborators(new ArrayList<>());
        projectEntity.setTags(new ArrayList<>());
        projectEntity.setProjectOwner(owner);
        projectEntity.setTitle(title);
        projectEntity.setLastModified(new Date());
        projectEntity.setCreationDate(new Date());
    }

    public static ProjectBuilder builder(UserEntity owner, String title) {
        return new ProjectBuilder(owner, title);
    }

    public ProjectEntity build() {
        var project = projectEntity;
        this.projectEntity = null;
        return project;
    }

    public ProjectBuilder addCollaborator(UserEntity user, Boolean canWrite, Boolean canFork) {
        var collaborator = new ProjectCollaboratorEntity(collaboratorId++, user, projectEntity, canWrite, canFork);
        projectEntity.getCollaborators().add(collaborator);
        return this;
    }

    public ProjectBuilder addCollaborator(UserEntity user) {
        return this.addCollaborator(user, false, false);
    }

    public ProjectBuilder addCollaborators(UserEntity... users) {
        for (UserEntity user : users) {
            this.addCollaborator(user, false, false);
        }
        return this;
    }

    public ProjectBuilder addTag(String tag) {
        var newTag = new ProjectTagEntity(tagId++, projectEntity, tag);
        projectEntity.getTags().add(newTag);
        return this;
    }

    public ProjectBuilder addTags(String... tags) {
        for (String tag : tags) {
            this.addTag(tag);
        }

        return this;
    }

    public ProjectBuilder setCreationDate(Date date) {
        projectEntity.setCreationDate(date);
        return this;
    }

    public ProjectBuilder setLastModifiedDate(Date date) {
        projectEntity.setLastModified(date);
        return this;
    }

    public ProjectBuilder setAccessModifier(AccessModifier accessModifier) {
        projectEntity.setAccessModifier(accessModifier);
        return this;
    }

    public ProjectBuilder setData(String data) {
        var projectData = new ProjectDataEntity(projectEntity.getId(), data, projectEntity);
        projectEntity.setProjectData(projectData);
        return this;
    }
}
