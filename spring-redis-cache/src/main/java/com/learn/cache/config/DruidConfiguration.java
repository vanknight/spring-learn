package com.learn.cache.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.JdbcConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
@ConfigurationProperties(prefix = "druid")
@Data
public class DruidConfiguration {
    private String url;
    private String username;
    private String password;
    private String driverClassName;

    private Integer initialSize = 10;
    private Integer minIdle = 3;
    private Integer maxActive = 60;
    private Integer maxWait = 60000;
    private Integer timeBetweenEvictionRunsMillis = 60000;
    private Integer minEvictableIdleTimeMillis = 300000;
    private Boolean poolPreparedStatements = true;
    private Integer maxPoolPreparedStatementPerConnectionSize = 50;
    private String filters = "stat";

    public void config(DruidDataSource dataSource) {
        dataSource.setDbType(JdbcConstants.MYSQL);
        dataSource.setUrl(url);
        dataSource.setPassword(password);
        dataSource.setUsername(username);
        dataSource.setDriverClassName(driverClassName);

        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);

        try {
            dataSource.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
