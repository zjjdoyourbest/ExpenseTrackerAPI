package org.example.service;

import org.example.mapper.UserMapper;
import org.example.type.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    public User getUserById(Integer id) {
        return userMapper.findById(id);
    }

    public User getUserByName(String username) {
        return userMapper.findByName(username);
    }

    public Integer addUser(User user){
        return userMapper.insert(user);
    }
}
