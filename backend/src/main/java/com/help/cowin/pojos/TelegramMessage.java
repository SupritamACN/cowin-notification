package com.help.cowin.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Setter @Getter @ToString
public class TelegramMessage {

    @JsonProperty("message_id")
    private Integer messageId;

    private String text;

    private TelegramChat chat;

    private TelegramUser user;

}
