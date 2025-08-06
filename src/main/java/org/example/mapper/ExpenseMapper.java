package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.type.Expense;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ExpenseMapper {
    List<Expense> getAllExpense();
    List<Expense> getAllExpenseByExpenseDate(LocalDate startDate,LocalDate endDate);
    List<Expense> getAllExpenseByCreateDate(LocalDateTime startDate,LocalDateTime endDate);
    List<Expense> getAllExpenseByUpdateDate(LocalDateTime startDate,LocalDateTime endDate);
    Expense getAllExpenseById(Integer id);
    Integer insert(Expense expense);
    Integer update(Expense expense);
    Integer delete(Integer id);
    String getTypeName(Integer typeId);
}
