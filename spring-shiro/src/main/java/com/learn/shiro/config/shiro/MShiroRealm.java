package com.learn.shiro.config.shiro;

import com.learn.shiro.entity.UserPermission;
import com.learn.shiro.entity.UserRole;
import com.learn.shiro.model.AuthManagerInfo;
import com.learn.shiro.model.AuthManagerRole;
import com.learn.shiro.model.JWTToken;
import com.learn.shiro.service.UserManagerService;
import com.learn.shiro.utils.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class MShiroRealm extends AuthorizingRealm {
    //你自定义的Cache的实现类的实例
    private Cache<Object, AuthorizationInfo> authorizationCache;
    @Autowired
    private UserManagerService userManagerService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        String username = JWTUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }
        AuthManagerInfo managerInfo = userManagerService.getManagerInfo(username);
        if (managerInfo == null) {
            throw new AuthenticationException("用户不存在");
        }
        if (!JWTUtil.verify(token, username, managerInfo.getUser().getPassword())) {

            throw new AuthenticationException("Token认证失败");
        }
        return new SimpleAuthenticationInfo(token, token, "m_realm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = JWTUtil.getUsername(principalCollection.toString());
        AuthManagerInfo managerInfo = userManagerService.getManagerInfo(username);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for (AuthManagerRole authRole : managerInfo.getRoles()) {
            UserRole role = authRole.getRole();
            info.addRole(role.getRoleName());
            for (UserPermission permission : authRole.getPermissions()) {
                info.addStringPermission(permission.getPermissionName());
            }
        }
        return info;
    }

    /**
     * 以下方法可以全部删除，不影响使用，如果要修改缓存规则（存储类型，存储前缀等等）在下面找到get，put方法修改
     * @return
     */
    /**
     * CacheManager初始化，类型为你注入到SecurityManager里的CacheManager
     * @return
     */
    private Cache<Object, AuthorizationInfo> getAuthorizationCacheLazy() {
        if (this.authorizationCache == null) {
            CacheManager cacheManager = this.getCacheManager();
            if (cacheManager != null) {
                String cacheName = this.getAuthorizationCacheName();
                this.authorizationCache = cacheManager.getCache(cacheName);
            }
        }
        return this.authorizationCache;
    }

    private Cache<Object, AuthorizationInfo> getAvailableAuthorizationCache() {
        Cache<Object, AuthorizationInfo> cache = this.getAuthorizationCache();
        if (cache == null && this.isAuthorizationCachingEnabled()) {
            cache = this.getAuthorizationCacheLazy();
        }
        return cache;
    }

    @Override
    protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            return null;
        } else {
            AuthorizationInfo info = null;

            Cache<Object, AuthorizationInfo> cache = this.getAvailableAuthorizationCache();
            Object key;
            if (cache != null) {
                key = this.getAuthorizationCacheKey(principals).toString();
                info = cache.get(key);
            }
            if (info == null) {
                info = this.doGetAuthorizationInfo(principals);
                if (info != null && cache != null) {
                    key = this.getAuthorizationCacheKey(principals).toString();
                    cache.put(key, info);
                }
            }

            return info;
        }
    }
}
