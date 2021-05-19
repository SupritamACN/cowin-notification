package com.help.cowin.pojos;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;



@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Sessions
{
    private String session_id;

    private String date;

    private int available_capacity;

    private int min_age_limit;

    private String vaccine;

    private List<String> slots;

    private int available_capacity_dose1;

    private int available_capacity_dose2;

    public int getAvailable_capacity_dose1() {
        return available_capacity_dose1;
    }
    public void setAvailable_capacity_dose1(int available_capacity_dose1) {
        this.available_capacity_dose1 = available_capacity_dose1;
    }
    public int getAvailable_capacity_dose2() {
        return available_capacity_dose2;
    }
    public void setAvailable_capacity_dose2(int available_capacity_dose2) {
        this.available_capacity_dose2 = available_capacity_dose2;
    }
    public void setSession_id(String session_id){
        this.session_id = session_id;
    }
    public String getSession_id(){
        return this.session_id;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getDate(){
        return this.date;
    }
    public void setAvailable_capacity(int available_capacity){
        this.available_capacity = available_capacity;
    }
    public int getAvailable_capacity(){
        return this.available_capacity;
    }
    public void setMin_age_limit(int min_age_limit){
        this.min_age_limit = min_age_limit;
    }
    public int getMin_age_limit(){
        return this.min_age_limit;
    }
    public void setVaccine(String vaccine){
        this.vaccine = vaccine;
    }
    public String getVaccine(){
        return this.vaccine;
    }
    public void setSlots(List<String> slots){
        this.slots = slots;
    }
    public List<String> getSlots(){
        return this.slots;
    }
}