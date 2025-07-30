package org.example.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.pojo.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class JsonUtil {
    private static ObjectMapper mapper=new ObjectMapper();
    private static Path path = Paths.get("data/User.Json");

    public static void checkJsonFileExist(){
        if(!Files.exists(path)){
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void writeJsonFile(User user){
        checkJsonFileExist();
        File jsonFile=path.toFile();
        List<User> users= readJsonFile();
        try {
            users.add(user);
            mapper.writeValue(jsonFile,users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static List<User> readJsonFile(){
        checkJsonFileExist();
        File jsonFile=path.toFile();
        List<User> users;
        try {
            if(jsonFile.length()!=0) {
                users = mapper.readValue(jsonFile, new TypeReference<List<User>>() {
                });
            }else{
                return new ArrayList<>();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public static User readJsonFileByUsername(String username){
        List<User> users = readJsonFile();
        User user =users.stream()
                .filter(u -> username.equals(u.getUsername()))
                .findFirst()
                .orElse(null);
        return user;
    }

    public static int readUserCounts(){
        List<User> users = readJsonFile();
        return users.size();
    }
}
