package com.haulmont.propsdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class CheckPropStateController {

    @Autowired
    private ExternalProperties externalProperties;

    @Autowired
    private GitHubProperties gitHubProperties;

    @GetMapping(value = {"/external-tz"}, produces = "text/plain")
    public String getExternalTimeZone() {
        return externalProperties.getTimezone();
    }

    @GetMapping(value = "/github-login", produces = "text/plain")
    public String getGitHubLogin() {
        return gitHubProperties.getLogin();
    }



}

