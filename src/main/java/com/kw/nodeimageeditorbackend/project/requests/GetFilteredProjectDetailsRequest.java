package com.kw.nodeimageeditorbackend.project.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class GetFilteredProjectDetailsRequest {
    @NotNull
    private String searchPhrase;
    private Date createBefore;
    private Date createdAfter;
    private Date modifiedBefore;
    private Date modifiedAfter;

    public GetFilteredProjectDetailsRequest(String searchPhrase, Date createBefore, Date createdAfter, Date modifiedBefore, Date modifiedAfter) {
        this.searchPhrase = searchPhrase;
        this.createBefore = createBefore != null ? createBefore : new Date(2208988800000L);
        this.createdAfter = createdAfter != null ? createdAfter : new Date(0);
        this.modifiedBefore = modifiedBefore != null ? modifiedBefore : new Date(2208988800000L);
        this.modifiedAfter = modifiedAfter != null ? modifiedAfter : new Date(0);
    }
}
