package com.SQLsupport;

import java.sql.Connection;

public interface Requestable {
    void execute(Connection conn);
    void getData(String data);
}
