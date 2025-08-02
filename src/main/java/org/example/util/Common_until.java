package org.example.util;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;


public class Common_until {

    public static DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public final static String fileName="User.Json";
    public final static String expense_fileName="Expense.Json";
    public static final Map<Integer, String> typeMap = Map.of(
            1,"Groceries",
            2,"Leisure",
            3,"Electronics",
            4,"Utilities",
            5,"Clothing",
            6,"Health",
            7,"Others"
    );

    public static <T> int check(T obj ){
        if(obj instanceof String){
            return (((String) obj).isEmpty() || obj==null) ? 1 :0 ;
        }
        return obj==null ? 1 : 0;
    }
}
