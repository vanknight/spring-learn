package com.learn.shiro.model;

import com.learn.shiro.entity.UserPermission;
import com.learn.shiro.entity.UserRole;
import lombok.Data;

import java.util.List;

@Data
public class AuthManagerRole {

    private UserRole role;
    private List<UserPermission> permissions;
}
