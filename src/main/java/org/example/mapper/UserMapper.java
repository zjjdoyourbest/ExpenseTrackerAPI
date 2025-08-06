package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.type.User;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAll();
    User findById(Integer id);
    User findByName(String username);
    Integer insert(User user);
}
