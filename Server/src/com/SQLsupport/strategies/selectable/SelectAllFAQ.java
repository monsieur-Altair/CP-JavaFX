package com.SQLsupport.strategies.selectable;

import com.SQLsupport.DBClass.Faq;
import com.SQLsupport.strategies.Requestable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.concurrent.DelayQueue;

import static com.SQLsupport.Constants.*;

public class SelectAllFAQ implements Requestable {
    @Override
    public void getData(String data) {

    }

    public Vector<Faq> executeSelect(Connection conn){
        int count;
        ResultSet res;
        Vector<Faq> faq=null;
        try{
            String sql1="SELECT "+
                    USER_SCHEMA+"."+USER_LOGIN+","+
                    FAQ_SCHEMA+"."+FAQ_QUESTION+","+
                    FAQ_SCHEMA+"."+FAQ_ANSWER+
                    " FROM "+DB_NAME+"."+FAQ_SCHEMA+
                    " INNER JOIN "+ DB_NAME+"."+USER_SCHEMA+
                    " ON "+FAQ_SCHEMA+"."+FAQ_ID_USER+" = "+ USER_SCHEMA+"."+USER_ID;
            System.out.println(sql1);
            try(PreparedStatement prepStmt=conn.prepareStatement(sql1)){
                res = prepStmt.executeQuery();
                faq = new Vector<>();
                while(res.next()){
                    count=1;
                    String login_user=res.getString(count++);
                    String question = res.getString(count++);
                    String answer= res.getString(count++);
                    faq.add(new Faq(login_user,question,answer));
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return faq;
    }
}
