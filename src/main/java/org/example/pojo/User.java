package org.example.pojo;


import org.example.util.JsonUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {
    private int id;
    private String username;
    private String password;
    private String CreateTime;
    private String UpdateTime;
    private boolean lock;
    private boolean isDeleted;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public User(){

    }

    public User(String username, String password) {
        this.id= JsonUtil.readUserCounts()+1;
        this.username = username;
        this.password = password;
        this.CreateTime=LocalDateTime.now().format(formatter);
        this.UpdateTime=LocalDateTime.now().format(formatter);
        this.lock=false;
        this.isDeleted=false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }



}
