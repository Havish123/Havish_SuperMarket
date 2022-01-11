package com.havish.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance=null;
    private Connection con;

    private DatabaseConnection(){

    }
    public static DatabaseConnection getInstance(){
        if (instance==null){
            instance=new DatabaseConnection();
        }
        return instance;
    }
    public Connection getConnection(){
        String url=DBData.MYSQL_URL+DBData.SCHEMA;
        try{
            Class.forName(DBData.MYSQL_DRIVER);
            con= DriverManager.getConnection(url,DBData.MYSQL_USER_NAME,DBData.MYSQL_PASSWORD);
        }catch (SQLException | ClassNotFoundException exception){
            System.out.println("SQL Exception!"+exception);
        }
        return con;
    }
    public void closeCon(){
        try{
            con.close();
        }catch (SQLException e){
            System.out.println(e);
        }
    }
}
