package com.help.cowin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "mail")
@Getter @Setter
public class MailConfig {
    
    String smtphost;

    String smtpauth;

    String smtpport;

    String username;

    String mailid;

    String password;
 
    
}
