package com.SQLsupport.strategies;

import com.SQLsupport.Updatable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.SQLsupport.Constants.*;

public class BuyProduct implements Updatable {

    private int id_user, product_cost, user_money;

    @Override
    public void getData(String data) {
        String[] dataFromClient=data.split(" ");
        id_user=Integer.parseInt(dataFromClient[0]);
        user_money=Integer.parseInt(dataFromClient[1]);
        product_cost=Integer.parseInt(dataFromClient[2]);
    }

    @Override
    public boolean executeUpdate(Connection conn) {

        if(user_money<product_cost)
            return false;

        String  sql1="UPDATE "+DB_NAME+"."+USER_SCHEMA+
                " SET "+USER_MONEY+"="+USER_MONEY+"-?"+
                " WHERE "+USER_ID+"=?;";
        try(PreparedStatement prepStmt=conn.prepareStatement(sql1)){
            prepStmt.setInt(1,product_cost);
            prepStmt.setInt(2,id_user);
            return prepStmt.executeUpdate()>0;
        }
        catch (SQLException e){

        }
        return false;
    }
}
