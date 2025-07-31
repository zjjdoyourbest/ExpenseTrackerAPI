package org.example.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.pojo.User;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


public class JsonUtil {
    private static ObjectMapper mapper=new ObjectMapper();

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static  Path checkJsonFileExist(String fileName){
        Path path = Paths.get("data/"+fileName);
        if(!Files.exists(path)){
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return path;
    }

    public static <T> void writeJsonFile(T obj,String fileName,TypeReference<List<T>> typeReference){
        Path path= checkJsonFileExist(fileName);
        File jsonFile=path.toFile();
        List<T> objs= readJsonFile(fileName, typeReference);
        try {
            objs.add(obj);
            mapper.writeValue(jsonFile,objs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> readJsonFile(String fileName,TypeReference<List<T>> typeReference){
        Path path= checkJsonFileExist(fileName);
        File jsonFile=path.toFile();
        List<T> objs;
        try {
            if(jsonFile.length()!=0) {
                objs = mapper.readValue(jsonFile, typeReference);
            }else{
                return new ArrayList<>();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return objs;
    }

    public static <T> Optional<T> find(Predicate<T> predicate,String fileName,TypeReference<List<T>> typeReference) {
        return readJsonFile(fileName,typeReference).stream().filter(predicate).findFirst();
    }

//    public static User readJsonFileByUsername(String username,TypeReference<List<T>> typeReference){
//        List<User> users = readJsonFile();
//        User user =users.stream()
//                .filter(u -> username.equals(u.getUsername()))
//                .findFirst()
//                .orElse(null);
//        return user;
//    }

    public static <T> int readUserCounts(String fileName,TypeReference<List<T>> typeReference){
        List<T> objs = readJsonFile(fileName,typeReference);
        return objs.size();
    }
}
