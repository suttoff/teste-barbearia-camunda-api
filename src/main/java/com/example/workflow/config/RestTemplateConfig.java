package com.example.workflow.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Objects;

@Configuration
public class RestTemplateConfig {

    private static final Logger LOG = LoggerFactory.getLogger(RestTemplateConfig.class);

    @Value("${rest.request.timeout.connect}")
    private int connectTimeout;

    @Value("${rest.request.timeout.read}")
    private int readTimeout;

    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder
                .setConnectTimeout(Duration.ofMillis(connectTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();

        if (LOG.isDebugEnabled()) {
            if (Objects.isNull(restTemplate.getInterceptors())) {
                restTemplate.setInterceptors(new ArrayList<>());
            }

            restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        }

        return restTemplate;
    }
}
