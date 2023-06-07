package com.tsapiszczak.gitapitask.controller;

import com.tsapiszczak.gitapitask.exception.APIException;
import com.tsapiszczak.gitapitask.service.GitService;
import com.tsapiszczak.gitapitask.model.GitEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class GitController {

    GitService gitService;

    @Autowired
    public GitController(GitService gitService) {
        this.gitService = gitService;
    }

    @GetMapping(value = "/getRepoByUser")
    public ResponseEntity<List<GitEntity>> getRepoByUser(@RequestHeader(value = "Accept") String acceptHeader, @RequestParam String name) {
        if (!(acceptHeader.equals("application/json"))) {
            throw new APIException(406, "Invalid Accept value in header");
        }
        else
            return ResponseEntity.ok().body(gitService.getRepositoriesWithFork(name));
    }
}
