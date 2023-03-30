package com.company.department.nexusservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig
{
    @Value("${nexus.username}")
    protected String nexusUsername;

    @Value("${nexus.password}")
    protected String nexusPassword;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder)
    {
        RestTemplate restTemplate = builder.setConnectTimeout(Duration.ofMinutes(1)).setReadTimeout(Duration.ofMinutes(1)).build();

        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(nexusUsername, nexusPassword));

        return restTemplate;
    }
}
