package com.haulmont.propsdemo.controller;

import com.haulmont.propsdemo.PropDependencyBean;
import com.haulmont.propsdemo.props.DateProperties;
import com.haulmont.propsdemo.props.GitHubProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class CheckPropStateController {

    @Autowired
    private DateProperties dateProperties;

    @Autowired
    private GitHubProperties gitHubProperties;

    @Autowired
    private PropDependencyBean propDependencyBeanObjectFactory;

    @GetMapping(value = {"/date-tz"}, produces = "text/plain")
    public String getExternalTimeZone() {
        return dateProperties.getTimezone();
    }

    @GetMapping(value = "/github-login", produces = "text/plain")
    public String getGitHubLogin() {
        return propDependencyBeanObjectFactory.getLogin();
    }



}

