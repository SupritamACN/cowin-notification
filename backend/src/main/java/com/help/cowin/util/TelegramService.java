package com.help.cowin.util;

import org.springframework.stereotype.Service;

@Service
public interface TelegramService {
    
    public boolean sendChat(String chatId, String message); 
}
