package com.tsapiszczak.gitapitask.service;

import com.tsapiszczak.gitapitask.exception.APIException;
import com.tsapiszczak.gitapitask.githubapi.GitApi;
import com.tsapiszczak.gitapitask.model.GitBranch;
import com.tsapiszczak.gitapitask.model.GitEntity;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class GitService {

    GitApi gitApi;

    public GitService(GitApi gitApi) {
        this.gitApi = gitApi;
    }

    public JSONArray getRepositories(String name) throws APIException {
        return gitApi.findReposByUser(name);

    }

    @SneakyThrows
    public List<GitEntity> getRepositoriesWithFork(String name){
        JSONArray reposByUser = getRepositories(name);
        List<GitEntity> reposList = new ArrayList<>();
        for (int i = 0; i < reposByUser.length(); i++) {
            JSONObject repository = reposByUser.getJSONObject(i);
            GitEntity gitEntity = new GitEntity();
            boolean isFork = repository.getBoolean("fork");
            if (!isFork) {
                String repositoryName = repository.getString("name");
                gitEntity.setRepositoryName(repositoryName);
                gitEntity.setOwnersLogin(repository.getJSONObject("owner").getString("login"));
                JSONArray branchesArray = gitApi.findRepoBranchDetails(name, repositoryName);
                List<GitBranch> branchList = IntStream.range(0, branchesArray.length())
                        .mapToObj(branchesArray::optJSONObject)
                        .filter(Objects::nonNull)
                        .map(branchDetails -> {
                            GitBranch gitBranch = new GitBranch();
                            gitBranch.setName(branchDetails.optString("name"));
                            gitBranch.setLastCommitSha(branchDetails.optJSONObject("commit").optString("sha"));
                            return gitBranch;
                        })
                        .collect(Collectors.toList());
                gitEntity.setGitBranches(branchList);
            }
            reposList.add(gitEntity);
        }
        return reposList;
    }
}
