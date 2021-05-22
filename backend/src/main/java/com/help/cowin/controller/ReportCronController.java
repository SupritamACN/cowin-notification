package com.help.cowin.controller;

import java.util.List;

import com.help.cowin.config.CowinEndpoints;
import com.help.cowin.config.MailConfig;
import com.help.cowin.pojos.UserEntity;
import com.help.cowin.repo.CowinDbUserRepo;
import com.help.cowin.repo.UserEntityUVRepo;
import com.help.cowin.util.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ReportCronController {

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
