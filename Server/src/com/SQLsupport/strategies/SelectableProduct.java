package com.SQLsupport.strategies;

import com.SQLsupport.DBClass.Product;
import com.SQLsupport.strategies.Requestable;

import java.sql.Connection;
import java.util.Vector;

public interface SelectableProduct extends Requestable {
    Vector<Product> executeSelect(Connection conn);
}
