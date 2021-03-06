package com.help.cowin.util.impl;

import com.help.cowin.config.TelegramConfig;
import com.help.cowin.util.TelegramService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Message;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class TelegramServiceImpl implements TelegramService {

    @Autowired
    TelegramConfig telegramConfig;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public boolean sendChat(Long chatId, String message) {
        
        JSONObject request = new JSONObject();
        request.put("chat_id", chatId);
        request.put("text", message);

        // set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);

        log.info("Telegram Send Message URL:", telegramConfig.getSendMessageURL());
        log.info("Request"+entity.toString());
        ResponseEntity<Message> messageSent = null;
       
            messageSent = restTemplate.exchange(telegramConfig.getSendMessageURL(), 
                HttpMethod.POST, entity, Message.class);
       
        

        if(null != messageSent)
            return true;
        else return false;
        
    }

    
}
