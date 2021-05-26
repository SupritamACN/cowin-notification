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

   
}
