package com.help.cowin.pojos;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "unverified_user")
public class UserEntityUV {

    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String _id;

    private String email;
    private ArrayList<PlaceEntity> district;
    private boolean enabled;

    private int minAgeLimit;

    public UserEntityUV() {
    }

    public UserEntityUV(String email, ArrayList<PlaceEntity> district) {
        this.email = email;
        this.district = district;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<PlaceEntity> getDistrict() {
        return district;
    }

    public void setDistrict(ArrayList<PlaceEntity> district) {
        this.district = district;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getMinAgeLimit() {
        return minAgeLimit;
    }

    public void setMinAgeLimit(int minAgeLimit) {
        this.minAgeLimit = minAgeLimit;
    }
}
