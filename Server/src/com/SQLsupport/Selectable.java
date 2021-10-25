package com.SQLsupport;

import com.SQLsupport.DBClass.User;

import java.sql.Connection;
import java.util.ArrayList;

public interface Selectable<T> extends Requestable{
    ArrayList<T> executeSelect(Connection conn);
}
