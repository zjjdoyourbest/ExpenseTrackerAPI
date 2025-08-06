package org.example.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.service.UserService;
import org.example.type.User;
import org.example.requestBody.LoginRequest;
import org.example.util.Common_until;
import org.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private JwtUtil jwtUtil=new JwtUtil();
    private String username;
    private String password;
    @Autowired
    private UserService userService;

    public AuthController() throws IOException {

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user =userService.getUserByName(request.getUsername());

        if(user != null){
            username=user.getUsername();
            password=user.getPassword();

            if (username.equals(request.getUsername()) && password.equals(request.getPassword())) {
                String token = JwtUtil.generateToken(request.getUsername());
                return ResponseEntity.ok(Collections.singletonMap("token", token));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> userSignUp(@RequestBody LoginRequest request){
        String username=request.getUsername();
        String password=request.getPassword();
        if(username!=null && password!=null) {
            username=username.trim();
            password=password.trim();
            if(username.isEmpty() || password.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username or password cannot be empty");
            }else {
                User user =new User(username,password);
                Integer result= userService.addUser(user);
                if (result == 1) {
                    return ResponseEntity.ok("user add successful. now you can login with your username and password.");
                }else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("user add failed. please contract system admin");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parameter is incorrect");
    }
}
