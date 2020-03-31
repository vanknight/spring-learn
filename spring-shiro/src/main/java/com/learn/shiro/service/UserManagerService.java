package com.learn.shiro.service;

import com.alibaba.fastjson.JSONObject;
import com.learn.shiro.entity.*;
import com.learn.shiro.model.AuthManagerInfo;
import com.learn.shiro.model.AuthManagerRole;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserManagerService {
    /**
     * 理论这里注入5个权限管理的服务
     */
    public AuthManagerInfo getManagerInfo(String username) {
        User user = new User();
        user.setId(4);
        user.setName(username);
        user.setSalt("asdf");
        user.setPassword("db8b543ffd86eb127bd82626f6dd815d");
        return getManagerInfo(user);
    }

    public AuthManagerInfo getManagerInfo(User user) {
        AuthManagerInfo info = new AuthManagerInfo();
        //判断user是否为空等等
        List<ManagerInfo> managerInfos = getManagerInfos(user.getId());
        List<AuthManagerRole> authRoles = new ArrayList<>();
        managerInfos.forEach(item -> {
            Integer roleId = item.getRoleId();
            AuthManagerRole authRole = getAuthManagerRole(roleId);
            authRoles.add(authRole);
        });
        info.setUser(user);
        info.setRoles(authRoles);
        System.out.println(JSONObject.toJSONString(info));
        return info;
    }

    private List<ManagerInfo> getManagerInfos(Integer userId) {
        List<ManagerInfo> managerInfos = new ArrayList<>();
        for (int i = 1; i <= userId; i++) {
            ManagerInfo managerInfo = new ManagerInfo();
            managerInfo.setId(i);
            managerInfo.setUserId(userId);
            managerInfo.setRoleId(i);
            managerInfos.add(managerInfo);
        }
        return managerInfos;
    }

    private AuthManagerRole getAuthManagerRole(Integer roleId) {
        UserRole role = new UserRole();
        role.setId(roleId);
        role.setRoleName(roleId > 3 ? "admin" : "user");
        List<UserPermission> permissions = new ArrayList<>();
        for (int i = 1; i <= roleId; i++) {
            UserPermission permission = new UserPermission();
            permission.setId(i);
            switch (i){
                case 1:
                    permission.setPermissionName("create");
                    break;
                case 2:
                    permission.setPermissionName("update");
                    break;
                case 3:
                    permission.setPermissionName("read");
                    break;
                default:
                    permission.setPermissionName("delete");
                    break;
            }

            permission.setUrl("/**?pid=" + i);
            permissions.add(permission);
        }
        AuthManagerRole authManagerRole = new AuthManagerRole();
        authManagerRole.setRole(role);
        authManagerRole.setPermissions(permissions);
        return authManagerRole;
    }


    public String passwordTo(String pwd) {
        return "asdf" + pwd + "asdf";
    }

}
