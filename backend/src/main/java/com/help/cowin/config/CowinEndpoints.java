package com.help.cowin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cowin.endpoints", ignoreUnknownFields = true)
public class CowinEndpoints {
	
	String calenderByDistrict;
	String states;
	String districtsByState;
	String sessionByDistrictAndDate;

	public String getCalenderByDistrict() {
		return calenderByDistrict;
	}
	public void setCalenderByDistrict(String calenderByDistrict) {
		this.calenderByDistrict = calenderByDistrict;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public String getDistrictsByState() {
		return districtsByState;
	}

	public void setDistrictsByState(String districtsByState) {
		this.districtsByState = districtsByState;
	}

	public String getSessionByDistrictAndDate() {
		return sessionByDistrictAndDate;
	}

	public void setSessionByDistrictAndDate(String sessionByDistrictAndDate) {
		this.sessionByDistrictAndDate = sessionByDistrictAndDate;
	}
}
