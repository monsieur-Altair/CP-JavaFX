package com.SQLsupport.strategies;

import com.SQLsupport.DBClass.Purchase;
import com.SQLsupport.DBClass.Review;
import com.SQLsupport.Requestable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import static com.SQLsupport.Constants.*;
import static com.SQLsupport.Constants.PRODUCT_NAME;

public class SelectAllPurchases implements Requestable {
    private int user_id;

    @Override
    public void getData(String data) {
        user_id=Integer.parseInt(data);
    }

    public int getId(){
        return user_id;
    }

    public Vector<Purchase> executeSelect(Connection conn) {
        int count=1;
        ResultSet res=null;
        Vector<Purchase> purchases=null;

        try{
            String sql1="SELECT " +
                    PURCHASE_SCHEMA+"." +PURCHASE_ID+", "+
                    PRODUCT_SCHEMA+"." +PRODUCT_NAME+", "+
                    PRODUCT_SCHEMA+"." +PRODUCT_TYPE+", "+
                    PRODUCT_SCHEMA+"." +PRODUCT_COST+", "+
                    MANUFACTURER_SCHEMA+"." +MANUFACTURER_NAME+
                    " FROM "+DB_NAME+"."+PURCHASE_SCHEMA+
                    " INNER JOIN "+DB_NAME+"."+USER_SCHEMA+
                    " ON "+PURCHASE_SCHEMA+"."+PURCHASE_USER+"="+ USER_SCHEMA+"."+USER_ID+
                    " INNER JOIN "+DB_NAME+"."+PRODUCT_SCHEMA+
                    " ON "+PURCHASE_SCHEMA+"."+PURCHASE_PRODUCT+"="+ PRODUCT_SCHEMA+"."+PRODUCT_ID+
                    " INNER JOIN "+DB_NAME+"."+MANUFACTURER_SCHEMA+
                    " ON "+PRODUCT_SCHEMA+"."+PRODUCT_MANUFACTURER+"="+ MANUFACTURER_SCHEMA+"."+MANUFACTURER_ID+
                    " WHERE "+PURCHASE_SCHEMA+"."+PURCHASE_USER+"=? ORDER BY "+PURCHASE_ID;
            try(PreparedStatement prepStmt=conn.prepareStatement(sql1)){
                prepStmt.setInt(1, user_id);
                res = prepStmt.executeQuery();
                purchases = new Vector<>();
                while(res.next()){
                    count=1;
                    int id_purchase=res.getInt(count++);
                    String productName = res.getString(count++);
                    String productType= res.getString(count++);
                    int productCost=res.getInt(count++);
                    String manufName= res.getString(count++);

                    purchases.add(new Purchase(id_purchase,productName,productType,productCost,manufName));
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return purchases;
    }
}
