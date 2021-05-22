package com.help.cowin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cowin.endpoints", ignoreUnknownFields = true)
@Getter @Setter
public class CowinEndpoints {
	
	String calenderByDistrict;
	String states;
	String districtsByState;
	String sessionByDistrictAndDate;


}
