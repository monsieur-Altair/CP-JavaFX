module Server {
    requires java.sql;
    requires mysql.connector.java;
    requires Client;
    exports com.SQLsupport;
}