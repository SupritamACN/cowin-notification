package com.help.cowin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Setter @Getter
public class YAMLConfig {
    
    private String environment;

    private String validatelink;

	private String appLink;
}