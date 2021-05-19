package com.help.cowin.util;

import com.help.cowin.config.CowinEndpoints;
import com.help.cowin.pojos.Centers;
import com.help.cowin.pojos.PlaceEntity;
import com.help.cowin.pojos.Sessions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ApiServiceImpl implements  ApiService{

    static final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    CowinEndpoints config;

    @Override
    public List<PlaceEntity> getStates() {

        List<PlaceEntity> statesList = new ArrayList<>();
        System.out.println(config.getCalenderByDistrict());
        ResponseEntity<Object> obj = restTemplate.exchange(
                config.getStates(), HttpMethod.GET,
                getHeaders(),
                Object.class
        );

        Map<String, Object> map = (Map<String, Object>) obj.getBody();
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("states");

        mapList.forEach(state -> {
            statesList.add(new PlaceEntity(
                    String.valueOf(state.get("state_id")),
                    String.valueOf(state.get("state_name")))
            );
        });
        return statesList;
    }

    @Override
    public List<PlaceEntity> getDistricts(String state_id) {
        List<PlaceEntity> placeEntities = new ArrayList<>();
        ResponseEntity<Object> obj = restTemplate.exchange(
                config.getDistrictsByState(),
                HttpMethod.GET,
                getHeaders(),
                Object.class, state_id
        );
        Map<String, Object> map = (Map<String, Object>) obj.getBody();
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("districts");
        mapList.forEach(district -> {
            placeEntities.add(new PlaceEntity(String.valueOf(district.get("district_id")), (String) district.get("district_name")));
        });
        return placeEntities;
    }

    @Override
    public List<Centers> getSessionsForDistricts(String district_id, String date) {
        List<Centers> centersEntityList = new ArrayList<>();
        var obj =restTemplate.exchange(
                config.getSessionByDistrictAndDate(),
                HttpMethod.GET,
                getHeaders(),
                Object.class, district_id, date
        );

        Map<String, Object> map = (Map<String, Object>) obj.getBody();
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("sessions");

        mapList.forEach(session -> {
            Centers entity = new Centers(
                    Integer.parseInt(String.valueOf(session.get("center_id"))),
                    String.valueOf(session.get("name")),
                    String.valueOf(session.get("address")),
                    String.valueOf(session.get("state_name")),
                    String.valueOf(session.get("district_name")),
                    String.valueOf(session.get("block_name")),
                    Integer.parseInt(String.valueOf(session.get("pincode"))),
                    Integer.parseInt(String.valueOf(session.get("lat"))),
                    Integer.parseInt("0"),
                    String.valueOf(session.get("from")),
                    String.valueOf(session.get("to")),
                    String.valueOf(session.get("fee_type")),
                    (ArrayList<Sessions>) (session.get("sessions"))
            );
            centersEntityList.add(entity);
        });

        return centersEntityList;
    }

    private HttpEntity<String> getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        return entity;
    }
}
