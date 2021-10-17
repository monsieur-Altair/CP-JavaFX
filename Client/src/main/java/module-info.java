module Client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires Server;
    requires java.sql;

    opens com.gui.client to javafx.fxml;
    exports com.gui.client;
}