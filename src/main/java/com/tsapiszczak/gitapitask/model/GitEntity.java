package com.tsapiszczak.gitapitask.model;


import lombok.Data;

import java.util.List;

@Data
public class GitEntity {
    private String repositoryName;
    private String ownersLogin;
    private List<GitBranch> gitBranches;
}
