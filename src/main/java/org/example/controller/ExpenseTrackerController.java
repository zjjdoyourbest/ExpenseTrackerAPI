package org.example.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.emun.ListCondition;
import org.example.emun.Unit;
import org.example.pojo.Expense;
import org.example.requestBody.ExpenseRequest;
import org.example.requestBody.LoginRequest;
import org.example.util.Common_until;
import org.example.util.JsonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@RestController
@RequestMapping("/api/expense")
public class ExpenseTrackerController {

    @PostMapping("/add")
    public ResponseEntity<?> addExpense(@RequestBody ExpenseRequest expenseRequest){
        try {
            int flg =0;
            flg=Common_until.<Integer>check(expenseRequest.getType_id())+flg;
            flg=Common_until.<Double>check(expenseRequest.getAmount())+flg;
            flg=Common_until.<String>check(expenseRequest.getExpensedate())+flg;

            if(flg!=0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("type_id and amount and expensedate cant be empty ");
            }

            flg=Common_until.<String>check(Common_until.typeMap.get(expenseRequest.getType_id()))+flg;
            if(flg!=0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("type_id is not exist (type_id : " +expenseRequest.getType_id()+ ")");
            }

            Expense expense =new Expense(expenseRequest);
            boolean result= JsonUtil.writeJsonFile(expense, Common_until.expense_fileName, new TypeReference<List<Expense>>() {},0,null);
            if (!result){
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
            Optional<Expense> optionalExpense= JsonUtil.<Expense>find(e -> expenseRequest.getId() == e.getId(), Common_until.expense_fileName, new TypeReference<List<Expense>>() {});
            if(!optionalExpense.isPresent()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id is not exist (id :"+expenseRequest.getId()+")");
            }else {
                int flg =0;
                flg=Common_until.<String>check(Common_until.typeMap.get(expenseRequest.getType_id()))+flg;
                if(flg!=0){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("type_id is not exist (type_id : " +expenseRequest.getType_id()+ ")");
                }

                Expense expense=optionalExpense.get();
                Optional.ofNullable(expenseRequest.getType_id()).ifPresent(expense::setType_id);
                Optional.ofNullable(expenseRequest.getAmount()).ifPresent(expense::setAmount);
                Optional.ofNullable(expenseRequest.getDetail()).ifPresent(expense::setDetial);
                Optional.ofNullable(expenseRequest.getExpensedate()).ifPresent(expense::setExpensedate);


                expense.setUpdateTime(LocalDateTime.now().format(Common_until.formatter1));

                Predicate<Expense> predicate = expense1 ->expense1.getId() ==expenseRequest.getId();
                boolean result= JsonUtil.writeJsonFile(expense,Common_until.expense_fileName,new TypeReference<List<Expense>>(){},1,predicate);
                if (!result){
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
            Optional<Expense> optionalExpense= JsonUtil.<Expense>find(e -> expenseRequest.getId() == e.getId(), Common_until.expense_fileName, new TypeReference<List<Expense>>() {});
            if(!optionalExpense.isPresent()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id is not exist (id :"+expenseRequest.getId()+")");
            }else {
                boolean result= JsonUtil.writeJsonFile(optionalExpense.get(),Common_until.expense_fileName,new TypeReference<List<Expense>>(){},2,null);
                if (!result){
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
        if(expenseRequest.getNum()!=null && expenseRequest.getUnit()!=null){
            LocalDateTime now =LocalDateTime.now();
            LocalDateTime after;
            Predicate<Expense> predicate;
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
                    predicate= e -> LocalDate.parse(e.getExpensedate(),Common_until.formatter2).isAfter(after.toLocalDate())||LocalDate.parse(e.getExpensedate(),Common_until.formatter2).isEqual(after.toLocalDate());
                    break;
                case CREATE_TIME:
                    predicate= e -> LocalDateTime.parse(e.getCreateTime(),Common_until.formatter1).isAfter(after)||LocalDateTime.parse(e.getCreateTime(),Common_until.formatter1).isEqual(after) ;
                    break;
                case UPDATE_TIME:
                    predicate= e -> LocalDateTime.parse(e.getUpdateTime(),Common_until.formatter1).isAfter(after)||LocalDateTime.parse(e.getUpdateTime(),Common_until.formatter1).isEqual(after) ;
                    break;
                default:
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("you can only use "+ListCondition.values());
            }
            List<Expense> expenses = JsonUtil.findList(predicate, Common_until.expense_fileName, new TypeReference<List<Expense>>() {});
            if (expenses.isEmpty()){
                return ResponseEntity.ok("this is no data for your search condition");
            }
            return ResponseEntity.ok(expenses);
        }else {
            List<Expense> optionalExpense= JsonUtil.readJsonFile(Common_until.expense_fileName, new TypeReference<List<Expense>>() {});
            return ResponseEntity.ok(optionalExpense);
        }
    }


}
