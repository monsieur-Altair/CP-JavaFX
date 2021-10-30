package com.SQLsupport;

import java.sql.*;

public class DBConnection {
    private Connection connection;

    public DBConnection(){

    }

    public void init() throws InstantiationException, IllegalAccessException {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection=DriverManager.getConnection("jdbc:mysql://localhost/db7", "root", "583M6259vn328");
            System.out.println("Connection to database is succesfull!");
        }
        catch (SQLException | ClassNotFoundException e)
        {
            System.out.println("oooops");
            System.out.println(e);
            e.printStackTrace();
        }

    }

    public Connection getMyConnection()
    {
        return connection;
    }

/*    public void close(ResultSet rs)
    {
        if(rs !=null)
        {
            try
            {
                rs.close();
            }
            catch(Exception e){}
        }
    }

    public void close(java.sql.Statement stmt)
    {
        if(stmt !=null)
        {
            try
            {
                stmt.close();
            }
            catch(Exception e){}
        }
    }*/

    public void destroy()
    {
        if(connection !=null)
        {
            try
            {
                connection.close();
            }
            catch(Exception e){}
        }
    }

}


