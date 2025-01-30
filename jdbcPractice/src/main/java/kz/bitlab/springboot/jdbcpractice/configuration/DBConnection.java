package kz.bitlab.springboot.jdbcpractice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;

@Configuration
public class DBConnection {
    private Connection connection;

    public DBConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/jdbcPractice_DB", "postgres", "postgres");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Bean
    public Connection getConnection() {
        return connection;
    }
}
