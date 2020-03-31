package com.learn.shiro.model;

import com.learn.shiro.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class AuthManagerInfo {

    private User user;
    private List<AuthManagerRole> roles;





}
