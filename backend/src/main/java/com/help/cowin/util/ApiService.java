package com.help.cowin.util;

import java.util.List;

import com.help.cowin.pojos.Centers;
import com.help.cowin.pojos.PlaceEntity;


public interface ApiService {
    public List<PlaceEntity> getStates();
    public List<PlaceEntity> getDistricts(String state_id);
    public List<Centers> getSessionsForDistricts(String district_id, String date);
}
