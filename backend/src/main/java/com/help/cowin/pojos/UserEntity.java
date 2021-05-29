package com.help.cowin.pojos;

import java.util.ArrayList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;

@Getter @Setter @AllArgsConstructor @ToString
@Document(value = "users")
public class UserEntity {
    @Id
    private String _id;

    private Long chatId = 0L;

    private String email;
    private ArrayList<PlaceEntity> district;
    private boolean enabled;

    private Integer notifyCount = 0;

    private int minAgeLimit;

    private int dose;
    
    public UserEntity() {
    }

    public UserEntity(String id, String email, ArrayList<PlaceEntity> district, int minAgeLimit, boolean enabled, int dose) {
        this.email = email;
        this.district = district;
        this.enabled = enabled;
        this.minAgeLimit = minAgeLimit;
        this._id = id;
        this.dose = dose;
    }

    public UserEntity(String id, String email, ArrayList<PlaceEntity> district, int minAgeLimit, boolean enabled, Integer notifyCount, int dose) {
        this.email = email;
        this.district = district;
        this.enabled = enabled;
        this.minAgeLimit = minAgeLimit;
        this._id = id;
        this.notifyCount = notifyCount;
        this.dose = dose;
    }

    public String getDistrictNameAString(){
        StringBuilder returnString = new StringBuilder();
        this.district.forEach(a -> {
            returnString.append(a.getPlaceName() + ", ");
        });
        return returnString.toString();
    }
}