package org.example.pojo;


import com.fasterxml.jackson.core.type.TypeReference;
import org.example.util.JsonUtil;
import org.example.util.Common_until;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String password;
    private String createTime;
    private String updateTime;
    private boolean lock;
    private boolean isDeleted;


    public User(){

    }

    public User(String username, String password) {
        this.id= JsonUtil.readUserCounts(Common_until.fileName, new TypeReference<List<User>>() {})+1;
        this.username = username;
        this.password = password;
        this.createTime=LocalDateTime.now().format(Common_until.formatter1);
        this.updateTime=LocalDateTime.now().format(Common_until.formatter1);
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
        return createTime;
    }

    public void setCreateTime(String createTime) {
        createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        updateTime = updateTime;
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
