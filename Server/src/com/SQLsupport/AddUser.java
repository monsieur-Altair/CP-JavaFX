package com.SQLsupport;

import java.io.IOException;
import java.sql.*;

import static com.gui.Constants.*;


public class AddUser implements Requestable{

    private String login, password, firstName, lastName, address, phone;
    int money,role;

    public AddUser(){}


    @Override
    public void getData(String data){
        String[] transformedData = data.split(" ");
        login=transformedData[0];
        password=transformedData[1];
        role=Integer.parseInt(transformedData[2]);
        firstName=transformedData[3];
        lastName=transformedData[4];
        money=Integer.parseInt(transformedData[5]);
        address=transformedData[6];
        phone=transformedData[7];
    }

    @Override
    public void execute(Connection conn) {
        int rowNumber=0;
        try{
            String sql0="SELECT * FROM db7.user";
            try(Statement stmt= conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)){
                ResultSet result= stmt.executeQuery(sql0);
                result.beforeFirst();
                while(result.next())
                    rowNumber++;
            }
            String sql1="INSERT INTO "+DB_NAME+"."+USER_SCHEMA+" ("+
                    USER_ID+","+ USER_LOGIN+","+ USER_PASSWORD+","+
                    USER_ROLE+","+USER_FIRST_NAME+","+ USER_LAST_NAME+","+
                    USER_MONEY+","+USER_ADDRESS+","+USER_PHONE+")"+" VALUES (?,?,?,?,?,?,?,?,?)";
            try(PreparedStatement prepStmt=conn.prepareStatement(sql1)){
                prepStmt.setInt(1, ++rowNumber);
                prepStmt.setString(2, login);
                prepStmt.setString(3, password);
                prepStmt.setInt(4, role);
                prepStmt.setString(5, firstName);
                prepStmt.setString(6, lastName);
                prepStmt.setInt(7, money);
                prepStmt.setString(8, address);
                prepStmt.setString(9, phone);
                boolean res = prepStmt.executeUpdate() > 0;
                System.out.println(res);
            }
        }catch(SQLException e){

        }
    }
}

