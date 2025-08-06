package org.example.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.emun.ListCondition;
import org.example.emun.Unit;
import org.example.type.Expense;
import org.example.requestBody.ExpenseRequest;
import org.example.service.ExpenseService;
import org.example.util.Common_until;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expense")
public class ExpenseTrackerController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/add")
    public ResponseEntity<?> addExpense(@RequestBody ExpenseRequest expenseRequest){
        try {
            int flg =0;
            flg=Common_until.<Integer>check(expenseRequest.getType_id())+flg;
            flg=Common_until.<BigDecimal>check(expenseRequest.getAmount())+flg;
            flg=Common_until.<LocalDate>check(expenseRequest.getExpensedate())+flg;

            if(flg!=0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("type_id and amount and expensedate cant be empty ");
            }

            flg=Common_until.<String>check(Common_until.typeMap.get(expenseRequest.getType_id()))+flg;
            if(flg!=0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("type_id is not exist (type_id : " +expenseRequest.getType_id()+ ")");
            }

            Expense expense =new Expense(expenseRequest.getType_id(),expenseRequest.getAmount(),expenseRequest.getDetail(),expenseRequest.getExpensedate());
            Integer result= expenseService.insert(expense);
            if (result == 0){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("expense add failed. please contract system admin");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("system error");
        }
        return ResponseEntity.ok("Add expense successfully");
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateExpense(@RequestBody ExpenseRequest expenseRequest){
        if(expenseRequest.getId()!=null) {
            Expense expense =expenseService.getAllExpenseById(expenseRequest.getId());

            if(expense==null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id is not exist (id :"+expenseRequest.getId()+")");
            }else {
                String typeName = expenseService.getTypeName(expenseRequest.getType_id());
                if(typeName==null){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("type_id is not exist (type_id : " +expenseRequest.getType_id()+ ")");
                }

                Optional.ofNullable(expenseRequest.getType_id()).ifPresent(expense::setTypeId);
                Optional.ofNullable(expenseRequest.getAmount()).ifPresent(expense::setAmount);
                Optional.ofNullable(expenseRequest.getDetail()).ifPresent(expense::setDetial);
                Optional.ofNullable(expenseRequest.getExpensedate()).ifPresent(expense::setExpenseDate);

                expense.setUpdateTime(LocalDateTime.now());
                Integer result = expenseService.update(expense);

                if (result==0){
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("expense updated failed. please contract system admin");
                }
                return ResponseEntity.ok("id : "+expenseRequest.getId()+ " updated successfully");
            }
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id should not be empty");
        }
    }

    @PostMapping("delete")
    public ResponseEntity<?> deleteExpense(@RequestBody ExpenseRequest expenseRequest){
        if(expenseRequest.getId()!=null) {
            Expense expense =expenseService.getAllExpenseById(expenseRequest.getId());
            if(expense == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id is not exist (id :"+expenseRequest.getId()+")");
            }else {
                Integer result= expenseService.delete(expenseRequest.getId());
                if (result==0){
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("expense deleted failed. please contract system admin");
                }
                return ResponseEntity.ok("id : "+expenseRequest.getId()+ " deleted successfully");
            }
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id should not be empty");
        }
    }

    @PostMapping("list")
    public ResponseEntity<?> listExpense(@RequestBody ExpenseRequest expenseRequest){
        List<Expense> expenseList=new ArrayList<>();
        if(expenseRequest.getNum()!=null && expenseRequest.getUnit()!=null && expenseRequest.getListCondition()!=null){
            LocalDateTime now =LocalDateTime.now();
            LocalDateTime after;
            switch (expenseRequest.getUnit()){
                case DAY : after=now.minusDays(expenseRequest.getNum()); break;
                case YEAR: after=now.minusYears(expenseRequest.getNum()); break;
                case MONTH:after=now.minusMonths(expenseRequest.getNum()); break;
                case WEEK:after=now.minusWeeks(expenseRequest.getNum()); break;
                case HOUR:
                    if(expenseRequest.getListCondition()!= ListCondition.EXPENSE_TIME){
                        after=now.minusHours(expenseRequest.getNum());
                    }else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("you cant use EXPENSE_TIME to getList with HOUR");
                    }
                    break;
                case MINUTE:
                    if(expenseRequest.getListCondition()!= ListCondition.EXPENSE_TIME){
                        after=now.minusMinutes(expenseRequest.getNum());
                    }else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("you cant use EXPENSE_TIME to getList with MINUTE");
                    }
                    break;
                default: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("you can only use "+Unit.values());
            }

            switch (expenseRequest.getListCondition()){
                case EXPENSE_TIME :
                    expenseList=expenseService.getAllExpenseByExpenseDate(after.toLocalDate(),now.toLocalDate());
                    break;
                case CREATE_TIME:
                    expenseList=expenseService.getAllExpenseByCreateDate(after,now);
                    break;
                case UPDATE_TIME:
                    expenseList=expenseService.getAllExpenseByUpdateDate(after,now);
                    break;
                default:
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("you can only use "+ListCondition.values());
            }

            if (expenseList.isEmpty()){
                return ResponseEntity.ok("this is no data for your search condition");
            }
            return ResponseEntity.ok(expenseList);
        }else if(expenseRequest.getStartDate()!=null && expenseRequest.getEndDate()!=null&& expenseRequest.getListCondition()!=null){
            LocalDate startDate=LocalDate.parse(expenseRequest.getStartDate(),Common_until.formatter2);
            LocalDate endDate=LocalDate.parse(expenseRequest.getEndDate(),Common_until.formatter2);
            if(startDate.isAfter(endDate)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("the endDate should be than than the startDate.");
            }else {
                switch (expenseRequest.getListCondition()){
                    case EXPENSE_TIME :
                        expenseList=expenseService.getAllExpenseByExpenseDate(startDate,endDate);
                        break;
                    case CREATE_TIME:
                        expenseList=expenseService.getAllExpenseByCreateDate(startDate.atStartOfDay(),endDate.atStartOfDay());
                        break;
                    case UPDATE_TIME:
                        expenseList=expenseService.getAllExpenseByUpdateDate(startDate.atStartOfDay(),endDate.atStartOfDay());
                        break;
                    default:
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("you can only use "+ListCondition.values());
                }

                if (expenseList.isEmpty()){
                    return ResponseEntity.ok("this is no data for your search condition");
                }
                return ResponseEntity.ok(expenseList);
            }
        } else {
            expenseList=expenseService.getAllExpense();
            return ResponseEntity.ok(expenseList);
        }
    }


}
