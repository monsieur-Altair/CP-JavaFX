package com.SQLsupport.strategies;

import com.SQLsupport.DBClass.Product;
import com.SQLsupport.SelectableProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import static com.SQLsupport.Constants.*;

public class SelectProductsByManufacturer implements SelectableProduct {

    private String nameOfManufacturer;

    @Override
    public void getData(String data) {
        nameOfManufacturer=data;
    }

    @Override
    public Vector<Product> executeSelect(Connection conn) {
        int count=1;
        ResultSet res=null;
        Vector<Product> products=null;

        try{
            String sql1="SELECT " +
                    PRODUCT_SCHEMA+"." +PRODUCT_ID+", "+
                    PRODUCT_SCHEMA+"." +PRODUCT_NAME+", "+
                    PRODUCT_SCHEMA+"." +PRODUCT_TYPE+", "+
                    PRODUCT_SCHEMA+"." +PRODUCT_COST+", "+
                    PRODUCT_SCHEMA+"." +PRODUCT_COUNT+", "+
                    MANUFACTURER_SCHEMA+"." +MANUFACTURER_NAME+
                    " FROM "+DB_NAME+"."+PRODUCT_SCHEMA+
                    " INNER JOIN "+DB_NAME+"."+MANUFACTURER_SCHEMA+
                    " ON "+PRODUCT_SCHEMA+"."+PRODUCT_MANUFACTURER+"="+ MANUFACTURER_SCHEMA+"."+MANUFACTURER_ID+
                    " AND "+MANUFACTURER_SCHEMA+"."+MANUFACTURER_NAME+"=?"+
                    " ORDER BY "+PRODUCT_SCHEMA+"."+PRODUCT_ID;

            try(PreparedStatement prepStmt=conn.prepareStatement(sql1)){
                prepStmt.setString(1, nameOfManufacturer);
                res = prepStmt.executeQuery();
                products = new Vector<>();
                while(res.next()){
                    count=1;
                    int id = res.getInt(count++);
                    String name = res.getString(count++);
                    String type= res.getString(count++);
                    int cost= res.getInt(count++);
                    int count1= res.getInt(count++);
                    String id_manufacturer= res.getString(count++);
                    products.add(new Product(id,name,type,cost,count1,id_manufacturer));
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return products;
    }
}
