package com.learn.shiro.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String password;
    private String salt;
    private String state;

}
