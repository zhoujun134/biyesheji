package bysj.common;

import java.sql.*;

public class JDBCDemo {
    public static Connection getConnection() {
        String driver = "com.mysql.jdbc.Driver";
        String URL = "jdbc:mysql://localhost:3306/test";
        Connection con = null;
        try
        {
            Class.forName(driver);
        }
        catch(java.lang.ClassNotFoundException e)
        {
            System.out.println("Connect Successfull.");
            System.out.println("Cant't load Driver");
          e.printStackTrace();
        }
        try
        {
            con=DriverManager.getConnection(URL,"root","root");
            System.out.println("Connect Successfull.");
        }
        catch(Exception e)
        {
            System.out.println("Connect fail:" + e.getMessage());
        }
        return con;
    }
}
