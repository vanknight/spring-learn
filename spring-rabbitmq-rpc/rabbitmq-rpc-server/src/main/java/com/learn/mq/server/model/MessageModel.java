package com.learn.mq.server.model;

import lombok.Data;

import java.util.Date;

@Data
public class MessageModel {
    private String message;
    private Date date;
}
