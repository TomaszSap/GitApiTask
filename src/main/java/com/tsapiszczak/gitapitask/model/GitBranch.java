package com.tsapiszczak.gitapitask.model;


import lombok.Data;

@Data
public class GitBranch {
    private  String name;
    private String lastCommitSha;
}
