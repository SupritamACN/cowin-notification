package com.help.cowin.pojos;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
@Document(value = "unverified_user")
public class UserEntityUV {

    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String _id;

    private String email;
    private ArrayList<PlaceEntity> district;
    private boolean enabled;

    private Long chatId;
    private int minAgeLimit;

    private int dose;

    public UserEntityUV() {
    }

    public UserEntityUV(String email, ArrayList<PlaceEntity> district) {
        this.email = email;
        this.district = district;
    }

}
