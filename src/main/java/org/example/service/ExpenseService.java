package org.example.service;

import org.example.mapper.ExpenseMapper;
import org.example.type.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseMapper expenseMapper;

    public List<Expense> getAllExpense(){
        return expenseMapper.getAllExpense();
    }

    public Expense getAllExpenseById(Integer id){
        return expenseMapper.getAllExpenseById(id);
    }

    public Integer insert(Expense expense){
        return expenseMapper.insert(expense);
    }

    public Integer update(Expense expense){
        return expenseMapper.update(expense);
    }
    public Integer delete(Integer id){
        return expenseMapper.delete(id);
    }

    public String getTypeName(Integer typeId){
        return expenseMapper.getTypeName(typeId);
    }

    public List<Expense> getAllExpenseByExpenseDate(LocalDate startDate, LocalDate endDate){
        return expenseMapper.getAllExpenseByExpenseDate(startDate,endDate);
    }

    public List<Expense> getAllExpenseByCreateDate(LocalDateTime startDate, LocalDateTime endDate){
        return expenseMapper.getAllExpenseByCreateDate(startDate,endDate);
    }

    public List<Expense> getAllExpenseByUpdateDate(LocalDateTime startDate, LocalDateTime endDate){
        return expenseMapper.getAllExpenseByUpdateDate(startDate,endDate);
    }



}
