# 关于权限管理的设计
### 数据库
```
db:
    user:                [用户]的基本信息
        userID
        userName
        userPassword
        ...
    user_role:           用户可选的[角色]信息
        roleID
        roleName
        ...
    user_permission:     角色可对应的[权限]
        permissionID
        permissionName
        permissionURL
        ...
    manager_info:        多对多，阐明哪些[用户]拥有哪些[角色]
        mangerInfoID
        userID
        roleID
    manager_role:        多对多，阐明哪些[角色]拥有哪些[权限]
        managerRoleID
        roleID
        permissionID
```
### 类
```
model:
    AuthManagerInfo:        根据userID查询[user] => User
                                查询[manager_info] => List<roleID>.forEach => /
                                roleID => (AuthManagerRole) => List<AuthManagerRole>
    AuthManagerRole:        根据roleID查询[user_role] => UserRole
                                查询[manager_role] => List<permissionID>.forEach => /
                                permissionId => List<UserPermission> 
entity:
    User:                   数据库实体类,仅增删改查
    UserRole:               数据库实体类,仅增删改查
    UserPermission:         数据库实体类,仅增删改查
    ManagerInfo:            数据库实体类,仅增删改查
    MnagerRole:             数据库实体类,仅增删改查
```
### 服务
```
service:
    UserService-impl
    UserRoleService-impl
    UserPermissionService-impl
    ManagerInfoService-impl
    ManagerRoleService-impl
    * UserManagerService:           上诉服务的综合，以获得AuthManagerInfo

```
```
备注:
    关于数据库User表:
        userID:                 由系统生成，不重复不为空，唯一，永不更改
        username:               一般是登录用户名，永不改变，不重复，不可为空(不可用于第三方登录)
        account_name:           一般是登录用户名，做第二登录，可用于第三方登录,可为空（根据业务可更改，不可重复）
        nickname:               用户昵称，其他用户可见的名字，可重复可更改，不可为空(且更改频繁)
        name:                   用户真实姓名，可重复，可更改
        *-----------------------手机号一般为username，第二手机号/qq邮箱等为account_name
        |                       用户自定义6-12字符串为用户名,某某某为name
        |                       权限级识别用户身份使用userID+username/account_name(根据请求切换识别第一登录用户名还是第二登录用户名)
        *-----------------------用户级识别用户使用userID
```
### 关于缓存
```
目前我所知的有ehcache,redis
ehcache:
    没去了解
redis:
    在只引入spring-boot-starter-data-redis和commons-pool2情况下
    需要实现Cache<K,V>和CacheManager这2个类，虽然操作起来和Map差不多
    但是：
```
