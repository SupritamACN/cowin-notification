package com.help.cowin.controller;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.help.cowin.pojos.Response;
import com.help.cowin.pojos.UserEntity;
import com.help.cowin.pojos.UserEntityUV;
import com.help.cowin.util.TelegramService;
import com.help.cowin.util.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/v1/public")
@CrossOrigin(origins = "*")
@Slf4j
public class TelegramController {

    @Autowired
    private TelegramService telegramService;

    @Autowired
    private UserService userService;

    @GetMapping("/telegram/subscribe/{email}")
    public ResponseEntity<Object> telegramSubcribe(@PathVariable("email") String email){
        UserEntity userEntity = userService.findUserByEmail(email);
        UserEntityUV userEntityUV = userService.findUVUserByMail(email);
        if(userEntity == null && userEntityUV == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        else if(userEntity != null && userEntityUV == null ) {
            return ResponseEntity.status(HttpStatus.OK).body(new Response("OK", userEntity.get_id()));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(new Response("OK", userEntityUV.get_id()));
        }
    }

    @PostMapping("/1788947908:AAGLz3HunYcCKneOZbrOU0IF-PuhJRYcVwI")
    public ResponseEntity<Object> telegramUpdate(@RequestBody Update update){
        
        String welcomeChatMessage = "Thank you for subscribing to Telegram for slot updates. You will get updates for districts:";

        log.info(update.toString());
        //updates.forEach((Update update) -> {
            if(update.getMessage().getText().contains("/start") && update.getMessage().getText().length() > 7){
                String userid = update.getMessage().getText().substring(7).trim();
                UserEntity user = userService.findUserById(userid);
                if(user == null){
                    user = userService.findUserByEmail(userid);
                    if(user != null){
                        user.setChatId(update.getMessage().getChatId());
                        userService.saveUser(user);
                        telegramService.sendChat(user.getChatId(), welcomeChatMessage + user.getDistrictNameAString() + 
                                " for Age:" + (user.getMinAgeLimit() == 0 ? "Both" : Integer.toString(user.getMinAgeLimit()))+"\n Type 'Unsubscribe' to turn off" 
                                + " Telegram notifications.");
                    }else{
                        UserEntityUV userUV = userService.findUVUserById(userid);
                        if(userUV != null){
                            userService.deleteUVUserByMail(userUV.getEmail());
                            UserEntity userToBeSaved = new UserEntity(userUV.get_id(),userUV.getEmail(),
                                userUV.getDistrict(),userUV.getMinAgeLimit(), false, 0);
                            userToBeSaved.setChatId(update.getMessage().getChatId()); 
                            userService.saveUser(userToBeSaved);
                            telegramService.sendChat(userToBeSaved.getChatId(), welcomeChatMessage + userToBeSaved.getDistrictNameAString() + 
                                " for Age:" + (userToBeSaved.getMinAgeLimit() == 0 ? "Both" : Integer.toString(userToBeSaved.getMinAgeLimit()))+"\n Type 'Unsubscribe' to turn off" 
                                + " Telegram notifications.");
                        }
                        else{
                            userUV = userService.findUVUserByMail(userid);
                            if(userUV != null){
                                userService.deleteUVUserByMail(userUV.getEmail());
                                UserEntity userToBeSaved = new UserEntity(userUV.get_id(),userUV.getEmail(),
                                    userUV.getDistrict(),userUV.getMinAgeLimit(), false, 0);
                                userToBeSaved.setChatId(update.getMessage().getChatId()); 
                                userService.saveUser(userToBeSaved);
                                telegramService.sendChat(userToBeSaved.getChatId(), welcomeChatMessage + userToBeSaved.getDistrictNameAString() + 
                                " for Age:" + (userToBeSaved.getMinAgeLimit() == 0 ? "Both" : Integer.toString(userToBeSaved.getMinAgeLimit()))+"\n Type 'Unsubscribe' to turn off" 
                                + " Telegram notifications.");
                            }
                        }
                    }
                }else{
                    user.setChatId(update.getMessage().getChatId());
                    userService.saveUser(user);
                } 
            }
            else if(update.getMessage().getText().contains("/start") && update.getMessage().getText().length() == 6){

                telegramService.sendChat(update.getMessage().getChatId(), "Ping your registered email id to subscribe for Telegram notification. Type 'Unsubscribe' to turn off" 
                + " Telegram notifications. Email notifications may still continue.");

            }else if(update.getMessage().getText().toLowerCase().contains("unsubscribe")){

                UserEntity user = userService.findUserByChatId(update.getMessage().getChatId());
                if(user != null){
                    userService.deleteUser(user);
                    UserEntity userToBeSaved = new UserEntity(user.get_id(), user.getEmail(), 
                        user.getDistrict(), user.getMinAgeLimit(), user.isEnabled(), user.getNotifyCount());
                    userService.saveUser(userToBeSaved);
                    telegramService.sendChat(update.getMessage().getChatId(), "Unsubscribed from" 
                    + " Telegram notifications. Email notifications may still continue.");
                }else{
                    telegramService.sendChat(update.getMessage().getChatId(), "You are not registered");
                }

            }else{
                ArrayList<String> email = getEmailAddressesInString(update.getMessage().getText());
                if(email.size() > 0){
                    email.forEach(e ->{
                        
                            UserEntity user = userService.findUserByEmail(e);
                            if(user != null){
                                userService.deleteUser(user);
                                UserEntity userToBeSaved = new UserEntity(user.get_id(), user.getEmail(), 
                                    user.getDistrict(), user.getMinAgeLimit(), user.isEnabled(), user.getNotifyCount());
                                userToBeSaved.setChatId(update.getMessage().getChatId());
                                userService.saveUser(userToBeSaved);
                                telegramService.sendChat(user.getChatId(), welcomeChatMessage + userToBeSaved.getDistrictNameAString() + 
                                        " for Age:" + (userToBeSaved.getMinAgeLimit() == 0 ? "Both" : Integer.toString(userToBeSaved.getMinAgeLimit()))+"\n Type 'Unsubscribe' to turn off" 
                                        + " Telegram notifications.");
                            }else{
                                UserEntityUV userUV = userService.findUVUserByMail(e);
                                if(userUV != null){
                                    userService.deleteUVUserByMail(userUV.getEmail());
                                    UserEntity userToBeSaved = new UserEntity(userUV.get_id(),userUV.getEmail(),
                                        userUV.getDistrict(),userUV.getMinAgeLimit(), false, 0);
                                    userToBeSaved.setChatId(update.getMessage().getChatId()); 
                                    userService.saveUser(userToBeSaved);
                                    telegramService.sendChat(userToBeSaved.getChatId(), welcomeChatMessage + userToBeSaved.getDistrictNameAString() + 
                                        " for Age:" + (userToBeSaved.getMinAgeLimit() == 0 ? "Both" : Integer.toString(userToBeSaved.getMinAgeLimit()))+"\n Type 'Unsubscribe' to turn off" 
                                        + " Telegram notifications.");
                                }

                            }
                        
                    });
                }else{
                    telegramService.sendChat(update.getMessage().getChatId(), "Ping your registered email id to subscribe for Telegram notification. Type 'Unsubscribe' to turn off" 
                    + " Telegram notifications. Email notifications may still continue.");
                }
            } 
        
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    public ArrayList<String> getEmailAddressesInString(String text) {
        ArrayList<String> emails = new ArrayList<>();

        Matcher matcher = Pattern.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}").matcher(text);
        while (matcher.find()) {
            emails.add(matcher.group());
        }
        return emails;
    }
}
