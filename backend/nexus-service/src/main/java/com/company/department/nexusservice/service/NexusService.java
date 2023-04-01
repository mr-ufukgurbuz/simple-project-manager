package com.company.department.nexusservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public abstract class NexusService
{
    @Value("${nexus.url}")
    protected String nexusUrl;

    @Value("${nexus.repository}")
    protected String repository;

    @Value("${nexus.assetsPath}")
    protected String assetsPath;

    @Value("${nexus.componentsPath}")
    protected String componentsPath;

    protected final RestTemplate restTemplate;

    public NexusService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
