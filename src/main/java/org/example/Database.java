package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final Database INSTANCE = new Database();
    private final String dbUrl = "jdbc:h2:./test";
    private final String dbUser = "sa";
    private final String dbPass = null;
    private Connection connection;

    private Database (){
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static Database getInstance(){
        return INSTANCE;
    }
    public Connection getConnection(){
        return connection;
    }
}
