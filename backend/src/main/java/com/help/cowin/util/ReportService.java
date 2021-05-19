package com.help.cowin.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.help.cowin.config.CowinEndpoints;
import com.help.cowin.config.MailConfig;
import com.help.cowin.pojos.Centers;
import com.help.cowin.pojos.Root;
import com.help.cowin.pojos.Sessions;
import com.help.cowin.pojos.UserEntity;
import com.help.cowin.repo.CowinDbUserRepo;
import com.help.cowin.repo.UserEntityUVRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReportService {

    @Autowired
    EmailService emailService;

    @Autowired
    CowinEndpoints config;

    @Autowired
    MailConfig mailConfig;

    @Autowired
    private CowinDbUserRepo cowinDbUserRepo;

    @Autowired
    private UserEntityUVRepo unverifiedUserRepo;

    static final RestTemplate restTemplate = new RestTemplateBuilder().build();

    
    @Scheduled(cron = "0 0 0 * * *")
    void housekeepingUnVerfiedUsers(){
        unverifiedUserRepo.deleteAll();
    }


    @Scheduled(cron = "0 0 0 * * *")
    void resetCountForUsers(){
        List<UserEntity> userEntityList = cowinDbUserRepo.findAll();
        userEntityList.forEach(user ->{
            user.setNotifyCount(0);
            cowinDbUserRepo.save(user);
        });
    }
    

}
