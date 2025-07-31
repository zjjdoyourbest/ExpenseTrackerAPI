package org.example.controller;

import org.example.requestBody.ExpenseRequest;
import org.example.requestBody.LoginRequest;
import org.example.util.JsonUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expense")
public class ExpenseTrackerController {

//    @PostMapping("/add")
//    public  ResponseEntity<?> addExpense(@RequestBody ExpenseRequest expenseRequest){
//
//    }
}
