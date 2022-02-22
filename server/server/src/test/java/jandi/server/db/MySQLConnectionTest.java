package jandi.server.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnectionTest {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    @Value("${spring.datasource.url}")
    private static String URL;
    @Value("${spring.datasource.username}")
    private static String USER;
    @Value("${spring.datasource.password}")
    private static String PASSWORD;

    @Test
    public void testConnection() throws Exception {
        Class.forName(DRIVER);
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
