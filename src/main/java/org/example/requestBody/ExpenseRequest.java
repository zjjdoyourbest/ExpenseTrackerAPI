package org.example.requestBody;

import org.example.emun.ListCondition;
import org.example.emun.Unit;

public class ExpenseRequest {
    private Integer id;
    private Integer type_id;
    private Double amount;
    private String detail;
    private String expensedate;
    private Unit unit;
    private Integer num;
    private ListCondition listCondition;

    public ListCondition getListCondition() {
        return listCondition;
    }

    public void setListCondition(ListCondition listCondition) {
        this.listCondition = listCondition;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType_id() {
        return type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getExpensedate() {
        return expensedate;
    }

    public void setExpensedate(String expensedate) {
        this.expensedate = expensedate;
    }
}
