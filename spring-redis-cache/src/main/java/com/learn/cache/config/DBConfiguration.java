package com.learn.cache.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.learn.cache.dao.repository"})
public class DBConfiguration {
    @Autowired
    private Environment env;
    @Resource
    private DruidConfiguration druidConfiguration;

    @Bean
    public DruidDataSource singleDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        druidConfiguration.config(dataSource);
        return dataSource;
    }
}
