package com.learn.cache.dao.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class User implements Serializable {
    private Integer id;
    private String name;
    private Long updateTime;

    public void setUpdateTime(String updateTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.updateTime = format.parse(updateTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            this.updateTime = null;
        }
    }

    public String getUpdateTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(updateTime));
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
