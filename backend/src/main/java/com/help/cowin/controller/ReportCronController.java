package com.help.cowin.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.help.cowin.config.CowinEndpoints;
import com.help.cowin.config.MailConfig;
import com.help.cowin.pojos.Centers;
import com.help.cowin.pojos.PlaceEntity;
import com.help.cowin.pojos.Root;
import com.help.cowin.pojos.Sessions;
import com.help.cowin.pojos.UserEntity;
import com.help.cowin.repo.CowinDbUserRepo;
import com.help.cowin.repo.UserEntityUVRepo;
import com.help.cowin.util.EmailService;
import com.help.cowin.util.TelegramService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/v1/public/batch")
@Slf4j
public class ReportCronController {

    @Autowired
    EmailService emailService;

    @Autowired
    CowinEndpoints config;

    @Autowired
    MailConfig mailConfig;

    @Autowired
    TelegramService telegramService;

    @Autowired
    private CowinDbUserRepo cowinDbUserRepo;

    @Autowired
    private UserEntityUVRepo unverifiedUserRepo;

    @Autowired
    private RestTemplate restTemplate;
    
    //@Scheduled(cron = "0 0 0 * * *")
    @GetMapping("/housekeep-temp-table")
    public void housekeepingUnVerfiedUsers(){
        unverifiedUserRepo.deleteAll();
    }


    //@Scheduled(cron = "0 0 0 * * *")
    @GetMapping("/reset-count-for-users")
    public void resetCountForUsers(){
        List<UserEntity> userEntityList = cowinDbUserRepo.findAll();
        userEntityList.forEach(user ->{
            user.setNotifyCount(0);
            cowinDbUserRepo.save(user);
        });
    }

    @Async
    public void sendMail(UserEntity userEntity) {

        //String userDistrictId = userEntity.getDistrict().getPlace_id();
        log.info(userEntity.toString());
        Boolean mailFlag = false;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept-Language", "en_IN");
        headers.set("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36 Edg/90.0.818.51");

        HttpEntity entity = new HttpEntity(headers);

        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String formattedDate = myDateObj.format(myFormatObj);
        StringBuffer message = new StringBuffer();

        for(PlaceEntity district : userEntity.getDistrict()){
            Boolean districtFlag = false;
            String url = config.getCalenderByDistrict();
            url += "?district_id=" + district.getplaceId() + "&date=" + formattedDate;

            ResponseEntity<Root> root = restTemplate.exchange(
                    url,
                    HttpMethod.GET, entity,
                    Root.class);
            Root response = root.getBody();

            log.info(response.toString());
            
            message.append("Slots for District:" + district.getPlaceName() + "\n\n");
            if(response.getCenters() != null) {
                for (Centers center : response.getCenters()) {
                    if(center.getSessions()!= null) {
                        for (Sessions session : center.getSessions()) {
                            if(userEntity.getMinAgeLimit()!=0){
                                if(session.getMin_age_limit() == userEntity.getMinAgeLimit() && session.getAvailable_capacity() > 0) {
                                    mailFlag= true;
                                    districtFlag = true;
                                    message.append(session.getAvailable_capacity()+ " slot(s) available for "+session.getMin_age_limit()+"+ on "+session.getDate()
                                            + " at "+ center.getName()+"\nCenter Address:"+ center.getAddress()+ " " + center.getBlock_name() + " "+ center.getDistrict_name() 
                                            + " PinCode:" +center.getPincode()+ "\nVaccine Name: "+ session.getVaccine() + "\nAvailable Dose 1: " + session.getAvailable_capacity_dose1() 
                                            + "\nAvailable Dose 2: " + session.getAvailable_capacity_dose2() + "\n");
                                }
                            }else{
                                if (session.getAvailable_capacity() > 0){   
                                    mailFlag= true;
                                    districtFlag = true;
                                    message.append(session.getAvailable_capacity()+ " slot(s) available for "+session.getMin_age_limit()+"+ on "+session.getDate()
                                            + " at "+ center.getName()+"\nCenter Address:"+ center.getAddress()+ " " + center.getBlock_name() + " "+ center.getDistrict_name() 
                                            + " PinCode:" +center.getPincode()+ "\nVaccine Name: "+ session.getVaccine() + "\nAvailable Dose 1: " + session.getAvailable_capacity_dose1() 
                                            + "\nAvailable Dose 2: " + session.getAvailable_capacity_dose2() + "\n");
                                }
                            }
                        }
                    }
                }
            }

            if(!districtFlag){
                message.append("No slots available!!\n");
            }

        }

        if(mailFlag && userEntity.isEnabled()){
            emailService.sendEmail(userEntity.getEmail(), "Vaccination Slot Availability", message.toString());
            userEntity.setNotifyCount(userEntity.getNotifyCount() + 1);
            cowinDbUserRepo.save(userEntity);
        }

        if(userEntity.getChatId() != 0){
            telegramService.sendChat(Long.toString(userEntity.getChatId()), message.toString());
        }
    }

    @GetMapping("/generateReport")
    public void generateReport(){
         List<UserEntity> userEntityList = cowinDbUserRepo.findAll();

         userEntityList.forEach(user ->{
             if(user.getNotifyCount() < 6) {
                sendMail(user);
             }
         });

    }
    

}
