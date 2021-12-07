package edu.soft1.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    String username;
    String pwd;
    int age;
    @DateTimeFormat(pattern = "yyyy-MM-dd") //类型转换
    Date birthday;
    //自定义对象
    Address address;
}
