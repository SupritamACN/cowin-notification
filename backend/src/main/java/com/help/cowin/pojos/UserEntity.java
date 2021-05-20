package com.help.cowin.pojos;

import java.util.ArrayList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter @Setter @AllArgsConstructor
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
    
    public UserEntity() {
    }

    public UserEntity(String id, String email, ArrayList<PlaceEntity> district, int minAgeLimit, boolean enabled) {
        this.email = email;
        this.district = district;
        this.enabled = enabled;
        this.minAgeLimit = minAgeLimit;
        this._id = id;
    }
}