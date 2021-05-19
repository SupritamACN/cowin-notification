package com.help.cowin.pojos;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Root implements java.io.Serializable
{
    private List<Centers> centers;

    public void setCenters(List<Centers> centers){
        this.centers = centers;
    }
    public List<Centers> getCenters(){
        return this.centers;
    }
}