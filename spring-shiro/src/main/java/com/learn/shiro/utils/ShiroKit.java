package com.learn.shiro.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;

public class ShiroKit {
    //name_delimiter
    private static final String NAMES_DELIMITER = ",";
    private static final String HASH_ALGORITHM_NAME = "MD5";
    private static final int HASH_FOR_COUNT = 2;

    public static String md5(String s1, String s2) {
        return new SimpleHash(HASH_ALGORITHM_NAME, s1, s2, HASH_FOR_COUNT).toHex();
    }

    public static String getRandomSalt(int len) {
        return new SecureRandomNumberGenerator().nextBytes(len).toHex();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static boolean hasRole(String roleName) {
        return getSubject() != null && roleName != null
                && roleName.length() > 0 && getSubject().hasRole(roleName);
    }

    public static boolean hasAnyRoles(String roleNames) {
        boolean hasAnyRole = false;
        Subject subject = getSubject();
        if (subject != null && roleNames != null && !roleNames.isEmpty()) {
            for (String role : roleNames.split(NAMES_DELIMITER)) {
                if (subject.hasRole(role.trim())) {
                    hasAnyRole = true;
                    break;
                }
            }
        }
        return hasAnyRole;
    }

    public static boolean hasPermission(String permission) {
        return getSubject() != null && permission != null
                && !permission.isEmpty() && getSubject().isPermitted(permission);
    }

    public static boolean isAuthenticated() {
        return getSubject() != null && getSubject().isAuthenticated();
    }

    public static boolean isUser() {
        return getSubject() != null && getSubject().getPrincipal() != null;
    }
}
