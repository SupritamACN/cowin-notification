package com.help.cowin.controller;

import com.help.cowin.config.YAMLConfig;
import com.help.cowin.pojos.Centers;
import com.help.cowin.pojos.TelegramUpdate;
import com.help.cowin.pojos.UserEntity;
import com.help.cowin.pojos.UserEntityUV;
import com.help.cowin.repo.CowinDbUserRepo;
import com.help.cowin.util.ApiService;
import com.help.cowin.util.EmailService;
import com.help.cowin.util.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/public")
@CrossOrigin(origins = "*")
@Slf4j
public class CowinApiPublicController {

    
    @Autowired
    private YAMLConfig yamlConfig;

    @Autowired
    private EmailService mailService;

    @Autowired
    private ApiService apiService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/test")
    public String testPoint(){
        return "Response from cowin-mail-notifier application:)";
    }

    
    @PostMapping("/subscribe")
    public ResponseEntity<Object> saveUVUser(@RequestBody UserEntityUV userEntityUV){

        if(userService.findUserByEmail(userEntityUV.getEmail()) != null){
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("email already registered!");
        }
        UserEntityUV existingUVUser = userService.findUVUserByMail(userEntityUV.getEmail());
        if( existingUVUser != null){
            existingUVUser.setDistrict(userEntityUV.getDistrict());
            userService.saveUVUser(existingUVUser);
            String link = yamlConfig.getValidatelink()+"/validate/"+existingUVUser.get_id();
            mailService.sendEmail(existingUVUser.getEmail(), "Subscription Verification", "Hello, Thank you for subcribing. \n"+
            "Please click on the link to validate and enable your subcription:" + link );
            return ResponseEntity.status(HttpStatus.OK).body(existingUVUser.get_id());
        }
        userEntityUV.setEnabled(false);
        UserEntityUV savedUser = userService.saveUVUser(userEntityUV);
        if(savedUser != null){
            String link = yamlConfig.getValidatelink()+"/validate/"+userEntityUV.get_id();
            mailService.sendEmail(userEntityUV.getEmail(), "Subscription Verification", "Hello, Thank you for subcribing. \n"+
            "Please click on the link to validate and enable your subcription:" + link );
            return ResponseEntity.status(HttpStatus.OK).body(userEntityUV.get_id());
        }
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Invalid request!");
    }

    @GetMapping("/validate/{objectId}")
    public ResponseEntity<Object> enableSubcription(@PathVariable("objectId") String id){
        UserEntity savedUser = null;
        UserEntityUV userEntityUV = userService.findUVUserById(id);
        if(userEntityUV == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token!");
        if(userEntityUV.isEnabled())
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Already verified.");

        if(userEntityUV != null) {
 
            userService.deleteUVUserByMail(userEntityUV.getEmail());
            savedUser = userService.saveUser(new UserEntity(userEntityUV.getEmail(),userEntityUV.getDistrict(),userEntityUV.getMinAgeLimit(), true));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Subcription activated!!");
    }

    @GetMapping("/unsubscribe/{email}")
    public ResponseEntity<Object> deleteUser(@PathVariable("email") String email){
        UserEntity userEntity = userService.findUserByEmail(email);
        if(userEntity == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        if(userEntity != null) {
            userService.deleteUser(userEntity);
            userService.deleteUVUserByMail(email);
        }
        return ResponseEntity.status(HttpStatus.OK).body("User removed successfully!");
    }

    @PostMapping("/1788947908:AAGLz3HunYcCKneOZbrOU0IF-PuhJRYcVwI")
    public ResponseEntity<Object> telegramUpdate(@RequestBody Update update){
        
        log.error(update.toString());
        
        if(update.getMessage().getText().contains("/start")){
            String userid = update.getMessage().getText().substring(6);
            UserEntity user = userService.findUserById(userid);
            if(user == null){
                user = userService.findUserByEmail(userid);
                if(user != null){
                    user.setChatId(update.getMessage().getChatId());
                }else{
                    UserEntityUV userUV = userService.findUVUserById(userid);
                    if(userUV != null){
                        userService.deleteUVUserByMail(userUV.getEmail());
                        UserEntity userToBeSaved = new UserEntity(userUV.getEmail(),userUV.getDistrict(),userUV.getMinAgeLimit(), true);
                        userToBeSaved.setChatId(update.getMessage().getChatId()); 
                        userService.saveUser(userToBeSaved);
                    }
                }
            }else{
                user.setChatId(update.getMessage().getChatId());
            }
            userService.saveUser(user);
            
        }


        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
