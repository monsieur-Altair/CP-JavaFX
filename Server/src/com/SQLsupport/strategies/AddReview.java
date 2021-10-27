package com.SQLsupport.strategies;

import com.SQLsupport.Updatable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.SQLsupport.Constants.*;
import static com.SQLsupport.Constants.USER_PHONE;

public class AddReview implements Updatable {
    private int id_product,id_user;
    private String review_text;

    @Override
    public void getData(String data) {
        String[] dataFromServer = data.split("@@@");
        id_user=Integer.parseInt(dataFromServer[0]);
        id_product=Integer.parseInt(dataFromServer[1]);
        review_text=dataFromServer[2];
    }

    @Override
    public boolean executeUpdate(Connection conn) {
        int count=1;
        try{
            String sql1="INSERT INTO "+DB_NAME+"."+REVIEW_SCHEMA+" ("+
                    REVIEW_USER+","+ REVIEW_PRODUCT+","+
                    REVIEW_TEXT+")"+" VALUE (?,?,?)";
            try(PreparedStatement prepStmt=conn.prepareStatement(sql1)){
                // prepStmt.setInt(1, ++rowNumber);
                prepStmt.setInt(count++, id_user);
                prepStmt.setInt(count++, id_product);
                prepStmt.setString(count++, review_text);
                boolean res = prepStmt.executeUpdate() > 0;
                System.out.println(res);
                return res;
            }
        }catch(SQLException e){

        }
        return false;
    }
}
