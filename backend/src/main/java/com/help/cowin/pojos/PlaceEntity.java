package com.help.cowin.pojos;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PlaceEntity {
    private String placeId;
    private String placeName;

    public PlaceEntity() {
    }

    public PlaceEntity(String placeId, String placeName) {
        this.placeId = placeId;
        this.placeName = placeName;
    }

    public String getplaceId() {
        return placeId;
    }

    public void setplaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
}
