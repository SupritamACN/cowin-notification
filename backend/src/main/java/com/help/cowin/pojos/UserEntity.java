package com.help.cowin.pojos;

import java.util.ArrayList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Document(value = "users")
public class UserEntity {
    @Id
    private String _id;

    private long chatId;

    private String email;
    private ArrayList<PlaceEntity> district;
    private boolean enabled;

    private Integer notifyCount = 0;

    private int minAgeLimit;
    
    /*public String get_id() {
        return _id;
    }*/

    public void set_id(String _id) {
        this._id = _id;
    }

    public UserEntity() {
    }

    public UserEntity(String email, ArrayList<PlaceEntity> district, int minAgeLimit, boolean enabled) {
        this.email = email;
        this.district = district;
        this.enabled = enabled;
        this.minAgeLimit = minAgeLimit;
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

    public Integer getNotifyCount() {
        return notifyCount;
    }

    public void setNotifyCount(Integer notifyCount) {
        this.notifyCount = notifyCount;
    }

    public int getMinAgeLimit() {
        return minAgeLimit;
    }

    public void setMinAgeLimit(int minAgeLimit) {
        this.minAgeLimit = minAgeLimit;
    }
}
