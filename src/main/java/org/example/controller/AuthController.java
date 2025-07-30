package org.example.controller;

import org.example.pojo.User;
import org.example.requestBody.LoginRequest;
import org.example.util.JsonUtil;
import org.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private JwtUtil jwtUtil=new JwtUtil();
    @Value("${user}")
    private String username;
    @Value("${password}")
    private String password;


    public AuthController() throws IOException {

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // 实际应用中应校验用户名密码
        if (username.equals(request.getUsername()) && password.equals(request.getPassword())) {
            String token = JwtUtil.generateToken(request.getUsername());
            return ResponseEntity.ok(Collections.singletonMap("token", token));
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
                JsonUtil.writeJsonFile(user);
                return ResponseEntity.ok("user add successful. now you can login with your username and password.");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parameter is incorrect");
    }
}
