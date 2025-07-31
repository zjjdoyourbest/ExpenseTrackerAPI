package org.example.pojo;

import org.example.requestBody.ExpenseRequest;
import org.example.util.Common_until;

import java.time.LocalDateTime;

public class Expense {
    private int id;
    private int type_id;
    private String type_name;
    private double amount;
    private String detial;
    private String expensedate;
    private String createTime;
    private String updateTime;

    public Expense() {
    }

//    public Expense(ExpenseRequest expenseRequest) {
//        this.id=todo;
//        this.type_id = expenseRequest.getType_id();
//        this.type_name=?;
//        this.amount = expenseRequest.getAmount();
//        this.detial = expenseRequest.getDetial();
//        this.expensedate = expenseRequest.getExpensedate();
//        this.createTime = LocalDateTime.now().format(Common_until.formatter1);
//        this.updateTime = LocalDateTime.now().format(Common_until.formatter1);
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDetial() {
        return detial;
    }

    public void setDetial(String detial) {
        this.detial = detial;
    }

    public String getExpensedate() {
        return expensedate;
    }

    public void setExpensedate(String expensedate) {
        this.expensedate = expensedate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
