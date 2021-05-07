package com.kw.nodeimageeditorbackend.project.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@AllArgsConstructor
public class GetFilteredProjectDetailsRequest {
    @NotEmpty
    private String searchPhrase;
    private Date createBefore;
    private Date createdAfter;
    private Date modifiedBefore;
    private Date modifiedAfter;
}
