package com.help.cowin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class YAMLConfig {
    
    private String environment;

    private String validatelink;

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}
    // standard getters and setters

    public String getValidatelink() {
        return validatelink;
    }

    public void setValidatelink(String validatelink) {
        this.validatelink = validatelink;
    }
}