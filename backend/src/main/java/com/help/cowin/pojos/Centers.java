package com.help.cowin.pojos;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Centers
{
    private int center_id;

    private String name;

    private String address;

    private String state_name;

    private String district_name;

    private String block_name;

    private int pincode;

    private int lat;
    
    @JsonProperty("long")
    private int longitude;

    private String from;

    private String to;

    private String fee_type;

    private List<Sessions> sessions;

    public Centers() {
    }

    public Centers(int center_id, String name, String address, String state_name, String district_name, String block_name, int pincode, int lat, int longitude, String from, String to, String fee_type, List<Sessions> sessions) {
        this.center_id = center_id;
        this.name = name;
        this.address = address;
        this.state_name = state_name;
        this.district_name = district_name;
        this.block_name = block_name;
        this.pincode = pincode;
        this.lat = lat;
        this.longitude = longitude;
        this.from = from;
        this.to = to;
        this.fee_type = fee_type;
        this.sessions = sessions;
    }

    public void setCenter_id(int center_id){
        this.center_id = center_id;
    }
    public int getCenter_id(){
        return this.center_id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getAddress(){
        return this.address;
    }
    public void setState_name(String state_name){
        this.state_name = state_name;
    }
    public String getState_name(){
        return this.state_name;
    }
    public void setDistrict_name(String district_name){
        this.district_name = district_name;
    }
    public String getDistrict_name(){
        return this.district_name;
    }
    public void setBlock_name(String block_name){
        this.block_name = block_name;
    }
    public String getBlock_name(){
        return this.block_name;
    }
    public void setPincode(int pincode){
        this.pincode = pincode;
    }
    public int getPincode(){
        return this.pincode;
    }
    public void setLat(int lat){
        this.lat = lat;
    }
    public int getLat(){
        return this.lat;
    }
   
    public int getLongitude() {
		return longitude;
	}
	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	public void setFrom(String from){
        this.from = from;
    }
    public String getFrom(){
        return this.from;
    }
    public void setTo(String to){
        this.to = to;
    }
    public String getTo(){
        return this.to;
    }
    public void setFee_type(String fee_type){
        this.fee_type = fee_type;
    }
    public String getFee_type(){
        return this.fee_type;
    }
    public void setSessions(List<Sessions> sessions){
        this.sessions = sessions;
    }
    public List<Sessions> getSessions(){
        return this.sessions;
    }
}