package com.zerobase.fastlms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
public class FastlmsApplication {

    static String URL="jdbc:mariadb://localhost:43306/minicampus";
    public static void main(String[] args) throws ClassNotFoundException {
        SpringApplication.run(FastlmsApplication.class, args);

    }
    
}
