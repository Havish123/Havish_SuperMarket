package com.havish.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection con;
    static {
        String url=DBData.MYSQL_URL+DBData.SCHEMA;
        try{
            Class.forName(DBData.MYSQL_DRIVER);
            con= DriverManager.getConnection(url,DBData.MYSQL_USER_NAME,DBData.MYSQL_PASSWORD);
        }catch (SQLException | ClassNotFoundException exception){
            System.out.println("SQL Exception!"+exception);
        }
    }
    public static Connection getConnection(){
        return con;
    }
    public static void closeCon(){
        try{
            con.close();
        }catch (SQLException e){
            System.out.println(e);
        }
    }
}
